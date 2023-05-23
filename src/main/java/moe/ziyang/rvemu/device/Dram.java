package moe.ziyang.rvemu.device;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import moe.ziyang.rvemu.infra.Const;
import moe.ziyang.rvemu.infra.EmuException;
import moe.ziyang.rvemu.infra.ExceptionEnum;

public class Dram implements Device {

    private final byte[] dram;

    public Dram(byte[] initMem) {
        dram = new byte[Const.DRAM_SIZE];
        System.arraycopy(initMem, 0, dram, 0, initMem.length);
    }

    @Override
    public long load(long address, int size) {
        return switch (size) {
            case 8 -> load8(address);
            case 16 -> load16(address);
            case 32 -> load32(address);
            case 64 -> load64(address);
            default -> throw new EmuException(ExceptionEnum.INVALID_LOAD_STORE_SIZE);
        };
    }

    private long load8(long address) {
        int index = (int) (address - Const.DRAM_BASE);
        return dram[index] & 0xff;
    }

    private long load16(long address) {
        int index = (int) (address - Const.DRAM_BASE);
        return (dram[index] & 0xff)
                | ((dram[index+1] & 0xff) << 8);
    }

    private long load32(long address) {
        int index = (int) (address - Const.DRAM_BASE);
        return (dram[index] & 0xff)
                | ((dram[index+1] & 0xff) << 8)
                | ((dram[index+2] & 0xff) << 16)
                | ((long) (dram[index + 3] & 0xff) << 24);
    }

    private long load64(long address) {
        int index = (int) (address - Const.DRAM_BASE);
        return (dram[index] & 0xff)
                | ((dram[index+1] & 0xff) << 8)
                | ((dram[index+2] & 0xff) << 16)
                | ((long) (dram[index + 3] & 0xff) << 24)
                | ((long) (dram[index + 4] & 0xff) << 32)
                | ((long) (dram[index + 5] & 0xff) << 40)
                | ((long) (dram[index + 6] & 0xff) << 48)
                | ((long) (dram[index + 7] & 0xff) << 56);
    }

    @Override
    public void store(long address, int size, long value) {
        switch(size) {
            case 8: store8(address, value);
            case 16: store16(address, value);
            case 32: store32(address, value);
            case 64: store64(address, value);
            default: throw new EmuException(ExceptionEnum.INVALID_LOAD_STORE_SIZE);
        }
    }

    private void store8(long address, long value) {
        int index = (int) (address - Const.DRAM_BASE);
        dram[index] = (byte) (value & 0xff);
    }

    private void store16(long address, long value) {
        int index = (int) (address - Const.DRAM_BASE);
        dram[index] = (byte) (value & 0xff);
        dram[index+1] = (byte) ((value >>> 8) & 0xff);
    }

    private void store32(long address, long value) {
        int index = (int) (address - Const.DRAM_BASE);
        dram[index] = (byte) (value & 0xff);
        dram[index+1] = (byte) ((value >>> 8) & 0xff);
        dram[index+2] = (byte) ((value >>> 16) & 0xff);
        dram[index+3] = (byte) ((value >>> 24) & 0xff);
    }

    private void store64(long address, long value) {
        int index = (int) (address - Const.DRAM_BASE);
        dram[index] = (byte) (value & 0xff);
        dram[index+1] = (byte) ((value >>> 8) & 0xff);
        dram[index+2] = (byte) ((value >>> 16) & 0xff);
        dram[index+3] = (byte) ((value >>> 24) & 0xff);
        dram[index+4] = (byte) ((value >>> 32) & 0xff);
        dram[index+5] = (byte) ((value >>> 40) & 0xff);
        dram[index+6] = (byte) ((value >>> 48) & 0xff);
        dram[index+7] = (byte) ((value >>> 56) & 0xff);
    }

    @Override
    public Range<Long> getRange() {
        // [0x80000000, +∞)
        return Range.downTo(Const.DRAM_BASE, BoundType.CLOSED);
    }
}
