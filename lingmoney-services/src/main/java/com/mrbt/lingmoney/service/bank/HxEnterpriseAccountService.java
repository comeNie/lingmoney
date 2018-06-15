package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 * 企业账号
 *
 * @author syb
 * @date 2017年9月29日 下午2:53:56
 * @version 1.0
 **/
public interface HxEnterpriseAccountService {

	/**
	 * 企业开户回调
	 * 
	 * @author syb
	 * @date 2017年9月29日 下午2:56:01
	 * @version 1.0
	 * @param document 回调报文
	 * @return 应答报文
	 *
	 */
	String hxEnterpriseAccountOpenCallBack(Document document);

}
