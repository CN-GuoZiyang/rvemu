package moe.ziyang.rvemu;

public enum Exception {

    InstructionAddressMisaligned(0, TrapType.Fatal),
    InstructionAccessFault(1, TrapType.Fatal),
    IllegalInstruction(2, TrapType.Invisible),
    Breakpoint(3, TrapType.Requested),
    LoadAddressMisaligned(4, TrapType.Fatal),
    LoadAccessFault(5, TrapType.Fatal),
    StoreAMOAddressMisaligned(6, TrapType.Fatal),
    StoreAMOAccessFault(7, TrapType.Fatal),
    EnvironmentCallFromUMode(8, TrapType.Requested),
    EnvironmentCallFromSMode(9, TrapType.Requested),
    EnvironmentCallFromMMode(11, TrapType.Requested),
    InstructionPageFault(12, TrapType.Invisible),
    LoadPageFault(13, TrapType.Invisible),
    StoreAMOPageFault(15, TrapType.Invisible);


    private final long code;
    private final TrapType trapType;
    private long value;

    Exception(long code, TrapType trapType) {
        this.code = code;
        this.trapType = trapType;
        this.value = 0;
    }

    public Exception setValue(long value) {
        this.value = value;
        return this;
    }

    public TrapType takeTrap(Core core) {
        return TrapType.Requested;
    }

}
