package com.xw.pay.strategy.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.xw.pay.client.wxpay.WXinPay;
import com.xw.pay.client.wxpay.WXinPayConfig;
import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.strategy.PayStrategy;

/**
 * 微信公众号支付
 * 
 * @author j.z
 *
 */
@Component
public class WXinPayPubStrategy extends WXinPay implements PayStrategy {

	/**
	 * 预支付信息封装<br>
	 * --》注意签名字符串的顺序
	 */
	@Override
	public Charge payExcute(PayReq req) {
		Map<String, String> map = unifiedOrder(req, TRADETYPEWXPUB);
		if (RETURN_FAIL.equals(map.get(RETURN_CODE))) {
			//异常处理map.toString());
		}
		WXPayConfig config = new WXinPayConfig();
		Map<String, String> returnMap = new HashMap<String, String>();
		long timeStamp = System.currentTimeMillis() / 1000;
		String nonce = UUID.randomUUID().toString().replaceAll("-", "");
		// 再算签名
		String newPrepay_id = "prepay_id=" + map.get(WXinPay.RETURN_PUB);
		String args = "appId=" + config.getAppID() + "&nonceStr=" + nonce + "&package=" + newPrepay_id + "&signType=MD5"
				+ "&timeStamp=" + timeStamp + "&key=" + config.getKey();
		String paySign = null;
		try {
			paySign = WXPayUtil.MD5(args);
		} catch (Exception e) {
			//异常处理"md5加密异常" + e);
		}
		returnMap.put("appId", config.getAppID());
		returnMap.put("nonceStr", nonce);
		returnMap.put("package", newPrepay_id);
		returnMap.put("signType", "MD5");
		returnMap.put("timeStamp", timeStamp + "");
		returnMap.put("paySign", paySign);
		Charge charge = new Charge();
		charge.setPrePayInfo(new Gson().toJson(returnMap));
		charge.setChannel(req.getChannel());
		return charge;

	}

}
