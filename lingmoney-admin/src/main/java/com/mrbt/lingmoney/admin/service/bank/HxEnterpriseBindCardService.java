package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 企业绑卡
 *
 * @author syb
 * @date 2017年9月29日 下午1:54:00
 * @version 1.0
 **/
public interface HxEnterpriseBindCardService {

	/**
	 * 请求解绑银行卡
	 * 
	 * @author syb
	 * @date 2017年9月29日 下午1:58:06
	 * @version 1.0
	 * @param appId	appId
	 * @param clentType
	 *            客户端类型 0pc 1app
	 * @param bankNo
	 *            银行存管账号
	 * @param logGroup
	 *            日志头
	 * @return	return
	 *
	 */
	Map<String, String> requestUnBindCard(String appId, Integer clentType, String bankNo, String logGroup);

	/**
	 * 根据条件查询企业充值来账记录
	 * 
	 * @author yiban
	 * @date 2017年10月20日 上午11:04:26
	 * @version 1.0
	 * @param date
	 *            交易日期
	 * @param bankNo
	 *            银行账号
	 * @param transType
	 *            交易类型 0 收入 1支出
	 * @param page	page
	 * @param rows	rows
	 * @return	return
	 *
	 */
	PageInfo listEnterpriseRechargeRecord(String date, String bankNo, String transType, Integer page, Integer rows);

	/**
	 * 查询企业绑卡记录
	 * 
	 * @author yiban
	 * @date 2017年10月20日 下午6:52:10
	 * @version 1.0
	 * @param acNo
	 *            企业银行账户
	 * @param page	page
	 * @param rows	rows
	 * @return	return
	 *
	 */
	PageInfo listEnterpriseBindCard(String acNo, Integer page, Integer rows);
}
