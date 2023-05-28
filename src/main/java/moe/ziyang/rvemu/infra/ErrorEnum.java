package moe.ziyang.rvemu.infra;

public enum ErrorEnum {

    INTERNAL_ERROR(0x10000, ""),
    DEVICE_COLLISION(0x10001, "设备地址范围冲突"),
    INVALID_ADDRESS(0x10002, "未注册的物理地址"),
    INVALID_LOAD_STORE_SIZE(0x10003, "非法内存读写大小"),
    UNSUPPORTED_INSTRUCTION(0x10004, "不支持的指令类型"),
    MISS_MATCH_INSTRUCTION(0x10005, "指令类型解析错误");

    private int typeCode;
    private String message;

    ErrorEnum(int typeCode, String message) {
        this.typeCode = typeCode;
        this.message = message;
    }

    ErrorEnum(Exception e) {
        this.typeCode = 0x10000;
        this.message = e.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTypeCode() {
        return typeCode;
    }

}
