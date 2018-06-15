package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;

public interface HxPaymentBidBorrowUnionMapper {
    /**
     *  根据指定的条件获取数据库记录数
     * @param example
     */
     long countByExample(HxPaymentBidBorrowUnionExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录
     * @param example
     */
    List<HxPaymentBidBorrowUnionInfo> selectByExample(HxPaymentBidBorrowUnionExample example);

    /**
     *  根据指定主键获取一条数据库记录
     * @param id
     */
    HxPaymentBidBorrowUnionInfo selectByPrimaryKey(String id);

}