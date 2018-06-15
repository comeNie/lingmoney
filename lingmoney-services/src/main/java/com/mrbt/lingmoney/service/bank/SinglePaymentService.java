package com.mrbt.lingmoney.service.bank;


import org.dom4j.Document;

/**
 * 华兴E账户-还款人单笔还款服务接口
 * @author YJQ
 *
 */
public interface SinglePaymentService {

	/**
	 * 接收还款人单笔还款应答结果
	 * @author YJQ  2017年6月5日
	 * @param document doc
	 * @return 应答报文
	 * @throws Exception 
	 */
	String receivePaymentResult(Document document) throws Exception;

}
