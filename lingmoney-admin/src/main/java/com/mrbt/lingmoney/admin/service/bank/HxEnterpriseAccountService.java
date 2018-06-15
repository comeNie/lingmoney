package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *
 *@author syb
 *@date 2017年9月29日 上午9:06:13
 *@version 1.0
 **/
public interface HxEnterpriseAccountService {

	/**
	 * 企业开户
	 * 
	 * @author syb
	 * @date 2017年9月29日 上午9:08:00
	 * @version 1.0
	 * @param appId	appId
	 * @param clientType
	 *            客户端类型 0 pc 1app
	 * @param companyName
	 *            企业名
	 * @param occCodeNo
	 *            企业注册证件号码
	 * @param logGroup	logGroup
	 * @return	return
	 *
	 */
	Map<String, String> accountOpen(String appId, Integer clientType, String companyName, String occCodeNo,
			String logGroup);

	/**
	 * 开户结果查询
	 * 
	 * @author syb
	 * @date 2017年9月29日 上午9:10:37
	 * @version 1.0
	 * @param id	id
	 * @param logGroup	logGroup
	 * @return	return
	 *
	 */
	PageInfo queryAccountOpenResult(String id, String logGroup);

	/**
	 * 查询企业账户信息
	 * 
	 * @author syb
	 * @date 2017年10月10日 上午10:45:33
	 * @version 1.0
	 * @param companyName
	 *            企业名
	 * @param bankNo
	 *            企业账号
	 * @param status
	 *            状态
	 * @param page	page
	 * @param rows	rows
	 * @return	return
	 *
	 */
	PageInfo listEnterpriseAccount(String companyName, String bankNo, Integer status, Integer page, Integer rows);

	/**
	 * 查询企业账户余额
	 * @param companyNameId	企业账户ID
	 * @return	return
	 */
	PageInfo queryCompanyBalance(String companyNameId);

	/**
	 * 企业账户提现
	 * @param companyNameId	companyNameId
	 * @param amount	amount
	 * @return	return
	 */
	Object withdrawals(String companyNameId, String amount);

}
