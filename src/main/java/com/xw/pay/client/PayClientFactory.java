package com.xw.pay.client;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.github.wxpay.sdk.WXPay;
import com.xw.pay.client.alipay.AlipayConfig;
import com.xw.pay.client.wxpay.WXinPayConfig;

/**
 * ali wx 客户端生成
 * 
 */
public class PayClientFactory {
	private static final String FORMAT = "json";
	private static final String CHARSET = "utf-8";
	private PayClientFactory() {

	}

	private static class PayHolder {
		private static AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.aLiUrl, AlipayConfig.appId, AlipayConfig.privateKey, FORMAT,
				CHARSET, AlipayConfig.PUBLICKEY, AlipayConfig.SIGNTYPE);
		private static WXPay wxPay = new WXPay(new WXinPayConfig());
		/**
		 * for Test
		 */
		/*private static AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", AlipayConfig.appId, AlipayConfig.privateKey, FORMAT,
				CHARSET, AlipayConfig.PUBLICKEY, AlipayConfig.SIGNTYPE);
		private static WXPay wxPay = new WXPay(new WXinPayConfig(), SignType.MD5, true);*///测试使用
	}
	
	/**
	 * 实例化阿里支付客户端
	 * 
	 * @return
	 */
	public static AlipayClient instanceAliPayClient() {
		return PayHolder.alipayClient;
	}

	/**
	 * 实例化微信支付客户端
	 * 
	 * @return
	 */
	public static WXPay instanceWxPayClient() {
		return PayHolder.wxPay;
	}
}
