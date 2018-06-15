package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.Condition;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;

public interface UsersAccountMapper {
	/**
	 * 根据指定的条件获取数据库记录数,users_account
	 *
	 * @param example
	 */
	long countByExample(UsersAccountExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,users_account
	 *
	 * @param example
	 */
	int deleteByExample(UsersAccountExample example);

	/**
	 * 根据主键删除数据库的记录,users_account
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,users_account
	 *
	 * @param record
	 */
	int insert(UsersAccount record);

	/**
	 * 动态字段,写入数据库记录,users_account
	 *
	 * @param record
	 */
	int insertSelective(UsersAccount record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,users_account
	 *
	 * @param example
	 */
	List<UsersAccount> selectByExample(UsersAccountExample example);

	/**
	 * 根据指定主键获取一条数据库记录,users_account
	 *
	 * @param id
	 */
	UsersAccount selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,users_account
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") UsersAccount record, @Param("example") UsersAccountExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,users_account
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") UsersAccount record, @Param("example") UsersAccountExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,users_account
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(UsersAccount record);

	/**
	 * 根据主键来更新符合条件的数据库记录,users_account
	 *
	 * @param record
	 */
	int updateByPrimaryKey(UsersAccount record);

	/**
	 * 根据用户ID查询
	 * 
	 * @param userID
	 * @return
	 */
	UsersAccount selectByUid(String uId);

	/**
	 * 查询用户理财排名信息
	 * 
	 * @return nickName 用户昵称 pic 用户头像 dPic 等级图片 dName 等级名称 account 登录账户名 uid
	 *         用户ID
	 */
	List<Map<String, Object>> queryUsersFinancialRanking();

	List<UsersAccount> selectAdminMessageList(Condition condition);

	/**
	 * 查询用户排名信息
	 * @param uid
	 * @return
	 */
	List<Map<String, Object>> queryUsersRanking(String uid);
}