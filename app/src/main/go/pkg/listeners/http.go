package listeners

import (
	"fmt"
	"github.com/dawidd6/AndTTT-server/pkg/handlers"
	"github.com/gorilla/mux"
	"net"
	"net/http"
)

func StartListeningHTTP(host string, port int) (*http.Server, error) {
	router := mux.NewRouter()
	server := new(http.Server)

	handlers.SetHandlers(router)

	listener, err := net.Listen("tcp", fmt.Sprintf("%s:%d", host, port))
	if err != nil {
		return nil, err
	}

	go http.Serve(listener, router)

	return server, nil
}

func StopListeningHTTP(server *http.Server) error {
	if err := server.Shutdown(nil); err != nil {
		return err
	}

	return nil
}
