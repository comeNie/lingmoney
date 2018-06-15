package com.mrbt.lingmoney.admin.service.pay;

import java.util.List;

import com.mrbt.lingmoney.model.PaymentPartition;

/**
 * @author luox
 * @Date 2017年5月12日
 */
public interface PaymentConfServices {

	/**
	 * 查询需要确认兑付的数据
	 * 
	 * @return 数据返回
	 */
	List<PaymentPartition> paymentList();

	/**
	 * 确认兑付
	 * 
	 * @param idStr
	 *            idStr
	 * @return 数据返回
	 */
	Integer paymentSubmission(String idStr);

}
