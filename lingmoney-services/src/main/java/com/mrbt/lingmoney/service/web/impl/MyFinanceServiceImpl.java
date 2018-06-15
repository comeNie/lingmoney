package com.mrbt.lingmoney.service.web.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.formula.ExchangeRateByFormula;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ProductCategoryFixRateMapper;
import com.mrbt.lingmoney.mapper.ProductFloatNetValueMapper;
import com.mrbt.lingmoney.mapper.TakeheartCategoryMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingFloatInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.model.ProductFloatNetValue;
import com.mrbt.lingmoney.model.ProductFloatNetValueExample;
import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.TakeheartFixRate;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.TradingFixInfoExample;
import com.mrbt.lingmoney.model.TradingFloatInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersBankExample;
import com.mrbt.lingmoney.model.webView.TradingView;
import com.mrbt.lingmoney.mongo.ContractFileInfo;
import com.mrbt.lingmoney.service.web.MyFinanceService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * @author syb
 * @date 2017年5月10日 下午1:56:39
 * @version 1.0
 * @description
 **/
@Service
public class MyFinanceServiceImpl implements MyFinanceService {
	private static final java.text.DecimalFormat DECIMALFORMAT = new java.text.DecimalFormat("#,###.00");

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersBankMapper usersBankMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private ExchangeRateByFormula formula;
	@Autowired
	private TakeheartFixRateMapper takeheartFixRateMapper;
	@Autowired
	private TakeheartCategoryMapper takeheartCategoryMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private TradingFloatInfoMapper tradingFloatInfoMapper;
	@Autowired
	private ProductCategoryFixRateMapper productCategoryFixRateMapper;
	@Autowired
	private ProductFloatNetValueMapper productFloatNetValueMapper;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void packageFinancialManageInfo(ModelMap modelMap, Integer status, Integer pageNo, String uid) {

		// 查询当前用户所有绑定的银行卡(可用status=1)
		UsersBankExample ubExample = new UsersBankExample();
		ubExample.createCriteria().andStatusEqualTo("1").andUIdEqualTo(uid);

		// 用户是否绑定银行卡，随心取赎回时使用.
		int isBindBank = (int) usersBankMapper.countByExample(ubExample);
		modelMap.addAttribute("isBindBank", isBindBank);

		// 默认状态判断：如果有待支付产品显示待支付，否则显示持有中
		if (StringUtils.isEmpty(status)) {
			TradingExample tradingExmp = new TradingExample();
			tradingExmp.createCriteria().andStatusEqualTo(0).andUIdEqualTo(uid);
			int countNeedPay = (int) tradingMapper.countByExample(tradingExmp);
			if (countNeedPay > 0) {
				status = 0;
			} else {
				status = 1;
			}
		}
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
        GridPage<TradingView> gridPage = financialManageInfo(pi, status, uid, null);

		Map<String, Object> params = new HashMap<>();
		if (status == -1) {
			params.put("status", null);
		} else {
			params.put("status", status);
		}
		params.put("uid", uid);
		params.put("number", null);
		List<TradingView> financialRecordList = listFormatterTradingRecord(params);
		BigDecimal amount = new BigDecimal(0); // 理财总金额金额
		BigDecimal profit = new BigDecimal(0); // 理财总预期收益
		for (TradingView financialRecord : financialRecordList) {
			amount = amount.add(financialRecord.getFinancialMoney());
			if (AppCons.TAKE_HEART_PCODE.equals(financialRecord.getpCode())) {
				profit = profit.add(financialRecord.getHreatInterest());
            } else if (AppCons.New_Buyer_PCODE.equals(financialRecord.getpCode()) || financialRecord.getpId() == 53) {
				profit = profit.add(financialRecord.getInterest());
			} else {
				profit = getProfit(profit, financialRecord);
			}
		}

		modelMap.addAttribute("gridPage", gridPage);
		modelMap.addAttribute("pageNo", pageNo);
		modelMap.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
		modelMap.addAttribute("totalSize", gridPage.getTotal());
		modelMap.addAttribute("status", status);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        modelMap.addAttribute("amount", df.format(amount));
        modelMap.addAttribute("profit", df.format(profit));
	}

	/**
	 * 获取收益
	 * 
	 * @param profitAmount
	 *            收益金额
	 * @param tradingView
	 *            tradingView
	 * @return 返回累计收益金额
	 */
	private BigDecimal getProfit(BigDecimal profitAmount, TradingView tradingView) {
		if ((null == tradingView.getInterest() || new BigDecimal("0").compareTo(tradingView.getInterest()) != -1) && tradingView.getStatus() != 3) {
			profitAmount = profitAmount.add(ProductUtils.countInterest(tradingView.getFinancialMoney(),
					tradingView.getProduct().getfTime(), tradingView.getProduct().getfYield()));
		} else {
			profitAmount = profitAmount.add(tradingView.getInterest());
		}
		if (null != tradingView.getUsersRedPacket() && null != tradingView.getUsersRedPacket().getActualAmount()) {
			profitAmount = profitAmount.add(BigDecimal.valueOf(tradingView.getUsersRedPacket().getActualAmount()));
		}
		return profitAmount;
	}

