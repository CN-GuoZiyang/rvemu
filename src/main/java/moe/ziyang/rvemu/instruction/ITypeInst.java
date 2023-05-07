package moe.ziyang.rvemu.instruction;

public class ITypeInst extends Instruction {

    private final int opcode;
    private int rd;
    private int rs1;
    private int funct3;
    private int imm;

    public ITypeInst(int opcode) {
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rd = (rawInst >>> 7) & 0x1f;
        int rs1 = (rawInst >>> 15) & 0x1f;
        int funct3 = (rawInst >>> 13) & 0x7;
        int imm = (rawInst & 0xfff00000) >> 20;
        return new ITypeInst(opcode).rd(rd).rs1(rs1).funct3(funct3).imm(imm);
    }

    @Override
    public InstType getInstType() {
        return InstType.IType;
    }

    public ITypeInst rd(int rd) {
        this.rd = rd;
        return this;
    }

    public ITypeInst rs1(int rs1) {
        this.rs1 = rs1;
        return this;
    }

    public ITypeInst funct3(int funct3) {
        this.funct3 = funct3;
        return this;
    }

    public ITypeInst imm(int imm) {
        this.imm = imm;
        return this;
    }

    @Override
    public int getOpcode() {
        return opcode;
    }

    @Override
    public int getRs1() {
        return rs1;
    }

    @Override
    public int getRd() {
        return rd;
    }

    @Override
    public int getFunct3() {
        return funct3;
    }

    @Override
    public int getImm() {
        return imm;
    }

}
