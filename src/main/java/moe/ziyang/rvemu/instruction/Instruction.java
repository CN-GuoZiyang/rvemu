package moe.ziyang.rvemu.instruction;

import moe.ziyang.rvemu.infra.Debug;
import moe.ziyang.rvemu.infra.EmuException;
import moe.ziyang.rvemu.infra.ExceptionEnum;

public abstract class Instruction {

    public abstract InstType getInstType();

    public int getOpcode() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRs1() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRs2() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getRd() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getFunct3() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public int getFunct7() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0;
    }

    public long getImm() {
        Debug.panic(new EmuException(ExceptionEnum.MISS_MATCH_INSTRUCTION));
        return 0L;
    }

}
