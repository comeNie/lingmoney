package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoSupplier;
import com.mrbt.lingmoney.model.LingbaoSupplierExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地供应商
 *
 */
public interface LingbaoSupplierService {
	/**
	 * 根据主键查询我的领地礼品供应商
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地礼品供应商
	 */
	LingbaoSupplier findById(int id);

	/**
	 * 删除我的领地礼品供应商
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地礼品供应商
	 * 
	 * @param vo
	 *            我的领地礼品供应商
	 */
	void save(LingbaoSupplier vo);

	/**
	 * 更新我的领地礼品供应商,字段选择修改
	 * 
	 * @param vo
	 *            我的领地礼品供应商
	 */
	void update(LingbaoSupplier vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoSupplierExample example);
	
	/**
	 * 更改我的领地礼品供应商状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);
}
