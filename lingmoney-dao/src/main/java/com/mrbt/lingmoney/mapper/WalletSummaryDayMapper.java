package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletSummaryDay;
import com.mrbt.lingmoney.model.WalletSummaryDayExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletSummaryDayMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_summary_day
     *
     * @param example
     */
    long countByExample(WalletSummaryDayExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_summary_day
     *
     * @param example
     */
    int deleteByExample(WalletSummaryDayExample example);

    /**
     *  根据主键删除数据库的记录,wallet_summary_day
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,wallet_summary_day
     *
     * @param record
     */
    int insert(WalletSummaryDay record);

    /**
     *  动态字段,写入数据库记录,wallet_summary_day
     *
     * @param record
     */
    int insertSelective(WalletSummaryDay record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_summary_day
     *
     * @param example
     */
    List<WalletSummaryDay> selectByExample(WalletSummaryDayExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_summary_day
     *
     * @param id
     */
    WalletSummaryDay selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_summary_day
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletSummaryDay record, @Param("example") WalletSummaryDayExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_summary_day
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletSummaryDay record, @Param("example") WalletSummaryDayExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_summary_day
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletSummaryDay record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_summary_day
     *
     * @param record
     */
    int updateByPrimaryKey(WalletSummaryDay record);
}