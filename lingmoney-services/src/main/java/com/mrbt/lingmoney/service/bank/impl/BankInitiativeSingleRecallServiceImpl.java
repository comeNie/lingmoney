package com.mrbt.lingmoney.service.bank.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.recall.HxInitiativeSingleRecall;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.AccountFlowExample;
import com.mrbt.lingmoney.model.HxUsersAccountRepaymentRecord;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.bank.BankInitiativeSingleRecallService;
import com.mrbt.lingmoney.utils.AppCons;

/**
 * @author syb
 * @date 2017年6月5日 上午11:04:10
 * @version 1.0
 * @description
 **/
@Service
public class BankInitiativeSingleRecallServiceImpl implements BankInitiativeSingleRecallService {
	private static final Logger LOGGER = LogManager.getLogger(BankInitiativeSingleRecallServiceImpl.class);

	@Autowired
	private HxInitiativeSingleRecall hxInitiativeSingleRecall;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private TradingFixInfoMapper tradingFixInfoMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private HxUsersAccountRepaymentRecordMapper hxUsersAccountRepaymentRecordMapper;

	@Override
	public String initiativeSingleRecall(Document document) {

		String logGroup = "\n银行主动单笔撤标_" + System.currentTimeMillis();
		
        String errorCode = "0"; // 错误码 默认0：成功
        String errorMsg = "success"; // 错误信息，默认success
        String oldReqseqNo = ""; // 原投标流水号
        String channelFlow = ""; // 渠道流水号
        String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定
		
		if (document != null) {
			Map<String, Object> map = HxBaseData.xmlToMap(document);

			oldReqseqNo = (String) map.get("OLDREQSEQNO");
			channelFlow = (String) map.get("channelFlow");

			String loanNo = (String) map.get("LOANNO");
			String acNo = (String) map.get("ACNO");
			String acName = (String) map.get("ACNAME");
			map.get("CANCELREASON");

			// 验证银行请求信息
			Map<String, String> param = new HashMap<String, String>();
			param.put("loanNo", loanNo);
			param.put("acNo", acNo);
			param.put("acName", acName);
			param.put("bizCode", oldReqseqNo);
			Map<String, Object> result = tradingMapper.confirmRecallInfo(param);
			
			// 撤标数据处理
			if (result != null && !result.isEmpty()) {
				// 1、更新trading状态为 已撤标
				int tid = (int) result.get("tid");
				Trading tradingRecord = new Trading();
				tradingRecord.setId(tid);
				tradingRecord.setStatus(AppCons.BUY_CANCEL_BID);
				int dataResult = tradingMapper.updateByPrimaryKeySelective(tradingRecord);

				if (dataResult > 0) {
					LOGGER.info("{}银行主动撤标,更新traidng数据成功。\n{}", logGroup, tradingRecord.toString());
				} else {
					LOGGER.error("{}银行主动撤标,更新traidng数据失败。\n{}", logGroup, tradingRecord.toString());
				}

				// 2、更新trading_fix_info状态为 已撤标
				TradingFixInfo tfi = tradingFixInfoMapper.selectFixInfoByTid(tid);
				if (tfi != null) {
					TradingFixInfo fixInfoRecord = new TradingFixInfo();
					fixInfoRecord.setId(tfi.getId());
					fixInfoRecord.setStatus(AppCons.BUY_CANCEL_BID);
					dataResult = tradingFixInfoMapper.updateByPrimaryKeySelective(fixInfoRecord);

					if (dataResult > 0) {
						LOGGER.info("{}银行主动撤标,更新trading_fix_info数据成功。\n{}", logGroup, fixInfoRecord.toString());
					} else {
						LOGGER.error("{}银行主动撤标,更新trading_fix_info数据失败。\n{}", logGroup, fixInfoRecord.toString());
					}

				} else {
					LOGGER.error("{}银行主动撤标,未查询到相关trading_fix_info信息。tid : {}", logGroup, tid);
				}

				// 3.初始化一条回款记录
				Trading trading = tradingMapper.selectByPrimaryKey(tid);
				HxUsersAccountRepaymentRecord repaymentRecord = new HxUsersAccountRepaymentRecord();
				repaymentRecord.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				repaymentRecord.setLoanNo(loanNo);
				repaymentRecord.setInitTime(new Date());
                repaymentRecord.setType(2); // 2表示撤标
				repaymentRecord.setState(0);
				repaymentRecord.settId(trading.getId());
				repaymentRecord.setAmount(trading.getBuyMoney());

				dataResult = hxUsersAccountRepaymentRecordMapper.insertSelective(repaymentRecord);

				if (dataResult > 0) {
					LOGGER.info("{}产品撤标，借款编号{}，初始化交易号{}回款记录成功", logGroup, loanNo, trading.getId());
				} else {
					LOGGER.error("{}产品撤标，借款编号{}，初始化交易号{}回款记录失败", logGroup, loanNo, trading.getId());
				}

				// 4、账户流水状态变更
				String uid = (String) result.get("uid");
				UsersAccount account = usersAccountMapper.selectByUid(uid);
				AccountFlowExample accountFlowExmp = new AccountFlowExample();
                accountFlowExmp.createCriteria().andNumberEqualTo(trading.getBizCode())
                        .andTypeEqualTo(AppCons.ACCOUNT_FLOW_TYPE_FINANCIAL).andAIdEqualTo(account.getId())
                        .andTIdEqualTo(trading.getId());
				List<AccountFlow> accountFlowList = accountFlowMapper.selectByExample(accountFlowExmp);

				if (accountFlowList != null && accountFlowList.size() > 0) {
					AccountFlow accountFlow = accountFlowList.get(0);

					AccountFlow accountFlowRecord = new AccountFlow();
					accountFlowRecord.setId(accountFlow.getId());
                    accountFlowRecord.setStatus(AppCons.ACCOUNT_FLOW_BIDDING_CANCEL);
					dataResult = accountFlowMapper.updateByPrimaryKeySelective(accountFlowRecord);

					if (dataResult > 0) {
						LOGGER.info("{}银行主动撤标,更新account_flow数据成功。\n{}", logGroup, accountFlowRecord.toString());
					} else {
						LOGGER.error("{}银行主动撤标,更新account_flow数据失败。\n{}", logGroup, accountFlowRecord.toString());
					}
				}
				
			} else {
				errorCode = "100003";
				errorMsg = "未查询到指定有效用户交易信息";
				status = "1";
				LOGGER.info("{}处理银行主动撤标失败。未查询到指定用户信息：\n loanNo:{},  acName:{}", logGroup, loanNo, acName);
			}

		} else {
			errorCode = "100002";
			errorMsg = "解析数据失败";
			status = "1";
		}

		
		LOGGER.info("{}银行主动单笔撤销请求响应参数：channelFlow:{}, oldReqseqNo:{}, errorCode:{}, errorMsg:{}", logGroup,
				channelFlow, oldReqseqNo, errorCode, errorMsg);
		
		return hxInitiativeSingleRecall.responseInitiativeSingleRecall(channelFlow, oldReqseqNo, errorCode, errorMsg,
				status, logGroup);
	}

}
