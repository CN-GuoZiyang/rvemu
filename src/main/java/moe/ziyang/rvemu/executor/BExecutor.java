package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class BExecutor implements TypeExecutor {

    private final Core core;

    public BExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x63 -> {
                switch (inst.getFunct3()) {
                    case 0x0:
                        // BEQ
                        if (core.gprs.read(inst.getRs1()) == core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                        break;
                    case 0x1:
                        // BNE
                        if (core.gprs.read(inst.getRs1()) != core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                        break;
                }
            }
        }
    }
}
