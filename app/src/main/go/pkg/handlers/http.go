package handlers

import (
	"encoding/json"
	"fmt"
	"github.com/dawidd6/AndTTT-server/pkg/service"
	"github.com/gorilla/mux"
	"net/http"
	"runtime"
)

var OnApiRequest = func(request *http.Request) {}

func SetHandlers(router *mux.Router) {
	router.HandleFunc("/clients", handleClients).Methods("GET")
	router.HandleFunc("/rooms", handleRooms).Methods("GET")
	router.HandleFunc("/status", handleStatus).Methods("GET")
}

// Handlers
func handleClients(writer http.ResponseWriter, request *http.Request) {
	OnApiRequest(request)

	jason, _ := json.MarshalIndent(service.GetClients(), "", "  ")
	fmt.Fprintln(writer, string(jason))
}

func handleRooms(writer http.ResponseWriter, request *http.Request) {
	OnApiRequest(request)

	jason, _ := json.MarshalIndent(service.GetRooms(), "", "  ")
	fmt.Fprintln(writer, string(jason))
}

func handleStatus(writer http.ResponseWriter, request *http.Request) {
	OnApiRequest(request)

	var status struct {
		NumGoroutines int `json:"num_goroutines"`
		NumClients    int `json:"num_clients"`
		NumRooms      int `json:"num_rooms"`
	}

	status.NumGoroutines = runtime.NumGoroutine()
	status.NumClients = len(service.GetClients())
	status.NumRooms = len(service.GetRooms())

	jason, _ := json.MarshalIndent(status, "", "  ")
	fmt.Fprintln(writer, string(jason))
}
