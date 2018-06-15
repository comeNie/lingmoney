package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.admin.service.smsSend.SmsService;
import com.mrbt.lingmoney.model.trading.SmsMessage;

/**
 * 发送短信
 * 
 * @author 000608
 *
 */
@Component("execSmsSend")
public class ExecSmsSend {
	private static final Logger LOG = LogManager.getLogger(ExecSmsSend.class);

	@Autowired
	private SmsService smsService;
	
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 发送短信执行方法
	 */
	public void exec() {
		LOG.info("获取redis中短信并发送定时任务开始执行");
		scheduleService.saveScheduleLog(null, "获取redis中短信并发送定时任务开始执行", null);
		try {
			SmsMessage smsMessage = smsService.takeSmsMessage();

			LOG.info("获取redis中短信并发送:" + System.currentTimeMillis() + "\t" + smsMessage);
			if (smsMessage != null) {
				smsService.sendSms(smsMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("获取redis中短信并发送定时任务，执行失败" + e.getMessage());
			scheduleService.saveScheduleLog(null, "获取redis中短信并发送定时任务，执行失败", e.getMessage());
		}
	}
}
