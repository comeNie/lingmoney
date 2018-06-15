package com.mrbt.lingmoney.service.product;

import java.util.Map;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.ActivityProductCustomer;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 活动
 * 
 * @author lihq
 * @date 2017年4月24日 上午9:46:25
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface ActivityService {

	/**
	 * 活动列表
	 * 
	 * @description
	 * @param pageInfo pageinfo
	 */
	void selectActivityList(PageInfo pageInfo);

	/**
	 * 活动内页 根据条件查询到活动产品，如果活动为earlyeight（早八点）：并计算活动距离开始时间、距离结束时间和当前时间
	 * 
	 * @param pageInfo pageinfo
	 * @return activityproductcustomer
	 */
	ActivityProductCustomer selectActivityInfo(PageInfo pageInfo);

	/**
	 * 查询活动产品说明
	 * 
	 * @description
	 * @param activityId 活动id
	 * @param productIndex 查询字段标识 ： act_pro_desc + ?
	 * @return 描述信息
	 */
	String selectActivityDesc(Integer activityId, Integer productIndex);

	/**
	 * 查询拉新活动信息
	 * 
	 * @Description
	 * @param uId 用户id
	 * @param judgeStatus 0判断活动状态 1不判断
	 * @return 拉新活动信息
	 */
    Map<String, Object> activityRecommend(String uId, Integer judgeStatus);

	/**
	 * 查询相关活动信息
	 * 
	 * @param activityKey 活动key activity_product.act_url
	 * @param isLogin 0 已登录 1未登录
	 * @param uId 用户id
	 * @param clientType 0pc 1app
	 * @param modelMap modelmap
	 * 
	 * @return 活动是否有效
	 */
	boolean activityShow(String activityKey, Integer isLogin, String uId, Integer clientType, ModelMap modelMap);

	/**
	 * 查询新手特供活动
	 * 
	 * @param uId 用户id
	 * @param needLogin 是否需要登录
	 * @param priority 活动属性
	 * @param type 类型
	 * @return pageinfo
	 */
	PageInfo selectActivityNovice(String uId, String needLogin, Integer priority, Integer type);
}
