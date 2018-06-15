package com.mrbt.lingmoney.admin.service.base;

import com.mrbt.lingmoney.model.AdminRole;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 
 * 权限
 * 
 * @author lihq
 * @date 2017年5月3日 下午2:42:22
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface AdminRoleService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            AdminRole
	 */
	void save(AdminRole vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            AdminRole
	 */
	void update(AdminRole vo);

	/**
	 * 分页查询
	 * 
	 * @param vo
	 *            AdminRole
	 * @param pageInfo
	 *            分页信息
	 * @return 数据返回
	 */
	PageInfo list(AdminRole vo, PageInfo pageInfo);

	/**
	 * 更改状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);
}
