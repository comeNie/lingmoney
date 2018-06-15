package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.exception.DataUnCompleteException;
import com.mrbt.lingmoney.admin.service.bank.SingleCancelBiddingService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.bid.HxSingleBiddingCancelResult;
import com.mrbt.lingmoney.bank.bid.HxSingleCancelBidding;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxBiddingCancelRecordMapper;
import com.mrbt.lingmoney.mapper.HxBiddingMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingCancelRecord;
import com.mrbt.lingmoney.model.HxBiddingCancelRecordExample;
import com.mrbt.lingmoney.model.HxBiddingExample;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 
 * SingleCancelBiddingServiceImpl
 *
 */
@Service
public class SingleCancelBiddingServiceImpl implements SingleCancelBiddingService {

	@Autowired
	private HxSingleCancelBidding hxSingleCancelBidding;

	@Autowired
	private HxAccountMapper hxAccountMapper;

	@Autowired
	private HxBiddingMapper hxBiddingMapper;

	@Autowired
	private TradingMapper tradingMapper;

	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;

	@Autowired
	private UsersAccountMapper usersAccountMapper;

	@Autowired
	private AccountFlowMapper accountFlowMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;
	
	@Autowired
	private HxBiddingCancelRecordMapper hxBiddingCancelRecordMapper;

	@Autowired
	private HxSingleBiddingCancelResult hxSingleBiddingCancelResult;

	private static final Logger LOGGER = LogManager.getLogger(SingleCancelBiddingServiceImpl.class);

	private static final String LOGHEARD = "\nHxSingleCancelBidding_";

	private static final String BANKURL = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");

	@Override
	public PageInfo requestSingleCancelBidding(int clientType, String appId, int tId, String cancelReason)
			throws Exception {
		// 生成日志头
		String logGroup = LOGHEARD + System.currentTimeMillis() + "_";

		LOGGER.info("单笔撤标请求：" + logGroup);

		PageInfo pageInfo = new PageInfo();
		// 根据tId找到交易和交易信息
		Trading trading = tradingMapper.selectByPrimaryKey(tId);
		TradingFixInfo tradingFixInfo = tradingFixInfoMapper.selectFixInfoByTid(tId);
		if (StringUtils.isEmpty(trading) || StringUtils.isEmpty(tradingFixInfo)) {
			pageInfo.setMsg("交易不存在");
			pageInfo.setCode(ResultParame.ResultInfo.ORDER_NOT_EXIST.getCode());
			return pageInfo;
		}
		Integer returnStatus = trading.getStatus(); // 交易状态
		if (returnStatus != ResultParame.ResultNumber.FOUR.getNumber()) {
			pageInfo.setMsg("只有冻结中的订单才可撤标");
			pageInfo.setCode(ResultParame.ResultInfo.UPDATE_FAIL.getCode());
			return pageInfo;
		}

		// 用户ID
		String uId = trading.getuId();
		// 投资人账号和投资人账号户名
		HxAccountExample example = new HxAccountExample();
		example.createCriteria().andUIdEqualTo(uId);
		List<HxAccount> hxList = hxAccountMapper.selectByExample(example);
		if (StringUtils.isEmpty(hxList) || hxList.size() == 0) {
			pageInfo.setMsg("未开通华兴E账户");
			pageInfo.setCode(ResultParame.ResultInfo.NOT_HX_ACCOUNT.getCode());
			return pageInfo;
		}
		HxAccount hxAccount = hxList.get(0);
		String acNo = hxAccount.getAcNo();
		String acName = hxAccount.getAcName();
		// 原投标流水号
		String oldReqseqno = trading.getBizCode();
		// 产品ID
		Integer pId = trading.getpId();
		// 借款编号
		HxBiddingExample hxBiddingExample = new HxBiddingExample();
		hxBiddingExample.createCriteria().andPIdEqualTo(pId);
		List<HxBidding> list = hxBiddingMapper.selectByExample(hxBiddingExample);
		if (StringUtils.isEmpty(list) || list.size() == 0) {
			pageInfo.setMsg("标的不存在");
			pageInfo.setCode(ResultParame.ResultInfo.BIAODI_NOT_EXIST.getCode());
			return pageInfo;
		}
		HxBidding hxBidding = list.get(0);
		String loanNo = hxBidding.getLoanNo();

		Map<String, Object> resMap = hxSingleCancelBidding.singleCancelBidding(appId, loanNo, oldReqseqno, acNo, acName,
				cancelReason, logGroup, BANKURL);

		if (resMap != null) {
			String errorCode = resMap.get("errorCode").toString();
			if (errorCode.equals("0")) { // 以下信息，只有errorCode =0时才返回，即正常响应时才返回
				String status = resMap.get("status").toString();
				if (status.equals("0")) { // 受理成功，不代表操作成功
					pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
					pageInfo.setMsg("撤标请求成功");

					// 撤标请求流水号，用于查询撤标结果
					String channelFlow = (String) resMap.get("channelFlow");

					// 插入一条撤标记录
					HxBiddingCancelRecord hxBiddingCancelRecord = new HxBiddingCancelRecord();
					hxBiddingCancelRecord.setAppId("PC");
					hxBiddingCancelRecord.setCancelReason(cancelReason);
					hxBiddingCancelRecord.setChannelFlow(channelFlow);
					hxBiddingCancelRecord.setRequestDate(new Date());
					hxBiddingCancelRecord.setStatus(0);
					hxBiddingCancelRecord.setType(0);
					hxBiddingCancelRecord.settId(tId);
					int i = hxBiddingCancelRecordMapper.insertSelective(hxBiddingCancelRecord);

					if (i > 0) {
						LOGGER.info("{}撤标，保存撤标记录成功。", logGroup);
					} else {
						LOGGER.info("{}撤标，保存撤标记录失败。{}", logGroup, hxBiddingCancelRecord.toString());
					}

				} else if (status.equals("1")) { // 受理失败，交易可置为失败
					pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
					pageInfo.setMsg("银行受理失败");
				} else {
					pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
					pageInfo.setMsg("处理中");
				}
			} else { // 错误，返回具体错误原因
				String errorMsg = resMap.get("errorMsg").toString();
				pageInfo.setCode(ResultParame.ResultInfo.TRADING_NOT_SUCCESS.getCode());
				pageInfo.setMsg(errorMsg);
			}
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.ING.getCode());
			pageInfo.setMsg("处理中");
		}

