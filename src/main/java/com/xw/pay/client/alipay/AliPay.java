package com.xw.pay.client.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.xw.pay.client.PayClientFactory;
import com.xw.pay.entity.PayNotifyReq;
import com.xw.pay.entity.PayReq;
import com.xw.pay.util.PayUtil;

/**
 * 支付宝下单支付
 *
 */
public abstract class AliPay {
	public static final String WEB_TYPE = "FAST_INSTANT_TRADE_PAY";
	public static final String WAP_TYPE = "QUICK_WAP_WAY";
	public static final String APP_TYPE = "QUICK_MSECURITY_PAY";
	
	/**
	 * 统一下单
	 * 
	 * @param req
	 * @param tradeType
	 * @return
	 */
	protected String unifiedOrder(PayReq req, String tradeType) {
		String resp = "";
		try {
			switch (tradeType) {
			case APP_TYPE:
				resp = PayClientFactory.instanceAliPayClient().sdkExecute(convertAppPayRequest(req, APP_TYPE)).getBody();
				break;
			case WEB_TYPE:
				resp = PayClientFactory.instanceAliPayClient().pageExecute(convertPagePayRequest(req, tradeType)).getBody();
				break;
			case WAP_TYPE:
				resp = PayClientFactory.instanceAliPayClient().pageExecute(convertWapPayRequest(req, tradeType)).getBody();
				break;
			default:
				break;
			}

		} catch (AlipayApiException e) {
			//("调用支付宝支付请求失败：" + e);
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 创建wap支付请求
	 * @param req
	 * @param type
	 * @return
	 */
	private AlipayTradeWapPayRequest convertWapPayRequest(PayReq req, String type) {
		AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
		alipayRequest.setReturnUrl(req.getSuccessUrl());
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);// 在公共参数中设置回跳和通知地址
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(req.getOrderNo());
		model.setSubject(req.getSubject());
		model.setTotalAmount(Float.toString(req.getAmount()));
		model.setBody(req.getBody());
		model.setTimeoutExpress(AlipayConfig.overTime);
		model.setProductCode(type);
		model.setSellerId(AlipayConfig.partner);
		alipayRequest.setBizModel(model);
		return alipayRequest;
	}
	
	/**
	 * 创建网页支付请求
	 * @param req
	 * @param type
	 * @return
	 */
	private AlipayTradePagePayRequest convertPagePayRequest(PayReq req, String type) {
		AlipayTradePagePayRequest alipayRequest=new AlipayTradePagePayRequest();
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		alipayRequest.setReturnUrl(req.getSuccessUrl());
		AlipayTradePagePayModel model = new AlipayTradePagePayModel();
		model.setOutTradeNo(req.getOrderNo());
		model.setSubject(req.getSubject());
		model.setTotalAmount(Float.toString(req.getAmount()));
		model.setBody(req.getBody());
		model.setTimeoutExpress(AlipayConfig.overTime);
		model.setProductCode(type);
		alipayRequest.setBizModel(model);
		
		return alipayRequest;
	}

	/**
	 * 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay<br>
	 * 请求封装
	 * 
	 * @param req
	 * @param type
	 * @return
	 */
	private AlipayTradeAppPayRequest convertAppPayRequest(PayReq req, String type) {
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();// (model和biz_content同时存在的情况下取biz_content)。
		model.setBody(req.getBody());
		model.setSubject(req.getSubject());
		model.setOutTradeNo(req.getOrderNo());
		model.setTimeoutExpress(AlipayConfig.overTime);
		model.setTotalAmount(String.valueOf(PayUtil.getBigDecimal(req.getAmount())));
		model.setProductCode(type);
		request.setBizModel(model);
		request.setNotifyUrl(AlipayConfig.notify_url);
		return request;
	}

	/**
	 * 封装回调函数请求
	 * 
	 * @param params
	 * @return
	 */
	public static PayNotifyReq convertNotifyReq(Map<String, String> params) {
		PayNotifyReq req = new PayNotifyReq();
		req.setAmount(Float.valueOf(params.get("total_amount")));
		req.setOrder_no(params.get("out_trade_no"));
		req.setSubject(params.get("subject"));
		req.setTransaction_no(params.get("trade_no"));
		return req;
	}

	/**
	 * 校验是否来自阿里的回调请求
	 * 
	 * @param params
	 * @return
	 */
	public static boolean validatePayNotify(Map<String, String> params) {
		try {// 使用支付宝公钥验签
			boolean isSignTrue = AlipaySignature.rsaCheckV1(params, AlipayConfig.PUBLICKEY, AlipayConfig.CHARSET,
					AlipayConfig.SIGNTYPE);
			if (!isSignTrue) {
				return false;
			}
		} catch (AlipayApiException e1) {
			//"支付宝公钥验签发生错误：" + e1);
			return false;
		}

		String notify_id = params.get("notify_id");
		// 验证 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。 验证是否来自支付宝的通知失败
		if (StringUtils.isEmpty(notify_id) || !verifyResponse(notify_id).equals("true")) {
			//"非支付宝通知-notify_id:" + notify_id);
			return false;
		}
		if (!AlipayConfig.appId.equals(params.get("app_id"))) {
			//"非支付宝通知-app_id:" + params.get("app_id"));
			return false;
		}

		String trade_status = params.get("trade_status");
		if (!"TRADE_FINISHED".equals(trade_status) && !"TRADE_SUCCESS".equals(trade_status)) {
			//"非支付宝通知-trade_status:" + trade_status);
			return false;
		}
		return true;

	}
	
	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static  String verifyResponse(String notify_id) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String partner = AlipayConfig.partner;
		String veryfy_url = AlipayConfig.HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			//"检查通知时发生错误：" + e);
			inputLine = "";
		}

		return inputLine;
	}
}
