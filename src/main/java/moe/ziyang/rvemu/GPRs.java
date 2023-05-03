package moe.ziyang.rvemu;

import moe.ziyang.rvemu.infra.Const;

public class GPRs {

    long[] regs;

    public GPRs() {
        regs = new long[Const.GPR_NUM];
        // x2，即 sp 栈顶寄存器，指向物理内存最高处
        regs[2] = Const.DRAM_SIZE;
    }

    public long read(int index) {
        if (index == 0) {
            // x0 恒为 0
            return 0L;
        }
        if (index >= Const.GPR_NUM) {
            // 啥也读不到（或许可以 panic？）
            return 0L;
        }
        return regs[index];
    }

    public void write(int index, long value) {
        if (index == 0) {
            // x0 恒为 0
            return;
        }
        if (index >= Const.GPR_NUM) {
            // 啥也不用干（或许可以 panic？）
            return;
        }
        regs[index] = value;
    }

}
