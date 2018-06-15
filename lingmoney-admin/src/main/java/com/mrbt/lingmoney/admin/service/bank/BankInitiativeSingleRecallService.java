package com.mrbt.lingmoney.admin.service.bank;

/**
 *@author syb
 *@date 2017年6月5日 上午11:02:59
 *@version 1.0
 *@description 银行主动单笔撤标
 **/
public interface BankInitiativeSingleRecallService {
	/**
	 * 处理银行主动单笔撤标
	 * @param message 请求报文
	 * @param logGroup 日志头
	 * @return string
	 */
	String initiativeSingleRecall(String message, String logGroup);
}
