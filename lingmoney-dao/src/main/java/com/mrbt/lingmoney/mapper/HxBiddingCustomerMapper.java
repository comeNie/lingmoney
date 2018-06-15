package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.HxBiddingCustomer;
import com.mrbt.lingmoney.utils.PageInfo;

public interface HxBiddingCustomerMapper {
	/**
	 * 根据条件查询，按ID倒序
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<HxBiddingCustomer> findByCondition(PageInfo pageInfo);

	/**
	 * 根据条件查询个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int findCountByCondition(PageInfo pageInfo);
}