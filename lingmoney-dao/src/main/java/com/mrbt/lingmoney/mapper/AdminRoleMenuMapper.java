package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.AdminRoleMenu;
import com.mrbt.lingmoney.model.AdminRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminRoleMenuMapper {
	/**
	 * 根据指定的条件获取数据库记录数,admin_role_menu
	 *
	 * @param example
	 */
	long countByExample(AdminRoleMenuExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,admin_role_menu
	 *
	 * @param example
	 */
	int deleteByExample(AdminRoleMenuExample example);

	/**
	 * 根据主键删除数据库的记录,admin_role_menu
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,admin_role_menu
	 *
	 * @param record
	 */
	int insert(AdminRoleMenu record);

	/**
	 * 动态字段,写入数据库记录,admin_role_menu
	 *
	 * @param record
	 */
	int insertSelective(AdminRoleMenu record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,admin_role_menu
	 *
	 * @param example
	 */
	List<AdminRoleMenu> selectByExample(AdminRoleMenuExample example);

	/**
	 * 根据指定主键获取一条数据库记录,admin_role_menu
	 *
	 * @param id
	 */
	AdminRoleMenu selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,admin_role_menu
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") AdminRoleMenu record, @Param("example") AdminRoleMenuExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,admin_role_menu
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") AdminRoleMenu record, @Param("example") AdminRoleMenuExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,admin_role_menu
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(AdminRoleMenu record);

	/**
	 * 根据主键来更新符合条件的数据库记录,admin_role_menu
	 *
	 * @param record
	 */
	int updateByPrimaryKey(AdminRoleMenu record);

	/**
	 * 添加权限菜单
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertByRoleIdMenuIds(@Param("roleId") int roleId, @Param("menuIds") List<Integer> menuIds);
}