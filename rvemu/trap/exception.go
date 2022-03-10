package trap

type ExceptionType uint64

const (
	InstructionAddressMisaligned ExceptionType = 0
	InstructionAccessFault       ExceptionType = 1
	IllegalInstruction           ExceptionType = 2
	Breakpoint                   ExceptionType = 3
	LoadAddressMisaligned        ExceptionType = 4
	LoadAccessFault              ExceptionType = 5
	StoreAMOAddressMisaligned    ExceptionType = 6
	StoreAMOAccessFault          ExceptionType = 7
	EnvironmentCallFromUMode     ExceptionType = 8
	EnvironmentCallFromSMode     ExceptionType = 9
	EnvironmentCallFromMMode     ExceptionType = 11
	InstructionPageFault         ExceptionType = 12
	LoadPageFault                ExceptionType = 13
	StoreAMOPageFault            ExceptionType = 15
)

type Trap uint8

const (
	Trap_Contained Trap = iota
	Trap_Requested
	Trap_Invisible
	Trap_Fatal
)

type Exception struct {
	EType ExceptionType
	Value uint64
}

func (e *Exception) ExceptionCode() uint64 {
	return uint64(e.EType)
}

func (e *Exception) Epc(pc uint64) uint64 {
	switch e.EType {
	case Breakpoint, EnvironmentCallFromUMode, EnvironmentCallFromSMode, EnvironmentCallFromMMode,
		InstructionPageFault, LoadPageFault, StoreAMOPageFault:
		return pc
	default:
		return pc + 4
	}
}

func (e *Exception) TrapValue(pc uint64) uint64 {
	switch e.EType {
	case InstructionAddressMisaligned, InstructionAccessFault, Breakpoint, LoadAddressMisaligned,
		LoadAccessFault, StoreAMOAddressMisaligned, StoreAMOAccessFault:
		return pc
	case InstructionPageFault, LoadPageFault, StoreAMOPageFault:
		return e.Value
	default:
		return 0
	}
}

func (e *Exception) TakeTrap() Trap {
	switch e.EType {
	case InstructionAddressMisaligned, InstructionAccessFault:
		return Trap_Fatal
	case IllegalInstruction:
		return Trap_Invisible
	case Breakpoint:
		return Trap_Requested
	case LoadAddressMisaligned, LoadAccessFault, StoreAMOAddressMisaligned, StoreAMOAccessFault:
		return Trap_Fatal
	case EnvironmentCallFromUMode, EnvironmentCallFromSMode, EnvironmentCallFromMMode:
		return Trap_Requested
	case InstructionPageFault, LoadPageFault, StoreAMOPageFault:
		return Trap_Invisible
	default:
		panic("无效的Exception类型！")
	}
}
