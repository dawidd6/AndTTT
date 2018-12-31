// Code generated by protoc-gen-go. DO NOT EDIT.
// source: enums.proto

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

type Error int32

const (
	Error_NONE                          Error = 0
	Error_UNDEFINED                     Error = 1
	Error_CLIENT_NAME_TOO_LONG          Error = 21
	Error_CLIENT_NAME_EMPTY             Error = 2
	Error_CLIENT_NAME_TAKEN             Error = 3
	Error_CLIENT_HAS_A_ROOM             Error = 4
	Error_CLIENT_HAS_NO_ROOM            Error = 5
	Error_CLIENT_NOT_FOUND_IN_ROOM      Error = 6
	Error_CLIENT_NOT_FOUND              Error = 7
	Error_CLIENT_HAS_NO_TURN            Error = 8
	Error_CLIENT_HAS_NO_SYMBOL          Error = 9
	Error_ROOM_NAME_TOO_LONG            Error = 20
	Error_ROOM_NAME_EMPTY               Error = 10
	Error_ROOM_NAME_TAKEN               Error = 11
	Error_ROOM_FULL                     Error = 12
	Error_ROOM_NOT_FOUND                Error = 13
	Error_ROOM_NOT_EMPTY                Error = 14
	Error_ROOM_PASSWORD_NOT_FOUND       Error = 22
	Error_ROOM_PASSWORD_MISMATCH        Error = 23
	Error_ROOM_PASSWORD_TOO_LONG        Error = 24
	Error_ENEMY_NOT_FOUND_BUT_SHOULD_BE Error = 15
	Error_ENEMY_NOT_FOUND               Error = 16
	Error_POSITION_ALREADY_OCCUPIED     Error = 17
	Error_POSITION_OUT_OF_BOUND         Error = 18
	Error_THERE_IS_A_WINNER             Error = 19
)

var Error_name = map[int32]string{
	0:  "NONE",
	1:  "UNDEFINED",
	21: "CLIENT_NAME_TOO_LONG",
	2:  "CLIENT_NAME_EMPTY",
	3:  "CLIENT_NAME_TAKEN",
	4:  "CLIENT_HAS_A_ROOM",
	5:  "CLIENT_HAS_NO_ROOM",
	6:  "CLIENT_NOT_FOUND_IN_ROOM",
	7:  "CLIENT_NOT_FOUND",
	8:  "CLIENT_HAS_NO_TURN",
	9:  "CLIENT_HAS_NO_SYMBOL",
	20: "ROOM_NAME_TOO_LONG",
	10: "ROOM_NAME_EMPTY",
	11: "ROOM_NAME_TAKEN",
	12: "ROOM_FULL",
	13: "ROOM_NOT_FOUND",
	14: "ROOM_NOT_EMPTY",
	22: "ROOM_PASSWORD_NOT_FOUND",
	23: "ROOM_PASSWORD_MISMATCH",
	24: "ROOM_PASSWORD_TOO_LONG",
	15: "ENEMY_NOT_FOUND_BUT_SHOULD_BE",
	16: "ENEMY_NOT_FOUND",
	17: "POSITION_ALREADY_OCCUPIED",
	18: "POSITION_OUT_OF_BOUND",
	19: "THERE_IS_A_WINNER",
}
var Error_value = map[string]int32{
	"NONE":                          0,
	"UNDEFINED":                     1,
	"CLIENT_NAME_TOO_LONG":          21,
	"CLIENT_NAME_EMPTY":             2,
	"CLIENT_NAME_TAKEN":             3,
	"CLIENT_HAS_A_ROOM":             4,
	"CLIENT_HAS_NO_ROOM":            5,
	"CLIENT_NOT_FOUND_IN_ROOM":      6,
	"CLIENT_NOT_FOUND":              7,
	"CLIENT_HAS_NO_TURN":            8,
	"CLIENT_HAS_NO_SYMBOL":          9,
	"ROOM_NAME_TOO_LONG":            20,
	"ROOM_NAME_EMPTY":               10,
	"ROOM_NAME_TAKEN":               11,
	"ROOM_FULL":                     12,
	"ROOM_NOT_FOUND":                13,
	"ROOM_NOT_EMPTY":                14,
	"ROOM_PASSWORD_NOT_FOUND":       22,
	"ROOM_PASSWORD_MISMATCH":        23,
	"ROOM_PASSWORD_TOO_LONG":        24,
	"ENEMY_NOT_FOUND_BUT_SHOULD_BE": 15,
	"ENEMY_NOT_FOUND":               16,
	"POSITION_ALREADY_OCCUPIED":     17,
	"POSITION_OUT_OF_BOUND":         18,
	"THERE_IS_A_WINNER":             19,
}

