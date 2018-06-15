package com.mrbt.lingmoney.service.userfinance.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersMessageMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.TakeheartTransactionFlowVo;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UserFinance;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.model.UsersMessageExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.webView.TradingView;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.service.web.MyFinanceService;
import com.mrbt.lingmoney.service.web.impl.MyFinanceServiceImpl;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.FtpUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.QRCodeUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

import net.sf.json.JSONObject;

/**
 * @author 罗鑫
 * @Date 2017年4月14日
 */
@Service
public class UserFinanceHomeServiceImpl implements UserFinanceHomeService {

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersMessageMapper usersMessageMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
	@Autowired
	private FtpUtils ftpUtils;
	@Autowired
	private MyFinanceService myFinanceService;
	@Autowired
	private MyFinanceServiceImpl myFinanceServiceImpl;
	
	@Autowired
	private RedisDao redisDao;
	
	private static final String USER_FIN_PROCESS = "USER_FIN_PROCESS_";

	@Override
	public JSONObject findUserReferralCode(String uId, HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		UsersPropertiesExample example = new UsersPropertiesExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(example);
		String referralCode = null;
		// 二维码
		String codePath = null;
		if (Common.isNotBlank(list)) {
			UsersProperties usersProperties = list.get(0);
			// 推荐码
			referralCode = usersProperties.getReferralCode();
			// 二维码路径
			codePath = usersProperties.getCodePath();

			if (codePath == null || codePath.trim().equals("")) {
				// 没有，生成
				String path = request.getSession().getServletContext().getRealPath("");
				String text = AppCons.REFERRAL_URL + "?referralTel=" + referralCode;
				String format = "gif";
				String fileName = "referralCode" + uId + ".gif";
				BufferedImage buffer = QRCodeUtil.encodeBufferedImage(text, path + "/resource/images/lingqian.gif",
						false);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(buffer, format, os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				ftpUtils.upload(is, AppCons.USER_PICTURE_DIR + "/" + uId, fileName);
				codePath = ftpUtils.getUrl() + "TUPAN" + "/" + uId + "/" + fileName;

				usersProperties.setCodePath(codePath);
				usersPropertiesMapper.updateByPrimaryKeySelective(usersProperties);
			}
		}
		String shareLink = PropertiesUtil.getPropertiesByKey("shareLink.properties", "shareLink");
		String shareContent = PropertiesUtil.getPropertiesByKey("shareLink.properties", "shareContent");

		json.put("shareContent", shareContent);
		json.put("shareLink", shareLink);
		json.put("codePath", codePath);
		json.put("referralCode", referralCode);
		return json;
	}

	@Override
    public void findMoneyFlow(String uId, PageInfo pageInfo, Integer type, String types) {
		// 查询a_id
		UsersAccountExample accountExample = new UsersAccountExample();
		accountExample.createCriteria().andUIdEqualTo(uId);
		Integer aId = usersAccountMapper.selectByExample(accountExample).get(0).getId();

		AccountFlowExample example = new AccountFlowExample();
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		example.setOrderByClause("id desc");
		if (type == null) {
            if (!StringUtils.isEmpty(types)) {
                String[] typeList = types.split(",");
                List<Integer> params = new ArrayList<Integer>();
                for (String t : typeList) {
                    params.add(Integer.parseInt(t));
                }
                example.createCriteria().andAIdEqualTo(aId).andTypeIn(params);

            } else {
                example.createCriteria().andAIdEqualTo(aId);
            }

		} else {
			example.createCriteria().andAIdEqualTo(aId).andTypeEqualTo(type);
		}

		long total = accountFlowMapper.countByExample(example);
		if (total > 0) {
			pageInfo.setRows(accountFlowMapper.selectByExample(example));
			pageInfo.setTotal((int) total);
		} else {
			pageInfo.setMsg("暂无数据");
		}
	}

	@Override
	public JSONObject findUserTradingDetail(Integer tId, String uId) {
		JSONObject json = new JSONObject();
		Trading trading = tradingMapper.selectByPrimaryKey(tId);
		if (!uId.equals(trading.getuId())) {
			throw new RuntimeException("数据异常...");
		}

		ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
		json.put("buyTime", DateUtils.getFormatDateString(trading.getBuyDt()));
		json.put("reWay", product.getReWay());

		Integer fTime = product.getfTime(); // 固定期限
		Date valueDt = product.getValueDt(); // 产品计息日
		if (fTime != null && valueDt != null) {
			long time = valueDt.getTime() + fTime * 24 * 3600 * 1000L;
			json.put("expireTime", DateUtils.getFormatDateString(new Date(time)));
		}
		return json;
	}

	@Override
	public UsersMessage findUserMessageById(Integer mId) {
		UsersMessage message = new UsersMessage();
		message.setId(mId);
		message.setStatus(1);
		// 标记为已读
		usersMessageMapper.updateByPrimaryKeySelective(message);
		return usersMessageMapper.selectByPrimaryKey(mId);
	}

	@Override
	public void findUserMessage(String uId, PageInfo pageInfo) {
		UsersMessageExample example = new UsersMessageExample();
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getPagesize());
		example.createCriteria().andUIdEqualTo(uId);
		example.setOrderByClause("time desc");
		long total = usersMessageMapper.countByExample(example);
		if (total > 0) {
			pageInfo.setRows(usersMessageMapper.selectByExample(example));
			pageInfo.setTotal((int) total);
		} else {
			pageInfo.setMsg("暂无消息");
		}
	}

