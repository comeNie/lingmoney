package com.mrbt.lingmoney.admin.service.bank.impl;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.admin.service.bank.BankAccountBalanceService;
import com.mrbt.lingmoney.bank.eaccount.HxAccountBanlance;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * @author luox
 * @Date 2017年6月5日
 */
@Service
public class BankAccountBalanceServiceImpl implements BankAccountBalanceService {

	@Autowired
	private HxAccountBanlance hxAccountBanlance;

	@Override
	public JSONObject findUserBanlance(String appId, String acName, String acNo) throws Exception {
		JSONObject json = new JSONObject();
		// 获取响应
		Document result = hxAccountBanlance.requestAccountBalance(appId, acName, acNo);
		if (result == null || !"0".equals(result.selectSingleNode("//errorCode").getText())) {
			throw new PageInfoException("查询失败", ResultParame.ResultInfo.RETURN_EMPTY_DATA.getCode());
		}

		json.put("availableBalance", result.selectSingleNode("//AVAILABLEBAL").getText());
		json.put("ACCTBAL", result.selectSingleNode("//ACCTBAL").getText());
		json.put("FROZBL", result.selectSingleNode("//FROZBL").getText());

		return json;
	}

	@Override
	public String checkAccountBalance(String appId, String amount, String acNo) throws Exception {
		// 获取响应
		Document result = hxAccountBanlance.checkAccountBalance(appId, amount, acNo);
		if (result == null || !"0".equals(result.selectSingleNode("//errorCode").getText())) {
			throw new PageInfoException("查询失败", ResultParame.ResultInfo.RETURN_EMPTY_DATA.getCode());
		}
		String code = result.selectSingleNode("//RESFLAG").getText();

		/*
		 * 000000 余额充足 540026 余额为零 540009 余额不足 540008 账户没有关联 OGW001 账户与户名不匹配
		 */
	
		
		return code;
	}
}