		return pageInfo;
	}

	@Transactional
	@Override
	public PageInfo querySingleBiddingCancelResult(String appId, Integer tid, String logGroup) {
		PageInfo pageInfo = new PageInfo();
		
		if (StringUtils.isEmpty(tid)) {
			pageInfo.setCode(ResultParame.ResultInfo.PARAM_MISS.getCode());
			pageInfo.setMsg("参数错误");
			return pageInfo;
		}
		
		Trading trading = tradingMapper.selectByPrimaryKey(tid);
		
		if (trading == null) {
			pageInfo.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("未查询到交易信息");
			return pageInfo;
		}

		// 查询撤标记录，更新对应状态
		HxBiddingCancelRecordExample hxBiddingCancelExmp = new HxBiddingCancelRecordExample();
		hxBiddingCancelExmp.createCriteria().andTIdEqualTo(tid);
		List<HxBiddingCancelRecord> hxBiddingCancelRecordList = hxBiddingCancelRecordMapper
				.selectByExample(hxBiddingCancelExmp);

		if (hxBiddingCancelRecordList != null && hxBiddingCancelRecordList.size() > 0) {
			HxBiddingCancelRecord hxBiddingCancelRecord = hxBiddingCancelRecordList.get(0);

			int status = hxBiddingCancelRecord.getStatus().intValue();
			
			if (status == 0) {
				Document doc = hxSingleBiddingCancelResult.requestBiddingCancelResult(appId,
						hxBiddingCancelRecord.getChannelFlow(), logGroup);
				
				if (doc != null) {
					Map<String, Object> resMap = HxBaseData.xmlToMap(doc);

					String errorCode = (String) resMap.get("errorCode");

					// 更新撤标数据
					HxBiddingCancelRecord updateRecord = new HxBiddingCancelRecord();
					updateRecord.setId(hxBiddingCancelRecord.getId());

					if ("0".equals(errorCode)) {
						// 响应正常
						pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
						String resStatus = (String) resMap.get("RETURN_STATUS");

						if ("S".equals(resStatus)) {
							// 成功
							pageInfo.setMsg("撤标成功");

							updateRecord.setStatus(1);

							int i = hxBiddingCancelRecordMapper.updateByPrimaryKeySelective(updateRecord);
							if (i > 0) {
								LOGGER.info("{}更新撤标结果成功", logGroup);

								try {
									handelCancelBidding(logGroup, trading.getpCode(), trading.getuId(), tid);
								} catch (DataUnCompleteException e) {
									e.printStackTrace();
									LOGGER.error("{}撤标成功，更新对应数据出错", logGroup);

									pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
									pageInfo.setMsg("系统错误");

								}

							} else {
								LOGGER.info("{}更新撤标结果失败", logGroup);
							}

						} else if ("F".equals(resStatus)) {
							// 失败
							pageInfo.setMsg("撤标失败");

							updateRecord.setStatus(ResultParame.ResultNumber.TWO.getNumber());

							int i = hxBiddingCancelRecordMapper.updateByPrimaryKeySelective(updateRecord);
							if (i > 0) {
								LOGGER.info("{}更新撤标结果成功", logGroup);
							} else {
								LOGGER.info("{}更新撤标结果失败", logGroup);
							}

						} else {
							// 处理中
							pageInfo.setMsg("处理中，请稍后再试");
						}

					} else {
						String errorMsg = (String) resMap.get("errorMsg");
						LOGGER.info("{}查询撤标结果失败，原因：{}", logGroup, errorMsg);

						pageInfo.setCode(ResultParame.ResultInfo.MODIFY_REJECT.getCode());
						pageInfo.setMsg("查询失败：" + errorMsg);
					}

				} else {
					pageInfo.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
					pageInfo.setMsg("未查询到撤标结果，请稍后再试");
				}
				
			} else if (status == 1) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("撤标成功");

			} else if (status == ResultParame.ResultNumber.TWO.getNumber()) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("撤标失败");
			}

		} else {
			pageInfo.setCode(ResultParame.ResultInfo.EMPTY_DATA.getCode());
			pageInfo.setMsg("未查询到撤标记录");
		}

		return pageInfo;
	}

	@Transactional
	@Override
	public void handelCancelBidding(String logGroup, String loanNo, String uid, Integer tid)
			throws DataUnCompleteException {
		// 1.更新交易状态
		Trading tradingRecord = new Trading();
		tradingRecord.setId(tid);
		tradingRecord.setStatus(AppCons.BUY_CANCEL_BID);
		int dataResult = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

		if (dataResult > 0) {
			LOGGER.info("{}撤标,更新traidng数据成功。\n{}", logGroup, tradingRecord.toString());

		} else {
			LOGGER.error("{}撤标,更新traidng数据失败。\n{}", logGroup, tradingRecord.toString());
			throw new DataUnCompleteException(logGroup + "更新trading数据失败");
		}

		// 2、更新trading_fix_info状态为 已撤标
		TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tid);
		if (tfi != null) {
			TradingFixInfo fixInfoRecord = new TradingFixInfo();
			fixInfoRecord.setId(tfi.getId());
			fixInfoRecord.setStatus(AppCons.BUY_CANCEL_BID);
			dataResult = tradingFixInfoMapper.updateByPrimaryKeySelective(fixInfoRecord);

			if (dataResult > 0) {
				LOGGER.info("{}撤标,更新trading_fix_info数据成功。\n{}", logGroup, fixInfoRecord.toString());

			} else {
				LOGGER.error("{}撤标,更新trading_fix_info数据失败。\n{}", logGroup, fixInfoRecord.toString());
				throw new DataUnCompleteException(logGroup + "更新trading_fix_info数据失败");
			}

		} else {
			LOGGER.error("{}撤标,未查询到相关trading_fix_info信息。tid : {}", logGroup, tid);
			throw new IllegalArgumentException(logGroup + "未查询到tid为：" + tid + "的交易流水信息");
		}

		// 3.初始化一条回款记录
		Trading trading = tradingMapper.selectByPrimaryKey(tid);
		HxUsersAccountRepaymentRecord repaymentRecord = new HxUsersAccountRepaymentRecord();
		repaymentRecord.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		repaymentRecord.setLoanNo(loanNo);
		repaymentRecord.setInitTime(new Date());
		repaymentRecord.setType(ResultParame.ResultNumber.TWO.getNumber());
		repaymentRecord.setState(0);
		repaymentRecord.settId(trading.getId());
		repaymentRecord.setAmount(trading.getBuyMoney());

		dataResult = hxUsersAccountRepaymentRecordMapper.insertSelective(repaymentRecord);

		if (dataResult > 0) {
			LOGGER.info("{}撤标，借款编号{}，初始化交易号{}回款记录成功", logGroup, loanNo, trading.getId());

		} else {
			LOGGER.error("{}撤标，借款编号{}，初始化交易号{}回款记录失败", logGroup, loanNo, trading.getId());
			throw new DataUnCompleteException(logGroup + "初始化回款记录失败");
		}

		// 4、账户流水状态变更
		AccountFlowExample accountFlowExmp = new AccountFlowExample();
		accountFlowExmp.createCriteria().andNumberEqualTo(trading.getBizCode())
				.andTypeEqualTo(ResultParame.ResultNumber.TWO.getNumber());
		List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);

		if (accountFlowList != null && accountFlowList.size() > 0) {
			AccountFlow accountFlow = accountFlowList.get(0);

			AccountFlow accountFlowRecord = new AccountFlow();
			accountFlowRecord.setId(accountFlow.getId());
			accountFlowRecord.setStatus(ResultParame.ResultNumber.THREE.getNumber());
			dataResult = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

			if (dataResult > 0) {
				LOGGER.info("{}撤标,更新account_flow数据成功。\n{}", logGroup, accountFlowRecord.toString());

			} else {
				LOGGER.error("{}撤标,更新account_flow数据失败。\n{}", logGroup, accountFlowRecord.toString());
				throw new DataUnCompleteException(logGroup + "更新account_flow数据失败");
			}
		}
	}

}
