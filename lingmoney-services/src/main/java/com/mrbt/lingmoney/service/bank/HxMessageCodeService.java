package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
public interface HxMessageCodeService {

	/**
	 * 获取验证码
	 * @param appId 
	 * @param uId 用户id
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo getHxMessageCode(String appId, String uId) throws Exception;


}
