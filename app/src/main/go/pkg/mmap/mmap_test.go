package mmap

import (
	"fmt"
	"strings"
	"sync"
	"testing"
)

const count = 1000

var m *mmap

func doInLoop(f func(i int, key string, value string)) {
	wg := new(sync.WaitGroup)

	wg.Add(count)
	for i := 0; i < count; i++ {
		key := fmt.Sprintf("key%d", i)
		value := fmt.Sprintf("value%d", i)

		go func(i int, key string, value string) {
			defer wg.Done()

			f(i, key, value)
		}(i, key, value)
	}
	wg.Wait()
}

func TestNew(t *testing.T) {
	m = New()
}

func TestMap_Set(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		m.Set(key, value)
	})
}

func TestMap_Count(t *testing.T) {
	if m.Count() != count {
		t.Fatal(m.Count())
	}
}

func TestMap_Has(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		if !m.Has(key) {
			t.Fatal("has not")
		}

		if m.Has(value) {
			t.Fatal("has")
		}
	})
}

func TestMap_Get(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		got := m.Get(key)

		if got != value {
			t.Fatal("!= value")
		}
	})
}

func TestMap_Iterate(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		m.Iterate(func(key interface{}, value interface{}) {
			if !strings.Contains(key.(string), "key") {
				t.Fatal("not contains key")
			}
			if !strings.Contains(value.(string), "value") {
				t.Fatal("not contains value")
			}
		})
	})
}

func TestMap_Items(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		items := m.Items()
		if len(items) != count {
			t.Fatal(len(items))
		}
	})
}

func TestMap_Remove(t *testing.T) {
	doInLoop(func(i int, key string, value string) {
		m.Remove(key)
	})

	if m.Count() != 0 {
		t.Fatal(m.Count())
	}
}
