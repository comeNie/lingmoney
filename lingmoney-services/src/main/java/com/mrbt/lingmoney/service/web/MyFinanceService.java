package com.mrbt.lingmoney.service.web;

import net.sf.json.JSONObject;

import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.model.webView.TradingView;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年5月10日 下午1:51:22
 * @version 1.0
 * @description 我的理财WEB服务接口
 **/
public interface MyFinanceService {

	/**
	 * 包装理财管理页面数据
	 * 
	 * @param modelMap 
	 * @param status 状态
	 * @param pageNo 页码
	 * @param uid 用户id
	 */
	void packageFinancialManageInfo(ModelMap modelMap, Integer status, Integer pageNo, String uid);

	/**
	 * 分页查询我的理财数据
	 * 
	 * @param pageInfo 
	 * @param status 状态
	 * @param uid 用户id
	 * @param multyStatus 多状态用英文逗号分隔
	 * @return 分页展示数据
	 */
	GridPage<TradingView> financialManageInfo(PageInfo pageInfo, Integer status, String uid, String multyStatus);

	/**
	 * 包装账户流水页面数据
	 * 
	 * @param modelMap 
	 * @param type
	 *            资金类型
	 * @param uid
	 *            用户id
	 * @param timeType
	 *            时间范围
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param pageNo
	 *            分页页数
	 */
	void packageAccountFlowInfo(ModelMap modelMap, Integer type, String uid, String timeType, String beginTime,
			String endTime, Integer pageNo);

	/**
	 * 获取我的资产折线表数据
	 * 
	 * @param dateTime
	 *            日期（年月）
	 * @param uid
	 *            用户id
	 * @return 折线图数据
	 */
	JSONObject getMyMoneyData(String dateTime, String uid);

	/**
	 * 查询账户余额
	 * 
	 * @param modelMap 
	 * @param uid
	 *            用户id
	 */
	void packageRechargeWithdraw(ModelMap modelMap, String uid);

}