	@Override
	public JSONObject finUserInfo(String uId) {
		JSONObject json = new JSONObject();
		Users users = usersMapper.selectByPrimaryKey(uId);
		String telephone = null;
		String picture = null;
		if (users != null) {
			telephone = users.getTelephone();
		}
		json.put("telephone", telephone);

		UsersPropertiesExample example = new UsersPropertiesExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersProperties> list = usersPropertiesMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			picture = list.get(0).getPicture();
		}
		json.put("picture", picture);

		return json;
	}

	@Override
	public JSONObject findExpectEarnings(String uId) {
		JSONObject json = new JSONObject();
		// TradingExample tradingExample = new TradingExample();
		// tradingExample.createCriteria().andUIdEqualTo(uId).andPIdNotEqualTo(1).andStatusIn(Arrays.asList(1,
		// 2, 4));
		//
		// List<Trading> tradingList =
		// tradingMapper.selectByExample(tradingExample);
		// BigDecimal expectEarnings = new BigDecimal(0);
		// if (Common.isNotBlank(tradingList)) {
		// TradingFixInfoExample tradingFixInfoExample = null;
		// List<TradingFixInfo> tradingFixInfoList = null;
		// for (Trading trading : tradingList) {
		// tradingFixInfoExample = new TradingFixInfoExample();
		// tradingFixInfoExample.createCriteria().andTIdEqualTo(trading.getId());
		//
		// tradingFixInfoList =
		// tradingFixInfoMapper.selectByExample(tradingFixInfoExample);
		//
		// if (Common.isNotBlank(tradingFixInfoList)) {
		// TradingFixInfo tradingFixInfo = tradingFixInfoList.get(0);
		//
		// BigDecimal interest = tradingFixInfo.getInterest();
		// if (interest == null) {
		// interest =
		// ProductUtils.countInterest(tradingFixInfo.getFinancialMoney(),
		// tradingFixInfo.getfTime(), tradingFixInfo.getInterestRate());
		// }
		// expectEarnings = expectEarnings.add(interest);
		// }
		// }
		// }
		UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
		json.put("income", usersAccount.getIncome());

		Map<String, BigDecimal> map = tradingMapper.queryUserPresentFinancialMoneyAndIncome(uId);
		if (map != null && map.size() > 0) {
			json.put("expectEarnings", map.get("interest"));
		} else {
			json.put("expectEarnings", "0.00");
		}
		return json;
	}

	@Override
	public JSONObject findUserFinance(String uId) {
		JSONObject json = new JSONObject();
		TradingExample tradingExample = new TradingExample();
		tradingExample.createCriteria().andPIdNotEqualTo(1).andUIdEqualTo(uId).andStatusEqualTo(1);
		List<Trading> list = tradingMapper.selectByExample(tradingExample);

		BigDecimal shortTerm = new BigDecimal(0);
		BigDecimal midTerm = new BigDecimal(0);
		BigDecimal longTerm = new BigDecimal(0);
		BigDecimal bigTerm = new BigDecimal(0);
		BigDecimal takeheart = new BigDecimal(0);
		BigDecimal currentBalance = new BigDecimal(0);
		if (list != null && list.size() > 0) {
			Product product = null;
			Integer fTime = null;
			for (Trading trading : list) {
				currentBalance = currentBalance.add(trading.getFinancialMoney());
				product = productMapper.selectByPrimaryKey(trading.getpId());
				fTime = product.getfTime();
				if (fTime != null) {
					if (fTime < 30) {
						shortTerm = shortTerm.add(trading.getFinancialMoney());
					} else if (fTime >= 30 && fTime <= 90) {
						midTerm = midTerm.add(trading.getFinancialMoney());
					} else if (fTime > 90 && fTime <= 180) {
						longTerm = longTerm.add(trading.getFinancialMoney());
					} else if (fTime > 180) {
						bigTerm = bigTerm.add(trading.getFinancialMoney());
					}
				} else {
					// 为空表示随心取
					takeheart = takeheart.add(trading.getFinancialMoney());
				}
			}
		}
		json.put("takeheart", takeheart);
		json.put("shortTerm", shortTerm);
		json.put("midTerm", midTerm);
		json.put("longTerm", longTerm);
		json.put("bigTerm", bigTerm);

		Map<String, BigDecimal> map = tradingMapper.queryUserPresentFinancialMoneyAndIncome(uId);
		if (map != null && map.size() > 0) {
			json.put("currentBalance", map.get("financialMoney"));
		} else {
			json.put("currentBalance", "0.00");
		}
		return json;
	}

	@Override
	public JSONObject hasMessage(String uId) {
		JSONObject json = new JSONObject();
		UsersMessageExample example = new UsersMessageExample();
		example.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(0);
		List<UsersMessage> list = usersMessageMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			json.put("hasMessage", true);
		} else {
			json.put("hasMessage", false);
		}
		return json;
	}

	@Override
	public JSONObject findUserBalance(String uId) {
		JSONObject json = new JSONObject();
		UsersAccountExample example = new UsersAccountExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersAccount> list = usersAccountMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			BigDecimal balance = list.get(0).getBalance();
			BigDecimal frozenMoney = list.get(0).getFrozenMoney();
			if(frozenMoney == null) {
				frozenMoney = new BigDecimal("0");
			}
			if(balance == null) {
				balance = new BigDecimal("0");
			}
			json.put("balance", balance); // 帐号可用余额
			json.put("frozenMoney", frozenMoney); // 当前冻结余额
			json.put("fullBalance", balance.add(frozenMoney)); // 账户余额
			json.put("totalFinance", list.get(0).getTotalFinance());//理财总额
			json.put("totalincome", list.get(0).getIncome());//累计收益
		}
		return json;
	}

	@Override
    public void findTradingData(String uId, PageInfo pageInfo, Integer status, String multyStatus) {
		// 查询分页数据
        GridPage<TradingView> gridPage = myFinanceService.financialManageInfo(pageInfo, status, uId, multyStatus);
		// 总记录数
		long total = gridPage.getTotal();
		// 是否有数据
		if (total > 0) {
			// 数据返回列表
			List<UserFinance> list = new ArrayList<UserFinance>();
			// 理财列表
			List<TradingView> views = gridPage.getRows();
			// 声明我的理财bean
			UserFinance userFinance = null;

			Map<String, Object> params = new HashMap<>();
			if (status == -1) {
				params.put("status", null);
				params.put("multyStatus", multyStatus.split(","));
			} else {
				params.put("status", status);
			}
			params.put("uid", uId);
			params.put("number", null);
			List<TradingView> financialRecordList = myFinanceServiceImpl.listFormatterTradingRecord(params);
			BigDecimal amount = new BigDecimal(0); // 待支付金额
			BigDecimal profit = new BigDecimal(0); // 待支付收益
			for (TradingView financialRecord : financialRecordList) {
				amount = amount.add(financialRecord.getFinancialMoney());
				if (AppCons.TAKE_HEART_PCODE.equals(financialRecord.getpCode())) {
					profit = profit.add(financialRecord.getHreatInterest());
				} else if (AppCons.New_Buyer_PCODE.equals(financialRecord.getpCode())) {
					profit = profit.add(financialRecord.getInterest());
				} else {
					profit = getProfit(profit, financialRecord);
				}
			}

			// 遍历
			for (TradingView tradingView : views) {
				userFinance = new UserFinance();
				// 是否有加息券信息
				if (tradingView.getUsersRedPacket() != null && tradingView.getHxRedPacket() != null) {
					userFinance.setActualAmount(tradingView.getUsersRedPacket().getActualAmount());
					userFinance.setHrpNumber(tradingView.getHxRedPacket().getHrpNumber());
					userFinance.setHrpType(tradingView.getHxRedPacket().getHrpType());
				}
				userFinance.setBuyDt(tradingView.getBuyDt());
				userFinance.setEdDt(tradingView.getProduct().getEdDt());
				if (AppCons.TAKE_HEART_PCODE.equals(tradingView.getpCode())) {
					userFinance.setExpectEarn(tradingView.getHreatInterest());
				} else if (AppCons.New_Buyer_PCODE.equals(tradingView.getpCode())) {
					userFinance.setExpectEarn(tradingView.getInterest());
				} else {
					if ((null == tradingView.getInterest()
							|| new BigDecimal("0").compareTo(tradingView.getInterest()) != -1) && tradingView.getStatus() != 3) {
						userFinance.setExpectEarn(ProductUtils.countInterest(tradingView.getFinancialMoney(),
								tradingView.getProduct().getfTime(), tradingView.getProduct().getfYield()));
					} else {
						userFinance.setExpectEarn(tradingView.getInterest());
					}
				}
				userFinance.setExpireDt(tradingView.getMinSellDt());
				userFinance.setFinancialMoney(tradingView.getFinancialMoney());
				userFinance.setfYield(tradingView.getProduct().getfYield());
				userFinance.setId(tradingView.getId());
				userFinance.setPayMoney(tradingView.getFinancialMoney());
				userFinance.setProductCode(tradingView.getpCode());
				userFinance.setProductName(tradingView.getProduct().getName());
				userFinance.setProductTerm(tradingView.getProduct().getfTime());
				userFinance.setProType(tradingView.getProduct().getPcName());
				userFinance.setRemainDt(tradingView.getTheRestTimeOfPayment());
				userFinance.setStatus(tradingView.getStatus());
				userFinance.setStDt(tradingView.getProduct().getStDt());
				userFinance.setValueDt(tradingView.getValueDt());
				userFinance.setHoldDays(tradingView.getFinancialDays());
				userFinance.setReWay(tradingView.getProduct().getReWay());
				userFinance.setpType(tradingView.getProduct().getpType());// 0
																			// 京东，1：中粮，2：华兴
				userFinance.setPcId(tradingView.getProduct().getPcId());
				userFinance.setInsuranceTrust(tradingView.getProduct().getInsuranceTrust());
				userFinance.setAddYield(tradingView.getProduct().getAddYield());
				userFinance.setIsDebt(tradingView.getProduct().getIsDebt());
				if (userFinance.getProductCode().equals(AppCons.TAKE_HEART_PCODE)) { // 随心取
					userFinance.setStalls(tradingView.getStalls());
					userFinance.setNextStalls(tradingView.getNextStalls());
					userFinance.setDifference(tradingView.getDifference());
					userFinance.setAllInterest(tradingView.getAllInterest());
					userFinance.setUpDay(tradingView.getUpDay());
					userFinance.setUpDayRate(tradingView.getUpDayRate());
				}

				list.add(userFinance);
			}

			JSONObject json = findIncome(uId);
			// DecimalFormat df = new DecimalFormat("#,##0.00");
			// df.setRoundingMode(RoundingMode.HALF_UP);
			json.put("amount", amount);
			json.put("profit", profit);

			pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("查询成功");
			pageInfo.setRows(list);
			pageInfo.setTotal((int) total);
			pageInfo.setObj(json);
		} else {
			pageInfo.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			pageInfo.setMsg("暂无数据");
		}
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
	public PageInfo findTakeHeartTransactionFlow(String uid, Integer pId, Integer tId, Integer limit) {
		PageInfo pi = new PageInfo();
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(pId) || StringUtils.isEmpty(tId)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("pid", pId);
		map.put("tid", tId);
		if (!StringUtils.isEmpty(limit)) {
			map.put("limit", limit);
		} else {
			map.put("limit", null);
		}

		List<TakeheartTransactionFlow> list = takeheartTransactionFlowMapper.findTakeheartTransactionFlow(map);
		if (list != null && list.size() > 0) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("success");
			pi.setRows(list);

		} else {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("查无数据");
		}

		return pi;
	}

	@Override
	public JSONObject findIncome(String uId) {
		JSONObject json = new JSONObject();
		BigDecimal income = null;
		UsersAccountExample example = new UsersAccountExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<UsersAccount> list = usersAccountMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			income = list.get(0).getIncome();
		}
		json.put("income", income);
		return json;
	}

	@Override
	public PageInfo findTakeheartTransactionFlow(String uId, PageInfo pageInfo, Integer tId, String yearmonth) {
		if (StringUtils.isEmpty(uId) || StringUtils.isEmpty(tId)) {
			pageInfo.setCode(ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("参数错误");
			return pageInfo;
		}

		Map<String, Object> mapResult = new HashMap<String, Object>();
		// 返回交易流水结果集
		List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
		// 参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uId", uId);
		map.put("tId", tId);
		map.put("yearmonth", yearmonth);
		// 查询所有年月、对应的理财金额、收益和流水(list)
		List<TakeheartTransactionFlowVo> listVos = productCustomerMapper.queryVoByYearmonth(map);
		// 循环查询该目录下内容
		if (null != listVos && listVos.size() > 0) {
			for (TakeheartTransactionFlowVo vo : listVos) {
				// 参数map
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("uId", uId);
				mapVo.put("tId", tId);
				mapVo.put("yearmonth", vo.getYearmonth());
				// 根据年月查询当月新增理财金额
				BigDecimal money = productCustomerMapper.queryMoneyByYearmonth(mapVo);
				// 根据年月查询当月交易流水
				List<TakeheartTransactionFlow> list = productCustomerMapper.queryByYearmonth(mapVo);
				// map用于接收年月、对应的理财金额、收益和流水
				Map<String, Object> mapList = new HashMap<String, Object>();
				mapList.put("yearmonth", vo.getYearmonth());
				mapList.put("money", money);
				mapList.put("interest", vo.getInterest());
				mapList.put("list", list);
				// 保存到list
				listMaps.add(mapList);
			}
		}
		mapResult.put("listTakeheartTransactionFlow", listMaps);
		mapResult.put("endYearmonth", DateUtils.sm.format(new Date()));
		Map<String, Object> mapAll = new HashMap<String, Object>();
		mapAll.put("uId", uId);
		mapAll.put("tId", tId);
		// 查询所有年月、对应的理财金额、收益和流水(list)
		List<TakeheartTransactionFlowVo> listVosAll = productCustomerMapper.queryVoByYearmonth(mapAll);
		if (null != listVosAll && listVosAll.size() > 0) {
			mapResult.put("startYearmonth", listVosAll.get(listVosAll.size() - 1).getYearmonth());

		} else {
			mapResult.put("startYearmonth", null);
		}
		pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("查询成功");
		pageInfo.setObj(mapResult);

		return pageInfo;
	}

	@Override
	public JSONObject getUserFreeingAmount(String uId) {
		JSONObject json = new JSONObject();
		BigDecimal waitBuyAmount = BigDecimal.ZERO; // 待支付冻结金额
		BigDecimal buyingAmount = BigDecimal.ZERO; // 支付中冻结金额
		BigDecimal waitCompleteAmount = BigDecimal.ZERO; // 待成立冻结金额
		BigDecimal withdrawalsAmount = BigDecimal.ZERO; // 提现中冻结金额

		for (int i = 0; i < 4; i++) {
			Map<String, Object> map = new HashMap<>();

			map.put("uid", uId);
			if (i == 0) { // 提现中
				BigDecimal frozenAmount = accountFlowMapper.getWithdrawalsFrozenAmount(map);
				withdrawalsAmount = null == frozenAmount ? BigDecimal.ZERO : frozenAmount;
			} else if (i == 1) { // 待成立
				map.put("status", 4);
				waitCompleteAmount = getFrozenAmount(waitCompleteAmount, map);
			} else if (i == 2) { // 支付中
				map.put("status", 12);
				buyingAmount = getFrozenAmount(buyingAmount, map);
			} else { // 待支付
				map.put("status", 0);
				waitBuyAmount = getFrozenAmount(waitBuyAmount, map);
			}
		}
		json.put("waitBuyAmount", waitBuyAmount.compareTo(BigDecimal.ZERO) == 0 ? "0.00" : waitBuyAmount);
		json.put("buyingAmount", buyingAmount.compareTo(BigDecimal.ZERO) == 0 ? "0.00" : buyingAmount);
		json.put("waitCompleteAmount",
				waitCompleteAmount.compareTo(BigDecimal.ZERO) == 0 ? "0.00" : waitCompleteAmount);
		json.put("withdrawalsAmount", withdrawalsAmount.compareTo(BigDecimal.ZERO) == 0 ? "0.00" : withdrawalsAmount);
		return json;
	}

	/**
	 * 获取冻结金额
	 * 
	 * @param freeingAmount
	 *            freeingAmount
	 * @param map
	 *            参数
	 * @return 数据返回
	 */
	private BigDecimal getFrozenAmount(BigDecimal amount, Map<String, Object> map) {
		BigDecimal frozenAmount = accountFlowMapper.selectByUidAndTypeAndStatus(map);
		if (null != frozenAmount) {
			amount = amount.add(frozenAmount);
		}
		return amount;
	}

    @Override
    public PageInfo getRepaymentCalendar(String uId, String date) {
        PageInfo pageInfo = new PageInfo();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        BigDecimal doneMoney = new BigDecimal("0");
        BigDecimal waitingMoney = new BigDecimal("0");
        List<String> doneDay = new ArrayList<String>();
        List<String> waitingDay = new ArrayList<String>();

        List<Map<String, Object>> tradingList = tradingMapper.listRepaymentData(uId, date);
        if (tradingList != null && tradingList.size() > 0) {
            for (Map<String, Object> trading : tradingList) {
                BigDecimal money = (BigDecimal) trading.get("money");
                String day = (String) trading.get("day");
                Integer status = (Integer) trading.get("state");

                if (status.intValue() == 3) {
                    // 已回款
                    doneMoney = doneMoney.add(money);
                    if (!doneDay.contains(day)) {
                        doneDay.add(day);
                    }

                } else {
                    // 待回款
                    waitingMoney = waitingMoney.add(money);
                    if (!waitingDay.contains(day)) {
                        waitingDay.add(day);
                    }
                }
            }
            pageInfo.setResultInfo(ResultInfo.SUCCESS);

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }
        
        resultMap.put("doneMoney", doneMoney);
        resultMap.put("waitingMoney", waitingMoney);
        resultMap.put("doneDay", doneDay);
        resultMap.put("waitingDay", waitingDay);
        resultMap.put("date", date);
        
        pageInfo.setObj(resultMap);

        return pageInfo;
    }

    @Override
    public PageInfo getAccompanyDays(String uId) {
        PageInfo pageInfo = new PageInfo();

        UsersProperties up = usersPropertiesMapper.selectByuId(uId);
        if (up != null) {
            int days = DateUtils.dateDiff(up.getRegDate(), new Date());
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("front", "领钱儿已陪伴您");
            resultMap.put("days", days + "");
            resultMap.put("behind", "天");
            pageInfo.setObj(resultMap);

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

    @Override
    public PageInfo listFrozenMoneyDetail(String uid, Integer pageNo, Integer pageSize) {
        PageInfo pageInfo = new PageInfo(pageNo, pageSize);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("uid", uid);
        paramMap.put("start", pageInfo.getFrom());
        paramMap.put("number", pageInfo.getSize());
        List<Map<String, Object>> list = accountFlowMapper.listFrozenMoneyDetail(paramMap);
        if (list != null && list.size() > 0) {
            pageInfo.setRows(list);
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            pageInfo.setTotal(accountFlowMapper.countListFrozenMoneyDetail(paramMap));

        } else {
            pageInfo.setResultInfo(ResultInfo.NO_DATA);
        }

        return pageInfo;
    }

	@Override
	public void queryUserFinProcess(String uId, PageInfo pageInfo) {
		JSONObject jsonObject = new JSONObject();
		//查询用户开户，绑卡状态
		String key = USER_FIN_PROCESS + uId;
		if (redisDao.get(key) != null) {
			pageInfo.setObj(redisDao.get(key));
		} else {
			UsersProperties uProperties = usersPropertiesMapper.selectByuId(uId);
			if (uProperties != null) {
				if (uProperties.getCertification() == 2 || uProperties.getCertification() == 3) {
					jsonObject.put("accountOpen", true);
					if (uProperties.getBank() == 2 || uProperties.getBank() == 3) {
						jsonObject.put("bindCard", true);
						
						AccountFlowExample accountFlowExample = new AccountFlowExample();
						accountFlowExample.createCriteria().andTypeEqualTo(ResultNumber.ZERO.getNumber()).andStatusEqualTo(ResultNumber.ONE.getNumber());
						
						Long count = accountFlowMapper.countByExample(accountFlowExample);
						
						if (count > 0) {
							jsonObject.put("recharge", true);
							AccountFlowExample accountFlowExample2 = new AccountFlowExample();
							accountFlowExample2.createCriteria().andTypeEqualTo(ResultNumber.TWO.getNumber()).andStatusEqualTo(ResultNumber.ONE.getNumber());
							
							Long count2 = accountFlowMapper.countByExample(accountFlowExample2);
							if (count2 > 0) {
								jsonObject.put("buyProduct", true);
								
								//有购买记录后写入到redis便于以后查询
								redisDao.set(key, jsonObject);
							}
						}
					}
				}
			}
			pageInfo.setObj(jsonObject);
		}
	}

}
