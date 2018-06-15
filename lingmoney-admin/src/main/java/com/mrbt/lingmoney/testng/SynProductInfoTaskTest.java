package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.service.oa.DatasToMongodbTaskService;

/**
 * 同步数据到mongodb测试类
 * @author Administrator
 *
 */
public class SynProductInfoTaskTest extends SpringTestNG {

	@Autowired
	private DatasToMongodbTaskService datasToMongodbTaskService;

	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化");
	}
	
	/**
	 * 执行测试
	 */
	@Test
	public void testAdd2() {
		datasToMongodbTaskService.usersDatasToMongodbTask();
		
		datasToMongodbTaskService.tradingDatasToMongodbTask();
	}

}
