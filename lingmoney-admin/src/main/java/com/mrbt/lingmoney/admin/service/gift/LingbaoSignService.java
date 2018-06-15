package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoSign;
import com.mrbt.lingmoney.model.LingbaoSignExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地签到属性
 *
 */
public interface LingbaoSignService {
	/**
	 * 根据主键查询我的领地签到属性
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地签到属性
	 */
	LingbaoSign findById(int id);

	/**
	 * 删除我的领地签到属性
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地签到属性
	 * 
	 * @param vo
	 *            我的领地签到属性
	 */
	void save(LingbaoSign vo);

	/**
	 * 更新我的领地签到属性,字段选择修改
	 * 
	 * @param vo
	 *            我的领地签到属性
	 */
	void update(LingbaoSign vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoSignExample example);
	
	/**
	 * 设置购物车限制个数
	 * 
	 * @param cartCount
	 *            数量
	 */
	void setCartCount(Integer cartCount);
	
	/**
	 *后台展示购物车限制个数
	 * 
	 * @return 分页实体类
	 */
	PageInfo showCartCount();
}
