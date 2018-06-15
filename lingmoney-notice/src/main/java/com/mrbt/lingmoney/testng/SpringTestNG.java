package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {
		"classpath:springmvc-servlet.xml",
		"classpath:spring-config.xml"
})
public class SpringTestNG extends AbstractTestNGSpringContextTests {
	
	@BeforeClass
	public void setUp(){
		System.out.println("初始化");
	}
	
	@Test  
    public void testAdd1() {
    }
	
    @AfterClass  
    public void destroy() {  
        System.out.println("退出，资源释放");  
    }  
	
}
