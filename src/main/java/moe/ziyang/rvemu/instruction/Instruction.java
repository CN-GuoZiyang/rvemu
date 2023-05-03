package moe.ziyang.rvemu.instruction;

import moe.ziyang.rvemu.Core;

public interface Instruction {

    static Instruction parse(byte[] rawInst) {
        return null;
    }

}
