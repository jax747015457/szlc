package com.stylefeng.guns.core.enums;

/**
 * 链接跳转类型
 */
public enum EnumUrlType {

    app(1, "内部跳转"),
    web(2, "网页跳转"),
    not(3, "不跳转");

    private final int value;

    private final String des;

    EnumUrlType(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public int getValue() {
        return value;
    }

    public String getDes() {
        return des;
    }

    /**
     * 通过code查询状态名
     */
    public static String getDesByCode(Integer code) {
        if (code != null) {
            for (EnumUrlType obj : values()) {
                if (obj.getValue() == code) {
                    return obj.getDes();
                }
            }
        }
        return null;
    }
}
