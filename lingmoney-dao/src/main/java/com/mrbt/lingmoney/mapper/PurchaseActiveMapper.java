package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PurchaseActivePostInfo;
import com.mrbt.lingmoney.model.Trading;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
public interface PurchaseActiveMapper {

	/**
	 * 更新Trading表的status的状态
	 * 
	 * @param tId
	 * @param states
	 */
	void updateTradingStatus(Integer tId, Integer status);

	void updateTradingFixInfoStatus(Integer tId, Integer status);

	Trading selectTradingWithProduect(String biz_code);

	PurchaseActivePostInfo queryPostInfo(String u_id);

	PurchaseActivePostInfo queryPostInfo2(String tId);
}
