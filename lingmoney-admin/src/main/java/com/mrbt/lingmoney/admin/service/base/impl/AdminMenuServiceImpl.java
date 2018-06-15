package com.mrbt.lingmoney.admin.service.base.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.base.AdminMenuService;
import com.mrbt.lingmoney.mapper.AdminMenuMapper;
import com.mrbt.lingmoney.model.AdminMenu;
import com.mrbt.lingmoney.model.AdminMenuExample;
import com.mrbt.lingmoney.utils.MenuTree;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 菜单
 *
 */
@Service
public class AdminMenuServiceImpl implements AdminMenuService {
	@Autowired
	private AdminMenuMapper adminMenuMapper;

	@Transactional
	@Override
	public void delete(int id) {
		adminMenuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public MenuTree listAllTree() {
		AdminMenuExample example = new AdminMenuExample();
		example.createCriteria().andStatusEqualTo(1);
		example.setOrderByClause("id,seq");
		List<AdminMenu> menuList = adminMenuMapper.selectByExample(example);
		return createTree(menuList);
	}

	@Override
	public MenuTree listAllTreeByRole(int roleId) {
		return createTree(adminMenuMapper.selectByRole(roleId));
	}

	@Override
	public MenuTree createTree(List<AdminMenu> menuList) {
		MenuTree tmpTree;
		MenuTree root = null;
		HashMap<Integer, MenuTree> tmpMap = new HashMap<Integer, MenuTree>();
		for (AdminMenu tmpMenu : menuList) {
			// 创建一个tree节点
			tmpTree = new MenuTree(tmpMenu.getId(), tmpMenu.getText(), null, tmpMenu.getIcon(), tmpMenu.getUrl());
			tmpMap.put(tmpTree.getId(), tmpTree);
			// 如果当前menu的pid不等于空，并且在Map中能查到pid对应的节点，则添加到次节点的children中
			if (tmpMenu.getPid() != null) {
				if (tmpMap.containsKey(tmpMenu.getPid().intValue())) {
					tmpMap.get(tmpMenu.getPid().intValue()).getChildren().add(tmpTree);
				}
			} else {
				root = tmpTree;
			}
		}
		tmpMap.clear();
		return root;
	}

	@Transactional
	@Override
	public void save(AdminMenu vo) {
		adminMenuMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(AdminMenu vo) {
		adminMenuMapper.updateByPrimaryKeySelective(vo);
	}

	@Override
	public PageInfo listByPid(int pid, PageInfo pageInfo) {
		AdminMenuExample example = new AdminMenuExample();
		example.createCriteria().andPidEqualTo(pid);
		example.setOrderByClause("seq");
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) adminMenuMapper.countByExample(example);
		List<AdminMenu> list = adminMenuMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			AdminMenu record = adminMenuMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				adminMenuMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}
}
