package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * @author Administrator
 *
 */
public interface AccountRechargeService {

	/**
	 * 账户充值查询
	 * @param uId	uId
	 * @param number 	number
	 * @param appId 	appId
	 * @return	PageInfo
	 */
	PageInfo queryAccountRecharge(String uId, String number, String appId);

}
