package moe.ziyang.rvemu.instruction;

import moe.ziyang.rvemu.infra.EmuException;
import moe.ziyang.rvemu.infra.ExceptionEnum;

import java.util.Map;

public class Parser {

    public static Instruction parse(int rawInst) {
        int opcode = rawInst & 0x7f;
        if (!instTypeMap.containsKey(opcode)) {
            throw new EmuException(ExceptionEnum.UNSUPPORTED_INSTRUCTION);
        }
        switch (instTypeMap.get(opcode)) {
            case IType -> {
                return ITypeInst.build(rawInst);
            }
            case RType -> {
                return RTypeInst.build(rawInst);
            }
        }
        return null;
    }

    public static Map<Integer, InstType> instTypeMap = Map.of(
            0x13, InstType.IType,    // addi
            0x33, InstType.RType        // add
    );

}
