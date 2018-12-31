package api

import (
    "github.com/husobee/vestigo"
    "net/http"
    "runtime"
    "server/proto"
    "server/service"
)

func handleClients(writer http.ResponseWriter, request *http.Request) {
    OnApiRequest(request)

    clients := service.GetClients()

    write(writer, &proto.ApiClients{
        Count: int32(len(clients)),
        Clients: clients,
    })
}

func handleClient(writer http.ResponseWriter, request *http.Request) {
    OnApiRequest(request)

    name := vestigo.Param(request, "name")

    for _, client := range service.GetClients() {
        if client.Name == name {
            write(writer, &proto.ApiClient{
                Client: client,
            })
            return
        }
    }

    writer.WriteHeader(http.StatusNotFound)
}

func handleRooms(writer http.ResponseWriter, request *http.Request) {
    OnApiRequest(request)

    rooms := service.GetRooms()

    write(writer, &proto.ApiRooms{
        Count: int32(len(rooms)),
        Rooms: rooms,
    })
}

func handleRoom(writer http.ResponseWriter, request *http.Request) {
    OnApiRequest(request)

    name := vestigo.Param(request, "name")

    for _, room := range service.GetRooms() {
        if room.Name == name {
            write(writer, &proto.ApiRoom{
                Room: room,
            })
            return
        }
    }

    writer.WriteHeader(http.StatusNotFound)
}

func handleStatus(writer http.ResponseWriter, request *http.Request) {
    OnApiRequest(request)

    registered := 0
    unregistered := 0
    clients := service.GetClients()
    rooms := service.GetRooms()

    for i := range clients {
        if clients[i].Name == "" {
            unregistered++
        } else {
            registered++
        }
    }

    write(writer, &proto.ApiStatus{
        NumGoroutines: int32(runtime.NumGoroutine()),
        NumUnregisteredClients: int32(unregistered),
        NumRegisteredClients: int32(registered),
        NumRooms: int32(len(rooms)),
    })
}
