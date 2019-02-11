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
 * 收益监控数据
 * </p>
 *
 * @author szlc123
 * @since 2018-06-27
 */
@TableName("app_monitor_data")
public class MonitorData extends Model<MonitorData> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 监控类型
     */
    private Integer type;
    /**
     * 金额
     */
    private Double money;
    /**
     * 记录时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
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
        return "MonitorData{" +
        "id=" + id +
        ", userId=" + userId +
        ", type=" + type +
        ", money=" + money +
        ", createTime=" + createTime +
        "}";
    }

    public MonitorData() {
    }

    public MonitorData(Integer userId, Integer type, Double money, Date createTime) {
        this.userId = userId;
        this.type = type;
        this.money = money;
        this.createTime = createTime;
    }
}
