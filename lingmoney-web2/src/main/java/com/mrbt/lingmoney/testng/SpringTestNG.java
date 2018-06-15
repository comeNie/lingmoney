package com.mrbt.lingmoney.testng;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * TestNg测试继承类
 * @author Administrator
 */
@ContextConfiguration(locations = {"classpath:springmvc-servlet.xml", "classpath:spring-config.xml"})
public class SpringTestNG extends AbstractTestNGSpringContextTests {
	
	private static final Logger LOGGER = LogManager.getLogger(SpringTestNG.class);
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化");
	}
	
	/**
	 * 执行体
	 * 
	 * @throws FileNotFoundException
	 */
	@Test  
	public void testAdd1() {
		PageInfo pageInfo = new PageInfo(2, 10);
		productService.queryPcHomeProductList(pageInfo, "000", null, null, null, null);
		
		JSONArray jsonArray = JSONArray.fromObject(pageInfo.getRows());
		for (int i = 0; i < jsonArray.size(); i++) {
			
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			System.out.println(jsonObject.get("name") + "\t" + jsonObject.get("code") + "\t" + jsonObject.get("status")); 
		}
    }
	
	/**
	 * 释放资源
	 */
    @AfterClass  
    public void destroy() {  
    	LOGGER.info("退出，资源释放");  
    }  
	
}
