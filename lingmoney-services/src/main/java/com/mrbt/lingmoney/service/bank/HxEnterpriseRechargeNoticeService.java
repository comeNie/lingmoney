package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 * 企业充值来账通知
 *
 * @author syb
 * @date 2017年9月29日 下午3:41:50
 * @version 1.0
 **/
public interface HxEnterpriseRechargeNoticeService {

	/**
	 * 企业充值来账通知处理
	 * 
	 * @author syb
	 * @date 2017年9月29日 下午3:42:43
	 * @version 1.0
	 * @param document doc
	 * @return 应答报文
	 *
	 */
	String callBackOfEnterpriseRecharge(Document document);

}
