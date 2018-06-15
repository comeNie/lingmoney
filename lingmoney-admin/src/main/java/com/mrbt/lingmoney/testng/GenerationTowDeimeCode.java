package com.mrbt.lingmoney.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.controller.task.AutoRebalanceTask;

/**
 * 
 * @author Administrator
 *
 */
public class GenerationTowDeimeCode extends SpringTestNG {
	
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("赠送加息券测试类");
	}

	/**
	 * 执行测试开始
	 */
	@Test
	public void testAdd1() {
		new AutoRebalanceTask().handleRebalance();
	}

}
