package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.BuyRecordsVo;
import com.mrbt.lingmoney.model.UsersVo;
import com.mrbt.lingmoney.utils.PageInfo;

public interface UsersCustomerMapper {

	/**
	 * 根据条件分页查询
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<UsersVo> selectUsersVoByCondition(PageInfo pageInfo);

	/**
	 * 根据条件查询个数
	 * 
	 * @param pageInfo
	 * @return
	 */
	int selectUsersVoCountByCondition(PageInfo pageInfo);

	/**
	 * 查询用户购买产品列表
	 * 
	 * @param pageInfo
	 * @return
	 */
	List<BuyRecordsVo> selectTradingListByUsersId(PageInfo pageInfo);

	/**
	 * 查询分公司
	 * 
	 * @param com
	 * @return
	 */
	List<Map<String, Object>> queryManagerCom(String com);

	/**
	 * 查询部门
	 * 
	 * @param dept
	 * @return
	 */
	List<Map<String, Object>> queryManagerDept(String dept);

	/**
	 * 查询注册渠道
	 * 
	 * @param name
	 * @return
	 */
	List<Map<String, Object>> queryRegistChannel(String name);

}