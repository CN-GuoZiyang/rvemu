package device

import "rvemu/rvemu/trap"

type Virtio struct {
}

func NewVirtio() *Virtio {
	return &Virtio{}
}

func (v *Virtio) Initialize(data []byte) {

}

func (v *Virtio) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	return 0, nil
}

func (v *Virtio) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	return nil
}
