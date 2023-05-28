package moe.ziyang.rvemu.instruction;

import moe.ziyang.rvemu.infra.Debug;
import moe.ziyang.rvemu.infra.EmuError;
import moe.ziyang.rvemu.infra.ErrorEnum;

public abstract class Instruction {

    public abstract InstType getInstType();

    private int rawInst;

    public Instruction(int rawInst) {
        this.rawInst = rawInst;
    }

    public int getRawInst() {
        return rawInst;
    }

    public int getOpcode() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRs1() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRs2() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRd() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getFunct3() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getFunct7() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public long getImm() {
        Debug.panic(new EmuError(ErrorEnum.MISS_MATCH_INSTRUCTION));
        return 0L;
    }

}
