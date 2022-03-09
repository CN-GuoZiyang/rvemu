package rvemu

import (
	"fmt"
	"strings"
)

type XRegisters interface {
	Read(index uint64) uint64
	Write(index, value uint64)
}

type xRegisters struct {
	// 32个通用寄存器
	xregs [REGISTER_COUNT]uint64
}

func NewXRegisters() XRegisters {
	var xregs [REGISTER_COUNT]uint64
	// sp 寄存器默认指向 DRAM 顶部
	xregs[2] = DRAM_BASE + DRAM_SIZE
	// riscv-pk
	// https://github.com/riscv-software-src/riscv-pk/blob/master/machine/mentry.S#L233-L235
	// #ifdef CUSTOM_DTS
	//  csrr a0, mhartid
	//  la a1, dtb_start
	//#endif
	//  j init_first_hart
	xregs[10] = 0
	xregs[11] = POINTER_TO_DTB
	return &xRegisters{xregs: xregs}
}

func (x *xRegisters) Read(index uint64) uint64 {
	return x.xregs[index]
}

func (x *xRegisters) Write(index, value uint64) {
	if index != 0 {
		x.xregs[index] = value
	}
}

func (x *xRegisters) String() string {
	abi := []string{
		"zero", " ra ", " sp ", " gp ", " tp ", " t0 ", " t1 ", " t2 ", " s0 ", " s1 ", " a0 ",
		" a1 ", " a2 ", " a3 ", " a4 ", " a5 ", " a6 ", " a7 ", " s2 ", " s3 ", " s4 ", " s5 ",
		" s6 ", " s7 ", " s8 ", " s9 ", " s10", " s11", " t3 ", " t4 ", " t5 ", " t6 ",
	}
	var builder strings.Builder
	for i := 0; i < REGISTER_COUNT; i += 4 {
		builder.WriteString("\n")
		builder.WriteString(
			fmt.Sprintf(
				"x%02d(%v)=%#18x x%02d(%v)=%#18x x%02d(%v)=%#18x x%02d(%v)=%#18x",
				i, abi[i], x.Read(uint64(i)),
				i+1, abi[i+1], x.Read(uint64(i+1)),
				i+2, abi[i+2], x.Read(uint64(i+2)),
				i+3, abi[i+3], x.Read(uint64(i+3)),
			))
	}
	return builder.String()
}

type FRegisters interface {
	Read(index uint64) float64
	Write(index uint64, value float64)
}

type fRegisters struct {
	// 32个浮点寄存器
	fregs [REGISTER_COUNT]float64
}

func NewFRegisters() FRegisters {
	return &fRegisters{fregs: [REGISTER_COUNT]float64{}}
}

func (f *fRegisters) Read(index uint64) float64 {
	return f.fregs[index]
}

func (f *fRegisters) Write(index uint64, value float64) {
	f.fregs[index] = value
}

func (f *fRegisters) String() string {
	abi := []string{
		// ft0-7: FP temporaries
		" ft0", " ft1", " ft2", " ft3", " ft4", " ft5", " ft6", " ft7",
		// fs0-1: FP saved registers
		" fs0", " fs1", // fa0-1: FP arguments/return values
		" fa0", " fa1", // fa2–7: FP arguments
		" fa2", " fa3", " fa4", " fa5", " fa6", " fa7",
		// fs2–11: FP saved registers
		" fs2", " fs3", " fs4", " fs5", " fs6", " fs7", " fs8", " fs9", "fs10", "fs11",
		// ft8–11: FP temporaries
		" ft8", " ft9", "ft10", "ft11",
	}
	var builder strings.Builder
	for i := 0; i < REGISTER_COUNT; i += 4 {
		builder.WriteString("\n")
		builder.WriteString(
			fmt.Sprintf(
				"x%02d(%v)=%18.8f x%02d(%v)=%18.8f x%02d(%v)=%18.8f x%02d(%v)=%18.8f",
				i, abi[i], f.Read(uint64(i)),
				i+1, abi[i+1], f.Read(uint64(i+1)),
				i+2, abi[i+2], f.Read(uint64(i+2)),
				i+3, abi[i+3], f.Read(uint64(i+3)),
			))
	}
	return builder.String()
}
