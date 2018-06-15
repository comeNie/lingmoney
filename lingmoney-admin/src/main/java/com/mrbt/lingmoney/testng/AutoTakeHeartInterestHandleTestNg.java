package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.controller.task.AutoTakeHeartInterestHandle;

/**
 * 查询银行接口入口
 * @author Administrator
 *
 */
public class AutoTakeHeartInterestHandleTestNg extends SpringTestNG {
	
	@Autowired
	private AutoTakeHeartInterestHandle autoTakeHeartInterestHandle;
	
	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("随心取22日结息测试类");
	}


	/**
	 * 查询充值接口
	 */
	@Test
	private void accountRechargeQuery() {
		autoTakeHeartInterestHandle.handle();
	}

}
