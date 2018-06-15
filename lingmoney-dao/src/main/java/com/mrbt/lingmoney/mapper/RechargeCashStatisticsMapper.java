package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.RechargeCashStatistics;
import com.mrbt.lingmoney.model.RechargeCashStatisticsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RechargeCashStatisticsMapper {
    /**
     *  根据指定的条件获取数据库记录数,recharge_cash_statistics
     *
     * @param example
     */
    long countByExample(RechargeCashStatisticsExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,recharge_cash_statistics
     *
     * @param example
     */
    int deleteByExample(RechargeCashStatisticsExample example);

    /**
     *  根据主键删除数据库的记录,recharge_cash_statistics
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,recharge_cash_statistics
     *
     * @param record
     */
    int insert(RechargeCashStatistics record);

    /**
     *  动态字段,写入数据库记录,recharge_cash_statistics
     *
     * @param record
     */
    int insertSelective(RechargeCashStatistics record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,recharge_cash_statistics
     *
     * @param example
     */
    List<RechargeCashStatistics> selectByExample(RechargeCashStatisticsExample example);

    /**
     *  根据指定主键获取一条数据库记录,recharge_cash_statistics
     *
     * @param id
     */
    RechargeCashStatistics selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,recharge_cash_statistics
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") RechargeCashStatistics record, @Param("example") RechargeCashStatisticsExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,recharge_cash_statistics
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") RechargeCashStatistics record, @Param("example") RechargeCashStatisticsExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,recharge_cash_statistics
     *
     * @param record
     */
    int updateByPrimaryKeySelective(RechargeCashStatistics record);

    /**
     *  根据主键来更新符合条件的数据库记录,recharge_cash_statistics
     *
     * @param record
     */
    int updateByPrimaryKey(RechargeCashStatistics record);
}