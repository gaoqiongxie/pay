package com.xw.pay.strategy.impl;

import org.springframework.stereotype.Component;

import com.xw.pay.client.alipay.AliPay;
import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.strategy.PayStrategy;

/**
 * 支付宝app支付
 * 
 */
@Component
public class ALiPayAppStrategy extends AliPay implements PayStrategy{

	@Override
	public Charge payExcute(PayReq req) {
		Charge charge = new Charge();
		charge.setPrePayInfo(unifiedOrder(req, APP_TYPE));
		charge.setChannel(req.getChannel());
		return charge;
	}

	

}
