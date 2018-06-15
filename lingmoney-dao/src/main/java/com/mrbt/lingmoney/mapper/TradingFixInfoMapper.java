package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.TradingFixInfo;
import com.mrbt.lingmoney.model.TradingFixInfoExample;

public interface TradingFixInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,trading_fix_info
     *
     * @param example
     */
    long countByExample(TradingFixInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,trading_fix_info
     *
     * @param example
     */
    int deleteByExample(TradingFixInfoExample example);

    /**
     *  根据主键删除数据库的记录,trading_fix_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,trading_fix_info
     *
     * @param record
     */
    int insert(TradingFixInfo record);

    /**
     *  动态字段,写入数据库记录,trading_fix_info
     *
     * @param record
     */
    int insertSelective(TradingFixInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,trading_fix_info
     *
     * @param example
     */
    List<TradingFixInfo> selectByExample(TradingFixInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,trading_fix_info
     *
     * @param id
     */
    TradingFixInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,trading_fix_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TradingFixInfo record, @Param("example") TradingFixInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,trading_fix_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TradingFixInfo record, @Param("example") TradingFixInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,trading_fix_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TradingFixInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,trading_fix_info
     *
     * @param record
     */
    int updateByPrimaryKey(TradingFixInfo record);

    /**
     *  动态字段,写入数据库记录,trading_fix_info，返回主键
     *
     * @param record
     */
	int insertSelectiveReturnId(TradingFixInfo tfi);

	/**
	 * 支付完成后更新状态
	 * @param tfi
	 * @return
	 */
	int updateStatusAfterPayment(TradingFixInfo tfi);

	/**
	 * 根据订单号查询交易信息
	 * @param bizCode
	 * @return
	 */
	TradingFixInfo selectByBizCode(String bizCode);

	/**
	 * 根据交易ID查询交易信息ID
	 * @param tid
	 * @return
	 */
	TradingFixInfo selectFixInfoByTid(int tid);
}