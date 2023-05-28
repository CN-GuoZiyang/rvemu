package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Optional;

public interface TypeExecutor {
    Optional<Exception> execute(Instruction inst);
}
