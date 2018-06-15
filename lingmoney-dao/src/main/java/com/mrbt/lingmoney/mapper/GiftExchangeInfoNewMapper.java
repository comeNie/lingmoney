package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.GiftExchangeInfoNew;
import com.mrbt.lingmoney.model.GiftExchangeInfoNewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GiftExchangeInfoNewMapper {
    /**
     *  根据指定的条件获取数据库记录数,gift_exchange_info_new
     * @param example
     */
    long countByExample(GiftExchangeInfoNewExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,gift_exchange_info_new
     * @param example
     */
    int deleteByExample(GiftExchangeInfoNewExample example);

    /**
     *  根据主键删除数据库的记录,gift_exchange_info_new
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,gift_exchange_info_new
     * @param record
     */
    int insert(GiftExchangeInfoNew record);

    /**
     *  动态字段,写入数据库记录,gift_exchange_info_new
     * @param record
     */
    int insertSelective(GiftExchangeInfoNew record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,gift_exchange_info_new
     * @param example
     */
    List<GiftExchangeInfoNew> selectByExample(GiftExchangeInfoNewExample example);

    /**
     *  根据指定主键获取一条数据库记录,gift_exchange_info_new
     * @param id
     */
    GiftExchangeInfoNew selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,gift_exchange_info_new
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") GiftExchangeInfoNew record, @Param("example") GiftExchangeInfoNewExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,gift_exchange_info_new
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") GiftExchangeInfoNew record, @Param("example") GiftExchangeInfoNewExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,gift_exchange_info_new
     * @param record
     */
    int updateByPrimaryKeySelective(GiftExchangeInfoNew record);

    /**
     *  根据主键来更新符合条件的数据库记录,gift_exchange_info_new
     * @param record
     */
    int updateByPrimaryKey(GiftExchangeInfoNew record);
}