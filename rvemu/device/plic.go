package device

import "rvemu/rvemu/trap"

type Plic struct {
}

func NewPlic() *Plic {
	return &Plic{}
}

func (p *Plic) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (p *Plic) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
