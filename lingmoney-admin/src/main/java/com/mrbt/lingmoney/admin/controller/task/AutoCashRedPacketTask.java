package com.mrbt.lingmoney.admin.controller.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.commons.exception.PageInfoException;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月12日
 */
@Component("autoCashRedPacketTask")
public class AutoCashRedPacketTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoCashRedPacketTask.class);

	@Autowired
	private RedPacketService redPacketService;

	/**
	 * 产品成立兑现返现红包
	 * @throws PageInfoException
	 *             PageInfoException
	 */
	public void giveRedPacketToUser() throws PageInfoException {
		LOGGER.info("产品成立兑现返现红包开始");
		redPacketService.doGiveRedPacketToUser();
		LOGGER.info("产品成立兑现返现红包结束");
	}
}
