package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.TakeheartUserStatistics;
import com.mrbt.lingmoney.model.TakeheartUserStatisticsExample;

public interface TakeheartUserStatisticsMapper {
    /**
     *  根据指定的条件获取数据库记录数,takeheart_user_statistics
     *
     * @param example
     */
    long countByExample(TakeheartUserStatisticsExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,takeheart_user_statistics
     *
     * @param example
     */
    int deleteByExample(TakeheartUserStatisticsExample example);

    /**
     *  根据主键删除数据库的记录,takeheart_user_statistics
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,takeheart_user_statistics
     *
     * @param record
     */
    int insert(TakeheartUserStatistics record);

    /**
     *  动态字段,写入数据库记录,takeheart_user_statistics
     *
     * @param record
     */
    int insertSelective(TakeheartUserStatistics record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,takeheart_user_statistics
     *
     * @param example
     */
    List<TakeheartUserStatistics> selectByExample(TakeheartUserStatisticsExample example);

    /**
     *  根据指定主键获取一条数据库记录,takeheart_user_statistics
     *
     * @param id
     */
    TakeheartUserStatistics selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,takeheart_user_statistics
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TakeheartUserStatistics record, @Param("example") TakeheartUserStatisticsExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,takeheart_user_statistics
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TakeheartUserStatistics record, @Param("example") TakeheartUserStatisticsExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,takeheart_user_statistics
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TakeheartUserStatistics record);

    /**
     *  根据主键来更新符合条件的数据库记录,takeheart_user_statistics
     *
     * @param record
     */
    int updateByPrimaryKey(TakeheartUserStatistics record);

    /**
     * 随心取当日库存统计
     * 
     * @author yiban
     * @date 2018年1月10日 上午9:10:37
     * @version 1.0
     *
     */
    int dayStatistics();
}