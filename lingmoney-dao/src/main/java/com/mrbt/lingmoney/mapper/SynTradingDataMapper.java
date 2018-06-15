package com.mrbt.lingmoney.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.SynTradingData;
import com.mrbt.lingmoney.model.SynTradingDataExample;

public interface SynTradingDataMapper {
    /**
     *  根据指定的条件获取数据库记录数,syn_trading_data
     *
     * @param example
     */
    long countByExample(SynTradingDataExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,syn_trading_data
     *
     * @param example
     */
    int deleteByExample(SynTradingDataExample example);

    /**
     *  新写入数据库记录,syn_trading_data
     *
     * @param record
     */
    int insert(SynTradingData record);

    /**
     *  动态字段,写入数据库记录,syn_trading_data
     *
     * @param record
     */
    int insertSelective(SynTradingData record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,syn_trading_data
     *
     * @param example
     */
    List<SynTradingData> selectByExample(SynTradingDataExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,syn_trading_data
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SynTradingData record, @Param("example") SynTradingDataExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,syn_trading_data
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SynTradingData record, @Param("example") SynTradingDataExample example);
}