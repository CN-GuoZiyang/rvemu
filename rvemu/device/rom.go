package device

import "rvemu/rvemu/trap"

type Rom struct {
}

func NewRom() *Rom {
	return &Rom{}
}

func (r *Rom) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (r *Rom) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
