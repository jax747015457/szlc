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
 * 用户信息
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
@TableName("app_user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 登录账号
     */
    private String account;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户密码
     */
    private String userPwd;
    /**
     * 手势密码
     */
    private String handPwd;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 信用分
     */
    private Integer creditScore;
    /**
     * 优先度
     */
    private Integer orderBy;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 角色类型
     */
    private Integer roleType;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 所属代理
     */
    private Integer roleParentId;
    /**
     * 分利比例
     */
    private Double profitRate;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getHandPwd() {
        return handPwd;
    }

    public void setHandPwd(String handPwd) {
        this.handPwd = handPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleParentId() {
        return roleParentId;
    }

    public void setRoleParentId(Integer roleParentId) {
        this.roleParentId = roleParentId;
    }

    public Double getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(Double profitRate) {
        this.profitRate = profitRate;
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
        return "UserInfo{" +
        "id=" + id +
        ", account=" + account +
        ", phone=" + phone +
        ", nickName=" + nickName +
        ", avatar=" + avatar +
        ", userPwd=" + userPwd +
        ", handPwd=" + handPwd +
        ", realName=" + realName +
        ", idCard=" + idCard +
        ", email=" + email +
        ", creditScore=" + creditScore +
        ", orderBy=" + orderBy +
        ", status=" + status +
        ", roleType=" + roleType +
        ", roleName=" + roleName +
        ", roleParentId=" + roleParentId +
        ", profitRate=" + profitRate +
        ", createTime=" + createTime +
        "}";
    }
}
