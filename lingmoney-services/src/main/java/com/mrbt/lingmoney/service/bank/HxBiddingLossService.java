package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 *@author syb
 *@date 2017年6月5日 下午4:26:59
 *@version 1.0
 *@description 流标
 **/
public interface HxBiddingLossService {
	
	/**
	 * 处理银行主动流标请求
	 * @param document 报文信息
	 * @return 响应报文
	 */
	String handleHxInitiativeBiddingLoss(Document document);
}
