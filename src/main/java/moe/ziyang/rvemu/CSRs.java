package moe.ziyang.rvemu;

import moe.ziyang.rvemu.infra.Const;

public class CSRs {

    private final long[] regs;

    public CSRs() {
        regs = new long[Const.CSR_SIZE];
    }

}
