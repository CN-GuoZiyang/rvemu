package moe.ziyang.rvemu.instruction;

public class JTypeInst extends Instruction {

    private int opcode;
    private int rd;
    private int imm;

    public JTypeInst(int opcode) {
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rd = (rawInst >>> 7) & 0x1f;
        int imm = ((rawInst & 0x80000000) >> 11)
                | (rawInst & 0xff000)
                | (rawInst >>> 9) & 0x800
                | (rawInst >>> 20) & 0x7fe;
        return new JTypeInst(opcode).rd(rd).imm(imm);
    }

    @Override
    public InstType getInstType() {
        return InstType.JType;
    }

    private JTypeInst rd(int rd) {
        this.rd = rd;
        return this;
    }

    private JTypeInst imm(int imm) {
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
