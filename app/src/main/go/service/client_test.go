package service

import (
	"net"
	"server/proto"
	"testing"
)

var clientsTest []*proto.Client

func TestNewClient(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		address, err := net.ResolveTCPAddr("tcp", addr)
		if err != nil {
			t.Fatal(err)
		}

		value, err := net.DialTCP("tcp", nil, address)
		if err != nil {
			t.Fatal(err)
		}

		client := NewClient(value)
		if client == nil {
			t.Fatal("nil client")
		}
	})
}

func TestGetClients(t *testing.T) {
	clientsTest = GetClients()

	if clients.Count() != len(clientsTest) {
		t.Fatal(len(clientsTest))
	}
}

func TestRegisterClient(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		err := RegisterClient(clientsTest[i], name)
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = RegisterClient(new(proto.Client), name)
		if err != proto.Error_CLIENT_NAME_TAKEN {
			t.Fatal(err)
		}

		err = RegisterClient(new(proto.Client), "")
		if err != proto.Error_CLIENT_NAME_EMPTY {
			t.Fatal(err)
		}
	})
}

func TestRemoveClient(t *testing.T) {
	clients.Iterate(func(key interface{}, value interface{}) {
		if key.(*proto.Client) == nil {
			t.Fatal("nil client")
		}

		if value.(*net.TCPConn) == nil {
			t.Fatal("nil conn")
		}
	})

	doInLoop(func(i int, name, room string) {
		err := RemoveClient(clientsTest[i])
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = RemoveClient(new(proto.Client))
		if err != proto.Error_CLIENT_NOT_FOUND {
			t.Fatal(err)
		}
	})

	clients.Iterate(func(key interface{}, value interface{}) {
		if key.(*proto.Client) != nil {
			t.Fatal("not nil client")
		}

		if value.(*net.TCPConn) != nil {
			t.Fatal("not nil conn")
		}
	})

	if clients.Count() != 0 {
		t.Fatal(clients.Count())
	}
}
