package com.stylefeng.guns.modular.system.model;

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
 * 收益回款订单
 * </p>
 *
 * @author szlc123
 * @since 2018-07-27
 */
@TableName("app_order_rebate")
public class OrderRebate extends Model<OrderRebate> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 转款序号
     */
    private Integer rebateNo;
    /**
     * 回款金额
     */
    private BigDecimal money;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 服务商ID
     */
    private Integer serviceProviderId;
    /**
     * 回款时间
     */
    private Date rebateTime;
    /**
     * 支付宝转款码
     */
    private String qrCodeAli;
    /**
     * 微信转款码
     */
    private String qrCodeWx;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 监控数据ID
     */
    private Integer monitorId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 服务商锁定ID
     */
    private Integer lockSpId;
    /**
     * 结算ID
     */
    private Integer settlementId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRebateNo() {
        return rebateNo;
    }

    public void setRebateNo(Integer rebateNo) {
        this.rebateNo = rebateNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Date getRebateTime() {
        return rebateTime;
    }

    public void setRebateTime(Date rebateTime) {
        this.rebateTime = rebateTime;
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

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLockSpId() {
        return lockSpId;
    }

    public void setLockSpId(Integer lockSpId) {
        this.lockSpId = lockSpId;
    }

    public Integer getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Integer settlementId) {
        this.settlementId = settlementId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "OrderRebate{" +
        "id=" + id +
        ", rebateNo=" + rebateNo +
        ", money=" + money +
        ", orderId=" + orderId +
        ", serviceProviderId=" + serviceProviderId +
        ", rebateTime=" + rebateTime +
        ", qrCodeAli=" + qrCodeAli +
        ", qrCodeWx=" + qrCodeWx +
        ", confirmTime=" + confirmTime +
        ", monitorId=" + monitorId +
        ", status=" + status +
        ", createTime=" + createTime +
        ", remark=" + remark +
        ", lockSpId=" + lockSpId +
        ", settlementId=" + settlementId +
        "}";
    }
}
