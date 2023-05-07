package moe.ziyang.rvemu.instruction;

public class UTypeInst extends Instruction {

    private int opcode;
    private int rd;
    private int imm;

    public UTypeInst(int opcode) {
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rd = (rawInst >>> 7) & 0x1f;
        int imm = (rawInst >> 12) << 12;
        return new UTypeInst(opcode).rd(rd).imm(imm);
    }

    @Override
    public InstType getInstType() {
        return InstType.UType;
    }

    private UTypeInst rd(int rd) {
        this.rd = rd;
        return this;
    }

    private UTypeInst imm(int imm) {
        this.imm = imm;
        return this;
    }

    @Override
    public int getOpcode() {
        return opcode;
    }

    @Override
    public int getRd() {
        return rd;
    }

    @Override
    public int getImm() {
        return imm;
    }

}
