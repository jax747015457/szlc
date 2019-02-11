package com.stylefeng.guns.rest.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 理财产品
 * </p>
 *
 * @author szlc123
 * @since 2018-06-12
 */
@TableName("app_product_info")
public class ProductInfo extends Model<ProductInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 七日年化率
     */
    private Double annualRate;
    /**
     * 起投金额
     */
    private Double minMoney;
    /**
     * 产品介绍
     */
    private String introduce;
    /**
     * 交易规则
     */
    private String tradingRules;
    /**
     * 风险提示
     */
    private String riskTip;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(Double annualRate) {
        this.annualRate = annualRate;
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTradingRules() {
        return tradingRules;
    }

    public void setTradingRules(String tradingRules) {
        this.tradingRules = tradingRules;
    }

    public String getRiskTip() {
        return riskTip;
    }

    public void setRiskTip(String riskTip) {
        this.riskTip = riskTip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
        "id=" + id +
        ", productName=" + productName +
        ", productNo=" + productNo +
        ", annualRate=" + annualRate +
        ", minMoney=" + minMoney +
        ", introduce=" + introduce +
        ", tradingRules=" + tradingRules +
        ", riskTip=" + riskTip +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
