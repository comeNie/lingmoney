package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxSingleRewardOrCutAMelonService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.reward.HxSingleRewardOrCutAMelon;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 单笔奖励或分红
 *@author syb
 *@date 2017年6月7日 下午2:53:48
 *@version 1.0
 *@description 
 **/
@Service
public class HxSingleRewardOrCutAMelonServiceImpl implements HxSingleRewardOrCutAMelonService {
	
	private static final Logger LOGGER = LogManager.getLogger(HxSingleRewardOrCutAMelonServiceImpl.class);
	
	@Autowired
	private HxSingleRewardOrCutAMelon hxSingleRewardOrCutAMelon;
	@Autowired
	private HxAccountMapper hxAccountMapper;

	@Override
	public PageInfo requestSingleRewardOrCutAMelon(String appId, String hxAccountId, String amount,
			String remark, String logGroup) {
		PageInfo pi = new PageInfo();
		
		if (StringUtils.isEmpty(hxAccountId) || StringUtils.isEmpty(amount)) {
			pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg(ResultInfo.PARAM_MISS.getMsg());
			return pi;
		}
		
		HxAccount account = hxAccountMapper.selectByPrimaryKey(hxAccountId);
		if (account == null || account.getStatus() != 1) {
			pi.setCode(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getCode());
			pi.setMsg(ResultInfo.ACCOUNT_NUMBER_NON_EXIST.getMsg());
			return pi;
		}
		
		String acNo = account.getAcNo();
		String acName = account.getAcName();
		
		Document resDoc = hxSingleRewardOrCutAMelon.requestSingleRewardOrCutAMelon(appId, acNo, acName, amount, remark, logGroup);
		if (resDoc != null) {
			// 银行受理 只有errorCode =0时才返回，即正常响应时才返回
			if ("0".equals(resDoc.selectSingleNode("//errorCode").getText())) {
				LOGGER.info(logGroup + "银行受理成功.\n" + resDoc.asXML());
				
				Map<String, Object> map = HxBaseData.xmlToMap(resDoc);
				
				// 银行交易流水号
				String resjnlNo = (String) map.get("RESJNLNO");
				// 交易日期 YYYYMMDD
				String transDt = (String) map.get("TRANSDT");
				// 交易时间 HHMMSS
				String transTm = (String) map.get("TRANSTM");
				
				// TODO 具体业务功能未知,数据库处理待定
				
				LOGGER.info("银行单笔奖励或分红请求成功，返回信息：\n" + resDoc.asXML());
				pi.setCode(ResultInfo.SUCCESS.getCode());
				pi.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				String errorMsg = resDoc.selectSingleNode("//errorMsg").getText();
				pi.setCode(ResultInfo.BANK_NOT_SUCCESS.getCode());
				pi.setMsg(ResultInfo.BANK_NOT_SUCCESS.getMsg() + "处理失败，原因：" + errorMsg);
			}
			
		} else {
			pi.setCode(ResultInfo.BANK_REQUEST_NOT_SUCCESS.getCode());
			pi.setMsg(ResultInfo.BANK_REQUEST_NOT_SUCCESS.getMsg());
		}
		
		return pi;
	}

}
