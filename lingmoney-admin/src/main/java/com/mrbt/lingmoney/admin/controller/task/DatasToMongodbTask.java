package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.oa.DatasToMongodbTaskService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;

/**
 * 同步数据给OA,写入书到MONGODB中，没10分钟执行一次，同步用户数据，成立的产品数据，成立的交易数据
 * @author Administrator
 *
 */
@Component("datasToMongodbTask")
public class DatasToMongodbTask {
	private static final Logger LOG = LogManager.getLogger(DatasToMongodbTask.class);
	@Autowired
	private DatasToMongodbTaskService datasToMongodbTaskService;
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 计划任务执行方法
	 */
	public void handle() {
		scheduleService.saveScheduleLog(null, "信息同步定时任务开始执行...", null);
		try {
			datasToMongodbTaskService.usersDatasToMongodbTask();
			LOG.info("执行用户信息同步定时器，执行成功");
			
			datasToMongodbTaskService.tradingDatasToMongodbTask();
			LOG.info("执行产品信息和交易信息同步定时器，执行成功");
			
		} catch (Exception e) {
			LOG.info("执行信息同步定时任务，执行错误："  + e);
			scheduleService.saveScheduleLog(null, "信息同步定时任务，执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "信息同步定时任务执行完毕", null);
	}
}
