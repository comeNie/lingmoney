package com.mrbt.lingmoney.service.users;

import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 *@author syb
 *@date 2017年5月9日 上午9:19:06
 *@version 1.0
 *@description 
 **/
public interface UsersPropertiesService {
	/**
	 * 根据用户id查询用户信息
	 * @param uid 用户id
	 * @return usersproperties
	 */
	UsersProperties queryByUid(String uid);

	/**
	 * 更新用户属性信息
	 * @param usersProperties 更新信息
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo update(UsersProperties usersProperties, String uid);
}
