package moe.ziyang.rvemu.device;

import com.google.common.collect.Range;

public interface Device {

    // 在 address 地址处读取 size 长度的数据并返回
    long load(long address, int size);

    // 在 address 地址处写入 size 长度的数据
    void store(long address, int size, long value);

    // 获取设备所占用地址空间的范围
    Range<Long> getRange();
}
