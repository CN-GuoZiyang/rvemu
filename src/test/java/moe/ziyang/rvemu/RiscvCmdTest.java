package moe.ziyang.rvemu;

import java.io.*;

public class RiscvCmdTest {

    private final String testName;
    private final String binFileName;

    public RiscvCmdTest(String testName) {
        this.testName = testName;
        this.binFileName = String.format("/tmp/%s.bin", this.testName);
    }

    public void compile(String source) throws IOException {
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
        Runtime.getRuntime().exec(
                String.format("riscv64-unknown-elf-gcc -Wl,-Ttext=0x0 -nostdlib -o %s %s", oFileName, sFileName)
        );

        // dump 为 binary
        Runtime.getRuntime().exec(
                String.format("riscv64-unknown-elf-objcopy -O binary %s %s", oFileName, binFileName)
        );
    }

    public void test() throws IOException {
        byte[] bytes;
        try (InputStream fin = new FileInputStream(binFileName)) {
            bytes = fin.readAllBytes();
        }
        Core core = new Core(bytes);
        core.run();
    }

}
