package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.instruction.InstType;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Map;

public class Executor {

    private Map<InstType, TypeExecutor> executorMap;

    public Executor(Core core) {
        executorMap = Map.of(
                InstType.IType, new IExecutor(core),
                InstType.RType, new RExecutor(core)
        );
    }

    public void execute(Instruction inst) {
        executorMap.get(inst.getInstType()).execute(inst);
    }

}
