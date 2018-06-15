package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.HxBorrowerCustomer;
import com.mrbt.lingmoney.utils.PageInfo;

public interface HxBorrowerCustomerMapper {
	/**
	 * 根据条件查询
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<HxBorrowerCustomer> findByCondition(PageInfo pageInfo);

	/**
	 * 根据条件查询个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int findCountByCondition(PageInfo pageInfo);
}