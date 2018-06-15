package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.LingbaoLotteryTypeExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地活动类型
 *
 */
public interface LingbaoLotteryTypeService {
	/**
	 * 根据主键查询我的领地活动类型
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地活动类型
	 */
	LingbaoLotteryType findById(int id);

	/**
	 * 删除我的领地活动类型
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地活动类型
	 * 
	 * @param vo
	 *            我的领地活动类型
	 */
	void save(LingbaoLotteryType vo);

	/**
	 * 更新我的领地活动类型,字段选择修改
	 * 
	 * @param vo
	 *            我的领地活动类型
	 */
	void updateByPrimaryKeySelective(LingbaoLotteryType vo);
	/**
	 * 更新我的领地活动类型,字段全部修改
	 * 
	 * @param vo
	 *            我的领地活动类型
	 */
	void updateByPrimaryKey(LingbaoLotteryType vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoLotteryTypeExample example);
	
	/**
	 * 更改我的领地活动类型状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(Integer id);

}
