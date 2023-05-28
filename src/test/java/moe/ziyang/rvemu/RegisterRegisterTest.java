package moe.ziyang.rvemu;

import junit.framework.TestCase;

public class RegisterRegisterTest extends TestCase {

    public void testAdd() throws java.lang.Exception {
        RiscvCmdTest addTest = new RiscvCmdTest("test_add");
        addTest.compile("""
addi x2, x0, 5
addi x3, x0, 37
add  x4, x2, x3
addi x5, x0, -7
add  x6, x5, x3
addi x7, x0, -15
add  x8, x7, x5
addi x9, x0, 3
add  x10, x7, x9
""");
        addTest.run();
        addTest.testAssertion(new GPRAssertion(2, 5L));
        addTest.testAssertion(new GPRAssertion(3, 37L));
        addTest.testAssertion(new GPRAssertion(4, 42L));
        addTest.testAssertion(new GPRAssertion(5, -7L));
        addTest.testAssertion(new GPRAssertion(6, 30L));
        addTest.testAssertion(new GPRAssertion(7, -15L));
        addTest.testAssertion(new GPRAssertion(8, -22L));
        addTest.testAssertion(new GPRAssertion(9, 3L));
        addTest.testAssertion(new GPRAssertion(10, -12L));
    }

    public void testSub() throws java.lang.Exception {
        RiscvCmdTest subTest = new RiscvCmdTest("test_sub");
        subTest.compile("""
addi x2, x0, 5
addi x3, x0, 37
sub  x4, x3, x2
addi x5, x0, -7
sub  x6, x5, x3
addi x7, x0, -15
sub  x8, x5, x7
addi x9, x0, 3
sub  x10, x7, x9
""");
        subTest.run();
        subTest.testAssertion(new GPRAssertion(2, 5L));
        subTest.testAssertion(new GPRAssertion(3, 37L));
        subTest.testAssertion(new GPRAssertion(4, 32L));
        subTest.testAssertion(new GPRAssertion(5, -7L));
        subTest.testAssertion(new GPRAssertion(6, -44L));
        subTest.testAssertion(new GPRAssertion(7, -15L));
        subTest.testAssertion(new GPRAssertion(8, 8L));
        subTest.testAssertion(new GPRAssertion(9, 3L));
        subTest.testAssertion(new GPRAssertion(10, -18L));
    }

    public void testSlt() throws java.lang.Exception {
        RiscvCmdTest sltTest = new RiscvCmdTest("test_slt");
        sltTest.compile("""
addi x2, x0, 5
addi x3, x0, 37
slt  x4, x2, x3
addi x5, x0, -7
slt  x6, x5, x3
""");
        sltTest.run();
        sltTest.testAssertion(new GPRAssertion(2, 5L));
        sltTest.testAssertion(new GPRAssertion(3, 37L));
        sltTest.testAssertion(new GPRAssertion(4, 1L));
        sltTest.testAssertion(new GPRAssertion(5, -7L));
        sltTest.testAssertion(new GPRAssertion(6, 1L));
    }

    public void testSltu() throws java.lang.Exception {
        RiscvCmdTest sltuTest = new RiscvCmdTest("test_sltu");
        sltuTest.compile("""
addi x2, x0, 5
addi x3, x0, 37
sltu  x4, x2, x3
addi x5, x0, -7
sltu  x6, x5, x3
""");
        sltuTest.run();
        sltuTest.testAssertion(new GPRAssertion(2, 5L));
        sltuTest.testAssertion(new GPRAssertion(3, 37L));
        sltuTest.testAssertion(new GPRAssertion(4, 1L));
        sltuTest.testAssertion(new GPRAssertion(5, -7L));
        sltuTest.testAssertion(new GPRAssertion(6, 0L));
    }

    public void testAndOrXor() throws java.lang.Exception {
        RiscvCmdTest andOrXorTest = new RiscvCmdTest("test_andOrXor");
        andOrXorTest.compile("""
addi x2, x0, 5
addi x3, x0, 37
addi x4, x0, -7
and  x5, x2, x3
and  x6, x3, x4
or   x7, x2, x3
or   x8, x3, x4
xor  x9, x2, x3
xor  x10, x3, x4
""");
        andOrXorTest.run();
        andOrXorTest.testAssertion(new GPRAssertion(2, 5L));
        andOrXorTest.testAssertion(new GPRAssertion(3, 37L));
        andOrXorTest.testAssertion(new GPRAssertion(4, -7L));
        andOrXorTest.testAssertion(new GPRAssertion(5, 5L&37L));
        andOrXorTest.testAssertion(new GPRAssertion(6, 37L&-7L));
        andOrXorTest.testAssertion(new GPRAssertion(7, 5L|37L));
        andOrXorTest.testAssertion(new GPRAssertion(8, 37L|-7L));
        andOrXorTest.testAssertion(new GPRAssertion(9, 5L^37L));
        andOrXorTest.testAssertion(new GPRAssertion(10, 37L^-7L));
    }

    public void testSll() throws java.lang.Exception {
        RiscvCmdTest sllTest = new RiscvCmdTest("test_sll");
        sllTest.compile("""
addi x2, x0, 5
addi x3, x0, 15
sll  x4, x2, x3
addi x5, x0, -7
addi x6, x0, 12
sll  x7, x5, x6
""");
        sllTest.run();
        sllTest.testAssertion(new GPRAssertion(2, 5L));
        sllTest.testAssertion(new GPRAssertion(3, 15L));
        sllTest.testAssertion(new GPRAssertion(4, 5L << 15));
        sllTest.testAssertion(new GPRAssertion(5, -7L));
        sllTest.testAssertion(new GPRAssertion(6, 12L));
        sllTest.testAssertion(new GPRAssertion(7, -7L << 12));
    }

    public void testSrlSra() throws java.lang.Exception {
        RiscvCmdTest srlsraTest = new RiscvCmdTest("test_srlsra");
        srlsraTest.compile("""
addi x2, x0, 0x72e
addi x3, x0, 5
srl  x4, x2, x3
sra  x5, x2, x3
addi x6, x0, -7
addi x7, x0, 8
srl  x8, x6, x7
sra  x9, x6, x7
""");
        srlsraTest.run();
        srlsraTest.testAssertion(new GPRAssertion(2, 0x72eL));
        srlsraTest.testAssertion(new GPRAssertion(3, 5L));
        srlsraTest.testAssertion(new GPRAssertion(4, 0x72eL >>> 5));
        srlsraTest.testAssertion(new GPRAssertion(5, 0x72eL >> 5));
        srlsraTest.testAssertion(new GPRAssertion(6, -7L));
        srlsraTest.testAssertion(new GPRAssertion(7, 8L));
        srlsraTest.testAssertion(new GPRAssertion(8, -7L >>> 8));
        srlsraTest.testAssertion(new GPRAssertion(9, -7L >> 8));
    }

}
