package com.xw.pay.client.wxpay;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class WXinPayConfig implements WXPayConfig {
	private byte[] certData;
	private static final String appId = null;//("WXinPay.appId");
	private static final String mchId = null;//("WXinPay.mchId");
	private static final String key = null;//("WXinPay.key");
	public static final String notifyUrl = null;//("WXinPay.notifyUrl");
	public static final String cert = null;//("WXinPay.cert");

	public WXinPayConfig() {
		certData = cert.getBytes();
	}

	@Override
	public String getAppID() {
		return appId;
	}

	@Override
	public String getMchID() {
		return mchId;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 10000;
	}

}
