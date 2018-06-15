package com.mrbt.lingmoney.admin.service.userincome;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.SellBatchInfo;
import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2017年12月19日
 */
public interface SellBatchIncomeService {

	/**
	 * 检测前一天兑付详情
	 * @return 兑付列表
	 */
	List<SellBatchInfo> findDayBeforeSellBatchInfo();

	/**
	 * 如果前一天兑付数据不为空，则将id放入缓存中
	 * 
	 * @param sellBatchInfoList
	 *            sellBatchInfoList
	 */
	void saveSellBatchInfoToMongo(List<SellBatchInfo> sellBatchInfoList);

	/**
	 * 查询用户回签列表
	 * @param sellDate
	 *            兑付时间
	 * @param dayCount
	 *            回签天数
	 */
	void findSellBatchIncomeList(Date sellDate, Integer dayCount, PageInfo pageInfo);

	/**
	 * 用户回签列表
	 * 
	 * @param sellDate
	 *            兑付时间
	 * @param dayCount
	 *            回签天数
	 * @return 用户回签列表
	 */
	List<Map<String, Object>> sellBatchIncomeList(Date sellDate, Integer dayCount);

}
