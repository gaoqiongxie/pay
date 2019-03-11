package com.xw.pay.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Charge implements Serializable {
	private String channel;
	
	private String prePayInfo;//支付宝web/wap预付form表单//支付宝app/微信公众号//微信h5-url
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPrePayInfo() {
		return prePayInfo;
	}

	public void setPrePayInfo(String prePayInfo) {
		this.prePayInfo = prePayInfo;
	}

}
