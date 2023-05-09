package moe.ziyang.rvemu;

import junit.framework.TestCase;

import java.io.IOException;

public class AppTest extends TestCase {

    public void testAddi() throws IOException {
        RiscvCmdTest addiTest = new RiscvCmdTest("test_addi");
        addiTest.compile("""
addi x29, x0, 5
addi x30, x0, 37""");
        addiTest.test();
    }
}
