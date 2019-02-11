package com.stylefeng.guns.core.enums;

/**
 * 订单回款封装类code定义
 */
public enum EnumOrderRebateScanCode {

    OK(0, "允许访问"),
    AccountError(1, "账号或Key输入错误"),
    IpException(2, "该访问IP地址无访问权限"),
    NoData(3, "暂无可扫码数据");

    private final int value;

    private final String des;

    EnumOrderRebateScanCode(int value, String des) {
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
            for (EnumOrderRebateScanCode obj : values()) {
                if (obj.getValue() == code) {
                    return obj.getDes();
                }
            }
        }
        return null;
    }
}
