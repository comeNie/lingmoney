package com.mrbt.lingmoney.service.discover;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月4日 下午2:17:32
 *@version 1.0
 *@description 我的领地 WEB端
 **/
public interface MyPlaceWebService {
	/**
	 * WEB 包装首页展示数据
	 * @param modelMap model
	 * @param uid 用户id
	 */
	void packageIndexModelMap(ModelMap modelMap, String uid);

	/**
	 * 查看更多页面--筛选查询
	 * @param typeId 类型id
	 * @param range 范围
	 * @param page 页码
	 * @param rows 页数
	 * @return pageinfo
	 */
	PageInfo queryGiftWithCondition(Integer typeId, String range, Integer page,
			Integer rows);

}	
