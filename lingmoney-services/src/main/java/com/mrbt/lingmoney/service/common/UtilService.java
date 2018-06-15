package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.model.HxAccount;

/**
 * 工具类
 */
public interface UtilService {
	/**
	 * 从redis中取出uid
	 * 
	 * @author YJQ 2017年5月19日
	 * @param key key
	 * @return 用户id
	 * @throws Exception 
	 */
	String queryUid(String key) throws Exception;

	
	/**
	 * 登录错误+1操作, 4小时超时
	 * 
	 * @author YJQ 2017年5月25日
	 * @param key
	 *            记录错误次数的key
	 * @param times
	 *            允许的最大错误次数
	 * @param item
	 *            项 ip/account
	 * @return +1后的次数
	 * @throws Exception 
	 */
    Integer dealLoginError(String key, Integer times, String item) throws Exception;


	/**
	 * 返回用户华兴E账户信息
	 * @author YJQ  2017年6月5日
	 * @param uId 用户id
	 * @return 账户信息
	 * @throws Exception 
	 */
	HxAccount queryUserHxAccount(String uId) throws Exception;
}
