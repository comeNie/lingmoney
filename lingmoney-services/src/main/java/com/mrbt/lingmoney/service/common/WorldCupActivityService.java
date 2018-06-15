package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 世界杯相关
 * @author ruochen.yu
 *
 */
public interface WorldCupActivityService {

	/**
	 * 获取世界杯问答题列表
	 * @param pageInfo
	 */
	void getAnswersList(PageInfo pageInfo);

	/**
	 * 计算得分
	 * @param sheet	答卷数据； 题号：答案；
	 * @param pageInfo	返回数据包装
	 */
	void calculatedFraction(String sheet, PageInfo pageInfo);

	/**
	 * 通过手机号进行保存答题得分
	 * @param phone
	 * @param score
	 * @param pageInfo
	 */
	void saveScoreByPhone(String phone, Integer score, PageInfo pageInfo);

	/**
	 * 查询用户答题分数
	 * @param uId
	 * @param pageInfo
	 */
	void queryUsersScore(String uId, PageInfo pageInfo);

	/**
	 * 领取红包
	 * @param uId
	 * @param pageInfo
	 */
	void receiveRedEnve(String uId, PageInfo pageInfo);

}
