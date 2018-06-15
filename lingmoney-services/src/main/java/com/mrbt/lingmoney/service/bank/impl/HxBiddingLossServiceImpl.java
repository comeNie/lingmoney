package com.mrbt.lingmoney.service.bank.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.biddingloss.HxInitiativeBiddingLoss;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxBiddingLossRecordMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxBiddingLossRecord;
import com.mrbt.lingmoney.model.HxBiddingLossRecordExample;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductWithBLOBs;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingExample;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.service.bank.HxBiddingLossService;
import com.mrbt.lingmoney.utils.AppCons;

/**
 * @author syb
 * @date 2017年6月5日 下午4:27:21
 * @version 1.0
 * @description
 **/
@Service
public class HxBiddingLossServiceImpl implements HxBiddingLossService {
	private static Logger logger = LogManager.getLogger(HxBiddingLossServiceImpl.class);

	@Autowired
	private HxInitiativeBiddingLoss hxInitiativeBiddingLoss;
	@Autowired
	private HxBiddingMapper hxBiddingMapper;
	@Autowired
	private HxBiddingLossRecordMapper hxBiddingLossRecordMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;


	@Override
	public String handleHxInitiativeBiddingLoss(Document document) {

		String logGroup = "\n银行主动流标_" + System.currentTimeMillis();

		String errorCode = "0"; // 错误码 默认0：成功
		String errorMsg = "success"; // 错误信息，默认success
		String loanNo = ""; // 借款编号
		String channelFlow = "";
		String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定

		if (document != null) {
			Map<String, Object> map = HxBaseData.xmlToMap(document);
			loanNo = (String) map.get("LOANNO");
			channelFlow = (String) map.get("channelFlow");

			// 只处理标的状态为0（正常）的数据
			HxBiddingExample example = new HxBiddingExample();
			example.createCriteria().andLoanNoEqualTo(loanNo);
			List<HxBidding> hxBiddingList = hxBiddingMapper.selectByExample(example);
			if (hxBiddingList != null && hxBiddingList.size() > 0) {
				HxBidding hxBidding = hxBiddingList.get(0);

				if ("0".equals(hxBidding.getInvestObjState())) {
					// 流标理由
					String cancelReason = (String) map.get("CANCELREASON");

					HxBidding biddingRecord = new HxBidding();
					biddingRecord.setId(hxBidding.getId());
					biddingRecord.setInvestObjState("2");
					int result = hxBiddingMapper.updateByPrimaryKeySelective(biddingRecord);

					if (result > 0) {
						logger.info("{}银行主动流标，更新标的{}状态成功。", logGroup, biddingRecord.getId());
					} else {
						logger.info("{}银行主动流标，更新标的{}状态失败。", logGroup, biddingRecord.getId());
					}

					// 流标记录，如果存在 则更新，否则插入一条数据。
					HxBiddingLossRecordExample biddingLossExample = new HxBiddingLossRecordExample();
					biddingLossExample.createCriteria().andLoanNoEqualTo(loanNo);
					biddingLossExample.setOrderByClause("apply_time desc");
					List<HxBiddingLossRecord> biddingLossList = hxBiddingLossRecordMapper
							.selectByExample(biddingLossExample);

					// 流标入库信息
					HxBiddingLossRecord biddingLossRecord;
					if (biddingLossList != null && biddingLossList.size() > 0) {
						// 二次流标
						biddingLossRecord = biddingLossList.get(0);
						biddingLossRecord.setState(1);
						biddingLossRecord.setCancelReason(cancelReason);
						biddingLossRecord.setType(1);
						biddingLossRecord.setResponseTime(new Date());
						biddingLossRecord.setErrorMsg(null);
						result = hxBiddingLossRecordMapper.updateByPrimaryKeySelective(biddingLossRecord);

						if (result > 0) {
							logger.info("{}银行主动流标,更新流标记录成功。\n{}", logGroup, biddingLossRecord.toString());
						} else {
							logger.error("{}银行主动流标,更新流标记录失败。\n{}", logGroup, biddingLossRecord.toString());
						}
					} else {
						// 首次流标
						biddingLossRecord = new HxBiddingLossRecord();
						biddingLossRecord.setId(UUID.randomUUID().toString().replace("-", ""));
						biddingLossRecord.setAppId("PC");
						biddingLossRecord.setApplyTime(new Date());
						biddingLossRecord.setChannelFlow(channelFlow);
						biddingLossRecord.setState(1);
						biddingLossRecord.setCancelReason(cancelReason);
						biddingLossRecord.setLoanNo(loanNo);
						biddingLossRecord.setType(1);
						biddingLossRecord.setResponseTime(new Date());
						result = hxBiddingLossRecordMapper.insertSelective(biddingLossRecord);

						if (result > 0) {
							logger.info("{}银行主动流标,插入流标记录成功。\n{}", logGroup, biddingLossRecord.toString());
						} else {
							logger.error("{}银行主动流标,插入流标记录失败。\n{}", logGroup, biddingLossRecord.toString());
						}
					}

					// 更新产品状态为7（已流标）
					updateProductAndTradingAfterBiddingLoss(logGroup, hxBidding, AppCons.PRODUCT_STATUS_BIDDINGLOSSED);

					// 初始化回款记录
					initHxRepaymentRecord(loanNo, logGroup);

					logger.info(logGroup + "处理银行主动流标成功");
				} else {
					errorCode = "100004";
					errorMsg = "标的状态异常";
					status = "1";

					logger.info(logGroup + "处理银行主动流标失败,标的非正常状态：state \t" + hxBidding.getInvestObjState());
				}
			} else {
				errorCode = "100003";
				errorMsg = "未查询到指定借款信息";
				status = "1";

				logger.info(logGroup + "处理银行主动流标失败,未查询到标的信息。loanNo:" + loanNo);
			}
		} else {
			errorCode = "100002";
			errorMsg = "解析数据失败";
			status = "1";

			logger.info(logGroup + "处理银行主动流标失败,解析数据失败");
		}

		return hxInitiativeBiddingLoss.responseInitiativeBiddingLoss(channelFlow, loanNo, errorCode, errorMsg, status,
				logGroup);
	}

