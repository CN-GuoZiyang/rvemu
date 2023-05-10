package moe.ziyang.rvemu;

import junit.framework.TestCase;

import java.io.IOException;

public class AppTest extends TestCase {

    public void testAddi() throws Exception {
        RiscvCmdTest addiTest = new RiscvCmdTest("test_addi");
        addiTest.compile("""
addi x29, x0, 5
addi x30, x0, 37""");
        addiTest.addAssertion(new GPRAssertion(29, 5L));
        addiTest.addAssertion(new GPRAssertion(30, 37L));
        addiTest.test();
    }
}
