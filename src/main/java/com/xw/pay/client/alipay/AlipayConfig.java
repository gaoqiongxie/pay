package com.xw.pay.client.alipay;


public class AlipayConfig {
	
	 /**
     * 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/
     * pidAndKey.htm
     */
	public static String partner = null;//("ALiPay.partner");
	
	// 支付宝appId
	public static String appId = null;//("ALiPay.appId");
	
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = null;//("ALiPay.notifyUrl");
	
 	 // 支付宝公钥
 	public static String PUBLICKEY = null;//("ALiPay.aliPublicKey");
    
    // 支付宝私钥
    public static String privateKey = null;//("ALiPay.privateKey");
    
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "";
	
	
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = null;//("ALiPay.returnUrl");
	
	// 支付宝网关
    public static String aLiUrl = "https://openapi.alipay.com/gateway.do";
    
    // 编码
 	public static String CHARSET = "UTF-8";
	
 	 // 返回格式
 	public static String FORMAT = "json";
 	
    // 加密方式
    public static String SIGNTYPE = "RSA2";
    
    // 超时时间
    public static String overTime = "30m";

    // 支付宝消息验证地址
    public static String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

    //失败返回
    public static String FAILURE_CODE = "fail";

    //成功返回
    public static String SUCCESS_CODE = "success";
    
    
}
