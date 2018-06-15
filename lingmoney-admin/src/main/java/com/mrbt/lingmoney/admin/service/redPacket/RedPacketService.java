package com.mrbt.lingmoney.admin.service.redPacket;

import java.math.BigDecimal;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author suyibo
 * @date 创建时间：2017年11月2日
 */
public interface RedPacketService {

	/**
	 * 执行红包奖励操作
	 * 
	 * @author suyibo 2017年10月25日
	 * @param userId
	 *            用户id
	 * @param actType
	 *            活动类型
	 * @param rpType
	 *            红包类型
	 * @param amount
	 *            金额/比例
	 */
	void rewardRedPackage(String userId, Integer actType, Double amount);

	/**
	 * 产品成立送红包
	 * @throws PageInfoException
	 *             异常
	 */
	void doGiveRedPacketToUser() throws PageInfoException;

	/**
	 * 活动红包专属接口
	 * @param userId 推荐人id
	 * @param money 赠送金额
	 * @return 信息返回
	 */
	PageInfo activityRedPacketReward(String userId, BigDecimal money);

	/**
	 * 赠送红包定时任务
	 */
	void autoSendRedPacketTask();
}
