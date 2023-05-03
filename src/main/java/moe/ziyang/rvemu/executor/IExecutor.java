package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class IExecutor implements TypeExecutor {

    private Core core;

    public IExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x13:
                long imm = inst.getImm();
                core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) + imm);
        }
    }
}
