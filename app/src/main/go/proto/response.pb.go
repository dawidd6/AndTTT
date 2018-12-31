// Code generated by protoc-gen-go. DO NOT EDIT.
// source: response.proto

package proto

import proto "github.com/golang/protobuf/proto"
import fmt "fmt"
import math "math"

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion2 // please upgrade the proto package

type Response struct {
	Error Error `protobuf:"varint,1000,opt,name=error,proto3,enum=proto.Error" json:"error,omitempty"`
	// Types that are valid to be assigned to Type:
	//	*Response_Unrecognized
	//	*Response_RegisterName
	//	*Response_CreateRoom
	//	*Response_JoinRoom
	//	*Response_LeaveRoom
	//	*Response_GetRooms
	//	*Response_Move
	//	*Response_Restart
	//	*Response_StarterPack
	//	*Response_EnemyDisconnected
	//	*Response_EnemyLeft
	//	*Response_EnemyMoved
	Type                 isResponse_Type `protobuf_oneof:"Type"`
	XXX_NoUnkeyedLiteral struct{}        `json:"-"`
	XXX_unrecognized     []byte          `json:"-"`
	XXX_sizecache        int32           `json:"-"`
}

func (m *Response) Reset()         { *m = Response{} }
func (m *Response) String() string { return proto.CompactTextString(m) }
func (*Response) ProtoMessage()    {}
func (*Response) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{0}
}
func (m *Response) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_Response.Unmarshal(m, b)
}
func (m *Response) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_Response.Marshal(b, m, deterministic)
}
func (dst *Response) XXX_Merge(src proto.Message) {
	xxx_messageInfo_Response.Merge(dst, src)
}
func (m *Response) XXX_Size() int {
	return xxx_messageInfo_Response.Size(m)
}
func (m *Response) XXX_DiscardUnknown() {
	xxx_messageInfo_Response.DiscardUnknown(m)
}

var xxx_messageInfo_Response proto.InternalMessageInfo

func (m *Response) GetError() Error {
	if m != nil {
		return m.Error
	}
	return Error_NONE
}

type isResponse_Type interface {
	isResponse_Type()
}

type Response_Unrecognized struct {
	Unrecognized *UnrecognizedResponse `protobuf:"bytes,9999,opt,name=unrecognized,proto3,oneof"`
}

type Response_RegisterName struct {
	RegisterName *RegisterNameResponse `protobuf:"bytes,1,opt,name=register_name,json=registerName,proto3,oneof"`
}

type Response_CreateRoom struct {
	CreateRoom *CreateRoomResponse `protobuf:"bytes,2,opt,name=create_room,json=createRoom,proto3,oneof"`
}

type Response_JoinRoom struct {
	JoinRoom *JoinRoomResponse `protobuf:"bytes,3,opt,name=join_room,json=joinRoom,proto3,oneof"`
}

type Response_LeaveRoom struct {
	LeaveRoom *LeaveRoomResponse `protobuf:"bytes,4,opt,name=leave_room,json=leaveRoom,proto3,oneof"`
}

type Response_GetRooms struct {
	GetRooms *GetRoomsResponse `protobuf:"bytes,5,opt,name=get_rooms,json=getRooms,proto3,oneof"`
}

type Response_Move struct {
	Move *MoveResponse `protobuf:"bytes,6,opt,name=move,proto3,oneof"`
}

type Response_Restart struct {
	Restart *RestartResponse `protobuf:"bytes,7,opt,name=restart,proto3,oneof"`
}

type Response_StarterPack struct {
	StarterPack *StarterPackResponse `protobuf:"bytes,8,opt,name=starter_pack,json=starterPack,proto3,oneof"`
}

type Response_EnemyDisconnected struct {
	EnemyDisconnected *EnemyDisconnectedResponse `protobuf:"bytes,101,opt,name=enemy_disconnected,json=enemyDisconnected,proto3,oneof"`
}

type Response_EnemyLeft struct {
	EnemyLeft *EnemyLeftResponse `protobuf:"bytes,102,opt,name=enemy_left,json=enemyLeft,proto3,oneof"`
}

type Response_EnemyMoved struct {
	EnemyMoved *EnemyMovedResponse `protobuf:"bytes,103,opt,name=enemy_moved,json=enemyMoved,proto3,oneof"`
}

func (*Response_Unrecognized) isResponse_Type() {}

func (*Response_RegisterName) isResponse_Type() {}

func (*Response_CreateRoom) isResponse_Type() {}

func (*Response_JoinRoom) isResponse_Type() {}

func (*Response_LeaveRoom) isResponse_Type() {}

func (*Response_GetRooms) isResponse_Type() {}

func (*Response_Move) isResponse_Type() {}

