package com.stylefeng.guns.rest.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author szlc123
 * @since 2018-06-28
 */
@TableName("app_order_info")
public class OrderInfo extends Model<OrderInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 七日年化率
     */
    private Double annualRate;
    /**
     * 收益金额
     */
    private BigDecimal incomeAmount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 起息时间
     */
    private Date incomeTime;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 支付交易号
     */
    private String payTradeNo;
    /**
     * 支付时间
     */
    private Date payTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(Double annualRate) {
        this.annualRate = annualRate;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Date incomeTime) {
        this.incomeTime = incomeTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", productId=" + productId +
        ", userId=" + userId +
        ", money=" + money +
        ", annualRate=" + annualRate +
        ", incomeAmount=" + incomeAmount +
        ", createTime=" + createTime +
        ", incomeTime=" + incomeTime +
        ", status=" + status +
        ", payTradeNo=" + payTradeNo +
        ", payTime=" + payTime +
        "}";
    }
}
