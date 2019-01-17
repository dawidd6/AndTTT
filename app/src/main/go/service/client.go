package service

import (
	"github.com/dawidd6/go-mmap"
	"github.com/golang/protobuf/ptypes"
	"net"
	"server/proto"
	"strings"
)

// clients[*proto.Client]*net.TCPConn
var clients = mmap.New()

func NewClient(conn *net.TCPConn) *proto.Client {
	client := new(proto.Client)
	client.Since = ptypes.TimestampNow()
	clients.Set(client, conn)
	return client
}

func RegisterClient(client *proto.Client, name string) proto.Error {
	if len(name) > 20 {
		return proto.Error_CLIENT_NAME_TOO_LONG
	}

	if strings.TrimSpace(name) == "" {
		return proto.Error_CLIENT_NAME_EMPTY
	}

	taken := false
	clients.Iterate(func(key interface{}, value interface{}) {
		if key.(*proto.Client).Name == name {
			taken = true
		}
	})
	if taken {
		return proto.Error_CLIENT_NAME_TAKEN
	}

	client.Name = name
	return proto.Error_NONE
}

func RemoveClient(client *proto.Client) proto.Error {
	if !clients.Has(client) {
		return proto.Error_CLIENT_NOT_FOUND
	}

	conn := clients.Get(client)
	if conn != nil {
		conn.(*net.TCPConn).Close()
	}

	clients.Remove(client)
	return proto.Error_NONE
}

func GetClients() []*proto.Client {
	data := make([]*proto.Client, 0)

	clients.Iterate(func(key interface{}, value interface{}) {
		data = append(data, key.(*proto.Client))
	})

	return data
}
