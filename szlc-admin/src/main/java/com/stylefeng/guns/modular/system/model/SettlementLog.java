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
 * 回款结算记录
 * </p>
 *
 * @author stylefeng123
 * @since 2018-07-27
 */
@TableName("app_settlement_log")
public class SettlementLog extends Model<SettlementLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 结算ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 服务商ID
     */
    private Integer spId;
    /**
     * 结算金额
     */
    private BigDecimal money;
    /**
     * 管理员ID
     */
    private Integer adminId;
    /**
     * 结算时间
     */
    private Date createTime;
    /**
     * 结算状态
     */
    private Integer status;
    /**
     * 结算备注
     */
    private String remark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SettlementLog{" +
        "id=" + id +
        ", spId=" + spId +
        ", money=" + money +
        ", adminId=" + adminId +
        ", createTime=" + createTime +
        ", status=" + status +
        ", remark=" + remark +
        "}";
    }
}
