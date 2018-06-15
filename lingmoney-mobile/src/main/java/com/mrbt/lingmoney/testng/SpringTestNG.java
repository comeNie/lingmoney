package com.mrbt.lingmoney.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * 
 * @author ruochen.yu
 *
 */
@ContextConfiguration(locations = { "classpath:springmvc-servlet.xml", "classpath:spring-config.xml" })
public class SpringTestNG extends AbstractTestNGSpringContextTests {
	
	public static final Logger LOGGER = LogManager.getLogger(SpringTestNG.class);
	
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化");
	}
	
	/**
	 * 退出，资源释放
	 */
    @AfterClass  
    public void destroy() {  
    	LOGGER.info("退出，资源释放");  
    }  
	
}
