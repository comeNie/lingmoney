package com.mrbt.lingmoney.bank.deal;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.HxDailyReconciliateReqDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;

/**
 * 华兴E账户- 日终对账
 * 
 * @author YJQ
 *
 */
@Component
public class HxDailyReconciliate extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxDailyReconciliate.class);

	private String TRANSCODE = "OGW00077";

	public static void main(String[] args) {

		// 发起请求
		try {
			HxDailyReconciliateReqDto paymentDto = new HxDailyReconciliateReqDto();
			paymentDto.setAPPID("PC");

			// new HxDailyReconciliate().requestPayment(paymentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 请求日终对账
	 * 
	 * @author YJQ 2017年6月6日
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> requestDaily(HxDailyReconciliateReqDto requestDto) throws Exception {
		String logGroup = "\nHxDailyReconciliate_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求日终对账");
		// 请求
		Document resDoc = request(requestDto, TRANSCODE, logGroup);
		Map<String, Object> resMap = xmlToMap(resDoc);
		String returnStatus = resMap.get("RETURN_STATUS").toString();
		// L 正在处理中 F不可对账 S 可对账
		switch (returnStatus) {
		case "L":
			throw new ResponseInfoException("正在处理中", 1005);
		case "F":
			throw new ResponseInfoException("不可对账，原因为：" + resMap.get("ERRORMSG"), 1005);
		}
		return resMap;
	}
}
