package com.xw.pay.strategy;

import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;

/**
 * 支付策略接口
 *
 */
public interface PayStrategy {

	public Charge payExcute(PayReq req);
}
