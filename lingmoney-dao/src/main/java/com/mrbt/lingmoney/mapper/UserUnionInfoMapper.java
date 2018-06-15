package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.model.UserUnionInfoExample;

public interface UserUnionInfoMapper {
	/**
	 * 通过用户id查出用户信息
	 * @author YJQ  2017年4月17日
	 * @param id
	 * @return
	 */
	UserUnionInfo selectByUid(String id);
	
	/**
	 * 查询用户关联信息列表
	 * @author YJQ  2017年7月20日
	 * @param example
	 * @return
	 */
	List<UserUnionInfo> selectByExample(UserUnionInfoExample example);
	
	/**
	 * 通过动态条件查询条数
	 * @author YJQ  2017年5月20日
	 * @param example
	 * @return
	 */
	Integer countByExample(UserUnionInfoExample example);
}
