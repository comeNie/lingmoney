package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxDailyData;
import com.mrbt.lingmoney.model.HxDailyDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxDailyDataMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_daily_data
     *
     * @param example
     */
    int countByExample(HxDailyDataExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_daily_data
     *
     * @param example
     */
    int deleteByExample(HxDailyDataExample example);

    /**
     *  根据主键删除数据库的记录,hx_daily_data
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,hx_daily_data
     *
     * @param record
     */
    int insert(HxDailyData record);

    /**
     *  动态字段,写入数据库记录,hx_daily_data
     *
     * @param record
     */
    int insertSelective(HxDailyData record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_daily_data
     *
     * @param example
     */
    List<HxDailyData> selectByExample(HxDailyDataExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_daily_data
     *
     * @param id
     */
    HxDailyData selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_daily_data
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxDailyData record, @Param("example") HxDailyDataExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_daily_data
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxDailyData record, @Param("example") HxDailyDataExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_daily_data
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxDailyData record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_daily_data
     *
     * @param record
     */
    int updateByPrimaryKey(HxDailyData record);
}