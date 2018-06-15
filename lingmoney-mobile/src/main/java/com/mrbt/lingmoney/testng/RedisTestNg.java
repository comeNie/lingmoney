package com.mrbt.lingmoney.testng;


import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.service.dream.DreamServer;


public class RedisTestNg extends SpringTestNG {

	@Autowired
	private DreamServer dreamServer;

	/**
	 * 
	 */
	@Test
	public void testAdd1() {
		JSONObject jsonObject = new JSONObject();
		
		Integer baseId = 1;
		
		jsonObject = dreamServer.queryUsersDreamInfo("0FB0CBBA4BBBD06CB5D84F8E3683D586", 1);
		
		System.out.println(jsonObject);
	}

}
