package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 *@author syb
 *@date 2017年8月14日 下午4:24:02
 *@version 1.0
 *@description 销户通知
 **/
public interface HxUnsubscribeService {

	/**
	 * 
	 * @description 销户通知处理
	 * @author syb
	 * @date 2017年8月14日 下午4:25:06
	 * @version 1.0
	 * @param document doc
	 * @return 应答报文
	 *
	 */
	String handelHxUnsubscribe(Document document);
	
}
