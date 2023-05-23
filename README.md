# RVEMU

A well-designed RISC-V emulator written in Java, supporting RV64GC

#### Compile RV64 Toolchain Without Compressed Instruction Support

```shell
./configure --prefix=/opt/riscv --with-arch=rv64g --disable-multilib --with-cmodel=medany
make -j8
```