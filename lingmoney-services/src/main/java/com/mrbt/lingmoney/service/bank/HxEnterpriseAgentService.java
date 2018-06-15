package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 * 企业经办人信息
 *
 * @author syb
 * @date 2017年9月29日 下午3:32:59
 * @version 1.0
 **/
public interface HxEnterpriseAgentService {

	/**
	 * 企业经办人信息变更回调
	 * 
	 * @author syb
	 * @date 2017年9月29日 下午3:34:27
	 * @version 1.0
	 * @param document doc
	 * @return 应答报文
	 *
	 */
	String updateEnterpriseAgentInfoCallBack(Document document);

}
