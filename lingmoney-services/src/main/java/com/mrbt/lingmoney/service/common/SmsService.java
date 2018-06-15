package com.mrbt.lingmoney.service.common;

import com.mrbt.lingmoney.model.trading.SmsMessage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 短信验证码
 * @author Administrator
 *
 * 验证码是通过手机号为key存入redis List中的，判断发送次数是按照list的数量来判断，验证验证码是取最后一个验证码，并且验证时间	
 */
public interface SmsService {

	/**
	 * 
	 * 注册，获取验证短信：先验证图形验证码，手机号以及当日获取短信次数是否超标，如果无误，保存短信内容到redis队列，保存短信验证码到redis队列
	 * @author syb
	 * @date 2017年8月23日 下午4:16:42
	 * @version 1.0
	 * @param phone 手机号
	 * @param pageInfo pageinfo
	 * @param picKey 图形验证码key
	 * @param code 图形验证码
	 * @return pageinfo
	 *
	 */
	PageInfo saveSendRegister(String phone, PageInfo pageInfo, String picKey, String code);

	/**
	 * 
	 * @description 验证注册短信验证码
	 * @author syb
	 * @date 2017年8月23日 下午4:26:40
	 * @version 1.0
	 * @param phone 手机号
	 * @param verifyCode 验证码
	 * @param pageInfo pageinfo
	 * @return pageinfo
	 *
	 */
	PageInfo verifyRegisterCode(String phone, String verifyCode, PageInfo pageInfo);

	/**
	 * 发送忘记密码验证码
	 * 
	 * @param phone 手机号
	 * @param pageInfo pageinfo
	 * @param picKey key
	 * @param code 验证码
	 * @return pageinfo
	 *
	 */
	PageInfo saveSendModiPw(String phone, PageInfo pageInfo, String picKey, String code);

	/**
	 * 发送修改用户手机号验证码
	 * 
	 * @param targetPhone 手机号
	 * @param key key
	 * @param pageInfo pageinfo
	 * @param picKey 图片key
	 * @param picCode 验证码
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo sendModiPhone(String targetPhone, String key, PageInfo pageInfo, String picKey, String picCode) throws Exception;

	/**
	 * 查询短信验证码
	 * @param phone 手机号
	 * @return pageinfo
	 */
	PageInfo querySmsVerify(String phone);

	/**
	 * 验证短信验证码
	 * 
	 * @param key key
	 * @param code 验证码
	 * @return boolean
	 */
	boolean validateCode(String key, String code);

	/**
	 * 随心取赎回 短信通知
	 * @param message 短信
	 * @return boolean
	 */
	boolean saveSellSmsMessage(SmsMessage message);

	/**
	 * 验证修改手机号时的原手机号
	 * @author YJQ  2017年6月1日
	 * @param oldKey 原key
	 * @param phone 手机号
	 * @param pageInfo pageinfo
	 * @param picKey key
	 * @param picCode 验证码
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo sendModiPhoneVerifyOld(String oldKey, String phone, PageInfo pageInfo, String picKey, String picCode) throws Exception;

	/**
	 * 保存信息到redis
	 * 
	 * @param message
	 *            短信
	 * @return boolean
	 */
	boolean saveSmsMessage(SmsMessage message);
}
