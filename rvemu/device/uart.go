package device

import "rvemu/rvemu/trap"

type Uart struct {
}

func NewUart() *Uart {
	return &Uart{}
}

func (u *Uart) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (u *Uart) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
