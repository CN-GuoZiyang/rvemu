package moe.ziyang.rvemu;

import junit.framework.TestCase;
import moe.ziyang.rvemu.infra.Const;

public class RegisterImmTest extends TestCase {

    public void testAddi() throws java.lang.Exception {
        RiscvCmdTest addiTest = new RiscvCmdTest("test_addi");
        addiTest.compile("""
addi x29, x0, 5
addi x30, x0, 37
addi x31, x0, -6
""");
        addiTest.run();
        addiTest.testAssertion(new GPRAssertion(29, 5L));
        addiTest.testAssertion(new GPRAssertion(30, 37L));
        addiTest.testAssertion(new GPRAssertion(31, -6L));
    }

    public void testSlti() throws java.lang.Exception {
        RiscvCmdTest sltiTest = new RiscvCmdTest("test_slti");
        sltiTest.compile("""
addi x28, x0, 5
addi x29, x0, 37
addi x30, x0, 42
addi x31, x0, -10
slti x3, x28, 4
slti x4, x29, 37
slti x5, x30, 60
slti x6, x31, -5
""");
        sltiTest.run();
        sltiTest.testAssertion(new GPRAssertion(28, 5));
        sltiTest.testAssertion(new GPRAssertion(29, 37L));
        sltiTest.testAssertion(new GPRAssertion(30, 42L));
        sltiTest.testAssertion(new GPRAssertion(31, -10L));
        sltiTest.testAssertion(new GPRAssertion(3, 0L));
        sltiTest.testAssertion(new GPRAssertion(4, 0L));
        sltiTest.testAssertion(new GPRAssertion(5, 1L));
        sltiTest.testAssertion(new GPRAssertion(6, 1L));
    }

    public void testSltiu() throws java.lang.Exception {
        RiscvCmdTest sltiuTest = new RiscvCmdTest("test_slti");
        sltiuTest.compile("""
addi x28, x0, 5
addi x29, x0, 37
addi x30, x0, 42
addi x31, x0, -10
addi x27, x0, -37
addi x26, x0, -54
sltiu x3, x28, 4
sltiu x4, x29, 37
sltiu x5, x30, 60
sltiu x6, x31, -5
sltiu x7, x27, -37
sltiu x8, x26, -56
sltiu x9, x28, -10
""");
        sltiuTest.run();
        sltiuTest.testAssertion(new GPRAssertion(28, 5));
        sltiuTest.testAssertion(new GPRAssertion(29, 37L));
        sltiuTest.testAssertion(new GPRAssertion(30, 42L));
        sltiuTest.testAssertion(new GPRAssertion(31, -10L));
        sltiuTest.testAssertion(new GPRAssertion(27, -37L));
        sltiuTest.testAssertion(new GPRAssertion(26, -54L));
        sltiuTest.testAssertion(new GPRAssertion(3, 0L));
        sltiuTest.testAssertion(new GPRAssertion(4, 0L));
        sltiuTest.testAssertion(new GPRAssertion(5, 1L));
        sltiuTest.testAssertion(new GPRAssertion(6, 1L));
        sltiuTest.testAssertion(new GPRAssertion(7, 0L));
        sltiuTest.testAssertion(new GPRAssertion(8, 0L));
        sltiuTest.testAssertion(new GPRAssertion(9, 1L));
    }

    public void testAndi() throws java.lang.Exception {
        RiscvCmdTest andiTest = new RiscvCmdTest("test_andi");
        andiTest.compile("""
addi x2, x0, 5
andi x3, x2, 37
addi x4, x0, -6
andi x5, x4, -10
addi x6, x0, -6
andi x7, x6, 37
""");
        andiTest.run();
        andiTest.testAssertion(new GPRAssertion(2, 5L));
        andiTest.testAssertion(new GPRAssertion(3, 5L&37L));
        andiTest.testAssertion(new GPRAssertion(4, -6L));
        andiTest.testAssertion(new GPRAssertion(5, -6L&-10L));
        andiTest.testAssertion(new GPRAssertion(6, -6L));
        andiTest.testAssertion(new GPRAssertion(7, -6&37L));
    }

