package com.stylefeng.guns.core.enums;

/**
 * 状态（启用/冻结）
 */
public enum EnumObjStatus {

    normal(0, "正常"),
    freeze(1, "冻结");

    private final int value;

    private final String des;

    EnumObjStatus(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public int getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }
}