	@Override
	public GridPage<TradingView> financialManageInfo(PageInfo pageInfo, Integer status, String uid, String multyStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (status == -1) {
			params.put("status", null);
		} else {
			params.put("status", status);
		}

        if (!StringUtils.isEmpty(multyStatus)) {
            String[] list = multyStatus.split(",");
            params.put("multyStatus", list);
        } else {
            params.put("multyStatus", null);
		}
		
		params.put("uid", uid);
		params.put("start", pageInfo.getFrom());
		params.put("number", pageInfo.getSize());

		GridPage<TradingView> gridPage = new GridPage<TradingView>();
		gridPage.setRows(listFormatterTradingRecord(params));
		gridPage.setTotal(tradingMapper.countFinancialRecord(params));
		return gridPage;
	}

	@Override
	public void packageAccountFlowInfo(ModelMap modelMap, Integer type, String uid, String timeType, String beginTime,
			String endTime, Integer pageNo) {

		AccountFlowExample afExample = new AccountFlowExample();
		AccountFlowExample.Criteria criteria = afExample.createCriteria();
		if (!StringUtils.isEmpty(type) && type != -1) {
			if (type > 10) {
				String[] types = type.toString().split("");

				List<Integer> list = new ArrayList<Integer>();
				for (int i = 1; i < types.length; i++) {
					list.add(NumberUtils.toInt(types[i]));
				}

				criteria.andTypeIn(list);
			} else {
				criteria.andTypeEqualTo(type);
			}
		}

		UsersAccount ua = usersAccountMapper.selectByUid(uid);
		if (ua != null) {
			criteria.andAIdEqualTo(ua.getId());
		} else {
			throw new IllegalArgumentException("未查询到用户账户信息");
		}

		System.out.println("用户" + uid + "理财记录状态是：" + type);

		Date begin = null;
		Date end = null;
		if (timeType != null && !"".equals(timeType)) {
			// 时间类型生效
			Date[] dateLsit = getBegTimeAndEndTime(timeType);
			begin = dateLsit[0];
			end = dateLsit[1];
			criteria.andOperateTimeBetween(begin, end);
			beginTime = "";
			endTime = "";
		} else {
			// 选择时间生效或者都为空
			if (beginTime != null && !"".equals(beginTime) && endTime != null && !"".equals(endTime)) {
				String beginStr = beginTime + " 00:00:00";
				String endStr = endTime + " 23:59:59";
				begin = DateUtils.getStringTODate(beginStr);
				end = DateUtils.getStringTODate(endStr);
				criteria.andOperateTimeBetween(begin, end);
				timeType = "";
			}
		}

		if (StringUtils.isEmpty(pageNo)) {
			pageNo = 1;
		}
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		afExample.setLimitStart(pi.getFrom());
		afExample.setLimitEnd(pi.getSize());
		afExample.setOrderByClause("operate_time desc");

		GridPage<AccountFlow> gridPage = new GridPage<AccountFlow>();
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(afExample);
		if (!accountFlowList.isEmpty()) {
			for (AccountFlow accountFlow : accountFlowList) {
				// 查找产品的交易类型
				Integer pType = null;
				if (null != accountFlow.gettId()) {
					pType = tradingMapper.selectPtypeByTid(accountFlow.gettId());
				} else {
					pType = tradingMapper.selectPtypeByNumber(accountFlow.getNumber());
				}
				if (null != pType) {
					accountFlow.setpType(pType);
				}

				// 银行卡
				if (null != accountFlow.getCardPan()) {
					accountFlow.setCardPan(StringOpertion.hideBankCardLast4(accountFlow.getCardPan()));
				}
			}
		}
		gridPage.setRows(accountFlowList);
		gridPage.setTotal(accountFlowMapper.countByExample(afExample));

		modelMap.addAttribute("gridPage", gridPage);
		modelMap.addAttribute("pageNo", pageNo);
		modelMap.addAttribute("pageSize", AppCons.DEFAULT_PAGE_SIZE);
		modelMap.addAttribute("totalSize", gridPage.getTotal());
		modelMap.addAttribute("type", type);
		modelMap.addAttribute("timeType", timeType);
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		modelMap.addAttribute("usersAccount", ua);
	}

