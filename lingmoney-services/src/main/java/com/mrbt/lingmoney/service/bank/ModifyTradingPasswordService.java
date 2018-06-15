package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年4月19日
 */
public interface ModifyTradingPasswordService {

	/**
	 * 个人客户进行重置交易密码
	 * @param clientType clientType
	 * @param appId appId
	 * @param uId uId
	 * @return return
	 */
	PageInfo modifyTradingPassword(Integer clientType, String appId, String uId);

}
