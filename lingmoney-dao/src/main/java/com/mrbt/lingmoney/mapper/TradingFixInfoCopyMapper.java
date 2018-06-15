package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TradingFixInfoCopy;
import com.mrbt.lingmoney.model.TradingFixInfoCopyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingFixInfoCopyMapper {
    /**
     *  根据指定的条件获取数据库记录数,trading_fix_info_copy
     *
     * @param example
     */
    long countByExample(TradingFixInfoCopyExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,trading_fix_info_copy
     *
     * @param example
     */
    int deleteByExample(TradingFixInfoCopyExample example);

    /**
     *  根据主键删除数据库的记录,trading_fix_info_copy
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,trading_fix_info_copy
     *
     * @param record
     */
    int insert(TradingFixInfoCopy record);

    /**
     *  动态字段,写入数据库记录,trading_fix_info_copy
     *
     * @param record
     */
    int insertSelective(TradingFixInfoCopy record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,trading_fix_info_copy
     *
     * @param example
     */
    List<TradingFixInfoCopy> selectByExample(TradingFixInfoCopyExample example);

    /**
     *  根据指定主键获取一条数据库记录,trading_fix_info_copy
     *
     * @param id
     */
    TradingFixInfoCopy selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,trading_fix_info_copy
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TradingFixInfoCopy record, @Param("example") TradingFixInfoCopyExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,trading_fix_info_copy
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TradingFixInfoCopy record, @Param("example") TradingFixInfoCopyExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,trading_fix_info_copy
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TradingFixInfoCopy record);

    /**
     *  根据主键来更新符合条件的数据库记录,trading_fix_info_copy
     *
     * @param record
     */
    int updateByPrimaryKey(TradingFixInfoCopy record);
}