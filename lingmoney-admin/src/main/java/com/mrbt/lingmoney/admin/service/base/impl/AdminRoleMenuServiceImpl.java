package com.mrbt.lingmoney.admin.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.base.AdminRoleMenuService;
import com.mrbt.lingmoney.mapper.AdminRoleMenuMapper;
import com.mrbt.lingmoney.model.AdminRoleMenu;
import com.mrbt.lingmoney.model.AdminRoleMenuExample;

/**
 * 菜单权限关联
 *
 */
@Service
public class AdminRoleMenuServiceImpl implements AdminRoleMenuService {
	@Autowired
	private AdminRoleMenuMapper adminRoleMenuMapper;

	@Transactional
	@Override
	public void delete(int roleId) {
		AdminRoleMenuExample example = new AdminRoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		adminRoleMenuMapper.deleteByExample(example);
	}

	@Override
	public List<AdminRoleMenu> listByRoleId(int roleId) {
		AdminRoleMenuExample example = new AdminRoleMenuExample();
		example.createCriteria().andRoleIdEqualTo(roleId);
		return adminRoleMenuMapper.selectByExample(example);
	}

	@Transactional
	@Override
	public void update(int roleId, String menuIds) {
		AdminRoleMenuExample example = new AdminRoleMenuExample();
		List<Integer> menuList = new ArrayList<Integer>();
		for (String tmp : menuIds.split(",")) {
			if (StringUtils.isNotBlank(tmp) && NumberUtils.isNumber(tmp)) {
				menuList.add(NumberUtils.toInt(tmp));
			}
		}
		example.createCriteria().andRoleIdEqualTo(roleId);
		adminRoleMenuMapper.deleteByExample(example);
		adminRoleMenuMapper.insertByRoleIdMenuIds(roleId, menuList);
	}

}
