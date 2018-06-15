package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 * @author syb
 * @date 2017年6月6日 下午5:12:34
 * @version 1.0
 * @description 自动还款
 **/
public interface HxAutoRepaymentService {
	/**
	 * 处理 自动还款授权 异步应答
	 * 
	 * @param document doc
	 * @return 应答报文
	 */
	String autoRepaymentAuthCallBack(Document document);

}
