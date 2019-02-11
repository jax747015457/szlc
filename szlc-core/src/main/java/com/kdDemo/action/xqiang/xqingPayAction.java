package com.kdDemo.action.xqiang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kdDemo.action.xqiang.comm.CharsetTypeEnum;
import com.kdDemo.action.xqiang.comm.ClientSignature;
import com.kdDemo.action.xqiang.config.xQingConfig;
import com.kdDemo.utils.StringUtils;

/**
 * Servlet implementation class xqingPayAction
 */
@WebServlet("/xqingPayAction")
public class xqingPayAction extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		req.setCharacterEncoding("utf-8");
		String result = "";
		try {
			result = pay(req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.getWriter().write(result);
	}

	/**
	 * 小强聚合系统支付方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String pay(HttpServletRequest req) throws Exception {
		String version = "1.0";
		String serialID = StringUtils.produceOrderNo("sn");// 序列号
		String submitTime = StringUtils.getDateymdhms(new Date());
		String failureTime = "";
		String customerIP = "127.0.0.1";
		String orderAmount = req.getParameter("orderAmount");
		String goodsCount = req.getParameter("goodsCount");
		// 获取订单详情
		String orderDetails = getOrderDetails(req, orderAmount, goodsCount);
		String totalAmount = String.valueOf(Integer.parseInt(orderAmount) * Integer.parseInt(goodsCount));
		String type = "1000";
		String buyerMarked = "";
		String payType = req.getParameter("payType");
		String orgCode = req.getParameter("orgCode");
		String currencyCode = "1";
		String directFlag = req.getParameter("directFlag");
		String borrowingMarked = "0";
		String couponFlag = "1";
		String platformID = "";// 平台商户ID
		String partnerID = req.getParameter("partnerID");// 商户ID
		String returnUrl = req.getParameter("returnUrl");// 支付完成地址
		String noticeUrl = req.getParameter("noticeUrl");// 回调通知地址
		String remark = req.getParameter("remark");
		String charset = "1";
		String signType = "2";
		// 组装签名字符串
		String signStr = getSignstr(version, serialID, submitTime, failureTime, customerIP, orderDetails, totalAmount,
				type, buyerMarked, payType, orgCode, currencyCode, directFlag, borrowingMarked, couponFlag, platformID,
				returnUrl, noticeUrl, partnerID, remark, charset, signType);
		// MD5签名
		String signMsg = ClientSignature.genSignByMD5(signStr, CharsetTypeEnum.UTF8, xQingConfig.key);
		// 构建请求参数
		Map<String, String> resquestMap = new LinkedHashMap<>();
		resquestMap.put("version", version);
		resquestMap.put("serialID", serialID);
		resquestMap.put("submitTime", submitTime);
		resquestMap.put("failureTime", failureTime);
		resquestMap.put("customerIP", customerIP);
		resquestMap.put("orderDetails", orderDetails);
		resquestMap.put("totalAmount", totalAmount);
		resquestMap.put("type", type);
		resquestMap.put("buyerMarked", buyerMarked);
		resquestMap.put("payType", payType);
		resquestMap.put("orgCode", orgCode);
		resquestMap.put("currencyCode", currencyCode);
		resquestMap.put("directFlag", directFlag);
		resquestMap.put("borrowingMarked", borrowingMarked);
		resquestMap.put("couponFlag", couponFlag);
		resquestMap.put("platformID", platformID);
		resquestMap.put("returnUrl", returnUrl);
		resquestMap.put("noticeUrl", noticeUrl);
		resquestMap.put("partnerID", partnerID);
		resquestMap.put("remark", remark);
		resquestMap.put("charset", charset);
		resquestMap.put("signType", signType);
		resquestMap.put("signMsg", signMsg);
		// form表单请求到接口地址
		StringBuffer strhtml = new StringBuffer();
		List<String> keys = new ArrayList<String>(resquestMap.keySet());
		strhtml.append("正在跳转。。。<br/>" + "<form id=\"xqpaysubmit\" name=\"xqpaysubmit\" action=\"" + xQingConfig.run_url
				+ "\" method = \"post\">");
		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) resquestMap.get(name);
			if (value != null && value != "") {
				strhtml.append("<input type=\"text\" name=\"" + name + "\" value=\"" + value + "\"/><br/>");
			}
		}
		strhtml.append("<input type=\"submit\" value=\"" + "确认" + "\" style=\"display:inline;\"></form>");
		// strhtml.append("<script>document.forms['xqpaysubmit'].submit();</script>");
		return strhtml.toString();
	}

	/**
	 * 获取签名字符串
	 * 
	 * @param version
	 * @param serialID
	 * @param submitTime
	 * @param failureTime
	 * @param customerIP
	 * @param orderDetails
	 * @param totalAmount
	 * @param type
	 * @param buyerMarked
	 * @param payType
	 * @param orgCode
	 * @param currencyCode
	 * @param directFlag
	 * @param borrowingMarked
	 * @param couponFlag
	 * @param platformID
	 * @param returnUrl
	 * @param noticeUrl
	 * @param partnerID
	 * @param remark
	 * @param charset
	 * @param signType
	 * @return signStr
	 */
	private static String getSignstr(String version, String serialID, String submitTime, String failureTime,
			String customerIP, String orderDetails, String totalAmount, String type, String buyerMarked, String payType,
			String orgCode, String currencyCode, String directFlag, String borrowingMarked, String couponFlag,
			String platformID, String returnUrl, String noticeUrl, String partnerID, String remark, String charset,
			String signType) {
		StringBuffer signBuffer = new StringBuffer();
		signBuffer.append("version=" + version);
		signBuffer.append("&serialID=" + serialID);
		signBuffer.append("&submitTime=" + submitTime);
		signBuffer.append("&failureTime=" + failureTime);
		signBuffer.append("&customerIP=" + customerIP);
		signBuffer.append("&orderDetails=" + orderDetails);
		signBuffer.append("&totalAmount=" + totalAmount);
		signBuffer.append("&type=" + type);
		signBuffer.append("&buyerMarked=" + buyerMarked);
		signBuffer.append("&payType=" + payType);
		signBuffer.append("&orgCode=" + orgCode);
		signBuffer.append("&currencyCode=" + currencyCode);
		signBuffer.append("&directFlag=" + directFlag);
		signBuffer.append("&borrowingMarked=" + borrowingMarked);
		signBuffer.append("&couponFlag=" + couponFlag);
		signBuffer.append("&platformID=" + platformID);
		signBuffer.append("&returnUrl=" + returnUrl);
		signBuffer.append("&noticeUrl=" + noticeUrl);
		signBuffer.append("&partnerID=" + partnerID);
		signBuffer.append("&remark=" + remark);
		signBuffer.append("&charset=" + charset);
		signBuffer.append("&signType=" + signType);
		return signBuffer.toString();
	}

	/**
	 * 获取订单明细信息
	 * 
	 * @return
	 */
	private static String getOrderDetails(HttpServletRequest req, String orderAmount, String goodsCount) {
		String orderID = req.getParameter("orderID");
		String displayName = req.getParameter("displayName");
		String goodsName = req.getParameter("goodsName");
		StringBuffer orderBuffer = new StringBuffer();
		orderBuffer.append(orderID + ",");
		orderBuffer.append(orderAmount + ",");
		orderBuffer.append(displayName + ",");
		orderBuffer.append(goodsName + ",");
		orderBuffer.append(goodsCount);
		return orderBuffer.toString();
	}
}
