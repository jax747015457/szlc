package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 微信收款监控
 * </p>
 *
 * @author stylefeng123
 * @since 2018-07-10
 */
@TableName("c_wechat_data")
public class CWechatData extends Model<CWechatData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 收款人
     */
    private String receiver;
    /**
     * 金额
     */
    private Double money;
    /**
     * 收集信息
     */
    private String collectinfo;
    /**
     * 监控时间
     */
    private Date rtime;
    /**
     * 是否上传
     */
    private Integer isUpload;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCollectinfo() {
        return collectinfo;
    }

    public void setCollectinfo(String collectinfo) {
        this.collectinfo = collectinfo;
    }

    public Date getRtime() {
        return rtime;
    }

    public void setRtime(Date rtime) {
        this.rtime = rtime;
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CWechatData{" +
        "id=" + id +
        ", receiver=" + receiver +
        ", money=" + money +
        ", collectinfo=" + collectinfo +
        ", rtime=" + rtime +
        ", isUpload=" + isUpload +
        "}";
    }
}
