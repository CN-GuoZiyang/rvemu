package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Optional;

public class UExecutor implements TypeExecutor {

    private final Core core;

    public UExecutor(Core core) {
        this.core = core;
    }

    @Override
    public Optional<Exception> execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x17 ->
                // AUIPC
                    core.gprs.write(inst.getRd(), core.getPc() + ((inst.getImm() << 32) >>> 32));
            case 0x37 ->
                // LUI
                    core.gprs.write(inst.getRd(), (inst.getImm() << 32) >>> 32);
            default -> {
                return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
            }
        }
        return Optional.empty();
    }
}
