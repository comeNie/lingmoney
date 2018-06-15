package com.mrbt.lingmoney.admin.utils;

import java.io.Serializable;

import com.mrbt.lingmoney.admin.service.smsSend.SmsService;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.utils.MyUtils;

/**
 * 
 * @author Administrator
 *
 */
public class ThreadPoolSmsSend implements Serializable {

	private static final long serialVersionUID = 0;

	private SmsMessage smsMessage;

	public ThreadPoolSmsSend(SmsMessage message) {

		this.smsMessage = message;

	}

	/**
	 * 短信发送
	 * @throws Exception	Exception
	 */
	public synchronized void call() throws Exception {
		try {
			MyUtils.getSmsSendLogger().info("smsService统一发送短信：" + smsMessage.getTelephone() + ":" + smsMessage.getContent());

			SmsService smsService = (SmsService) SpringApplicationContextHolder.getSpringBean("smsService");

			if (smsService != null) {
				smsService.sendSms(smsMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
