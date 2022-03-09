package rvemu

type Emulator interface {
	start()
	initDram(data []byte)
	initDisk(data []byte)
	initPc(pc uint64)
}

type emulator struct {
	cpu     Cpu
	isDebug bool
}

func NewEmulator() Emulator {
	return &emulator{
		cpu:     NewCpu(),
		isDebug: false,
	}
}

func (e *emulator) initDram(data []byte) {

}

func (e *emulator) initDisk(data []byte) {

}

func (e *emulator) initPc(pc uint64) {

}

func (e *emulator) start() {
	// TODO debug 模式兼容
	for {

	}
}
