package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.instruction.Instruction;

public interface TypeExecutor {
    void execute(Instruction inst);
}