func (*Response_Restart) isResponse_Type() {}

func (*Response_StarterPack) isResponse_Type() {}

func (*Response_EnemyDisconnected) isResponse_Type() {}

func (*Response_EnemyLeft) isResponse_Type() {}

func (*Response_EnemyMoved) isResponse_Type() {}

func (m *Response) GetType() isResponse_Type {
	if m != nil {
		return m.Type
	}
	return nil
}

func (m *Response) GetUnrecognized() *UnrecognizedResponse {
	if x, ok := m.GetType().(*Response_Unrecognized); ok {
		return x.Unrecognized
	}
	return nil
}

func (m *Response) GetRegisterName() *RegisterNameResponse {
	if x, ok := m.GetType().(*Response_RegisterName); ok {
		return x.RegisterName
	}
	return nil
}

func (m *Response) GetCreateRoom() *CreateRoomResponse {
	if x, ok := m.GetType().(*Response_CreateRoom); ok {
		return x.CreateRoom
	}
	return nil
}

func (m *Response) GetJoinRoom() *JoinRoomResponse {
	if x, ok := m.GetType().(*Response_JoinRoom); ok {
		return x.JoinRoom
	}
	return nil
}

func (m *Response) GetLeaveRoom() *LeaveRoomResponse {
	if x, ok := m.GetType().(*Response_LeaveRoom); ok {
		return x.LeaveRoom
	}
	return nil
}

func (m *Response) GetGetRooms() *GetRoomsResponse {
	if x, ok := m.GetType().(*Response_GetRooms); ok {
		return x.GetRooms
	}
	return nil
}

func (m *Response) GetMove() *MoveResponse {
	if x, ok := m.GetType().(*Response_Move); ok {
		return x.Move
	}
	return nil
}

func (m *Response) GetRestart() *RestartResponse {
	if x, ok := m.GetType().(*Response_Restart); ok {
		return x.Restart
	}
	return nil
}

func (m *Response) GetStarterPack() *StarterPackResponse {
	if x, ok := m.GetType().(*Response_StarterPack); ok {
		return x.StarterPack
	}
	return nil
}

func (m *Response) GetEnemyDisconnected() *EnemyDisconnectedResponse {
	if x, ok := m.GetType().(*Response_EnemyDisconnected); ok {
		return x.EnemyDisconnected
	}
	return nil
}

func (m *Response) GetEnemyLeft() *EnemyLeftResponse {
	if x, ok := m.GetType().(*Response_EnemyLeft); ok {
		return x.EnemyLeft
	}
	return nil
}

func (m *Response) GetEnemyMoved() *EnemyMovedResponse {
	if x, ok := m.GetType().(*Response_EnemyMoved); ok {
		return x.EnemyMoved
	}
	return nil
}

// XXX_OneofFuncs is for the internal use of the proto package.
func (*Response) XXX_OneofFuncs() (func(msg proto.Message, b *proto.Buffer) error, func(msg proto.Message, tag, wire int, b *proto.Buffer) (bool, error), func(msg proto.Message) (n int), []interface{}) {
	return _Response_OneofMarshaler, _Response_OneofUnmarshaler, _Response_OneofSizer, []interface{}{
		(*Response_Unrecognized)(nil),
		(*Response_RegisterName)(nil),
		(*Response_CreateRoom)(nil),
		(*Response_JoinRoom)(nil),
		(*Response_LeaveRoom)(nil),
		(*Response_GetRooms)(nil),
		(*Response_Move)(nil),
		(*Response_Restart)(nil),
		(*Response_StarterPack)(nil),
		(*Response_EnemyDisconnected)(nil),
		(*Response_EnemyLeft)(nil),
		(*Response_EnemyMoved)(nil),
	}
}

func _Response_OneofMarshaler(msg proto.Message, b *proto.Buffer) error {
	m := msg.(*Response)
	// Type
	switch x := m.Type.(type) {
	case *Response_Unrecognized:
		b.EncodeVarint(9999<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.Unrecognized); err != nil {
			return err
		}
	case *Response_RegisterName:
		b.EncodeVarint(1<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.RegisterName); err != nil {
			return err
		}
	case *Response_CreateRoom:
		b.EncodeVarint(2<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.CreateRoom); err != nil {
			return err
		}
	case *Response_JoinRoom:
		b.EncodeVarint(3<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.JoinRoom); err != nil {
			return err
		}
	case *Response_LeaveRoom:
		b.EncodeVarint(4<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.LeaveRoom); err != nil {
			return err
		}
	case *Response_GetRooms:
		b.EncodeVarint(5<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.GetRooms); err != nil {
			return err
		}
	case *Response_Move:
		b.EncodeVarint(6<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.Move); err != nil {
			return err
		}
	case *Response_Restart:
		b.EncodeVarint(7<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.Restart); err != nil {
			return err
		}
	case *Response_StarterPack:
		b.EncodeVarint(8<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.StarterPack); err != nil {
			return err
		}
	case *Response_EnemyDisconnected:
		b.EncodeVarint(101<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.EnemyDisconnected); err != nil {
			return err
		}
	case *Response_EnemyLeft:
		b.EncodeVarint(102<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.EnemyLeft); err != nil {
			return err
		}
	case *Response_EnemyMoved:
		b.EncodeVarint(103<<3 | proto.WireBytes)
		if err := b.EncodeMessage(x.EnemyMoved); err != nil {
			return err
		}
	case nil:
	default:
		return fmt.Errorf("Response.Type has unexpected type %T", x)
	}
	return nil
}

