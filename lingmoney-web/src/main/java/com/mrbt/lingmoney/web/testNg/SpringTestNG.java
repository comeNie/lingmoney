package com.mrbt.lingmoney.web.testNg;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 加载配置文件
 * @author Administrator
 *
 */
@ContextConfiguration(locations = { "classpath:spring-config.xml", "classpath:springmvc-servlet.xml"})
//@ContextConfiguration(locations = { "classpath:spring/spring-ftp.xml"})
public class SpringTestNG extends AbstractTestNGSpringContextTests {
	
	public static final Logger LOGGER = LogManager.getLogger(SpringTestNG.class);
	
}
