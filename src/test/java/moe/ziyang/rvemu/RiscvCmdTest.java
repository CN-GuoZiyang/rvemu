package moe.ziyang.rvemu;

import java.io.*;

public class RiscvCmdTest {

    private Core core;
    private final String testName;
    private final String binFileName;

    public RiscvCmdTest(String testName) {
        this.testName = testName;
        this.binFileName = String.format("/tmp/%s.bin", this.testName);
    }

    public void compile(String source) throws Exception {
        String sFileName = String.format("/tmp/%s.s", this.testName);
        String oFileName = String.format("/tmp/%s.o", this.testName);

        // 写入 S 文件
        File sourceFile = new File(sFileName);
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
        try (FileWriter fw = new FileWriter(sourceFile);
            BufferedWriter writer = new BufferedWriter(fw)) {
            writer.write(source);
            writer.flush();
        }

        // 编译为目标文件
        File oFile = new File(oFileName);
        if (oFile.exists()) {
            oFile.delete();
        }
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
        File binFile = new File(binFileName);
        if (binFile.exists()) {
            binFile.delete();
        }
        pb = new ProcessBuilder(
                "riscv64-unknown-elf-objcopy",
                "-O",
                "binary",
                oFileName,
                binFileName);
        p = pb.start();
        p.waitFor();
    }

    public void testAssertion(Assertion a) {
        assert core != null;
        a.Assert(core);
    }

    public void run() throws IOException {
        byte[] bytes;
        try (InputStream fin = new FileInputStream(binFileName)) {
            bytes = fin.readAllBytes();
        }
        core = new Core(bytes);
        core.run();
    }

}
