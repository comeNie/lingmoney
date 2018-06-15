package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxBidding;
import com.mrbt.lingmoney.model.HxBiddingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxBiddingMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_bidding
     * @param example
     */
    long countByExample(HxBiddingExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_bidding
     * @param example
     */
    int deleteByExample(HxBiddingExample example);

    /**
     *  根据主键删除数据库的记录,hx_bidding
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_bidding
     * @param record
     */
    int insert(HxBidding record);

    /**
     *  动态字段,写入数据库记录,hx_bidding
     * @param record
     */
    int insertSelective(HxBidding record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_bidding
     * @param example
     */
    List<HxBidding> selectByExample(HxBiddingExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_bidding
     * @param id
     */
    HxBidding selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_bidding
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBidding record, @Param("example") HxBiddingExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_bidding
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBidding record, @Param("example") HxBiddingExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_bidding
     * @param record
     */
    int updateByPrimaryKeySelective(HxBidding record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_bidding
     * @param record
     */
    int updateByPrimaryKey(HxBidding record);
}