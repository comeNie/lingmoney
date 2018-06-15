package com.mrbt.lingmoney.service.trading.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ProductCategoryFixRateMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TakeheartSellPayMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UserFinanceMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.model.trading.buy.TradingBuyCallback;
import com.mrbt.lingmoney.service.product.ProductService;
import com.mrbt.lingmoney.service.trading.BaseTradingService;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.BigDecimalUtils;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月8日 上午10:31:49
 * @version 1.0
 * @description
 **/
@Transactional
@Service
public class TradingServiceImpl extends BaseTradingService implements TradingService {
	private static final Logger LOG = LogManager.getLogger(TradingServiceImpl.class);

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private TakeheartFixRateMapper takeheartFixRateMapper;
	@Autowired
	private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TakeheartSellPayMapper takeheartSellPayMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private UserFinanceMapper userFinanceMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private UserFinanceHomeService userFinanceHomeService;
	@Autowired
	public TradingBuyCallback tradingBuyCallback;
    @Autowired
    private ProductCategoryFixRateMapper productCategoryFixRateMapper;

	@Override
	public SellResult sellProduct(String uid, Integer tid, Double moneyInput, Integer redeemType, String logGroup) {
		SellResult sellResult = new SellResult();
		sellResult.setFlag(1); // 结果标识，1失败 0成功 用户页面返回值判断

		Trading trading = tradingMapper.selectByPrimaryKey(tid);

		// 验证交易信息
		SellResult validTradingResult = validTradingInfo(uid, tid, moneyInput, redeemType, sellResult, trading,
				logGroup);

		if (validTradingResult != null) {
			return validTradingResult;
		}

		// 赎回日期
		trading.setSellDt(new Date());
		// 创建卖出交易码时需要判断此状态
		trading.setStatus(AppCons.SELL_STATUS);
		// 创建卖出的交易码
		Common.buildOutBizCode(trading, logGroup);

        // 卖出类型判断
		boolean sellSuccess = false;
        // 随心取卖出
		if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {
			LOG.info(logGroup + "=================随心取卖出操作开始执行=================");
			sellSuccess = sellTakeHeart(trading, redeemType, moneyInput, logGroup);
			String key = AppCons.TAKE_HEART + trading.getuId();
			redisDao.delete(key);

            // 随心取A计划卖出
        } else if (trading.getpId() == 53) {
            LOG.info(logGroup + "=================随心取A计划卖出操作开始执行=================");
            sellSuccess = sellTakeHeartAPlan(trading, logGroup);
		}

		if (sellSuccess) { // 卖出成功
			LOG.info("用户{}卖出 {}号理财，卖出成功", logGroup, uid, tid);
			sellResult.setFlag(0);
		} else {
			LOG.info("用户{}卖出 {}号理财，卖出失败", logGroup, uid, tid);
			sellResult.setCode("5100");
			sellResult.setMsg("只能赎回随心取产品");
			sellResult.setFlag(1);
		}

		return sellResult;
	}

