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

	// 总线设备地址范围
	MROM_BASE   uint64 = 0x1000
	MROM_END    uint64 = MROM_BASE + 0xf000
	CLINT_BASE  uint64 = 0x200_0000
	CLINT_END   uint64 = CLINT_BASE + 0x1000
	PLIC_BASE   uint64 = 0xc00_0000
	PLIC_END    uint64 = PLIC_BASE + 0x208000
	UART_BASE   uint64 = 0x1000_0000
	UART_SIZE   uint64 = 0x100
	UART_END    uint64 = UART_BASE + UART_SIZE
	VIRTIO_BASE uint64 = 0x1000_1000
	VIRTIO_END  uint64 = VIRTIO_BASE + 0x1000
	DRAM_BASE   uint64 = 0x8000_0000
	DRAM_SIZE   uint64 = 1024 * 1024 * 1024
	DRAM_END    uint64 = DRAM_BASE + DRAM_SIZE
)
