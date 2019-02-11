package com.stylefeng.guns.rest.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户个人设置
 * </p>
 *
 * @author szlc123
 * @since 2018-05-24
 */
@TableName("app_user_set")
public class UserSet extends Model<UserSet> {

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
     * 是否接收消息
     */
    private Integer isReceiveMsg;
    /**
     * 是否开启手势
     */
    private Integer isEnableHandPwd;


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

    public Integer getIsReceiveMsg() {
        return isReceiveMsg;
    }

    public void setIsReceiveMsg(Integer isReceiveMsg) {
        this.isReceiveMsg = isReceiveMsg;
    }

    public Integer getIsEnableHandPwd() {
        return isEnableHandPwd;
    }

    public void setIsEnableHandPwd(Integer isEnableHandPwd) {
        this.isEnableHandPwd = isEnableHandPwd;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserSet{" +
        "id=" + id +
        ", userId=" + userId +
        ", isReceiveMsg=" + isReceiveMsg +
        ", isEnableHandPwd=" + isEnableHandPwd +
        "}";
    }
}
