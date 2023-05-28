package moe.ziyang.rvemu.infra;

import static moe.ziyang.rvemu.infra.ErrorEnum.INTERNAL_ERROR;

public class EmuError extends RuntimeException {

    private ErrorEnum emuEnum;

    public EmuError(ErrorEnum emuEnum) {
        super(emuEnum.getMessage());
        this.emuEnum = emuEnum;
    }

    public EmuError(Exception e) {
        super(e);
        this.emuEnum = INTERNAL_ERROR;
        this.emuEnum.setMessage(e.getMessage());
    }

    public String toString() {
        return String.format("0X%5x: %s", emuEnum.getTypeCode(), emuEnum.getMessage());
    }

}
