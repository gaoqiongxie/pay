package com.xw.pay.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * 支付工具类
 * 
 */
public class PayUtil {
	
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/**
	 * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
	 * 
	 * @param amount
	 * @return
	 */
	public static BigDecimal getBigDecimal(Float amount) {
		BigDecimal b1 = new BigDecimal(Float.toString(amount));
		return b1.setScale(2, RoundingMode.HALF_EVEN);
	}

	/**
	 * 订单号生成规则
	 * 
	 * @return
	 */
	public static String getOrderNum() {
		String orderNo = "HMC" + getCurDateTimeStr23() + (int) (Math.random() * 10000000);
		while (orderNo.length() < 27) {
			orderNo = orderNo + "0";
		}
		return orderNo;
	}
	
	/**
	 * 获取当前日期的23位格式 格式：yyyyMMddHHmmssSSS add by:jackyshang
	 * 
	 * @return
	 */

	public static String getCurDateTimeStr23() {
		Date date = new Date();
		SimpleDateFormat FM_YYYYMMDDHHMMSSSSS = new SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		return FM_YYYYMMDDHHMMSSSSS.format(date);
	}
}
