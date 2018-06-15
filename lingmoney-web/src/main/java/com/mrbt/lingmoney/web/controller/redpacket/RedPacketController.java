package com.mrbt.lingmoney.web.controller.redpacket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.web.controller.users.UserController;

/**
 * 红包活动
 * 
 * @author suyibo
 *
 */

@Controller
@RequestMapping(value = "/redPacket")
public class RedPacketController {
	Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private RedPacketService redPacketService;

	/**
	 * 红包奖励
	 * @author suyibo 2017年10月25日
	 * @param userId
	 * @param actType
	 *            活动类型 0-手动，1-注册后，2-开通E账户后，3-激活E账户后，4-首次理财后，5-理财后
	 * @param amount
	 *            红包或加息卷金额
	 */
	public void rewardRedPackage(String userId, Integer actType, Double amount) {

		try {
			// 存在执行红包奖励操作
			redPacketService.rewardRedPackage(userId, actType, amount);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
