package rvemu

import "rvemu/rvemu/trap"

type Mode uint8

const (
	Mode_User Mode = iota
	Mode_Supervisor
	Mode_Machine
	Mode_Debug
)

type Cpu struct {
	xregs            XRegisters
	fregs            FRegisters
	pc               uint64
	csr              CSR
	mode             Mode
	bus              *Bus
	enablePaging     bool
	pageTable        uint64
	reservationBytes []byte
	idle             bool
	instCounter      map[string]uint64
	count            bool
	preInst          uint64
}

func NewCpu() *Cpu {
	return &Cpu{
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

func (c *Cpu) handleInterrupt(i *trap.Interrupt) {
	c.idle = false
	exceptionPc := c.pc
	previousMode := c.mode
	cause := i.ExceptionCode()
	if previousMode <= Mode_Supervisor &&
		((c.csr.Read(MIDELEG)>>cause)&1) == 1 &&
		cause != uint64(trap.MachineTimerInterrupt) {
		c.mode = Mode_Supervisor
		var vector uint64
		if c.csr.ReadBit(STVEC, 0) == 1 {
			vector = 4 * cause
		} else {
			vector = 0
		}
		c.pc = (c.csr.Read(STVEC) & ^uint64(1)) + vector
		c.csr.Write(SEPC, exceptionPc & ^uint64(1))
		c.csr.Write(SCAUSE, 1<<63|cause)
		c.csr.Write(STVAL, 0)
		c.csr.WriteBit(SSTATUS, 5, c.csr.ReadBit(SSTATUS, 1))
		c.csr.WriteBit(SSTATUS, 1, 0)
		switch previousMode {
		case Mode_User:
			c.csr.WriteBit(SSTATUS, 8, 0)
		default:
			c.csr.WriteBit(SSTATUS, 8, 1)
		}
	} else {
		c.mode = Mode_Machine
		var vector uint64
		if c.csr.ReadBit(MTVEC, 0) == 1 {
			vector = 4 * cause
		} else {
			vector = 0
		}
		c.pc = (c.csr.Read(MTVEC) & ^uint64(1)) + vector
		c.csr.Write(MEPC, exceptionPc & ^uint64(1))
		c.csr.Write(MCAUSE, 1<<63|cause)
		c.csr.Write(MTVAL, 0)
		c.csr.WriteBit(MSTATUS, 7, c.csr.ReadBit(MSTATUS, 3))
		c.csr.WriteBit(MSTATUS, 3, 0)
		switch previousMode {
		case Mode_User:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b00)
		case Mode_Supervisor:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b01)
		case Mode_Machine:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b11)
		default:
			panic("错误的特权级！")
		}
	}
}

func (c *Cpu) handleException(e *trap.Exception) {
	exceptionPc := e.Epc(c.pc)
	previousMode := c.mode
	cause := e.ExceptionCode()

	if previousMode <= Mode_Supervisor && ((c.csr.Read(MEDELEG)>>cause)&1) == 1 {
		// 在S-mode中处理trap
		c.mode = Mode_Supervisor
		c.pc = c.csr.Read(STVEC) & ^uint64(1)
		c.csr.Write(SEPC, exceptionPc & ^uint64(1))
		c.csr.Write(SCAUSE, cause)
		c.csr.Write(STVAL, e.TrapValue(exceptionPc))
		c.csr.WriteBit(SSTATUS, 5, c.csr.ReadBit(SSTATUS, 1))
		c.csr.WriteBit(SSTATUS, 1, 0)
		switch previousMode {
		case Mode_User:
			c.csr.WriteBit(SSTATUS, 8, 0)
		default:
			c.csr.WriteBit(SSTATUS, 8, 1)
		}
	} else {
		// 在M-mode中处理trap
		c.mode = Mode_Machine
		c.pc = c.csr.Read(MTVEC) & ^uint64(1)
		c.csr.Write(MEPC, exceptionPc & ^uint64(1))
		c.csr.Write(MCAUSE, cause)
		c.csr.Write(MTVAL, e.TrapValue(exceptionPc))
		c.csr.WriteBit(MSTATUS, 5, c.csr.ReadBit(MSTATUS, 3))
		c.csr.WriteBit(MSTATUS, 3, 0)
		switch previousMode {
		case Mode_User:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b00)
		case Mode_Supervisor:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b01)
		case Mode_Machine:
			c.csr.WriteBits(MSTATUS, 11, 13, 0b11)
		default:
			panic("无效的特权级！")
		}
	}
}
