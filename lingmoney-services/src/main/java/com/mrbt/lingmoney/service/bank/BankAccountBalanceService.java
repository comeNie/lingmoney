package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
public interface BankAccountBalanceService {

	/**
	 * 根据uid查询华兴账户信息：姓名，账号，状态
	 * 
	 * @author syb
	 * @date 2017年8月23日 下午2:15:48
	 * @version 1.0
	 * @param uId 用户id
	 * @return json对象：acName：姓名，acNo：账号，status：状态（0未绑定，1已绑定）
	 * @throws Exception 
	 *
	 */
	PageInfo userHxAccout(String uId) throws Exception;

	/**
	 * 查询账户余额
	 * 
	 * @author syb
	 * @date 2017年8月23日 下午2:18:50
	 * @version 1.0
	 * @param appId 应用标识
	 * @param uId 用户id
	 * @return json类型：availableBalance：可用余额
	 * @throws Exception 
	 *
	 */
	PageInfo findUserBalance(String appId, String uId) throws Exception;

	/**
	 * 账户余额校验
	 * 
	 * @author syb
	 * @date 2017年8月23日 下午2:20:29
	 * @version 1.0
	 * @param appId 应用标识
	 * @param uId 用户id
	 * @param amount 金额
	 * @return json类型：resflag:校验结果（ 000000 余额充足 540026 余额为零 540009 余额不足 540008
	 *         账户没有关联 OGW001 账户与户名不匹配）
	 * @throws Exception 
	 *
	 */
	PageInfo hxAccoutBalanceVerify(String appId, String uId, String amount) throws Exception;
	

}
