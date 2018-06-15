package com.mrbt.lingmoney.admin.service.smsSend.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.controller.task.ExecSmsSend;
import com.mrbt.lingmoney.admin.service.smsSend.SmsService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.SendSMSUtils;

/**
 * 
 * SmsService实现类
 *
 */
@Service
public class SmsServiceImpl implements SmsService {

	private static String SMSMESSAGE_KEY_HEAD = "smsmessage:data:";
	
	private static final Logger LOG = LogManager.getLogger(SmsServiceImpl.class);

	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SendSMSUtils sendSMSUtils;

	/**
	 * 发送信息
	 */
	@Override
	public void sendSms(SmsMessage message) {
		
		LOG.info("smsService统一发送短信：" + message.getTelephone() + ":" + message.getContent());

		sendSMSUtils.sendSMS(message.getTelephone(), message.getContent());

	}

	/**
	 * 保存信息
	 * 
	 * @param message
	 *            SmsMessage
	 * @return 数据返回
	 */
	@Override
	public boolean saveSmsMessage(SmsMessage message) {

		boolean result = true;

		String key = SMSMESSAGE_KEY_HEAD;

		try {
			redisDao.setList(key, message);
		} catch (Exception e) {

			result = false;

		}

		return result;
	}

	/**
	 * 获取信息
	 * 
	 * @return 数据返回
	 */
	@Override
	public SmsMessage takeSmsMessage() {

		SmsMessage result = null;

		String key = SMSMESSAGE_KEY_HEAD;
		try {
			result = (SmsMessage) redisDao.getListObject(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
