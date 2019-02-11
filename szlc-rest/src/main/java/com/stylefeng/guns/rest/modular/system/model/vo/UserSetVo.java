package com.stylefeng.guns.rest.modular.system.model.vo;

public class UserSetVo {
    
    /** 是否接收消息 */
    private Integer isReceiveMsg;

    /** 是否开启手势 */
    private Integer isEnableHandPwd;

    /** 用户协议 */
    private String userAgreementH5;

    /** 关于我们 */
    private String aboutUsH5;

    public UserSetVo() {
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

    public String getUserAgreementH5() {
        return userAgreementH5;
    }

    public void setUserAgreementH5(String userAgreementH5) {
        this.userAgreementH5 = userAgreementH5;
    }

    public String getAboutUsH5() {
        return aboutUsH5;
    }

    public void setAboutUsH5(String aboutUsH5) {
        this.aboutUsH5 = aboutUsH5;
    }
}
