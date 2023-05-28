package moe.ziyang.rvemu.executor;

import moe.ziyang.rvemu.Core;
import moe.ziyang.rvemu.Exception;
import moe.ziyang.rvemu.instruction.InstType;
import moe.ziyang.rvemu.instruction.Instruction;

import java.util.Map;
import java.util.Optional;

public class Executor {

    private Map<InstType, TypeExecutor> executorMap;

    public Executor(Core core) {
        executorMap = Map.of(
                InstType.IType, new IExecutor(core),
                InstType.RType, new RExecutor(core),
                InstType.UType, new UExecutor(core),
                InstType.JType, new JExecutor(core),
                InstType.BType, new BExecutor(core),
                InstType.SType, new SExecutor(core)
        );
    }

    public Optional<Exception> execute(Instruction inst) {
        return executorMap.get(inst.getInstType()).execute(inst);
    }

}
