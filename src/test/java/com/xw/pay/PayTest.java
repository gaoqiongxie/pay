package com.xw.pay;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.enums.PayChannelEnum;
import com.xw.pay.strategy.PayStrategyFactory;
import com.xw.pay.util.PayUtil;

public class PayTest extends PayApplicationTests {

	private static Logger logger = Logger.getLogger(PayTest.class);

	@Autowired
	private PayStrategyFactory payClient;
	
	@Test
    public void testPayStrategyFactory() {
    	System.out.println(payClient);
    }

	/**
	 * 请求支付接口
	 */
	@Test
	public void testCreatePay() {
		// 检查重复支付
		// 检查支付金额,检查并且修改订单状态,插入待支付订单

		String orderNo = PayUtil.getOrderNum();

		PayReq req = new PayReq();
//		req.setAmount(amount);
//		req.setBody(body);
//		req.setChannel(channel);
//		req.setOrderNo(orderNo);
//		req.setSubject(subject);
//		req.setUserIp(clientIp);
//		req.setOpenId(wxOpenId);
//		req.setSuccessUrl(successUrl);
//		req.setCancelUrl(cancelUrl);

		String strategy = PayChannelEnum.getStrategyByValue("alipay_wap");// 支付渠道
		Charge charge = null;
		try {
			charge = payClient.payExcute(strategy, req);
		} catch (Exception e) {

			e.printStackTrace();
		}

		// 添加支付记录
	}
}
