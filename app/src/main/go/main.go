package main

import (
	"flag"
	"io/ioutil"
	"log"
	"net"
	"net/http"
	"os"
	"os/signal"
	"server/handlers"
	"server/listeners"
	"server/msg"
	"server/proto"
	"server/service"
	"syscall"
	"time"
)

var (
	version string = "0.5.0+git"
	logger *log.Logger
)

func main() {
	logger = log.New(os.Stdout, "", log.Lshortfile)

	sig := make(chan os.Signal)
	hostTCP := flag.String("host-tcp", "127.0.0.1", "ip address on which TCP service will listen")
	hostHTTP := flag.String("host-http", "127.0.0.1", "ip address on which HTTP service will listen")
	portTCP := flag.Int("port-tcp", 33333, "port on which TCP service will listen")
	portHTTP := flag.Int("port-http", 33334, "port on which HTTP service will listen")
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

	handlers.OnApiRequest = func(request *http.Request) {
		logger.Println(request.Method, request.RequestURI, request.RemoteAddr)
	}

	listenerTCP, err := listeners.StartListeningTCP(*hostTCP, *portTCP)
	if err != nil {
		logger.Fatal(err)
	}

	listenerHTTP, err := listeners.StartListeningHTTP(*hostHTTP, *portHTTP)
	if err != nil {
		logger.Fatal(err)
	}

	defer func() {
		if err := listeners.StopListeningTCP(listenerTCP); err != nil {
			logger.Fatal(err)
		}
		if err := listeners.StopListeningHTTP(listenerHTTP); err != nil {
			logger.Fatal(err)
		}
	}()

	logger.Println("version", version)
	logger.Println("cleaning at interval", *cleanInterval)
	logger.Println("listening TCP", *hostTCP, *portTCP)
	logger.Println("listening HTTP", *hostHTTP, *portHTTP)

	go accept(listenerTCP)
	go clean(*cleanInterval)

	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	<-sig
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
