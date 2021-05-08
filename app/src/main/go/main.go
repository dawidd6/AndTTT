package main

import (
	"flag"
	"io"
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

	"github.com/husobee/vestigo"
)

var (
	sig              = make(chan os.Signal)
	logger           = log.New(os.Stdout, "", log.Lshortfile)
	network          = "tcp"
	keepAlivePeriod  = time.Second * 30
	addrTCP          = "0.0.0.0:33333"
	addrHTTP         = "0.0.0.0:33334"
	logQuiet         = false
	logDate          = false
	logMessages      = false
	onlyPrintVersion = false
	cleanInterval    = time.Minute * 5
)

func main() {
    port := os.Getenv("PORT")
    if port != "" {
        addrTCP = "0.0.0.0:" + port
    }

	flag.StringVar(&addrTCP, "addr-tcp", addrTCP, "ip address on which TCP service will listen")
	flag.StringVar(&addrHTTP, "addr-http", addrHTTP, "ip address on which HTTP service will listen")
	flag.BoolVar(&logQuiet, "log-quiet", logQuiet, "produce no output at all")
	flag.BoolVar(&logDate, "log-date", logDate, "prepend output with date")
	flag.BoolVar(&logMessages, "log-messages", logMessages, "log every request and response")
	flag.DurationVar(&cleanInterval, "room-clean-interval", cleanInterval, "interval between room cleaning")
    flag.Parse()

	if logQuiet {
		logger.SetOutput(ioutil.Discard)
	}

	if logDate {
		logger.SetFlags(log.LstdFlags | logger.Flags())
	}

	go listenTCP()
	go listenHTTP()
	go clean()

	signal.Notify(sig, syscall.SIGINT, syscall.SIGTERM)
	<-sig
}

func listenTCP() {
	addr, err := net.ResolveTCPAddr(network, addrTCP)
	if err != nil {
		logger.Fatalln("E/ResolveTCPAddr", err)
	}
	logger.Println("ResolveTCPAddr", addr)

	listener, err := net.ListenTCP(network, addr)
	if err != nil {
		logger.Fatalln("E/ListenTCP", err)
	}
	logger.Println("ListenTCP", listener.Addr().String())

	go accept(listener)
}

func listenHTTP() {
	router := vestigo.NewRouter()

	api.SetHandlers(router)
	api.OnApiRequest = func(request *http.Request) {
		logger.Println(request.Method, request.RequestURI, request.RemoteAddr)
	}

	listener, err := net.Listen(network, addrHTTP)
	if err != nil {
		logger.Fatalln("E/Listen", err)
	}
	logger.Println("Listen", listener.Addr().String())

	err = http.Serve(listener, router)
	if err != nil {
		logger.Fatalln("E/Serve", err)
	}
}

func accept(listener *net.TCPListener) {
	for {
		conn, err := listener.AcceptTCP()
		if conn == nil {
			break
		}
		addr := conn.RemoteAddr().String()

		if err != nil {
			logger.Println("E/AcceptTCP", addr, err)
			continue
		}
		logger.Println("AcceptTCP", addr)

		err = conn.SetKeepAlive(true)
		if err != nil {
			logger.Println("E/SetKeepAlive", addr, err)
			continue
		}

		err = conn.SetKeepAlivePeriod(keepAlivePeriod)
		if err != nil {
			logger.Println("E/SetKeepAlivePeriod", addr, err)
			continue
		}

		go read(conn)
	}
}

func read(conn *net.TCPConn) {
	client := service.NewClient(conn)
	addr := conn.RemoteAddr().String()

	for {
		request, err := msg.ReceiveRequest(conn)
		if err == io.EOF {
			break
		}
		if err != nil {
			logger.Println("E/ReceiveRequest", addr)
			break
		} else if logMessages {
			logger.Println("ReceiveRequest", addr, request.String())
		}

		go dispatch(client, conn, request)
	}

	disconnect(client, conn)
}

func clean() {
	for {
		<-time.After(cleanInterval)

		names, err := service.CleanRooms(cleanInterval)
		if err != proto.Error_NONE {
			logger.Println("E/CleanRooms", names)
		} else if len(names) > 0 {
			logger.Println("CleanRooms", names)
		}
	}
}

func disconnect(client *proto.Client, conn *net.TCPConn) {
	addr := conn.RemoteAddr().String()
	name := client.Name

	if client.Room != "" {
		// notify enemy of disconnected client about this fact
		enemy, connEnemy, err := service.GetEnemy(client)
		if err != proto.Error_NONE {
			logger.Println("E/GetEnemy", addr, err)
		} else {
			logger.Println("GetEnemy", addr, name)

			enemy.Ready = true

			response := &proto.Response{
				Type: &proto.Response_EnemyDisconnected{
					EnemyDisconnected: &proto.EnemyDisconnectedResponse{},
				},
			}

			err := msg.SendResponse(connEnemy, response)
			if err != nil {
				logger.Println("E/SendResponse", err)
			} else if logMessages {
				logger.Println("SendResponse", addr, response.String())
			}
		}

		// leave room
		if err := service.LeaveRoom(client); err != proto.Error_NONE {
			logger.Println("E/LeaveRoom", addr, name, err)
		} else {
			logger.Println("LeaveRoom", addr, name)
		}
	}

	// finally remove client
	if err := service.RemoveClient(client); err != proto.Error_NONE {
		logger.Println("E/RemoveClient", addr, name, err)
	} else {
		logger.Println("RemoveClient", addr, name)
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
			logger.Println("E/SendResponse", addr, err)
		} else if logMessages {
			logger.Println("SendResponse", addr, dispatch.Response.String())
		}
	}

	if dispatch.ConnEnemy != nil {
		err := msg.SendResponse(dispatch.ConnEnemy, dispatch.ResponseEnemy)
		addr := dispatch.ConnEnemy.RemoteAddr()
		if err != nil {
			logger.Println("E/SendResponse", addr, err)
		} else if logMessages {
			logger.Println("SendResponse", addr, dispatch.ResponseEnemy.String())
		}
	}
}
