package com.cloopen;

import com.cloopen.rest.sdk.CCPRestSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信发送接口
 */
public class RestSms {
	private final static Logger log = LoggerFactory.getLogger(RestSms.class);

	//生成环境：https://app.cloopen.com:8883   
	//开发环境：https://sandboxapp.cloopen.com:8883
	private static String ACCOUNTSID = "8aaf0708635e4ce001636d7b5cb40ac1";
	private static String AUTHTOKEN = "e558c2be8045491095d9f686c998be91";
	private static String APPID = "8aaf0708635e4ce001636d7b5d140ac8";

	public static final String templateId = "";

	/**
	 * 发送方法 其他方法同理 返回1都是提交成功 -1发送失败
	 */
	public static Map<String, Object> send(String msg, String... phone) {
		HashMap<String, Object> result = null; 
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");//初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(ACCOUNTSID, AUTHTOKEN);// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(APPID);// 初始化应用ID
		StringBuffer telPhone = new StringBuffer();
		for (int i = 0; i < phone.length; i++) {
			telPhone.append(phone[i]+",");
		}
		result = restAPI.sendTemplateSMS(telPhone.substring(0, telPhone.length()-1).toString(),"252419" ,new String[]{msg,"10分钟"});
		if("000000".equals(result.get("statusCode"))){
//			//正常返回输出data包体信息（map）
//			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
//			Set<String> keySet = data.keySet();
//			for(String key:keySet){
//				Object object = data.get(key);
//			}
		}else{
			log.error("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
		return result;
	}	
}
