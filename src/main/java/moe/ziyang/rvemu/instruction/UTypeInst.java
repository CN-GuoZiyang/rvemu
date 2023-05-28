package moe.ziyang.rvemu.instruction;

public class UTypeInst extends Instruction {

    private int opcode;
    private int rd;
    private long imm;

    public UTypeInst(int rawInst, int opcode) {
        super(rawInst);
        this.opcode = opcode;
    }

    public static Instruction build(int rawInst) {
        int opcode = rawInst & 0x7f;
        int rd = (rawInst >>> 7) & 0x1f;
        long imm = rawInst & 0xfffff000L;
        return new UTypeInst(rawInst, opcode).rd(rd).imm(imm);
    }

    @Override
    public InstType getInstType() {
        return InstType.UType;
    }

    private UTypeInst rd(int rd) {
        this.rd = rd;
        return this;
    }

    private UTypeInst imm(long imm) {
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
    public long getImm() {
        return imm;
    }

}
