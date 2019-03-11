package com.xw.pay.strategy.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.xw.pay.client.wxpay.WXinPay;
import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.strategy.PayStrategy;

/**
 * 微信h5支付
 * 
 * @author j.z
 *
 */
@Component
public class WXinPayH5Strategy extends WXinPay implements PayStrategy{

	@Override
	public Charge payExcute(PayReq req) {
		Map<String, String> map = unifiedOrder(req, TRADETYPEWXWAP);
		if(RETURN_FAIL.equals(map.get(RETURN_CODE))) {
			//异常处理map.get(RETURN_MSG));
		}
		Charge charge = new Charge();
		charge.setPrePayInfo(map.get(WXinPay.RETURN_WAP)+"&redirect_url=" + req.getSuccessUrl());
		charge.setChannel(req.getChannel());
		return charge;
	}

}
