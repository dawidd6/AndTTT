package mmap

import (
	"sync"
)

type mmap struct {
	m map[interface{}]interface{}
	sync.RWMutex
}

func New() *mmap {
	m := new(mmap)
	m.m = make(map[interface{}]interface{})
	return m
}

func (m *mmap) Set(key interface{}, value interface{}) {
	m.Lock()
	m.m[key] = value
	m.Unlock()
}

func (m *mmap) Get(key interface{}) interface{} {
	m.Lock()
	value, _ := m.m[key]
	m.Unlock()
	return value
}

func (m *mmap) Has(key interface{}) bool {
	m.Lock()
	_, ok := m.m[key]
	m.Unlock()
	return ok
}

func (m *mmap) Remove(key interface{}) {
	m.Lock()
	delete(m.m, key)
	m.Unlock()
}

func (m *mmap) Count() int {
	m.Lock()
	count := len(m.m)
	m.Unlock()
	return count
}

func (m *mmap) Items() []interface{} {
	values := make([]interface{}, 0)

	m.Lock()
	for _, value := range m.m {
		values = append(values, value)
	}
	m.Unlock()

	return values
}

func (m *mmap) Iterate(f func(key interface{}, value interface{})) {
	m.Lock()
	for key, value := range m.m {
		f(key, value)
	}
	m.Unlock()
}
