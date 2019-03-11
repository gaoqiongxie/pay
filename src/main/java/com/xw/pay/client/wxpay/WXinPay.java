package com.xw.pay.client.wxpay;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.xw.pay.client.PayClientFactory;
import com.xw.pay.entity.PayNotifyReq;
import com.xw.pay.entity.PayReq;

/**
 * 微信下单支付
 * 
 */
public abstract class WXinPay {
	
	public static final String TRADETYPEWXPUB = "JSAPI";
	public static final String TRADETYPEWXWAP = "MWEB";
	
	public static final String RETURN_WAP = "mweb_url";
	public static final String RETURN_PUB = "prepay_id";
	
	public static final String RETURN_CODE = "return_code";
	public static final String RETURN_MSG = "return_msg";
	
	public static final String RETURN_FAIL = "FAIL";
	public static final String RETURN_SUCCESS = "SUCCESS";
	/**
	 * 统一下单
	 * 
	 * @param orderNo
	 * @param subject
	 * @param body
	 * @param totalAmount
	 * @param successUrl
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected Map<String, String> unifiedOrder(PayReq req, String tradeType) {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("body", req.getBody());
		reqData.put("out_trade_no", req.getOrderNo());
		reqData.put("device_info", "");
		reqData.put("fee_type", "CNY");
		reqData.put("total_fee", (int)(req.getAmount()*100)+"");
		reqData.put("spbill_create_ip", req.getUserIp());
		reqData.put("notify_url", WXinPayConfig.notifyUrl);
		reqData.put("trade_type", tradeType);
		reqData.put("product_id", "");//扫码支付必传
		reqData.put("openid", StringUtils.isEmpty(req.getOpenId())?"":req.getOpenId());
		reqData.put("attach", req.getSubject());
		//[appid, body, device_info, fee_type, mch_id, nonce_str, notify_url, openid, out_trade_no, product_id, sign_type, spbill_create_ip, total_fee, trade_type]
		try {
			return PayClientFactory.instanceWxPayClient().unifiedOrder(reqData);
		} catch (Exception e) {
			//"调用微信支付请求失败：" + e);
			e.printStackTrace();
		}
		return reqData;

	}

	/**
	 * 校验是否来自微信的回调函数
	 * 
	 * @param params
	 * @return
	 */
	public static boolean validatePayNotify(Map<String, String> params) {
		try {
			if (!PayClientFactory.instanceWxPayClient().isPayResultNotifySignatureValid(params)) {// 签名错误，如果数据里没有sign字段，也认为是签名错误
				//"支付失败 - order_no:" + params.get("out_trade_no"));
				return false;
			}
		} catch (Exception e) {
			//"支付异常 - order_no:" + params.get("out_trade_no") + ";exception:" + e);
			return false;
		}
		return true;
	}
	
	/**
	 * 封装回调函数请求
	 * 
	 * @param params
	 * @return
	 */
	public static PayNotifyReq convertNotifyReq(Map<String, String> params){
		PayNotifyReq req = new PayNotifyReq();
		req.setAmount(Float.valueOf(params.get("total_fee").toString()) / 100);
		req.setOrder_no(params.get("out_trade_no"));
		req.setSubject(params.get("attach"));
		req.setTransaction_no(params.get("transaction_id").toString());
		return req;
	}
}
