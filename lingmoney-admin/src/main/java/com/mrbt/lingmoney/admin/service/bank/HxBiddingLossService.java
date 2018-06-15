package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年6月5日 下午4:26:59
 *@version 1.0
 *@description 流标
 **/
public interface HxBiddingLossService {
	
	/**
	 * 申请流标
	 * @param appId 应用标识
	 * @param loanNo 原投标借款编号
	 * @param cancelReason 流标理由（选填）
	 * @param logGroup 日志头
	 * @return	PageInfo
	 */
	PageInfo applyBiddingLoss(String appId, String loanNo, String cancelReason, String logGroup);
	
	/**
	 * 处理银行主动流标请求
	 * @param message 报文信息
	 * @param logGroup 日志头
	 * @return 响应报文
	 */
	String handleHxInitiativeBiddingLoss(String message, String logGroup);
	/**
	 * 查询流标结果 当收不到返回时（5~10分钟后），可通过该接口查询银行处理结果。
	 * @param appId 应用标识 PC APP WX
	 * @param loanNo 借款编号
	 * @param logGroup 日志头
	 * @return	PageInfo
	 */
	PageInfo queryBiddingLossResult(String appId, String loanNo, String logGroup);

}
