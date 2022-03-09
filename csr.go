package rvemu

const (
	CSR_SIZE uint64 = 4096

	//////////////////////////////
	// User-level CSR addresses //
	//////////////////////////////
	// User trap setup.

	USTATUS uint16 = 0x000 // User status register.
	UTVEC   uint16 = 0x005 // User trap handler base address.

	// User trap handling.

	UEPC   uint16 = 0x041 // User exception program counter.
	UCAUSE uint16 = 0x042 // User trap cause.
	UTVAL  uint16 = 0x043 // User bad address or instruction.

	// User floating-point CSRs.

	FFLAGS uint16 = 0x001 // Flating-point accrued exceptions.
	FRB    uint16 = 0x002 // Floating-point dynamic rounding mode.
	FCSR   uint16 = 0x003 // Floating-point control and status register (frm + fflags).

	// User Counter/Timers.

	TIME uint16 = 0xc01 // Timer for RDTIME instruction.

	/////////////////////////////////////
	// Supervisor-level CSR addresses //
	////////////////////////////////////
	// Supervisor trap setup.

	SSTATUS uint16 = 0x100 // Supervisor status register.
	SEDELEG uint16 = 0x102 // Supervisor exception delegation register.
	SIDELEG uint16 = 0x103 // Supervisor interrupt delegation register.
	SIE     uint16 = 0x104 // Supervisor interrupt-enable register.
	STVEC   uint16 = 0x105 // Supervisor trap handler base address.

	// Supervisor trap handling.

	SSCRATCH uint16 = 0x140 // Scratch register for supervisor trap handlers.
	SEPC     uint16 = 0x141 // Supervisor exception program counter.
	SCAUSE   uint16 = 0x142 // Supervisor trap cause.
	STVAL    uint16 = 0x143 // Supervisor bad address or instruction.
	SIP      uint16 = 0x144 // Supervisor interrupt pending.

	// Supervisor protection and translation.

	SATP uint16 = 0x180 // Supervisor address translation and protection.

	// SSTATUS fields.

	SSTATUS_SIE  uint64 = 0x2                 // sstatus[1]
	SSTATUS_SPIE uint64 = 0x20                // sstatus[5]
	SSTATUS_UBE  uint64 = 0x40                // sstatus[6]
	SSTATUS_SPP  uint64 = 0x100               // sstatus[8]
	SSTATUS_FS   uint64 = 0x6000              // sstatus[14:13]
	SSTATUS_XS   uint64 = 0x18000             // sstatus[16:15]
	SSTATUS_SUM  uint64 = 0x40000             // sstatus[18]
	SSTATUS_MXR  uint64 = 0x80000             // sstatus[19]
	SSTATUS_UXL  uint64 = 0x3_00000000        // sstatus[33:32]
	SSTATUS_SD   uint64 = 0x80000000_00000000 // sstatus[63]
	SSTATUS_MASK uint64 = SSTATUS_SIE |
		SSTATUS_SPIE |
		SSTATUS_UBE |
		SSTATUS_SPP |
		SSTATUS_FS |
		SSTATUS_XS |
		SSTATUS_SUM |
		SSTATUS_MXR |
		SSTATUS_UXL |
		SSTATUS_SD

	/////////////////////////////////
	// Machine-level CSR addresses //
	/////////////////////////////////
	// Machine information registers.

	MVENDORID uint16 = 0xf11 // Vendor ID.
	MARCHID   uint16 = 0xf12 // Architecture ID.
	MIMPID    uint16 = 0xf13 // Implementation ID.
	MHARTID   uint16 = 0xf14 // Hardware thread ID.

	// Machine trap setup.

	MSTATUS    uint16 = 0x300 // Machine status register.
	MISA       uint16 = 0x301 // ISA and extensions.
	MEDELEG    uint16 = 0x302 // Machine exception delefation register.
	MIDELEG    uint16 = 0x303 // Machine interrupt delefation register.
	MIE        uint16 = 0x304 // Machine interrupt-enable register.
	MTVEC      uint16 = 0x305 // Machine trap-handler base address.
	MCOUNTEREN uint16 = 0x306 // Machine counter enable.

	// Machine trap handling.

	MSCRATCH uint16 = 0x340 // Scratch register for machine trap handlers.
	MEPC     uint16 = 0x341 // Machine exception program counter.
	MCAUSE   uint16 = 0x342 // Machine trap cause.
	MTVAL    uint16 = 0x343 // Machine bad address or instruction.
	MIP      uint16 = 0x344 // Machine interrupt pending.

	// Machine memory protection.

	PMPCFG0  uint16 = 0x3a0 // Physical memory protection configuration.
	PMPADDR0 uint16 = 0x3b0 // Physical memory protection address register.

	// MIP fields.

	SSIP_BIT uint64 = 1 << 1  // Supervisor software interrupt.
	MSIP_BIT uint64 = 1 << 3  // Machine software interrupt.
	STIP_BIT uint64 = 1 << 5  // Supervisor timer interrupt.
	MTIP_BIT uint64 = 1 << 7  // Machine timer interrupt.
	SEIP_BIT uint64 = 1 << 9  // Supervisor external interrupt.
	MEIP_BIT uint64 = 1 << 11 // Machine external interrupt.
)

