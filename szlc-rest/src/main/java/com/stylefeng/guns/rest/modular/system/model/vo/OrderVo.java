package com.stylefeng.guns.rest.modular.system.model.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.rest.modular.system.model.OrderRebate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderVo {

    /**
     * ID
     */
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
     * 产品名称
     */
    private String productName;
    /**
     * 产品编号
     */
    private String productNo;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户手机
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 收益金额
     */
    private BigDecimal incomeAmount;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    private List<OrderRebate> list;

    public OrderVo() {
    }

    /** 个人主页列表 */
    public OrderVo(Integer id, Integer productId, String productName, String productNo, BigDecimal money, BigDecimal incomeAmount, Integer status) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productNo = productNo;
        this.money = money;
        this.incomeAmount = incomeAmount;
        this.status = status;
    }

    /** 收益明细 */
    public OrderVo(String productName, BigDecimal incomeAmount, Date createTime) {
        this.productName = productName;
        this.incomeAmount = incomeAmount;
        this.createTime = createTime;
    }

    /** 交易记录 */
    public OrderVo(BigDecimal money, String productName, Date createTime) {
        this.money = money;
        this.productName = productName;
        this.createTime = createTime;
    }

    /** 回款管理 */
    public OrderVo(Integer id, String productName, BigDecimal incomeAmount, Integer status, List<OrderRebate> list) {
        this.id = id;
        this.productName = productName;
        this.incomeAmount = incomeAmount;
        this.status = status;
        this.list = list;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
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

    public List<OrderRebate> getList() {
        return list;
    }

    public void setList(List<OrderRebate> list) {
        this.list = list;
    }
}
