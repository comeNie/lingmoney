package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

/**
 * @author syb
 * @date 2017年8月14日 下午2:16:17
 * @version 1.0
 * @description 交易结果查询
 **/
public interface HxQueryTradingResultService {
	/**
	 * 该接口可用于单笔撤标、公司垫付还款、单笔奖励或分红、自动投标和自动还款交易后状态查询。发起单笔撤标、公司垫付还款、单笔奖励或分红、自动投标和自动还
	 * 款交易 3 分钟后，可通过本接口查询交易的处理结果。单个流水号查询时间需间隔 5 分钟。
	 * 
	 * @description
	 * @author syb
	 * @date 2017年8月14日 下午2:17:58
	 * @version 1.0
	 * @param channelFlow
	 *            原交易请求流水号
	 * @param tranType
	 *            交易类型，目前为空
	 * @param logGroup
	 *            日志头
	 * @return	return
	 *
	 */
	Map<String, Object> queryHxTradingResult(String channelFlow, String tranType, String logGroup);
}