type CSR interface {
	Read(addr uint16) uint64
	Write(addr uint16, value uint64)
	ReadBit(addr uint16, bit uint64) uint64
	ReadBits(addr uint16, start, end uint64) uint64
	WriteBit(addr uint16, bit uint64, value uint64)
	WriteBits(addr uint16, start, end uint64, value uint64)
	Reset()
}

type csr struct {
	csrs [CSR_SIZE]uint64
}

func NewCSR() CSR {
	var csrs [CSR_SIZE]uint64
	csrs[MISA] = (2 << 62) | // MXL[1:0]=2 (XLEN is 64)
		(1 << 20) | // Extensions[20] (User mode implemented)
		(1 << 18) | // Extensions[18] (Supervisor mode implemented)
		(1 << 12) | // Extensions[12] (Integer Multiply/Divide extension)
		(1 << 8) | // Extensions[8] (RV32I/64I/128I base ISA)
		(1 << 5) | // Extensions[5] (Single-precision floating-point extension)
		(1 << 3) | // Extensions[3] (Double-precision floating-point extension)
		(1 << 2) | // Extensions[2] (Compressed extension)
		1 // Extensions[0] (Atomic extension)
	return &csr{csrs: csrs}
}

func (c *csr) Read(addr uint16) uint64 {
	switch addr {
	case SSTATUS:
		return c.csrs[MSTATUS] & SSTATUS_MASK
	case SIE:
		return c.csrs[MIE] & c.csrs[MIDELEG]
	case SIP:
		return c.csrs[MIP] & c.csrs[MIDELEG]
	default:
		return c.csrs[addr]
	}
}

func (c *csr) Write(addr uint16, value uint64) {
	switch addr {
	case MVENDORID, MARCHID, MIMPID, MHARTID:
	case SSTATUS:
		c.csrs[MSTATUS] = (c.csrs[MSTATUS] & ^SSTATUS_MASK) | (value & SSTATUS_MASK)
	case SIE:
		c.csrs[MIE] = (c.csrs[MIE] & ^c.csrs[MIDELEG]) | (value & c.csrs[MIDELEG])
	case SIP:
		mask := SSIP_BIT & c.csrs[MIDELEG]
		c.csrs[MIP] = (c.csrs[MIP] & ^mask) | (value & mask)
	default:
		c.csrs[addr] = value
	}
}

func (c *csr) ReadBit(addr uint16, bit uint64) uint64 {
	if (c.Read(addr) & (1 << bit)) != 0 {
		return 1
	} else {
		return 0
	}
}

func (c *csr) ReadBits(addr uint16, start, end uint64) uint64 {
	var bitMask uint64
	if end != 64 {
		bitMask = ^0 << end
	}
	return (c.Read(addr) & ^bitMask) >> start
}

func (c *csr) WriteBit(addr uint16, bit uint64, value uint64) {
	if value == 1 {
		c.Write(addr, c.Read(addr)|1<<bit)
	} else {
		c.Write(addr, c.Read(addr)|^(1<<bit))
	}
}

func (c *csr) WriteBits(addr uint16, start, end uint64, value uint64) {
	var bitMask uint64 = (^0 << end) | ^(^0 << start)
	c.Write(addr, (c.Read(addr)&bitMask)|(value<<start))
}

func (c *csr) Reset() {
	var csrs [CSR_SIZE]uint64
	csrs[MISA] = (2 << 62) | // MXL[1:0]=2 (XLEN is 64)
		(1 << 20) | // Extensions[20] (User mode implemented)
		(1 << 18) | // Extensions[18] (Supervisor mode implemented)
		(1 << 12) | // Extensions[12] (Integer Multiply/Divide extension)
		(1 << 8) | // Extensions[8] (RV32I/64I/128I base ISA)
		(1 << 5) | // Extensions[5] (Single-precision floating-point extension)
		(1 << 3) | // Extensions[3] (Double-precision floating-point extension)
		(1 << 2) | // Extensions[2] (Compressed extension)
		1 // Extensions[0] (Atomic extension)
	c.csrs = csrs
}
