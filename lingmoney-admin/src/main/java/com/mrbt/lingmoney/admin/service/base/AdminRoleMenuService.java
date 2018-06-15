package com.mrbt.lingmoney.admin.service.base;

import java.util.List;

import com.mrbt.lingmoney.model.AdminRoleMenu;

/**
 * 菜单权限关联
 *
 * @author lihq
 * @date 2017年5月3日 下午2:42:09
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AdminRoleMenuService {

	/**
	 * 根据roleId删除所有菜单
	 * 
	 * @param roleId
	 *            数据id
	 */
	void delete(int roleId);

	/**
	 * 根据权限id查找菜单项
	 * 
	 * @param roleId
	 *            数据id
	 * @return 数据返回
	 */
	List<AdminRoleMenu> listByRoleId(int roleId);

	/**
	 * 修改
	 * 
	 * @param roleId
	 *            roleId
	 * @param menuIds
	 *            menuIds
	 */
	void update(int roleId, String menuIds);
}
