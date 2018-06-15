package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxDailyReconciliate;
import com.mrbt.lingmoney.model.HxDailyReconciliateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxDailyReconciliateMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_daily_reconciliate
     *
     * @param example
     */
    int countByExample(HxDailyReconciliateExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_daily_reconciliate
     *
     * @param example
     */
    int deleteByExample(HxDailyReconciliateExample example);

    /**
     *  根据主键删除数据库的记录,hx_daily_reconciliate
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_daily_reconciliate
     *
     * @param record
     */
    int insert(HxDailyReconciliate record);

    /**
     *  动态字段,写入数据库记录,hx_daily_reconciliate
     *
     * @param record
     */
    int insertSelective(HxDailyReconciliate record);

    /**
     * ,hx_daily_reconciliate
     *
     * @param example
     */
    List<HxDailyReconciliate> selectByExampleWithBLOBs(HxDailyReconciliateExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_daily_reconciliate
     *
     * @param example
     */
    List<HxDailyReconciliate> selectByExample(HxDailyReconciliateExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_daily_reconciliate
     *
     * @param id
     */
    HxDailyReconciliate selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_daily_reconciliate
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxDailyReconciliate record, @Param("example") HxDailyReconciliateExample example);

    /**
     * ,hx_daily_reconciliate
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") HxDailyReconciliate record, @Param("example") HxDailyReconciliateExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_daily_reconciliate
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxDailyReconciliate record, @Param("example") HxDailyReconciliateExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_daily_reconciliate
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxDailyReconciliate record);

    /**
     * ,hx_daily_reconciliate
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(HxDailyReconciliate record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_daily_reconciliate
     *
     * @param record
     */
    int updateByPrimaryKey(HxDailyReconciliate record);
}