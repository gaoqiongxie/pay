package com.xw.pay.strategy.impl;

import org.springframework.stereotype.Component;

import com.xw.pay.client.alipay.AliPay;
import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.strategy.PayStrategy;

/**
 * 支付宝网页支付
 * 
 * @author j.z
 *
 */
@Component
public class ALiPayWebStrategy extends AliPay implements PayStrategy{

	@Override
	public Charge payExcute(PayReq req) {
		Charge charge = new Charge();
		charge.setPrePayInfo(unifiedOrder(req, WEB_TYPE).replace("<script>document.forms[0].submit();</script>", "").
				replace("<form name=\"punchout_form\"", "<form id=\""+req.getChannel()+"_form\" name=\"punchout_form\""));
		charge.setChannel(req.getChannel());
		return charge;
	}
}
