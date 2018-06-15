package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.generatePdf.GeneratePdfSerive;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
/**
 * 生成pdf合同
 * @author wzy
 *
 */
@Component("autoGeneratePdfTask")
public class AutoGeneratePdfTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoGeneratePdfTask.class);
	@Autowired
	private GeneratePdfSerive generatePdfSerive;
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 执行方法
	 */
	public void generatePdf() {
		LOGGER.info("生成pdf合同任务开始执行");
		scheduleService.saveScheduleLog(null, "生成pdf合同定时任务开始执行", null);
		try {
			generatePdfSerive.generate();
			LOGGER.info("执行生成pdf定时器，执行成功"); 
		} catch (Exception e) {
			LOGGER.info("执行生成pdf定时器，执行错误："  + e); 
			
			
			scheduleService.saveScheduleLog(null, "执行生成pdf定时任务，执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "执行生成pdf定时任务完毕 ", null);
	}
}
