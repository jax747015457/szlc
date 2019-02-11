package com.stylefeng.guns.rest.modular.system.model.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.rest.modular.system.model.ProductInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 理财产品
 * </p>
 */
public class ProductVo {
    /**
     * ID
     */
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
     * 收益规则
     */
    private String incomeRulesUrl;
    /**
     * 取出规则
     */
    private String takeoutRulesUrl;
    /**
     * 创建时间
     */
    private Date createTime;

    /** 产品详情 */
    public ProductVo(ProductInfo pro, String incomeRulesUrl, String takeoutRulesUrl) {
        this.id = pro.getId();
        this.annualRate = pro.getAnnualRate();
        this.createTime = pro.getCreateTime();
        this.introduce = pro.getIntroduce();
        this.minMoney = pro.getMinMoney();
        this.productName = pro.getProductName();
        this.productNo = pro.getProductNo();
        this.riskTip = pro.getRiskTip();
        this.tradingRules = pro.getTradingRules();
        this.incomeRulesUrl = incomeRulesUrl;
        this.takeoutRulesUrl = takeoutRulesUrl;
    }

    public ProductVo(Integer id, String productName, String productNo, Double annualRate, Double minMoney) {
        this.id = id;
        this.productName = productName;
        this.productNo = productNo;
        this.annualRate = annualRate;
        this.minMoney = minMoney;
    }

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

    public String getIncomeRulesUrl() {
        return incomeRulesUrl;
    }

    public void setIncomeRulesUrl(String incomeRulesUrl) {
        this.incomeRulesUrl = incomeRulesUrl;
    }

    public String getTakeoutRulesUrl() {
        return takeoutRulesUrl;
    }

    public void setTakeoutRulesUrl(String takeoutRulesUrl) {
        this.takeoutRulesUrl = takeoutRulesUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
