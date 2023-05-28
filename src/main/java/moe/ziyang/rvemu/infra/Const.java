package moe.ziyang.rvemu.infra;

public class Const {

    // 通用寄存器个数
    public static final int GPR_NUM = 32;
    // DRAM 大小
    public static final int DRAM_SIZE = 1024 * 1024 * 512;
    // DRAM 基地址
    public static final long DRAM_BASE = 0x80000000L;
    // CSR 寄存器空间大小
    public static final int CSR_SIZE = 4096;

}
