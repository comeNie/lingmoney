package com.mrbt.lingmoney.service.web;

import com.mrbt.lingmoney.model.webView.ActivityProductView;

/**
 *@author syb
 *@date 2017年5月16日 下午2:43:21
 *@version 1.0
 *@description 活动产品
 **/
public interface ActivityProductService {

	/**
	 * 根据活动关键字查询该活动信息及关联产品
	 *
	 * @param activityKey 活动关键字
	 * @param timeLimit
	 *            有无时间限制 0无 1有
	 * @return 活动产品展示数据
	 */
	ActivityProductView findActivityByActivityKey(String activityKey, int timeLimit);

}
