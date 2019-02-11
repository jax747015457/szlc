package com.stylefeng.guns.core.enums;

/**
 * 是否标识（是/否）
 */
public enum EnumObjIsFlag {

    no(0, "否"),
    yes(1, "是");

    private final int value;

    private final String des;

    EnumObjIsFlag(int value, String des) {
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
