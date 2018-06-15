package com.mrbt.lingmoney.service.users;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.UsersAsk;
import com.mrbt.lingmoney.utils.GridPage;

/**
 *@author syb
 *@date 2017年5月8日 下午5:50:47
 *@version 1.0
 *@description 
 **/
public interface UsersAskService {

	/**
	 * 包装初始页列表数据
	 * @param modelMap 
	 * @param title 标题
	 * @param pageNo 
	 * @param status 状态，默认0未回答，1已回答
	 * @param type 0:问题，1：回答
	 * @param hot 是否是热门问题，默认0不是，1是
	 * @return gridpage
	 */
	GridPage<UsersAsk> packageListInfo(ModelMap modelMap, String title, Integer pageNo,
			Integer status, Integer type, Integer hot);

	/**
	 * 包装详情页数据
	 * @param modelMap 
	 * @param id 问题ID
	 */
	void packageDetailInfo(ModelMap modelMap, Integer id);

	/**
	 * 保存
	 * @param uid 用户id
	 * @param newTitle 标题
	 * @param newContent 内容
	 */
	void save(String uid, String newTitle, String newContent);

}
