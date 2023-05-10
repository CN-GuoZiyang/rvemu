package moe.ziyang.rvemu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RiscvCmdTest {

    private static final String RISCV_TOOLCHAIN_LOCATION = "/opt/riscv/bin/";

    private final String testName;
    private final String binFileName;

    private final List<Assertion> assertions;

    public RiscvCmdTest(String testName) {
        this.testName = testName;
        this.binFileName = String.format("/tmp/%s.bin", this.testName);
        assertions = new ArrayList<>();
    }

    public void compile(String source) throws Exception {
        String sFileName = String.format("/tmp/%s.S", this.testName);
        String oFileName = String.format("/tmp/%s.o", this.testName);

        // 写入 S 文件
        File sourceFile = new File(sFileName);
        new FileWriter(sourceFile, false).close();
        try (FileWriter fw = new FileWriter(sourceFile, true);
            BufferedWriter writer = new BufferedWriter(fw)) {
            writer.write(source);
            writer.flush();
        }

        // 编译为目标文件
        ProcessBuilder pb = new ProcessBuilder(
                "riscv64-unknown-elf-gcc",
                "-Wl,-Ttext=0x0",
                "-nostdlib",
                "-o",
                oFileName,
                sFileName);
        Process p = pb.start();
        p.waitFor();

        // dump 为 binary
        pb = new ProcessBuilder(
                "riscv64-unknown-elf-objcopy",
                "-O",
                "binary",
                oFileName,
                binFileName);
        p = pb.start();
        p.waitFor();
    }

    public void addAssertion(Assertion a) {
        this.assertions.add(a);
    }

    public void test() throws IOException {
        byte[] bytes;
        try (InputStream fin = new FileInputStream(binFileName)) {
            bytes = fin.readAllBytes();
        }
        Core core = new Core(bytes);
        core.run();
        for (Assertion a : this.assertions) {
            a.Assert(core);
        }
    }

}
