package com.mrbt.lingmoney.admin.service.festival;

import com.mrbt.lingmoney.model.admin.GiftDetailVo;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 中奖礼品兑换详情
 *
 */
public interface GiftDetailsService {

	/**
	 * 查询
	 * 
	 * @param pageInfo
	 *            pageInfo
	 */
	void findDataGrid(PageInfo pageInfo);

	/**
	 * 发货
	 * 
	 * @param vo
	 *            GiftDetailVo
	 * @return 数据返回
	 */
	PageInfo processingDelivery(GiftDetailVo vo);

	/**
	 * 确认收货
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	PageInfo processingReceipt(Integer id);
}
