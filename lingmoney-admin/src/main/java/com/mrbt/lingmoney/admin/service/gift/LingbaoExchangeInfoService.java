package com.mrbt.lingmoney.admin.service.gift;

import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 我的领地礼品兑换信息
 *
 */
public interface LingbaoExchangeInfoService {
	/**
	 * 根据主键查询礼品兑换信息
	 * 
	 * @param id
	 *            主键
	 * @return 礼品兑换信息
	 */
	LingbaoExchangeInfo findById(int id);

	/**
	 * 删除礼品兑换信息
	 * 
	 * @param id
	 *            主键
	 */
	void delete(int id);

	/**
	 * 保存礼品兑换信息
	 * 
	 * @param vo
	 *            礼品兑换信息
	 */
	void save(LingbaoExchangeInfo vo);

	/**
	 * 更新礼品兑换信息,字段选择修改
	 * 
	 * @param vo
	 *            礼品兑换信息
	 */
	void update(LingbaoExchangeInfo vo);

	/**
	 * 查询列表
	 * 
	 * @param pageInfo
	 *            分页实体类
	 */
	void findDataGrid(PageInfo pageInfo);

	/**
	 * 查询收货地址
	 * 
	 * @param id
	 *            主键
	 * @return 收货地址
	 */
	LingbaoAddress findAddressById(Integer id);

	/**
	 * 礼品兑换信息==》处理订单
	 * 
	 * @param vo
	 *            礼品兑换信息
	 * @return 数据返回
	 */
	PageInfo processingOrder(LingbaoExchangeInfo vo);

}
