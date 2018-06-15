package com.mrbt.lingmoney.admin.service.customerQuery;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * @author syb
 * @date 2017年9月5日 下午3:11:06
 * @version 1.0
 **/
public interface UserFootprintService {

	/**
	 * 根据条件查询用户足迹
	 * 
	 * @author syb
	 * @date 2017年9月5日 下午3:13:11
	 * @version 1.0
	 * @param account
	 *            用户账号，全模糊查询
	 * @param tel
	 *            用户手机号，全模糊查询
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页条数
	 * @param date
	 *            日期
	 * @return 数据返回
	 *
	 */
	PageInfo viewFootprint(String account, String tel, Integer page, Integer rows, String date);

}
