package com.mrbt.lingmoney.bank.deal;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.HxCompanyAdvPaymentReqDto;

/**
 * 华兴E账户- 单标公司垫付还款
 * 
 * @author YJQ
 *
 */
@Component
public class HxCompanyAdvPayment extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxCompanyAdvPayment.class);

	private String TRANSCODE = "OGW00073";

	public static void main(String[] args) {

		// 发起请求
		try {
			HxCompanyAdvPaymentReqDto paymentDto = new HxCompanyAdvPaymentReqDto();
			paymentDto.setAPPID("PC");

			new HxCompanyAdvPayment().requestPayment(paymentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 请求垫付还款
	 * 
	 * @author YJQ 2017年6月6日
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> requestPayment(HxCompanyAdvPaymentReqDto paymentDto) throws Exception {
		String logGroup = "\nHxCompanyAdvPayment_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求单标公司垫付还款");
		// 请求
		return xmlToMap(request(paymentDto, TRANSCODE, logGroup));
	}

}
