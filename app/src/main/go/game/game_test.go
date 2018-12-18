package game

import (
	"server/proto"
	"testing"
)

func doInLoop(f func(i int, state []proto.Symbol)) {
	for i := range patterns {
		a := [9]proto.Symbol{}
		state := a[:]
		f(i, state)
	}
}

func TestIsWin(t *testing.T) {
	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CROSS
		state[patterns[i][1]] = proto.Symbol_CROSS
		state[patterns[i][2]] = proto.Symbol_CROSS

		if !IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CIRCLE
		state[patterns[i][1]] = proto.Symbol_CIRCLE
		state[patterns[i][2]] = proto.Symbol_CIRCLE

		if !IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CROSS
		state[patterns[i][1]] = proto.Symbol_NO
		state[patterns[i][2]] = proto.Symbol_CROSS

		if IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_NO
		state[patterns[i][1]] = proto.Symbol_NO
		state[patterns[i][2]] = proto.Symbol_CROSS

		if IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CROSS
		state[patterns[i][1]] = proto.Symbol_NO
		state[patterns[i][2]] = proto.Symbol_NO

		if IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CROSS
		state[patterns[i][1]] = proto.Symbol_CIRCLE
		state[patterns[i][2]] = proto.Symbol_CROSS

		if IsWin(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[patterns[i][0]] = proto.Symbol_CIRCLE
		state[patterns[i][1]] = proto.Symbol_CROSS
		state[patterns[i][2]] = proto.Symbol_CIRCLE

		if IsWin(state) {
			t.Fatal(i, state)
		}
	})
}

func TestIsDraw(t *testing.T) {
	doInLoop(func(i int, state []proto.Symbol) {
		state[0] = proto.Symbol_CIRCLE
		state[1] = proto.Symbol_CROSS
		state[2] = proto.Symbol_CIRCLE
		state[3] = proto.Symbol_CROSS
		state[4] = proto.Symbol_CIRCLE
		state[5] = proto.Symbol_CROSS
		state[6] = proto.Symbol_CROSS
		state[7] = proto.Symbol_CIRCLE
		state[8] = proto.Symbol_CROSS

		if !IsDraw(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[0] = proto.Symbol_CIRCLE
		state[1] = proto.Symbol_CROSS
		state[2] = proto.Symbol_CIRCLE
		state[3] = proto.Symbol_CROSS
		state[4] = proto.Symbol_NO
		state[5] = proto.Symbol_CROSS
		state[6] = proto.Symbol_CROSS
		state[7] = proto.Symbol_CIRCLE
		state[8] = proto.Symbol_CROSS

		if IsDraw(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		state[0] = proto.Symbol_CIRCLE
		state[1] = proto.Symbol_CIRCLE
		state[2] = proto.Symbol_CIRCLE
		state[3] = proto.Symbol_CROSS
		state[4] = proto.Symbol_CIRCLE
		state[5] = proto.Symbol_CROSS
		state[6] = proto.Symbol_CROSS
		state[7] = proto.Symbol_CIRCLE
		state[8] = proto.Symbol_CROSS

		if IsDraw(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		for j := range state {
			state[j] = proto.Symbol_CROSS
		}

		state[patterns[i][0]] = proto.Symbol_CIRCLE
		state[patterns[i][1]] = proto.Symbol_CIRCLE
		state[patterns[i][2]] = proto.Symbol_CIRCLE

		if IsDraw(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		for j := range state {
			state[j] = proto.Symbol_CROSS
		}

		if IsDraw(state) {
			t.Fatal(i, state)
		}
	})

	doInLoop(func(i int, state []proto.Symbol) {
		for j := range state {
			state[j] = proto.Symbol_CIRCLE
		}

		if IsDraw(state) {
			t.Fatal(i, state)
		}
	})
}
