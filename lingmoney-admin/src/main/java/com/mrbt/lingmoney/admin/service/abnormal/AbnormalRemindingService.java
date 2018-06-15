package com.mrbt.lingmoney.admin.service.abnormal;

import java.util.Date;

public interface AbnormalRemindingService {

	/**
	 * 充值异常邮件提醒
	 * @param logGroup
	 * @param queryDate 
	 * @throws Exception
	 */
	void rechargeLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception ;

	/**
	 * 提现异常邮件提醒
	 * @param logGroup
	 * @throws Exception
	 */
	void putforwardLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception;

	/**
	 * 开户异常邮件提醒
	 * @param string
	 * @throws Exception 
	 */
	void openAccountLossProcessing(String string, StringBuffer emailContent, Date queryDate) throws Exception;

	/**
	 * 绑卡异常邮件提醒
	 * @param logGroup
	 * @throws Exception 
	 */
	void bindCardLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception;

	/**
	 * 交易异常邮件提醒
	 * @param logGroup
	 * @throws Exception 
	 */
	void tradingLossProcessing(String logGroup, StringBuffer emailContent, Date queryDate) throws Exception;

	/**
	 * 发送邮件
	 * @param string
	 */
	void sendMail(String string);

}