	@Override
	public JSONObject getMyMoneyData(String dateTime, String uid) {
		JSONObject json = new JSONObject();

		Date begin = null;
		Date end = null;
		if (dateTime == null || "".equals(dateTime)) {
			dateTime = DateUtils.getDateStr(new Date(), DateUtils.sf);
			String[] dateStr = dateTime.split("-");
			int syear = Integer.parseInt(dateStr[0]);
			int smonth = Integer.parseInt(dateStr[1]);
			begin = DateUtils.getFirstDayofMonth(syear, smonth - 1);
			end = new Date();
		} else {
			String[] dateStr = dateTime.split("-");
			int syear = Integer.parseInt(dateStr[0]);
			int smonth = Integer.parseInt(dateStr[1]);
			begin = DateUtils.getFirstDayofMonth(syear, smonth - 1);
			end = DateUtils.getEndDayofMonth(syear, smonth - 1);
		}

		// 折线图数据
		int days = DateUtils.dateDiff(begin, end) + 1;
		String[] categories = new String[days];
		String[] values = new String[days];

		TradingExample example = new TradingExample();
        example.createCriteria().andStatusEqualTo(AppCons.SELL_OK).andUIdEqualTo(uid).andPIdNotEqualTo(1)
                .andSellDtBetween(begin, end);
		example.setOrderByClause("sell_dt");
		List<Trading> list = tradingMapper.selectByExample(example);

		// 格式化折线图数据
		HashMap<Integer, BigDecimal> tempMap = new HashMap<Integer, BigDecimal>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Trading trading = list.get(i);
				Date sellDate = trading.getSellDt();
				// 取得日期
				java.util.Calendar calendar = Calendar.getInstance();
				calendar.setTime(sellDate);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if (tempMap.get(day) != null) {
					BigDecimal d = trading.getSellMoney().subtract(trading.getBuyMoney()).add(tempMap.get(day));
					tempMap.put(day, d);
				} else {
					BigDecimal d = trading.getSellMoney().subtract(trading.getBuyMoney());
					tempMap.put(day, d);
				}
			}
		}

		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
		java.text.DecimalFormat df1 = new java.text.DecimalFormat("###0.00");
		for (Map.Entry<Integer, BigDecimal> entry : tempMap.entrySet()) {
			Object key = entry.getKey();
			Object val = entry.getValue();
			treeMap.put((Integer) key, df1.format(val));
		}

		for (int i = 0; i < days; i++) {
			if (treeMap.get(i + 1) != null) {
				categories[i] = i + 1 + "号";
				values[i] = treeMap.get(i + 1) + "";
			} else {
				categories[i] = i + 1 + "号";
				values[i] = "0.00";
			}
		}

		json.put("categories", categories);
		json.put("values", values);
		return json;
	}

	/**
	 * 获取开始时间和结束时间
	 * 
	 * @param timeType
	 *            时间类型标识 XX天内
	 * @return date数组
	 */
	private Date[] getBegTimeAndEndTime(String timeType) {
		Date[] dateList = new Date[2];
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		dateList[1] = calendar.getTime();

		if (timeType.equals("7")) {
			calendar.add(Calendar.DAY_OF_YEAR, -7);
			dateList[0] = calendar.getTime();
		} else if (timeType.equals("30")) {
			calendar.add(Calendar.DAY_OF_YEAR, -30);
			dateList[0] = calendar.getTime();
		} else if (timeType.equals("90")) {
			calendar.add(Calendar.DAY_OF_YEAR, -90);
			dateList[0] = calendar.getTime();
		} else if (timeType.equals("365")) {
			calendar.add(Calendar.DAY_OF_YEAR, -365);
			dateList[0] = calendar.getTime();
		}

		return dateList;
	}

	/**
	 * 查询格式化后的交易信息，用于我的理财页面展示
	 * 
	 * @param vo
	 * @param page
	 * @return 格式化后的trading list
	 */
	public List<TradingView> listFormatterTradingRecord(Map<String, Object> params) {
		List<TradingView> tradingList = null;
		if (null != params.get("number")) {			
			tradingList = tradingMapper.queryFinancialRecord(params);
		} else {
			tradingList = tradingMapper.findFinancialRecord(params);
		}
		if (null != tradingList && tradingList.size() > 0) {
			for (int i = 0; i < tradingList.size(); i++) {
				TradingView trading = tradingList.get(i);
				
				
				//判断合同是否已经生成
				if (params.get("number") != null) {
					String fileName = trading.getId() + "" + params.get("uid");
					ContractFileInfo cif = mongoTemplate.findOne(new Query(Criteria.where("fileName").is(fileName)), ContractFileInfo.class);
					if (cif != null) {
						trading.setShowContract(1);
					}
				}
				
				// 待支付产品，需要计算剩余时间
				if (trading.getStatus() == AppCons.BUY_STATUS) {
					// 支付限制时间，默认15分钟
					int minute = 15;
					String orderRemainPayTime = PropertiesUtil.getPropertiesByKey("orderRemainPayTime");
					if (!StringUtils.isEmpty(orderRemainPayTime)) {
						minute = NumberUtils.toInt(orderRemainPayTime);
					}
					// 支付剩余时间（毫秒）购买时间 + 限制时间 - 当前时间
					long remainDt = trading.getBuyDt().getTime() + minute * 60 * 1000 - System.currentTimeMillis();
					remainDt = remainDt < 0 ? 0 : remainDt;
					// 距离产品结束时间
					long remainEdDt = trading.getProduct().getEdDt().getTime() - System.currentTimeMillis();
					remainEdDt = remainEdDt < 0 ? 0 : remainEdDt;
					trading.setTheRestTimeOfPayment(remainDt < remainEdDt ? remainDt : remainEdDt);

					// 预计到期时间
                    Date end = new Date(); // 结束时间
					Date sellDt = null; // 赎回时间
					if (trading.getProduct().getEdDt() != null) {
						end = trading.getProduct().getEdDt();
					} else {
						end = trading.getBuyDt();
					}
					if (trading.getProduct().getUnitTime() == 0) {
						sellDt = DateUtils.addDay(end, trading.getProduct().getfTime());
					}

                    if (trading.getProduct().getUnitTime() == 1) { // 周
						sellDt = DateUtils.addDay(end, trading.getProduct().getfTime() * 7);
					}

                    if (trading.getProduct().getUnitTime() == 2) { // 月
						sellDt = DateUtils.addMonth(end, trading.getProduct().getfTime());
					}

					if (trading.getMinSellDt() == null) {
						trading.setMinSellDt(sellDt);
					}
				}

				// 初始化本息金额为理财金额
				trading.setPrincipal(DECIMALFORMAT.format(trading.getFinancialMoney()));
				// 是否可赎回 0 可赎回 1 不可赎回 ，默认设置不可赎回
				trading.setRedeemFlag(1);
				// 设置产品类型 0 普通产品 1随心取
				trading.setPudType(0);

				// 拆分产品CODE信息
				String pcode = trading.getpCode();
				// 0:固定收益类，1:浮动类
				int type = Integer.parseInt(pcode.substring(8, 9));
				// 0:无,1:固定不变，2:区间
				int fixType = Integer.parseInt(pcode.substring(9, 10));
				trading.setType(type);
				trading.setFixType(fixType);

                if (trading.getStatus() == 1) { // 理财中，判断产品是否可赎回
					if (trading.getMinSellDt() != null) {
						Date newDate = new Date();
						Date minSellDt = trading.getMinSellDt();
						String tDate = DateUtils.getDateStr(newDate, DateUtils.sf);
						String minDate = DateUtils.getDateStr(minSellDt, DateUtils.sf);
						if (tDate.compareTo(minDate) > 0 || tDate.equals(minDate)) {
							trading.setRedeemFlag(0);
						}
					} else {
                        if (trading.getProduct().getRule() == 3) { // 随心取产品
							trading.setRedeemFlag(0);
						}
					}
				}

				// 判断是否为随心取产品
				if (AppCons.TAKE_HEART_PCODE.equals(pcode)) {
					trading.setPudType(1);

					// 取得产品利率
					if (trading.getStatus() == 1 || trading.getStatus() == 4) {// 1支付成功4资金冻结
						handTakeHeartTrading(trading);

					} else {
						if (type == AppCons.PROFIT_TYPE_0) {
							if (fixType == 1) {
								if (trading.getProduct().getfTime() == 0) {
									trading.setMinSellDt(trading.getSellDt());
								}
							}
						}
						trading.setRunTime(100);
						trading.setRedeemFlag(1);
						int tDays = DateUtils.dateDiff(trading.getValueDt(), trading.getLastValueDt());
						System.out.println("理财天数：" + tDays);
						trading.setFinancialDays(tDays);
						// 查询随心取利率
						Map<String, Object> fixRageParams = new HashMap<String, Object>();
						fixRageParams.put("rDay", tDays);
						fixRageParams.put("financialMoney", trading.getFinancialMoney());
						Map<String, Object> takeHertRateInfo = takeheartFixRateMapper
								.getTakeHeartFixRate(fixRageParams);
						trading.getProduct().setfYield((BigDecimal) takeHertRateInfo.get("rate"));
					}

				} else {
                    if (trading.getStatus() == AppCons.BUY_OK || trading.getStatus() == AppCons.BUY_FROKEN) {
                        if (type == AppCons.PROFIT_TYPE_0) { // 固定
							handFixedNet(trading);
                        } else if (type == AppCons.PROFIT_TYPE_1) { // 净值
							handFloatNet(trading);
						}
                    } else if (trading.getStatus() == AppCons.SELL_STATUS || trading.getStatus() == AppCons.SELL_OK) {
						if (type == AppCons.PROFIT_TYPE_1) {
							TradingFloatInfo tradingFloatInfo = tradingFloatInfoMapper
									.selectTradingFloatInfoByTID(trading.getId());
							double buy = tradingFloatInfo.getInNetValue().doubleValue();
							double sell = tradingFloatInfo.getOutNetValue().doubleValue();
							trading.setBuyNetValue(buy);
							trading.setLastNetValue(sell);
							if (sell > buy) {
								trading.setNetValueFlag(1);
							} else if (sell == buy) {
								trading.setNetValueFlag(0);
							} else {
								trading.setNetValueFlag(2);
							}
						}
						if (type == AppCons.PROFIT_TYPE_0) {
							if (fixType == 1) {
								if (trading.getProduct().getfTime() == 0) {
									trading.setMinSellDt(trading.getSellDt());
								}
							}
						}
						trading.setRunTime(100);
						if (fixType == AppCons.PROFIT_FIX_TYPE_1) {
							trading.setDinanciaDay(trading.getProduct().getfTime());
						} else if (fixType == AppCons.PROFIT_FIX_TYPE_2) {
							// 设置持有天数和产品赎回时候的利率
							TradingFixInfo info = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
							if (info != null) {
								trading.getProduct().setfYield(info.getInterestRate());
								trading.setFinancialDays(info.getfTime());
								trading.getProduct().setfTime(info.getfTime());
								trading.setInterest(info.getInterest());
							}
						}
						trading.setRedeemFlag(1);
						trading.setPrincipal(trading.getSellMoney() + "");
						BigDecimal profitAmount = BigDecimal.ZERO;
						if ((null == trading.getInterest() || new BigDecimal("0").compareTo(trading.getInterest()) != -1) && trading.getStatus() != 3) {
							profitAmount = profitAmount.add(ProductUtils.countInterest(trading.getFinancialMoney(),
									trading.getProduct().getfTime(), trading.getProduct().getfYield()));
						} else {
							profitAmount = profitAmount.add(trading.getInterest());
						}
						trading.setInterest(profitAmount);
					}
				}
				String princi = trading.getPrincipal().replaceAll(",", "");
				trading.setHreatInterest(new BigDecimal(princi).subtract(trading.getFinancialMoney()));
			}
		}

		return tradingList;
	}

	/**
	 * 随心取处理
	 *
	 * @Description
	 * @param trading
	 */
	private void handTakeHeartTrading(TradingView trading) {
		// 取得利率
		int tDays = 0;
		int days = 0;

		if (trading.getValueDt().getTime() < new Date().getTime()) {
			if (trading.getProduct().getUnitTime() == 0) {
				tDays = DateUtils.dateDiff(trading.getValueDt(), new Date());
			}

			if (trading.getProduct().getUnitTime() == 0) {
				days = tDays;
			}

			if (trading.getProduct().getUnitTime() == 1) {
				days = DateUtils.dateDiff(trading.getValueDt(), new Date()) / 7;
			}

			if (trading.getProduct().getUnitTime() == 2) {
				days = DateUtils.getMonth(DateUtils.getDateStr(trading.getValueDt(), DateUtils.sft),
						DateUtils.getDateStr(new Date(), DateUtils.sft));
			}
		}

		// 本金
		formula.setPp(trading.getFinancialMoney());
		if (trading.getLastValueDt() != null) {
			// 持有时间
			formula.setHold(DateUtils.dateDiff(trading.getLastValueDt(), new Date()));
		} else {
			formula.setHold(0);
		}

		// 查询随心取利率
		Map<String, Object> fixRageParams = new HashMap<String, Object>();
		if (tDays <= 0) {
			fixRageParams.put("rDay", 1);
		} else {
			fixRageParams.put("rDay", tDays);
		}
		fixRageParams.put("financialMoney", trading.getFinancialMoney());
		Map<String, Object> takeHertRateInfo = takeheartFixRateMapper.getTakeHeartFixRate(fixRageParams);

		if (takeHertRateInfo != null) {
			BigDecimal rate = (BigDecimal) takeHertRateInfo.get("rate");
			trading.getProduct().setfYield(rate);
            trading.setFinancialDays(days); // 持有天数.

            formula.setR(rate); // 汇率
			trading.setPrincipal(DECIMALFORMAT.format(
					formula.getFix_day_r_value().doubleValue() + trading.getFinancialMoney().doubleValue()) + "");

			// 判断是否有下一档
			String name = (String) takeHertRateInfo.get("name");
			trading.setStalls(name.substring(0, 1));

			// 根据上限查询是否存在更高级别随心取
			int upperLimit = (int) takeHertRateInfo.get("upperLimit");
			TakeheartCategory nextCategory = takeheartCategoryMapper.queryNextLevelByUpperLimit(upperLimit);
			if (nextCategory != null) {
				trading.setNextStalls(nextCategory.getName().substring(0, 1));
				trading.setDifference(new BigDecimal(upperLimit - trading.getFinancialMoney().doubleValue()));
			} else {
				// 没有下一档
				trading.setDifference(new BigDecimal(0));
			}

			// 获取下一天数利率及相差天数
			// 查询随心取利率
			Map<String, Object> upDayfixRageParams = new HashMap<String, Object>();
			if (tDays <= 0) {
				upDayfixRageParams.put("rDay", 1);
			} else {
				upDayfixRageParams.put("rDay", tDays);
			}
			upDayfixRageParams.put("cId", takeHertRateInfo.get("cId"));
			TakeheartFixRate upDayRate = takeheartFixRateMapper.getUpDayTakeHeartFixRate(upDayfixRageParams);

			// 最大档
			if (upDayRate == null) {

				trading.setUpDay(0);
				trading.setUpDayRate(new BigDecimal(0));

			} else {

				trading.setUpDay(upDayRate.getrDay() - days);
				trading.setUpDayRate(upDayRate.getRate());

			}

		}
	}

	/**
	 * 固定类处理
	 * 
	 * @Description
	 * @param trading
	 */
	private void handFixedNet(TradingView trading) {
        if (trading.getFixType() == AppCons.PROFIT_FIX_TYPE_1) { // 固定不变
			handleTradingRunTime(trading);
			handleFixInvest(trading);
			if (trading.getValueDt() != null) {
				if (trading.getValueDt().getTime() < new Date().getTime()) {
					int tDays = DateUtils.dateDiff(trading.getValueDt(), new Date());
					if (tDays > 0) {
						trading.setFinancialDays(tDays);
					}
				} else {
					trading.setFinancialDays(0);
				}
			} else {
				trading.setFinancialDays(0);
			}
        } else if (trading.getFixType() == AppCons.PROFIT_FIX_TYPE_2) { // 区间
			// 判断产品是不是新手标（新手表产品已经下架，可购买过的页面还需展示）
			if (AppCons.New_Buyer_PCODE.equals(trading.getpCode())) {
				TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
				if (tradingFixInfo != null) {
					trading.setInterest(tradingFixInfo.getInterest());
					BigDecimal money = trading.getFinancialMoney().add(tradingFixInfo.getInterest());
					trading.setPrincipal(DECIMALFORMAT.format(money.doubleValue()) + "");
					trading.getProduct().setfYield(tradingFixInfo.getInterestRate());
					trading.getProduct()
							.setfTime(DateUtils.dateDiff(tradingFixInfo.getValueDt(), tradingFixInfo.getExpiryDt()));
				}

				if (trading.getValueDt() != null && trading.getValueDt().getTime() < new Date().getTime()) {
                    int tDays = DateUtils.dateDiff(trading.getValueDt(), new Date());
                    if (tDays > 0) {
                        trading.setFinancialDays(tDays);
					} else {
						trading.setFinancialDays(0);
					}
				} else {
					trading.setFinancialDays(0);
				}
			} else {
				// 取得利率
				int tDays = 0;
				int days = 0;
				if (trading.getValueDt() != null && trading.getValueDt().getTime() < new Date().getTime()) {

					if (trading.getProduct().getUnitTime() == 0) {
						tDays = DateUtils.dateDiff(trading.getValueDt(), new Date());
					}

					if (trading.getProduct().getUnitTime() == 0) {
						days = tDays;
					}

					if (trading.getProduct().getUnitTime() == 1) {
						days = DateUtils.dateDiff(trading.getValueDt(), new Date()) / 7;

					}

					if (trading.getProduct().getUnitTime() == 2) {
						days = DateUtils.getMonth(DateUtils.getDateStr(trading.getValueDt(), DateUtils.sft),
								DateUtils.getDateStr(new Date(), DateUtils.sft));
					}
				}
				formula.setPp(trading.getFinancialMoney());
				formula.setHold(tDays);

				ProductCategoryFixRate productCategoryFixRate = new ProductCategoryFixRate();
				productCategoryFixRate.setpId(trading.getpId());
				productCategoryFixRate.setrTime(days);
				BigDecimal fixRate = productCategoryFixRateMapper.findRate(productCategoryFixRate);
				trading.setFinancialDays(tDays);
				if (fixRate != null) {
					formula.setR(fixRate);
					trading.setPrincipal(DECIMALFORMAT.format(
							formula.getFix_day_r_value().doubleValue() + trading.getFinancialMoney().doubleValue())
							+ "");
					trading.getProduct().setfYield(fixRate);
                    trading.setInterest(formula.getFix_day_r_value());
				}
			}
		}
	}

	/**
	 * 浮动类处理
	 * 
	 * @param trading
	 */
	private void handFloatNet(TradingView trading) {
		TradingFloatInfo tradingFloatInfo = tradingFloatInfoMapper.selectTradingFloatInfoByTID(trading.getId());
		if (tradingFloatInfo != null) {
			BigDecimal inNetValue = tradingFloatInfo.getInNetValue();
			if (trading.getFixInvest() != 0) {
				trading.setNetValueFlag(0);
				if (inNetValue != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tId", trading.getId());
					map.put("inNetValue", inNetValue);

					Double money = tradingFloatInfoMapper.selectSUMMoney(map);

					if (money != null) {
						trading.setPrincipal(DECIMALFORMAT.format(money.doubleValue()));
					}
				}
			} else {
				// 有买入净值，才能最近卖出净值
				if (inNetValue != null) {
					// 有卖出净值 ,查询净值表
					trading.setBuyNetValue(inNetValue.doubleValue());
					ProductFloatNetValueExample example = new ProductFloatNetValueExample();
					example.createCriteria().andPIdEqualTo(trading.getpId());
					example.setOrderByClause("net_value_dt desc");
					List<ProductFloatNetValue> floatNetList = productFloatNetValueMapper.selectByExample(example);
					if (floatNetList != null) {
						ProductFloatNetValue floatNetValue = floatNetList.get(0);
						double outNetValue = floatNetValue.getNetValue().doubleValue();
						formula.setI_nv(inNetValue);
						formula.setO_nv(new BigDecimal(outNetValue));
						formula.setNum(tradingFloatInfo.getNum());
						formula.setPp(trading.getFinancialMoney());
						trading.setPrincipal(DECIMALFORMAT.format(formula.getFloat_value()) + "");
						trading.setInterest(formula.getFloat_value().subtract(trading.getFinancialMoney()));
						trading.setLastNetValue(outNetValue);
						if (outNetValue > inNetValue.doubleValue()) {
							trading.setNetValueFlag(1);
						} else if (outNetValue == inNetValue.doubleValue()) {
							trading.setNetValueFlag(0);
						} else {
							trading.setNetValueFlag(2);
						}
					} else {
						trading.setNetValueFlag(0);
					}
				} else {
					trading.setNetValueFlag(0);
				}
			}
		}
	}

	/**
	 * 根据不同条件计算交易相关时间（计息日期，赎回日期，理财天数）
	 * 
	 * @Description
	 * @param trading
	 */
	private void handleTradingRunTime(TradingView trading) {
        int days = 0; // 单位时间
		// 如果产品已经成立
		if (trading.getProduct().getEstablishDt() != null) {
			// 如果计息日在当前时间之前
			if (trading.getValueDt() != null && trading.getValueDt().getTime() < new Date().getTime()) {

                if (trading.getProduct().getUnitTime() == 0) { // 日
					days = DateUtils.dateDiff(trading.getValueDt(), new Date());
				}

                if (trading.getProduct().getUnitTime() == 1) { // 周
					days = DateUtils.dateDiff(trading.getValueDt(), new Date()) / 7;
				}

                if (trading.getProduct().getUnitTime() == 2) { // 月
					days = DateUtils.getMonth(DateUtils.getDateStr(trading.getValueDt(), DateUtils.sft),
							DateUtils.getDateStr(new Date(), DateUtils.sft));
				}
			}
        } else { // 产品没有成立
			// 允许时间不做限制（非固定期限）
			if (trading.getProduct().getfTime() == 0) {
				if (trading.getValueDt() != null && trading.getValueDt().getTime() < new Date().getTime()) {

                    if (trading.getProduct().getUnitTime() == 0) { // 日
						days = DateUtils.dateDiff(trading.getValueDt(), new Date());
					}

                    if (trading.getProduct().getUnitTime() == 1) { // 周
						days = DateUtils.dateDiff(trading.getValueDt(), new Date()) / 7;
					}

                    if (trading.getProduct().getUnitTime() == 2) { // 月
						days = DateUtils.getMonth(DateUtils.getDateStr(trading.getValueDt(), DateUtils.sft),
								DateUtils.getDateStr(new Date(), DateUtils.sft));
					}

					// 如果当前时间大于 可赎回日期，设置可赎回日期为当前时间
					if ((new Date().getTime()) > trading.getMinSellDt().getTime()) {
						trading.setMinSellDt(new Date());
					}
				}
            } else { // 投资规则无限制
				if (trading.getProduct().getRule() == 3) {
					if (trading.getValueDt() != null && trading.getValueDt().getTime() < new Date().getTime()) {

						if (trading.getProduct().getUnitTime() == 0) {
							days = DateUtils.dateDiff(trading.getValueDt(), new Date());
						}

                        if (trading.getProduct().getUnitTime() == 1) { // 周
							days = DateUtils.dateDiff(trading.getValueDt(), new Date()) / 7;
						}

                        if (trading.getProduct().getUnitTime() == 2) { // 月
							days = DateUtils.getMonth(DateUtils.getDateStr(trading.getValueDt(), DateUtils.sft),
									DateUtils.getDateStr(new Date(), DateUtils.sft));
						}
					}
				} else {
					days = 0;
				}
                Date end = new Date(); // 结束时间
				Date sellDt = null; // 赎回时间
				if (trading.getProduct().getEdDt() != null) {
					end = trading.getProduct().getEdDt();
				} else {
					end = trading.getBuyDt();
				}
				if (trading.getProduct().getUnitTime() == 0) {
					sellDt = DateUtils.addDay(end, trading.getProduct().getfTime());
				}

                if (trading.getProduct().getUnitTime() == 1) { // 周
					sellDt = DateUtils.addDay(end, trading.getProduct().getfTime() * 7);
				}

                if (trading.getProduct().getUnitTime() == 2) { // 月
					sellDt = DateUtils.addMonth(end, trading.getProduct().getfTime());
				}

				if (trading.getMinSellDt() == null) {
					trading.setMinSellDt(sellDt);
				}

				if (trading.getValueDt() == null) {
					trading.setValueDt(end);
				}
			}
		}
		double runTime = 1;

		// 如果是固定期限
		if (trading.getProduct().getfTime() != 0) {
			runTime = (double) days / (double) trading.getProduct().getfTime();
		}
		trading.setRunTime(runTime * 100);
		if (trading.getRedeemFlag() == 0) {
			if (trading.getProduct().getEstablishDt() != null) {
				trading.setDinanciaDay(trading.getProduct().getfTime());
			} else {
				trading.setDinanciaDay(0);
			}
		} else {
			trading.setDinanciaDay(days);
		}
	}

	/**
	 * 计算本息（固定类）
	 * 
	 * @param trading
	 */
	private void handleFixInvest(TradingView trading) {
		// 不是定投处理
		if (trading.getFixInvest() == 0) {
			if (trading.getProduct().getfTime() == 0) {
				trading.setRunTime(100);
				formula.setPp(trading.getFinancialMoney());
				System.out.println(trading.getProduct().getfYield());
				if ((new Date().getTime()) > trading.getMinSellDt().getTime()) {
					if (trading.getMinSellDt() == null) {
						trading.setMinSellDt(new Date());
					}
				}
				trading.setPrincipal(DECIMALFORMAT.format(trading.getFinancialMoney().add(trading.getInterest())));
			} else {
				// 固定收益
				if (trading.getProduct().getStatus() == 2) {
					// 产品成立 取得固定交易流水信息
					TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
					if (tradingFixInfo != null) {
						if (null != tradingFixInfo.getInterest()) {
							trading.setInterest(tradingFixInfo.getInterest());
							trading.setPrincipal(DECIMALFORMAT
									.format(trading.getFinancialMoney().add(tradingFixInfo.getInterest())));
						}
					}
                } else { // 产品没成立，计算收益
					BigDecimal income = new BigDecimal(0);
					formula.setPp(trading.getFinancialMoney());
					formula.setR(trading.getProduct().getfYield());
					formula.setHold(trading.getProduct().getfTime());
					if (trading.getProduct().getUnitTime() == AppCons.UNIT_TIME_DAY) {
						income = formula.getFix_day_r_value();
					} else if (trading.getProduct().getUnitTime() == AppCons.UNIT_TIME_MONTH) {
						income = formula.getFix_month_r_value();
					}
					trading.setInterest(income);
					trading.setPrincipal(DECIMALFORMAT.format(trading.getFinancialMoney().add(income)));
				}
			}
		} else {
			double principal = 0;
			BigDecimal totalFinancialMoney = new BigDecimal("0");
			TradingFixInfoExample example = new TradingFixInfoExample();
			example.createCriteria().andTIdEqualTo(trading.getId()).andStatusEqualTo(1);
			List<TradingFixInfo> traingFixInfoList = tradingFixInfoMapper.selectByExample(example);
			if (traingFixInfoList != null && traingFixInfoList.size() > 0) {
				for (int i = 0; i < traingFixInfoList.size(); i++) {
					TradingFixInfo fixInfo = traingFixInfoList.get(i);
					if (trading.getMinSellDt() != null) {
						formula.setHold(DateUtils.dateDiff(fixInfo.getValueDt(), trading.getMinSellDt()));
					} else {
						formula.setHold(0);
					}
					formula.setPp(fixInfo.getFinancialMoney());
					formula.setR(trading.getProduct().getfYield());
					principal += formula.getFix_day_r_value().doubleValue() + fixInfo.getFinancialMoney().doubleValue();
					totalFinancialMoney = fixInfo.getFinancialMoney().add(totalFinancialMoney);
				}
				trading.setPrincipal(DECIMALFORMAT.format(principal));
				trading.setInterest(new BigDecimal(principal).subtract(totalFinancialMoney));
			}
		}
	}

	@Override
	public void packageRechargeWithdraw(ModelMap modelMap, String uid) {
		UsersAccount ua = usersAccountMapper.selectByUid(uid);
		if (ua != null) {
			modelMap.addAttribute("balance", ua.getBalance());
		}
	}

}
