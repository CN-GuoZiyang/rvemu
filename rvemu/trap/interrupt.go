package trap

type Interrupt uint8

const (
	UserSoftwareInterrupt       Interrupt = 0
	SupervisorSoftwareInterrupt Interrupt = 1
	MachineSoftwareInterrupt    Interrupt = 3
	UserTimerInterrupt          Interrupt = 4
	SupervisorTimerInterrupt    Interrupt = 5
	MachineTimerInterrupt       Interrupt = 7
	UserExternalInterrupt       Interrupt = 8
	SupervisorExternalInterrupt Interrupt = 9
	MachineExternalInterrupt    Interrupt = 11
)

func (i *Interrupt) ExceptionCode() uint64 {
	return uint64(*i)
}
