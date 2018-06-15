package com.mrbt.lingmoney.admin.service.batchGiftRedPacket;

import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月4日
 */
public interface BatchGiftRedPacketService {

	/**
	 * 查找批量赠送对象
	 * @param channel 渠道号
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @param type 1：购买 2：兑付
	 * @param productName 产品名称
	 * @return 数据返回
	 */
	PageInfo findUsersByParams(Integer pageNumber, Integer pageSize, Integer channel, String startDate, String endDate,
			Integer type, String productName);

	/**
	 * 给指定人员赠送加息券
	 *
	 * @Description
	 * @param rId 红包ID
	 * @param uids 用户ID
	 */
	void giveRedPacket(String uids, String rId);

}