	/**
	 * 随心取A计划赎回数据操作
	 */
    private boolean sellTakeHeartAPlan(Trading trading, String logGroup) {
        TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
        if (tradingFixInfo == null) {
            LOG.info("{}未查询到有效交易流水信息，tid:{}", logGroup, trading.getId());
            return false;
        }

        // 持有天数
        int tDays = DateUtils.dateDiff(trading.getValueDt(), new Date());
        // 查询当前利率
        ProductCategoryFixRate productCategoryFixRate = new ProductCategoryFixRate();
        productCategoryFixRate.setpId(trading.getpId());
        productCategoryFixRate.setrTime(tDays);
        BigDecimal fixRate = productCategoryFixRateMapper.findRate(productCategoryFixRate);
        // 计算利息
        BigDecimal interest = ProductUtils.countInterest(trading.getFinancialMoney(), tDays, fixRate);

        // 更新trading表数据
        Trading record = new Trading();
        record.setId(trading.getId());
        record.setSellDt(new Date());
        record.setOutBizCode(trading.getOutBizCode());
        record.setStatus(AppCons.SELL_STATUS);
        // 卖出金额 = 理财金额 + 利息
        record.setSellMoney(trading.getFinancialMoney().add(interest));
        tradingMapper.updateByPrimaryKeySelective(record);

        TradingFixInfo infoRecord = new TradingFixInfo();
        infoRecord.setId(tradingFixInfo.getId());
        infoRecord.setInterest(interest);
        infoRecord.setfTime(tDays);
        infoRecord.setStatus(AppCons.SELL_STATUS);
        infoRecord.setInterestRate(fixRate);
        infoRecord.setExpiryDt(new Date());
        tradingFixInfoMapper.updateByPrimaryKeySelective(infoRecord);

        // 插入一条账户流水
        UsersAccount userAcc = usersAccountMapper.selectByUid(trading.getuId());
        AccountFlow accFlow = new AccountFlow();
        accFlow.setaId(userAcc.getId());
        accFlow.setBalance(userAcc.getBalance());
        accFlow.setMoney(record.getSellMoney());
        accFlow.setNote("赎回");
        accFlow.setNumber(trading.getOutBizCode());
        accFlow.setOperateTime(new Date());
        accFlow.setPlatform(0);
        accFlow.setType(AppCons.ACCOUNT_FLOW_TYPE_REDEEM);
        accFlow.setStatus(0);
        accFlow.settId(trading.getId());
        accountFlowMapper.insertSelective(accFlow);
        return true;
    }

    @Override
	public GridPage<Map<String, Object>> listTRUserGrid(int pid, Integer pageNo) {
		GridPage<Map<String, Object>> result = new GridPage<Map<String, Object>>();
		result.setRows(listTRUser(pid, pageNo));
		result.setTotal(listTRUserCount(pid));
		return result;
	}

	@Override
	@Transactional
	public PageInfo cancelPayment(String uid, Integer tId) {
		String logGroup = "\n取消支付" + System.currentTimeMillis() + "_";

		LOG.info("{}参数信息：{}:{}", logGroup, tId, uid);

		PageInfo pi = new PageInfo();

		if (StringUtils.isEmpty(tId)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
			return pi;
		}

		Trading trading = tradingMapper.selectByPrimaryKey(tId);

		if (trading == null) {
			pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("操作失败,请刷新后重试");
			LOG.info("{}取消支付失败，未查询到指定交易信息", logGroup);
			return pi;
		}

		if (!trading.getuId().equals(uid)) {
			pi.setCode(ResultInfo.ORDER_INFO_ERROR.getCode());
			pi.setMsg("操作失败,请刷新后重试");
			LOG.info("{}取消支付失败，交易用户不匹配", logGroup);
			return pi;
		}

		// 状态为取消时，直接返回成功
		if (trading.getStatus().intValue() == AppCons.BUY_CANCEL) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("操作超时，该订单已自动取消");
			return pi;
		}

		// 只能取消状态为0的交易（待支付）
		if (trading.getStatus().intValue() != AppCons.BUY_STATUS) {
			pi.setCode(ResultInfo.ORDER_INFO_ERROR.getCode());
			pi.setMsg("操作失败,请刷新后重试");
			LOG.info("{}取消支付失败，交易状态错误", logGroup);
			return pi;
		}

