package com.mrbt.lingmoney.admin.service.bank;

import java.util.Map;

import com.mrbt.lingmoney.bank.deal.dto.HxCompanyAdvPaymentReqDto;

/**
 * 单标公司垫付还款
 * @author YJQ
 *
 */
public interface CompanyAdvPaymentService {
	/**
	 * 请求单标公司垫付还款
	 * @author YJQ  2017年6月5日
	 * @param paymentDto	paymentDto
	 * @throws Exception	Exception
	 * @return map
	 */
	Map<String, Object> requestPayment(HxCompanyAdvPaymentReqDto paymentDto) throws Exception;

}