func _Response_OneofUnmarshaler(msg proto.Message, tag, wire int, b *proto.Buffer) (bool, error) {
	m := msg.(*Response)
	switch tag {
	case 9999: // Type.unrecognized
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(UnrecognizedResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_Unrecognized{msg}
		return true, err
	case 1: // Type.register_name
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(RegisterNameResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_RegisterName{msg}
		return true, err
	case 2: // Type.create_room
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(CreateRoomResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_CreateRoom{msg}
		return true, err
	case 3: // Type.join_room
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(JoinRoomResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_JoinRoom{msg}
		return true, err
	case 4: // Type.leave_room
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(LeaveRoomResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_LeaveRoom{msg}
		return true, err
	case 5: // Type.get_rooms
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(GetRoomsResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_GetRooms{msg}
		return true, err
	case 6: // Type.move
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(MoveResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_Move{msg}
		return true, err
	case 7: // Type.restart
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(RestartResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_Restart{msg}
		return true, err
	case 8: // Type.starter_pack
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(StarterPackResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_StarterPack{msg}
		return true, err
	case 101: // Type.enemy_disconnected
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(EnemyDisconnectedResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_EnemyDisconnected{msg}
		return true, err
	case 102: // Type.enemy_left
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(EnemyLeftResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_EnemyLeft{msg}
		return true, err
	case 103: // Type.enemy_moved
		if wire != proto.WireBytes {
			return true, proto.ErrInternalBadWireType
		}
		msg := new(EnemyMovedResponse)
		err := b.DecodeMessage(msg)
		m.Type = &Response_EnemyMoved{msg}
		return true, err
	default:
		return false, nil
	}
}

func _Response_OneofSizer(msg proto.Message) (n int) {
	m := msg.(*Response)
	// Type
	switch x := m.Type.(type) {
	case *Response_Unrecognized:
		s := proto.Size(x.Unrecognized)
		n += 3 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_RegisterName:
		s := proto.Size(x.RegisterName)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_CreateRoom:
		s := proto.Size(x.CreateRoom)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_JoinRoom:
		s := proto.Size(x.JoinRoom)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_LeaveRoom:
		s := proto.Size(x.LeaveRoom)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_GetRooms:
		s := proto.Size(x.GetRooms)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_Move:
		s := proto.Size(x.Move)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_Restart:
		s := proto.Size(x.Restart)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_StarterPack:
		s := proto.Size(x.StarterPack)
		n += 1 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_EnemyDisconnected:
		s := proto.Size(x.EnemyDisconnected)
		n += 2 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_EnemyLeft:
		s := proto.Size(x.EnemyLeft)
		n += 2 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case *Response_EnemyMoved:
		s := proto.Size(x.EnemyMoved)
		n += 2 // tag and wire
		n += proto.SizeVarint(uint64(s))
		n += s
	case nil:
	default:
		panic(fmt.Sprintf("proto: unexpected type %T in oneof", x))
	}
	return n
}

type UnrecognizedResponse struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *UnrecognizedResponse) Reset()         { *m = UnrecognizedResponse{} }
func (m *UnrecognizedResponse) String() string { return proto.CompactTextString(m) }
func (*UnrecognizedResponse) ProtoMessage()    {}
func (*UnrecognizedResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{1}
}
func (m *UnrecognizedResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_UnrecognizedResponse.Unmarshal(m, b)
}
func (m *UnrecognizedResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_UnrecognizedResponse.Marshal(b, m, deterministic)
}
func (dst *UnrecognizedResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_UnrecognizedResponse.Merge(dst, src)
}
func (m *UnrecognizedResponse) XXX_Size() int {
	return xxx_messageInfo_UnrecognizedResponse.Size(m)
}
func (m *UnrecognizedResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_UnrecognizedResponse.DiscardUnknown(m)
}

var xxx_messageInfo_UnrecognizedResponse proto.InternalMessageInfo

type RegisterNameResponse struct {
	Name                 string   `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *RegisterNameResponse) Reset()         { *m = RegisterNameResponse{} }
func (m *RegisterNameResponse) String() string { return proto.CompactTextString(m) }
func (*RegisterNameResponse) ProtoMessage()    {}
func (*RegisterNameResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{2}
}
func (m *RegisterNameResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_RegisterNameResponse.Unmarshal(m, b)
}
func (m *RegisterNameResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_RegisterNameResponse.Marshal(b, m, deterministic)
}
func (dst *RegisterNameResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_RegisterNameResponse.Merge(dst, src)
}
func (m *RegisterNameResponse) XXX_Size() int {
	return xxx_messageInfo_RegisterNameResponse.Size(m)
}
func (m *RegisterNameResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_RegisterNameResponse.DiscardUnknown(m)
}

var xxx_messageInfo_RegisterNameResponse proto.InternalMessageInfo

func (m *RegisterNameResponse) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

type CreateRoomResponse struct {
	Name                 string   `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *CreateRoomResponse) Reset()         { *m = CreateRoomResponse{} }
func (m *CreateRoomResponse) String() string { return proto.CompactTextString(m) }
func (*CreateRoomResponse) ProtoMessage()    {}
func (*CreateRoomResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{3}
}
func (m *CreateRoomResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_CreateRoomResponse.Unmarshal(m, b)
}
func (m *CreateRoomResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_CreateRoomResponse.Marshal(b, m, deterministic)
}
func (dst *CreateRoomResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_CreateRoomResponse.Merge(dst, src)
}
func (m *CreateRoomResponse) XXX_Size() int {
	return xxx_messageInfo_CreateRoomResponse.Size(m)
}
func (m *CreateRoomResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_CreateRoomResponse.DiscardUnknown(m)
}

var xxx_messageInfo_CreateRoomResponse proto.InternalMessageInfo

func (m *CreateRoomResponse) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

type JoinRoomResponse struct {
	Name                 string   `protobuf:"bytes,1,opt,name=name,proto3" json:"name,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *JoinRoomResponse) Reset()         { *m = JoinRoomResponse{} }
func (m *JoinRoomResponse) String() string { return proto.CompactTextString(m) }
func (*JoinRoomResponse) ProtoMessage()    {}
func (*JoinRoomResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{4}
}
func (m *JoinRoomResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_JoinRoomResponse.Unmarshal(m, b)
}
func (m *JoinRoomResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_JoinRoomResponse.Marshal(b, m, deterministic)
}
func (dst *JoinRoomResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_JoinRoomResponse.Merge(dst, src)
}
func (m *JoinRoomResponse) XXX_Size() int {
	return xxx_messageInfo_JoinRoomResponse.Size(m)
}
func (m *JoinRoomResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_JoinRoomResponse.DiscardUnknown(m)
}

var xxx_messageInfo_JoinRoomResponse proto.InternalMessageInfo

func (m *JoinRoomResponse) GetName() string {
	if m != nil {
		return m.Name
	}
	return ""
}

type LeaveRoomResponse struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *LeaveRoomResponse) Reset()         { *m = LeaveRoomResponse{} }
func (m *LeaveRoomResponse) String() string { return proto.CompactTextString(m) }
func (*LeaveRoomResponse) ProtoMessage()    {}
func (*LeaveRoomResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{5}
}
func (m *LeaveRoomResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_LeaveRoomResponse.Unmarshal(m, b)
}
func (m *LeaveRoomResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_LeaveRoomResponse.Marshal(b, m, deterministic)
}
func (dst *LeaveRoomResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_LeaveRoomResponse.Merge(dst, src)
}
func (m *LeaveRoomResponse) XXX_Size() int {
	return xxx_messageInfo_LeaveRoomResponse.Size(m)
}
func (m *LeaveRoomResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_LeaveRoomResponse.DiscardUnknown(m)
}

var xxx_messageInfo_LeaveRoomResponse proto.InternalMessageInfo

type GetRoomsResponse struct {
	Rooms                []*Room  `protobuf:"bytes,1,rep,name=rooms,proto3" json:"rooms,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *GetRoomsResponse) Reset()         { *m = GetRoomsResponse{} }
func (m *GetRoomsResponse) String() string { return proto.CompactTextString(m) }
func (*GetRoomsResponse) ProtoMessage()    {}
func (*GetRoomsResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{6}
}
func (m *GetRoomsResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_GetRoomsResponse.Unmarshal(m, b)
}
func (m *GetRoomsResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_GetRoomsResponse.Marshal(b, m, deterministic)
}
func (dst *GetRoomsResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_GetRoomsResponse.Merge(dst, src)
}
func (m *GetRoomsResponse) XXX_Size() int {
	return xxx_messageInfo_GetRoomsResponse.Size(m)
}
func (m *GetRoomsResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_GetRoomsResponse.DiscardUnknown(m)
}

var xxx_messageInfo_GetRoomsResponse proto.InternalMessageInfo

func (m *GetRoomsResponse) GetRooms() []*Room {
	if m != nil {
		return m.Rooms
	}
	return nil
}

type MoveResponse struct {
	Position             int32    `protobuf:"varint,1,opt,name=position,proto3" json:"position,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *MoveResponse) Reset()         { *m = MoveResponse{} }
func (m *MoveResponse) String() string { return proto.CompactTextString(m) }
func (*MoveResponse) ProtoMessage()    {}
func (*MoveResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{7}
}
func (m *MoveResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_MoveResponse.Unmarshal(m, b)
}
func (m *MoveResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_MoveResponse.Marshal(b, m, deterministic)
}
func (dst *MoveResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_MoveResponse.Merge(dst, src)
}
func (m *MoveResponse) XXX_Size() int {
	return xxx_messageInfo_MoveResponse.Size(m)
}
func (m *MoveResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_MoveResponse.DiscardUnknown(m)
}

var xxx_messageInfo_MoveResponse proto.InternalMessageInfo

func (m *MoveResponse) GetPosition() int32 {
	if m != nil {
		return m.Position
	}
	return 0
}

type RestartResponse struct {
	Restart              Restart  `protobuf:"varint,1,opt,name=restart,proto3,enum=proto.Restart" json:"restart,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *RestartResponse) Reset()         { *m = RestartResponse{} }
func (m *RestartResponse) String() string { return proto.CompactTextString(m) }
func (*RestartResponse) ProtoMessage()    {}
func (*RestartResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{8}
}
func (m *RestartResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_RestartResponse.Unmarshal(m, b)
}
func (m *RestartResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_RestartResponse.Marshal(b, m, deterministic)
}
func (dst *RestartResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_RestartResponse.Merge(dst, src)
}
func (m *RestartResponse) XXX_Size() int {
	return xxx_messageInfo_RestartResponse.Size(m)
}
func (m *RestartResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_RestartResponse.DiscardUnknown(m)
}

var xxx_messageInfo_RestartResponse proto.InternalMessageInfo

func (m *RestartResponse) GetRestart() Restart {
	if m != nil {
		return m.Restart
	}
	return Restart_UNSPECIFIED
}

type StarterPackResponse struct {
	MySymbol             Symbol   `protobuf:"varint,1,opt,name=my_symbol,json=mySymbol,proto3,enum=proto.Symbol" json:"my_symbol,omitempty"`
	EnemySymbol          Symbol   `protobuf:"varint,2,opt,name=enemy_symbol,json=enemySymbol,proto3,enum=proto.Symbol" json:"enemy_symbol,omitempty"`
	MyTurn               bool     `protobuf:"varint,3,opt,name=my_turn,json=myTurn,proto3" json:"my_turn,omitempty"`
	EnemyTurn            bool     `protobuf:"varint,4,opt,name=enemy_turn,json=enemyTurn,proto3" json:"enemy_turn,omitempty"`
	EnemyName            string   `protobuf:"bytes,5,opt,name=enemy_name,json=enemyName,proto3" json:"enemy_name,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *StarterPackResponse) Reset()         { *m = StarterPackResponse{} }
func (m *StarterPackResponse) String() string { return proto.CompactTextString(m) }
func (*StarterPackResponse) ProtoMessage()    {}
func (*StarterPackResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{9}
}
func (m *StarterPackResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_StarterPackResponse.Unmarshal(m, b)
}
func (m *StarterPackResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_StarterPackResponse.Marshal(b, m, deterministic)
}
func (dst *StarterPackResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_StarterPackResponse.Merge(dst, src)
}
func (m *StarterPackResponse) XXX_Size() int {
	return xxx_messageInfo_StarterPackResponse.Size(m)
}
func (m *StarterPackResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_StarterPackResponse.DiscardUnknown(m)
}

var xxx_messageInfo_StarterPackResponse proto.InternalMessageInfo

func (m *StarterPackResponse) GetMySymbol() Symbol {
	if m != nil {
		return m.MySymbol
	}
	return Symbol_NO
}

func (m *StarterPackResponse) GetEnemySymbol() Symbol {
	if m != nil {
		return m.EnemySymbol
	}
	return Symbol_NO
}

func (m *StarterPackResponse) GetMyTurn() bool {
	if m != nil {
		return m.MyTurn
	}
	return false
}

func (m *StarterPackResponse) GetEnemyTurn() bool {
	if m != nil {
		return m.EnemyTurn
	}
	return false
}

func (m *StarterPackResponse) GetEnemyName() string {
	if m != nil {
		return m.EnemyName
	}
	return ""
}

type EnemyDisconnectedResponse struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *EnemyDisconnectedResponse) Reset()         { *m = EnemyDisconnectedResponse{} }
func (m *EnemyDisconnectedResponse) String() string { return proto.CompactTextString(m) }
func (*EnemyDisconnectedResponse) ProtoMessage()    {}
func (*EnemyDisconnectedResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{10}
}
func (m *EnemyDisconnectedResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_EnemyDisconnectedResponse.Unmarshal(m, b)
}
func (m *EnemyDisconnectedResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_EnemyDisconnectedResponse.Marshal(b, m, deterministic)
}
func (dst *EnemyDisconnectedResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_EnemyDisconnectedResponse.Merge(dst, src)
}
func (m *EnemyDisconnectedResponse) XXX_Size() int {
	return xxx_messageInfo_EnemyDisconnectedResponse.Size(m)
}
func (m *EnemyDisconnectedResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_EnemyDisconnectedResponse.DiscardUnknown(m)
}

var xxx_messageInfo_EnemyDisconnectedResponse proto.InternalMessageInfo

type EnemyLeftResponse struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *EnemyLeftResponse) Reset()         { *m = EnemyLeftResponse{} }
func (m *EnemyLeftResponse) String() string { return proto.CompactTextString(m) }
func (*EnemyLeftResponse) ProtoMessage()    {}
func (*EnemyLeftResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{11}
}
func (m *EnemyLeftResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_EnemyLeftResponse.Unmarshal(m, b)
}
func (m *EnemyLeftResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_EnemyLeftResponse.Marshal(b, m, deterministic)
}
func (dst *EnemyLeftResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_EnemyLeftResponse.Merge(dst, src)
}
func (m *EnemyLeftResponse) XXX_Size() int {
	return xxx_messageInfo_EnemyLeftResponse.Size(m)
}
func (m *EnemyLeftResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_EnemyLeftResponse.DiscardUnknown(m)
}

var xxx_messageInfo_EnemyLeftResponse proto.InternalMessageInfo

type EnemyMovedResponse struct {
	Position             int32    `protobuf:"varint,1,opt,name=position,proto3" json:"position,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *EnemyMovedResponse) Reset()         { *m = EnemyMovedResponse{} }
func (m *EnemyMovedResponse) String() string { return proto.CompactTextString(m) }
func (*EnemyMovedResponse) ProtoMessage()    {}
func (*EnemyMovedResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_response_e7fe0db36a319882, []int{12}
}
func (m *EnemyMovedResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_EnemyMovedResponse.Unmarshal(m, b)
}
func (m *EnemyMovedResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_EnemyMovedResponse.Marshal(b, m, deterministic)
}
func (dst *EnemyMovedResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_EnemyMovedResponse.Merge(dst, src)
}
func (m *EnemyMovedResponse) XXX_Size() int {
	return xxx_messageInfo_EnemyMovedResponse.Size(m)
}
func (m *EnemyMovedResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_EnemyMovedResponse.DiscardUnknown(m)
}

var xxx_messageInfo_EnemyMovedResponse proto.InternalMessageInfo

func (m *EnemyMovedResponse) GetPosition() int32 {
	if m != nil {
		return m.Position
	}
	return 0
}

func init() {
	proto.RegisterType((*Response)(nil), "proto.Response")
	proto.RegisterType((*UnrecognizedResponse)(nil), "proto.UnrecognizedResponse")
	proto.RegisterType((*RegisterNameResponse)(nil), "proto.RegisterNameResponse")
	proto.RegisterType((*CreateRoomResponse)(nil), "proto.CreateRoomResponse")
	proto.RegisterType((*JoinRoomResponse)(nil), "proto.JoinRoomResponse")
	proto.RegisterType((*LeaveRoomResponse)(nil), "proto.LeaveRoomResponse")
	proto.RegisterType((*GetRoomsResponse)(nil), "proto.GetRoomsResponse")
	proto.RegisterType((*MoveResponse)(nil), "proto.MoveResponse")
	proto.RegisterType((*RestartResponse)(nil), "proto.RestartResponse")
	proto.RegisterType((*StarterPackResponse)(nil), "proto.StarterPackResponse")
	proto.RegisterType((*EnemyDisconnectedResponse)(nil), "proto.EnemyDisconnectedResponse")
	proto.RegisterType((*EnemyLeftResponse)(nil), "proto.EnemyLeftResponse")
	proto.RegisterType((*EnemyMovedResponse)(nil), "proto.EnemyMovedResponse")
}

func init() { proto.RegisterFile("response.proto", fileDescriptor_response_e7fe0db36a319882) }

var fileDescriptor_response_e7fe0db36a319882 = []byte{
	// 650 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x7c, 0x94, 0xdf, 0x6e, 0xd3, 0x4c,
	0x10, 0xc5, 0x3f, 0xb7, 0xf9, 0x3b, 0x49, 0xfb, 0xb5, 0xdb, 0xaa, 0x75, 0x53, 0x21, 0x82, 0x2b,
	0xa1, 0xd0, 0x8b, 0xa8, 0x0a, 0xa2, 0x12, 0x02, 0x09, 0x29, 0x80, 0x40, 0xa8, 0x54, 0xc5, 0x94,
	0xeb, 0xc8, 0xb5, 0xa7, 0xc1, 0x6d, 0xbc, 0x1b, 0xad, 0x37, 0x45, 0xe6, 0x25, 0xb8, 0xe1, 0xa9,
	0x78, 0x12, 0x1e, 0x03, 0x79, 0x76, 0x9d, 0x6e, 0x9c, 0xc0, 0x55, 0xd7, 0xe7, 0xcc, 0x99, 0xd1,
	0xce, 0xfe, 0x1a, 0xd8, 0x94, 0x98, 0x4e, 0x05, 0x4f, 0xb1, 0x3f, 0x95, 0x42, 0x09, 0x56, 0xa5,
	0x3f, 0x1d, 0x90, 0x42, 0x24, 0x5a, 0xea, 0xb4, 0x90, 0xcf, 0x92, 0x54, 0x7f, 0x78, 0x3f, 0x6b,
	0xd0, 0xf0, 0x4d, 0x84, 0x1d, 0x41, 0x15, 0xa5, 0x14, 0xd2, 0xfd, 0x5d, 0xef, 0x3a, 0xbd, 0xcd,
	0x41, 0x5b, 0x17, 0xf5, 0xdf, 0xe6, 0xa2, 0xaf, 0x3d, 0x36, 0x84, 0xf6, 0x8c, 0x4b, 0x0c, 0xc5,
	0x98, 0xc7, 0xdf, 0x31, 0x72, 0x7f, 0x9c, 0x77, 0x9d, 0x5e, 0x6b, 0x70, 0x68, 0x6a, 0xbf, 0x58,
	0x5e, 0xd1, 0xf8, 0xfd, 0x7f, 0xfe, 0x42, 0x86, 0x0d, 0x61, 0x43, 0xe2, 0x38, 0x4e, 0x15, 0xca,
	0x11, 0x0f, 0x12, 0x74, 0x9d, 0x85, 0x1e, 0xbe, 0xf1, 0xce, 0x83, 0x04, 0xed, 0x1e, 0xd2, 0xd2,
	0xd9, 0x4b, 0x68, 0x85, 0x12, 0x03, 0x85, 0xa3, 0xfc, 0x6e, 0xee, 0x1a, 0x75, 0x38, 0x30, 0x1d,
	0x5e, 0x93, 0xe3, 0x0b, 0x91, 0x58, 0x79, 0x08, 0xe7, 0x2a, 0x3b, 0x85, 0xe6, 0x8d, 0x88, 0xb9,
	0xce, 0xae, 0x53, 0x76, 0xdf, 0x64, 0x3f, 0x88, 0x98, 0x97, 0x92, 0x8d, 0x1b, 0xa3, 0xb1, 0xe7,
	0x00, 0x13, 0x0c, 0xee, 0xcc, 0xd0, 0x0a, 0x05, 0x5d, 0x13, 0x3c, 0xcb, 0x8d, 0x52, 0xb2, 0x39,
	0x29, 0xc4, 0x7c, 0xe4, 0x18, 0x15, 0x05, 0x53, 0xb7, 0xba, 0x30, 0xf2, 0x1d, 0xaa, 0xbc, 0x24,
	0xb5, 0x47, 0x8e, 0x8d, 0xc6, 0x9e, 0x40, 0x25, 0x11, 0x77, 0xe8, 0xd6, 0x28, 0xb2, 0x63, 0x22,
	0x1f, 0xc5, 0x9d, 0xbd, 0x1b, 0x2a, 0x61, 0x03, 0xa8, 0x4b, 0x4c, 0x55, 0x20, 0x95, 0x5b, 0xa7,
	0xea, 0xbd, 0xf9, 0x46, 0x49, 0xb5, 0x02, 0x45, 0x21, 0x7b, 0x05, 0x6d, 0x3a, 0xa0, 0x1c, 0x4d,
	0x83, 0xf0, 0xd6, 0x6d, 0x50, 0xb0, 0x63, 0x82, 0x9f, 0xb5, 0x75, 0x11, 0x84, 0xb7, 0x56, 0xb8,
	0x95, 0xde, 0xcb, 0xec, 0x13, 0x30, 0xe4, 0x98, 0x64, 0xa3, 0x28, 0x4e, 0x43, 0xc1, 0x39, 0x86,
	0x0a, 0x23, 0x17, 0xa9, 0x4d, 0xb7, 0x20, 0x28, 0x2f, 0x78, 0x63, 0xf9, 0x56, 0xb3, 0x6d, 0x2c,
	0x9b, 0xf9, 0x96, 0x75, 0xcb, 0x09, 0x5e, 0x2b, 0xf7, 0x7a, 0x61, 0xcb, 0xd4, 0xea, 0x0c, 0xaf,
	0xed, 0xcb, 0x34, 0xb1, 0x10, 0x73, 0x2c, 0x74, 0x34, 0x5f, 0x48, 0xe4, 0x8e, 0x17, 0xb0, 0xa0,
	0x6c, 0xbe, 0x39, 0x7b, 0xbe, 0x1e, 0x45, 0xea, 0xb0, 0x06, 0x95, 0xcb, 0x6c, 0x8a, 0xde, 0x1e,
	0xec, 0xae, 0x02, 0xd9, 0x3b, 0x86, 0xdd, 0x55, 0x70, 0x32, 0x06, 0x95, 0x39, 0xc7, 0x4d, 0x9f,
	0xce, 0x5e, 0x0f, 0xd8, 0x32, 0x86, 0x2b, 0x2b, 0x1f, 0xc3, 0x56, 0x19, 0xba, 0x95, 0x75, 0x3b,
	0xb0, 0xbd, 0xc4, 0x98, 0xf7, 0x0c, 0xb6, 0xca, 0xf8, 0xb0, 0x47, 0x50, 0xd5, 0x98, 0x39, 0xdd,
	0xf5, 0x5e, 0x6b, 0xd0, 0x2a, 0x28, 0xc8, 0x73, 0xda, 0xf1, 0x8e, 0xa1, 0x6d, 0x23, 0xc4, 0x3a,
	0xd0, 0x98, 0x8a, 0x34, 0x56, 0xb1, 0xe0, 0x34, 0xb3, 0xea, 0xcf, 0xbf, 0xbd, 0x17, 0xf0, 0x7f,
	0x09, 0x20, 0xd6, 0xbb, 0x27, 0xcd, 0xa1, 0xdf, 0x8a, 0xcd, 0x12, 0x69, 0x85, 0xed, 0xfd, 0x72,
	0x60, 0x67, 0x05, 0x45, 0xec, 0x18, 0x9a, 0x49, 0x36, 0x4a, 0xb3, 0xe4, 0x4a, 0x4c, 0x4c, 0x8f,
	0x8d, 0x02, 0x3a, 0x12, 0xfd, 0x46, 0x92, 0xe9, 0x13, 0x3b, 0x81, 0xb6, 0x7e, 0x54, 0x53, 0xbe,
	0xb6, 0xaa, 0x5c, 0xbf, 0xbb, 0x49, 0xec, 0x43, 0x3d, 0xc9, 0x46, 0x6a, 0x26, 0x39, 0xfd, 0x77,
	0x37, 0xfc, 0x5a, 0x92, 0x5d, 0xce, 0x24, 0x67, 0x0f, 0x0a, 0xb4, 0xc8, 0xab, 0x90, 0xa7, 0xf1,
	0x59, 0xb4, 0x69, 0xf9, 0x55, 0x5a, 0xbe, 0xb6, 0xf3, 0xf7, 0xf6, 0x0e, 0xe1, 0xe0, 0xaf, 0x28,
	0xe7, 0xcf, 0xb3, 0x04, 0xa7, 0x77, 0x02, 0x6c, 0x99, 0xba, 0x7f, 0x6d, 0x7b, 0x78, 0x04, 0x0f,
	0x43, 0x91, 0xf4, 0xc7, 0xb1, 0xfa, 0x3a, 0xbb, 0xea, 0x47, 0xc1, 0xb7, 0x38, 0x8a, 0x4e, 0xfb,
	0x01, 0x8f, 0x94, 0x52, 0xfa, 0xc6, 0x17, 0xce, 0x55, 0x8d, 0x0e, 0x4f, 0xff, 0x04, 0x00, 0x00,
	0xff, 0xff, 0xd8, 0x65, 0xcb, 0xcf, 0xf0, 0x05, 0x00, 0x00,
}
