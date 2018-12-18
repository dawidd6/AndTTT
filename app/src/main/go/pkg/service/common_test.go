package service

import (
	"fmt"
	"net"
	"os"
	"sync"
)

const count = 100
const addr = "localhost:33330"

func init() {
	listener, err := net.Listen("tcp", addr)
	if err != nil {
		fmt.Println("listen error", err)
		os.Exit(1)
	}
	go func() {
		for {
			listener.Accept()
		}
	}()
}

func doInLoop(f func(i int, name, room string)) {
	wg := new(sync.WaitGroup)

	wg.Add(count)
	for i := 0; i < count; i++ {
		name := fmt.Sprintf("client%d", i)
		room := fmt.Sprintf("room%d", i)

		go func(i int, name, room string) {
			defer wg.Done()

			f(i, name, room)
		}(i, name, room)
	}
	wg.Wait()
}
