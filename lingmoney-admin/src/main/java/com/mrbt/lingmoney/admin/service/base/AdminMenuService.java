package com.mrbt.lingmoney.admin.service.base;

import java.util.List;

import com.mrbt.lingmoney.model.AdminMenu;
import com.mrbt.lingmoney.utils.MenuTree;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 菜单
 *
 * @author lihq
 * @date 2017年5月3日 下午2:41:54
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AdminMenuService {
	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 树形结构生成的json图
	 * 
	 * @return 数据返回
	 */
	MenuTree listAllTree();

	/**
	 * 根据roleId查询树
	 * 
	 * @param roleId
	 *            roleId
	 * @return 数据返回
	 */
	MenuTree listAllTreeByRole(int roleId);

	/**
	 * 生成树对象
	 * 
	 * @param menuList
	 *            menuList
	 * @return 数据返回
	 */
	MenuTree createTree(List<AdminMenu> menuList);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            AdminMenu
	 */
	void save(AdminMenu vo);

	/**
	 * 修改菜单
	 * 
	 * @description
	 * @param vo
	 *            AdminMenu
	 */
	void update(AdminMenu vo);

	/**
	 * 根据pid查询节点
	 * 
	 * @param pid
	 *            pid
	 * @param pageInfo
	 *            分页信息
	 * @return 数据返回
	 */
	PageInfo listByPid(int pid, PageInfo pageInfo);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);
}
