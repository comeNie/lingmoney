package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.service.bank.BankAccountService;

/**
 * 查询用户绑卡情况计划任务
 * @author Administrator
 *
 */
public class QueryAccountBindCard extends SpringTestNG {

	@Autowired
	private BankAccountService bankAccountService;

	/**
	 * 初始化
	 */
	@BeforeClass
	public void setUp() {
		LOGGER.info("初始化, 查询用户绑卡情况计划任务");
	}
	
	/**
	 * 执行测试
	 */
	@Test
	public void testAdd2() {
		bankAccountService.pollingTiedCardResult();
	}

}
