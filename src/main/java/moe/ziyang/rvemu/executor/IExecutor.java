package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.Instruction;

public class IExecutor implements TypeExecutor {

    private final Core core;

    public IExecutor(Core core) {
        this.core = core;
    }

    @Override
    public void execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x13 -> {
                long imm = inst.getImm();
                switch (inst.getFunct3()) {
                    case 0x0:
                        // ADDI
                        core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) + imm);
                        break;
                    case 0x1:
                        // SLLI
                        int shift = (int) (imm & 0x3fL);
                        core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) << shift);
                        break;
                    case 0x2:
                        // SLTI
                        core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) < imm ? 1L : 0L);
                        break;
                    case 0x3:
                        // SLTIU
                        core.gprs.write(inst.getRd(), Long.compareUnsigned(core.gprs.read(inst.getRs1()), imm) < 0 ? 1L : 0L);
                        break;
                    case 0x4:
                        // XORI
                        core.gprs.write(inst.getRd(), imm ^ core.gprs.read(inst.getRs1()));
                        break;
                    case 0x5:
                        // SRLI/SRAI
                        shift = (int) (imm & 0x3fL);
                        int shiftType = (int) (imm >> 4);
                        core.gprs.write(inst.getRd(),
                                shiftType == 0 ? core.gprs.read(inst.getRs1()) >> shift : core.gprs.read(inst.getRs1()) >>> shift);
                        break;
                    case 0x6:
                        // ORI
                        core.gprs.write(inst.getRd(), imm | core.gprs.read(inst.getRs1()));
                        break;
                    case 0x7:
                        // ANDI
                        core.gprs.write(inst.getRd(), imm & core.gprs.read(inst.getRs1()));
                        break;
                }
            }
            case 0x67 -> {
                // JALR
                core.gprs.write(inst.getRd(), core.getPc() + 4L);
                long target = (inst.getImm() + core.gprs.read(inst.getRs1())) & ~0x1L;
                core.setPc(target - 4L);
            }
        }
    }
}
