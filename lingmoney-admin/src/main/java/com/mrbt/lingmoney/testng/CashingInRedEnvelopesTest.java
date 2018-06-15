package com.mrbt.lingmoney.testng;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.commons.exception.PageInfoException;

/**
 * 同步数据到mongodb测试类
 * @author Administrator
 *
 */
public class CashingInRedEnvelopesTest extends SpringTestNG {
	
	@Autowired
	private RedPacketService redPacketService;

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
		LOGGER.info("产品成立兑现返现红包开始");
		try {
			redPacketService.doGiveRedPacketToUser();
		} catch (PageInfoException e) {
			e.printStackTrace();
		}
		LOGGER.info("产品成立兑现返现红包结束");
	}

}
