package moe.ziyang.rvemu;

import junit.framework.TestCase;

import java.io.IOException;

public class AppTest extends TestCase {

    public void testAddi() throws Exception {
        RiscvCmdTest addiTest = new RiscvCmdTest("test_addi");
        addiTest.compile("""
addi x29, x0, 5
addi x30, x0, 37
addi x31, x0, -6""");
        addiTest.run();
        addiTest.testAssertion(new GPRAssertion(29, 5L));
        addiTest.testAssertion(new GPRAssertion(30, 37L));
        addiTest.testAssertion(new GPRAssertion(31, -6L));
    }

    public void testSlti() throws Exception {
        RiscvCmdTest sltiTest = new RiscvCmdTest("test_slti");
        sltiTest.compile("""
addi x28, x0, 5
addi x29, x0, 37
addi x30, x0, 42
addi x31, x0, -10
slti x3, x28, 4
slti x4, x29, 37
slti x5, x30, 60
slti x6, x31, -5""");
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

    public void testSltiu() throws Exception {
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
sltiu x9, x28, -10""");
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

    public void testAndi() throws Exception {
        RiscvCmdTest andiTest = new RiscvCmdTest("test_andi");
        andiTest.compile("""
addi x2, x0, 5
andi x3, x2, 37
addi x4, x0, -6
andi x5, x4, -10
addi x6, x0, -6
andi x7, x6, 37""");
        andiTest.run();
        andiTest.testAssertion(new GPRAssertion(2, 5L));
        andiTest.testAssertion(new GPRAssertion(3, 5L));
        andiTest.testAssertion(new GPRAssertion(4, -6L));
        andiTest.testAssertion(new GPRAssertion(5, -14L));
        andiTest.testAssertion(new GPRAssertion(6, -6L));
        andiTest.testAssertion(new GPRAssertion(7, 32L));
    }
}

