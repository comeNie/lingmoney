package com.mrbt.lingmoney.admin.service.bank.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
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
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.SingleBiddingResultService;
import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.admin.service.smsSend.SmsService;
import com.mrbt.lingmoney.bank.bid.HxSingleBiddingResult;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.ProductCustomerMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ActivityRecordExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ProductUtils;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 
 * SingleBiddingResultServiceImpl
 *
 */
@Service
public class SingleBiddingResultServiceImpl implements SingleBiddingResultService {

	private static final Logger LOGGER = LogManager.getLogger(SingleBiddingResultServiceImpl.class);
	private static final String LOGHEARD = "\nHxSingleBiddingResult_";
	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Autowired
	private HxSingleBiddingResult hxSingleBiddingResult;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private ProductCustomerMapper productCustomerMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private RedPacketService redPacketService;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private SmsService smsService;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;

	@Override
	public PageInfo requestSingleBiddingResult(int clientType, String appId, String uId, String oldReqseqno)
			throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("单笔投标结果查询请求：" + logGroup + "，用户{}，流水号{}", uId, oldReqseqno);
		PageInfo pageInfo = new PageInfo();
		
		if (StringUtils.isEmpty(oldReqseqno)) {
			LOGGER.info("单笔投标结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，交易不存在", uId, oldReqseqno);
			pageInfo.setMsg("交易不存在");
			pageInfo.setCode(ResultParame.ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}

		Trading trading = tradingMapper.selectByBizCode(oldReqseqno);
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByBizCode(oldReqseqno);
		if (StringUtils.isEmpty(trading) || StringUtils.isEmpty(tradingFixInfo)) {
			LOGGER.info("单笔投标结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，交易不存在", uId, oldReqseqno);
			pageInfo.setMsg("交易不存在");
			pageInfo.setCode(ResultParame.ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}
		// 2、只有订单状态为支付中12的需要查询
		Integer returnStatus = trading.getStatus();
		if (returnStatus != ResultParame.ResultNumber.TWELVE.getNumber()) {
			LOGGER.info("单笔投标结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，只提供支付中的订单查询，当前状态为{}", uId, oldReqseqno,
					returnStatus);
			pageInfo.setMsg("只提供支付中的订单查询");
			pageInfo.setCode(ResultParame.ResultInfo.ORDER_STATUS_ERROR.getCode());
			return pageInfo;
		}

		// 频繁查询过滤
		String seqNo = "querySingleBiddingResult_" + oldReqseqno;
		if (redisDao.get(seqNo) != null) {
			LOGGER.info("单笔投标结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，2分钟内频繁查询", uId, oldReqseqno);
			pageInfo.setMsg("2分钟内频繁查询");
			pageInfo.setCode(ResultParame.ResultInfo.RETURN_DATA_EMPTY_DATA.getCode());
			return pageInfo;
		}
		redisDao.set(seqNo, uId);
		redisDao.expire(seqNo, ResultParame.ResultNumber.TWO.getNumber(), TimeUnit.MINUTES);

        // 5分钟后才可查询结果
		Date now = new Date();
		Date operateTime = trading.getBuyDt();
		if (operateTime == null
				|| DateUtils.dateDiffMins(operateTime, now) <= ResultParame.ResultNumber.FIVE.getNumber()) {
			LOGGER.info("单笔投标结果查询请求失败：" + logGroup + "，用户{}，流水号{}" + "，5分钟后再进行查询", uId, oldReqseqno);
			pageInfo.setMsg("5分钟后再进行查询");
			pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
			return pageInfo;
		}

		// 3、生成报文
		Map<String, Object> resMap = hxSingleBiddingResult.querySingleBiddingResult(appId, trading.getBizCode(),
				logGroup, BANKURL);
		if (resMap != null && resMap.size() > 0) {
			LOGGER.info("单笔投标结果查询请求：" + logGroup + "，用户{}，流水号{}" + "，银行返回报文为{}", uId, oldReqseqno, resMap.toString());
			String errorCode = resMap.get("errorCode").toString();
			if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
				if (status.equals("0")) { // 受理成功，不代表操作成功
					/**
					 * S 成功 F 失败 R 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。） N
					 * 未知（已提交后台，需再次发查询接口。）
					 * 
					 */
					String returnStatus2 = resMap.get("RETURN_STATUS").toString();
					if (returnStatus2.equals("S")) {
						// 处理成功
						boolean b = handleBuyProduct(trading.getId(), tradingFixInfo.getId(), trading.getBizCode(), uId,
								CallbackType.Frozen);
						if (b) {
							buyWeiyingbaoGetLingbao(uId, trading.getId(), tradingFixInfo.getId());
							pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
							pageInfo.setMsg("购买成功");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("name", productMapper.selectByPrimaryKey(trading.getpId()).getName());
							map.put("money", trading.getFinancialMoney());
							pageInfo.setObj(map);

							// 购买成功执行短信提醒
							try {
								Users user = usersMapper.selectByPrimaryKey(uId);
								Product pro = productCustomerMapper.selectProductByPrimaryKey(trading.getpId());
								UsersProperties usersProperties = usersPropertiesMapper.selectByuId(uId);
								String userName = "";
								if (usersProperties != null) {
									userName = usersProperties.getName();
								}
								String content = AppCons.buy_content;
								content = MessageFormat.format(content, userName, pro.getName(), pro.getfTime(),
										trading.getFinancialMoney());

								// 发送短信修改为放入redis统一发送
								SmsMessage message = new SmsMessage();
								message.setTelephone(user.getTelephone());
								message.setContent(content);
								smsService.saveSmsMessage(message);

							} catch (Exception e) {
								LOGGER.error("发送购买成功短信失败，用户id{}，交易id：{}，错误信息：{}", uId, trading.getId(), e.toString());
								e.printStackTrace();
							}
							// 首笔理财成功，赠送0.025加息卷
							redPacketService.rewardRedPackage(uId, ResultParame.ResultNumber.FOUR.getNumber(), null);
						} else {
							pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
							pageInfo.setMsg("更新数据失败");
						}
					} else if (returnStatus2.equals("F")) {
						// 处理失败
						String errormsg = resMap.get("ERRORMSG").toString();
						handleBuyProduct(trading.getId(), tradingFixInfo.getId(), trading.getBizCode(), uId,
								CallbackType.failure);
						pageInfo.setCode(ResultParame.ResultInfo.NO_SUCCESS.getCode());
						pageInfo.setMsg(errormsg);
					} else if (returnStatus2.equals("R")) {
						// 处理中（客户仍停留在页面操作，25分钟仍R状态的可置为交易失败。）
						long minuteDiff = DateUtils.dateDiffMins(trading.getBuyDt(), new Date());
						if (minuteDiff >= ResultParame.ResultNumber.TWENTY_FIVE.getNumber()) {
							handleBuyProduct(trading.getId(), tradingFixInfo.getId(), trading.getBizCode(), uId,
									CallbackType.failure);
						}
						pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					} else {
						// 未知（已提交后台，需再次发查询接口。）
						pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
						pageInfo.setMsg("处理中");
					}
				} else if (status.equals("1")) { // 受理失败，交易可置为失败
					handleBuyProduct(trading.getId(), tradingFixInfo.getId(), trading.getBizCode(), uId,
							CallbackType.failure);
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			} else { // 错误，返回具体错误原因
				// 如果返回code不为访问频繁
				if (!resMap.get("errorCode").equals("OGW100200009")) {
					// 错误，返回具体错误原因
					String errorMsg = resMap.get("errorMsg").toString();
					handleBuyProduct(trading.getId(), tradingFixInfo.getId(), trading.getBizCode(), uId,
							CallbackType.failure);
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg(errorMsg);
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			}
		} else {
			LOGGER.info(logGroup + "查询投标结果银行返回报文为空");
			pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;

	}

	/**
	 * 
	 * 枚举类
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
	 * handleBuyProduct
	 * 
	 * @param tId
	 *            tId
	 * @param infoId
	 *            infoId
	 * @param bizCode
	 *            bizCode
	 * @param uId
	 *            uId
	 * @param flag
	 *            flag
	 * @return 数据返回
	 */
	@Transactional
    public boolean handleBuyProduct(Integer tId, Integer infoId, String bizCode, String uId, CallbackType flag) {
		String logGroup = "\n购买产品，支付处理" + System.currentTimeMillis() + "_";
		try {
			Trading trading = tradingMapper.selectByPrimaryKey(tId);
			TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectByPrimaryKey(infoId);
			UsersAccount usersAccount = usersAccountMapper.selectByUid(uId);
			if (trading == null || tradingFixInfo == null || usersAccount == null) {
				LOGGER.info("交易不存在");
				return false;
			}
			ProductWithBLOBs product = productMapper.selectByPrimaryKey(trading.getpId());
			LOGGER.info("\n用户:{},购买产品{},金额:{}元,交易id:{}", uId, product.getCode(), trading.getFinancialMoney(),
					trading.getId());
			// 状态，0:买入，1支付成功，2卖出，3卖出成功, 4 资金冻结，5支付失败，7退回，8 已撤标，12 支付中， 18 订单失效
			if (flag == CallbackType.trading) { // 支付中（处理中）
				LOGGER.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付中", uId, product.getCode(), trading.getFinancialMoney(),
						trading.getId());
				// 更新交易表和更新交易信息表 status = 12，修改订单号为channelflow
				trading.setStatus(AppCons.BUY_TRADING);
				trading.setBizCode(bizCode);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOGGER.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易表状态失败。", logGroup);
				}
				tradingFixInfo.setStatus(AppCons.BUY_TRADING);
				tradingFixInfo.setBizCode(bizCode);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOGGER.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易信息表状态失败。", logGroup);
				}
				// 修改资金流水订单号
				AccountFlowExample example = new AccountFlowExample();
				example.createCriteria().andTIdEqualTo(tId).andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber())
						.andStatusEqualTo(ResultParame.ResultNumber.SEVEN.getNumber())
						.andAIdEqualTo(usersAccount.getId());
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setNumber(bizCode);
				result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
				if (result > 0) {
					LOGGER.info("{}修改资金流水订单号成功。", logGroup);
				} else {
					LOGGER.error("{}修改资金流水订单号失败。", logGroup);
				}

			} else if (flag == CallbackType.failure) { // 失败
				LOGGER.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为支付失败", uId, product.getCode(),
						trading.getFinancialMoney(),
						trading.getId());
				// 更新交易表和更新交易信息表 status = 5
				trading.setStatus(AppCons.BUY_FAIL);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOGGER.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易表状态失败。", logGroup);
				}
				tradingFixInfo.setStatus(AppCons.BUY_FAIL);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOGGER.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易信息表状态失败。", logGroup);
				}
				// 更新产品表，回复reach_money
				if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE) {
					BigDecimal nowReach = product.getReachMoney().subtract(trading.getFinancialMoney());
					product.setReachMoney(nowReach.doubleValue() <= 0 ? new BigDecimal(0) : nowReach);
					result = productMapper.updateByPrimaryKeySelective(product);
					if (result > 0) {
						LOGGER.info("{}更新产品表成功。", logGroup);
					} else {
						LOGGER.error("{}更新产品表失败。", logGroup);
					}
				}

				// 修改账户流水表状态为失败
				AccountFlowExample example = new AccountFlowExample();
				example.createCriteria().andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber())
						.andNumberEqualTo(bizCode);
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setStatus(ResultParame.ResultNumber.TWO.getNumber());
				result = accountFlowMapper.updateByExampleSelective(accountFlow, example);
				if (result > 0) {
					LOGGER.info("{}修改资金流水状态成功。", logGroup);
				} else {
					LOGGER.error("{}修改资金流水状态失败。", logGroup);
				}

				// 修改用户账户表 balance↑ frozen_money↓
				usersAccount.setBalance(usersAccount.getBalance().add(trading.getBuyMoney()));
				usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
				result = usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
				if (result > 0) {
					LOGGER.info("{}修改用户账户表成功。", logGroup);
				} else {
					LOGGER.error("{}修改用户账户表失败。", logGroup);
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
							LOGGER.info("{}恢复优惠券信息成功。", logGroup);
						} else {
							LOGGER.error("{}恢复优惠券信息失败。", logGroup);
						}
					}
				}

			} else if (flag == CallbackType.Frozen) { // 回调成功，状态先改为冻结中（购买成功），如果产品成立，所有交易改为成功。
				LOGGER.info("\n用户:{},购买产品{},金额:{}元,交易id:{},状态为冻结中", uId, product.getCode(), trading.getFinancialMoney(),
						trading.getId());
				// 更新交易表和更新交易信息表 status = 4
				trading.setStatus(AppCons.BUY_FROKEN);
				int result = tradingMapper.updateByPrimaryKeySelective(trading);
				if (result > 0) {
					LOGGER.info("{}更新交易表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易表状态失败。", logGroup);
				}
				tradingFixInfo.setStatus(AppCons.BUY_FROKEN);
				result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfo);
				if (result > 0) {
					LOGGER.info("{}更新交易信息表状态成功。", logGroup);
				} else {
					LOGGER.error("{}更新交易信息表状态失败。", logGroup);
				}
				// 如果最后一笔交易成功后，更新产品为募集满状态，并更新详细交易表中的结息日和状态
				// 如果募集满，则查找最后一条的交易信息
				if (product.getRule() != null && product.getRule() != AppCons.RULE_NONE
						&& product.getPriorMoney().compareTo(product.getReachMoney()) == 0) {
					// 查找最后一次交易信息
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("priorMoney", product.getPriorMoney());
					map.put("pId", trading.getpId());
					map.put("status", AppCons.BUY_FROKEN);
					Trading lastTrading = productCustomerMapper.selectLastTrading(map);
					if (lastTrading != null) {
						LOGGER.info("\n用户:{},最后一笔交易id:{},成功后,产品:{}募集满,等待放款", lastTrading.getuId(), lastTrading.getId(),
								lastTrading.getpCode());
						// 更新产品为募集满状态
						product.setStatus(AppCons.PRODUCT_STATUS_COLLECT_SUCCESS);
						result = productMapper.updateByPrimaryKeySelective(product);
						if (result > 0) {
							LOGGER.info("{}更新产品表成功。", logGroup);
						} else {
							LOGGER.error("{}更新产品表失败。", logGroup);
						}
						// // 添加最小赎回的天数
						// lastTrading.setValueDt(lastTrading.getwValueDt());
						// if (product.getUnitTime() == AppCons.UNIT_TIME_DAY) {
						// lastTrading.setMinSellDt(DateUtils.addDay(lastTrading.getValueDt(),
						// product.getfTime()));
						//
						// } else if (product.getUnitTime() ==
						// AppCons.UNIT_TIME_MONTH) {
						// lastTrading.setMinSellDt(DateUtils.addMonth(lastTrading.getValueDt(),
						// product.getfTime()));
						//
						// } else if (product.getUnitTime() ==
						// AppCons.UNIT_TIME_WEEK) {
						// lastTrading.setMinSellDt(DateUtils.addWeek(lastTrading.getValueDt(),
						// product.getfTime()));
						// }
						// // 产品成立
						// updateProductByRun(lastTrading, false);
					}
				}
				// // 修改用户账户表 frozen_money↓ total_finance↑
				// usersAccount.setFrozenMoney(usersAccount.getFrozenMoney().subtract(trading.getBuyMoney()));
				// usersAccount.setTotalFinance(usersAccount.getTotalFinance().add(trading.getFinancialMoney()));
				// usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
				// 修改账户流水表 status = 5（冻结中）
				AccountFlowExample accountFlowExample = new AccountFlowExample();
				accountFlowExample.createCriteria().andNumberEqualTo(bizCode)
						.andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber());
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setFrozenMoney(new BigDecimal(0));
				accountFlow.setStatus(ResultParame.ResultNumber.FIVE.getNumber());
				accountFlowMapper.updateByExampleSelective(accountFlow, accountFlowExample);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("交易失败");
			return false;
		}

	}

	/**
	 * buyWeiyingbaoGetLingbao
	 * 
	 * @param uid
	 *            uid
	 * @param tid
	 *            tid
	 * @param infoid
	 *            infoid
	 */
	private void buyWeiyingbaoGetLingbao(String uid, int tid, int infoid) {
		// 支付成功，如果是购买的额稳赢宝，赠送领宝：理财额÷365天×理财天数÷50＝所赠送领宝
		Trading trading = tradingMapper.selectByPrimaryKey(tid);
		String wenyingbaocode = ProductUtils.getContent("wenyingbao_code");
		String chedaibaocode = ProductUtils.getContent("chedaibao_code");
		String huinongdaicode = ProductUtils.getContent("huinongdai_code");
        String qiyebaocode = ProductUtils.getContent("qiyebao_code");

        // 获取产品代码前十位和属性文件中的产品代码对比
		String productCode = trading.getpCode().substring(ResultParame.ResultNumber.ZERO.getNumber(),
				ResultParame.ResultNumber.TEN.getNumber());
        String content = "理财送领宝";
		if (null != wenyingbaocode && wenyingbaocode.equals(productCode)) {
			content = "购买稳赢宝送领宝";
		} else if (null != chedaibaocode && chedaibaocode.equals(productCode)) {
			content = "购买车贷宝送领宝";
        } else if (null != huinongdaicode && huinongdaicode.equals(productCode)) {
            content = "购买惠农贷送领宝";
        } else if (null != qiyebaocode && qiyebaocode.equals(productCode)) {
            content = "购买企业宝送领宝";
        }
		Product product = productMapper.selectByPrimaryKey(trading.getpId());
		// 计算领宝
		int lingbao = (trading.getFinancialMoney().multiply(new BigDecimal(product.getfTime())))
				.divide(new BigDecimal(ResultParame.ResultNumber.THREE_SIXTY_FIVE.getNumber()
						* ResultParame.ResultNumber.FIFTY.getNumber()), RoundingMode.HALF_UP)
				.intValue();

		ActivityRecordExample example = new ActivityRecordExample();
		example.createCriteria().andUIdEqualTo(uid).andTIdEqualTo(trading.getId()).andNameEqualTo("理财")
				.andUseDateEqualTo(new Date()).andAmountEqualTo(lingbao);
		int i = activityRecordMapper.countByExample(example);

		if (i < 1) { // 避免重复赠送
			ActivityRecord ar = new ActivityRecord();
			ar.setAmount(lingbao);
			ar.setContent(content);
			ar.setName("理财");
			ar.setStatus(1);
			ar.settId(trading.getId());
			ar.setType(0);
			ar.setuId(uid);
			ar.setUseDate(new Date());
			// 向领宝记录表中插入记录
			activityRecordMapper.insertSelective(ar);
			UsersAccountExample accountExample = new UsersAccountExample();
			accountExample.createCriteria().andUIdEqualTo(uid);
			UsersAccount ua = usersAccountMapper.selectByExample(accountExample).get(0);
			ua.setCountLingbao(lingbao + ua.getCountLingbao());
			// 更新用户账户领宝数量
			usersAccountMapper.updateByPrimaryKeySelective(ua);
			// 发送短信

		}
	}

	@Override
	public void updateProductReachMoney(Integer id, Integer pId, BigDecimal buyMoney) {
		//回归产品额度
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pId", pId);
		map.put("buyMoney", buyMoney);
		productCustomerMapper.updateProductReachMoney(map);
		
		//修改订单状态
		Trading record = tradingMapper.selectByPrimaryKey(id);
		record.setStatus(ResultNumber.SEVENTEEN.getNumber());
		tradingMapper.updateByPrimaryKeySelective(record);
	}

}
