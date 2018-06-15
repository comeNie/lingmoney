package com.mrbt.lingmoney.service.trading.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.TakeheartFixRateMapper;
import com.mrbt.lingmoney.mapper.TakeheartTransactionFlowMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.TakeheartTransactionFlowExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.trading.BaseTradingService;
import com.mrbt.lingmoney.service.trading.TradingTakeheartBuyService;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
  *
  *@author yiban
  *@date 2018年1月6日 下午3:25:39
  *@version 1.0
 **/
@Service
public class TradingTakeheartBuyServiceImpl extends BaseTradingService implements TradingTakeheartBuyService {
    private static final Logger LOG = LogManager.getLogger(TradingTakeheartBuyServiceImpl.class);

    @Autowired
    private TradingMapper tradingMapper;
    @Autowired
    private TakeheartTransactionFlowMapper takeheartTransactionFlowMapper;
    @Autowired
    private BaseTradingService baseTradingService;
    @Autowired
    private TakeheartFixRateMapper takeheartFixRateMapper;
    @Autowired
    private UsersAccountMapper usersAccountMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;
    @Autowired
    private RedisDao redisDao;

    @Override
    public PageInfo buy(String pCode, String uid, BigDecimal buyMoney) {
        PageInfo pageInfo = new PageInfo();

        if (StringUtils.isEmpty(pCode) || StringUtils.isEmpty(uid) || StringUtils.isEmpty(buyMoney)) {
            pageInfo.setResultInfo(ResultInfo.PARAM_MISS);
            return pageInfo;
        }

        if (new BigDecimal("1").compareTo(buyMoney) == 1) {
            pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
            pageInfo.setMsg("购买金额有误");
            return pageInfo;
        }

        if (!AppCons.TAKE_HEART_PCODE.equals(pCode)) {
            pageInfo.setCode(ResultInfo.DO_FAIL.getCode());
            pageInfo.setMsg("产品信息有误CODE:ERROR");
            return pageInfo;
        }

        TradingExample tradingExmp = new TradingExample();
        tradingExmp.createCriteria().andPCodeEqualTo(pCode).andUIdEqualTo(uid)
                .andStatusIn(Arrays.asList(AppCons.BUY_OK, AppCons.BUY_FROKEN));
        List<Trading> buyInfoList = tradingMapper.selectByExample(tradingExmp);
        if (buyInfoList != null && buyInfoList.size() > 0) {
            TakeheartTransactionFlow flow = new TakeheartTransactionFlow();
            flow.setuId(uid);
            flow.setpId(Integer.parseInt(pCode.substring(10, pCode.length())));
            flow.settId(buyInfoList.get(0).getId());
            flow.setMoney(buyMoney);
            flow.setType(AppCons.WALLET_FLOW_TYPE_BUY);
            flow.setState(AppCons.WALLET_FLOW_STATE_PROCESS);
            flow.setOperateTime(new Date());
            flow.setNumber(baseTradingService.buildBizCode());
            takeheartTransactionFlowMapper.insertSelective(flow);
            
            pageInfo.setResultInfo(ResultInfo.SUCCESS);
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("tId", flow.getId());
            resMap.put("pCode", pCode);
            pageInfo.setObj(resMap);

        } else {
            pageInfo.setCode(ResultInfo.NO_DATA.getCode());
            pageInfo.setMsg("当前随心取只能申购");
        }

        return pageInfo;
    }

