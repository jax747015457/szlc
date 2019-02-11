package com.stylefeng.guns.core.enums;

/**
 * 订单回款状态
 */
public enum EnumOrderRebateStatus {

    waitRebate(1, "待回款"),
    inTheRebate(2, "回款中"),
    userConfirm(3, "用户已确认"),
    monitorConfirm(4, "监控已确认"),
    systemConfirm(5, "平台已确认"),
    exceptionOrder(9, "超时未确认");

    private final int value;

    private final String des;

    EnumOrderRebateStatus(int value, String des) {
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
            for (EnumOrderRebateStatus obj : values()) {
                if (obj.getValue() == code) {
                    return obj.getDes();
                }
            }
        }
        return null;
    }
}
