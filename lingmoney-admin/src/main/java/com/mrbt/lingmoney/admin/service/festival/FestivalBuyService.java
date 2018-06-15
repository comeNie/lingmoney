package com.mrbt.lingmoney.admin.service.festival;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月18日
 */
public interface FestivalBuyService {
	/**
	 * 查询活动产品购买明细
	 * 
	 * @param pactName
	 *            pactName
	 * @param pageInfo
	 *            pageInfo
	 * @param startDate
	 *            startDate
	 * @param endDate
	 *            endDate
	 * @return return
	 */
	PageInfo festivalBuyDetailList(String pactName, PageInfo pageInfo, String startDate, String endDate,
			String activityStartDate, String activityEndDate);

	/**
	 * 查询活动产品购买明细
	 * 
	 * @param pactName
	 *            pactName
	 * @param startDate
	 *            startDate
	 * @param endDate
	 *            endDate
	 * @return return
	 */
	List<Map<String, Object>> exportFestivalBuyDetailList(String pactName, String startDate, String endDate,
			String activityStartDate, String activityEndDate);
}
