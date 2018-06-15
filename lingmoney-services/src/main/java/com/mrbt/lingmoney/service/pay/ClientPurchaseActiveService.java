package com.mrbt.lingmoney.service.pay;

import org.springframework.ui.ModelMap;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月3日
 */
public interface ClientPurchaseActiveService {

	/**
	 * 手机端购买接口
	 * 
	 * @param paymentClientText
	 *            paymentClientText
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	String active(String paymentClientText, ModelMap modelMap);

	/**
	 * 选择银行卡支付第一步
	 * 
	 * @param paymentClientText
	 *            paymentClientText
	 * @param usersBankId
	 *            usersBankId
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	String selectQuickPayMentFirst(String paymentClientText, String usersBankId, ModelMap modelMap);

	/**
	 * 快捷支付第二步，发验证码
	 * 
	 * @param paymentClientText
	 *            paymentClientText
	 * @param usersBankId
	 *            usersBankId
	 * @param verifyCode
	 *            verifyCode
	 * @param status
	 *            status
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	String selectQuickPayMentSecond(String paymentClientText, String usersBankId, String verifyCode, String status,
			ModelMap modelMap);

}
