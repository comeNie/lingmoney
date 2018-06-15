package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年6月7日 下午2:51:50
 * @version 1.0
 * @description 单笔奖励或分红
 **/
public interface HxSingleRewardOrCutAMelonService {
	/**
	 * 单笔奖励或分红 请求
	 * 
	 * @param appId
	 *            应用标识
	 * @param hxAccountId
	 * 			     账户ID
	 * @param amount
	 *            交易金额
	 * @param remark
	 *            备注
	 * @param logGroup
	 *            日志头
	 * @return	return
	 */
	PageInfo requestSingleRewardOrCutAMelon(String appId, String hxAccountId, String amount, String remark,
			String logGroup);
}
