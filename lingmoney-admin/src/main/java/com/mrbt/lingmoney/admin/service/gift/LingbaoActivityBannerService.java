package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoActivityBanner;
import com.mrbt.lingmoney.model.LingbaoActivityBannerExample;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地活动banner
 *
 */
public interface LingbaoActivityBannerService {

	/**
	 * 根据主键查询我的领地活动banner
	 * 
	 * @param id
	 *            主键
	 * @return 我的领地活动banner
	 */
	LingbaoActivityBanner findById(int id);

	/**
	 * 删除我的领地活动banner
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存我的领地活动banner
	 * 
	 * @param vo
	 *            我的领地活动banner
	 */
	void save(LingbaoActivityBanner vo);

	/**
	 * 更新我的领地活动banner,字段选择修改
	 * 
	 * @param vo
	 *            我的领地活动banner
	 */
	void update(LingbaoActivityBanner vo);

	/**
	 * 根据条件帮助类查询
	 * 
	 * @param example
	 *            条件帮助类
	 * @return 分页实体类
	 */
	PageInfo getList(LingbaoActivityBannerExample example);

	/**
	 * 更改活动banner状态
	 * 
	 * @param id
	 *            主键
	 * @return 分页实体类
	 */
	PageInfo changeStatus(Integer id);

}
