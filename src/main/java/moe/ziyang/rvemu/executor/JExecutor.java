package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Optional;

public class JExecutor implements TypeExecutor {

    private final Core core;

    public JExecutor(Core core) {
        this.core = core;
    }

    @Override
    public Optional<Exception> execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x6f -> {
                // JAL
                core.gprs.write(inst.getRd(), core.getPc() + 4L);
                core.setPc(inst.getImm() - 4L);
            }
            default -> {
                return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
            }
        }
        return Optional.empty();
    }
}
