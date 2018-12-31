package api

import (
    "encoding/json"
    "github.com/husobee/vestigo"
    "net/http"
)

var OnApiRequest = func(request *http.Request) {}

func SetHandlers(router *vestigo.Router) {
    router.Get("/clients", handleClients)
    router.Get("/client/:name", handleClient)
    router.Get("/rooms", handleRooms)
    router.Get("/room/:name", handleRoom)
    router.Get("/status", handleStatus)
}

func write(writer http.ResponseWriter, v interface{}) {
    jason, err := json.MarshalIndent(v, "", "  ")
    if err != nil {
        writer.WriteHeader(http.StatusInternalServerError)
    } else {
        writer.Write(jason)
    }
}
