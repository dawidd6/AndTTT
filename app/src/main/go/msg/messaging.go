package msg

import (
	"encoding/binary"
	pb "github.com/golang/protobuf/proto"
	"net"
	"server/proto"
)

func Send(conn *net.TCPConn, data []byte) error {
	frame := make([]byte, 4)
	binary.BigEndian.PutUint32(frame, uint32(len(data)))
	frame = append(frame, data...)

	_, err := conn.Write(frame)

	return err
}

func Receive(conn *net.TCPConn) ([]byte, error) {
	// read first 4 bytes to get the length of data
	buffer := make([]byte, 4)

	_, err := conn.Read(buffer)
	if err != nil {
		return nil, err
	}

	// buffer length should never exceed 8 MB
	length := binary.BigEndian.Uint32(buffer)
	if length > 8*1024*1024 {
		return nil, nil
	}

	// read the rest of bytes to get data
	buffer = make([]byte, length)

	_, err = conn.Read(buffer)
	if err != nil {
		return nil, err
	}

	return buffer, nil
}

func SendResponse(conn *net.TCPConn, message *proto.Response) error {
	data, err := pb.Marshal(message)
	if err != nil {
		return err
	}

	return Send(conn, data)
}

func SendRequest(conn *net.TCPConn, message *proto.Request) error {
	data, err := pb.Marshal(message)
	if err != nil {
		return err
	}

	return Send(conn, data)
}

func ReceiveRequest(conn *net.TCPConn) (*proto.Request, error) {
	message := new(proto.Request)
	buffer, err := Receive(conn)

	if err != nil {
		return nil, err
	}

	return message, pb.Unmarshal(buffer, message)
}

func ReceiveResponse(conn *net.TCPConn) (*proto.Response, error) {
	message := new(proto.Response)
	buffer, err := Receive(conn)

	if err != nil {
		return nil, err
	}

	return message, pb.Unmarshal(buffer, message)
}
