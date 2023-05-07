package moe.ziyang.rvemu;

import moe.ziyang.rvemu.device.Bus;
import moe.ziyang.rvemu.executor.Executor;
import moe.ziyang.rvemu.infra.Const;
import moe.ziyang.rvemu.infra.EmuException;
import moe.ziyang.rvemu.instruction.Instruction;
import moe.ziyang.rvemu.instruction.Parser;

public class Core {

    // PC 寄存器
    private long pc;
    // 通用寄存器组
    public final GPRs gprs;
    // 指令和数据总线
    private final Bus bus;
    // 指令执行
    private final Executor executor;

    public Core(byte[] code) {
        this.pc = Const.DRAM_BASE;
        gprs = new GPRs();
        bus = new Bus(code);
        executor = new Executor(this);
    }

    public void run() {
        // 执行循环：取指、译码、执行
        while(true) {
            // 1 取指
            int rawInst = this.fetch();

            // 2 译码
            Instruction inst;
            try {
                inst = Parser.parse(rawInst);
            } catch (EmuException e) {
                break;
            }

            // 3 执行
            executor.execute(inst);

            // 4 设置 pc
            pc += 4;

            if (pc == 0) {
                break;
            }

        }
    }

    private int fetch() {
        return (int)bus.load(pc, 32);
    }

    public long getPc() {
        return pc;
    }

}
