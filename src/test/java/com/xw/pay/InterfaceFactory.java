package com.xw.pay;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:spring/spring-context.xml" })
public class InterfaceFactory extends PayApplicationTests{

	@Autowired
	RunFactory runFactory;
	@Test
	public void test() {
//		System.out.println(runFactory);
		runFactory.run("rabit");
	}
}

@Component
class RunFactory{
	@Autowired
	Map<String, Run> stgMap = new HashMap<String, Run>();
	
	public Map<String, Run> getStgMap() {
		return stgMap;
	}

	public void setStgMap(Map<String, Run> stgMap) {
		this.stgMap = stgMap;
	}
	
	public void run(String name) {
		this.stgMap.get(name).run();
	}
}

interface Run{
	void run();
}

@Component
class Rabit implements Run{

	@Override
	public void run() {
		System.out.println("fastly!");
	}
	
}

@Component
class Tortoise implements Run{

	@Override
	public void run() {
		System.out.println("slowly!");
	}
	
}