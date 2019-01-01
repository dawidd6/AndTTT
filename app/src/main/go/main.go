package main

import (
	"flag"
	"github.com/husobee/vestigo"
	"io/ioutil"
	"log"
	"net"
	"net/http"
	"os"
	"os/signal"
	"server/api"
	"server/handlers"
	"server/msg"
	"server/proto"
	"server/service"
	"syscall"
	"time"
)

var (
	version string = "0.5.2"
	logger *log.Logger
)

func main() {
	logger = log.New(os.Stdout, "", log.Lshortfile)

	sig := make(chan os.Signal)
	addrTCP := flag.String("addr-tcp", "127.0.0.1:33333", "ip address on which TCP service will listen")
	addrHTTP := flag.String("addr-http", "127.0.0.1:33334", "ip address on which HTTP service will listen")
	quiet := flag.Bool("quiet", false, "produce no output at all")
	date := flag.Bool("date", false, "prepend output with date")
	cleanInterval := flag.Duration("room-clean-interval", time.Minute*5, "interval between room cleaning")
	flag.Parse()

	if *quiet {
		logger.SetOutput(ioutil.Discard)
	}

	if *date {
		logger.SetFlags(log.LstdFlags | logger.Flags())
	}

	go listen(*addrHTTP, *addrTCP)
	go clean(*cleanInterval)

	logger.Println("version", version)
	logger.Println("cleaning at interval", *cleanInterval)
	logger.Println("listening TCP", *addrTCP)
	logger.Println("listening HTTP", *addrHTTP)

	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	<-sig
}

func listen(addrHTTP, addrTCP string) {
	router := vestigo.NewRouter()

	api.SetHandlers(router)
	api.OnApiRequest = func(request *http.Request) {
		logger.Println(request.Method, request.RequestURI, request.RemoteAddr)
	}

	addr, err := net.ResolveTCPAddr("tcp", addrTCP)
	if err != nil {
		logger.Fatal("E/listen", err)
	}

	listener, err := net.ListenTCP("tcp", addr)
	if err != nil {
		logger.Fatal("E/listen", err)
	}

	go accept(listener)

	err = http.ListenAndServe(addrHTTP, router)
	if err != nil {
		logger.Fatal("E/listen", err)
	}
}

func accept(listener *net.TCPListener) {
	for {
		conn, err := listener.AcceptTCP()

		if conn == nil {
			return
		}

		if err != nil {
			logger.Println("E/accept", conn.RemoteAddr())
			continue
		}

		conn.SetKeepAlive(true)
		conn.SetKeepAlivePeriod(time.Second * 30)

		logger.Println("accept", conn.RemoteAddr())
		go read(conn)
	}
}

func read(conn *net.TCPConn) {
	client := service.NewClient(conn)

	for {
		request, err := msg.ReceiveRequest(conn)
		if err != nil {
			break
		}

		go dispatch(client, conn, request)
	}

	disconnect(client, conn)
}

func clean(cleanInterval time.Duration) {
	for {
		<-time.After(cleanInterval)

		names, err := service.CleanRooms(cleanInterval)
		if err != proto.Error_NONE {
			logger.Println("E/clean", names)
		} else if len(names) > 0 {
			logger.Println("clean", names)
		}
	}
}

func disconnect(client *proto.Client, conn *net.TCPConn) {
	logger.Println("disconnect", conn.RemoteAddr(), client.Name)

	// notify enemy of disconnected client about this fact
	enemy, connEnemy, err := service.GetEnemy(client)
	if err != proto.Error_NONE {
		logger.Println("E/disconnect", err)
	} else {
		enemy.Ready = true

		response := &proto.Response{
			Type: &proto.Response_EnemyDisconnected{
				EnemyDisconnected: &proto.EnemyDisconnectedResponse{},
			},
		}
		if err := msg.SendResponse(connEnemy, response); err != nil {
			logger.Println("E/disconnect", err)
		}
	}

	// leave room
	if err := service.LeaveRoom(client); err != proto.Error_NONE {
		logger.Println("E/disconnect", err)
	}

	// finally remove client
	if err := service.RemoveClient(client); err != proto.Error_NONE {
		logger.Println("E/disconnect", err)
	}
}

func dispatch(client *proto.Client, conn *net.TCPConn, request *proto.Request) {
	dispatch := &handlers.Dispatch{
		Request:       request,
		Client:        client,
		Conn:          conn,
		ConnEnemy:     nil,
		Response:      new(proto.Response),
		ResponseEnemy: new(proto.Response),
	}

	switch request.Type.(type) {
	case *proto.Request_RegisterName:
		handlers.OnRegisterName(dispatch)
	case *proto.Request_CreateRoom:
		handlers.OnCreateRoom(dispatch)
	case *proto.Request_JoinRoom:
		handlers.OnJoinRoom(dispatch)
	case *proto.Request_GetRooms:
		handlers.OnGetRooms(dispatch)
	case *proto.Request_LeaveRoom:
		handlers.OnLeaveRoom(dispatch)
	case *proto.Request_Move:
		handlers.OnMove(dispatch)
	case *proto.Request_Restart:
		handlers.OnRestart(dispatch)
	case *proto.Request_StarterPack:
		handlers.OnStarterPack(dispatch)
	default:
		handlers.OnDefault(dispatch)
	}

	if dispatch.Conn != nil {
		err := msg.SendResponse(dispatch.Conn, dispatch.Response)
		addr := dispatch.Conn.RemoteAddr()
		if err != nil {
			logger.Println("E/dispatch", err, addr)
		}
	}

	if dispatch.ConnEnemy != nil {
		err := msg.SendResponse(dispatch.ConnEnemy, dispatch.ResponseEnemy)
		addr := dispatch.ConnEnemy.RemoteAddr()
		if err != nil {
			logger.Println("E/dispatchEnemy", err, addr)
		}
	}
}