func (x Error) String() string {
	return proto.EnumName(Error_name, int32(x))
}
func (Error) EnumDescriptor() ([]byte, []int) {
	return fileDescriptor_enums_62d2914ff991fb5c, []int{0}
}

type Restart int32

const (
	Restart_UNSPECIFIED Restart = 0
	Restart_REQUESTED   Restart = 1
	Restart_APPROVED    Restart = 2
	Restart_DENIED      Restart = 3
)

var Restart_name = map[int32]string{
	0: "UNSPECIFIED",
	1: "REQUESTED",
	2: "APPROVED",
	3: "DENIED",
}
var Restart_value = map[string]int32{
	"UNSPECIFIED": 0,
	"REQUESTED":   1,
	"APPROVED":    2,
	"DENIED":      3,
}

func (x Restart) String() string {
	return proto.EnumName(Restart_name, int32(x))
}
func (Restart) EnumDescriptor() ([]byte, []int) {
	return fileDescriptor_enums_62d2914ff991fb5c, []int{1}
}

type Symbol int32

const (
	Symbol_NO     Symbol = 0
	Symbol_CROSS  Symbol = 1
	Symbol_CIRCLE Symbol = 2
)

var Symbol_name = map[int32]string{
	0: "NO",
	1: "CROSS",
	2: "CIRCLE",
}
var Symbol_value = map[string]int32{
	"NO":     0,
	"CROSS":  1,
	"CIRCLE": 2,
}

func (x Symbol) String() string {
	return proto.EnumName(Symbol_name, int32(x))
}
func (Symbol) EnumDescriptor() ([]byte, []int) {
	return fileDescriptor_enums_62d2914ff991fb5c, []int{2}
}

func init() {
	proto.RegisterEnum("proto.Error", Error_name, Error_value)
	proto.RegisterEnum("proto.Restart", Restart_name, Restart_value)
	proto.RegisterEnum("proto.Symbol", Symbol_name, Symbol_value)
}

func init() { proto.RegisterFile("enums.proto", fileDescriptor_enums_62d2914ff991fb5c) }

