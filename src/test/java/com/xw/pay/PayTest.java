package com.xw.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xw.pay.client.alipay.AliPay;
import com.xw.pay.client.wxpay.WXPayUtil;
import com.xw.pay.client.wxpay.WXinPay;
import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayNotifyReq;
import com.xw.pay.entity.PayReq;
import com.xw.pay.entity.WebPayNo;
import com.xw.pay.enums.PayChannelEnum;
import com.xw.pay.strategy.PayStrategyFactory;
import com.xw.pay.util.PayUtil;

public class PayTest extends PayApplicationTests {

	private static Logger logger = Logger.getLogger(PayTest.class);
	
	private static final String WEIXIN_PAY_NOTIFY_RETURN_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[check fail!]]></return_msg></xml>";
	private static final String WEIXIN_PAY_NOTIFY_RETURN_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

	@Autowired
	private PayStrategyFactory payClient;
	
	@Test
    public void testPayStrategyFactory() {
    	System.out.println(payClient);
    }

	/**
	 * 请求支付接口
	 */
	@SuppressWarnings("unused")
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
	
	/**
	 * TODO 支付宝回调
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String alipayNotifyTest(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> params = new HashMap<String, String>();//获取支付宝POST过来反馈信息
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			params.put(name, valueStr);
		}
		
		try {
			if(ajaxReturn(params, AliPay.class.getSimpleName())) {
				return "SUCCESS";
			}else {
				logger.error("支付请求校验失败！");
				return "FAIL";
			}
		}catch(Exception e) {
			return "FAIL";
		}
	}
	
	/**
	 * TODO 微信支付回调
	 * @param request
	 * @param response
	 */
	public void wxNotifyTest(HttpServletRequest request, HttpServletResponse response){
		InputStream is = null;
		String resStr = null;
		Map<String, String> params = null;
		try {
			params = WXPayUtil.xmlToMap(inputStream2String(request.getInputStream(), "UTF-8"));
			if(ajaxReturn(params, WXinPay.class.getSimpleName())) {
				resStr = WEIXIN_PAY_NOTIFY_RETURN_SUCCESS;
			}else {
				logger.error("支付请求校验失败！");
				resStr = WEIXIN_PAY_NOTIFY_RETURN_FAIL;
			}
		} catch (IOException e) {
			logger.error("获取请求数据异常：" + e.getMessage());
			resStr = WEIXIN_PAY_NOTIFY_RETURN_FAIL;
		} catch (Exception e) {
			logger.error("内容格式化失败！" + e);
			resStr = WEIXIN_PAY_NOTIFY_RETURN_FAIL;
		}
		
		try {
			response.getWriter().write(resStr!=null?resStr:WEIXIN_PAY_NOTIFY_RETURN_FAIL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * InputStream 流转换成 String 字符串
	 * @param inStream
	 * @param encoding
	 * @return
	 */
	private String inputStream2String(InputStream inStream, String encoding) {
		String result = null;
		try {
			if (inStream != null) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] tempBytes = new byte[1024];
				int count = -1;
				while ((count = inStream.read(tempBytes, 0, 1024)) != -1) {
					outStream.write(tempBytes, 0, count);
				}
				tempBytes = null;
				outStream.flush();
				result = new String(outStream.toByteArray(), encoding);
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	private boolean ajaxReturn(Map<String, String> params, String clazz) {
		//"支付回调发起："+params.toString());
		if(AliPay.class.getSimpleName().equals(clazz)) {
			if (!AliPay.validatePayNotify(params)) {
				//"支付宝支付校验失败 - 请求数据:" + new Gson().toJson(params));
				return false;
			}
			return notify(AliPay.convertNotifyReq(params));
		}else if(WXinPay.class.getSimpleName().equals(clazz)) {
			if (!WXinPay.validatePayNotify(params)) {
				//"微信支付校验失败 - 请求数据:" + new Gson().toJson(params));
				return false;
			}
			return notify(WXinPay.convertNotifyReq(params));
		}
		return false;
	}
	
	/**
	 *  回调函数业务逻辑
	 * @param req
	 * @return
	 */
//	@Transactional
	private boolean notify(PayNotifyReq req) {
		// 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
		//"order_no:" + req.getOrder_no() + "  amount:" + req.getAmount());

		//校验是否存在该订单 req.getOrder_no()
		WebPayNo webPayNo = null;
		//校验订单状态
		if(!"init".equals(webPayNo.getDataState())) {
			//"订单" + req.getOrder_no() + "状态已经变更" + webPayNo.getDataState() );
			return true;
		}
		
		//("更新比价|直销订单信息:");
		//根据订单类型处理订单 订单金额req.getAmount() 对否存在相应订单 订单状态
		// 更新支付流水号req.getTransaction_no() 和状态为已支付

		
		try {
			//支付完成记录日志
		} catch (Exception e) {
			//异常处理
		}

		return true;

	}
	
}
