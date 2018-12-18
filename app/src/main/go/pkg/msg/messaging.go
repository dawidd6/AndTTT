package msg

import (
	"github.com/dawidd6/AndTTT-server/pkg/proto"
	pb "github.com/golang/protobuf/proto"
	"net"
)

// Send writes []byte to *net.TCPConn and returns error
func Send(conn *net.TCPConn, data []byte) error {
	_, err := conn.Write(data)

	return err
}

// Receive creates buffer, blocks the code flow one time with Read function
// and returns read data
// It's convenient to use this function in for loop with goroutine
// to constantly check for available data to read
func Receive(conn *net.TCPConn) ([]byte, error) {
	buffer := make([]byte, 4096)
	length, err := conn.Read(buffer)

	if err != nil {
		return []byte(""), err
	}

	return buffer[:length], nil
}

func SendResponse(conn *net.TCPConn, message *proto.Response) error {
	jason, err := pb.Marshal(message)
	if err != nil {
		return err
	}

	return Send(conn, jason)
}

func ReceiveRequest(conn *net.TCPConn) (*proto.Request, error) {
	message := new(proto.Request)
	buffer, err := Receive(conn)

	if err != nil {
		return nil, err
	}

	return message, pb.Unmarshal(buffer, message)
}
