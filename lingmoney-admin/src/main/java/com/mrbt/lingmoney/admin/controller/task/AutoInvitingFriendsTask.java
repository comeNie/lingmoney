package com.mrbt.lingmoney.admin.controller.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.inviting.InvitingFriendsService;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月15日
 */
@Component("autoInvitingFriendsTask")
public class AutoInvitingFriendsTask {
	private static final Logger LOGGER = LogManager.getLogger(AutoInvitingFriendsTask.class);

	@Autowired
	private InvitingFriendsService invitingFriendsService;

	public void invitingFriendsTask() {
		try {
			LOGGER.info("邀请好友定时任务开始");
			invitingFriendsService.invitingFriendsTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("邀请好友定时任务结束");
	}
}
