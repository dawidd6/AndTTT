package handlers

import (
	"net"
	"server/game"
	"server/proto"
	"server/service"
)

type Dispatch struct {
	Request       *proto.Request
	Client        *proto.Client
	Conn          *net.TCPConn
	ConnEnemy     *net.TCPConn
	Response      *proto.Response
	ResponseEnemy *proto.Response
}

func OnDefault(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_Unrecognized{
		Unrecognized: &proto.UnrecognizedResponse{},
	}
}

func OnRegisterName(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_RegisterName{
		RegisterName: &proto.RegisterNameResponse{
			Name: dispatch.Request.GetRegisterName().Name,
		},
	}

	err := service.RegisterClient(dispatch.Client, dispatch.Request.GetRegisterName().Name)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
	}
}

func OnCreateRoom(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_CreateRoom{
		CreateRoom: &proto.CreateRoomResponse{
			Name: dispatch.Request.GetCreateRoom().Name,
		},
	}

	err := service.CreateRoom(dispatch.Request.GetCreateRoom().Name, dispatch.Request.GetCreateRoom().Password)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
	}
}

func OnJoinRoom(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_JoinRoom{
		JoinRoom: &proto.JoinRoomResponse{
			Name: dispatch.Request.GetJoinRoom().Name,
		},
	}

	err := service.JoinRoom(dispatch.Client, dispatch.Request.GetJoinRoom().Name, dispatch.Request.GetJoinRoom().Password)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
	}
}

func OnRestart(dispatch *Dispatch) {
	dispatch.Conn = nil

	dispatch.ResponseEnemy.Type = &proto.Response_Restart{
		Restart: &proto.RestartResponse{
			Restart: dispatch.Request.GetRestart().Restart},
	}

	_, connEnemy, err := service.GetEnemy(dispatch.Client)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
		return
	}

	dispatch.ConnEnemy = connEnemy
}

func OnStarterPack(dispatch *Dispatch) {
	dispatch.Client.Ready = true

	enemy, connEnemy, err := service.GetEnemy(dispatch.Client)
	if err != proto.Error_NONE {
		dispatch.Conn = nil
		return
	}

	if dispatch.Client.Ready && enemy.Ready {
		dispatch.Client.Ready = false
		enemy.Ready = false

		dispatch.Client.Symbol = game.GetRandomSymbol()
		dispatch.Client.Turn = game.GetRandomTurn()

		enemy.Symbol = game.GetOppositeSymbol(dispatch.Client.Symbol)
		enemy.Turn = !dispatch.Client.Turn

		dispatch.ConnEnemy = connEnemy

		dispatch.Response.Type = &proto.Response_StarterPack{
			StarterPack: &proto.StarterPackResponse{
				MySymbol:    dispatch.Client.Symbol,
				MyTurn:      dispatch.Client.Turn,
				EnemySymbol: enemy.Symbol,
				EnemyTurn:   enemy.Turn,
				EnemyName:   enemy.Name,
			},
		}

		dispatch.ResponseEnemy.Type = &proto.Response_StarterPack{
			StarterPack: &proto.StarterPackResponse{
				MySymbol:    enemy.Symbol,
				MyTurn:      enemy.Turn,
				EnemySymbol: dispatch.Client.Symbol,
				EnemyTurn:   dispatch.Client.Turn,
				EnemyName:   dispatch.Client.Name,
			},
		}

		err = service.ResetRoomState(dispatch.Client.Room)
		if err != proto.Error_NONE {
			dispatch.Response.Error = err
			dispatch.ResponseEnemy.Error = err
		}
	}
}

func OnLeaveRoom(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_LeaveRoom{
		LeaveRoom: &proto.LeaveRoomResponse{},
	}

	dispatch.ResponseEnemy.Type = &proto.Response_EnemyLeft{
		EnemyLeft: &proto.EnemyLeftResponse{},
	}

	_, connEnemy, err := service.GetEnemy(dispatch.Client)
	if err == proto.Error_NONE {
		dispatch.ConnEnemy = connEnemy
	}

	err = service.LeaveRoom(dispatch.Client)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
		dispatch.ConnEnemy = nil
	}
}

func OnGetRooms(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_GetRooms{
		GetRooms: &proto.GetRoomsResponse{
			Rooms: service.GetRooms(),
		},
	}
}

func OnMove(dispatch *Dispatch) {
	dispatch.Response.Type = &proto.Response_Move{
		Move: &proto.MoveResponse{
			Position: dispatch.Request.GetMove().Position,
		},
	}

	dispatch.ResponseEnemy.Type = &proto.Response_EnemyMoved{
		EnemyMoved: &proto.EnemyMovedResponse{
			Position: dispatch.Request.GetMove().Position,
		},
	}

	_, connEnemy, err := service.GetEnemy(dispatch.Client)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
		return
	}

	err = service.UpdateRoomState(dispatch.Client, dispatch.Request.GetMove().Position)
	if err != proto.Error_NONE {
		dispatch.Response.Error = err
		return
	}

	dispatch.ConnEnemy = connEnemy
}
