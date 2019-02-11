package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户转账二维码
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-29
 */
@TableName("app_user_qrcode")
public class UserQrcode extends Model<UserQrcode> {

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
     * 支付宝转账码
     */
    private String qrCodeAli;
    /**
     * 微信转账码
     */
    private String qrCodeWx;
    /**
     * 监控收款通知微信码
     */
    private String qrCodeWxMonitor;


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

    public String getQrCodeWxMonitor() {
        return qrCodeWxMonitor;
    }

    public void setQrCodeWxMonitor(String qrCodeWxMonitor) {
        this.qrCodeWxMonitor = qrCodeWxMonitor;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserQrcode{" +
        "id=" + id +
        ", userId=" + userId +
        ", qrCodeAli=" + qrCodeAli +
        ", qrCodeWx=" + qrCodeWx +
        ", qrCodeWxMonitor=" + qrCodeWxMonitor +
        "}";
    }
}
