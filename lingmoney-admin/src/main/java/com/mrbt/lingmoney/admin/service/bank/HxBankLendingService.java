package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年6月6日 上午11:44:48
 * @version 1.0
 * @description 放款
 **/
public interface HxBankLendingService {
	/**
	 * 申请 银行放款
	 * 
	 * @param appId
	 *            应用标识 PC APP WX
	 * @param biddingId
	 *            标的ID
	 * @param guarantAmt
	 *            风险保证金（选填）
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return	PageInfo
	 */
    PageInfo askForBankLending(String appId, String biddingId, Double guarantAmt, String remark, String logGroup);

	/**
	 * 查询银行放款结果 交易提交我行5~10分钟后，可通过该接口查询银行处理结果
	 * 
	 * @param appId
	 *            应用标识 PC APP WX
	 * @param loanNo
	 *            借款编号
	 * @param logGroup
	 *            日志头
	 * @return	PageInfo
	 */
	PageInfo queryBankLendingResult(String appId, String loanNo, String logGroup);
}
