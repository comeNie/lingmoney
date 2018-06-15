package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TradingFloatInfo;
import com.mrbt.lingmoney.model.TradingFloatInfoExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TradingFloatInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,trading_float_info
     *
     * @param example
     */
    long countByExample(TradingFloatInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,trading_float_info
     *
     * @param example
     */
    int deleteByExample(TradingFloatInfoExample example);

    /**
     *  根据主键删除数据库的记录,trading_float_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,trading_float_info
     *
     * @param record
     */
    int insert(TradingFloatInfo record);

    /**
     *  动态字段,写入数据库记录,trading_float_info
     *
     * @param record
     */
    int insertSelective(TradingFloatInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,trading_float_info
     *
     * @param example
     */
    List<TradingFloatInfo> selectByExample(TradingFloatInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,trading_float_info
     *
     * @param id
     */
    TradingFloatInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,trading_float_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TradingFloatInfo record, @Param("example") TradingFloatInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,trading_float_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TradingFloatInfo record, @Param("example") TradingFloatInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,trading_float_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TradingFloatInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,trading_float_info
     *
     * @param record
     */
    int updateByPrimaryKey(TradingFloatInfo record);

    /**
     * 根据交易ID查询相关信息
     * @param id
     * @return
     */
	TradingFloatInfo selectTradingFloatInfoByTID(Integer id);

	Double selectSUMMoney(Map<String, Object> map);

	/**
	 * 根据交易ID查询佣金总额
	 * @param tid
	 * @return
	 */
	BigDecimal getFolatFeesRateByTid(Integer tid);
}