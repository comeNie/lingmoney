package com.mrbt.lingmoney.service.pay;

import com.mrbt.lingmoney.model.UsersBank;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
public interface UsersBankService {

	/**
	 * 将订单编号放入redis
	 * 
	 * @param dizNumber
	 *            dizNumber
	 * @param i
	 *            数据常量
	 * @return return
	 */
	boolean checkBankRedis(String dizNumber, Integer i);

	/**
	 * 清除身份证号redis
	 * 
	 * @param dizNumber
	 *            dizNumber
	 * @param i
	 *            数据常量
	 */
	void deleteBankRedis(String idCard, Integer type);

	/**
	 * 查询
	 * 
	 * @param parseInt
	 *            parseInt
	 * @return return
	 */
	UsersBank findByUserBankId(Integer parseInt);

}
