package rvemu

type Mode uint8

const (
	Mode_User Mode = iota
	Mode_Supervisor
	Mode_Machine
	Mode_Debug
)

type Cpu interface {
}

type cpu struct {
	xregs            XRegisters
	fregs            FRegisters
	pc               uint64
	csr              CSR
	mode             Mode
	bus              Bus
	enablePaging     bool
	pageTable        uint64
	reservationBytes []byte
	idle             bool
	instCounter      map[string]uint64
	count            bool
	preInst          uint64
}

func NewCpu() Cpu {
	return &cpu{
		xregs:            NewXRegisters(),
		fregs:            NewFRegisters(),
		pc:               0,
		csr:              NewCSR(),
		mode:             Mode_Machine,
		bus:              NewBus(),
		enablePaging:     false,
		pageTable:        0,
		reservationBytes: []byte{},
		idle:             false,
		instCounter:      map[string]uint64{},
		count:            false,
		preInst:          0,
	}
}
