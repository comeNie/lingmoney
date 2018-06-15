package com.mrbt.lingmoney.admin.service.festival;

import com.mrbt.lingmoney.model.BayWindow;
import com.mrbt.lingmoney.model.BayWindowExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 活动飘窗设置
 *
 */
public interface BayWindowService {
	/**
	 * 根据主键查询活动飘窗
	 * 
	 * @param id
	 *            主键
	 * @return 活动飘窗
	 */
	BayWindow findById(int id);

	/**
	 * 删除活动飘窗
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存活动飘窗
	 * 
	 * @param vo
	 *            活动飘窗
	 */
	void save(BayWindow vo);

	/**
	 * 更新活动飘窗,字段选择修改
	 * 
	 * @param vo
	 *            活动飘窗
	 */
	void update(BayWindow vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(BayWindowExample example);

	/**
	 * 更改活动飘窗状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(Integer id);
}
