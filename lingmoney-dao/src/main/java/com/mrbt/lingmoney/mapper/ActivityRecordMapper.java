package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ActivityRecordExample;

public interface ActivityRecordMapper {
	/**
	 * 根据指定的条件获取数据库记录数,activity_record
	 *
	 * @param example
	 */
	int countByExample(ActivityRecordExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,activity_record
	 *
	 * @param example
	 */
	int deleteByExample(ActivityRecordExample example);

	/**
	 * 根据主键删除数据库的记录,activity_record
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,activity_record
	 *
	 * @param record
	 */
	int insert(ActivityRecord record);

	/**
	 * 动态字段,写入数据库记录,activity_record
	 *
	 * @param record
	 */
	int insertSelective(ActivityRecord record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,activity_record
	 *
	 * @param example
	 */
	List<ActivityRecord> selectByExample(ActivityRecordExample example);

	/**
	 * 根据指定主键获取一条数据库记录,activity_record
	 *
	 * @param id
	 */
	ActivityRecord selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,activity_record
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") ActivityRecord record,
			@Param("example") ActivityRecordExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,activity_record
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") ActivityRecord record, @Param("example") ActivityRecordExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,activity_record
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(ActivityRecord record);

	/**
	 * 根据主键来更新符合条件的数据库记录,activity_record
	 *
	 * @param record
	 */
	int updateByPrimaryKey(ActivityRecord record);

	/**
	 * 根据tid查询记录个数
	 * 
	 * @param t_id
	 * @return
	 */
	int selectCountByTid(int t_id);

	/**
	 * 查询赠送次数
	 * 
	 * @param map
	 *            map
	 * @return 数量
	 */
	int queryByTidUidName(Map<String, Object> map);
}