package rvemu

const (
	REGISTER_COUNT = 32   // 通用寄存器个数
	PAGE_SIZE      = 4096 // 虚拟/物理页大小

	POINTER_TO_DTB = 0x1020 // 设备树地址

	// 不同位宽的数据占用的bit数
	BYTE       = 8
	HALFWORD   = 16
	WORD       = 32
	DOUBLEWORD = 64

	DRAM_BASE = 0x8000_0000
	DRAM_SIZE = 1024 * 1024 * 1024
)
