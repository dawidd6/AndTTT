package msg

import (
    "bytes"
    pb "github.com/golang/protobuf/proto"
    "net"
    "server/proto"
    "sync"
    "testing"
)

const address = "localhost:43535"

var (
    request = &proto.Request{
        Type: &proto.Request_CreateRoom{
            CreateRoom: &proto.CreateRoomRequest{
                Name: "some name",
                Password: "some_password123",
            },
        },
    }
    response = &proto.Response{
        Type: &proto.Response_StarterPack{
            StarterPack: &proto.StarterPackResponse{
                MySymbol: proto.Symbol_CIRCLE,
                EnemyName: "enemy",
                MyTurn: true,
            },
        },
    }
    wg = new(sync.WaitGroup)
)

func Test(t *testing.T) {
    resolvedListen, err := net.ResolveTCPAddr("tcp", address)
    if err != nil {
        t.Fatal(err)
    }

    listener, err := net.ListenTCP("tcp", resolvedListen)
    if err != nil {
        t.Fatal(err)
    }

    wg.Add(1)
    go func() {
        defer wg.Done()

        conn, err := listener.AcceptTCP()
        if err != nil {
            t.Fatal(err)
        }

        receivedRequest, err := ReceiveRequest(conn)
        if err != nil {
            t.Fatal(err)
        }

        receivedResponse, err := ReceiveResponse(conn)
        if err != nil {
            t.Fatal(err)
        }

        bytesRequest, err := pb.Marshal(request)
        if err != nil {
            t.Fatal(err)
        }

        bytesResponse, err := pb.Marshal(response)
        if err != nil {
            t.Fatal(err)
        }

        bytesReceivedRequest, err := pb.Marshal(receivedRequest)
        if err != nil {
            t.Fatal(err)
        }

        bytesReceivedResponse, err := pb.Marshal(receivedResponse)
        if err != nil {
            t.Fatal(err)
        }

        if ! bytes.Equal(bytesRequest, bytesReceivedRequest) {
            t.Fatal("requests mismatch")
        }

        if ! bytes.Equal(bytesResponse, bytesReceivedResponse) {
            t.Fatal("responses mismatch")
        }
    }()

    resolvedDial, err := net.ResolveTCPAddr("tcp", address)
    if err != nil {
        t.Fatal(err)
    }

    conn, err := net.DialTCP("tcp", nil, resolvedDial)
    if err != nil {
        t.Fatal(err)
    }

    if err := SendRequest(conn, request); err != nil {
        t.Fatal(err)
    }

    if err := SendResponse(conn, response); err != nil {
        t.Fatal(err)
    }

    wg.Wait()
}
