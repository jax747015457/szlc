package com.stylefeng.guns.modular.system.model.vo;

import java.math.BigDecimal;

/**
 * 订单回款实体类
 */
public class OrderRebateScanVo {

    /**
     * 操作代码
     */
    private Integer code;

    /**
     * 操作消息
     */
    private String msg;

    /**
     * 回款订单ID
     */
    private Integer orderRebateId;

    /**
     * 转款金额
     */
    private BigDecimal money;

    /**
     * 支付宝转账码
     */
    private String qrCodeAli;
    /**
     * 微信转账码
     */
    private String qrCodeWx;

    public OrderRebateScanVo() {
    }

    public OrderRebateScanVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public OrderRebateScanVo(Integer code, String msg, Integer orderRebateId, BigDecimal money, String qrCodeAli, String qrCodeWx) {
        this.code = code;
        this.msg = msg;
        this.orderRebateId = orderRebateId;
        this.money = money;
        this.qrCodeAli = qrCodeAli;
        this.qrCodeWx = qrCodeWx;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getQrCodeAli() {
        return qrCodeAli;
    }

    public void setQrCodeAli(String qrCodeAli) {
        this.qrCodeAli = qrCodeAli;
    }

    public String getQrCodeWx() {
        return qrCodeWx;
    }

    public void setQrCodeWx(String qrCodeWx) {
        this.qrCodeWx = qrCodeWx;
    }

    public Integer getOrderRebateId() {
        return orderRebateId;
    }

    public void setOrderRebateId(Integer orderRebateId) {
        this.orderRebateId = orderRebateId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
