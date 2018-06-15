package com.mrbt.lingmoney.admin.controller.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月18日
 */
@Component("autoSendRedPacketTask")
public class AutoSendRedPacketTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoSendRedPacketTask.class);

	@Autowired
	private RedPacketService redPacketService;

	public void autoSendRedPacketTask() {
		try {
			LOGGER.info("赠送红包定时任务开始");
			redPacketService.autoSendRedPacketTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("赠送红包定时任务结束");
	}
}
