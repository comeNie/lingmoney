package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.WinningCount;
import com.mrbt.lingmoney.model.WinningCountExample;

public interface WinningCountMapper {
	/**
	 * 根据指定的条件获取数据库记录数,winning_count
	 * 
	 * @param example
	 */
	long countByExample(WinningCountExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,winning_count
	 * 
	 * @param example
	 */
	int deleteByExample(WinningCountExample example);

	/**
	 * 根据主键删除数据库的记录,winning_count
	 * 
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,winning_count
	 * 
	 * @param record
	 */
	int insert(WinningCount record);

	/**
	 * 动态字段,写入数据库记录,winning_count
	 * 
	 * @param record
	 */
	int insertSelective(WinningCount record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,winning_count
	 * 
	 * @param example
	 */
	List<WinningCount> selectByExample(WinningCountExample example);

	/**
	 * 根据指定主键获取一条数据库记录,winning_count
	 * 
	 * @param id
	 */
	WinningCount selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,winning_count
	 * 
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") WinningCount record, @Param("example") WinningCountExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,winning_count
	 * 
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") WinningCount record, @Param("example") WinningCountExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,winning_count
	 * 
	 * @param record
	 */
	int updateByPrimaryKeySelective(WinningCount record);

	/**
	 * 根据主键来更新符合条件的数据库记录,winning_count
	 * 
	 * @param record
	 */
	int updateByPrimaryKey(WinningCount record);

	void batchInsertByTelephone(List<String> list);
}