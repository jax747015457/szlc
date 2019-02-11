package com.stylefeng.guns.config;

/**
 * 小强支付配置文件
 */
public class XqPayConfig {
	public static String key = "30820122300d06092a864886f70d01010105000382010f003082010a02820101008919e3d1a94a015ae5ef5d1e2639354f6be08a491af084b80cf30392441e65109048b251a5704ca050ff775e12dc044c0466999c97bc185df52a44f6e0ab8e690b067e460d34238e64cca0f4250cf41c76a4bfd36d22bd30b1cb3699a42819bf86182a2f7c279e1661b98aa18939768dbc3aef673a054a072c2b78309d4dc2a592614186fa051eb7561faace21fb9914bd7c141a4d171c32523b9c8988d7ed34aec9c4618a834b4776864f30074ec1fb5844ba56dd7ef308e6be5b0aa3120f3bcd72f9c9b0e3e4de2a4361d49935a7f8dd6f352d5bb440d9f09615ef15078d82a8941ff168c24df66090c3ee06154cfc1fcdf7cc9e24878bda08facf823f88350203010001";
	public static String test_url = "https://test.aklpay.com/website/pay.htm";
	public static String run_url = "https://www.aklpay.com/website/pay.htm";
	public static String merch_id = "10000048461";
	public static String displayName = "商品订单";
	public static String goodsName = "商品购买";
	public static String returnUrl = "http://103.228.108.200:8070/szlc/api/xqPay/paySuccess";
	public static String noticeUrl = "http://103.228.108.200:8070/szlc/api/xqPay/notify";
	public static String remark = "xqpay";
}
