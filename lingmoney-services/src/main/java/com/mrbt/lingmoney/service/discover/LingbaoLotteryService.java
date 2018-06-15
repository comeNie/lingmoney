package com.mrbt.lingmoney.service.discover;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 *@author syb
 *@date 2017年4月17日 下午3:57:46
 *@version 1.0
 *@description 抽奖
 **/
public interface LingbaoLotteryService {

	/**
	 * 查询中奖概率
	 * @param uid 用户id
	 * @return 中奖概率
	 */
	int queryLotteryRatio(String uid);

	/**
	 * 开始抽奖
	 * @param uid 用户id
	 * @param typeID 类型id
	 * @return json
	 */
	JSONObject validateChoujiang(String uid, Integer typeID);

	//抽奖------------------------------------------------
	/**
	 * 查询中奖记录
	 * @return 中奖纪录
	 */
	List<Map<String, Object>> queryLotteryInfo();

	/**
	 * 查询奖项信息
	 * @param typeID 活动ID
	 * @return 奖项属性
	 */
	JSONObject queryChoujiangItem(Integer typeID);
}
