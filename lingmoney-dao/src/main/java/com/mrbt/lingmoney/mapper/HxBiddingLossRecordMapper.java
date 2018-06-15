package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.HxBiddingLossRecord;
import com.mrbt.lingmoney.model.HxBiddingLossRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HxBiddingLossRecordMapper {
    /**
     *  根据指定的条件获取数据库记录数,hx_bidding_loss_record
     * @param example
     */
    long countByExample(HxBiddingLossRecordExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,hx_bidding_loss_record
     * @param example
     */
    int deleteByExample(HxBiddingLossRecordExample example);

    /**
     *  根据主键删除数据库的记录,hx_bidding_loss_record
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     *  新写入数据库记录,hx_bidding_loss_record
     * @param record
     */
    int insert(HxBiddingLossRecord record);

    /**
     *  动态字段,写入数据库记录,hx_bidding_loss_record
     * @param record
     */
    int insertSelective(HxBiddingLossRecord record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,hx_bidding_loss_record
     * @param example
     */
    List<HxBiddingLossRecord> selectByExample(HxBiddingLossRecordExample example);

    /**
     *  根据指定主键获取一条数据库记录,hx_bidding_loss_record
     * @param id
     */
    HxBiddingLossRecord selectByPrimaryKey(String id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,hx_bidding_loss_record
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") HxBiddingLossRecord record, @Param("example") HxBiddingLossRecordExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,hx_bidding_loss_record
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") HxBiddingLossRecord record, @Param("example") HxBiddingLossRecordExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,hx_bidding_loss_record
     * @param record
     */
    int updateByPrimaryKeySelective(HxBiddingLossRecord record);

    /**
     *  根据主键来更新符合条件的数据库记录,hx_bidding_loss_record
     * @param record
     */
    int updateByPrimaryKey(HxBiddingLossRecord record);
}