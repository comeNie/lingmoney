package com.mrbt.lingmoney.admin.controller.task;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.admin.service.base.CommonMethodService;
import com.mrbt.lingmoney.admin.service.schedule.ScheduleService;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.CustomQueryMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.schedule.TradingObject;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * @author syb
 * @date 2017年6月26日 下午5:21:12
 * @version 1.0
 * @description 稳盈宝赎回计划任务
 **/
@Component
public class AutoWenYingBaoRedeem {
	private static final Logger LOGGER = LogManager.getLogger(AutoWenYingBaoRedeem.class);

	@Autowired
	private CustomQueryMapper customQueryMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private CommonMethodService commonMethodService;
    @Autowired
    private UsersAccountMapper usersAccountMapper;
    @Autowired
    private UsersRedPacketMapper usersRedPacketMapper;
    @Autowired
    private AccountFlowMapper accountFlowMapper;

    /**
     * 执行方法
     */
	public void handle() {
		LOGGER.info("\n自动赎回定时任务开始运行{}", DateUtils.getDtStr(new Date(), DateUtils.sft));
		scheduleService.saveScheduleLog(null, "自动赎回定时任务开始运行" + System.currentTimeMillis(), null);

        List<TradingObject> result = null;
		Map<Integer, Integer> pidMap = new HashMap<Integer, Integer>();

        while ((result = customQueryMapper.listWenYingBaoAutoRedeemTrading()) != null && result.size() > 0) {
			LOGGER.info("需要执行赎回的交易信息数量：\n{}", result.size());
			if (result != null && result.size() > 0) {
				for (int i = 0; i < result.size(); i++) {
					try {
						TradingObject tradingObject = result.get(i);

						Integer pid = tradingObject.getpId();
						if (pidMap.get(pid) == null) {
							pidMap.put(pid, pid);
						}
						int tid = tradingObject.getId();
						SellTrading sellTrading = customQueryMapper.querySellTradingInfo(tid);
						if (sellTrading != null) {
							LOGGER.info("\n赎回交易信息:\n{}", sellTrading.toString());
							if (sellTrading.getStatus() == AppCons.BUY_OK) {
								String uid = tradingObject.getuId();
								if (sellTrading.getUid().equals(uid)) {
									sellTrading.setSellDt(new Date());
									sellTrading.setStatus(AppCons.SELL_STATUS);
									if (validFix(sellTrading)) {
                                        Common.buildOutBizCode(sellTrading);
										int pcType = NumberUtils.toInt(sellTrading.getpCode().substring(ResultNumber.EIGHT.getNumber(), ResultNumber.NINE.getNumber()));
										if (pcType == AppCons.FIX_FLAG) {
											// 稳盈宝系列
											if (sellTrading.getRule() == null || sellTrading.getRule() != AppCons.RULE_NONE) {
												LOGGER.info("\n赎回信息验证通过，开始执行稳赢宝赎回。");
												TradingFixInfo info = tradingFixInfoMapper.selectFixInfoByTid(sellTrading.getTid());
												sellTrading.setSellMoney(sellTrading.getFinancialMoney().add(info.getInterest()));

                                                int dataResult = customQueryMapper.updateTradingInfoAftersellWenYing(sellTrading);

												if (dataResult > 0) {
													LOGGER.info("\n更新交易信息成功");
                                                    // 查看用户是否有加息劵
                                                    BigDecimal incomeAmtRedpacket = new BigDecimal("0"); // 加息金额
                                                    UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
                                                    usersRedPacketExmp.createCriteria().andTIdEqualTo(tid).andStatusEqualTo(1).andUIdEqualTo(uid);
                                                    List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
                                                    if (usersRedPacketList != null && usersRedPacketList.size() > 0) { 
                                                    	incomeAmtRedpacket = new BigDecimal(usersRedPacketList.get(0).getActualAmount().toString());
                                                    }

                                                    // 插入一条用户流水
                                                    UsersAccount userAcc = usersAccountMapper.selectByUid(uid);
                                                    AccountFlow accFlow = new AccountFlow();
                                                    accFlow.setaId(userAcc.getId());
                                                    accFlow.setBalance(userAcc.getBalance());
                                                    accFlow.setMoney(sellTrading.getSellMoney().add(incomeAmtRedpacket));
                                                    accFlow.setNote("赎回");
                                                    accFlow.setNumber(sellTrading.getOutBizCode());
                                                    accFlow.setOperateTime(new Date());
                                                    accFlow.setPlatform(0);
                                                    accFlow.setType(AppCons.ACCOUNT_FLOW_TYPE_REDEEM);
                                                    accFlow.setStatus(0);
                                                    accFlow.settId(sellTrading.getTid());
                                                    accountFlowMapper.insertSelective(accFlow);

												} else {
													LOGGER.error("\n稳赢宝赎回，更新交易信息失败.\n{}", sellTrading.toString());

													scheduleService.saveScheduleLog(null,
															"自动赎回失败" + System.currentTimeMillis(), "更新交易信息失败，"
																	+ sellTrading.toString());
												}

											} else {
												LOGGER.info("非稳赢宝交易信息");

												scheduleService.saveScheduleLog(null, "自动赎回失败" + System.currentTimeMillis(), "非稳赢宝交易信息，rule:"
																+ sellTrading.getRule());
											}

										} else {
											scheduleService.saveScheduleLog(null, "自动赎回失败" + System.currentTimeMillis(),
															"非固定收益模式产品,pcType:" + pcType);
										}

									}

								} else {
									LOGGER.info("用户不匹配");
									scheduleService.saveScheduleLog(null, "自动赎回失败" + System.currentTimeMillis(),
											"用户信息不匹配," + sellTrading.getUid() + ":" + uid);
								}

							} else {
								LOGGER.info("不是已经买入的产品");
							}

						} else {
							LOGGER.info("交易不存在");
						}

					} catch (Exception e) {
						LOGGER.info("自动赎回定时任务执行错误" + e);
						scheduleService.saveScheduleLog(null, "自动赎回失败,系统错误" + System.currentTimeMillis(), e.toString());
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(ResultParame.ResultNumber.FIVE_THOUSAND.getNumber());
			} catch (Exception e) {

			}
		}

		// 当所有交易的都卖出时，更新产品状态为汇款中
		if (pidMap.size() > 0) {
			for (Map.Entry<Integer, Integer> entry : pidMap.entrySet()) {
				TradingExample tradingExample = new TradingExample();
				tradingExample.createCriteria().andPIdEqualTo(entry.getKey()).andStatusEqualTo(1);
				int count = (int) tradingMapper.countByExample(tradingExample);
				if (count == 0) {
					ProductWithBLOBs productRecord = new ProductWithBLOBs();
					productRecord.setId(entry.getKey());
					productRecord.setStatus(AppCons.PRODUCT_STATUS_REPAYMENT);
					int pro = productMapper.updateByPrimaryKeySelective(productRecord);

					if (pro > 0) {
						LOGGER.info("\n自动赎回定时任务，更新产品信息成功。");
						scheduleService.saveScheduleLog(null, "自动赎回定时任务，更新产品信息成功" + System.currentTimeMillis(), null);
					} else {
						LOGGER.error("\n自动赎回定时任务，更新产品信息失败。\n{}", productRecord.toString());
						scheduleService.saveScheduleLog(null, "自动赎回定时任务，更新产品信息失败" + System.currentTimeMillis(),
								"产品信息：id" + productRecord.getId() + "; status:" + productRecord.getStatus());
					}
				}
			}
		}

		LOGGER.info("\n自动赎回定时任务执行结束{}", DateUtils.getDtStr(new Date(), DateUtils.sft));
		scheduleService.saveScheduleLog(null, "自动赎回定时任务执行结束" + System.currentTimeMillis(), null);
	}

	/**
	 * 验证交易日期是否可用
	 * @param sellTrading	sellTrading
	 * @return	return
	 */
	private boolean validFix(SellTrading sellTrading) {
		Date valueDt = DateUtils.getTradeDate(sellTrading.getSellDt(), commonMethodService.findHoliday());

		if (sellTrading.getMinSellDt() == null || sellTrading.getMinSellDt().after(valueDt)) {
			scheduleService.saveScheduleLog(null, "自动赎回失败" + System.currentTimeMillis(),
					"赎回日期错误，valueDt:" + valueDt.getTime());
			return false;
		}

		return true;
	}

}