		try {
			Trading tradingRecord = new Trading();
			tradingRecord.setId(trading.getId());
			tradingRecord.setStatus(AppCons.BUY_CANCEL);
			int result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

			if (result > 0) {
				LOG.info("{}更新交易状态成功。", logGroup);

				TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tId);

				if (tfi != null) {
					TradingFixInfo fixInfoRecord = new TradingFixInfo();
					fixInfoRecord.setId(tfi.getId());
					fixInfoRecord.setStatus(AppCons.BUY_CANCEL);
					result = tradingFixInfoMapper.updateByPrimaryKeySelective(fixInfoRecord);

					if (result > 0) {
						LOG.info("{}更新交易流水状态成功。", logGroup);

					} else {
						LOG.error("{}更新交易流水状态失败。", logGroup);
						throw new SQLException("更新trading_fix_info数据失败，id:" + tfi.getId());
					}

				} else {
					LOG.error("{}未查询到固定交易信息", logGroup);
					throw new IllegalArgumentException("未查询到交易id为" + tId + "的交易流水信息");
				}

				// 产品已筹规模回复
				ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
				if (product != null) {
					ProductWithBLOBs productRecord = new ProductWithBLOBs();
					productRecord.setId(product.getId());
					productRecord.setReachMoney(product.getReachMoney().subtract(trading.getBuyMoney()));
					result = productMapper.updateByPrimaryKeySelective(productRecord);

					if (result > 0) {
						LOG.info("{}更新产品募集金额成功。", logGroup);

					} else {
						LOG.error("{}更新产品募集金额失败。", logGroup);
						throw new SQLException("更新product数据失败，id:" + product.getId());
					}

				} else {
					LOG.error("{}未查询到产品信息：", logGroup);
					throw new IllegalArgumentException("未查询到id为" + trading.getpId() + "的产品信息");
				}

				// 如果使用过优惠券，恢复优惠券
				UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
				usersRedPacketExmp.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(tId).andStatusEqualTo(1);

				List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
				if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
					for (UsersRedPacket usersRedPacket : usersRedPacketList) {
						usersRedPacket.setStatus(0);
						usersRedPacket.settId(null);
						usersRedPacket.setActualAmount(null);
						usersRedPacket.setUsedTime(null);
						result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

						if (result > 0) {
							LOG.info("{}恢复加息券信息成功。", logGroup);

						} else {
							LOG.error("{}恢复加息券信息失败。", logGroup);
							throw new SQLException("更新user_red_packet数据失败，id:" + usersRedPacket.getId());
						}
					}

				} else {
					LOG.info("{}无加息券使用记录");
				}

				UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
				if (usersAccount != null) {
					if (product.getpType() == 2) {
						// 修改用户账户表 balance↑ frozen_money↓
						usersAccount.setBalance(usersAccount.getBalance().add(trading.getBuyMoney()));
						usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
						result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);

						if (result > 0) {
							LOG.info("{}修改用户账户表成功。", logGroup);

						} else {
							LOG.error("{}修改用户账户表失败。", logGroup);
							throw new SQLException("更新users_account数据失败，id:" + usersAccount.getId());
						}
					}

					// 修改资金流水状态为取消支付6
					AccountFlowExample example = new AccountFlowExample();
					example.createCriteria().andTIdEqualTo(tId).andTypeEqualTo(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL)
							.andStatusEqualTo(AppCons.ACCOUNT_FLOW_WAITING_PAYMENT).andAIdEqualTo(usersAccount.getId());
					AccountFlow accountFlow = new AccountFlow();
					accountFlow.setStatus(AppCons.ACCOUNT_FLOW_CANCEL_PAYMENT); // 取消支付
					result = accountFlowMapper.updateByExampleSelective(accountFlow, example);

