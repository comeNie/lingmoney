package com.mrbt.lingmoney.admin.controller.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.activityReward.ActivityRewardService;

/**
 * 活动奖励现金红包定时任务
 * 
 * @author suyibo
 * @date 创建时间：2018年5月10日
 */
@Component("activityRewardTask")
public class ActivityRewardTask {
	private static final Logger LOGGER = LogManager.getLogger(ActivityRewardTask.class);
	
	@Autowired
	private ActivityRewardService activityRewardService;

	// /**
	// * 活动奖励现金红包
	// *
	// * @author suyibo
	// * @date 2018-05-10 17:00:00
	// */
	// public void activityRewardTask() {
	// try {
	// LOGGER.info("活动奖励现金红包定时任务开始");
	// activityRewardService.activityRewardTask();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// LOGGER.info("活动奖励现金红包定时任务结束");
	// }

	/**
	 * wap推广活动奖励现金红包
	 * 
	 * @author suyibo
	 * @date 2018-05-28 09:35:00
	 */
	public void wapActivityRewardTask() {
		try {
			LOGGER.info("wap推广活动奖励现金红包定时任务开始");
			activityRewardService.wapActivityRewardTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("wap推广活动奖励现金红包定时任务结束");
	}
}
