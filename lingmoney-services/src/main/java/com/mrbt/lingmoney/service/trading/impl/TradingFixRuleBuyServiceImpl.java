package com.mrbt.lingmoney.service.trading.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.service.bank.SingleBiddingService;
import com.mrbt.lingmoney.service.trading.BaseTradingService;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.trading.TradingFixRuleBuyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 稳赢宝购买业务
 *
 */
@Service
@Transactional
public class TradingFixRuleBuyServiceImpl extends BaseTradingService implements TradingFixRuleBuyService {
	private static final Logger LOG = LogManager.getLogger(TradingFixRuleBuyServiceImpl.class);

	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
    @Autowired
    private JDPurchaseService jdPurchaseService;
    @Autowired
    private SingleBiddingService singleBiddingService;

	/**
	 * 购买稳赢宝
	 * 
	 * @param trading
	 * @return
	 */
	@Override
	@Transactional
	public PageInfo buy(String pCode, String uid, Integer platForm, BigDecimal buyMoney, Integer minute,
			Integer userRedPacketId) {
		String logGroup = "\n产品购买" + System.currentTimeMillis() + "_";

		LOG.info("{}请求参数：{}:{}:{}:{}:{}:{}", logGroup, pCode, uid, platForm, buyMoney, minute, userRedPacketId);

		PageInfo pi = new PageInfo();
		// 自定义流水号
		String selfSeriaNumber = UUID.randomUUID().toString().replace("-", "").substring(15) + System.currentTimeMillis();

		Trading trading = initTradingInfo(pCode, uid, buyMoney);
		if (trading == null) {
            pi.setCode(ResultInfo.BUY_NOT_SUCCESS.getCode());
			pi.setMsg("购买失败");
			return pi;
		}

        // 如果是京东产品，使用自定义流水号
		Product pro = productCustomerMapper.selectByCode(pCode);
		trading.setPlatformType(platForm);
		if (pro.getpType() == 0) {
		    trading.setBizCode(selfSeriaNumber);
		}
		
		// 返回结果集
		Map<String, Object> resMap = new HashMap<String, Object>();

		// ===================购买数据库操作开始=====================================

		try {
			// 更新产品已筹集金额，此时占用产品额度
			int result = productCustomerMapper.updateProductAfterTrading(trading);
			if (result > 0) {
				LOG.info("{}更新产品信息成功。", logGroup);

			} else {
				LOG.error("{}购买失败，更新产品信息失败。", logGroup);
                throw new PageInfoException("购买失败,更新产品信息失败。", ResultInfo.SERVER_ERROR.getCode());
			}

			// 保存一条交易记录
			result = tradingMapper.insertSelectiveReturnId(trading);
			if (result > 0) {
				LOG.info("{}初始化交易记录成功。", logGroup);

			} else {
				LOG.error("{}购买失败，保存交易记录信息失败。", logGroup);
                throw new PageInfoException("购买失败，保存交易记录信息失败。", ResultInfo.SERVER_ERROR.getCode());
			}

			// 保存交易流水信息记录
			TradingFixInfo tfi = createTradingFixInfo(trading, pCode);
			result = tradingFixInfoMapper.insertSelectiveReturnId(tfi);
			if (result > 0) {
				LOG.info("{}初始化交易流水记录成功。", logGroup);

			} else {
				LOG.error("{}购买失败，保存交易流水记录信息失败。", logGroup);
                throw new PageInfoException("购买失败，保存交易流水记录信息失败。", ResultInfo.SERVER_ERROR.getCode());
			}

			// 如果有优惠券，修改优惠券信息
			if (!StringUtils.isEmpty(userRedPacketId)) {
				UsersRedPacket usersRedPacket = usersRedPacketMapper.selectByPrimaryKey(userRedPacketId);

				if (usersRedPacket != null) {
					UsersRedPacket userRedPacketRecord = new UsersRedPacket();
					userRedPacketRecord.setId(userRedPacketId);
					userRedPacketRecord.setStatus(1);
					userRedPacketRecord.settId(trading.getId());
					userRedPacketRecord.setUsedTime(new Date());

					HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
					if (hxRedPacket != null) {
						Integer hrpType = hxRedPacket.getHrpType();
						// 红包 直接取赠送金额
						if (hrpType == 2) {
							userRedPacketRecord.setActualAmount(0.00);
						} else if (hrpType == 1) {
							// 加息券 计算利息
							Product product = productCustomerMapper.selectByCode(pCode);
							BigDecimal interest = ProductUtils.countInterest(buyMoney,
									ProductUtils.getFinancialDays(product), new BigDecimal(hxRedPacket.getHrpNumber()));
							userRedPacketRecord.setActualAmount(interest.doubleValue());
						}
						resMap.put("hrpType", hrpType);
						resMap.put("hrpNumber", hxRedPacket.getHrpNumber());
						resMap.put("actualAmount", userRedPacketRecord.getActualAmount());
					}

					result = usersRedPacketMapper.updateByPrimaryKeySelective(userRedPacketRecord);

					if (result > 0) {
						LOG.info("{}使用优惠券成功，更新用户优惠券信息成功。", logGroup);

					} else {
						LOG.error("{}使用优惠券失败，更新用户优惠券信息失败.", logGroup);
                        throw new PageInfoException("使用优惠券失败，更新用户优惠券信息失败.", ResultInfo.SERVER_ERROR.getCode());
					}

				}
			}

			UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            // 添加账户流水表 status = 0
            AccountFlow accountFlow = new AccountFlow();
            // 只有华兴产品才修改用户余额
            if (pro.getpType() == 2) {
                // 修改用户账户表 balance↓ frozen_money↑
                usersAccount.setBalance(usersAccount.getBalance().subtract(trading.getBuyMoney()));
                usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().add(trading.getBuyMoney()));
                result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);

                if (result > 0) {
                    LOG.info("{}更新用户账户金额成功。", logGroup);

                } else {
                    LOG.error("{}更新用户账户金额失败。", logGroup);
                    throw new PageInfoException("更新账户金额失败", ResultInfo.SERVER_ERROR.getCode());
                }

			} else {
                accountFlow.setNumber(selfSeriaNumber);
			}

