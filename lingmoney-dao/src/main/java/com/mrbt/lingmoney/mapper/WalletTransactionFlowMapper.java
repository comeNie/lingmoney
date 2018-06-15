package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.WalletTransactionFlow;
import com.mrbt.lingmoney.model.WalletTransactionFlowExample;
import com.mrbt.lingmoney.model.WalletTransactionFlowKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletTransactionFlowMapper {
    /**
     *  根据指定的条件获取数据库记录数,wallet_transaction_flow
     *
     * @param example
     */
    long countByExample(WalletTransactionFlowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,wallet_transaction_flow
     *
     * @param example
     */
    int deleteByExample(WalletTransactionFlowExample example);

    /**
     *  根据主键删除数据库的记录,wallet_transaction_flow
     *
     * @param key
     */
    int deleteByPrimaryKey(WalletTransactionFlowKey key);

    /**
     *  新写入数据库记录,wallet_transaction_flow
     *
     * @param record
     */
    int insert(WalletTransactionFlow record);

    /**
     *  动态字段,写入数据库记录,wallet_transaction_flow
     *
     * @param record
     */
    int insertSelective(WalletTransactionFlow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,wallet_transaction_flow
     *
     * @param example
     */
    List<WalletTransactionFlow> selectByExample(WalletTransactionFlowExample example);

    /**
     *  根据指定主键获取一条数据库记录,wallet_transaction_flow
     *
     * @param key
     */
    WalletTransactionFlow selectByPrimaryKey(WalletTransactionFlowKey key);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,wallet_transaction_flow
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") WalletTransactionFlow record, @Param("example") WalletTransactionFlowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,wallet_transaction_flow
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") WalletTransactionFlow record, @Param("example") WalletTransactionFlowExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,wallet_transaction_flow
     *
     * @param record
     */
    int updateByPrimaryKeySelective(WalletTransactionFlow record);

    /**
     *  根据主键来更新符合条件的数据库记录,wallet_transaction_flow
     *
     * @param record
     */
    int updateByPrimaryKey(WalletTransactionFlow record);
}