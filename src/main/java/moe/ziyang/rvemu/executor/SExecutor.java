package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class SExecutor implements TypeExecutor {

    private final Core core;

    public SExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x23 -> {
                long address = inst.getImm() + core.gprs.read(inst.getRs1());
                switch (inst.getFunct3()) {
                    case 0x0 -> {
                        // SB
                        long value = (byte)core.gprs.read(inst.getRs2());
                        core.store(address, 8, value);
                    }
                    case 0x1 -> {
                        // SH
                        long value = (short)core.gprs.read(inst.getRs2());
                        core.store(address, 16, value);
                    }
                    case 0x2 -> {
                        // SW
                        long value = (int)core.gprs.read(inst.getRs2());
                        core.store(address, 32, value);
                    }
                    case 0x3 -> {
                        // SD
                        long value = core.gprs.read(inst.getRs2());
                        core.store(address, 64, value);
                    }
                }
            }
        }
    }
}
