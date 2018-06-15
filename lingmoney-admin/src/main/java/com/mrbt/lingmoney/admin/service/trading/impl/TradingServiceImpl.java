package com.mrbt.lingmoney.admin.service.trading.impl;

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

import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.admin.service.trading.TradingService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TakeheartSellPayMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.BigDecimalUtils;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * 交易
 *
 */
@Service
public class TradingServiceImpl implements TradingService {
	private static final Logger LOGGER = LogManager.getLogger(TradingServiceImpl.class);

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
    @Autowired
    private TakeheartSellPayMapper takeheartSellPayMapper;
    @Autowired
    private CommonMethodService commonMethodService;
    @Autowired
    private TakeheartFixRateMapper takeheartFixRateMapper;
    @Autowired
    private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;

	@Override
	public GridPage<Map<String, Object>> listGridWenYingBao(Map<String, Object> map) {
		GridPage<Map<String, Object>> result = new GridPage<Map<String, Object>>();
		result.setRows(tradingMapper.selectWenYingBao(map));
		result.setTotal(tradingMapper.countWenYingBao(map));
		return result;
	}

	@Override
	public boolean cashAhead(int tId, String minSellDt) {
		Map<String, Object> propMap = new HashMap<String, Object>();
		propMap.put("minSellDt", minSellDt);
		propMap.put("tId", tId);
		propMap.put("buy_ok", AppCons.BUY_OK);

		return tradingMapper.cashAhead(propMap);
	}

	@Override
	public Trading findByPk(int id) {
		return tradingMapper.selectByPrimaryKey(id);
	}

	// @Override
	// public boolean checkTakeHeart(String key) {
	// if (redisDao.get(key) == null && redisDao.get(PayVo.takeheart +
	// "interest") == null) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@Override
	public void setTakeHeart(String key) {
		redisDao.set(key, key);
		redisDao.expire(key, ResultParame.ResultNumber.TEN.getNumber(), TimeUnit.MINUTES);
	}

	@Override
	public void delTakeHeart(String key) {
		redisDao.delete(key);
	}

	@Override
	public BigDecimal getFolatFeesRate(int tid) {
		return tradingMapper.selectTradingFloatfeesRate(tid);
	}
	
	@Override
	public List<Trading> selectPayFailList() {
		return tradingMapper.selectPayFailList();
	}

