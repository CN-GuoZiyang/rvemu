package moe.ziyang.rvemu.instruction;

public class RTypeInst extends Instruction {

    private final int opcode;
    private int rd;
    private int rs1;
    private int rs2;
    private int funct3;
    private int funct7;

    public RTypeInst(int rawInst, int opcode) {
        super(rawInst);
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rd = (rawInst >>> 7) & 0x1f;
        int rs1 = (rawInst >>> 15) & 0x1f;
        int rs2 = (rawInst >>> 20) & 0x1f;
        int funct3 = (rawInst >>> 12) & 0x7;
        int funct7 = (rawInst & 0xfe000000) >>> 25;
        return new RTypeInst(rawInst, opcode).rd(rd).rs1(rs1).rs2(rs2).funct3(funct3).funct7(funct7);
    }

    @Override
    public InstType getInstType() {
        return InstType.RType;
    }

    public RTypeInst rd(int rd) {
        this.rd = rd;
        return this;
    }

    public RTypeInst rs1(int rs1) {
        this.rs1 = rs1;
        return this;
    }

    public RTypeInst rs2(int rs2) {
        this.rs2 = rs2;
        return this;
    }

    public RTypeInst funct3(int funct3) {
        this.funct3 = funct3;
        return this;
    }

    public RTypeInst funct7(int funct7) {
        this.funct7 = funct7;
        return this;
    }

    @Override
    public int getOpcode() {
        return opcode;
    }

    public int getRd() {
        return rd;
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
    public int getFunct7() {
        return funct7;
    }

}
