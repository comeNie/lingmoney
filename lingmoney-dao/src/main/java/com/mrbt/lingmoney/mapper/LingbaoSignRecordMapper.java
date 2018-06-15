package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoSignRecord;
import com.mrbt.lingmoney.model.LingbaoSignRecordExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LingbaoSignRecordMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_sign_record
     *
     * @param example
     */
    long countByExample(LingbaoSignRecordExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_sign_record
     *
     * @param example
     */
    int deleteByExample(LingbaoSignRecordExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_sign_record
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_sign_record
     *
     * @param record
     */
    int insert(LingbaoSignRecord record);

    /**
     *  动态字段,写入数据库记录,lingbao_sign_record
     *
     * @param record
     */
    int insertSelective(LingbaoSignRecord record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_sign_record
     *
     * @param example
     */
    List<LingbaoSignRecord> selectByExample(LingbaoSignRecordExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_sign_record
     *
     * @param id
     */
    LingbaoSignRecord selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_sign_record
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoSignRecord record, @Param("example") LingbaoSignRecordExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_sign_record
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoSignRecord record, @Param("example") LingbaoSignRecordExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_sign_record
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoSignRecord record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_sign_record
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoSignRecord record);

    /**
     * 根据用户ID从数据库查询今日签到记录
     * @param uid
     * @return
     */
	int queryTodaySignRecord(String uid);

	/**
	 * 根据用户ID查询最新一条签到记录信息
	 * @param uid
	 * @return
	 */
	LingbaoSignRecord getLastSingRecord(String uid);
}