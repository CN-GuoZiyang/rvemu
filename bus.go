package rvemu

type Bus interface {
}

type bus struct {
}

func NewBus() Bus {
	return &bus{}
}
