package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.UserFinance;

/**
 * @author 罗鑫
 * @Date 2017年4月18日
 */
public interface UserFinanceMapper {

	/**
	 * 查询用户理财数据
	 */
	List<UserFinance> findUserTrading(Map<String, Object> map);

	/**
	 * 统计用户理财数据
	 */
	long countUserTrading(Map<String, Object> map);
}
