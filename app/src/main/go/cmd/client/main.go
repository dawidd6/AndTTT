package main

import (
	"fmt"
	"github.com/dawidd6/AndTTT-server/pkg/proto"
	pb "github.com/golang/protobuf/proto"
	"github.com/thanhpk/randstr"
	"log"
	"math/rand"
	"net"
	"os"
	"os/exec"
)

var (
	address  = "localhost:33333"
	logger   = log.New(os.Stdout, "", log.LstdFlags|log.Lshortfile)
	name     = ""
	room     = ""
	pos      = int32(0)
	password = ""
)

func main() {
	addr, err := net.ResolveTCPAddr("tcp", address)
	if err != nil {
		logger.Fatal(err)
	}

	conn, err := net.DialTCP("tcp", nil, addr)
	if err != nil {
		logger.Fatal(err)
	}

	go read(conn)

	for {
		request := new(proto.Request)
		choice := 0

		cmd := exec.Command("clear")
		cmd.Stdout = os.Stdout
		cmd.Run()

		fmt.Println("name =", name)
		fmt.Println("room =", room)
		fmt.Println("pos =", pos)
		fmt.Println("password =", password)

		fmt.Println("\nMENU:")

		fmt.Println("1. REGISTER_NAME")
		fmt.Println("2. CREATE_ROOM")
		fmt.Println("3. JOIN_ROOM")
		fmt.Println("4. LEAVE_ROOM")
		fmt.Println("5. MOVE")
		fmt.Println("6. STARTER_PACK")
		fmt.Println("7. RESTART REQUEST")
		fmt.Println("8. RESTART APPROVE")
		fmt.Println("9. RESTART DENY")

		fmt.Println("-1. randomize name")
		fmt.Println("-2. randomize room")
		fmt.Println("-3. randomize pos")

		fmt.Println("-4. input name")
		fmt.Println("-5. input room")
		fmt.Println("-6. input pos")
		fmt.Println("-7. input password")

		fmt.Printf("Choice: ")

		fmt.Scanf("%d", &choice)

		switch choice {
		case -1:
			name = randstr.String(5)
			continue
		case -2:
			room = randstr.String(10)
			continue
		case -3:
			pos = rand.Int31n(9)
			continue
		case -4:
			fmt.Printf("Enter name: ")
			fmt.Scanf("%s", &name)
			continue
		case -5:
			fmt.Printf("Enter room: ")
			fmt.Scanf("%s", &room)
			continue
		case -6:
			fmt.Printf("Enter pos: ")
			fmt.Scanf("%d", &pos)
			continue
		case -7:
			fmt.Printf("Enter password: ")
			fmt.Scanf("%s", &password)
			continue
		case 1:
			request.Type = &proto.Request_RegisterName{
				RegisterName: &proto.RegisterNameRequest{
					Name: name,
				},
			}
		case 2:
			request.Type = &proto.Request_CreateRoom{
				CreateRoom: &proto.CreateRoomRequest{
					Name:     room,
					Password: password,
				},
			}
		case 3:
			request.Type = &proto.Request_JoinRoom{
				JoinRoom: &proto.JoinRoomRequest{
					Name:     room,
					Password: password,
				},
			}
		case 4:
			request.Type = &proto.Request_LeaveRoom{
				LeaveRoom: &proto.LeaveRoomRequest{},
			}
			room = ""
		case 5:
			request.Type = &proto.Request_Move{
				Move: &proto.MoveRequest{
					Position: pos,
				},
			}
		case 6:
			request.Type = &proto.Request_StarterPack{
				StarterPack: &proto.StarterPackRequest{},
			}
		case 7:
			request.Type = &proto.Request_Restart{
				Restart: &proto.RestartRequest{
					Restart: proto.Restart_REQUESTED,
				},
			}
		case 8:
			request.Type = &proto.Request_Restart{
				Restart: &proto.RestartRequest{
					Restart: proto.Restart_APPROVED,
				},
			}
		case 9:
			request.Type = &proto.Request_Restart{
				Restart: &proto.RestartRequest{
					Restart: proto.Restart_DENIED,
				},
			}
		default:
			continue
		}

		fmt.Println("-------------------")
		fmt.Printf("Request: %v\n", request)
		write(conn, request)
		fmt.Println("-------------------")
		fmt.Scanln()
	}
}

func write(conn *net.TCPConn, request *proto.Request) {
	buffer, err := pb.Marshal(request)
	if err != nil {
		logger.Fatal(err)
	}

	conn.Write(buffer)
}

func read(conn *net.TCPConn) {
	for {
		buffer := make([]byte, 4096)
		response := new(proto.Response)

		length, err := conn.Read(buffer)
		if err != nil {
			logger.Fatal(err)
		}

		err = pb.Unmarshal(buffer[:length], response)
		if err != nil {
			logger.Fatal(err)
		}

		fmt.Printf("Response: %v\n", response)
	}
}
