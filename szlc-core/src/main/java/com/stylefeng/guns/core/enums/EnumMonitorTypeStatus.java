package com.stylefeng.guns.core.enums;

/**
 * 监控数据类型状态
 */
public enum EnumMonitorTypeStatus {

    aliPay(1, "支付宝"),
    wechatPay(2, "微信");

    private final int value;

    private final String des;

    EnumMonitorTypeStatus(int value, String des) {
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
            for (EnumMonitorTypeStatus obj : values()) {
                if (obj.getValue() == code) {
                    return obj.getDes();
                }
            }
        }
        return null;
    }
}
