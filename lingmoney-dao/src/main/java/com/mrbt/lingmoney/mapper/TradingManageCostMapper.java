package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TradingManageCost;
import com.mrbt.lingmoney.model.TradingManageCostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingManageCostMapper {
    /**
     *  根据指定的条件获取数据库记录数,trading_manage_cost
     *
     * @param example
     */
    long countByExample(TradingManageCostExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,trading_manage_cost
     *
     * @param example
     */
    int deleteByExample(TradingManageCostExample example);

    /**
     *  根据主键删除数据库的记录,trading_manage_cost
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,trading_manage_cost
     *
     * @param record
     */
    int insert(TradingManageCost record);

    /**
     *  动态字段,写入数据库记录,trading_manage_cost
     *
     * @param record
     */
    int insertSelective(TradingManageCost record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,trading_manage_cost
     *
     * @param example
     */
    List<TradingManageCost> selectByExample(TradingManageCostExample example);

    /**
     *  根据指定主键获取一条数据库记录,trading_manage_cost
     *
     * @param id
     */
    TradingManageCost selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,trading_manage_cost
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TradingManageCost record, @Param("example") TradingManageCostExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,trading_manage_cost
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TradingManageCost record, @Param("example") TradingManageCostExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,trading_manage_cost
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TradingManageCost record);

    /**
     *  根据主键来更新符合条件的数据库记录,trading_manage_cost
     *
     * @param record
     */
    int updateByPrimaryKey(TradingManageCost record);
}