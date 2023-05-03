package moe.ziyang.rvemu;

import moe.ziyang.rvemu.device.Bus;
import moe.ziyang.rvemu.instruction.Executor;
import moe.ziyang.rvemu.instruction.Instruction;

public class Core {

    // PC 寄存器
    private long pc;
    // 通用寄存器组
    private GPRs gprs;
    // 指令和数据总线
    private Bus bus;
    // 指令执行
    private Executor executor;

    public Core() {
        this.pc = 0L;
        gprs = new GPRs();

        executor = new Executor(this);
    }

    public void run() {
        // 执行循环：取指、译码、执行
        while(true) {
            // 1 取指
            byte[] rawInst = this.fetch();

            // 1.5 pc 自增
            pc += 4;

            // 2 译码
            Instruction inst = Instruction.parse(rawInst);

            // 3 执行
            executor.execute(inst);

            if (pc == 0) {
                break;
            }

        }
    }

    private byte[] fetch() {
        return null;
    }

}
