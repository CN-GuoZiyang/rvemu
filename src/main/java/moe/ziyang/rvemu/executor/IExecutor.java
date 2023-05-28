package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Optional;

public class IExecutor implements TypeExecutor {

    private final Core core;

    public IExecutor(Core core) {
        this.core = core;
    }

    @Override
    public Optional<Exception> execute(Instruction inst) {
        switch (inst.getOpcode()) {
            case 0x03 -> {
                long address = inst.getImm() + core.gprs.read(inst.getRs1());
                switch (inst.getFunct3()) {
                    case 0x0 -> {
                        // LB
                        // 此处强转为 byte 类型以进行符号位扩展
                        byte b = (byte) core.load(address, 8);
                        core.gprs.write(inst.getRd(), b);
                    }
                    case 0x1 -> {
                        // LH
                        short h = (byte) core.load(address, 16);
                        core.gprs.write(inst.getRd(), h);
                    }
                    case 0x2 -> {
                        // LW
                        int w = (int) core.load(address, 32);
                        core.gprs.write(inst.getRd(), w);
                    }
                    case 0x3 ->
                        // LD
                            core.gprs.write(inst.getRd(), core.load(address, 64));
                    case 0x4 ->
                        // LBU
                            core.gprs.write(inst.getRd(), core.load(address, 8));
                    case 0x5 ->
                        // LHU
                            core.gprs.write(inst.getRd(), core.load(address, 16));
                    case 0x6 ->
                        // LWU
                            core.gprs.write(inst.getRd(), core.load(address, 32));
                    default -> {
                        return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
                    }
                }
            }
            case 0x0f -> {
                int funct3 = inst.getFunct3();
                switch (funct3) {
                    case 0x0:
                        // FENCE
                        break;
                    case 0x1:
                        // FENCE.I
                        break;
                    default:
                        return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
                }
            }
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
                    default:
                        return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
                }
            }
            case 0x67 -> {
                // JALR
                core.gprs.write(inst.getRd(), core.getPc() + 4L);
                long target = (inst.getImm() + core.gprs.read(inst.getRs1())) & ~0x1L;
                core.setPc(target - 4L);
            }
            default -> {
                return Optional.of(Exception.IllegalInstruction.setValue(inst.getRawInst()));
            }
        }
        return Optional.empty();
    }
}
