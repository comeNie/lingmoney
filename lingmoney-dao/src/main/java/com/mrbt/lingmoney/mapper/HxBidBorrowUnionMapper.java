package com.mrbt.lingmoney.mapper;

import java.util.List;

import com.mrbt.lingmoney.model.HxBidBorrowUnionExample;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxTradingAccUnion;
import com.mrbt.lingmoney.model.HxTradingAccUnionExample;

public interface HxBidBorrowUnionMapper {
    /**
     *  根据指定的条件获取数据库记录数
     * @param example
     */
    // long countByExample(HxBiddingExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录
     * @param example
     */
    List<HxBidBorrowUnionInfo> selectByExample(HxBidBorrowUnionExample example);

    /**
     *  根据指定主键获取一条数据库记录
     * @param id
     */
    HxBidBorrowUnionInfo selectByPrimaryKey(String id);
    
    /**
     * 获取交易和账户信息
     * @author YJQ  2017年6月16日
     * @param example
     * @return
     */
    List<HxTradingAccUnion> selectTradeUnionByExample(HxTradingAccUnionExample example);
    
    /**
     * 获取交易和账户信息条数
     * @author YJQ  2017年6月16日
     * @param example
     * @return
     */
    Long countTradeUnionByExample(HxTradingAccUnionExample example);

}