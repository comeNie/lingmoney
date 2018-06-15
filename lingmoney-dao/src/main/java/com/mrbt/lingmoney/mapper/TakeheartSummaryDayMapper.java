package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TakeheartSummaryDay;
import com.mrbt.lingmoney.model.TakeheartSummaryDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TakeheartSummaryDayMapper {
    /**
     *  根据指定的条件获取数据库记录数,takeheart_summary_day
     *
     * @param example
     */
    long countByExample(TakeheartSummaryDayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,takeheart_summary_day
     *
     * @param example
     */
    int deleteByExample(TakeheartSummaryDayExample example);

    /**
     *  根据主键删除数据库的记录,takeheart_summary_day
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,takeheart_summary_day
     *
     * @param record
     */
    int insert(TakeheartSummaryDay record);

    /**
     *  动态字段,写入数据库记录,takeheart_summary_day
     *
     * @param record
     */
    int insertSelective(TakeheartSummaryDay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,takeheart_summary_day
     *
     * @param example
     */
    List<TakeheartSummaryDay> selectByExample(TakeheartSummaryDayExample example);

    /**
     *  根据指定主键获取一条数据库记录,takeheart_summary_day
     *
     * @param id
     */
    TakeheartSummaryDay selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,takeheart_summary_day
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TakeheartSummaryDay record, @Param("example") TakeheartSummaryDayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,takeheart_summary_day
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TakeheartSummaryDay record, @Param("example") TakeheartSummaryDayExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,takeheart_summary_day
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TakeheartSummaryDay record);

    /**
     *  根据主键来更新符合条件的数据库记录,takeheart_summary_day
     *
     * @param record
     */
    int updateByPrimaryKey(TakeheartSummaryDay record);
}