package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsersMessage;
import com.mrbt.lingmoney.model.UsersMessageExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UsersMessageMapper {
	/**
	 * 根据指定的条件获取数据库记录数,users_message
	 *
	 * @param example
	 */
	long countByExample(UsersMessageExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,users_message
	 *
	 * @param example
	 */
	int deleteByExample(UsersMessageExample example);

	/**
	 * 根据主键删除数据库的记录,users_message
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,users_message
	 *
	 * @param record
	 */
	int insert(UsersMessage record);

	/**
	 * 动态字段,写入数据库记录,users_message
	 *
	 * @param record
	 */
	int insertSelective(UsersMessage record);

	/**
	 * ,users_message
	 *
	 * @param example
	 */
	List<UsersMessage> selectByExampleWithBLOBs(UsersMessageExample example);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,users_message
	 *
	 * @param example
	 */
	List<UsersMessage> selectByExample(UsersMessageExample example);

	/**
	 * 根据指定主键获取一条数据库记录,users_message
	 *
	 * @param id
	 */
	UsersMessage selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,users_message
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") UsersMessage record, @Param("example") UsersMessageExample example);

	/**
	 * ,users_message
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleWithBLOBs(@Param("record") UsersMessage record, @Param("example") UsersMessageExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,users_message
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") UsersMessage record, @Param("example") UsersMessageExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,users_message
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(UsersMessage record);

	/**
	 * ,users_message
	 *
	 * @param record
	 */
	int updateByPrimaryKeyWithBLOBs(UsersMessage record);

	/**
	 * 根据主键来更新符合条件的数据库记录,users_message
	 *
	 * @param record
	 */
	int updateByPrimaryKey(UsersMessage record);

	/**
	 * 根据用户ID查询
	 * 
	 * @param uid
	 * @return
	 */
	UsersMessage selectByUid(String uid);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	long insertContentList(List<UsersMessage> list);
}