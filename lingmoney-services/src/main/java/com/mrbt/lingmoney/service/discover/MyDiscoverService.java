package com.mrbt.lingmoney.service.discover;

import java.text.ParseException;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年4月12日 下午2:56:02
 *@version 1.0
 *@description 发现
 **/
public interface MyDiscoverService {

	/**
	 * 查询用户信息
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo getUserInfo(String uid);

	/**
	 * 签到
	 * @param uid 用户id
	 * @return pageinfo
	 * @throws ParseException 
	 */
	PageInfo signIn(String uid) throws ParseException;
	
	/**
	 * 设置签到redis，第二天0点过期
	 * 
	 * @param uid 用户id
	 * @return true成功  false失败
	 */
	boolean setSingIn(String uid);
	
	/**
	 * 今日是否签到
	 * @param uid 用户 id
	 * @return true已签到  false未签到
	 */
	boolean isSignInToday(String uid);

}
