package com.example.demo.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.xson.core.XSON;
import org.xson.core.XsonSupport;

import com.example.demo.bean.User;

public class XsonTest {

	
	public static void main(String[] args) {
		User user = new User();
		user.setId(1l);
		user.setUsername("javaScript");
		user.setRole("role_admin");
		user.setPassword("132419");
		
		byte[] encode = XSON.encode(user);
		System.out.println(Arrays.toString(encode));
		
		
		Object decode = XSON.decode(encode);
		System.out.println(decode);
		
		
		Map<String, String> prop = new HashMap<String, String>();
		prop.put("com.example.demo.bean.User", "x7");
		prop.put("com.example.demo.bean.User", "x6");
		XsonSupport.addCustomAgreementType(prop);
	}
}
