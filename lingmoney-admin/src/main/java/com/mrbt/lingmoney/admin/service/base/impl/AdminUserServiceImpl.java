package com.mrbt.lingmoney.admin.service.base.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.admin.service.base.AdminMenuService;
import com.mrbt.lingmoney.admin.service.base.AdminUserService;
import com.mrbt.lingmoney.mapper.AdminUserMapper;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.model.AdminUserExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.MenuTree;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 用户
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserMapper adminUserMapper;
	@Autowired
	private AdminMenuService adminMenuService;

	@Transactional
	@Override
	public void delete(int id) {
		adminUserMapper.deleteByPrimaryKey(id);
	}

	@Transactional
	@Override
	public boolean changeStatus(Integer id) {
		boolean flag = false;
		if (id != null && id > 0) {
			AdminUser record = adminUserMapper.selectByPrimaryKey(id);
			if (record != null) {
				if (record.getStatus() == null || record.getStatus() == 0) {
					record.setStatus(1);
				} else {
					record.setStatus(0);
				}
				adminUserMapper.updateByPrimaryKeySelective(record);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public PageInfo list(AdminUser vo, PageInfo pageInfo) {
		AdminUserExample example = createAdminUserExample(vo, null);
		example.setLimitStart(pageInfo.getFrom());
		example.setLimitEnd(pageInfo.getSize());

		int resSize = (int) adminUserMapper.countByExample(example);
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		pageInfo.setRows(list);
		pageInfo.setTotal(resSize);
		return pageInfo;
	}

	@Override
	public int listCount(AdminUser vo, Integer isUpdate) {
		return (int) adminUserMapper.countByExample(createAdminUserExample(vo, isUpdate));
	}

	@Transactional
	@Override
	public void save(AdminUser vo) {
		adminUserMapper.insert(vo);
	}

	@Transactional
	@Override
	public void update(AdminUser vo) {
		adminUserMapper.updateByPrimaryKeySelective(vo);
	}

	/**
	 * 创建
	 * 
	 * @param vo
	 *            AdminUser
	 * @param isUpdate
	 *            isUpdate
	 * @return 数据返回
	 */
	private AdminUserExample createAdminUserExample(AdminUser vo, Integer isUpdate) {
		AdminUserExample example = new AdminUserExample();
		AdminUserExample.Criteria cri = example.createCriteria();
		if (isUpdate != null && isUpdate == 0) { // add
			cri.andLoginNameEqualTo(vo.getLoginName());
		} else if (isUpdate != null && isUpdate == 1) { // update
			cri.andLoginNameEqualTo(vo.getLoginName());
			cri.andLoginNameNotEqualTo(adminUserMapper.selectByPrimaryKey(vo.getId()).getLoginName());
		} else {
			if (StringUtils.isNotBlank(vo.getName())) {
				cri.andNameLike("%" + vo.getName() + "%");
			}
			if (StringUtils.isNotBlank(vo.getLoginName())) {
				cri.andLoginNameEqualTo(vo.getLoginName());
			}
			if (StringUtils.isNotBlank(vo.getLoginPsw())) {
				cri.andLoginPswEqualTo(vo.getLoginPsw());
			}
			if (vo.getStatus() != null) {
				cri.andStatusEqualTo(vo.getStatus());
			}
		}

		return example;
	}

	@Transactional
	@Override
	public void login(PageInfo pageInfo, String loginName, String loginPsw, HttpSession session) {
		// 根据登录名和密码判断用户是否存在
		AdminUser user = new AdminUser();
		user.setLoginName(loginName);
		user.setLoginPsw(MD5Utils.MD5(loginPsw));
		user.setStatus(1); // 状态为可用

		AdminUserExample example = createAdminUserExample(user, null);
		example.setLimitStart(0);
		example.setLimitEnd(1);
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		// 处理登录用户放入session
		if (list != null && list.size() > 0) {
			user = list.get(0);
			user.setLastTime(new Date());
			adminUserMapper.updateByPrimaryKeySelective(user);

			UserSession currentUser = new UserSession();
			currentUser.setUser(user);

			MenuTree tree = null;
			// 默认的id为1的admin用户取所有菜单
			if (user.getId().intValue() != 1) {
				tree = adminMenuService.listAllTreeByRole(user.getRoleId());
			} else {
				tree = adminMenuService.listAllTree();
			}
			if (tree != null) {
				HashMap<String, Object> menuMap = new HashMap<String, Object>();
				menuMap.put("menus", tree.getChildren());
				currentUser.setMenuMap(menuMap);
			}
			session.setAttribute("__changed__", "");
			session.setAttribute(AppCons.SESSION_USER, currentUser);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		} else {
			pageInfo.setCode(ResultParame.ResultInfo.DATA_EXISTED.getCode());
			pageInfo.setMsg("用户名/密码错误或该用户已禁用");
		}
	}
}
