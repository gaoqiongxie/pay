package com.xw.pay.enums;


public enum PayChannelEnum {
    /** 支付宝手机网页支付*/
    ALIPAY_WAP("alipay_wap", "ALiPayWapStrategy"),
    /** 支付宝电脑网站支付*/
    ALIPAY_PC_DIRECT("alipay_pc_direct", "ALiPayWebStrategy"),
    /** 微信公众号支付*/
    WX_PUB("wx_pub", "WXinPayPubStrategy"),
    /** 微信app支付*/
    WX("wx", ""),
    /** 微信H5支付*/
    WX_WAP("wx_wap", "WXinPayH5Strategy"),
    /** 支付宝app*/
    ALIPAY("alipay", "ALiPayAppStrategy"),
    
    
    ;
    private String value;
    private String strategy;

    PayChannelEnum(String i, String strategy) {
        this.value = i;
        this.strategy = strategy;
    }

    public String getValue() {
        return this.value;
    }
    
    public static String getStrategyByValue(String value) {
    	for(PayChannelEnum payEnum : values()) {
    		if(payEnum.value.equals(value)) {
    			return payEnum.strategy;
    		}
    	}
    	return null;
    }
}
