package com.mrbt.lingmoney.admin.service.festival.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.festival.FestivalBuyService;
import com.mrbt.lingmoney.mapper.ActivityProductMapper;
import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月18日
 */
@Service
public class FestivalBuyServiceImpl implements FestivalBuyService {

	@Autowired
	private ActivityProductMapper activityProductMapper;

	@Override
	public PageInfo festivalBuyDetailList(String pactName, PageInfo pageInfo, String startDate,
			String endDate, String activityStartDate, String activityEndDate) {
		if (null != pactName) {
			// 获取活动的开始时间和结束时间
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pactName", pactName);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			List<Map<String, Object>> listMap = activityProductMapper.findFestivalBuyDetailList(params);
			if (!listMap.isEmpty()) {
				for (Map<String, Object> map : listMap) {
					String uid = (String) map.get("id");

					// 活动期间三月期累计持有
					BigDecimal moneyForThree = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000090A", activityStartDate, activityEndDate);
					// 活动期间六月期累计持有
					BigDecimal moneyForSix = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000180A", activityStartDate, activityEndDate);
					// 活动期间十二月期累计持有
					BigDecimal moneyForTwelve = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000365A", activityStartDate, activityEndDate);

					map.put("moneyForThree", moneyForThree != null ? moneyForThree : "0.00");
					map.put("moneyForSix", moneyForSix != null ? moneyForSix : "0.00");
					map.put("moneyForTwelve", moneyForTwelve != null ? moneyForTwelve : "0.00");
				}
			}
			pageInfo.setRows(listMap);
			pageInfo.setTotal(listMap.size());
		}
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> exportFestivalBuyDetailList(String pactName, String startDate, String endDate,
			String activityStartDate, String activityEndDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (null != pactName) {
			// 获取活动的开始时间和结束时间
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pactName", pactName);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			List<Map<String, Object>> listMap = activityProductMapper.findFestivalBuyDetailList(params);
			if (!listMap.isEmpty()) {
				for (Map<String, Object> map : listMap) {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					String uid = (String) map.get("id");
					dataMap.put("usernmae", map.get("u_name"));
					dataMap.put("telephone", map.get("telephone"));
					dataMap.put("ordername", map.get("o_name"));
					dataMap.put("department", map.get("department"));
					dataMap.put("productname", map.get("p_name"));
					dataMap.put("ftime", map.get("f_time"));
					dataMap.put("financialMoney", map.get("financial_money"));
					dataMap.put("buyDate", map.get("buy_dt"));
					
					// 活动期间三月期累计持有
					BigDecimal moneyForThree = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000090A", activityStartDate, activityEndDate);
					// 活动期间六月期累计持有
					BigDecimal moneyForSix = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000180A", activityStartDate, activityEndDate);
					// 活动期间十二月期累计持有
					BigDecimal moneyForTwelve = activityProductMapper.findFinanceMoneyByPcode(pactName, uid,
							"20180116000365A", activityStartDate, activityEndDate);

					dataMap.put("moneyForThree",
							moneyForThree != null ? moneyForThree : "0.00");
					dataMap.put("moneyForSix", moneyForSix != null ? moneyForSix : "0.00");
					dataMap.put("moneyForTwelve",
							moneyForTwelve != null ? moneyForTwelve : "0.00");

					list.add(dataMap);
				}
			}
		}
		return list;
	}

}
