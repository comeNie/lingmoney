package com.mrbt.lingmoney.admin.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.base.AdminRoleService;
import com.mrbt.lingmoney.mapper.AdminRoleMapper;
import com.mrbt.lingmoney.model.AdminRole;
import com.mrbt.lingmoney.model.AdminRoleExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 权限
 * 
 */
@Service
public class AdminRoleServiceImpl implements AdminRoleService {
	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Transactional
	@Override
	public void delete(int id) {
		adminRoleMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public void save(AdminRole vo) {
		adminRoleMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(AdminRole vo) {
		adminRoleMapper.updateByPrimaryKeySelective(vo);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			AdminRole record = adminRoleMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				adminRoleMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public PageInfo list(AdminRole vo, PageInfo pageInfo) {
		AdminRoleExample example = createAdminRoleExample(vo);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) adminRoleMapper.countByExample(example);
		List<AdminRole> list = adminRoleMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	/**
	 * 查询
	 * 
	 * @param vo
	 *            AdminRole
	 * @return 数据返回
	 */
	private AdminRoleExample createAdminRoleExample(AdminRole vo) {
		AdminRoleExample example = new AdminRoleExample();
		if (StringUtils.isNotBlank(vo.getRoleName())) {
			example.createCriteria().andRoleNameLike("%" + vo.getRoleName() + "%");
		}
		return example;
	}
}
