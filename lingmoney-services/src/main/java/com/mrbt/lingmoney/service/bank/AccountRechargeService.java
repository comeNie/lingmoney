package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 账户充值
 */
public interface AccountRechargeService {

	/**
	 * 账户充值请求
	 * @param clientType 客户端类型
	 * @param appId 应用标识
	 * @param uId 用户id
	 * @param amount 金额
	 * @return pageinfo
	 */
	PageInfo accountRechargeRequest(int clientType, String appId, String uId, String amount);

	/**
	 * 账户充值异步通知
	 * @param xml 通知报文
	 * @param note 用户id
	 * @return 应答报文
	 */
	String accountRechargeCallBack(String xml, String note);
	
	/**
	 * 账户充值查询
	 * @param uId 用户id
	 * @param number 原请求流水号
	 * @param appId 应用标识
	 * @return pageinfo
	 */
	PageInfo queryAccountRecharge(String uId, String number, String appId);

	
	/**
	 * 处理异步回调
	 * 
	 * @param document 请求报文
	 * @return 应答报文
	 */
	String opentionAccountRecharge(Document document);

	
	/**
	 * 查询结果-返回商户操作
	 * @author YJQ  2017年8月1日
	 * @param oldReqseqno 原请求流水号
	 * @return pageinfo
	 */
	PageInfo queryAccountRecharge(String oldReqseqno);

}
