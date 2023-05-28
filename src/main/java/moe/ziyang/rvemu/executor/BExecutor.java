package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Optional;

public class BExecutor implements TypeExecutor {

    private final Core core;

    public BExecutor(Core core) {
        this.core = core;
    }

    @Override
    public Optional<Exception> execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x63 -> {
                switch (inst.getFunct3()) {
                    case 0x0 -> {
                        // BEQ
                        if (core.gprs.read(inst.getRs1()) == core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    case 0x1 -> {
                        // BNE
                        if (core.gprs.read(inst.getRs1()) != core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    case 0x4 -> {
                        // BLT
                        if (core.gprs.read(inst.getRs1()) < core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    case 0x5 -> {
                        // BGE
                        if (core.gprs.read(inst.getRs1()) >= core.gprs.read(inst.getRs2())) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    case 0x6 -> {
                        // BLTU
                        if (Long.compareUnsigned(core.gprs.read(inst.getRs1()), core.gprs.read(inst.getRs2())) < 0) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    case 0x7 -> {
                        // BGEU
                        if (Long.compareUnsigned(core.gprs.read(inst.getRs1()), core.gprs.read(inst.getRs2())) >= 0) {
                            core.setPc(core.getPc() + inst.getImm() - 4L);
                        }
                    }
                    default -> {
                        return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
                    }
                }
            }
            default -> {
                return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
            }
        }
        return Optional.empty();
    }
}
