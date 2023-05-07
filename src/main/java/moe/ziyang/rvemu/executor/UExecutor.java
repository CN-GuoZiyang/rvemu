package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class UExecutor implements TypeExecutor {

    private Core core;

    public UExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x17:
                // AUIPC
                core.gprs.write(inst.getRd(), core.getPc() + inst.getImm());
                break;
            case 0x37:
                // LUI
                core.gprs.write(inst.getRd(), inst.getImm());
                break;
        }
    }
}
