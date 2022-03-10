package device

import "rvemu/rvemu/trap"

type Dram struct {
}

func NewDram() *Dram {
	return &Dram{}
}

func (d *Dram) Initialize(data []byte) {

}

func (d *Dram) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (d *Dram) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
