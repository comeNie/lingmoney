package com.mrbt.lingmoney.service.commonweal;

import org.springframework.ui.Model;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.utils.PageInfo;

public interface CommonwealService {

	/**
	 * 用户获得爱心值
	 * @param uId
	 * @return
	 */
	PageInfo achieveLove(String uId);

	/**
	 * 查询公益项目列表
	 * @param uId
	 * @param type
	 * @param page
	 * @param rows
	 * @return
	 */
	JSONObject queryList(String uId, Integer type, Integer page, Integer rows);

	/**
	 * 获取用户已有爱心数量
	 * @param uId
	 * @return
	 */
	JSONObject getUserLoveNum(String uId);

	/**
	 * 用户赞助项目
	 * @param uId
	 * @param loveNumber
	 * @param commonwealId
	 * @return
	 */
	JSONObject sponsorProject(String uId, Integer loveNumber, Integer commonwealId);

	/**
	 * 查询详情html
	 * @param model 
	 * @param i	客户端类型
	 * @param commonwealId 
	 * @return
	 */
	void queryDetailsHtml(Model model, int i, Integer commonwealId);

}
