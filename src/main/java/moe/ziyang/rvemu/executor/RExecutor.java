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
                switch (inst.getFunct3()) {
                    case 0x0:
                        // ADD/SUB
                        int funct7 = inst.getFunct7();
                        core.gprs.write(inst.getRd(),
                                funct7 == 0 ? core.gprs.read(inst.getRs1()) + core.gprs.read(inst.getRs2())
                                            : core.gprs.read(inst.getRs1()) - core.gprs.read(inst.getRs2())
                        );
                        break;
                    case 0x1:
                        // SLL
                        long shift = core.gprs.read(inst.getRs2()) & 0x1fL;
                        core.gprs.write(inst.getRd(), core.gprs.read(inst.getRs1()) << shift);
                        break;
                    case 0x2:
                        // SLT
                        core.gprs.write(inst.getRd(), inst.getRs1() < inst.getRs2() ? 1L : 0L);
                        break;
                    case 0x3:
                        // SLTU
                        core.gprs.write(inst.getRd(), Integer.compareUnsigned(inst.getRs1(), inst.getRs2()) < 0 ? 1L : 0L);
                        break;
                    case 0x4:
                        // XOR
                        core.gprs.write(inst.getRd(), inst.getRs1() ^ inst.getRs2());
                        break;
                    case 0x5:
                        // SRL/SRA
                        shift = core.gprs.read(inst.getRs2()) & 0x1fL;
                        int shiftType = inst.getFunct7();
                        core.gprs.write(inst.getRd(), shiftType == 0 ? core.gprs.read(inst.getRs1()) >>> shift
                                                                     : core.gprs.read(inst.getRs1()) >> shift);
                        break;
                    case 0x6:
                        // OR
                        core.gprs.write(inst.getRd(), inst.getRs1() | inst.getRs2());
                        break;
                    case 0x7:
                        // AND
                        core.gprs.write(inst.getRd(), inst.getRs1() & inst.getRs2());
                        break;
                }
        }
    }
}
