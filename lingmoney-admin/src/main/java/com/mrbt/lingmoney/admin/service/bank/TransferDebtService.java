package com.mrbt.lingmoney.admin.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 债权转让
 * @author luox
 * @Date 2017年6月8日
 */
public interface TransferDebtService {
	
	/**
	 * 债权转让异步应答
	 * @param document	document
	 * @return	return
	 */
	String transferDebtCallBack(Document document);

	/**
	 * 债权转让申请
	 * @param uId	用户UID
	 * @param clientType	客户端类型
	 * @param appId	appId
	 * @param amount	金额
	 * @param remark	备注
	 * @param pCode	成品编码
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo transferDebtAssignment(String uId, int clientType, String appId, 
			String amount, String remark, String pCode) throws Exception;

	/**
	 * 债权转让结果查询
	 * @param appId	appId
	 * @param oldreqseqno	原交易流水号
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo transferDebtAssignmentSearch(String appId, String oldreqseqno) throws Exception;

}
