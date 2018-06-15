package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoLotteryRatio;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地抽奖比例
 *
 */
public interface LingbaoLotteryRatioService {
	/**
	 * 根据主键查询我的领地抽奖比例
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地抽奖比例
	 */
	LingbaoLotteryRatio findById(int id);

	/**
	 * 删除我的领地抽奖比例
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地抽奖比例
	 * 
	 * @param vo
	 *            我的领地抽奖比例
	 */
	void save(LingbaoLotteryRatio vo);

	/**
	 * 更新我的领地抽奖比例,字段选择修改
	 * 
	 * @param vo
	 *            我的领地抽奖比例
	 */
	void updateByPrimaryKeySelective(LingbaoLotteryRatio vo);
	/**
	 * 更新我的领地抽奖比例,字段全部修改
	 * 
	 * @param vo
	 *            我的领地抽奖比例
	 */
	void updateByPrimaryKey(LingbaoLotteryRatio vo);

	/**
	 * 根据分页实体类查询
	 * 
	 * @param pageInfo
	 *            分页实体类
	 * @return 分页实体类
	 */
	PageInfo getList(PageInfo pageInfo);

	/**
	 * 更改我的领地抽奖比例状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	boolean changeStatus(Integer id);
}
