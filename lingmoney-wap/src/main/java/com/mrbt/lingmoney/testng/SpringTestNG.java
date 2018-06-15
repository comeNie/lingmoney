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

import com.mrbt.lingmoney.wechat.controller.utils.CreateImgController;

/**
 * TestNg测试继承类
 * @author Administrator
 */
@ContextConfiguration(locations = {"classpath:springmvc-servlet.xml", "classpath:spring-config.xml"})
public class SpringTestNG extends AbstractTestNGSpringContextTests {
	
	private static final Logger LOGGER = LogManager.getLogger(SpringTestNG.class);
	
	@Autowired
	private CreateImgController createImgController;

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
	public void testAdd1() throws FileNotFoundException {
		// WaterMarkConfigure configure = createImgController.new
		// WaterMarkConfigure();
		// configure.setFont(new Font("楷体", Font.ITALIC, 26));
		// configure.setWaterMarkContent("领钱儿恭祝狗年吉祥：旺狗贺岁，欢乐祥瑞;旺狗汪汪，事业兴旺;旺狗打滚，财源滚滚;旺狗高跳，吉星高照;旺狗撒欢，如意平安;旺狗祈福，阖家幸福!");
		// configure.setMarkContentColor(new Color(0, 0, 0));
		// configure.setDegree(null);
		// configure.setAlpha(0.5f);
		// InputStream srcStream = new
		// FileInputStream("E:/gallery/微信图片_20180208091817.jpg");
		// createImgController.getMarkedInputstream(configure);
    }
	
	/**
	 * 释放资源
	 */
    @AfterClass  
    public void destroy() {  
    	LOGGER.info("退出，资源释放");  
    }  
	
}
