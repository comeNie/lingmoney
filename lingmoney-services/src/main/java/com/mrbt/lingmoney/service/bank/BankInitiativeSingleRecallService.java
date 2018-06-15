package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 *@author syb
 *@date 2017年6月5日 上午11:02:59
 *@version 1.0
 *@description 银行主动单笔撤标
 **/
public interface BankInitiativeSingleRecallService {
	/**
	 * 处理银行主动单笔撤标
	 * @param document 报文
	 * @return 应答报文
	 */
	String initiativeSingleRecall(Document document);
}
