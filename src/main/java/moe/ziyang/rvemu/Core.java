package moe.ziyang.rvemu;

import moe.ziyang.rvemu.device.Bus;
import moe.ziyang.rvemu.executor.Executor;
import moe.ziyang.rvemu.infra.Const;
import moe.ziyang.rvemu.infra.EmuError;
import moe.ziyang.rvemu.instruction.Instruction;
import moe.ziyang.rvemu.instruction.Parser;

import java.util.Optional;

public class Core {

    // PC 寄存器
    private long pc;
    // 通用寄存器组
    public final GPRs gprs;
    // 控制寄存器组
    public final CSRs csrs;
    // 指令和数据总线
    private final Bus bus;
    // 指令执行
    private final Executor executor;

    public Core(byte[] code) {
        this.pc = Const.DRAM_BASE;
        gprs = new GPRs();
        csrs = new CSRs();
        bus = new Bus(code);
        executor = new Executor(this);
    }

    public void run() {
        // 执行循环：取指、译码、执行
        while(true) {
            Optional<Exception> exception = normalCycle();
            if (!exception.isEmpty()) {
                TrapType trapType = exception.get().takeTrap(this);
                if (trapType == TrapType.Fatal) {
                    break;
                }
            }
        }
    }

    private Optional<Exception> normalCycle() {
        // 1 取指
        int rawInst = this.fetch();

        // 2 译码
        Instruction inst;
        try {
            inst = Parser.parse(rawInst);
        } catch (EmuError e) {
            return Optional.empty();
        }
        if (inst == null) {
            return Optional.empty();
        }

        // 3 执行
        Optional<Exception> exception = executor.execute(inst);
        if (!exception.isEmpty()) {
            return exception;
        }

        // 4 pc自增
        pc += 4;
        return Optional.empty();
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
