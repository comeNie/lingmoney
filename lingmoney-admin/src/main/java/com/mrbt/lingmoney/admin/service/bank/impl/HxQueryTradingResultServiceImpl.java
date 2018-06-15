package com.mrbt.lingmoney.admin.service.bank.impl;

import java.util.Map;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.admin.service.bank.HxQueryTradingResultService;
import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.HxQueryTradingResult;

/**
 *@author syb
 *@date 2017年8月14日 下午2:22:08
 *@version 1.0
 *@description 
 **/
@Service
public class HxQueryTradingResultServiceImpl implements HxQueryTradingResultService {

	@Autowired
	private HxQueryTradingResult hxQueryTradingResult;

	@Override
	public Map<String, Object> queryHxTradingResult(String channelFlow, String tranType, String logGroup) {

		if (StringUtils.isEmpty(channelFlow)) {
			return null;
		}
		
		Document resDoc = hxQueryTradingResult.requestQueryTradingResult(channelFlow, tranType, logGroup);

		if (resDoc != null) {
			return HxBaseData.xmlToMap(resDoc);
		}

		return null;
	}

}