var fileDescriptor_enums_62d2914ff991fb5c = []byte{
	// 464 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0x64, 0x92, 0x41, 0x6f, 0xda, 0x4e,
	0x10, 0xc5, 0x03, 0x01, 0x02, 0x43, 0x12, 0x26, 0x13, 0x20, 0xe4, 0xff, 0x6f, 0x54, 0x55, 0x3d,
	0x54, 0xe2, 0xc0, 0xa5, 0x52, 0xef, 0xc6, 0x1e, 0xca, 0xaa, 0xf6, 0xae, 0xbb, 0xbb, 0x6e, 0xc4,
	0x69, 0x05, 0x25, 0x6a, 0x23, 0x95, 0x50, 0x39, 0x8e, 0xaa, 0x7e, 0xcf, 0x7e, 0xa0, 0xca, 0x76,
	0x4b, 0x0d, 0x39, 0xd9, 0xfa, 0xbd, 0x37, 0x4f, 0xfb, 0x76, 0x07, 0xba, 0x77, 0x0f, 0x4f, 0x9b,
	0xc7, 0xc9, 0xf7, 0x74, 0x9b, 0x6d, 0xa9, 0x59, 0x7c, 0xc6, 0xbf, 0x1a, 0xd0, 0xe4, 0x34, 0xdd,
	0xa6, 0xd4, 0x86, 0x86, 0x54, 0x92, 0xf1, 0x88, 0xce, 0xa0, 0x93, 0xc8, 0x80, 0x67, 0x42, 0x72,
	0x80, 0x35, 0x1a, 0x41, 0xdf, 0x0f, 0x05, 0x4b, 0xeb, 0xa4, 0x17, 0xb1, 0xb3, 0x4a, 0xb9, 0x50,
	0xc9, 0xf7, 0x38, 0xa0, 0x01, 0x5c, 0x54, 0x15, 0x8e, 0x62, 0xbb, 0xc0, 0xfa, 0x21, 0xb6, 0xde,
	0x07, 0x96, 0x78, 0x5c, 0xc1, 0x73, 0xcf, 0x38, 0xcf, 0x69, 0xa5, 0x22, 0x6c, 0xd0, 0x10, 0xa8,
	0x82, 0xa5, 0x2a, 0x79, 0x93, 0x5e, 0xc0, 0xe8, 0x6f, 0x8a, 0xb2, 0x6e, 0xa6, 0x12, 0x19, 0x38,
	0x21, 0x4b, 0xb5, 0x45, 0x7d, 0xc0, 0x43, 0x15, 0x4f, 0x9e, 0x67, 0xd9, 0x44, 0x4b, 0x6c, 0x57,
	0x2a, 0xfc, 0xe1, 0x66, 0x11, 0x4d, 0x55, 0x88, 0x9d, 0x7c, 0x22, 0x4f, 0x3c, 0xa8, 0xd6, 0xa7,
	0x4b, 0xe8, 0xfd, 0xe3, 0x65, 0x31, 0xd8, 0x87, 0x65, 0xad, 0x6e, 0x7e, 0x5b, 0x05, 0x9c, 0x25,
	0x61, 0x88, 0xa7, 0x44, 0x70, 0x5e, 0x7a, 0x76, 0xc7, 0x3a, 0xdb, 0x63, 0x65, 0xd6, 0x39, 0xfd,
	0x0f, 0x57, 0x05, 0x8b, 0x3d, 0x63, 0x6e, 0x95, 0x0e, 0x2a, 0x03, 0x43, 0xfa, 0x0f, 0x86, 0xfb,
	0x62, 0x24, 0x4c, 0xe4, 0x59, 0x7f, 0x8e, 0x57, 0xcf, 0xb5, 0xdd, 0xa9, 0x47, 0xf4, 0x0a, 0x6e,
	0x58, 0x72, 0xb4, 0xa8, 0x5c, 0xd9, 0x34, 0xb1, 0xce, 0xcc, 0x55, 0x12, 0x06, 0x6e, 0xca, 0xd8,
	0xcb, 0x3b, 0x1c, 0x58, 0x10, 0xe9, 0x06, 0xae, 0x63, 0x65, 0x84, 0x15, 0x4a, 0x3a, 0x2f, 0xd4,
	0xec, 0x05, 0x0b, 0xa7, 0x7c, 0x3f, 0x89, 0x05, 0x07, 0x78, 0x41, 0xd7, 0x30, 0xd8, 0xc9, 0x2a,
	0xb1, 0x4e, 0xcd, 0xdc, 0xb4, 0x98, 0xa4, 0xfc, 0x51, 0xed, 0x9c, 0x35, 0x3b, 0x91, 0x3f, 0xe9,
	0xad, 0x90, 0x92, 0x35, 0x5e, 0x8e, 0x7d, 0x38, 0xd1, 0x77, 0x8f, 0xd9, 0x32, 0xcd, 0xa8, 0x07,
	0xdd, 0x44, 0x9a, 0x98, 0x7d, 0x31, 0xcb, 0xd3, 0x8a, 0xf5, 0xd2, 0xfc, 0x31, 0x61, 0x63, 0x8b,
	0xf5, 0x3a, 0x85, 0xb6, 0x17, 0xc7, 0x5a, 0x7d, 0xe2, 0x00, 0xeb, 0x04, 0xd0, 0x0a, 0x58, 0xe6,
	0xc6, 0xe3, 0xf1, 0x1b, 0x68, 0x99, 0x9f, 0x9b, 0xd5, 0xf6, 0x1b, 0xb5, 0xa0, 0x2e, 0x15, 0x1e,
	0x51, 0x07, 0x9a, 0xbe, 0x56, 0xc6, 0x60, 0x2d, 0x37, 0xfa, 0x42, 0xfb, 0x21, 0x63, 0x7d, 0xfa,
	0x1a, 0x5e, 0x7e, 0xde, 0x6e, 0x26, 0x5f, 0xee, 0xb3, 0xaf, 0x4f, 0xab, 0xc9, 0x7a, 0xf9, 0xe3,
	0x7e, 0xbd, 0x7e, 0x37, 0x59, 0x3e, 0xac, 0xb3, 0x2c, 0x2b, 0xd7, 0x3d, 0xae, 0xad, 0x5a, 0xc5,
	0xcf, 0xdb, 0xdf, 0x01, 0x00, 0x00, 0xff, 0xff, 0xa6, 0x59, 0xe6, 0x96, 0x06, 0x03, 0x00, 0x00,
}
