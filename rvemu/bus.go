package rvemu

import (
	"rvemu/rvemu/device"
	"rvemu/rvemu/trap"
)

type Bus struct {
	clint  *device.Clint
	plic   *device.Plic
	uart   *device.Uart
	virtio *device.Virtio
	dram   *device.Dram
	rom    *device.Rom
}

func NewBus() *Bus {
	return &Bus{
		clint:  device.NewClint(),
		plic:   device.NewPlic(),
		uart:   device.NewUart(),
		virtio: device.NewVirtio(),
		dram:   device.NewDram(),
		rom:    device.NewRom(),
	}
}

func (b *Bus) InitializeDram(data []byte) {
	b.dram.Initialize(data)
}

func (b *Bus) InitializeDisk(data []byte) {
	b.virtio.Initialize(data)
}

func (b *Bus) Read(addr uint64, size uint8) (uint64, *trap.Exception) {
	if MROM_BASE <= addr && addr <= MROM_END {
		return b.rom.Read(addr, size)
	} else if CLINT_BASE <= addr && addr <= CLINT_END {
		return b.clint.Read(addr, size)
	} else if PLIC_BASE <= addr && addr <= PLIC_END {
		return b.plic.Read(addr, size)
	} else if UART_BASE <= addr && addr <= UART_END {
		return b.uart.Read(addr, size)
	} else if VIRTIO_BASE <= addr && addr <= VIRTIO_END {
		return b.virtio.Read(addr, size)
	} else if DRAM_BASE <= addr && addr <= DRAM_END {
		return b.dram.Read(addr, size)
	} else {
		return 0, &trap.Exception{EType: trap.LoadAccessFault}
	}
}

func (b *Bus) Write(addr uint64, value uint64, size uint8) *trap.Exception {
	if MROM_BASE <= addr && addr <= MROM_END {
		return b.rom.Write(addr, value, size)
	} else if CLINT_BASE <= addr && addr <= CLINT_END {
		return b.clint.Write(addr, value, size)
	} else if PLIC_BASE <= addr && addr <= PLIC_END {
		return b.plic.Write(addr, value, size)
	} else if UART_BASE <= addr && addr <= UART_END {
		return b.uart.Write(addr, value, size)
	} else if VIRTIO_BASE <= addr && addr <= VIRTIO_END {
		return b.virtio.Write(addr, value, size)
	} else if DRAM_BASE <= addr && addr <= DRAM_END {
		return b.dram.Write(addr, value, size)
	} else {
		return &trap.Exception{EType: trap.StoreAMOAccessFault}
	}
}