            accountFlow.settId(trading.getId());
            accountFlow.setaId(usersAccount.getId());
            accountFlow.setOperateTime(new Date());
            accountFlow.setType(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL);
            accountFlow.setStatus(AppCons.ACCOUNT_FLOW_WAITING_PAYMENT); // 待支付
            accountFlow.setBalance(usersAccount.getBalance());
            accountFlow.setFrozenMoney(trading.getBuyMoney());
            accountFlow.setMoney(trading.getBuyMoney());
            result = accountFlowMapper.insertSelective(accountFlow);

            if (result > 0) {
                LOG.info("{}保存用户账户流水成功。", logGroup);

            } else {
                LOG.error("{}保存用户账户流水失败。", logGroup);
                throw new PageInfoException("更新账户流水失败", ResultInfo.SERVER_ERROR.getCode());
            }

		} catch (Exception e) {
			e.printStackTrace();
			// 失败回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("购买失败，系统错误");
			return pi;
		}

		// ===================购买数据库操作结束=====================================

		LOG.info("{}购买成功，购买操作处理完成", logGroup);

		// 支付剩余时间（毫秒）购买时间 + 15分钟-当前时间
		long remainDt = trading.getBuyDt().getTime() + minute * 60 * 1000 - System.currentTimeMillis();
		remainDt = remainDt < 0 ? 0 : remainDt;
		// 距离产品结束时间
		long remainEdDt = productCustomerMapper.selectByCode(pCode).getEdDt().getTime() - System.currentTimeMillis();
		remainEdDt = remainEdDt < 0 ? 0 : remainEdDt;

		resMap.put("tId", trading.getId());
		resMap.put("remainDt", remainDt < remainEdDt ? remainDt : remainEdDt);
		resMap.put("buyMoney", buyMoney);
        resMap.put("pCode", trading.getpCode());
		pi.setObj(resMap);
        pi.setCode(ResultInfo.SUCCESS.getCode());
		pi.setMsg("操作成功");

		return pi;
	}

	@Transactional
	@Override
    public boolean handleBuyProduct(Integer tId, String bizCode, String uId, CallbackType flag) {
		String logGroup = "\n购买产品，支付处理" + System.currentTimeMillis() + "_";
		try {
			Trading trading = tradingMapper.selectByPrimaryKey(tId);
            TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
			UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
			if (trading == null || tradingFixInfo == null || usersAccount == null) {
				LOG.info("交易不存在");
				return false;
			}

			ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
			LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{}", uId, product.getCode(), trading.getFinancialMoney(),
					trading.getId());

            if (flag == CallbackType.trading) { // 支付中（处理中）
				LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付中", uId, product.getCode(), trading.getFinancialMoney(),
						trading.getId());
				
				// 更新交易表和更新交易信息表状态为支付中，修改订单号为请求流水号
				trading.setStatus(AppCons.BUY_TRADING);
				trading.setBizCode(bizCode);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOG.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易表状态失败。", logGroup);
				}
				
				tradingFixInfo.setStatus(AppCons.BUY_TRADING);
				tradingFixInfo.setBizCode(bizCode);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOG.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易信息表状态失败。", logGroup);
				}
				
				// 修改资金流水订单号
				AccountFlowExample example = new AccountFlowExample();
                example.createCriteria().andTIdEqualTo(tId).andTypeEqualTo(2).andStatusEqualTo(8)
						.andAIdEqualTo(usersAccount.getId());
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setNumber(bizCode);
                accountFlow.setStatus(7); // 支付中
				result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
				if (result > 0) {
					LOG.info("{}修改资金流水订单号成功。", logGroup);
				} else {
					LOG.error("{}修改资金流水订单号失败。", logGroup);
				}
				
            } else if (flag == CallbackType.failure) { // 失败
				LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付失败", uId, product.getCode(), trading.getFinancialMoney(),
						trading.getId());
				// 更新交易表和更新交易信息表 status = 5
				trading.setStatus(AppCons.BUY_FAIL);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOG.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易表状态失败。", logGroup);
				}
				
				tradingFixInfo.setStatus(AppCons.BUY_FAIL);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOG.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易信息表状态失败。", logGroup);
				}

				// 更新产品表，回复reach_money
				if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE) {
					BigDecimal nowReach = product.getReachMoney().subtract(trading.getFinancialMoney());
					product.setReachMoney(nowReach.doubleValue() <= 0 ? new BigDecimal(0) : nowReach);
					result = productMapper.updateByPrimaryKeySelective(product);
					if (result > 0) {
						LOG.info("{}更新产品表成功。", logGroup);
					} else {
						LOG.error("{}更新产品表失败。", logGroup);
					}
				}

				// 修改账户流水表状态为失败
				AccountFlowExample example = new AccountFlowExample();
                example.createCriteria().andTypeEqualTo(2).andNumberEqualTo(bizCode);
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setStatus(2);
				result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
				if (result > 0) {
					LOG.info("{}修改资金流水状态成功。", logGroup);
				} else {
					LOG.error("{}修改资金流水状态失败。", logGroup);
				}

                if (product.getpType() == 2) {
                    // 修改用户账户表 balance↑ frozen_money↓
                    usersAccount.setBalance(usersAccount.getBalance().add(trading.getBuyMoney()));
                    usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
                    result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
                    if (result > 0) {
                        LOG.info("{}修改用户账户表成功。", logGroup);
                    } else {
                        LOG.error("{}修改用户账户表失败。", logGroup);
                    }
				}

				// 如果使用过优惠券，恢复优惠券
				UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
				usersRedPacketExmp.createCriteria().andUIdEqualTo(uId).andTIdEqualTo(tId).andStatusEqualTo(1);

				List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
				if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
					for (UsersRedPacket usersRedPacket : usersRedPacketList) {
						usersRedPacket.setStatus(0);
						usersRedPacket.settId(null);
						usersRedPacket.setActualAmount(null);
						usersRedPacket.setUsedTime(null);
						result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

						if (result > 0) {
							LOG.info("{}恢复优惠券信息成功。", logGroup);
						} else {
							LOG.error("{}恢复优惠券信息失败。", logGroup);
						}
					}
				}

            } else if (flag == CallbackType.Frozen) { // 回调成功，状态先改为冻结中（购买成功），如果产品成立，所有交易改为成功。
				LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为冻结中", uId, product.getCode(), trading.getFinancialMoney(),
						trading.getId());
                // 更新交易表和更新交易信息表 status 
				trading.setStatus(AppCons.BUY_FROKEN);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOG.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易表状态失败。", logGroup);
				}

				tradingFixInfo.setStatus(AppCons.BUY_FROKEN);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOG.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOG.error("{}更新交易信息表状态失败。", logGroup);
				}

				// 如果最后一笔交易成功后，更新产品为募集满状态，并更新详细交易表中的结息日和状态 如果募集满，则查找最后一条的交易信息
				if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE
						&& product.getPriorMoney().compareTo(product.getReachMoney()) == 0) {
					// 查找最后一次交易信息
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("priorMoney", product.getPriorMoney());
					map.put("pId", trading.getpId());
					map.put("status", AppCons.BUY_FROKEN);
					Trading lastTrading = productCustomerMapper.selectLastTrading(map);
					if (lastTrading != null) {
						LOG.info("\n用户:{},最后一笔交易id:{},成功后,产品:{}募集满,等待放款", lastTrading.getuId(), lastTrading.getId(),
								lastTrading.getpCode());
						// 更新产品为募集满状态
						product.setStatus(AppCons.PRODUCT_STATUS_COLLECT_SUCCESS);
						result = productMapper.updateByPrimaryKeySelective(product);
						if (result > 0) {
							LOG.info("{}更新产品表成功。", logGroup);
						} else {
							LOG.error("{}更新产品表失败。", logGroup);
						}

					}
				}

				// 修改账户流水表 status = 5（冻结中）
				AccountFlowExample accountFlowExample = new AccountFlowExample();
                accountFlowExample.createCriteria().andNumberEqualTo(bizCode).andTypeEqualTo(2);
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setFrozenMoney(trading.getBuyMoney());
				accountFlow.setStatus(5);
				accountFlowMapper.updateByExampleSelective(accountFlow, accountFlowExample);

            } else if (flag == CallbackType.ok && product.getpType() == 0) { // 京东支付成功
                LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为成功", uId, product.getCode(), trading.getFinancialMoney(),
                        trading.getId());
                // 更新交易表和更新交易信息表 status 
                trading.setStatus(AppCons.BUY_OK);
                int result = tradingMapper.updateByPrimaryKeySelective(trading);
                if (result > 0) {
                    LOG.info("{}更新交易表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易表状态失败。", logGroup);
                }

                tradingFixInfo.setStatus(AppCons.BUY_OK);
                result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
                if (result > 0) {
                    LOG.info("{}更新交易信息表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易信息表状态失败。", logGroup);
                }

                // 如果最后一笔交易成功后，更新产品为募集满状态，并更新详细交易表中的结息日和状态 如果募集满，则查找最后一条的交易信息
                if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE
                        && product.getPriorMoney().compareTo(product.getReachMoney()) == 0) {
                    // 查找最后一次交易信息
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("priorMoney", product.getPriorMoney());
                    map.put("pId", trading.getpId());
                    map.put("status", AppCons.BUY_OK);
                    Trading lastTrading = productCustomerMapper.selectLastTrading(map);
                    if (lastTrading != null) {
                        LOG.info("\n用户:{},最后一笔交易id:{},成功后,产品:{}募集满,等待放款", lastTrading.getuId(), lastTrading.getId(),
                                lastTrading.getpCode());

                        // 添加最小赎回的天数
                        lastTrading.setValueDt(lastTrading.getwValueDt());
                        if (product.getUnitTime() == AppCons.UNIT_TIME_DAY) {
                            lastTrading.setMinSellDt(DateUtils.addDay(lastTrading.getValueDt(), product.getfTime()));

                        } else if (product.getUnitTime() == AppCons.UNIT_TIME_MONTH) {
                            lastTrading.setMinSellDt(DateUtils.addMonth(lastTrading.getValueDt(), product.getfTime()));

                        } else if (product.getUnitTime() == AppCons.UNIT_TIME_WEEK) {
                            lastTrading.setMinSellDt(DateUtils.addWeek(lastTrading.getValueDt(), product.getfTime()));
                        }

                        if (!updateProductByRun(lastTrading, false)) {
                            LOG.error("产品募集满，更新交易信息失败", logGroup);
                        }
                    }
                }

                // 修改账户流水表 status = 5（冻结中）
                AccountFlowExample accountFlowExample = new AccountFlowExample();
                accountFlowExample.createCriteria().andNumberEqualTo(bizCode).andTypeEqualTo(2);
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setFrozenMoney(trading.getBuyMoney());
                accountFlow.setStatus(AppCons.ACCOUNT_FLOW_FINISH);
                accountFlowMapper.updateByExampleSelective(accountFlow, accountFlowExample);

                singleBiddingService.buyWeiyingbaoGetLingbao(uId, trading.getId());
            }

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("交易失败");
			return false;
		}

	}

	@Override
	public boolean updateProductByRun(final Trading lastTrading, final boolean manual) {

		// 更新交易表
		Map<String, Object> map2 = new HashMap<String, Object>();
		// 产品状态 改为运行中
		map2.put("param1", AppCons.PRODUCT_STATUS_RUNNING);
		// 设置产品计息日
		map2.put("param2", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置产品成立日x,如果manual为真，手动成立，计息日（节假日顺延）为成立时间，如果为假，取最后一次交易的时间为成立时间
		map2.put("param3",
				new Timestamp(manual ? lastTrading.getValueDt().getTime() : lastTrading.getBuyDt().getTime()));
		// 设置交易表状态为成功
        map2.put("param4", AppCons.BUY_OK);
		// 设置交易信息表状态为成功
        map2.put("param5", AppCons.BUY_OK);
		// 设置交易表中的计息日
		map2.put("param6", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置交易表中可赎回日期
		map2.put("param7", new Timestamp(lastTrading.getMinSellDt().getTime()));
		// 设置固定类交易信息表中的结息日
		map2.put("param8", new Timestamp(lastTrading.getMinSellDt().getTime()));
		// 设置固定类交易信息表中的等待时长
		map2.put("param9", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置利息的时长
		map2.put("param10", DateUtils.dateDiff(lastTrading.getValueDt(), lastTrading.getMinSellDt()));
		// 设置利息计算中的等待时长
		map2.put("param11", new Timestamp(lastTrading.getValueDt().getTime()));
		// 设置产品id
		map2.put("param12", lastTrading.getpId());
		// 查询状态为筹集中的产品
		map2.put("param13", AppCons.PRODUCT_STATUS_READY);
		// 查询交易状态为冻结中（购买成功，但是未成立）
        map2.put("param14", AppCons.BUY_OK);
		int ok = productCustomerMapper.updateProductByRun(map2);
		return ok > 0 ? true : false;
	}

    /**
     * 回调类型枚举
     *
     */
	public enum CallbackType {

		Frozen("CALLBACK_FROZEN"), ok("CALLBACK_OK"), failure("CALLBACK_FAILURE"), trading("TRADING_CONDU");
		String value;

		CallbackType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * 初始化交易实体类信息
	 * 
	 * @param pCode
	 *            产品code
	 * @param uid
	 *            用户id
	 * @param buyMoney
	 *            购买金额
	 * @return trading
	 */
	private Trading initTradingInfo(String pCode, String uid, BigDecimal buyMoney) {
		Trading trading = new Trading();

		Product product = productCustomerMapper.selectByCode(pCode);
		if (product != null) {
			String pcId = null, pcType = null, pId = null;
			pcId = pCode.substring(2, 6);
			pcType = pCode.substring(8, 9);
			pId = pCode.substring(pCode.length() - 4);

			trading.setpId(NumberUtils.toInt(pId));
			trading.setPcType(NumberUtils.toInt(pcType));
			trading.setPcId(NumberUtils.toInt(pcId));
			// 原订单号，换成华兴银行后保存投标交易流水号
			// trading.setBizCode(buildBizCode());
			trading.setuId(uid);
			trading.setpCode(pCode);
			trading.setFixInvest(0); // 是否定投
			trading.setAutoPay(AppCons.AUTO_PAY_FALSE); // 是否自动扣款
			trading.setBuyDt(new Date());
			trading.setBuyMoney(buyMoney);
			trading.setFinancialMoney(buyMoney);
			trading.setStatus(AppCons.BUY_STATUS);
			trading.setwValueDt(DateUtils.getTradeDate(trading.getBuyDt(), findHoliday()));

			boolean b = rule(product, trading);
			if (b) {
				return trading;
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * 初始化固定交易实体信息
	 * 
	 * @param trading 交易信息
	 * @param pCode 产品code
	 * @return tradingfixinfo
	 */
	private TradingFixInfo createTradingFixInfo(Trading trading, String pCode) {
		Product product = productCustomerMapper.selectByCode(pCode);

		TradingFixInfo tfi = new TradingFixInfo();
		tfi.settId(trading.getId());
		tfi.setBuyDt(trading.getBuyDt());
		tfi.setFinancialMoney(trading.getFinancialMoney());
		tfi.setValueDt(DateUtils.getTradeDate(trading.getBuyDt(), findHoliday()));
		tfi.setStatus(AppCons.BUY_STATUS);
		if (!StringUtils.isEmpty(trading.getBizCode())) {
		    tfi.setBizCode(trading.getBizCode());
		}
		tfi.setInterestRate(product.getfYield());
		tfi.setfTime(product.getfTime());

		return tfi;
	}

	/**
	 * 规则的验证
	 * 
	 * @param product 产品信息
	 * @param trading 交易信息
	 * @return true 验证通过   false 验证失败
	 */
	private boolean rule(Product product, Trading trading) {
		// 对于日期的验证
		if (product.getRule() == AppCons.RULE_DATE || product.getRule() == AppCons.RULE_MONEY_DATE) {
			if (product.getStDt() == null || product.getEdDt() == null) {
				LOG.info("带日期限制的规则，开始时间和结束时间不能为空");
				return false;
			}
			if (trading.getBuyDt().getTime() > product.getEdDt().getTime()
					|| trading.getBuyDt().getTime() < product.getStDt().getTime()) {
				LOG.info("购买时间超出募集时间");
				return false;
			}
		}

		// 金额的限制
		if (product.getRule() == AppCons.RULE_MONEY || product.getRule() == AppCons.RULE_MONEY_DATE) {
			if (product.getPriorMoney() == null || product.getPriorMoney().intValue() <= 0
					|| product.getReachMoney() == null
					|| product.getReachMoney().compareTo(product.getPriorMoney()) == 1) {
				LOG.info("带金额限制的规则，筹备金额不能<=0,以筹金额不能>筹备金额");
				return false;
			}

			// 如果筹备金额大于限制金额，则返回错误
			if (product.getReachMoney().add(trading.getBuyMoney()).compareTo(product.getPriorMoney()) == 1) {
				LOG.info("购买金额超出募集金额");
				return false;
			}
		}

		return true;
	}

    @Transactional
    @Override
    public boolean handleBuyProductVersionOne(Integer tId, String bizCode, String uId, CallbackType flag) {
        String logGroup = "\n购买产品，支付处理" + System.currentTimeMillis() + "_";
        try {
            Trading trading = tradingMapper.selectByPrimaryKey(tId);
            TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
            UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
            if (trading == null || tradingFixInfo == null || usersAccount == null) {
                LOG.info("交易不存在");
                return false;
            }

            ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
            LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{}", uId, product.getCode(), trading.getFinancialMoney(),
                    trading.getId());

            if (flag == CallbackType.trading) { // 支付中（处理中）
                LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付中", uId, product.getCode(), trading.getFinancialMoney(),
                        trading.getId());

                // 更新交易表和更新交易信息表状态为支付中，修改订单号为请求流水号
                trading.setStatus(AppCons.BUY_TRADING);
                trading.setBizCode(bizCode);
                int result = tradingMapper.updateByPrimaryKeySelective(trading);
                if (result > 0) {
                    LOG.info("{}更新交易表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易表状态失败。", logGroup);
                }

                tradingFixInfo.setStatus(AppCons.BUY_TRADING);
                tradingFixInfo.setBizCode(bizCode);
                result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
                if (result > 0) {
                    LOG.info("{}更新交易信息表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易信息表状态失败。", logGroup);
                }

                // 华兴产品修改用户余额，冻结金额 
                if (product.getpType() == 2) {
                    UsersAccount usersAccountRecord = new UsersAccount();
                    usersAccountRecord.setId(usersAccount.getId());
                    usersAccountRecord.setBalance(usersAccount.getBalance().subtract(trading.getBuyMoney()));
                    usersAccountRecord.setFrozenMoney(usersAccount.getFrozenMoney().add(trading.getBuyMoney()));
                    result = usersAccountMapper.updateByPrimaryKeySelective(usersAccountRecord);
                    if (result > 0) {
                        LOG.info("{}修改用户余额，冻结金额成功。", logGroup);
                    } else {
                        LOG.error("{}修改用户余额，冻结金额失败。", logGroup);
                    }
                }

                // 修改资金流水订单号
                AccountFlowExample example = new AccountFlowExample();
                example.createCriteria().andTIdEqualTo(tId).andTypeEqualTo(2).andStatusEqualTo(8)
                        .andAIdEqualTo(usersAccount.getId());
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setNumber(bizCode);
                accountFlow.setStatus(7); // 支付中
                result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
                if (result > 0) {
                    LOG.info("{}修改资金流水订单号成功。", logGroup);
                } else {
                    LOG.error("{}修改资金流水订单号失败。", logGroup);
                }

            } else if (flag == CallbackType.failure) { // 失败
                LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付失败", uId, product.getCode(), trading.getFinancialMoney(),
                        trading.getId());
                // 更新交易表和更新交易信息表 status = 5
                trading.setStatus(AppCons.BUY_FAIL);
                int result = tradingMapper.updateByPrimaryKeySelective(trading);
                if (result > 0) {
                    LOG.info("{}更新交易表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易表状态失败。", logGroup);
                }

                tradingFixInfo.setStatus(AppCons.BUY_FAIL);
                result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
                if (result > 0) {
                    LOG.info("{}更新交易信息表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易信息表状态失败。", logGroup);
                }

                // 更新产品表，回复reach_money
                if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE) {
                    BigDecimal nowReach = product.getReachMoney().subtract(trading.getFinancialMoney());
                    product.setReachMoney(nowReach.doubleValue() <= 0 ? new BigDecimal(0) : nowReach);
                    result = productMapper.updateByPrimaryKeySelective(product);
                    if (result > 0) {
                        LOG.info("{}更新产品表成功。", logGroup);
                    } else {
                        LOG.error("{}更新产品表失败。", logGroup);
                    }
                }

                // 修改账户流水表状态为失败
                AccountFlowExample example = new AccountFlowExample();
                example.createCriteria().andTypeEqualTo(2).andNumberEqualTo(bizCode);
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setStatus(2);
                result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
                if (result > 0) {
                    LOG.info("{}修改资金流水状态成功。", logGroup);
                } else {
                    LOG.error("{}修改资金流水状态失败。", logGroup);
                }

                if (product.getpType() == 2) {
                    // 修改用户账户表 balance↑ frozen_money↓
                    usersAccount.setBalance(usersAccount.getBalance().add(trading.getBuyMoney()));
                    usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
                    result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
                    if (result > 0) {
                        LOG.info("{}修改用户账户表成功。", logGroup);
                    } else {
                        LOG.error("{}修改用户账户表失败。", logGroup);
                    }
                }

                // 如果使用过优惠券，恢复优惠券
                UsersRedPacketExample usersRedPacketExmp = new UsersRedPacketExample();
                usersRedPacketExmp.createCriteria().andUIdEqualTo(uId).andTIdEqualTo(tId).andStatusEqualTo(1);

                List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(usersRedPacketExmp);
                if (usersRedPacketList != null && usersRedPacketList.size() > 0) {
                    for (UsersRedPacket usersRedPacket : usersRedPacketList) {
                        usersRedPacket.setStatus(0);
                        usersRedPacket.settId(null);
                        usersRedPacket.setActualAmount(null);
                        usersRedPacket.setUsedTime(null);
                        result = usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);

                        if (result > 0) {
                            LOG.info("{}恢复优惠券信息成功。", logGroup);
                        } else {
                            LOG.error("{}恢复优惠券信息失败。", logGroup);
                        }
                    }
                }

            } else if (flag == CallbackType.Frozen) { // 回调成功，状态先改为冻结中（购买成功），如果产品成立，所有交易改为成功。
                LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为冻结中", uId, product.getCode(), trading.getFinancialMoney(),
                        trading.getId());
                // 更新交易表和更新交易信息表 status 
                trading.setStatus(AppCons.BUY_FROKEN);
                int result = tradingMapper.updateByPrimaryKeySelective(trading);
                if (result > 0) {
                    LOG.info("{}更新交易表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易表状态失败。", logGroup);
                }

                tradingFixInfo.setStatus(AppCons.BUY_FROKEN);
                result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
                if (result > 0) {
                    LOG.info("{}更新交易信息表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易信息表状态失败。", logGroup);
                }

                // 如果最后一笔交易成功后，更新产品为募集满状态，并更新详细交易表中的结息日和状态 如果募集满，则查找最后一条的交易信息
                if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE
                        && product.getPriorMoney().compareTo(product.getReachMoney()) == 0) {
                    // 查找最后一次交易信息
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("priorMoney", product.getPriorMoney());
                    map.put("pId", trading.getpId());
                    map.put("status", AppCons.BUY_FROKEN);
                    Trading lastTrading = productCustomerMapper.selectLastTrading(map);
                    if (lastTrading != null) {
                        LOG.info("\n用户:{},最后一笔交易id:{},成功后,产品:{}募集满,等待放款", lastTrading.getuId(), lastTrading.getId(),
                                lastTrading.getpCode());
                        // 更新产品为募集满状态
                        product.setStatus(AppCons.PRODUCT_STATUS_COLLECT_SUCCESS);
                        result = productMapper.updateByPrimaryKeySelective(product);
                        if (result > 0) {
                            LOG.info("{}更新产品表成功。", logGroup);
                        } else {
                            LOG.error("{}更新产品表失败。", logGroup);
                        }

                    }
                }

                // 修改账户流水表 status = 5（冻结中）
                AccountFlowExample accountFlowExample = new AccountFlowExample();
                accountFlowExample.createCriteria().andNumberEqualTo(bizCode).andTypeEqualTo(2);
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setFrozenMoney(trading.getBuyMoney());
                accountFlow.setStatus(5);
                accountFlowMapper.updateByExampleSelective(accountFlow, accountFlowExample);

            } else if (flag == CallbackType.ok && product.getpType() == 0) { // 京东支付成功
                LOG.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为成功", uId, product.getCode(), trading.getFinancialMoney(),
                        trading.getId());
                // 更新交易表和更新交易信息表 status 
                trading.setStatus(AppCons.BUY_OK);
                int result = tradingMapper.updateByPrimaryKeySelective(trading);
                if (result > 0) {
                    LOG.info("{}更新交易表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易表状态失败。", logGroup);
                }

                tradingFixInfo.setStatus(AppCons.BUY_OK);
                result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
                if (result > 0) {
                    LOG.info("{}更新交易信息表状态成功。", logGroup);
                } else {
                    LOG.error("{}更新交易信息表状态失败。", logGroup);
                }

                // 如果最后一笔交易成功后，更新产品为募集满状态，并更新详细交易表中的结息日和状态 如果募集满，则查找最后一条的交易信息
                if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE
                        && product.getPriorMoney().compareTo(product.getReachMoney()) == 0) {
                    // 查找最后一次交易信息
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("priorMoney", product.getPriorMoney());
                    map.put("pId", trading.getpId());
                    map.put("status", AppCons.BUY_OK);
                    Trading lastTrading = productCustomerMapper.selectLastTrading(map);
                    if (lastTrading != null) {
                        LOG.info("\n用户:{},最后一笔交易id:{},成功后,产品:{}募集满,等待放款", lastTrading.getuId(), lastTrading.getId(),
                                lastTrading.getpCode());

                        // 添加最小赎回的天数
                        lastTrading.setValueDt(lastTrading.getwValueDt());
                        if (product.getUnitTime() == AppCons.UNIT_TIME_DAY) {
                            lastTrading.setMinSellDt(DateUtils.addDay(lastTrading.getValueDt(), product.getfTime()));

                        } else if (product.getUnitTime() == AppCons.UNIT_TIME_MONTH) {
                            lastTrading.setMinSellDt(DateUtils.addMonth(lastTrading.getValueDt(), product.getfTime()));

                        } else if (product.getUnitTime() == AppCons.UNIT_TIME_WEEK) {
                            lastTrading.setMinSellDt(DateUtils.addWeek(lastTrading.getValueDt(), product.getfTime()));
                        }

                        if (!updateProductByRun(lastTrading, false)) {
                            LOG.error("产品募集满，更新交易信息失败", logGroup);
                        }
                    }
                }

                // 修改账户流水表 status = 5（冻结中）
                AccountFlowExample accountFlowExample = new AccountFlowExample();
                accountFlowExample.createCriteria().andNumberEqualTo(bizCode).andTypeEqualTo(2);
                AccountFlow accountFlow = new AccountFlow();
                accountFlow.setFrozenMoney(trading.getBuyMoney());
                accountFlow.setStatus(AppCons.ACCOUNT_FLOW_FINISH);
                accountFlowMapper.updateByExampleSelective(accountFlow, accountFlowExample);

                singleBiddingService.buyWeiyingbaoGetLingbao(uId, trading.getId());
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("交易失败");
            return false;
        }
    }

    @Transactional
    @Override
    public PageInfo buyVersionOne(String pCode, String uid, int platForm, BigDecimal buyMoney, Integer minute,
            Integer userRedPacketId) {
        String logGroup = "\n新版产品购买" + System.currentTimeMillis() + "_";

        LOG.info("{}请求参数：{}:{}:{}:{}:{}:{}", logGroup, pCode, uid, platForm, buyMoney, minute, userRedPacketId);

        PageInfo pi = new PageInfo();
        // 自定义流水号
        String selfSeriaNumber = UUID.randomUUID().toString().replace("-", "").substring(15)
                + System.currentTimeMillis();

        Trading trading = initTradingInfo(pCode, uid, buyMoney);
        if (trading == null) {
            pi.setCode(ResultInfo.BUY_NOT_SUCCESS.getCode());
            pi.setMsg("购买失败");
            return pi;
        }

        // 如果是京东产品，使用自定义流水号
        Product pro = productCustomerMapper.selectByCode(pCode);
        trading.setPlatformType(platForm);
        if (pro.getpType() == 0) {
            trading.setBizCode(selfSeriaNumber);
        }

        // 返回结果集
        Map<String, Object> resMap = new HashMap<String, Object>();

        // ===================购买数据库操作开始=====================================

        try {
            // 更新产品已筹集金额，此时占用产品额度
            int result = productCustomerMapper.updateProductAfterTrading(trading);
            if (result > 0) {
                LOG.info("{}更新产品信息成功。", logGroup);

            } else {
                LOG.error("{}购买失败，更新产品信息失败。", logGroup);
                throw new PageInfoException("购买失败,更新产品信息失败。", ResultInfo.SERVER_ERROR.getCode());
            }

            // 保存一条交易记录
            result = tradingMapper.insertSelectiveReturnId(trading);
            if (result > 0) {
                LOG.info("{}初始化交易记录成功。", logGroup);

            } else {
                LOG.error("{}购买失败，保存交易记录信息失败。", logGroup);
                throw new PageInfoException("购买失败，保存交易记录信息失败。", ResultInfo.SERVER_ERROR.getCode());
            }

            // 保存交易流水信息记录
            TradingFixInfo tfi = createTradingFixInfo(trading, pCode);
            result = tradingFixInfoMapper.insertSelectiveReturnId(tfi);
            if (result > 0) {
                LOG.info("{}初始化交易流水记录成功。", logGroup);

            } else {
                LOG.error("{}购买失败，保存交易流水记录信息失败。", logGroup);
                throw new PageInfoException("购买失败，保存交易流水记录信息失败。", ResultInfo.SERVER_ERROR.getCode());
            }

            // 如果有优惠券，修改优惠券信息
            if (!StringUtils.isEmpty(userRedPacketId)) {
                UsersRedPacket usersRedPacket = usersRedPacketMapper.selectByPrimaryKey(userRedPacketId);

                if (usersRedPacket != null) {
                    UsersRedPacket userRedPacketRecord = new UsersRedPacket();
                    userRedPacketRecord.setId(userRedPacketId);
                    userRedPacketRecord.setStatus(1);
                    userRedPacketRecord.settId(trading.getId());
                    userRedPacketRecord.setUsedTime(new Date());

                    HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
                    if (hxRedPacket != null) {
                        Integer hrpType = hxRedPacket.getHrpType();
                        // 红包 直接取赠送金额
                        if (hrpType == 2) {
                            userRedPacketRecord.setActualAmount(0.00);
                        } else if (hrpType == 1) {
                            // 加息券 计算利息
                            Product product = productCustomerMapper.selectByCode(pCode);
                            BigDecimal interest = ProductUtils.countInterest(buyMoney,
                                    ProductUtils.getFinancialDays(product), new BigDecimal(hxRedPacket.getHrpNumber()));
                            userRedPacketRecord.setActualAmount(interest.doubleValue());
                        }
                        resMap.put("hrpType", hrpType);
                        resMap.put("hrpNumber", hxRedPacket.getHrpNumber());
                        resMap.put("actualAmount", userRedPacketRecord.getActualAmount());
                    }

                    result = usersRedPacketMapper.updateByPrimaryKeySelective(userRedPacketRecord);

                    if (result > 0) {
                        LOG.info("{}使用优惠券成功，更新用户优惠券信息成功。", logGroup);

                    } else {
                        LOG.error("{}使用优惠券失败，更新用户优惠券信息失败.", logGroup);
                        throw new PageInfoException("使用优惠券失败，更新用户优惠券信息失败.", ResultInfo.SERVER_ERROR.getCode());
                    }

                }
            }

            UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
            // 添加账户流水表 status = 0
            AccountFlow accountFlow = new AccountFlow();
            // 只有京东产品需要设置流水号
            if (pro.getpType() == 0) {
                accountFlow.setNumber(selfSeriaNumber);
            }
            accountFlow.settId(trading.getId());
            accountFlow.setaId(usersAccount.getId());
            accountFlow.setOperateTime(new Date());
            accountFlow.setType(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL);
            accountFlow.setStatus(AppCons.ACCOUNT_FLOW_WAITING_PAYMENT); // 待支付
            accountFlow.setBalance(usersAccount.getBalance());
            accountFlow.setFrozenMoney(trading.getBuyMoney());
            accountFlow.setMoney(trading.getBuyMoney());
            result = accountFlowMapper.insertSelective(accountFlow);

            if (result > 0) {
                LOG.info("{}保存用户账户流水成功。", logGroup);

            } else {
                LOG.error("{}保存用户账户流水失败。", logGroup);
                throw new PageInfoException("更新账户流水失败", ResultInfo.SERVER_ERROR.getCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 失败回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            pi.setCode(ResultInfo.SERVER_ERROR.getCode());
            pi.setMsg("购买失败，系统错误");
            return pi;
        }

        // ===================购买数据库操作结束=====================================

        LOG.info("{}购买成功，购买操作处理完成", logGroup);

        // 支付剩余时间（毫秒）购买时间 + 15分钟-当前时间
        long remainDt = trading.getBuyDt().getTime() + minute * 60 * 1000 - System.currentTimeMillis();
        remainDt = remainDt < 0 ? 0 : remainDt;
        // 距离产品结束时间
        long remainEdDt = productCustomerMapper.selectByCode(pCode).getEdDt().getTime() - System.currentTimeMillis();
        remainEdDt = remainEdDt < 0 ? 0 : remainEdDt;

        resMap.put("tId", trading.getId());
        resMap.put("remainDt", remainDt < remainEdDt ? remainDt : remainEdDt);
        resMap.put("buyMoney", buyMoney);
        resMap.put("pCode", trading.getpCode());
        pi.setObj(resMap);
        pi.setCode(ResultInfo.SUCCESS.getCode());
        pi.setMsg("操作成功");

        return pi;
    }

}
