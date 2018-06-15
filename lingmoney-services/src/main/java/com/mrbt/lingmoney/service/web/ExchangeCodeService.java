package com.mrbt.lingmoney.service.web;

import java.util.List;

import com.mrbt.lingmoney.model.ExchangeCode;

/**
 *@author syb
 *@date 2017年5月16日 下午3:47:47
 *@version 1.0
 *@description 礼品兑换
 **/
public interface ExchangeCodeService {

	/**
	 * 根据礼品代码和状态查询
	 * 
	 * @param code
	 *            兑换码
	 * @param status
	 *            状态 0,未使用；1，已使用
	 * @return 礼品兑换码记录
	 */
	List<ExchangeCode> findByCode(String code, int status);

	/**
	 * 兑换礼品
	 * @param code 兑换码
	 * @param uid 用户id
	 */
	void exchangeGift(String code, String uid);

}
