package com.mrbt.lingmoney.admin.service.userincome.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.controller.task.AutoRebalanceTask;
import com.mrbt.lingmoney.admin.mongo.SellBatchIncomeMongo;
import com.mrbt.lingmoney.admin.service.userincome.SellBatchIncomeService;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.SellBatchInfoMapper;
import com.mrbt.lingmoney.model.SellBatchInfo;
import com.mrbt.lingmoney.model.SellBatchInfoExample;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/** 
 * @author  suyibo 
 * @date 创建时间：2017年12月19日
 */
@Service
public class SellBatchIncomeServiceImpl implements SellBatchIncomeService {
	@Autowired
	private SellBatchInfoMapper sellBatchInfoMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private AccountFlowMapper accountFlowMapper;

	private static final Logger LOGGER = LogManager.getLogger(AutoRebalanceTask.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置时间格式

	@Override
	public List<SellBatchInfo> findDayBeforeSellBatchInfo() {
		SellBatchInfoExample example = new SellBatchInfoExample();
		example.createCriteria().andBatchIsNotNull(); // 兑付时间不为空
		// example.createCriteria().andBatchLike("%201712%");
		List<SellBatchInfo> sellBatchInfoList = sellBatchInfoMapper.selectByExample(example);
		return sellBatchInfoList;
	}

	@Override
	public void saveSellBatchInfoToMongo(List<SellBatchInfo> sellBatchInfoList) {

		long beforeDate = 0;
		String financeDateStart = null;
		try {
			beforeDate = sdf.parse(DateUtils.giveDay(-1)).getTime(); // 前一天凌晨时间戳
			financeDateStart = sdf.format(beforeDate) + " 16:00:00"; // 前一天理财开始时间
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		for (SellBatchInfo sellBatchInfo : sellBatchInfoList) {
			// 获取用户基本信息
			Map<String, Object> userInfoMap = sellBatchInfoMapper.findUserInfoByUid(sellBatchInfo.getuId());

			// 获取理财经理信息
			Map<String, Object> orderInfoMap = sellBatchInfoMapper
					.findOrderInfoByUid((String) userInfoMap.get("referral_id"));

			// 兑付时间
			String strTime = sellBatchInfo.getBatch().substring(ResultNumber.THREE.getNumber(), ResultNumber.SEVEN.getNumber()) + "-" + sellBatchInfo.getBatch().substring(ResultNumber.SEVEN.getNumber(), ResultNumber.NINE.getNumber())
					+ "-" + sellBatchInfo.getBatch().substring(ResultNumber.NINE.getNumber(), sellBatchInfo.getBatch().length()) + " " + "16:00:00";
			Date date = null;
			try {
				date = sdf.parse(strTime);
			} catch (ParseException e) {
				LOGGER.info("兑付时间异常");
				e.printStackTrace();
			}
			// 查询兑付记录是否存在，不存在则新建
			SellBatchIncomeMongo query = mongoTemplate.findOne(
					new Query(Criteria.where("uId").is(sellBatchInfo.getuId()).and("sellDate").is(date.getTime())),
					SellBatchIncomeMongo.class);

			if (query != null) {

				// 查询用户前一天是否有理财记录
				List<Map<String, Object>> financeMapList = getFinanceInfo(financeDateStart, sellBatchInfo);
				Update update = new Update();
				update.set("sellMoney", query.getSellMoney().add(sellBatchInfo.getSellMoney()));
				long sellDate = query.getSellDate(); // 兑付时间
				if (!financeMapList.isEmpty()) {
					BigDecimal money = BigDecimal.ZERO;
					Date operateTime = null;
					for (Map<String, Object> financeMap : financeMapList) {
						money = money.add((BigDecimal) financeMap.get("money"));
						operateTime = (Date) financeMap.get("operate_time");
					}

					// 计算前一天理财距离兑付日天数
					int days = (int) (((new Date()).getTime() - sellDate) / (1000 * 3600 * 24));
					switch (days) {
					case 1:
						update.set("oneMoney", money);
						break;
					case 2:
						update.set("twoMoney", money);
						break;
					case 3:
						update.set("threeMoney", money);
						break;
					case 4:
						update.set("fourMoney", money);
						break;
					case 5:
						update.set("fiveMoney", money);
						break;
					}
					update.set("incomeDate", sdf.format(operateTime));
					update.set("changeMoney", query.getChangeMoney().add(money));
					// 查询兑付日到今天用户的总理财金额
					Map<String, Object> params = new HashMap<>();
					params.put("uId", sellBatchInfo.getuId());
					params.put("financeDateStart", sellDate);
					params.put("financeDateEnd", sf.format(new Date()));
					List<Map<String, Object>> countMapList = accountFlowMapper.findUserFinanceOfToday(params);
					BigDecimal countMoney = BigDecimal.ZERO;
					if (!countMapList.isEmpty()) {
						for (Map<String, Object> countMap : countMapList) {
							countMoney = countMoney.add((BigDecimal) countMap.get("money"));
						}
					}
					update.set("incomeEfficiency",
							countMoney.divide(query.getSellMoney().add(sellBatchInfo.getSellMoney())));

				} else {
					update.set("changeMoney", query.getChangeMoney().subtract(sellBatchInfo.getSellMoney()));
				}

				// 获取理财经理信息
				if (null != orderInfoMap) {
					update.set("orderName", orderInfoMap.get("name"));
					update.set("orderPhone", orderInfoMap.get("telephone"));
					update.set("orderOrg", orderInfoMap.get("department"));
					update.set("orderCompany", orderInfoMap.get("comp_name"));
				}
				mongoTemplate.updateFirst(
						new Query(Criteria.where("uId").is(sellBatchInfo.getuId()).and("sellDate").is(date.getTime())),
						update, SellBatchIncomeMongo.class);

			} else {

				SellBatchIncomeMongo sbim = new SellBatchIncomeMongo();

				sbim.setSellDate(date.getTime());
				sbim.setuId(sellBatchInfo.getuId());
				sbim.setSellMoney(sellBatchInfo.getSellMoney());
				sbim.setUsername((String) userInfoMap.get("name"));
				sbim.setPhoneNumber((String) userInfoMap.get("telephone"));
				// 获取理财经理信息
				getOrderInfo(orderInfoMap, sbim);

				// 查询用户当天内是否有理财记录
				List<Map<String, Object>> financeMapList = getFinanceInfo(financeDateStart, sellBatchInfo);
				if (!financeMapList.isEmpty()) {
					BigDecimal money = BigDecimal.ZERO;
					Date operateTime = null;
					for (Map<String, Object> financeMap : financeMapList) {
						money = money.add((BigDecimal) financeMap.get("money"));
						operateTime = (Date) financeMap.get("operate_time");
					}
						
					// 计算当前时间距离兑付日天数
					int days = (int) (((new Date()).getTime() - date.getTime()) / (1000 * 3600 * 24));
					getIncomeMoney(sbim, money, operateTime, days, sf.format(new Date()), strTime,
							sellBatchInfo.getSellMoney(), sellBatchInfo.getuId());

				} else {
					sbim.setChangeMoney(BigDecimal.ZERO.subtract(sellBatchInfo.getSellMoney()));
				}
				mongoTemplate.insert(sbim);

			}
		}
	}

	/**
	 * 查询用户前一天是否有理财记录
	 * 
	 * @param financeDateStart
	 *            理财开始时间
	 * @param sellBatchInfo
	 *            兑付信息
	 */
	private List<Map<String, Object>> getFinanceInfo(String financeDateStart, SellBatchInfo sellBatchInfo) {
		Map<String, Object> params = new HashMap<>();
		params.put("uId", sellBatchInfo.getuId());
		params.put("financeDateStart", financeDateStart);
		params.put("financeDateEnd", sf.format(new Date()));
		List<Map<String, Object>> financeMapList = accountFlowMapper.findUserFinanceOfToday(params);
		return financeMapList;
	}

	/**
	 * 获取理财经理信息
	 * 
	 * @param orderInfoMap
	 *            orderInfoMap
	 * @param query
	 *            query
	 */
	private void getOrderInfo(Map<String, Object> orderInfoMap, SellBatchIncomeMongo query) {
		if (null != orderInfoMap) {
			query.setOrderName((String) orderInfoMap.get("name"));
			query.setOrderPhone((String) orderInfoMap.get("telephone"));
			query.setOrderOrg((String) orderInfoMap.get("department"));
			query.setOrderCompany((String) orderInfoMap.get("comp_name"));
		}
	}

	/**
	 * 记录回签金额
	 * 
	 * @param query
	 *            兑付回签对象
	 * @param financeMap
	 *            理财
	 * @param days
	 *            天数
	 */
	private void getIncomeMoney(SellBatchIncomeMongo query, BigDecimal money, Date operateTime, int days,
			String incomeDate, String sellDate, BigDecimal sellMoney, String uId) {
		switch (days) {
		case 1:
			query.setOneMoney(money);
			break;
		case 2:
			query.setTwoMoney(money);
			break;
		case 3:
			query.setThreeMoney(money);
			break;
		case 4:
			query.setFourMoney(money);
			break;
		case 5:
			query.setFiveMoney(money);
			break;
		}
		query.setIncomeDate(sdf.format(operateTime));

		// 查询兑付日到今天用户的总理财金额
		Map<String, Object> params = new HashMap<>();
		params.put("uId", uId);
		params.put("financeDateStart", sellDate);
		params.put("financeDateEnd", incomeDate);
		List<Map<String, Object>> countMapList = accountFlowMapper.findUserFinanceOfToday(params);
		BigDecimal countMoney = BigDecimal.ZERO;
		if (!countMapList.isEmpty()) {
			for (Map<String, Object> countMap : countMapList) {
				countMoney = countMoney.add((BigDecimal) countMap.get("money"));
			}
		}
		query.setChangeMoney(countMoney.subtract(sellMoney));
		query.setIncomeEfficiency(countMoney.divide(sellMoney, 4));
	}

	@Override
	public void findSellBatchIncomeList(Date sellDate, Integer dayCount, PageInfo pageInfo) {
		List<Map<String, Object>> listMap = getDataList(sellDate, dayCount);
		pageInfo.setRows(listMap);
		pageInfo.setTotal(listMap.size());
	}

	@Override
	public List<Map<String, Object>> sellBatchIncomeList(Date sellDate, Integer dayCount) {
		List<Map<String, Object>> listMap = getDataList(sellDate, dayCount);
		return listMap;
	}

	/**
	 * 获取数据集合
	 * 
	 * @param sellDate
	 *            兑付时间
	 * @param dayCount
	 *            回签天数
	 * @return 数据返回
	 */
	private List<Map<String, Object>> getDataList(Date sellDate, Integer dayCount) {
		if (null == dayCount) {
			dayCount = ResultParame.ResultNumber.FIVE.getNumber();
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strTime = simpleDateFormat.format(sellDate) + " " + "16:00:00";
		// String strTime = "2017-12-20 00:00:00";
		Date date = null;
		try {
			date = sdf.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("sellDate").is(date.getTime()));
		List<SellBatchIncomeMongo> findList = mongoTemplate.find(new Query(criatira), SellBatchIncomeMongo.class);
		// 封装数据
		List<Map<String, Object>> listMap = new ArrayList<>();

		if (!findList.isEmpty()) {
			for (SellBatchIncomeMongo sbim : findList) {

				Map<String, Object> map = new HashMap<>();
				map.put("sellDate", simpleDateFormat.format(new Date(sbim.getSellDate()))); // 兑付时间
				map.put("sellMoney", sbim.getSellMoney()); // 兑付金额
				map.put("username", sbim.getUsername()); // 兑付人
				map.put("phoneNumber", sbim.getPhoneNumber()); // 兑付人电话
				map.put("orderName", sbim.getOrderName()); // 理财经理
				map.put("orderPhone", sbim.getOrderPhone()); // 理财经理电话
				map.put("orderOrg", sbim.getOrderOrg()); // 理财经理所属部门
				map.put("orderCompany", sbim.getOrderCompany()); // 理财经理所属分公司
				switch (dayCount) {
				case 1:
					map.put("oneMoney", sbim.getOneMoney() == null ? BigDecimal.ZERO : sbim.getOneMoney()); // 第一天回签金额
					map.put("twoMoney", BigDecimal.ZERO); // 第二天回签金额
					map.put("threeMoney", BigDecimal.ZERO); // 第三天回签金额
					map.put("fourMoney", BigDecimal.ZERO); // 第四天回签金额
					map.put("fiveMoney", BigDecimal.ZERO); // 第⑤天回签金额
					break;
				case 2:
					map.put("oneMoney", sbim.getOneMoney() == null ? BigDecimal.ZERO : sbim.getOneMoney()); // 第一天回签金额
					map.put("twoMoney", sbim.getTwoMoney() == null ? BigDecimal.ZERO : sbim.getTwoMoney()); // 第二天回签金额
					map.put("threeMoney", BigDecimal.ZERO); // 第三天回签金额
					map.put("fourMoney", BigDecimal.ZERO); // 第四天回签金额
					map.put("fiveMoney", BigDecimal.ZERO); // 第⑤天回签金额
					break;
				case 3:
					map.put("oneMoney", sbim.getOneMoney() == null ? BigDecimal.ZERO : sbim.getOneMoney()); // 第一天回签金额
					map.put("twoMoney", sbim.getTwoMoney() == null ? BigDecimal.ZERO : sbim.getTwoMoney()); // 第二天回签金额
					map.put("threeMoney", sbim.getThreeMoney() == null ? BigDecimal.ZERO : sbim.getThreeMoney()); // 第三天回签金额
					map.put("fourMoney", BigDecimal.ZERO); // 第四天回签金额
					map.put("fiveMoney", BigDecimal.ZERO); // 第⑤天回签金额
					break;
				case 4:
					map.put("oneMoney", sbim.getOneMoney() == null ? BigDecimal.ZERO : sbim.getOneMoney()); // 第一天回签金额
					map.put("twoMoney", sbim.getTwoMoney() == null ? BigDecimal.ZERO : sbim.getTwoMoney()); // 第二天回签金额
					map.put("threeMoney", sbim.getThreeMoney() == null ? BigDecimal.ZERO : sbim.getThreeMoney()); // 第三天回签金额
					map.put("fourMoney", sbim.getFourMoney() == null ? BigDecimal.ZERO : sbim.getFourMoney()); // 第四天回签金额
					map.put("fiveMoney", BigDecimal.ZERO); // 第⑤天回签金额
					break;
				default:
					map.put("oneMoney", sbim.getOneMoney() == null ? BigDecimal.ZERO : sbim.getOneMoney()); // 第一天回签金额
					map.put("twoMoney", sbim.getTwoMoney() == null ? BigDecimal.ZERO : sbim.getTwoMoney()); // 第二天回签金额
					map.put("threeMoney", sbim.getThreeMoney() == null ? BigDecimal.ZERO : sbim.getThreeMoney()); // 第三天回签金额
					map.put("fourMoney", sbim.getFourMoney() == null ? BigDecimal.ZERO : sbim.getFourMoney()); // 第四天回签金额
					map.put("fiveMoney", sbim.getFiveMoney() == null ? BigDecimal.ZERO : sbim.getFiveMoney()); // 第⑤天回签金额
				}
				map.put("incomeDate", sbim.getIncomeDate()); // 回签时间
				map.put("changeMoney", sbim.getChangeMoney()); // 金额变化
				map.put("incomeEfficiency", sbim.getIncomeEfficiency()); // 回签率

				listMap.add(map);
			}
		}

		return listMap;
	}

}
