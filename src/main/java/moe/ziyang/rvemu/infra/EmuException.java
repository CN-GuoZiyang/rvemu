package moe.ziyang.rvemu.infra;

import static moe.ziyang.rvemu.infra.ExceptionEnum.INTERNAL_ERROR;

public class EmuException extends RuntimeException {

    private ExceptionEnum emuEnum;

    public EmuException(ExceptionEnum emuEnum) {
        super(emuEnum.getMessage());
        this.emuEnum = emuEnum;
    }

    public EmuException(Exception e) {
        super(e);
        this.emuEnum = INTERNAL_ERROR;
        this.emuEnum.setMessage(e.getMessage());
    }

    public String toString() {
        return String.format("0X%5x: %s", emuEnum.getTypeCode(), emuEnum.getMessage());
    }

}