    @Transactional
    @Override
    public void handleTradingResult(Integer tid, CallbackType type) {
        String logHead = "\n随心取支付结果处理_" + System.currentTimeMillis() + "_";

        if (!StringUtils.isEmpty(tid)) {
            TakeheartTransactionFlow flow = takeheartTransactionFlowMapper.selectByPrimaryKey(tid);
            if (flow != null) {
                Trading trading = tradingMapper.selectByPrimaryKey(flow.gettId());
                UsersAccount usersAccount = usersAccountMapper.selectByUid(trading.getuId());

                if (type == CallbackType.ok) {
                    Date date = DateUtils.getTradeDate(new Date(), findHoliday());
                    TakeheartTransactionFlowExample flowExmp = new TakeheartTransactionFlowExample();
                    flowExmp.createCriteria().andIdEqualTo(tid).andStateEqualTo(2);
                    TakeheartTransactionFlow record = new TakeheartTransactionFlow();
                    record.setState(1);
                    record.setPayEndTime(new Date());

                    int result = takeheartTransactionFlowMapper.updateByExampleSelective(record, flowExmp);
                    if (result > 0) {
                        BigDecimal interest = new BigDecimal("0");
                        LOG.info("{}支付成功，更新随心取交易流水成功。id:{}", logHead, tid);

                        int interestDay = DateUtils.dateDiff(trading.getLastValueDt(), date);
                        if (interestDay > 0) { // 需要计息
                            LOG.info("{}需要计息", logHead);

                            // 获取购买产品以来的总时长，用于计算利息
                            int day = DateUtils.dateDiff(trading.getValueDt(), date);
                            Map<String, Object> paramMap = new HashMap<String, Object>();
                            paramMap.put("rDay", day);
                            paramMap.put("financialMoney", trading.getFinancialMoney());
                            Map<String, Object> fixRate = takeheartFixRateMapper.getTakeHeartFixRate(paramMap);
                            interest = ProductUtils.countInterest(trading.getFinancialMoney(), interestDay,
                                    (BigDecimal) fixRate.get("rate"));

                            // 插入计息流水记录
                            TakeheartTransactionFlow interestRecord = new TakeheartTransactionFlow();
                            interestRecord.setuId(trading.getuId());
                            interestRecord.setpId(trading.getpId());
                            interestRecord.settId(trading.getId());
                            interestRecord.setType(2);
                            interestRecord.setState(1);
                            interestRecord.setOperateTime(new Date());
                            interestRecord.setInterest(interest);
                            result = takeheartTransactionFlowMapper.insertSelective(interestRecord);
                            if (result > 0) {
                                LOG.info("{}支付成功，插入计息流水记录成功。", logHead);
                            } else {
                                LOG.error("{}支付成功，插入计息流水记录失败。", logHead, interestRecord.toString());
                            }

                            // 插入交易流水表付息记录
                            AccountFlow accountFlow = new AccountFlow();
                            accountFlow.settId(trading.getId());
                            accountFlow.setaId(usersAccount.getId());
                            accountFlow.setOperateTime(new Date());
                            accountFlow.setMoney(interest);
                            accountFlow.setStatus(AppCons.ACCOUNT_FLOW_FINISH);
                            accountFlow.setNote("随心取付息");
                            accountFlow.setType(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL);
                            accountFlow.setBalance(usersAccount.getBalance());
                            accountFlow.setFrozenMoney(usersAccount.getFrozenMoney());
                            accountFlow.setPlatform(0);
                            result = accountFlowMapper.insertSelective(accountFlow);
                            if (result > 0) {
                                LOG.info("{}支付成功，插入交易流水表付息记录成功。", logHead);
                            } else {
                                LOG.error("{}支付成功，插入交易流水表付息记录失败。", logHead, accountFlow.toString());
                            }

                            // 更新trading收益和总金额
                            Trading tradingRecord = new Trading();
                            tradingRecord.setId(trading.getId());
//                          tradingRecord.setFinancialMoney(trading.getFinancialMoney().add(interest));
                            tradingRecord.setInterest(trading.getInterest().add(interest));
                            tradingRecord.setAllInterest(trading.getAllInterest().add(interest));
                            tradingRecord.setLastValueDt(date);
							tradingRecord.setStatus(1);
                            result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);
                            if (result > 0) {
                                LOG.info("{}支付成功，更新trading收益和总金额成功。", logHead);
                            } else {
                                LOG.error("{}支付成功，更新trading收益和总金额失败。", logHead, tradingRecord.toString());
                            }

                            // 更新账户理财总额加上付息金额
                            UsersAccount usersAccountRecord = new UsersAccount();
                            usersAccountRecord.setId(usersAccount.getId());
                            usersAccountRecord.setTotalFinance(usersAccount.getTotalFinance().add(interest));
                            result = usersAccountMapper.updateByPrimaryKeySelective(usersAccountRecord);
                            if (result > 0) {
                                LOG.info("{}支付成功，更新账户理财总额加上付息金额成功。", logHead);
                            } else {
                                LOG.error("{}支付成功，更新账户理财总额加上付息金额失败。", logHead, usersAccountRecord.toString());
                            }
                        }

                        // 更新理财总额
                        Trading tradingRecord = new Trading();
                        tradingRecord.setId(trading.getId());
                        tradingRecord.setFinancialMoney(trading.getFinancialMoney().add(flow.getMoney()).add(interest));
						tradingRecord.setStatus(1);
                        result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);
                        if (result > 0) {
                            LOG.info("{}支付成功，更新理财金额成功。", logHead);
                        } else {
                            LOG.error("{}支付成功，更新理财金额失败。", logHead, tradingRecord.toString());
                        }

                        // 插入账户流水
                        AccountFlow accountFlowRecord = new AccountFlow();
                        accountFlowRecord.setStatus(AppCons.ACCOUNT_FLOW_FINISH);
                        accountFlowRecord.settId(trading.getId());
                        accountFlowRecord.setaId(usersAccount.getId());
                        accountFlowRecord.setOperateTime(new Date());
                        accountFlowRecord.setNumber(flow.getNumber());
                        accountFlowRecord.setType(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL);
                        accountFlowRecord.setBalance(usersAccount.getBalance());
                        accountFlowRecord.setFrozenMoney(usersAccount.getFrozenMoney());
                        accountFlowRecord.setMoney(flow.getMoney());
                        result = accountFlowMapper.insertSelective(accountFlowRecord);
                        if (result > 0) {
                            LOG.info("{}支付成功，插入账户流水成功。", logHead);
                        } else {
                            LOG.error("{}支付成功，插入账户流水失败。", logHead, accountFlowRecord.toString());
                        }

                        // 处理成功
                        redisDao.delete(AppCons.TAKE_HEART + trading.getuId());
                    }

                } else if (type == CallbackType.trading) {
                    TakeheartTransactionFlow flowRecord = new TakeheartTransactionFlow();
                    flowRecord.setId(flow.getId());
                    flowRecord.setState(3);
                    flowRecord.setPayEndTime(new Date());
                    int result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(flowRecord);
                    if (result > 0) {
                        LOG.info("{}支付处理中，更新随心取交易流水状态成功。", logHead);
                    } else {
                        LOG.error("{}支付处理中，更新随心取交易流水状态失败。", logHead, flowRecord.toString());
                    }

                    Trading tradingRecord = new Trading();
                    tradingRecord.setId(trading.getId());
                    tradingRecord.setStatus(AppCons.BUY_FROKEN);
                    result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);
                    if (result > 0) {
                        LOG.info("{}支付处理中，更新交易状态为冻结中成功。", logHead);
                    } else {
                        LOG.error("{}支付处理中，更新交易状态为冻结中失败。", logHead, tradingRecord.toString());
                    }

                } else if (type == CallbackType.failure) {
                    TakeheartTransactionFlow flowRecord = new TakeheartTransactionFlow();
                    flowRecord.setId(flow.getId());
                    flowRecord.setState(0);
                    flowRecord.setPayEndTime(new Date());
                    int result = takeheartTransactionFlowMapper.updateByPrimaryKeySelective(flowRecord);
                    if (result > 0) {
                        LOG.info("{}支付失败，更新随心取交易流水状态成功。", logHead);
                    } else {
                        LOG.error("{}支付失败，更新随心取交易流水状态失败。", logHead, flowRecord.toString());
                    }

                    if (flow.getState() == AppCons.WALLET_FLOW_STATE_PROCESS) {
                        AccountFlow accountFlowRecord = new AccountFlow();
                        accountFlowRecord.setStatus(AppCons.ACCOUNT_FLOW_FAIL);
                        AccountFlowExample accountExmp = new AccountFlowExample();
                        accountExmp.createCriteria().andTIdEqualTo(trading.getId()).andNumberEqualTo(flow.getNumber());
                        result = accountFlowMapper.updateByExampleSelective(accountFlowRecord, accountExmp);
                        if (result > 0) {
                            LOG.info("{}支付失败，更新账户流水成功。", logHead);
                        } else {
                            LOG.error("{}支付失败，更新账户流水失败。", logHead, accountFlowRecord.toString());
                        }
                    }

                }
            }
        }
        
    }

}
