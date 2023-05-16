package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class JExecutor implements TypeExecutor {

    private final Core core;

    public JExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x6f -> {
                // JAL
                core.gprs.write(inst.getRd(), core.getPc() + 4L);
                core.setPc(inst.getImm() - 4L);
            }
        }
    }
}
