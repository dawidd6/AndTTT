package service

import (
	"github.com/dawidd6/go-mmap"
	"github.com/golang/protobuf/ptypes"
	"net"
	"server/game"
	"server/proto"
	"time"
	"strings"
)

var (
	// rooms[string]*proto.Room
	rooms = mmap.New()
	// passwords[string]string
	passwords = mmap.New()
)

func CreateRoom(name, password string) proto.Error {
	if len(password) > 20 {
		return proto.Error_ROOM_PASSWORD_TOO_LONG
	}

	if len(name) > 20 {
		return proto.Error_ROOM_NAME_TOO_LONG
	}

	if strings.TrimSpace(name) == "" {
		return proto.Error_ROOM_NAME_EMPTY
	}

	if rooms.Has(name) {
		return proto.Error_ROOM_NAME_TAKEN
	}

	room := new(proto.Room)
	room.Name = name
	room.Since = ptypes.TimestampNow()
	if password != "" {
		room.IsProtected = true
		passwords.Set(name, password)
	}
	rooms.Set(name, room)
	return ResetRoomState(name)
}

func JoinRoom(client *proto.Client, name, password string) proto.Error {
	if client.Name == "" {
		return proto.Error_CLIENT_NAME_EMPTY
	}

	if client.Room != "" {
		return proto.Error_CLIENT_HAS_A_ROOM
	}

	value := rooms.Get(name)
	if value == nil {
		return proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	if room.IsProtected {
		if !passwords.Has(name) {
			return proto.Error_ROOM_PASSWORD_NOT_FOUND
		}

		if passwords.Get(name).(string) != password {
			return proto.Error_ROOM_PASSWORD_MISMATCH
		}
	}

	if room.FirstClient == nil {
		room.FirstClient = client
	} else if room.SecondClient == nil {
		room.SecondClient = client
	} else {
		return proto.Error_ROOM_FULL
	}

	client.Room = name

	return proto.Error_NONE
}

func LeaveRoom(client *proto.Client) proto.Error {
	if client.Room == "" {
		return proto.Error_CLIENT_HAS_NO_ROOM
	}

	value := rooms.Get(client.Room)
	if value == nil {
		return proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	if room.FirstClient == client {
		room.FirstClient = nil
	} else if room.SecondClient == client {
		room.SecondClient = nil
	} else {
		return proto.Error_CLIENT_NOT_FOUND_IN_ROOM
	}

	client.Room = ""
	client.Symbol = proto.Symbol_NO
	client.Turn = false
	client.Ready = false

	if room.FirstClient == nil && room.SecondClient == nil {
		return ResetRoomState(room.Name)
	}

	return proto.Error_NONE
}

func RemoveRoom(name string) proto.Error {
	if name == "" {
		return proto.Error_ROOM_NAME_EMPTY
	}

	if !rooms.Has(name) {
		return proto.Error_ROOM_NOT_FOUND
	}

	value := rooms.Get(name)
	if value == nil {
		return proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	if room.FirstClient != nil || room.SecondClient != nil {
		return proto.Error_ROOM_NOT_EMPTY
	}

	rooms.Remove(name)
	passwords.Remove(name)
	return proto.Error_NONE
}

func GetEnemy(client *proto.Client) (*proto.Client, *net.TCPConn, proto.Error) {
	if client.Room == "" {
		return nil, nil, proto.Error_CLIENT_HAS_NO_ROOM
	}

	value := rooms.Get(client.Room)
	if value == nil {
		return nil, nil, proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	if room.FirstClient != client && room.FirstClient != nil {
		conn := clients.Get(room.FirstClient)
		if conn != nil {
			return room.FirstClient, conn.(*net.TCPConn), proto.Error_NONE
		}
		return room.FirstClient, nil, proto.Error_NONE
	}

	if room.SecondClient != client && room.SecondClient != nil {
		conn := clients.Get(room.SecondClient)
		if conn != nil {
			return room.SecondClient, conn.(*net.TCPConn), proto.Error_NONE
		}
		return room.SecondClient, nil, proto.Error_NONE
	}

	if room.FirstClient != nil && room.SecondClient != nil {
		return nil, nil, proto.Error_ENEMY_NOT_FOUND_BUT_SHOULD_BE
	}

	return nil, nil, proto.Error_ENEMY_NOT_FOUND
}

func GetRooms() []*proto.Room {
	data := make([]*proto.Room, 0)

	rooms.Iterate(func(key interface{}, value interface{}) {
		data = append(data, value.(*proto.Room))
	})

	return data
}

func ResetRoomState(name string) proto.Error {
	value := rooms.Get(name)
	if value == nil {
		return proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	state := [9]proto.Symbol{}
	room.State = state[:]

	return proto.Error_NONE
}

func UpdateRoomState(client *proto.Client, position int32) proto.Error {
	value := rooms.Get(client.Room)
	if value == nil {
		return proto.Error_ROOM_NOT_FOUND
	}
	room := value.(*proto.Room)

	if room.FirstClient == nil || room.SecondClient == nil {
		return proto.Error_ENEMY_NOT_FOUND
	}

	enemy, _, err := GetEnemy(client)
	if err != proto.Error_NONE {
		return err
	}

	if client.Symbol == proto.Symbol_NO {
		return proto.Error_CLIENT_HAS_NO_SYMBOL
	}

	if !client.Turn {
		return proto.Error_CLIENT_HAS_NO_TURN
	}

	if position < 0 || position > 8 {
		return proto.Error_POSITION_OUT_OF_BOUND
	}

	if room.State[position] != proto.Symbol_NO {
		return proto.Error_POSITION_ALREADY_OCCUPIED
	}

	if game.IsWin(room.State) {
		return proto.Error_THERE_IS_A_WINNER
	}

	room.State[position] = client.Symbol

	client.Turn = false
	enemy.Turn = true

	return proto.Error_NONE
}

func CleanRooms(interval time.Duration) ([]string, proto.Error) {
	names := make([]string, 0)
	err := proto.Error_NONE

	if rooms.Count() == 0 {
		return names, err
	}

	for _, value := range rooms.Items() {
		room := value.(*proto.Room)
		since := room.Since.Seconds
		now := ptypes.TimestampNow().Seconds

		if room.FirstClient != nil || room.SecondClient != nil {
			continue
		}

		if since+int64(interval.Seconds()) > now {
			continue
		}

		if err = RemoveRoom(room.Name); err == proto.Error_NONE {
			names = append(names, room.Name)
		}
	}

	return names, err
}
