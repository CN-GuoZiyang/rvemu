package moe.ziyang.rvemu;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;

public class AppTest extends TestCase {

    public void testAddi() throws IOException {
        InputStream fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("add-addi.bin");
        byte[] bytes = fin.readAllBytes();
        Core core = new Core(bytes);
        core.run();
        System.out.printf("x28=%x\n", core.gprs.read(28));
        System.out.printf("x29=%x\n", core.gprs.read(29));
        System.out.printf("x30=%x\n", core.gprs.read(30));
        System.out.printf("x31=%x\n", core.gprs.read(31));
    }
}
