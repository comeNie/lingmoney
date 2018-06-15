package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

/**
 * @author syb
 * @date 2017年8月14日 下午3:13:30
 * @version 1.0
 * @description 退汇异步通知
 **/
public interface HxReexchangeService {

	/**
	 * 
	 * @description 退汇异步通知处理
	 * @author syb
	 * @date 2017年8月14日 下午3:14:40
	 * @version 1.0
	 * @param document 银行请求信息
	 * @return 应答报文
	 *
	 */
	String handelHxReexchange(Document document);

}
