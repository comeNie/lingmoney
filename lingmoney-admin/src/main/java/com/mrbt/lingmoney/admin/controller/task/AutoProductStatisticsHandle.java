package com.mrbt.lingmoney.admin.controller.task;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.schedule.ProductStatisticsService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.model.ProductStatistics;

/**
 * 产品统计定时任务
 * @author Administrator
 *
 */
@Component("autoProductStatisticsHandle")
public class AutoProductStatisticsHandle {
	private static final Logger LOGGER = LogManager.getLogger(AutoProductStatisticsHandle.class);
	@Autowired
	
	private ProductStatisticsService productStatisticsService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	private static final int SLEEP_TIME = 1000;
	/**
	 * 执行方法
	 */
	public void handle() {
		LOGGER.info("产品统计开始");
		scheduleService.saveScheduleLog(null, "产品统计定时任务开始执行...", null);
		try {
			List<ProductStatistics> productStatisticsList = productStatisticsService.recommendLineStatistics();
			productStatisticsService.exportProductStatistics(productStatisticsList);
			LOGGER.info("产品统计结束");
			Thread.sleep(SLEEP_TIME);
			scheduleService.saveScheduleLog(null, "产品统计定时任务执行成功", null);
		} catch (Exception e) {
			scheduleService.saveScheduleLog(null, "产品统计定时任务执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "产品统计定时任务执行完毕", null);
	}
}
