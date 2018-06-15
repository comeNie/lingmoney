package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 *
 *@author syb
 *@date 2017年9月8日 下午2:28:52
 *@version 1.0
 **/
public interface HxUsersAccountRepaymentRecordService {
	/**
	 * 自定义查询
	 * 
	 * @author syb
	 * @date 2017年9月8日 下午2:30:36
	 * @version 1.0
	 * @param tel 手机号
	 * @param name 姓名
	 * @param initTime  操作时间
	 * @param page  状态
	 * @param rows  状态
	 * @param status  状态
	 * @return	return
	 *
	 */
	PageInfo queryWithSelfCondition(String tel, String name, String initTime, Integer page, Integer rows, Integer status);

	/**
	 * 手动恢复 用户余额增加，冻结金额减少
	 * 
	 * @author syb
	 * @date 2017年9月8日 下午4:25:56
	 * @version 1.0
	 * @param id
	 *            回款 记录id
	 * @return	return
	 *
	 */
	PageInfo manualRecoverPayment(String id);
}
