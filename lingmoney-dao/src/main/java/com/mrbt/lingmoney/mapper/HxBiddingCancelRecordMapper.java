package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxBiddingCancelRecord;
import com.mrbt.lingmoney.model.HxBiddingCancelRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxBiddingCancelRecordMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_bidding_cancel_record
     *
     * @param example
     */
    int countByExample(HxBiddingCancelRecordExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_bidding_cancel_record
     *
     * @param example
     */
    int deleteByExample(HxBiddingCancelRecordExample example);

    /**
     *  根据主键删除数据库的记录,hx_bidding_cancel_record
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,hx_bidding_cancel_record
     *
     * @param record
     */
    int insert(HxBiddingCancelRecord record);

    /**
     *  动态字段,写入数据库记录,hx_bidding_cancel_record
     *
     * @param record
     */
    int insertSelective(HxBiddingCancelRecord record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_bidding_cancel_record
     *
     * @param example
     */
    List<HxBiddingCancelRecord> selectByExample(HxBiddingCancelRecordExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_bidding_cancel_record
     *
     * @param id
     */
    HxBiddingCancelRecord selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_bidding_cancel_record
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBiddingCancelRecord record, @Param("example") HxBiddingCancelRecordExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_bidding_cancel_record
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBiddingCancelRecord record, @Param("example") HxBiddingCancelRecordExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_bidding_cancel_record
     *
     * @param record
     */
    int updateByPrimaryKeySelective(HxBiddingCancelRecord record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_bidding_cancel_record
     *
     * @param record
     */
    int updateByPrimaryKey(HxBiddingCancelRecord record);
}