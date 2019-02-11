package com.stylefeng.guns.rest.modular.system.controller;

import com.kdDemo.action.xqiang.comm.CharsetTypeEnum;
import com.kdDemo.action.xqiang.comm.ClientSignature;
import com.kdDemo.action.xqiang.config.xQingConfig;
import com.kdDemo.utils.StringUtils;
import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Servlet implementation class xqingPayAction
 */
@Controller
@RequestMapping("/api/xqPay")
public class XiaoqiangPayController extends BaseController {

	private String PREFIX = "/pay/";

	@RequestMapping("/test")
	public String test(){
		return PREFIX + "test.html";
	}

	/**
	 * 小强聚合系统支付方法
	 */
	@RequestMapping("/pay")
	public String pay(String orderID, Double amount, String orgCode, Model model) throws Exception {
		String version = "1.0";
		String serialID = StringUtils.produceOrderNo("sn");// 序列号
		String submitTime = StringUtils.getDateymdhms(new Date());
		String failureTime = "";
		String customerIP = "127.0.0.1";
		String orderAmount = (amount = amount == null ? 1 : amount * 100).intValue() + "";
		String goodsCount = "1";
		// 获取订单详情
		String orderDetails = getOrderDetails(orderID, orderAmount, xQingConfig.displayName, xQingConfig.goodsName, goodsCount);
		String totalAmount = String.valueOf(Integer.parseInt(orderAmount) * Integer.parseInt(goodsCount));
		String type = "1000";
		String buyerMarked = "";
		String payType = "BANK_B2C";
//		String orgCode = orgCode;
		String currencyCode = "1";
		String directFlag = "1";
		String borrowingMarked = "0";
		String couponFlag = "1";
		String platformID = "";// 平台商户ID
		String partnerID = xQingConfig.merch_id;// 商户ID
		String returnUrl = xQingConfig.returnUrl;// 支付完成地址
		String noticeUrl = xQingConfig.noticeUrl;// 回调通知地址
		String remark = xQingConfig.remark;
		String charset = "1";
		String signType = "2";
		// 组装签名字符串
		String signStr = getSignstr(version, serialID, submitTime, failureTime, customerIP, orderDetails, totalAmount,
				type, buyerMarked, payType, orgCode, currencyCode, directFlag, borrowingMarked, couponFlag, platformID,
				returnUrl, noticeUrl, partnerID, remark, charset, signType);
		// MD5签名
		String signMsg = ClientSignature.genSignByMD5(signStr, CharsetTypeEnum.UTF8, xQingConfig.key);

		model.addAttribute("version", version);
		model.addAttribute("serialID", serialID);
		model.addAttribute("submitTime", submitTime);
		model.addAttribute("failureTime", failureTime);
		model.addAttribute("customerIP", customerIP);
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("totalAmount", totalAmount);
		model.addAttribute("type", type);
		model.addAttribute("buyerMarked", buyerMarked);
		model.addAttribute("payType", payType);
		model.addAttribute("orgCode", orgCode);
		model.addAttribute("currencyCode", currencyCode);
		model.addAttribute("directFlag", directFlag);
		model.addAttribute("borrowingMarked", borrowingMarked);
		model.addAttribute("couponFlag", couponFlag);
		model.addAttribute("platformID", platformID);
		model.addAttribute("returnUrl", returnUrl);
		model.addAttribute("noticeUrl", noticeUrl);
		model.addAttribute("partnerID", partnerID);
		model.addAttribute("remark", remark);
		model.addAttribute("charset", charset);
		model.addAttribute("signType", signType);
		model.addAttribute("signMsg", signMsg);

		return PREFIX + "pay.html";
	}

	/**
	 * 获取签名字符串
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
	 * @param orderID 订单号
	 * @param orderAmount 订单明细金额
	 * @param displayName 下单商户显示名
	 * @param goodsName 商品名称
	 * @param goodsCount 商品数量
	 * @return
	 */
	private static String getOrderDetails(String orderID, String orderAmount, String displayName, String goodsName, String goodsCount) {
		StringBuffer orderBuffer = new StringBuffer();
		orderBuffer.append(orderID + ",");
		orderBuffer.append(orderAmount + ",");
		orderBuffer.append(displayName + ",");
		orderBuffer.append(goodsName + ",");
		orderBuffer.append(goodsCount);
		return orderBuffer.toString();
	}
}
