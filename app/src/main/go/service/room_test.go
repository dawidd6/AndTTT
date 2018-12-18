package service

import (
	"fmt"
	"math/rand"
	"server/proto"
	"strings"
	"testing"
	"time"
)

var roomsTest []*proto.Room

func TestCreateRoom(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		err := CreateRoom(room, "")
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = CreateRoom(fmt.Sprintf("room_to_clean%d", i), "")
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = CreateRoom(room, "")
		if err != proto.Error_ROOM_NAME_TAKEN {
			t.Fatal(err)
		}

		if rooms.Get(room).(*proto.Room).IsProtected {
			t.Fatal("should not be protected")
		}

		err = CreateRoom("", "")
		if err != proto.Error_ROOM_NAME_EMPTY {
			t.Fatal(err)
		}

	})

	err := CreateRoom("room_with_password", "pass123")
	if err != proto.Error_NONE {
		t.Fatal(err)
	}

	if !rooms.Get("room_with_password").(*proto.Room).IsProtected {
		t.Fatal("should be protected")
	}
}

func TestGetRooms(t *testing.T) {
	roomsTest = GetRooms()

	if rooms.Count() != len(roomsTest) {
		t.Fatal(len(roomsTest))
	}
}

func TestResetRoomState(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		err := ResetRoomState(room)
		if err != proto.Error_NONE {
			t.Fatal(err)
		}
	})
}

func TestJoinRoom(t *testing.T) {
	TestNewClient(t)
	TestGetClients(t)
	TestRegisterClient(t)

	doInLoop(func(i int, name, room string) {
		err := JoinRoom(clientsTest[i], room, "")
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = JoinRoom(clientsTest[i], room, "")
		if err != proto.Error_CLIENT_HAS_A_ROOM {
			t.Fatal(err)
		}

		err = JoinRoom(new(proto.Client), "", "")
		if err != proto.Error_CLIENT_NAME_EMPTY {
			t.Fatal(err)
		}

		client := new(proto.Client)
		client.Name = "second_client"
		err = JoinRoom(client, room, "")
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		client = new(proto.Client)
		client.Name = "room_full"
		err = JoinRoom(client, room, "")
		if err != proto.Error_ROOM_FULL {
			t.Fatal(err)
		}

		err = JoinRoom(client, "", "")
		if err != proto.Error_ROOM_NOT_FOUND {
			t.Fatal(err)
		}

	})

	client := new(proto.Client)
	client.Name = "password_client"
	err := JoinRoom(client, "room_with_password", "")
	if err != proto.Error_ROOM_PASSWORD_MISMATCH {
		t.Fatal(err)
	}

	err = JoinRoom(client, "room_with_password", "pass")
	if err != proto.Error_ROOM_PASSWORD_MISMATCH {
		t.Fatal(err)
	}

	err = JoinRoom(client, "room_with_password", "pass123")
	if err != proto.Error_NONE {
		t.Fatal(err)
	}
}

func TestUpdateRoomState(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		clientsTest[i].Symbol = proto.Symbol_CROSS
		clientsTest[i].Turn = false
		err := UpdateRoomState(clientsTest[i], 0)
		if err != proto.Error_CLIENT_HAS_NO_TURN {
			t.Fatal(err)
		}

		clientsTest[i].Symbol = proto.Symbol_NO
		clientsTest[i].Turn = true
		err = UpdateRoomState(clientsTest[i], 0)
		if err != proto.Error_CLIENT_HAS_NO_SYMBOL {
			t.Fatal(err)
		}

		pos := rand.Int31n(9)
		clientsTest[i].Symbol = proto.Symbol_CROSS
		err = UpdateRoomState(clientsTest[i], pos)
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		clientsTest[i].Turn = true
		err = UpdateRoomState(clientsTest[i], pos)
		if err != proto.Error_POSITION_ALREADY_OCCUPIED {
			t.Fatal(err)
		}
	})
}

func TestGetEnemy(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		enemy, enemyConn, err := GetEnemy(clientsTest[i])
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		if enemy.Name != "second_client" {
			t.Fatal("not second_client")
		}

		if enemy.Room != clientsTest[i].Room {
			t.Fatal("rooms mismatch")
		}

		if enemyConn != nil {
			t.Fatal("not nil enemyConn")
		}
	})
}

func TestLeaveRoom(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		err := LeaveRoom(clientsTest[i])
		if err != proto.Error_NONE {
			t.Fatal(err)
		}

		err = LeaveRoom(clientsTest[i])
		if err != proto.Error_CLIENT_HAS_NO_ROOM {
			t.Fatal(err)
		}

		r := rooms.Get(room)
		rr := r.(*proto.Room)
		if rr.FirstClient != nil {
			t.Fatal("not nil first_client")
		}
		if rr.SecondClient == nil {
			t.Fatal("nil second_client")
		}
	})
}

func TestRemoveRoom(t *testing.T) {
	doInLoop(func(i int, name, room string) {
		err := RemoveRoom(room)
		if err != proto.Error_ROOM_NOT_EMPTY {
			t.Fatal(err)
		}

		err = RemoveRoom("")
		if err != proto.Error_ROOM_NAME_EMPTY {
			t.Fatal(err)
		}

		err = RemoveRoom("rr")
		if err != proto.Error_ROOM_NOT_FOUND {
			t.Fatal(err)
		}
	})

	err := CreateRoom("room_to_remove", "")
	if err != proto.Error_NONE {
		t.Fatal(err)
	}

	err = RemoveRoom("room_to_remove")
	if err != proto.Error_NONE {
		t.Fatal(err)
	}
}

func TestCleanRooms(t *testing.T) {
	names, err := CleanRooms(time.Minute)
	if err != proto.Error_NONE {
		t.Fatal(err)
	}

	if len(names) != 0 {
		t.Fatal(len(names))
	}

	<-time.After(time.Second)

	names, err = CleanRooms(time.Second)
	if err != proto.Error_NONE {
		t.Fatal(err)
	}

	if len(names) != count {
		t.Fatal(len(names))
	}

	for i := range names {
		if !strings.Contains(names[i], "room_to_clean") {
			t.Fatal("does not contain room_to_clean")
		}
	}
}