					if (result > 0) {
						LOG.info("{}更新资金流水状态成功。", logGroup);

					} else {
						LOG.error("{}更新资金流水状态失败。", logGroup);
						throw new SQLException("更新account_flow数据失败");
					}

				} else {
					LOG.error("{}未查询到该订单资金流水信息", logGroup);
					throw new IllegalArgumentException("未查询到用户id为" + uid + "的账户信息");
				}

				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("取消成功");

			} else {
				pi.setCode(ResultInfo.SERVER_ERROR.getCode());
				pi.setMsg("取消失败");
				LOG.error("{}取消支付失败，更新交易信息失败", logGroup);
				throw new SQLException("更新trading失败,id:" + tId);
			}

		} catch (SQLException e) {
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("取消失败，请与管理员联系。code:" + tId);
			// 出错直接回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			LOG.error("{}执行出错，回滚数据。{}", logGroup, e.toString());
		}

		LOG.info("{}取消订单操作执行完毕。", logGroup);
		return pi;
	}

	@Override
	public PageInfo unPayOrder(String uid) {
		PageInfo pi = new PageInfo(0, 1);

		if (!StringUtils.isEmpty(uid)) {
			String logHead = "\n" + System.currentTimeMillis() + "_";
			LOG.info(logHead + "开始执行查询用户未支付订单操作 \t" + uid);
            userFinanceHomeService.findTradingData(uid, pi, 0, null);
			// 判断用户是否有未完成交易 status = 0
			int total = pi.getTotal();
			if (total > 0) {
				pi.setObj(pi.getRows().get(0));
				pi.setRows(null);
				pi.setTotal(0);
				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg("查询成功");

			} else {
				pi.setCode(ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
				pi.setMsg("暂无未支付的产品");
				LOG.info(logHead + "用户" + uid + "暂无未支付的产品");
			}
		} else {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("参数错误");
		}

		return pi;
	}

	/**
	 * 查询某产品交易总数
	 * 
	 * @param pid
	 *            产品id
	 * @return 产品交易总数
	 */
	private int listTRUserCount(int pid) {
		return tradingMapper.selectTrUserCount(pid);
	}

	/**
	 * 查询某产品交易
	 * 
	 * @param pid
	 *            产品id
	 * @param pageNo
	 *            分页页数
	 * @return 交易信息list
	 */
	private List<Map<String, Object>> listTRUser(int pid, Integer pageNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageInfo pi = new PageInfo(pageNo, AppCons.DEFAULT_PAGE_SIZE);
		params.put("pid", pid);
		params.put("start", pi.getFrom());
		params.put("number", pi.getSize());
		return tradingMapper.selectTRUserList(params);
	}

	/**
	 * 验证交易信息
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @param moneyInput
	 *            金额
	 * @param redeemType
	 *            赎回类型
	 * @param sellResult
	 *            卖出结果
	 * @param trading
	 *            交易
	 * @param logGroup
	 *            日志头
	 * @return 卖出结果sellresult
	 *
	 */
	private SellResult validTradingInfo(String uid, Integer tid, Double moneyInput, Integer redeemType,
			SellResult sellResult, Trading trading, String logGroup) {

		if (trading == null) {
			LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。未查询到交易信息");

			sellResult.setCode("20012");
			sellResult.setMsg("交易不存在");
			return sellResult;
		}

		if (trading.getStatus() != AppCons.BUY_OK) {
			LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。交易状态错误");

			sellResult.setCode("20032");
			sellResult.setMsg("不是已经买入的产品");
			return sellResult;
		}

		if (!uid.equals(trading.getuId())) {
			LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。用户不匹配");

			sellResult.setCode("20011");
			sellResult.setMsg("赎回用户错误，非本用户");
			return sellResult;
		}

		if (redeemType == null || redeemType < 1 || 2 < redeemType) {
			LOG.info("用户卖出{}号理财失败，无效赎回类型");

			sellResult.setCode("20022");
			sellResult.setMsg("赎回类型错误");
			return sellResult;
		}

		if (redeemType == 1) { // 部分赎回
			if (StringUtils.isEmpty(moneyInput) || moneyInput < 0.01) {
				LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出金额不合理" + moneyInput);

				sellResult.setCode("12000");
				sellResult.setMsg("金额有误,请重新输入");
				return sellResult;
			}

			// 判断理财金额是否小于赎回金额
			if (trading.getFinancialMoney().doubleValue() < moneyInput) {
				LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败，理财金额小于卖出金额");

				sellResult.setCode("11000");
				sellResult.setMsg("卖出失败,理财金额小于卖出金额");
				return sellResult;
			}
		}

		if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {

			// 判断是否禁止赎回
			if (redisDao.get("takeHeart_Allow_Sell") != null) {
				LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败。随心取禁止赎回");

				sellResult.setCode("31000");
				sellResult.setMsg(redisDao.get("takeHeart_Allow_Sell").toString());
				return sellResult;
			}

			// 判读是否可以赎回
			String key = AppCons.TAKE_HEART + uid;
			if (!checkTakeHeart(key)) {
				LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败。操作次数过多。");

				sellResult.setCode("21000");
				sellResult.setMsg("操作次数过多,请稍后再试");
				return sellResult;
			} else {
				setTakeHeart(key);
			}
		}

		LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财。交易信息验证通过");

		return null;
	}

	/**
	 * 随心取卖出操作
	 * 
	 * @param trading
	 *            交易信息
	 * @param redeemType
	 *            赎回类型
	 * @param sellMoney
	 *            卖出金额
	 * @param logGroup
	 *            日志头
	 * @return true 成功 false 失败
	 */
	@Transactional
	private boolean sellTakeHeart(Trading trading, Integer redeemType, Double sellMoney, String logGroup) {
		// 进行结息
		Date date = null;
		if (trading.getSellDt() == null) {
			date = DateUtils.getTradeDate(new Date(), findHoliday());
		} else {
			date = DateUtils.getTradeDate(trading.getSellDt(), findHoliday());
		}
		int interestDay = DateUtils.dateDiff(trading.getLastValueDt(), date); // 计息天数
		BigDecimal interest = new BigDecimal(0); // 所得利息

		// 需要计息
		if (interestDay > 0) {
			// 获取购买产品以来的总时长，用于计算利息
			int day = DateUtils.dateDiff(trading.getValueDt(), date);
			Map<String, Object> fixRateParams = new HashMap<String, Object>();
			fixRateParams.put("rDay", day);
			fixRateParams.put("financialMoney", trading.getFinancialMoney());
			Map<String, Object> rateMap = takeheartFixRateMapper.getTakeHeartFixRate(fixRateParams);
			// 利率
			BigDecimal rate = (BigDecimal) rateMap.get("rate");
			interest = BigDecimalUtils.money(trading.getFinancialMoney().multiply(rate)
					.multiply(new BigDecimal(interestDay)).divide(new BigDecimal(365), 3));

			// 插入计息流水记录
			TakeheartTransactionFlow takeHeartFlow = new TakeheartTransactionFlow();
			takeHeartFlow.setuId(trading.getuId());
			takeHeartFlow.setpId(trading.getpId());
			takeHeartFlow.settId(trading.getId());
			takeHeartFlow.setType(2);
			takeHeartFlow.setState(1);
			takeHeartFlow.setOperateTime(new Date());
			takeHeartFlow.setInterest(interest);
			int saveTakeHeartFlow = takeheartTransactionFlowMapper.insertSelective(takeHeartFlow);

			if (saveTakeHeartFlow != 1) {
				LOG.error(logGroup + "插入随心取计息流水记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
						+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
				throw new RuntimeException("插入随心取计息流水记录错误,TakeheartTransactionFlow");
			}
			LOG.info(logGroup + "插入随心取计息流水记录成功 ,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
					+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

			// 插入账户流水表付息记录
			Map<String, Object> accountFlowParams = new HashMap<String, Object>();
			accountFlowParams.put("tid", trading.getId());
			accountFlowParams.put("uid", trading.getuId());
			accountFlowParams.put("interest", interest);
			int saveAccountFlow = accountFlowMapper.saveAccountFlowInterest(accountFlowParams);

			if (saveAccountFlow < 1) {
				LOG.error(logGroup + "插入用户账户计息流水记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
						+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
				throw new RuntimeException("插入账户流水记录错误,AccountFlow");
			}

			LOG.info(logGroup + "插入用户账户计息流水记录成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
					+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

			// 更新trading收益和总金额
			Trading tradingUpdateRecord = new Trading();
			tradingUpdateRecord.setId(trading.getId());
//			tradingUpdateRecord.setFinancialMoney(trading.getFinancialMoney().add(interest));
			tradingUpdateRecord.setInterest(interest);
			tradingUpdateRecord.setAllInterest(trading.getAllInterest().add(interest));
			tradingUpdateRecord.setLastValueDt(date);
			int updateTrading = tradingMapper.updateByPrimaryKeySelective(tradingUpdateRecord);

			if (updateTrading < 1) {
				LOG.error(logGroup + "更新trading表记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
						+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
				throw new RuntimeException("更新trading表错误,trading");
			}
			LOG.info(logGroup + "更新trading表记录成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
					+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

			// 更新账户总额加上付息金额
			UsersAccount account = usersAccountMapper.selectByUid(trading.getuId());
			UsersAccount accountUpdateRecord = new UsersAccount();
			accountUpdateRecord.setId(account.getId());
			accountUpdateRecord.setTotalAsset(account.getTotalAsset().add(interest));
			accountUpdateRecord.setTotalFinance(account.getTotalFinance().add(interest));
			accountUpdateRecord.setIncome(account.getIncome().add(interest));
			int updateUserAccount = usersAccountMapper.updateByPrimaryKeySelective(accountUpdateRecord);

			if (updateUserAccount < 1) {
				LOG.error(logGroup + "更新用户账户信息错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
						+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
				throw new RuntimeException("更新账户总额加上付息金额错误,UsersAccount");
			}

			LOG.info(logGroup + "更新用户账户信息成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
					+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

		}

		// 插入一条随心取交易流水记录 部分赎回/全部赎回共同数据
		TakeheartTransactionFlow saveTakeheartFlowRecord = new TakeheartTransactionFlow();
		saveTakeheartFlowRecord.setuId(trading.getuId());
		saveTakeheartFlowRecord.setpId(trading.getpId());
		saveTakeheartFlowRecord.settId(trading.getId());
		saveTakeheartFlowRecord.setType(1);
		saveTakeheartFlowRecord.setState(2);
		saveTakeheartFlowRecord.setOperateTime(
				new Timestamp(trading.getSellDt() == null ? (new Date()).getTime() : trading.getSellDt().getTime()));
		saveTakeheartFlowRecord.setNumber(trading.getOutBizCode());

		// 更新trading表记录 部分赎回/全部赎回共同数据
		Trading updateTradingRecordAfterFlow = new Trading();
		updateTradingRecordAfterFlow.setId(trading.getId());

		if (redeemType == 2) { // 全部卖出
			saveTakeheartFlowRecord.setMoney(trading.getFinancialMoney().add(interest));
			saveTakeheartFlowRecord.setInterest(new BigDecimal(0));
			saveTakeheartFlowRecord.setRedeemType(1);
            updateTradingRecordAfterFlow.setFinancialMoney(trading.getFinancialMoney().add(interest));
			updateTradingRecordAfterFlow.setStatus(AppCons.SELL_STATUS);
			updateTradingRecordAfterFlow.setSellDt(new Date());
		} else { // 部分卖出
			saveTakeheartFlowRecord.setRedeemType(0);
			saveTakeheartFlowRecord.setMoney(new BigDecimal(sellMoney));
            updateTradingRecordAfterFlow.setFinancialMoney(trading.getFinancialMoney()
                    .subtract(new BigDecimal(sellMoney)).add(interest));
		}

		int tfId = -1; // 操作结果标识 -1表示失败

		int ok = takeheartTransactionFlowMapper.insertSelectiveReturnKey(saveTakeheartFlowRecord);
		if (ok > 0) {
			tfId = saveTakeheartFlowRecord.getId(); // 交易流水ID
			LOG.info(logGroup + "保存随心取流水数据成功。 id:" + tfId);

			// 更新trading表记录
			ok = tradingMapper.updateByPrimaryKeySelective(updateTradingRecordAfterFlow);
			if (ok < 1) {
				tfId = -1;
				LOG.error(logGroup + "更新交易记录数据失败。 id:" + updateTradingRecordAfterFlow.toString());
			} else {
				LOG.info(logGroup + "更新交易记录数据成功。 id:" + updateTradingRecordAfterFlow.getId());
			}

		} else {
			tfId = -1;
			LOG.error(logGroup + "随心取模式产品卖出失败,保存随心取流水记录失败,uid:" + trading.getuId() + ",tid:" + trading.getId()
					+ ",buyDt:" + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
		}

		if (tfId > 0) {
			// 插入一条随心取买卖记录
			Map<String, Object> sellPayParams = new HashMap<String, Object>();
			sellPayParams.put("pCode", trading.getpCode());

			Product product = productCustomerMapper.selectByCode(trading.getpCode());
			sellPayParams.put("platUserNo", product.getPlatformUserNo() + ""); // string类型

			// 时间格式 2016-04-06 00:25:11 小时设置为0 原项目设置，理由未知，忽略~
			java.util.Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			sellPayParams.put("operateTime", new Timestamp(calendar.getTimeInMillis()));
			sellPayParams.put("tfId", tfId);
			ok = takeheartSellPayMapper.saveAfterSell(sellPayParams);

			if (ok > 0) {
				LOG.error("{}保存插入随心取买卖记录成功, tfId:{}", logGroup, tfId);
			} else {
				LOG.error("{}保存插入随心取买卖记录失败, tfId:{}", logGroup, tfId);
			}

		} else {
			LOG.error(logGroup + "随心取模式产品卖出失败,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
					+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

			throw new RuntimeException("随心取模式产品卖出失败");
		}

		LOG.error(logGroup + "随心取模式产品卖出成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
				+ trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney() + "; tfId:" + tfId);

		return true;
	}

	/**
	 * 从redis查询随心取是否存在
	 *
	 * @Description takeheart.interest
	 * @param key
	 *            随心取key
	 * @return 不存在 true 存在false
	 */
	public boolean checkTakeHeart(String key) {
		if (redisDao.get(key) == null && redisDao.get(AppCons.TAKE_HEART + "interest") == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存随心取购买记录到redis
	 *
	 * @Description 十分钟失效，用于限制购买/赎回
	 * @param key
	 *            随心取key
	 */
	public void setTakeHeart(String key) {
		redisDao.set(key, key);
		redisDao.expire(key, 10, TimeUnit.MINUTES);
	}

	@Override
	public String getContractId(String uid) {
		Integer tradingId = tradingMapper.getLastTradingIdByUserId(uid);
		String id = null;
		if (!StringUtils.isEmpty(tradingId)) {
			id = uid + String.valueOf(tradingId);
		}
		return id;
	}

	@Override
	public String getTidByUserId(String uid) {
		Integer tradingId = tradingMapper.getTidByUserId(uid);
		String id = null;
		if (!StringUtils.isEmpty(tradingId)) {
			id = uid + String.valueOf(tradingId);
		}
		return id;
	}

	@Override
	public Trading selectByExample(String uid) {
		TradingExample tradingExample = new TradingExample();
		tradingExample.createCriteria().andUIdEqualTo(uid);
		List<Trading> list = tradingMapper.selectByExample(tradingExample);
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

    @Transactional
    @Override
    public PageInfo cancelPaymentVersionOne(String uid, Integer tId) {
        String logGroup = "\n新版取消支付" + System.currentTimeMillis() + "_";

        LOG.info("{}参数信息：{}:{}", logGroup, tId, uid);

        PageInfo pi = new PageInfo();

        if (StringUtils.isEmpty(tId)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
            pi.setMsg("参数错误");
            return pi;
        }

        Trading trading = tradingMapper.selectByPrimaryKey(tId);

        if (trading == null) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
            pi.setMsg("操作失败,请刷新后重试");
            LOG.info("{}取消支付失败，未查询到指定交易信息", logGroup);
            return pi;
        }

        if (!trading.getuId().equals(uid)) {
            pi.setCode(ResultInfo.ORDER_INFO_ERROR.getCode());
            pi.setMsg("操作失败,请刷新后重试");
            LOG.info("{}取消支付失败，交易用户不匹配", logGroup);
            return pi;
        }

        // 状态为取消时，直接返回成功
        if (trading.getStatus().intValue() == AppCons.BUY_CANCEL) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
            pi.setMsg("操作超时，该订单已自动取消");
            return pi;
        }

        // 只能取消状态为0的交易（待支付）
        if (trading.getStatus().intValue() != AppCons.BUY_STATUS) {
            pi.setCode(ResultInfo.ORDER_INFO_ERROR.getCode());
            pi.setMsg("操作失败,请刷新后重试");
            LOG.info("{}取消支付失败，交易状态错误", logGroup);
            return pi;
        }

        try {
            Trading tradingRecord = new Trading();
            tradingRecord.setId(trading.getId());
            tradingRecord.setStatus(AppCons.BUY_CANCEL);
            int result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

            if (result > 0) {
                LOG.info("{}更新交易状态成功。", logGroup);

                TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tId);

                if (tfi != null) {
                    TradingFixInfo fixInfoRecord = new TradingFixInfo();
                    fixInfoRecord.setId(tfi.getId());
                    fixInfoRecord.setStatus(AppCons.BUY_CANCEL);
                    result = tradingFixInfoMapper.updateByPrimaryKeySelective(fixInfoRecord);

                    if (result > 0) {
                        LOG.info("{}更新交易流水状态成功。", logGroup);

                    } else {
                        LOG.error("{}更新交易流水状态失败。", logGroup);
                        throw new SQLException("更新trading_fix_info数据失败，id:" + tfi.getId());
                    }

                } else {
                    LOG.error("{}未查询到固定交易信息", logGroup);
                    throw new IllegalArgumentException("未查询到交易id为" + tId + "的交易流水信息");
                }

                // 产品已筹规模回复
                ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
                if (product != null) {
                    ProductWithBLOBs productRecord = new ProductWithBLOBs();
                    productRecord.setId(product.getId());
                    productRecord.setReachMoney(product.getReachMoney().subtract(trading.getBuyMoney()));
                    result = productMapper.updateByPrimaryKeySelective(productRecord);

                    if (result > 0) {
                        LOG.info("{}更新产品募集金额成功。", logGroup);

                    } else {
                        LOG.error("{}更新产品募集金额失败。", logGroup);
                        throw new SQLException("更新product数据失败，id:" + product.getId());
                    }

                } else {
                    LOG.error("{}未查询到产品信息：", logGroup);
                    throw new IllegalArgumentException("未查询到id为" + trading.getpId() + "的产品信息");
                }

                // 如果使用过优惠券，恢复优惠券
                UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
                usersRedPacketExmp.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(tId).andStatusEqualTo(1);

                List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
                if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
                    for (UsersRedPacket usersRedPacket : usersRedPacketList) {
                        usersRedPacket.setStatus(0);
                        usersRedPacket.settId(null);
                        usersRedPacket.setActualAmount(null);
                        usersRedPacket.setUsedTime(null);
                        result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

                        if (result > 0) {
                            LOG.info("{}恢复加息券信息成功。", logGroup);

                        } else {
                            LOG.error("{}恢复加息券信息失败。", logGroup);
                            throw new SQLException("更新user_red_packet数据失败，id:" + usersRedPacket.getId());
                        }
                    }

                } else {
                    LOG.info("{}无加息券使用记录");
                }

                UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
                if (usersAccount != null) {
                    // 修改资金流水状态为取消支付6
                    AccountFlowExample example = new AccountFlowExample();
                    example.createCriteria().andTIdEqualTo(tId).andTypeEqualTo(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL)
                            .andStatusEqualTo(AppCons.ACCOUNT_FLOW_WAITING_PAYMENT).andAIdEqualTo(usersAccount.getId());
                    AccountFlow accountFlow = new AccountFlow();
                    accountFlow.setStatus(AppCons.ACCOUNT_FLOW_CANCEL_PAYMENT); // 取消支付
                    result = accountFlowMapper.updateByExampleSelective(accountFlow, example);

                    if (result > 0) {
                        LOG.info("{}更新资金流水状态成功。", logGroup);

                    } else {
                        LOG.error("{}更新资金流水状态失败。", logGroup);
                        throw new SQLException("更新account_flow数据失败");
                    }

                } else {
                    LOG.error("{}未查询到该订单资金流水信息", logGroup);
                    throw new IllegalArgumentException("未查询到用户id为" + uid + "的账户信息");
                }

                pi.setCode(ResultInfo.SUCCESS.getCode());
                pi.setMsg("取消成功");

            } else {
                pi.setCode(ResultInfo.SERVER_ERROR.getCode());
                pi.setMsg("取消失败");
                LOG.error("{}取消支付失败，更新交易信息失败", logGroup);
                throw new SQLException("更新trading失败,id:" + tId);
            }

        } catch (SQLException e) {
            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
            pi.setMsg("取消失败，请与管理员联系。code:" + tId);
            // 出错直接回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            LOG.error("{}执行出错，回滚数据。{}", logGroup, e.toString());
        }

        LOG.info("{}取消订单操作执行完毕。", logGroup);
        return pi;
    }

}
