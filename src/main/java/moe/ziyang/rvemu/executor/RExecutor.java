package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class RExecutor implements TypeExecutor {

    private Core core;

    public RExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x33:
                core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) + core.gprs.read(inst.getRs2()));
        }
    }
}
