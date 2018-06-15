package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.utils.PageInfo;

public interface WorldCupMatchService {

	/**
	 * 查询比赛场次
	 * @param pageInfo
	 * @param uId
	 */
	void queryMatchInfo(PageInfo pageInfo, String uId);

	/**
	 * 竞猜
	 * @param pageInfo
	 * @param uId	用户ID
	 * @param gameChoice 竞猜选项	竞猜选项  1平，2left， 3right
	 * @param matchId 场次ID
	 */
	void guessingCompet(PageInfo pageInfo, String uId, Integer matchId, Integer gameChoice);

	/**
	 * 查询用户竞猜信息
	 * @param pageInfo
	 * @param uId
	 */
	void queryUserGuess(PageInfo pageInfo, String uId);

	/**
	 * 世界杯抽奖
	 * @param uid
	 * @param typeID
	 * @param pageInfo 
	 * @return
	 */
	PageInfo validateChoujiang(String uid, int typeID, PageInfo pageInfo);

	/**
	 * 获取抽奖次数
	 * @param uid
	 * @param typeID
	 * @param pageInfo
	 */
	void validateChoujiangCount(String uid, int typeID, PageInfo pageInfo);

	/**
	 * 用户中奖统计，每种奖项的中奖个数
	 * @param pageInfo
	 * @param uId
	 */
	void winningCount(PageInfo pageInfo, String uId);

}
