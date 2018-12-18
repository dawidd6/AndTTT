package handlers

import (
	"server/proto"
	"server/service"
	"testing"
)

func newDispatch(request *proto.Request) *Dispatch {
	return &Dispatch{
		Request:       request,
		Client:        service.NewClient(nil),
		Response:      new(proto.Response),
		ResponseEnemy: new(proto.Response),
		Conn:          nil,
		ConnEnemy:     nil,
	}
}

func TestOnRegisterName(t *testing.T) {
	request := &proto.Request{
		Type: &proto.Request_RegisterName{
			RegisterName: &proto.RegisterNameRequest{
				Name: "some_name",
			},
		},
	}

	dispatch := newDispatch(request)
	OnRegisterName(dispatch)
	if dispatch.Response.Error != proto.Error_NONE {
		t.Fatal(dispatch.Response.Error)
	}

	dispatch = newDispatch(request)
	OnRegisterName(dispatch)
	if dispatch.Response.Error != proto.Error_CLIENT_NAME_TAKEN {
		t.Fatal(dispatch.Response.Error)
	}

	request.GetRegisterName().Name = "  "
	dispatch = newDispatch(request)
	OnRegisterName(dispatch)
	if dispatch.Response.Error != proto.Error_CLIENT_NAME_EMPTY {
		t.Fatal(dispatch.Response.Error)
	}
}

func TestOnCreateRoom(t *testing.T) {
	request := &proto.Request{
		Type: &proto.Request_CreateRoom{
			CreateRoom: &proto.CreateRoomRequest{
				Name: "some_name",
			},
		},
	}

	dispatch := newDispatch(request)
	OnCreateRoom(dispatch)
	if dispatch.Response.Error != proto.Error_NONE {
		t.Fatal(dispatch.Response.Error)
	}

	dispatch = newDispatch(request)
	OnCreateRoom(dispatch)
	if dispatch.Response.Error != proto.Error_ROOM_NAME_TAKEN {
		t.Fatal(dispatch.Response.Error)
	}
}
