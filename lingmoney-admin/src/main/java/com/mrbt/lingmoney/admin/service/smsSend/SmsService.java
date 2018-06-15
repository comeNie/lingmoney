package com.mrbt.lingmoney.admin.service.smsSend;

import com.mrbt.lingmoney.model.trading.SmsMessage;

/**
 * 
 * 信息
 *
 */
public interface SmsService {

	/**
	 * 发送信息
	 * 
	 * @param message
	 *            SmsMessage
	 */
	void sendSms(SmsMessage message);

	/**
	 * 保存信息
	 * 
	 * @param message
	 *            SmsMessage
	 * @return 数据返回
	 */
	boolean saveSmsMessage(SmsMessage message);

	/**
	 * 获取信息
	 * 
	 * @return 数据返回
	 */
	SmsMessage takeSmsMessage();

}
