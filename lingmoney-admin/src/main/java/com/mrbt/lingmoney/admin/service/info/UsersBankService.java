package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户银行卡
 * 
 * @author lihq
 * @date 2017年5月18日 下午3:05:06
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface UsersBankService {

	/**
	 * 修改
	 * 
	 * @param vo
	 *            UsersBank
	 */
	void update(UsersBank vo);

	/**
	 * 通过条件查询用户绑卡信息
	 * 
	 * @param pageInfo
	 *            pageInfo
	 */
	void findDataGrid(PageInfo pageInfo);

}
