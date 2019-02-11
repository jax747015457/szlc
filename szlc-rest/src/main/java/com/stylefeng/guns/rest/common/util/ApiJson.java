package com.stylefeng.guns.rest.common.util;

public class ApiJson{

	public static final String eMsgProductInfoNotExist = "产品信息不存在！";

	public static final String eMsgSmsError = "短信验证码错误";
	public static final String eMsgSmsInvalid = "短信验证码无效";

	public static final String eMsgUserLoginError = "用户名或密码错误！";
	public static final String eMsgUserInfoNotExist = "用户名信息不存在！";
	public static final String eMsgUserPhoneError = "用户手机号不正确！";
	public static final String eMsgUserPwdError = "输入密码不正确！";

	public static final String eMsgUserIdCardError = "实名身份信息验证失败！";
	public static final String eMsgUserPhoneExist = "用户手机账号已存在！";
	public static final String sMsgUserRegistSuccess = "用户注册成功！";

	/** 接口请求消息 */
	public static final String msgException = "接口请求异常";

	/** 基础代码 */
	public static final Integer codeOk = 0;
	public static final String msgOk ="操作成功";
	
	public static final Integer codeNg = 1;
	public static final String msgNg ="操作失败";

	/** 基础参数 */
	public Integer code;
	public String msg;

	/** 扩展参数 */
	public Object data;
	
	public ApiJson() {
		this.code = ApiJson.codeOk;
        this.msg = ApiJson.msgOk;
	}
	
    public ApiJson(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public ApiJson(ApiJson json) {
        this.code = json.code;
        this.msg = json.msg;
        this.data = json.data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
	
	/**
	 * 封装接口返回数据
	 * @param params 可选返参集：params[0]设置data（默认：空集），params[1]设置msg（默认：操作成功！），params[2]设置code（默认：0）
	 * @return
	 */
	public static Object returnOK(Object... params) {
		ApiJson json = new ApiJson();
		json.data = null;
		json.msg = msgOk;
		json.code = codeOk;
		
		if (params != null && params.length >= 1 && params[0] != null) {
			json.data = params[0];
		}
		
		if (params != null && params.length >= 2 && params[1] != null && !"".equals(params[1])) {
			json.msg = params[1].toString();
		}
		
		if (params != null && params.length >= 3 && params[2] != null) {
			json.code = Integer.parseInt(params[2].toString());
		}

		return json;
	}
	
	/**
	 * 封装接口返回数据（失败）
	 * @param params 可选返参集：params[0]设置msg（默认：操作失败！），params[1]设置code（默认：1），params[2]设置data（默认：空集）
	 * @return
	 */
	public static Object returnNG(Object... params) {
		ApiJson json = new ApiJson();
		json.msg = msgNg;
		json.code = codeNg;
		json.data = null;

		if (params != null && params.length >= 1 && params[0] != null && !"".equals(params[0])) {
			json.msg = params[0].toString();
		}

		if (params != null && params.length >= 2 && params[1] != null) {
			json.code = Integer.parseInt(params[1].toString());
		}

		if (params != null && params.length >= 3 && params[2] != null) {
			json.data = params[2];
		}
		
		return json;
	}

}