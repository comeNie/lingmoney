package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.service.generatePdf.GeneratePdfSerive;
import com.mrbt.lingmoney.admin.service.inviting.InvitingFriendsService;

public class AutoInvitingFriendsNg extends SpringTestNG {
	
	@Autowired
	private InvitingFriendsService invitingFriendsService;
	
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
		invitingFriendsService.invitingFriendsTask();
	}

}
