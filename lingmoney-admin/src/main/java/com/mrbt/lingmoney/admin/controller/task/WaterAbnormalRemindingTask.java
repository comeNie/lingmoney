package com.mrbt.lingmoney.admin.controller.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.abnormal.AbnormalRemindingService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;

/**
 * 用户流水异常邮件提醒功能
 * 用户充值，提现，支付过程中出现2次异常后通过邮件提醒客服
 * @author Administrator
 *
 */
@Component
public class WaterAbnormalRemindingTask {
	
	private static final Logger LOGGER = LogManager.getLogger(WaterAbnormalRemindingTask.class);

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private AbnormalRemindingService abnormalRemindingService;
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:00:00");

	/**
	 * 计划任务执行方法
	 */
	public void handle() {
		scheduleService.saveScheduleLog(null, "流水异常提醒，开始执行...", null);
		
		
		try {
			LOGGER.info("流水异常提醒，开始执行...");
			
			StringBuffer emailContent = new StringBuffer();
			
			//获取当前日期
			Date queryDate = SDF.parse(SDF.format(new Date()));
			
			queryDate.setHours(queryDate.getHours() - 1);//前一个小时
			
//			Date queryDate = sdf.parse("2018-05-04 00:00:00");
			
			//充值异常提醒
			String logGroup = "\nWaterAbnormalRemindingTask_" + System.currentTimeMillis() + "_";
			abnormalRemindingService.rechargeLossProcessing(logGroup, emailContent, queryDate);
			
			
			//提现异常提醒
			logGroup = "\nWaterAbnormalRemindingTask_" + System.currentTimeMillis() + "_";
			abnormalRemindingService.putforwardLossProcessing(logGroup, emailContent, queryDate);
			
			//开户异常提醒
			logGroup = "\nWaterAbnormalRemindingTask_" + System.currentTimeMillis() + "_";
			abnormalRemindingService.openAccountLossProcessing(logGroup, emailContent, queryDate);
			
			//绑卡异常提醒
			logGroup = "\nWaterAbnormalRemindingTask_" + System.currentTimeMillis() + "_";
			abnormalRemindingService.bindCardLossProcessing(logGroup, emailContent, queryDate);
			
			//交易异常提醒
			logGroup = "\nWaterAbnormalRemindingTask_" + System.currentTimeMillis() + "_";
			abnormalRemindingService.tradingLossProcessing(logGroup, emailContent, queryDate);
			
			
			abnormalRemindingService.sendMail(emailContent.toString());
			
			LOGGER.info("流水异常提醒，执行完毕");
		} catch (Exception e) {
			LOGGER.info("执行信息同步定时任务，执行错误："  + e);
			scheduleService.saveScheduleLog(null, "流水异常提醒，执行失败", e.getMessage());
			e.printStackTrace();
		}
		scheduleService.saveScheduleLog(null, "流水异常提醒，执行完毕", null);
	}
	
}
