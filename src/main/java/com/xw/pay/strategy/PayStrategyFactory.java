package com.xw.pay.strategy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xw.pay.entity.Charge;
import com.xw.pay.entity.PayReq;
import com.xw.pay.strategy.PayStrategy;


@Component
public class PayStrategyFactory {

	@Autowired  
	private Map<String, PayStrategy> stgMap = new HashMap<String, PayStrategy>();  
	   
    public Map<String, PayStrategy> getStgMap() {  
        return stgMap;  
    }  
   
    public void setStgMap(Map<String, PayStrategy> stgMap) {  
        this.stgMap = stgMap;  
    }  
   
    public Charge payExcute(String strType, PayReq req) {  
		return this.stgMap.get(strType).payExcute(req); 
    }  
    
}
