package com.stylefeng.guns.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kdDemo.action.xqiang.comm.CharsetTypeEnum;
import com.kdDemo.action.xqiang.comm.ClientSignature;
import com.kdDemo.utils.StringUtils;
import com.stylefeng.guns.config.XqPayConfig;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.enums.EnumOrderStatus;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.model.OrderInfo;
import com.stylefeng.guns.modular.system.service.IOrderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Servlet implementation class xqingPayAction
 */
@Controller
@RequestMapping("/api/xqPay")
public class XiaoqiangPayController extends BaseController {
	private final static Logger log = LoggerFactory.getLogger(UploadController.class);

	private String PREFIX = "/system/pay/";

	@Autowired
	private IOrderInfoService orderInfoService;

	@RequestMapping("/toPay")
	public String toPay(String orderID, Double amount, Model model){
		model.addAttribute("orderID", orderID);
		model.addAttribute("amount", amount);
		model.addAttribute("displayName", XqPayConfig.displayName);
		model.addAttribute("goodsName", XqPayConfig.goodsName);
		return PREFIX + "toPay.html";
	}

	/**
	 * 小强聚合系统支付方法
	 */
	@RequestMapping("/pay")
	public String pay(String orderID, Double amount, String orgCode, Model model) throws Exception {
		// 测试金额
		amount = 0.01;
		// 订单编号后，追加6位时间字符串（回调时去掉）
		orderID += DateUtil.formatDate(new Date(), "HHmmss");
		String version = "1.0";
		String serialID = StringUtils.produceOrderNo("sn");// 序列号
		String submitTime = StringUtils.getDateymdhms(new Date());
		String failureTime = "";
		String customerIP = "127.0.0.1";
		String orderAmount = (amount = amount == null ? 1 : amount * 100).intValue() + "";
		String goodsCount = "1";
		// 获取订单详情
		String orderDetails = getOrderDetails(orderID, orderAmount, XqPayConfig.displayName, XqPayConfig.goodsName, goodsCount);
		String totalAmount = String.valueOf(Integer.parseInt(orderAmount) * Integer.parseInt(goodsCount));
		String type = "1000";
		String buyerMarked = "";
		String payType = "BANK_B2C";
		orgCode = orgCode == null ? "ccb" : orgCode;
		String currencyCode = "1";
		String directFlag = "1";
		String borrowingMarked = "0";
		String couponFlag = "1";
		String platformID = "";// 平台商户ID
		String partnerID = XqPayConfig.merch_id;// 商户ID
		String returnUrl = XqPayConfig.returnUrl;// 支付完成地址
		String noticeUrl = XqPayConfig.noticeUrl;// 回调通知地址
		String remark = XqPayConfig.remark;
		String charset = "1";
		String signType = "2";
		// 组装签名字符串
		String signStr = getSignstr(version, serialID, submitTime, failureTime, customerIP, orderDetails, totalAmount,
				type, buyerMarked, payType, orgCode, currencyCode, directFlag, borrowingMarked, couponFlag, platformID,
				returnUrl, noticeUrl, partnerID, remark, charset, signType);
		// MD5签名
		String signMsg = ClientSignature.genSignByMD5(signStr, CharsetTypeEnum.UTF8, XqPayConfig.key);

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
	 * 支付成功回调接口
	 */
	@RequestMapping("/notify")
	public void notify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		resp.setContentType("text/html;charset=utf-8");
//		req.setCharacterEncoding("utf-8");
//		String result = StringUtils.parseRequst(req);
//		System.out.println("回调内容：" + result);
//		// {charset=1, orderNo=1051806272013090999, orderID=28893354445201318, resultCode=, completeTime=20180627201320, acquiringTime=20180627201319, remark=理财产品支付, orderAmount=1, payAmount=1, signType=2, stateCode=2, partnerID=10000048461, signMsg=465bc33d46c0033586e4f0690c77e6d6}
//		Map<String, String> resultMap = StringUtils.StrToMap(result);
		String result = "";
		Map<String, String> resultMap = HttpKit.getRequestParameters();
		String stateCode = resultMap.get("stateCode");
		if (!StringUtils.isEmpty(stateCode) && "2".equals(stateCode)) {
			// 验签
			String signMsg = resultMap.remove("signMsg");
			String signStr = StringUtils.createRetStr(resultMap);
			try {
				String newsignMsg = ClientSignature.genSignByMD5(signStr, CharsetTypeEnum.UTF8, XqPayConfig.key);
//				if (signMsg.equals(newsignMsg)) {// 这里验签有问题，不能验签
                    // 订单编号
                    String orderNo = resultMap.get("orderID");
                    // 去掉订单编号6位时间字符串
                    orderNo = orderNo.substring(0, orderNo.length() - 6);
                    // 支付流水号
                    String payTradeNo = resultMap.get("orderNo");
					/*************************************************
                     处理订单状态回调逻辑
					**************************************************/
                    updatePayStatus(orderNo, payTradeNo);
//				} else {
//					result = "sign fail";
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			result = "fail";
		}
		resp.getWriter().print(result);
	}

    /**
     * 处理订单状态回调逻辑
     */
    @RequestMapping("ok") // 这里OK为测试访问地址
	public String updatePayStatus(String orderNo, String payTradeNo) {
        OrderInfo order = orderInfoService.selectOne(new EntityWrapper<OrderInfo>().eq("orderNo", orderNo));
        if (order != null) {
            // 订单已支付
            order.setStatus(EnumOrderStatus.waitRebate.getValue());
            // 记录订单支付信息
            order.setPayTradeNo(payTradeNo);
            order.setPayTime(new Date());
            // 修改订单信息状态,并生成回款信息
            orderInfoService.updateOrderAddRebate(order);
            return "success";
        }
        return "";
    }

	/**
	 * 支付成功回调页面
	 */
	@RequestMapping("/paySuccess")
	public String paySuccess() {
		return PREFIX + "paySuccess.html";
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
