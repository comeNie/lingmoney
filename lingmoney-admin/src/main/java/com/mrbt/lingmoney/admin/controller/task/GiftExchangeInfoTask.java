package com.mrbt.lingmoney.admin.controller.task;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.product.GiftExchangeInfoService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 拉新活动定时任务
 * 
 * @author Administrator
 * @date 2017年4月10日 下午1:04:01
 * @description
 * @since
 */
@Component
public class GiftExchangeInfoTask {
	private static final Logger LOG = LogManager.getLogger(GiftExchangeInfoTask.class);

	@Autowired
	private GiftExchangeInfoService giftExchangeInfoService;
	@Autowired
	private ScheduleService scheduleService;

	/**
	 * 定时查询满足新吉粉条件的记录，批量插入gift_exchange_info表
	 */
//	@Scheduled(cron = "0 0/10 * * * ? ")
	public void batchInsertGiftExchangeInfos() {
		LOG.info("定时查询满足新吉粉条件的记录，批量插入gift_exchange_info表");
		scheduleService.saveScheduleLog(null, "拉新活动定时任务开始执行...", null);
		try {
			giftExchangeInfoService.batchInsertGiftExchangeInfo(1);
			scheduleService.saveScheduleLog(null, "拉新活动定时任务执行成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("定时查询满足新吉粉条件的记录，批量插入gift_exchange_info表失败，失败原因" + e);
			scheduleService.saveScheduleLog(null, "拉新活动定时任务执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "拉新活动定时任务执行完毕", null);
	}
	/**
	 * 
	 * 定时查询满足新的拉新活动条件的记录，批量插入gift_exchange_info_new表
	 * 
	 */
	//@Scheduled(cron = "0 0/10 * * * ? ")
	public void batchInsertGiftExchangeInfoNew() {
		LOG.info("定时查询满足拉新活动第二季条件的记录，批量插入gift_exchange_info_new表");
		scheduleService.saveScheduleLog(null, "拉新活动第二季定时任务开始执行...", null);
		try {
			giftExchangeInfoService.batchInsertGiftExchangeInfo(ResultNumber.TWO.getNumber());
			scheduleService.saveScheduleLog(null, "拉新活动第二季定时任务执行成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("定时查询满足拉新活动第二季条件的记录，批量插入gift_exchange_info_new表失败，失败原因" + e);
			scheduleService.saveScheduleLog(null, "拉新活动第二季定时任务执行失败", e.getMessage());
		}
		scheduleService.saveScheduleLog(null, "拉新活动第二季定时任务执行完毕", null);
	}
}