	@Override
	public void updateProductReachMoney(Integer id, Integer pId,
			BigDecimal buyMoney) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		map.put("buyMoney", buyMoney);
		productCustomerMapper.updateProductReachMoney(map);
		// tradingDao.delete(id);
		Trading t = tradingMapper.selectByPrimaryKey(id);
		t.setStatus(ResultParame.ResultNumber.EIGHTEEN.getNumber());
		tradingMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void handelBiddingLoss(String loanNo) {
		TradingExample example = new TradingExample();
		example.createCriteria().andBizCodeEqualTo(loanNo);
		List<Trading> tradingList = tradingMapper.selectByExample(example);

		if (null != tradingList && tradingList.size() > 0) {
			for (Trading trading : tradingList) {
				// 修改交易状态为9
				Trading tradingRecord = new Trading();
				tradingRecord.setId(trading.getId());
				tradingRecord.setStatus(ResultParame.ResultNumber.NINE.getNumber());
				int result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

				if (result > 0) {
					LOGGER.info("产品流标，更新交易{}状态为9成功", tradingRecord.getId());
				} else {
					LOGGER.error("产品流标，更新交易{}状态为9失败", tradingRecord.getId());
				}

				TradingFixInfo orgiTradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
				TradingFixInfo tradingFixInfoRecord = new TradingFixInfo();
				tradingFixInfoRecord.setId(orgiTradingFixInfo.getId());
				tradingFixInfoRecord.setStatus(ResultParame.ResultNumber.NINE.getNumber());
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfoRecord);

				if (result > 0) {
					LOGGER.info("产品流标，更新交易流水{}状态为9成功", tradingFixInfoRecord.getId());
				} else {
					LOGGER.error("产品流标，更新交易流水{}状态为9失败", tradingFixInfoRecord.getId());
				}

				UsersAccountExample usersAccountExmp = new UsersAccountExample();
				usersAccountExmp.createCriteria().andUIdEqualTo(trading.getuId());
				List<UsersAccount> accountList = usersAccountMapper.selectByExample(usersAccountExmp);

				// 流标后更新用户账户信息,账户流水信息。
				if (accountList != null && accountList.size() > 0) {
					UsersAccount account = accountList.get(0);

					UsersAccount accountRecord = new UsersAccount();
					accountRecord.setId(account.getId());

					// 投标成功，回复理财总额和账户余额
					if (trading.getStatus() == ResultParame.ResultNumber.FOUR.getNumber()) {
						accountRecord.setBalance(account.getBalance().add(trading.getBuyMoney()));
						accountRecord.setTotalFinance(account.getTotalFinance().subtract(trading.getBuyMoney()));
						result = usersAccountMapper.updateByPrimaryKeySelective(accountRecord);

						if (result > 0) {
							LOGGER.info("产品流标，回复用户{}账户信息成功：购买金额：{}，原账户余额：{}，回复后账户余额：{}，原理财总额：{}，回复后理财总额：{}",
									trading.getuId(), trading.getBuyMoney(), account.getBalance(),
									accountRecord.getBalance(), account.getTotalFinance(),
									accountRecord.getTotalFinance());
						} else {
							LOGGER.error("产品流标，回复用户{}账户信息失败：购买金额：{}，原账户余额：{}，回复后账户余额：{}，原理财总额：{}，回复后理财总额：{}",
									trading.getuId(), trading.getBuyMoney(), account.getBalance(),
									accountRecord.getBalance(), account.getTotalFinance(),
									accountRecord.getTotalFinance());
						}


						// 支付中，回复冻结金额，账户余额
					} else if (trading.getStatus() == ResultParame.ResultNumber.TWELVE.getNumber()) {
						accountRecord.setBalance(account.getBalance().add(trading.getBuyMoney()));
						accountRecord.setFrozenMoney(account.getFrozenMoney().subtract(trading.getBuyMoney()));
						result = usersAccountMapper.updateByPrimaryKeySelective(accountRecord);

						if (result > 0) {
							LOGGER.info("产品{}流标，回复用户{}账户信息成功：购买金额：{}，原账户余额：{}，回复后账户余额：{}，原冻结金额：{}，回复后冻结金额：{}",
									trading.getId(), trading.getuId(), trading.getBuyMoney(), account.getBalance(),
									accountRecord.getBalance(), account.getFrozenMoney(),
									accountRecord.getFrozenMoney());
						} else {
							LOGGER.error("产品{}流标，回复用户{}账户信息失败：购买金额：{}，原账户余额：{}，回复后账户余额：{}，原冻结金额：{}，回复后冻结金额：{}",
									trading.getId(), trading.getuId(), trading.getBuyMoney(), account.getBalance(),
									accountRecord.getBalance(), account.getFrozenMoney(),
									accountRecord.getFrozenMoney());
						}
					}

					// 账户流水状态变更
					AccountFlowExample accountFlowExmp = new AccountFlowExample();
					accountFlowExmp.createCriteria().andNumberEqualTo(trading.getBizCode())
							.andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber())
							.andAIdEqualTo(account.getId()).andTIdEqualTo(trading.getId());
					List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);

					if (accountFlowList != null && accountFlowList.size() > 0) {
						AccountFlow accountFlow = accountFlowList.get(0);

						AccountFlow accountFlowRecord = new AccountFlow();
						accountFlowRecord.setId(accountFlow.getId());
						accountFlowRecord.setStatus(ResultParame.ResultNumber.FOUR.getNumber());
						result = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);
						
						if (result > 0) {
							LOGGER.info("产品{}流标，更新{}号账户流水状态{}成功。", trading.getId(), accountFlow.getId(),
									ResultParame.ResultNumber.FOUR.getNumber());
						} else {
							LOGGER.error("产品{}流标，更新{}号账户流水状态{}失败。", trading.getId(), accountFlow.getId(),
									ResultParame.ResultNumber.FOUR.getNumber());
						}
					}

				}

			}
		}

	}

	@Override
	public boolean cashAheadRecountRedPacket(String uid, Integer tid) {
		TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tid);
		UsersRedPacketExample usersRedPacketExample = new UsersRedPacketExample();
		usersRedPacketExample.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(tid);

		List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExample);
		if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
			UsersRedPacket usersRedPacket = usersRedPacketList.get(0);

			HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
			if (hxRedPacket != null && hxRedPacket.getHrpType() == 1) {
				BigDecimal interest = ProductUtils.countInterest(tfi.getFinancialMoney(), tfi.getfTime(),
						new BigDecimal(hxRedPacket.getHrpNumber()));

				LOGGER.info("原加息利息：{}， 重新计算加息利息为：{}", usersRedPacket.getActualAmount(), interest);

				UsersRedPacket usersRedPacketRecord = new UsersRedPacket();
				usersRedPacketRecord.setId(usersRedPacket.getId());
				usersRedPacketRecord.setActualAmount(interest.doubleValue());

				int result = usersRedPacketMapper.updateByPrimaryKeySelective(usersRedPacketRecord);

				return result > 0 ? true : false;
			}
		}

		return true;
	}

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

        // 目前只有随心取的卖出
        boolean sellSuccess = false;
        if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {
			LOGGER.info(logGroup + "=================随心取卖出操作开始执行=================");
            sellSuccess = sellTakeHeart(trading, redeemType, moneyInput, logGroup);
            String key = AppCons.TAKE_HEART + trading.getuId();
            redisDao.delete(key);
        }

		if (sellSuccess) { // 卖出成功
			LOGGER.info("用户{}卖出 {}号理财，卖出成功", logGroup, uid, tid);
            sellResult.setFlag(0);
        } else {
			LOGGER.info("用户{}卖出 {}号理财，卖出失败", logGroup, uid, tid);
			sellResult.setCode(String.valueOf(ResultParame.ResultInfo.SUIXINQU_BUY_NOT_SUCCESS.getCode()));
            sellResult.setMsg("只能赎回随心取产品");
            sellResult.setFlag(1);
        }

        return sellResult;
    }

	/**
	 * 验证交易信息
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @param moneyInput
	 *            moneyInput
	 * @param redeemType
	 *            redeemType
	 * @param sellResult
	 *            sellResult
	 * @param trading
	 *            trading
	 * @param logGroup
	 *            logGroup
	 * @return 返回信息
	 */
    private SellResult validTradingInfo(String uid, Integer tid, Double moneyInput, Integer redeemType,
            SellResult sellResult, Trading trading, String logGroup) {

        if (trading == null) {
			LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。未查询到交易信息");

			sellResult.setCode(String.valueOf(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_TWELVE.getCode()));
			sellResult.setMsg(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_TWELVE.getMsg());
            return sellResult;
        }

        if (trading.getStatus() != AppCons.BUY_OK) {
			LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。交易状态错误");

			sellResult.setCode(String.valueOf(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_THIRTY_TWO.getCode()));
			sellResult.setMsg(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_THIRTY_TWO.getMsg());
            return sellResult;
        }

        if (!uid.equals(trading.getuId())) {
			LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财失败。用户不匹配");

			sellResult.setCode(String.valueOf(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_ELEVEN.getCode()));
			sellResult.setMsg(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_ELEVEN.getMsg());
            return sellResult;
        }

		if (redeemType == null || redeemType < ResultParame.ResultNumber.ONE.getNumber()
				|| ResultParame.ResultNumber.TWO.getNumber() < redeemType) {
			LOGGER.info("用户卖出{}号理财失败，无效赎回类型");

			sellResult.setCode(String.valueOf(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_TWENTY_TWO.getCode()));
			sellResult.setMsg(ResultParame.ResultInfo.TWENTY_THOUSAND_AND_TWENTY_TWO.getMsg());
            return sellResult;
        }

		if (redeemType == 1) { // 部分赎回
			if (StringUtils.isEmpty(moneyInput)
					|| moneyInput < ResultParame.ResultBigDecimal.ZERO_POINT_ZERO_ONE.getBigDec().doubleValue()) {
				LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出金额不合理" + moneyInput);

				sellResult.setCode(String.valueOf(ResultParame.ResultInfo.TWELVE_THOUSAND.getCode()));
				sellResult.setMsg(ResultParame.ResultInfo.TWELVE_THOUSAND.getMsg());
                return sellResult;
            }

            // 判断理财金额是否小于赎回金额
            if (trading.getFinancialMoney().doubleValue() < moneyInput) {
				LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败，理财金额小于卖出金额");

				sellResult.setCode(String.valueOf(ResultParame.ResultInfo.ELEVEN_THOUSAND.getCode()));
				sellResult.setMsg(ResultParame.ResultInfo.ELEVEN_THOUSAND.getMsg());
                return sellResult;
            }
        }

        if (AppCons.TAKE_HEART_PCODE.equals(trading.getpCode())) {

            // 判断是否禁止赎回
            if (redisDao.get("takeHeart_Allow_Sell") != null) {
				LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败。随心取禁止赎回");

                sellResult.setCode("31000");
                sellResult.setMsg(redisDao.get("takeHeart_Allow_Sell").toString());
                return sellResult;
            }

            // 判读是否可以赎回
            String key = AppCons.TAKE_HEART + uid;
            if (!checkTakeHeart(key)) {
				LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财，卖出失败。操作次数过多。");

                sellResult.setCode("21000");
                sellResult.setMsg("操作次数过多,请稍后再试");
                return sellResult;
            } else {
                setTakeHeart(key);
            }
        }

		LOGGER.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财。交易信息验证通过");

        return null;
    }

    /**
	 * 随心取卖出操作
	 * 
	 * @param trading
	 *            Trading
	 * @param redeemType
	 *            redeemType
	 * @param sellMoney
	 *            sellMoney
	 * @param logGroup
	 *            logGroup
	 * @return 数据返回
	 */
    @Transactional
    private boolean sellTakeHeart(Trading trading, Integer redeemType, Double sellMoney, String logGroup) {
        // 进行结息
        Date date = null;
        if (trading.getSellDt() == null) {
            date = DateUtils.getTradeDate(new Date(), commonMethodService.findHoliday());
        } else {
            date = DateUtils.getTradeDate(trading.getSellDt(), commonMethodService.findHoliday());
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
            interest = BigDecimalUtils.money(trading.getFinancialMoney().multiply(rate).multiply(new BigDecimal(interestDay))
					.divide(new BigDecimal(ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber()),
							ResultParame.ResultNumber.THREE.getNumber()));

            // 插入计息流水记录
            TakeheartTransactionFlow takeHeartFlow = new TakeheartTransactionFlow();
            takeHeartFlow.setuId(trading.getuId());
            takeHeartFlow.setpId(trading.getpId());
            takeHeartFlow.settId(trading.getId());
			takeHeartFlow.setType(ResultParame.ResultNumber.TWO.getNumber());
            takeHeartFlow.setState(1);
            takeHeartFlow.setOperateTime(new Date());
            takeHeartFlow.setInterest(interest);
            int saveTakeHeartFlow = takeheartTransactionFlowMapper.insertSelective(takeHeartFlow);

            if (saveTakeHeartFlow != 1) {
				LOGGER.error(logGroup + "插入随心取计息流水记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                        + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
                throw new RuntimeException("插入随心取计息流水记录错误,TakeheartTransactionFlow");
            }
			LOGGER.info(logGroup + "插入随心取计息流水记录成功 ,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                    + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

            // 插入账户流水表付息记录
            Map<String, Object> accountFlowParams = new HashMap<String, Object>();
            accountFlowParams.put("tid", trading.getId());
            accountFlowParams.put("uid", trading.getuId());
            accountFlowParams.put("interest", interest);
            int saveAccountFlow = accountFlowMapper.saveAccountFlowInterest(accountFlowParams);

            if (saveAccountFlow < 1) {
				LOGGER.error(logGroup + "插入用户账户计息流水记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId()
                        + ",buyDt:" + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
                throw new RuntimeException("插入账户流水记录错误,AccountFlow");
            }

			LOGGER.info(logGroup + "插入用户账户计息流水记录成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                    + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

            // 更新trading收益和总金额
            Trading tradingUpdateRecord = new Trading();
            tradingUpdateRecord.setId(trading.getId());
            tradingUpdateRecord.setFinancialMoney(trading.getFinancialMoney().add(interest));
            tradingUpdateRecord.setInterest(interest);
            tradingUpdateRecord.setAllInterest(trading.getAllInterest().add(interest));
            tradingUpdateRecord.setLastValueDt(date);
            int updateTrading = tradingMapper.updateByPrimaryKeySelective(tradingUpdateRecord);

            if (updateTrading < 1) {
				LOGGER.error(logGroup + "更新trading表记录错误,uid:" + trading.getuId() + ",tid:" + trading.getId()
                        + ",buyDt:" + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
                throw new RuntimeException("更新trading表错误,trading");
            }
			LOGGER.info(logGroup + "更新trading表记录成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
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
				LOGGER.error(logGroup + "更新用户账户信息错误,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                        + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());
                throw new RuntimeException("更新账户总额加上付息金额错误,UsersAccount");
            }

			LOGGER.info(logGroup + "更新用户账户信息成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                    + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

        }

        // 插入一条随心取交易流水记录 部分赎回/全部赎回共同数据
        TakeheartTransactionFlow saveTakeheartFlowRecord = new TakeheartTransactionFlow();
        saveTakeheartFlowRecord.setuId(trading.getuId());
        saveTakeheartFlowRecord.setpId(trading.getpId());
        saveTakeheartFlowRecord.settId(trading.getId());
        saveTakeheartFlowRecord.setType(1);
		saveTakeheartFlowRecord.setState(ResultParame.ResultNumber.TWO.getNumber());
        saveTakeheartFlowRecord.setOperateTime(new Timestamp(trading.getSellDt() == null ? (new Date()).getTime()
                : trading.getSellDt().getTime()));
        saveTakeheartFlowRecord.setNumber(trading.getOutBizCode());

        // 更新trading表记录 部分赎回/全部赎回共同数据
        Trading updateTradingRecordAfterFlow = new Trading();
        updateTradingRecordAfterFlow.setId(trading.getId());

		if (redeemType == ResultParame.ResultNumber.TWO.getNumber()) { // 全部卖出
            saveTakeheartFlowRecord.setMoney(trading.getFinancialMoney().add(interest));
            saveTakeheartFlowRecord.setInterest(new BigDecimal(0));
            saveTakeheartFlowRecord.setRedeemType(1);
            updateTradingRecordAfterFlow.setStatus(AppCons.SELL_STATUS);
            updateTradingRecordAfterFlow.setSellDt(new Date());
		} else { // 部分卖出
            saveTakeheartFlowRecord.setRedeemType(0);
            saveTakeheartFlowRecord.setMoney(new BigDecimal(sellMoney));
            updateTradingRecordAfterFlow.setFinancialMoney(trading.getFinancialMoney().subtract(
                    new BigDecimal(sellMoney)));
        }

		int tfId = ResultParame.ResultNumber.MINUS_ONE.getNumber(); // 操作结果标识    -1表示失败

        int ok = takeheartTransactionFlowMapper.insertSelectiveReturnKey(saveTakeheartFlowRecord);
        if (ok > 0) {
			tfId = saveTakeheartFlowRecord.getId(); // 交易流水ID
			LOGGER.info(logGroup + "保存随心取流水数据成功。 id:" + tfId);

            // 更新trading表记录
            ok = tradingMapper.updateByPrimaryKeySelective(updateTradingRecordAfterFlow);
            if (ok < 1) {
				tfId = ResultParame.ResultNumber.MINUS_ONE.getNumber();
				LOGGER.error(logGroup + "更新交易记录数据失败。 id:" + updateTradingRecordAfterFlow.toString());
            } else {
				LOGGER.info(logGroup + "更新交易记录数据成功。 id:" + updateTradingRecordAfterFlow.getId());
            }

        } else {
			tfId = ResultParame.ResultNumber.MINUS_ONE.getNumber();
			LOGGER.error(logGroup + "随心取模式产品卖出失败,保存随心取流水记录失败,uid:" + trading.getuId() + ",tid:" + trading.getId()
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
				LOGGER.error("{}保存插入随心取买卖记录成功, tfId:{}", logGroup, tfId);
            } else {
				LOGGER.error("{}保存插入随心取买卖记录失败, tfId:{}", logGroup, tfId);
            }

        } else {
			LOGGER.error(logGroup + "随心取模式产品卖出失败,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                    + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney());

            throw new RuntimeException("随心取模式产品卖出失败");
        }

		LOGGER.error(logGroup + "随心取模式产品卖出成功,uid:" + trading.getuId() + ",tid:" + trading.getId() + ",buyDt:"
                + trading.getBuyDt() + ",financialMoney:" + trading.getFinancialMoney() + "; tfId:" + tfId);

        return true;
    }

    @Override
    public void handelTimeoutTrading(Trading trading) {
        if (trading == null) {
            return;
        }

        String logGroup = "\nautoCancelOvertimeTrading_id" + trading.getId();
        try {
            Trading tradingRecord = new Trading();
            tradingRecord.setId(trading.getId());
            tradingRecord.setStatus(AppCons.BUY_CANCEL);
            int result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

            if (result > 0) {
				LOGGER.info("{}号订单已超时，更新状态成功。", trading.getId());

                TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());
                if (tfi != null) {
                    TradingFixInfo tradingFixInfoRecord = new TradingFixInfo();
                    tradingFixInfoRecord.setId(tfi.getId());
                    tradingFixInfoRecord.setStatus(AppCons.BUY_CANCEL);

                    result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfoRecord);

                    if (result > 0) {
						LOGGER.info("{}号交流流水超时，更新状态成功。", tfi.getId());
                    } else {
						LOGGER.error("{}号交流流水超时，更新状态失败。", tfi.getId());
                        throw new SQLException("更新trading_fix_info数据失败，id:" + tfi.getId());
                    }

                } else {
					LOGGER.error("为查询到交易id为{}的交易流水信息。", trading.getId());
                    throw new SQLException("更新trading数据失败，id:" + trading.getId());
                }

                // 产品已筹规模回复
                ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
                if (product != null) {
                    ProductWithBLOBs productRecord = new ProductWithBLOBs();
                    productRecord.setId(product.getId());
                    productRecord.setReachMoney(product.getReachMoney().subtract(trading.getBuyMoney()));
                    result = productMapper.updateByPrimaryKeySelective(productRecord);

                    if (result > 0) {
						LOGGER.info("{}更新产品募集金额成功。", logGroup);

                    } else {
						LOGGER.error("{}更新产品募集金额失败。", logGroup);
                        throw new SQLException("更新product数据失败，id:" + product.getId());
                    }

                } else {
					LOGGER.error("{}未查询到产品信息：", logGroup);
                }

                String uid = trading.getuId();
                // 如果使用过优惠券，恢复优惠券
                UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
                usersRedPacketExmp.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(trading.getId())
                        .andStatusEqualTo(1);

                List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
                if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
                    for (UsersRedPacket usersRedPacket : usersRedPacketList) {
                        usersRedPacket.setStatus(0);
                        usersRedPacket.settId(null);
                        usersRedPacket.setActualAmount(null);
                        usersRedPacket.setUsedTime(null);
                        result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

                        if (result > 0) {
							LOGGER.info("{}恢复优惠券信息成功。", logGroup);

                        } else {
							LOGGER.error("{}恢复优惠券信息失败。", logGroup);
                            throw new SQLException("更新user_red_packet数据失败，id:" + usersRedPacket.getId());
                        }
                    }
                }

                // 修改资金流水状态为失败2
                UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
                if (usersAccount != null) {
                    // 此业务逻辑于201803新版修改
                    // 只有华兴产品才恢复账户余额
                    //                    if (product.getpType() == ResultNumber.TWO.getNumber()) {
                    //                        // 修改用户账户表 balance↑ frozen_money↓
                    //                        usersAccount.setBalance(usersAccount.getBalance().add(trading.getBuyMoney()));
                    //                        usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
                    //                        result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
                    //                        if (result > 0) {
                    //                            LOGGER.info("{}修改用户账户表成功。", logGroup);
                    //
                    //                        } else {
                    //                            LOGGER.error("{}修改用户账户表失败。", logGroup);
                    //                            throw new SQLException("更新users_account数据失败，id:" + usersAccount.getId());
                    //                        }
                    //                    }

                    AccountFlowExample example = new AccountFlowExample();
					example.createCriteria().andTIdEqualTo(trading.getId())
							.andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber())
//							.andStatusEqualTo(ResultParame.ResultNumber.EIGHT.getNumber())
                            .andAIdEqualTo(usersAccount.getId());
					AccountFlow accountFlow = new AccountFlow();
					accountFlow.setStatus(ResultParame.ResultNumber.SIX.getNumber());
					List<AccountFlow> selectByExample = accountFlowMapper.selectByExample(example);
					if (!selectByExample.isEmpty()) {
						for (AccountFlow af : selectByExample) {
							if (af.getStatus() != ResultNumber.SIX.getNumber()) {
								result += accountFlowMapper.updateByAccountFlowId(accountFlow, af.getId());
							}
						}
					}

                    if (result > 0) {
						LOGGER.info("{}更新资金流水状态成功。", logGroup);

                    } else {
						LOGGER.error("{}更新资金流水状态失败。", logGroup);
                        throw new SQLException("更新account_flow数据失败");
                    }

                } else {
					LOGGER.error("{}未查询到该订单资金流水信息", logGroup);
                }

            } else {
				LOGGER.error("{}号订单已超时，更新状态失败。", trading.getId());
                throw new SQLException("更新trading失败,id:" + trading.getId());
            }

        } catch (Exception e) {
            // 出错直接回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
			LOGGER.error("{}执行出错，回滚数据。{}", logGroup, e.toString());
        }

    }

	@Override
	public boolean checkTakeHeart(String key) {
		// TODO Auto-generated method stub
		return false;
	}
}
