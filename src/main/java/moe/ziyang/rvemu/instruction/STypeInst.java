package moe.ziyang.rvemu.instruction;

public class STypeInst extends Instruction {

    private final int opcode;
    private int rs1;
    private int rs2;
    private int funct3;
    private long imm;

    public STypeInst(int rawInst, int opcode) {
        super(rawInst);
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rs1 = (rawInst >>> 15) & 0x1f;
        int rs2 = (rawInst >>> 20) & 0x1f;
        int funct3 = (rawInst >>> 12) & 0x7;
        long imm = ((rawInst & 0xfe000000) >> 20) | ((rawInst >> 7) & 0x1f);
        return new STypeInst(rawInst, opcode).rs1(rs1).rs2(rs2).funct3(funct3).imm(imm);
    }

    @Override
    public InstType getInstType() {
        return InstType.SType;
    }

    public STypeInst rs1(int rs1) {
        this.rs1 = rs1;
        return this;
    }

    public STypeInst rs2(int rs2) {
        this.rs2 = rs2;
        return this;
    }

    public STypeInst funct3(int funct3) {
        this.funct3 = funct3;
        return this;
    }

    public STypeInst imm(long imm) {
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
    public int getRs2() {
        return rs2;
    }

    @Override
    public int getFunct3() {
        return funct3;
    }

    @Override
    public long getImm() {
        return imm;
    }

}
