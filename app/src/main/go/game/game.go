package game

import (
	"math/rand"
	"server/proto"
)

var patterns = [][]int{
	{0, 1, 2}, // 0 horizontal up
	{3, 4, 5}, // 1 horizontal mid
	{6, 7, 8}, // 2 horizontal bottom
	{0, 3, 6}, // 3 vertical left
	{1, 4, 7}, // 4 vertical mid
	{2, 5, 8}, // 5 vertical right
	{0, 4, 8}, // 6 narrow left
	{2, 4, 6}, // 7 narrow right
}

func IsWin(state []proto.Symbol) bool {
	symbols := []proto.Symbol{proto.Symbol_CIRCLE, proto.Symbol_CROSS}

	for i := 0; i < len(patterns); i++ {
		for _, symbol := range symbols {
			if state[patterns[i][0]] != symbol {
				continue
			}

			if state[patterns[i][1]] != symbol {
				continue
			}

			if state[patterns[i][2]] != symbol {
				continue
			}

			return true
		}
	}

	return false
}

func IsDraw(state []proto.Symbol) bool {
	for i := range state {
		if state[i] == proto.Symbol_NO {
			return false
		}
	}

	return !IsWin(state)
}

func GetRandomSymbol() proto.Symbol {
	if rand.Intn(2) == 0 {
		return proto.Symbol_CROSS
	}
	return proto.Symbol_CIRCLE
}

func GetRandomTurn() bool {
	if rand.Intn(2) == 0 {
		return true
	}
	return false
}

func GetOppositeSymbol(symbol proto.Symbol) proto.Symbol {
	if symbol == proto.Symbol_CIRCLE {
		return proto.Symbol_CROSS
	}

	if symbol == proto.Symbol_CROSS {
		return proto.Symbol_CIRCLE
	}

	return proto.Symbol_NO
}
