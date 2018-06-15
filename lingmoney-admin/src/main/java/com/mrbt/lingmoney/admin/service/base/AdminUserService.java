package com.mrbt.lingmoney.admin.service.base;

import javax.servlet.http.HttpSession;

import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 用户
 *
 * @author lihq
 * @date 2017年5月3日 下午2:42:37
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AdminUserService {

	/**
	 * 删除权限
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            AdminUser
	 * @param pageInfo
	 *            分页信息
	 * @return 数据返回
	 */
	PageInfo list(AdminUser vo, PageInfo pageInfo);

	/**
	 * 查询总数
	 * 
	 * @param vo
	 *            AdminUser
	 * @param isUpdate
	 *            0添加 1修改
	 * @return 总数
	 */
	int listCount(AdminUser vo, Integer isUpdate);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            AdminUser
	 */
	void save(AdminUser vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            AdminUser
	 */
	void update(AdminUser vo);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);

	/**
	 * 登录
	 * 
	 * @param pageInfo
	 *            pageInfo
	 * @param loginName
	 *            loginName
	 * @param loginPsw
	 *            loginPsw
	 * @param session
	 *            session
	 */
	void login(PageInfo pageInfo, String loginName, String loginPsw, HttpSession session);
}