	/**
	 * 初始化回款记录，因为涉及数据库事物操作，需要查询交易状态为（冻结中，已流标，支付中）的数据
	 * 
	 * @param loanNo 借款编号
	 * @param logGroup 日志头
	 */
	private void initHxRepaymentRecord(String loanNo, String logGroup) {
		List<Integer> paramlist = new ArrayList<Integer>();
		paramlist.add(AppCons.BUY_FROKEN);
		paramlist.add(AppCons.BUY_LOSS_BID);
		paramlist.add(AppCons.BUY_TRADING);
		// 查询支付成功/支付中/已流标 记录
		TradingExample example = new TradingExample();
		example.createCriteria().andPCodeEqualTo(loanNo).andStatusIn(paramlist);
		List<Trading> tradingList = tradingMapper.selectByExample(example);

		if (tradingList != null && tradingList.size() > 0) {
			for (Trading trading : tradingList) {
				HxUsersAccountRepaymentRecord repaymentRecord = new HxUsersAccountRepaymentRecord();
				repaymentRecord.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				repaymentRecord.setLoanNo(loanNo);
				repaymentRecord.setInitTime(new Date());
				repaymentRecord.setType(1);
				repaymentRecord.setState(0);
				repaymentRecord.settId(trading.getId());
				repaymentRecord.setAmount(trading.getBuyMoney());

				int result = hxUsersAccountRepaymentRecordMapper.insertSelective(repaymentRecord);

				if (result > 0) {
					logger.info("{}产品流标，借款编号{}，初始化交易号{}回款记录成功", logGroup, loanNo, trading.getId());
				} else {
					logger.error("{}产品流标，借款编号{}，初始化交易号{}回款记录失败", logGroup, loanNo, trading.getId());
				}
			}
		}

	}

	/**
	 * 流标操作，相关产品状态更新、交易状态更新
	 * 
	 * @param logGroup 日志头
	 * @param hxBidding 标的信息
	 * @param status 状态
	 */
	private void updateProductAndTradingAfterBiddingLoss(String logGroup, HxBidding hxBidding, int status) {
		int result;
		Product product = productMapper.selectByPrimaryKey(hxBidding.getpId());
		if (product != null) {
			ProductWithBLOBs productRecord = new ProductWithBLOBs();
			productRecord.setId(product.getId());
			productRecord.setStatus(status);
			result = productMapper.updateByPrimaryKeySelective(productRecord);

			if (result > 0) {
				logger.info("{}流标,更新产品{}状态为{}成功。", logGroup, productRecord.getId(), status);
			} else {
				logger.error("{}流标,更新产品{}状态为{}失败。", logGroup, productRecord.getId(), status);
			}

			// 已流标时需要更新该产品下交易冻结状态为流标状态
			if (status == AppCons.PRODUCT_STATUS_BIDDINGLOSSED) {
				// 更新投标人交易状态为 已流标
				TradingExample tradingExmp = new TradingExample();
				tradingExmp.createCriteria().andPIdEqualTo(product.getId()).andStatusEqualTo(AppCons.BUY_FROKEN);

				List<Trading> tradingList = tradingMapper.selectByExample(tradingExmp);
				if (tradingList != null && tradingList.size() > 0) {
					for (Trading trading : tradingList) {
						Trading tradingRecord = new Trading();
						tradingRecord.setId(trading.getId());
						tradingRecord.setStatus(AppCons.BUY_LOSS_BID);
						result = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

						if (result > 0) {
							logger.info("{}流标,更新交易{}状态为已流标成功。", logGroup, tradingRecord.getId());
						} else {
							logger.error("{}流标,更新交易{}状态为已流标失败。", logGroup, tradingRecord.getId());
						}

						TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(trading.getId());

						if (tradingFixInfo != null) {
							TradingFixInfo tradingFixInfoRecord = new TradingFixInfo();
							tradingFixInfoRecord.setId(tradingFixInfo.getId());
							tradingFixInfoRecord.setStatus(AppCons.BUY_LOSS_BID);
							result = tradingFixInfoMapper.updateByPrimaryKeySelective(tradingFixInfoRecord);

							if (result > 0) {
								logger.info("{}流标,更新交易流水{}状态为已流标成功。", logGroup, tradingFixInfoRecord.getId());
							} else {
								logger.error("{}流标,更新交易流水{}状态为已流标失败。", logGroup, tradingFixInfoRecord.getId());
							}

						}

						// 更新交易流水状态为已流标
						AccountFlowExample accountFlowExmp = new AccountFlowExample();
						accountFlowExmp.createCriteria().andNumberEqualTo(trading.getBizCode());

						List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);
						if (accountFlowList != null && accountFlowList.size() > 0) {
							AccountFlow accountFlow = accountFlowList.get(0);

							AccountFlow accountFlowRecord = new AccountFlow();
							accountFlowRecord.setId(accountFlow.getId());
                            accountFlowRecord.setStatus(AppCons.ACCOUNT_FLOW_BIDDING_LOSS);
							result = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

							if (result > 0) {
								logger.info("{}流标,更新账户流水{}状态为已流标成功。", logGroup, accountFlowRecord.getId());
							} else {
								logger.error("{}流标,更新账户流水{}状态为已流标失败。", logGroup, accountFlowRecord.getId());
							}
						}

					}
				}
			}

		}
	}

}
