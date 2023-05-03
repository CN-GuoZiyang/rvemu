package moe.ziyang.rvemu.device;

import com.google.common.collect.Range;
import moe.ziyang.rvemu.infra.Debug;
import moe.ziyang.rvemu.infra.EmuException;
import moe.ziyang.rvemu.infra.ExceptionEnum;

import java.util.ArrayList;
import java.util.List;

public class Bus implements Device {

    private final List<Device> devices;

    public Bus(byte[] code) {
        devices = new ArrayList<>();
        registerDevices(code);
    }

    @Override
    public long load(long address, int size) {
        for (Device device : devices) {
            if (device.getRange().contains(address)) {
                try {
                    return device.load(address, size);
                } catch (EmuException e) {
                    Debug.panic(e);
                }
            }
        }
        Debug.panic(new EmuException(ExceptionEnum.INVALID_ADDRESS));
        return 0;  // NEVER REACHED
    }

    @Override
    public void store(long address, int size, long value) {
        for (Device device : devices) {
            if (device.getRange().contains(address)) {
                try {
                    device.store(address, size, value);
                } catch (EmuException e) {
                    Debug.panic(e);
                }
                return;
            }
        }
        Debug.panic(new EmuException(ExceptionEnum.INVALID_ADDRESS));
    }

    @Override
    public Range<Long> getRange() {
        return Range.all();
    }

    // 注册所有设备到地址空间
    private void registerDevices(byte[] code) {
        // 注册 DRAM
        Device dram = new Dram(code);
        registerDevice(dram);
    }

    private void registerDevice(Device newDevice) {
        for (Device device : devices) {
            // 判断范围是否重合：区间相交且相交区间不为空区间
            if (device.getRange().isConnected(newDevice.getRange()) &&
                    !device.getRange().intersection(newDevice.getRange()).isEmpty()) {
                Debug.panic(new EmuException(ExceptionEnum.DEVICE_COLLISION));
            }
        }
        devices.add(newDevice);
    }

}
