package com.mrbt.lingmoney.admin.controller.task;

import java.text.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.bank.BankAccountService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;

/**
 * 自动查询账户开立结果
 * @author ruochen.yu
 *
 */
@Component
public class AutoQueryAccountOpenResult {
	private static final Logger LOGGER = LogManager.getLogger(AutoQueryAccountOpenResult.class);

	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 执行方法
	 * @throws ParseException ParseException
	 */
	public void handelAccountOpenResult() throws ParseException {
		LOGGER.info("自动查询账户开立结果定时任务开始执行。。。。");
		scheduleService.saveScheduleLog(null, "自动查询账户开立结果定时任务开始执行", null);
		
		bankAccountService.queryAccountOpen();

		LOGGER.info("自动查询账户开立结果定时任务执行完毕。。。。");
		scheduleService.saveScheduleLog(null, "自动查询账户开立结果定时任务执行完成", null);
	}

}
