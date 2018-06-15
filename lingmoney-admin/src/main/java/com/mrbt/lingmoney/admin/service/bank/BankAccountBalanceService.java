package com.mrbt.lingmoney.admin.service.bank;


import com.alibaba.fastjson.JSONObject;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
public interface BankAccountBalanceService {

	/**
	 * 查询账户余额
	 * @param appId	appId
	 * @param acName	acName
	 * @param acNo	acNo
	 * @return	JSONObject
	 * @throws Exception	Exception
	 */
	JSONObject findUserBanlance(String appId, String acName, String acNo) throws Exception;

	/**
	 * 校验余额
	 * @author YJQ  2017年7月4日
	 * @param appId	appId
	 * @param amount	amount
	 * @param acNo	acNo
	 * @return	String
	 * @throws Exception	Exception
	 */
	String checkAccountBalance(String appId, String amount, String acNo) throws Exception;

}
