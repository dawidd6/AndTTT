package listeners

import (
	"fmt"
	"net"
)

func StartListeningTCP(host string, port int) (*net.TCPListener, error) {
	addr, err := net.ResolveTCPAddr("tcp", fmt.Sprintf("%s:%d", host, port))

	listener, err := net.ListenTCP("tcp", addr)
	if err != nil {
		return nil, err
	}

	return listener, nil
}

func StopListeningTCP(listener net.Listener) error {
	if err := listener.Close(); err != nil {
		return err
	}

	return nil
}
