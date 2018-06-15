package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.LingbaoLotteryTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LingbaoLotteryTypeMapper {
    /**
     *  根据指定的条件获取数据库记录数,lingbao_lottery_type
     *
     * @param example
     */
    long countByExample(LingbaoLotteryTypeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,lingbao_lottery_type
     *
     * @param example
     */
    int deleteByExample(LingbaoLotteryTypeExample example);

    /**
     *  根据主键删除数据库的记录,lingbao_lottery_type
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,lingbao_lottery_type
     *
     * @param record
     */
    int insert(LingbaoLotteryType record);

    /**
     *  动态字段,写入数据库记录,lingbao_lottery_type
     *
     * @param record
     */
    int insertSelective(LingbaoLotteryType record);

    /**
     * ,lingbao_lottery_type
     *
     * @param example
     */
    List<LingbaoLotteryType> selectByExampleWithBLOBs(LingbaoLotteryTypeExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,lingbao_lottery_type
     *
     * @param example
     */
    List<LingbaoLotteryType> selectByExample(LingbaoLotteryTypeExample example);

    /**
     *  根据指定主键获取一条数据库记录,lingbao_lottery_type
     *
     * @param id
     */
    LingbaoLotteryType selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,lingbao_lottery_type
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") LingbaoLotteryType record, @Param("example") LingbaoLotteryTypeExample example);

    /**
     * ,lingbao_lottery_type
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") LingbaoLotteryType record, @Param("example") LingbaoLotteryTypeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,lingbao_lottery_type
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") LingbaoLotteryType record, @Param("example") LingbaoLotteryTypeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,lingbao_lottery_type
     *
     * @param record
     */
    int updateByPrimaryKeySelective(LingbaoLotteryType record);

    /**
     * ,lingbao_lottery_type
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(LingbaoLotteryType record);

    /**
     *  根据主键来更新符合条件的数据库记录,lingbao_lottery_type
     *
     * @param record
     */
    int updateByPrimaryKey(LingbaoLotteryType record);
}