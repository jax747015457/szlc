package com.stylefeng.guns.core.enums;

/**
 * 订单状态
 */
public enum EnumOrderStatus {

    waitPay(1, "待支付"),
    waitRebate(2, "待收益"),
    inTheRebate(3, "收益中"),
    rebateSuccessful(4, "已确认到账"),
    exceptionOrder(9, "超时未确认"),
    cancel(0, "已取消");

    private final int value;

    private final String des;

    EnumOrderStatus(int value, String des) {
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
            for (EnumOrderStatus obj : values()) {
                if (obj.getValue() == code) {
                    return obj.getDes();
                }
            }
        }
        return null;
    }
}
