package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.schedule.AutoBuyRankingService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;

/**
 * 每月送领宝 定时任务
 * @author Administrator
 *
 */
@Component("autoBuyRankingTask")
public class AutoBuyRankingTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoBuyRankingTask.class);
	@Autowired
	private AutoBuyRankingService autoBuyRankingService;
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 执行方法
	 */
	public void handle() {
		scheduleService.saveScheduleLog(null, "每月赠送领宝定时任务开始执行...", null);
		try {
			autoBuyRankingService.monthlySendLingbao();
			LOGGER.info("执行每月赠送领宝定时器，执行成功");
			scheduleService.saveScheduleLog(null, "每月赠送领宝定时器，执行成功", null);
		} catch (Exception e) {
			LOGGER.info("执行每月赠送领宝定时器，执行错误："  + e);
			scheduleService.saveScheduleLog(null, "每月赠送领宝定时器，执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "每月赠送领宝定时任务执行完毕", null);
	}

}
