package com.mrbt.lingmoney.service.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月9日 下午4:06:00
 *@version 1.0
 *@description 我的领钱儿
 **/
public interface UsersIndexService {

	/**
	 * 包装首页信息
	 * @param modelMap 
	 * @param request 
	 * @param uid 用户id
	 */
	void packageUsersInfo(ModelMap modelMap, HttpServletRequest request, String uid);

	/**
	 * 包装我的装好-存管账户首页
	 * @param model 
	 * @param uId
	 */
	void queryHxAccountInfo(ModelMap model, String uId);

	/**
	 * 我的账户
	 * 
	 * @param pageInfo
	 *            pageInfo
	 * @param uId
	 *            uId
	 * @return return
	 */
	PageInfo queryHxAccountInfoOfWap(PageInfo pageInfo, String uId);
	
}
