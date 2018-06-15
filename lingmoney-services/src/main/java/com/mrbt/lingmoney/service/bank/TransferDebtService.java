package com.mrbt.lingmoney.service.bank;

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
	 * 
	 * @param document doc
	 * @return 应答报文
	 *
	 */
	String transferDebtCallBack(Document document);

	/**
	 * 债权转让申请
	 * 
	 * @param uId 用户id
	 * @param clientType 客户端类型
	 * @param appId 应用标识
	 * @param amount 金额
	 * @param remark 备注
	 * @param pCode 产品code
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo transferDebtAssignment(String uId, int clientType, String appId, 
			String amount, String remark, String pCode) throws Exception;

	/**
	 * 债权转让结果查询
	 * 
	 * @param appId 应用标识
	 * @param uId 用户id
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo transferDebtAssignmentSearch(String appId, String uId) throws Exception;

}
