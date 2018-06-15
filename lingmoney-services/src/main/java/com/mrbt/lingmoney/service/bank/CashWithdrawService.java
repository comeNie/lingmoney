package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 6.7 单笔提现相关接口
 * 
 * @author lihq
 * @date 2017年6月3日 上午9:08:17
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface CashWithdrawService {

	/**
	 * 用户发起单笔提现请求
	 * 
	 * @param clientType
	 *            客户端类型 0,PC 1,APP
	 * @param appId
	 *            应用标识
	 * @param amount
	 *            交易金额
	 * @param uId
	 *            用户ID
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo requestCashWithdraw(int clientType, String appId, String amount, String uId) throws Exception;

	/**
	 * 单笔提现回调执行方法
	 * 
	 * @param document doc
	 * @return 应答报文
	 * @throws Exception 
	 */
	String cashWithdrawCallBack(Document document) throws Exception;
}