    public void testOri() throws java.lang.Exception {
        RiscvCmdTest oriTest = new RiscvCmdTest("test_ori");
        oriTest.compile("""
addi x2, x0, 5
ori x3, x2, 37
addi x4, x0, -6
ori x5, x4, -10
addi x6, x0, -6
ori x7, x6, 37
""");
        oriTest.run();
        oriTest.testAssertion(new GPRAssertion(2, 5L));
        oriTest.testAssertion(new GPRAssertion(3, 5L|37L));
        oriTest.testAssertion(new GPRAssertion(4, -6L));
        oriTest.testAssertion(new GPRAssertion(5, -6L|-10L));
        oriTest.testAssertion(new GPRAssertion(6, -6L));
        oriTest.testAssertion(new GPRAssertion(7, -6L|37L));
    }

    public void testXori() throws java.lang.Exception {
        RiscvCmdTest xoriTest = new RiscvCmdTest("test_xori");
        xoriTest.compile("""
addi x2, x0, 5
xori x3, x2, 37
addi x4, x0, -6
xori x5, x4, -10
addi x6, x0, -6
xori x7, x6, 37
""");
        xoriTest.run();
        xoriTest.testAssertion(new GPRAssertion(2, 5L));
        xoriTest.testAssertion(new GPRAssertion(3, 5L^37L));
        xoriTest.testAssertion(new GPRAssertion(4, -6L));
        xoriTest.testAssertion(new GPRAssertion(5, -6L^-10L));
        xoriTest.testAssertion(new GPRAssertion(6, -6L));
        xoriTest.testAssertion(new GPRAssertion(7, -6L^37L));
    }

    public void testSLLI() throws java.lang.Exception {
        RiscvCmdTest slliTest = new RiscvCmdTest("test_slli");
        slliTest.compile("""
addi x2, x0, 5
slli x3, x2, 8
addi x4, x0, -6
slli x5, x4, 6
""");
        slliTest.run();
        slliTest.testAssertion(new GPRAssertion(2, 5L));
        slliTest.testAssertion(new GPRAssertion(3, 5L << 8));
        slliTest.testAssertion(new GPRAssertion(4, -6L));
        slliTest.testAssertion(new GPRAssertion(5, -6L << 6));
    }

    public void testSRLI() throws java.lang.Exception {
        RiscvCmdTest srliTest = new RiscvCmdTest("test_srli");
        srliTest.compile("""
addi x2, x0, 0x7FF
srli x3, x2, 8
addi x4, x0, 0x6FF
srli x5, x4, 4
""");
        srliTest.run();
        srliTest.testAssertion(new GPRAssertion(2, 0x7FFL));
        srliTest.testAssertion(new GPRAssertion(3, 0x7FFL >>> 8));
        srliTest.testAssertion(new GPRAssertion(4, 0x6FFL));
        srliTest.testAssertion(new GPRAssertion(5, 0x6FFL >>> 4));
    }

    public void testSRAI() throws java.lang.Exception {
        RiscvCmdTest sraiTest = new RiscvCmdTest("test_srai");
        sraiTest.compile("""
addi x2, x0, 0x7FF
srai x3, x2, 8
addi x4, x0, 0x6FF
srai x5, x4, 4
""");
        sraiTest.run();
        sraiTest.testAssertion(new GPRAssertion(2, 0x7FFL));
        sraiTest.testAssertion(new GPRAssertion(3, 0x7FFL >> 8));
        sraiTest.testAssertion(new GPRAssertion(4, 0x6FFL));
        sraiTest.testAssertion(new GPRAssertion(5, 0x6FFL >> 4));
    }

    public void testLUI() throws java.lang.Exception {
        RiscvCmdTest luiTest = new RiscvCmdTest("test_lui");
        luiTest.compile("""
lui x10, 0x1234
lui x11, 0x8FFF
""");
        luiTest.run();
        luiTest.testAssertion(new GPRAssertion(10, 0x1234L << 12));
        luiTest.testAssertion(new GPRAssertion(11, 0x8FFFL << 12));
    }

    public void testAUIPC() throws java.lang.Exception {
        RiscvCmdTest auipcTest = new RiscvCmdTest("test_auipc");
        auipcTest.compile("""
auipc x10, 0x1234
auipc x11, 0x8FFF
""");
        auipcTest.run();
        auipcTest.testAssertion(new GPRAssertion(10, (0x1234L << 12) + Const.DRAM_BASE));
        auipcTest.testAssertion(new GPRAssertion(11, (0x8FFFL << 12) + Const.DRAM_BASE + 4L));
    }
}

