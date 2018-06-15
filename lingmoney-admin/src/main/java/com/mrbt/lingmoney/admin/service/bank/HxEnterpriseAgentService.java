package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 企业经办人
 *
 * @author syb
 * @date 2017年9月29日 上午11:02:26
 * @version 1.0
 **/
public interface HxEnterpriseAgentService {

	/**
	 * 企业经办人信息修改/经办人变更
	 * 
	 * @author syb
	 * @date 2017年9月29日 上午11:04:16
	 * @version 1.0
	 * @param appId	appId
	 * @param clientType 客户端类型 0PC 1APP
	 * @param bankNo 银行存管账户
	 * @param logGroup		logGroup
	 * @return	return
	 *
	 */
	Map<String, String> requestUpdateEnterpriseAgentInfo(String appId, Integer clientType, String bankNo,
			String logGroup);
	
	/**
	 * 查询企业经办人信息修改/经办人变更 结果
	 * 
	 * @author syb
	 * @date 2017年9月29日 上午11:17:21
	 * @version 1.0
	 * @param id	id
	 * @param logGroup		logGroup
	 * @return	return
	 *
	 */
	PageInfo queryUpdateEnterpriseAgentInfoResult(String id, String logGroup);

	/**
	 * 查询企业经办人信息变更记录
	 * 
	 * @author syb
	 * @date 2017年10月10日 下午6:48:25
	 * @version 1.0
	 * @param page	page
	 * @param rows	rows
	 * @param eacId	eacId
	 * @return	return
	 *
	 */
	PageInfo listTransactor(Integer page, Integer rows, String eacId);

}
