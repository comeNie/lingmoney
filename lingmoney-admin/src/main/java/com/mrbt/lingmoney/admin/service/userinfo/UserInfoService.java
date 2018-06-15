package com.mrbt.lingmoney.admin.service.userinfo;

import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年4月10日
 */
public interface UserInfoService {

	/**
	 * 查询用户信息
	 * 
	 * @param uid 用户id
	 * @param telephone 电话
	 * @param name 姓名
	 * @return 用户信息
	 */
	PageInfo findUserInfoByParams(String uid, String telephone, String name);

	/**
	 * 查询用户账户流水
	 * 
	 * @param aid 用户账户id
	 * @return 账户流水信息
	 */
	PageInfo findAccountFlowByAId(Integer aid);

}
