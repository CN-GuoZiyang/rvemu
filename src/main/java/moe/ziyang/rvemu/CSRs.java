package moe.ziyang.rvemu;

import moe.ziyang.rvemu.infra.Const;

public class CSRs {

    private final long[] regs;

    public CSRs() {
        regs = new long[Const.CSR_SIZE];
    }

    public static final int fflags = 0x001;
    public static final int frm = 0x002;
    public static final int fcsr = 0x003;
    public static final int cycle = 0xC00;
    public static final int time = 0xC01;
    public static final int instret = 0xC02;
    public static final int sstatus = 0x100;
    public static final int sie = 0x104;
    public static final int stvec = 0x105;
    public static final int scounteren = 0x106;
    public static final int senvcfg = 0x10A;
    public static final int sscratch = 0x140;
    public static final int sepc = 0x141;
    public static final int scause = 0x142;
    public static final int stval = 0x143;
    public static final int sip = 0x144;
    public static final int satp = 0x180;
    public static final int scontext = 0x5A8;
    public static final int mvendorid = 0xF11;
    public static final int marchid = 0xF12;
    public static final int mimpid = 0xF13;
    public static final int mhartid = 0xF14;
    public static final int mconfigptr = 0xF15;
    public static final int mstatus = 0x300;
    public static final int misa = 0x301;
    public static final int medeleg = 0x302;
    public static final int mideleg = 0x303;
    public static final int mie = 0x304;
    public static final int mtvec = 0x305;
    public static final int mcounteren = 0x306;
    public static final int mscratch = 0x340;
    public static final int mepc = 0x341;
    public static final int mcause = 0x342;
    public static final int mtval = 0x343;
    public static final int mip = 0x344;
    public static final int mtinst = 0x34A;
    public static final int mtval2 = 0x34B;
    public static final int menvcfg = 0x30A;
    public static final int mseccfg = 0x747;
    public static final int mcycle = 0xB00;
    public static final int minstret = 0xB02;
    public static final int mcountinhibit = 0x320;
}
