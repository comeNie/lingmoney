package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoGiftType;
import com.mrbt.lingmoney.model.LingbaoGiftTypeExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地礼品类型
 *
 */
public interface LingbaoGiftTypeService {
	/**
	 * 根据主键查询我的领地礼品类型
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地礼品类型
	 */
	LingbaoGiftType findById(int id);

	/**
	 * 删除我的领地礼品类型
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地礼品类型
	 * 
	 * @param vo
	 *            我的领地礼品类型
	 */
	void save(LingbaoGiftType vo);

	/**
	 * 更新我的领地礼品类型,字段选择修改
	 * 
	 * @param vo
	 *            我的领地礼品类型
	 */
	void update(LingbaoGiftType vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoGiftTypeExample example);

	/**
	 * 更改我的领地礼品类型状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(Integer id);

}
