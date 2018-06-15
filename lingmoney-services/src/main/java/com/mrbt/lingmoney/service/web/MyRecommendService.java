package com.mrbt.lingmoney.service.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.UserUnionInfo;

/**
 *@author syb
 *@date 2017年5月11日 上午11:37:18
 *@version 1.0
 *@description 我的推荐
 **/
public interface MyRecommendService {

	/**
	 * 包装我的推荐页面信息，如果用户第一次进入，添加二维码
	 * @param request 
	 * @param modelMap 
	 * @param uid 用户id
	 * @return 用户基本信息
	 */
	UserUnionInfo packageRecommednInfo(HttpServletRequest request, ModelMap modelMap,
			String uid);

}
