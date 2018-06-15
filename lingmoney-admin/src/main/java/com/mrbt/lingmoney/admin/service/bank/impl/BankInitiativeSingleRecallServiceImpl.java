package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.bank.BankInitiativeSingleRecallService;
import com.mrbt.lingmoney.admin.service.bank.SingleCancelBiddingService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.recall.HxInitiativeSingleRecall;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxBiddingCancelRecordMapper;
import com.mrbt.lingmoney.mapper.HxUsersAccountRepaymentRecordMapper;
import com.mrbt.lingmoney.mapper.TradingFixInfoMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.HxBiddingCancelRecord;

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
	@Autowired
	private HxBiddingCancelRecordMapper hxBiddingCancelRecordMapper;
	@Autowired
	private SingleCancelBiddingService singleCancelBiddingService;

	@Override
	public String initiativeSingleRecall(String message, String logGroup) {
		
		String errorCode = "0"; // 错误码 默认0：成功
		String errorMsg = "success"; // 错误信息，默认success
		String oldReqseqNo = ""; // 原投标流水号
		String channelFlow = ""; // 渠道流水号
		String status = "0"; // 业务状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定
		
		if (StringUtils.isNotBlank(message)) {
			Document doc = HxBaseData.analysisAsyncMsg(message, logGroup);
			
			if (doc != null) {
				LOGGER.info("{}解密后数据为{}", logGroup, doc.asXML());

				Map<String, Object> map = HxBaseData.xmlToMap(doc);
				
				oldReqseqNo = (String) map.get("OLDREQSEQNO");
				channelFlow = (String) map.get("channelFlow");
				
				String loanNo = (String) map.get("LOANNO");
				String acNo = (String) map.get("ACNO");
				String acName = (String) map.get("ACNAME");
				String cancelReason = (String) map.get("CANCELREASON");

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
					String uid = (String) result.get("uid");
					
					// 插入一条撤标记录
					HxBiddingCancelRecord hxBiddingCancelRecord = new HxBiddingCancelRecord();
					hxBiddingCancelRecord.setAppId("PC");
					hxBiddingCancelRecord.setCancelReason(cancelReason);
					hxBiddingCancelRecord.setChannelFlow(channelFlow);
					hxBiddingCancelRecord.setRequestDate(new Date());
					hxBiddingCancelRecord.setStatus(1);
					hxBiddingCancelRecord.setType(0);
					int i = hxBiddingCancelRecordMapper.insertSelective(hxBiddingCancelRecord);

					if (i > 0) {
						LOGGER.info("{}银行主动撤标，保存撤标记录成功。", logGroup);
					} else {
						LOGGER.error("{}银行主动撤标，保存撤标记录失败。{}", logGroup, hxBiddingCancelRecord.toString());
					}

					try {
						singleCancelBiddingService.handelCancelBidding(logGroup, loanNo, uid, tid);
					} catch (Exception e) {
						e.printStackTrace();
						LOGGER.error("{}银行主动撤标，更新对应数据失败", logGroup);
					}

				} else {
					errorCode = "100003";
					errorMsg = "未查询到指定有效用户交易信息";
					status = "1";
					LOGGER.info("{}处理银行主动撤标失败。未查询到指定用户信息：\n loanNo:{},  acName:{}", logGroup, loanNo,
							acName);
				}
				
			} else {
				errorCode = "100002";
				errorMsg = "解析数据失败";
				status = "1";
			}

		} else {
			errorCode = "100001";
			errorMsg = "请求信息为空";
			status = "1";
		}
		
		LOGGER.info("{}银行主动单笔撤销请求响应参数：channelFlow:{}, oldReqseqNo:{}, errorCode:{}, errorMsg:{}", logGroup,
				channelFlow, oldReqseqNo, errorCode, errorMsg);
		
		return hxInitiativeSingleRecall.responseInitiativeSingleRecall(channelFlow, oldReqseqNo, errorCode, errorMsg,
				status, logGroup);
	}

}
