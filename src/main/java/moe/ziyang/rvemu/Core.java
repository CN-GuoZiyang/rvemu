package moe.ziyang.rvemu;

import moe.ziyang.rvemu.device.Bus;
import moe.ziyang.rvemu.executor.Executor;
import moe.ziyang.rvemu.infra.Const;
import moe.ziyang.rvemu.infra.EmuError;
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
            } catch (EmuError e) {
                break;
            }

            // 3 执行
            executor.execute(inst);

            // 4 pc自增
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

    public void setPc(long pc) {
        this.pc = pc;
    }

    public long load(long address, int size) {
        return this.bus.load(address, size);
    }

    public void store(long address, int size, long value) {
        this.bus.store(address, size, value);
    }

}
