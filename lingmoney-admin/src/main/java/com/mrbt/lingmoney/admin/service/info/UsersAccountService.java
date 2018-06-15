package com.mrbt.lingmoney.admin.service.info;

import java.util.List;

import com.mrbt.lingmoney.model.Condition;
import com.mrbt.lingmoney.model.UsersAccount;

/**
 * 用户账户
 * 
 * @author lihq
 * @date 2017年5月18日 下午3:02:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface UsersAccountService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            UsersAccount
	 */
	void save(UsersAccount vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            UsersAccount
	 */
	void update(UsersAccount vo);

	/**
	 * 根据用户id查询账户信息
	 * 
	 * @param uId
	 *            uId
	 * @return 数据返回
	 */
	UsersAccount getUsersAccountByUid(String uId);

	/**
	 * 查询用户消息
	 * 
	 * @param condition
	 *            Condition
	 * @return 数据返回
	 */
	List<UsersAccount> findAdminMessage(Condition condition);
}
