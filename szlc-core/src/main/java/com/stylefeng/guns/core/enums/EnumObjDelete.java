package com.stylefeng.guns.core.enums;

/**
 * 是否删除（删除/未删除）
 */
public enum EnumObjDelete {

    normal(0, "未删除"),
    deleted(1, "已删除");

    private final int value;

    private final String des;

    EnumObjDelete(int value, String des) {
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
