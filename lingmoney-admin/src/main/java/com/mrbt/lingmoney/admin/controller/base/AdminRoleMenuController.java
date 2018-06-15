package com.mrbt.lingmoney.admin.controller.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.lingmoney.admin.service.base.AdminRoleMenuService;
import com.mrbt.lingmoney.model.AdminRoleMenu;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 角色权限
 * 
 * @author lihq
 * @date 2017年5月3日 下午4:44:29
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@RestController
@RequestMapping("base/role_menu")
public class AdminRoleMenuController {
	private Logger log = MyUtils.getLogger(AdminRoleMenuController.class);
	
	@Autowired
	private AdminRoleMenuService adminRoleMenuService;

	/**
	 * 根据角色id查询
	 * @param roleId	角色ID
	 * @return 返回结果
	 */
	@RequestMapping("listByRoleId")
	public List<AdminRoleMenu> listByRoleId(Integer roleId) {
		log.info("/base/role_menu/listByRoleId");
		if (roleId != null && roleId > 0) {
			return adminRoleMenuService.listByRoleId(roleId);
		} else {
			return new ArrayList<AdminRoleMenu>();
		}
	}

	/**
	 * 保存分配权限
	 * 
	 * @param roleId	角色ID
	 * @param menuIds	菜单IDS	
	 * @return 返回结果
	 */
	@RequestMapping("updateRoleMenu")
	public Object updateRoleMenu(Integer roleId, String menuIds) {
		log.info("/base/role_menu/updateRoleMenu");
		PageInfo pageInfo = new PageInfo();
		try {
			if (roleId != null && roleId > 0) {
				adminRoleMenuService.update(roleId, menuIds);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
