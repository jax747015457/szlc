package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 商户信息
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-19
 */
@TableName("YYBM_MchContactInfo")
public class MchContactInfo extends Model<MchContactInfo>{

    private static final long serialVersionUID = 1L;

    /**
     * 系统编号
     */
    @TableId(value = "iId", type = IdType.AUTO)
    private Integer iId;
    /**
     * 真实姓名
     */
    private String sTureName;
    /**
     * 手机号
     */
    private String sPhone;
    /**
     * 注册邮箱
     */
    private String sEmail;
    /**
     * 收款银行
     */
    private String sCashBank;
    /**
     * 收款账号
     */
    private String sCashAccount;
    /**
     * 开户地址
     */
    private String sCashAddr;
    /**
     * 身份证号
     */
    private String sIdNumber;
    /**
     * 网站名称
     */
    private String sSiteName;
    /**
     * 站点地址
     */
    private String sSiteHttp;
    /**
     * 创建时间
     */
    private Date dCreatTime;
    /**
     * 更新时间
     */
    private Date dUpdateTime;
    /**
     * 更新人ID
     */
    private Integer iUpdateUserId;
    /**
     * 更新操作人的名字
     */
    private String sUpdateUserName;

    
    
	public Integer getiId() {
		return iId;
	}



	public void setiId(Integer iId) {
		this.iId = iId;
	}



	public String getsTureName() {
		return sTureName;
	}



	public void setsTureName(String sTureName) {
		this.sTureName = sTureName;
	}



	public String getsPhone() {
		return sPhone;
	}



	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}



	public String getsEmail() {
		return sEmail;
	}



	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}



	public String getsCashBank() {
		return sCashBank;
	}



	public void setsCashBank(String sCashBank) {
		this.sCashBank = sCashBank;
	}



	public String getsCashAccount() {
		return sCashAccount;
	}



	public void setsCashAccount(String sCashAccount) {
		this.sCashAccount = sCashAccount;
	}



	public String getsCashAddr() {
		return sCashAddr;
	}



	public void setsCashAddr(String sCashAddr) {
		this.sCashAddr = sCashAddr;
	}



	public String getsIdNumber() {
		return sIdNumber;
	}



	public void setsIdNumber(String sIdNumber) {
		this.sIdNumber = sIdNumber;
	}



	public String getsSiteName() {
		return sSiteName;
	}



	public void setsSiteName(String sSiteName) {
		this.sSiteName = sSiteName;
	}



	public String getsSiteHttp() {
		return sSiteHttp;
	}



	public void setsSiteHttp(String sSiteHttp) {
		this.sSiteHttp = sSiteHttp;
	}



	public Date getdCreatTime() {
		return dCreatTime;
	}



	public void setdCreatTime(Date dCreatTime) {
		this.dCreatTime = dCreatTime;
	}



	public Date getdUpdateTime() {
		return dUpdateTime;
	}



	public void setdUpdateTime(Date dUpdateTime) {
		this.dUpdateTime = dUpdateTime;
	}



	public Integer getiUpdateUserId() {
		return iUpdateUserId;
	}



	public void setiUpdateUserId(Integer iUpdateUserId) {
		this.iUpdateUserId = iUpdateUserId;
	}



	public String getsUpdateUserName() {
		return sUpdateUserName;
	}



	public void setsUpdateUserName(String sUpdateUserName) {
		this.sUpdateUserName = sUpdateUserName;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
    protected Serializable pkVal() {
        return this.iId;
    }

	@Override
    public String toString() {
        return "MchContactInfo{" +
        "iId=" + iId +
        ", sTureName=" + sTureName +
        ", sPhone=" + sPhone +
        ", sEmail=" + sEmail +
        ", sCashBank=" + sCashBank +
        ", sCashAccount=" + sCashAccount +
        ", sCashAddr=" + sCashAddr +
        ", sIdNumber=" + sIdNumber +
        ", sSiteName=" + sSiteName +
        ", sSiteHttp=" + sSiteHttp +
        ", dCreatTime=" + dCreatTime +
        ", dUpdateTime=" + dUpdateTime +
        ", iUpdateUserId=" + iUpdateUserId +
        ", sUpdateUserName=" + sUpdateUserName +
        "}";
    }
}
