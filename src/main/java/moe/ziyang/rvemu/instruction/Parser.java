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
            case UType -> {
                return UTypeInst.build(rawInst);
            }
            case JType -> {
                return JTypeInst.build(rawInst);
            }
            case BType -> {
                return BTypeInst.build(rawInst);
            }
        }
        return null;
    }

    public static Map<Integer, InstType> instTypeMap = Map.of(
            0x13, InstType.IType,    // ADDI/SLTI/SLTIU/ANDI/ORI/XORI/SLLI/SRLI/SRAI
            0x17, InstType.UType,       // AUIPC
            0x33, InstType.RType,       // ADD/SUB/SLT/SLTU/AND/OR/XOR/SLL/SRL/SRA
            0x37, InstType.UType,       // LUI
            0x63, InstType.BType,       // BEQ/BNE
            0x67, InstType.IType,       // JALR
            0x6f, InstType.JType       // JAL
    );

}
