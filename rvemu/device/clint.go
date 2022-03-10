package device

import "rvemu/rvemu/trap"

type Clint struct {
}

func NewClint() *Clint {
	return &Clint{}
}

func (c *Clint) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (c *Clint) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
