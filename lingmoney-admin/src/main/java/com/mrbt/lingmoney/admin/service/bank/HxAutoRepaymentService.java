package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年6月6日 下午5:12:34
 * @version 1.0
 * @description 自动还款
 **/
public interface HxAutoRepaymentService {
	/**
	 * 请求 自动还款授权
	 * 
	 * @param clientType
	 *            客户端类型
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param borrowerId
	 * 			      借款人信息id
	 * @param remark
	 *            备注
	 * @param logGroup
	 *            日志标签
	 * @return	PageInfo
	 */
	PageInfo askForAutoRepaymentAuthorization(Integer clientType, String appId, String loanNo, String borrowerId, String remark, String logGroup);

	/**
	 * 处理 自动还款授权 异步应答
	 * 
	 * @param xml
	 *            响应报文
	 * @param note
	 *            借款编号
	 * @param logGroup
	 *            日志头
	 * @return	PageInfo
	 */
	String autoRepaymentAuthCallBack(String xml, String note, String logGroup);

	/**
	 * 自动还款授权结果 查询
	 * 
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param logGroup
	 *            日志头
	 * @return	PageInfo
	 */
	PageInfo queryAutoRepaymentAuthResult(String appId, String loanNo, String logGroup);

	/**
	 * 请求 撤销自动还款授权
	 * 
	 * @param appId
	 *            应用标识
	 * @param otpSeqNo
	 *            动态密码唯一标识
	 * @param otpNo
	 *            动态密码
	 * @param loanNo
	 *            借款编号
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return	PageInfo
	 */
	PageInfo repealAutoRepaymentAuthorization(String appId, String otpSeqNo, String otpNo, String loanNo, String remark, String logGroup);

	    /**
     * 请求 自动单笔还款
     * 
     * @param appId
     *            应用标识
     * @param biddingId
     *            标的ID
     * @param feeAmt
     *            手续费 为0
     * @param amount
     *            还款金额 保留两位小数 0.00
     * @param remark
     *            备注（选填）
     * @param logGroup
     *            日志头
     * @return	PageInfo
     */
	PageInfo requestAutoSingleRepayment(String appId, String biddingId, double feeAmt, double amount, String remark,
			String logGroup);

	/**
	 * 
	 * @author syb
	 * @date 2017年7月31日 下午6:49:39
	 * @version 1.0
	 * @description 自动还款成功后操作
	 * @param amount
	 *            还款金额
	 * @param logGroup
	 *            日志头
	 * @param channelFlow
	 *            还款申请流水号
	 * @param uid
	 *            用户id
	 * @param acNO
	 *            E账号
	 * @param loanNo
	 * 			  借款编号
	 *
	 */
	void operateAfterRepaymentSuccess(Double amount, String logGroup, String channelFlow, String uid,
			String acNO, String loanNo);
}
