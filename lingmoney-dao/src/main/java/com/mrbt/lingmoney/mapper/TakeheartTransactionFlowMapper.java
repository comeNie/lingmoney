package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TakeheartTransactionFlow;
import com.mrbt.lingmoney.model.TakeheartTransactionFlowExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TakeheartTransactionFlowMapper {
    /**
     *  根据指定的条件获取数据库记录数,takeheart_transaction_flow
     *
     * @param example
     */
    long countByExample(TakeheartTransactionFlowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,takeheart_transaction_flow
     *
     * @param example
     */
    int deleteByExample(TakeheartTransactionFlowExample example);

    /**
     *  根据主键删除数据库的记录,takeheart_transaction_flow
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,takeheart_transaction_flow
     *
     * @param record
     */
    int insert(TakeheartTransactionFlow record);

    /**
     *  动态字段,写入数据库记录,takeheart_transaction_flow
     *
     * @param record
     */
    int insertSelective(TakeheartTransactionFlow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,takeheart_transaction_flow
     *
     * @param example
     */
    List<TakeheartTransactionFlow> selectByExample(TakeheartTransactionFlowExample example);

    /**
     *  根据指定主键获取一条数据库记录,takeheart_transaction_flow
     *
     * @param id
     */
    TakeheartTransactionFlow selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,takeheart_transaction_flow
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TakeheartTransactionFlow record, @Param("example") TakeheartTransactionFlowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,takeheart_transaction_flow
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TakeheartTransactionFlow record, @Param("example") TakeheartTransactionFlowExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,takeheart_transaction_flow
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TakeheartTransactionFlow record);

    /**
     *  根据主键来更新符合条件的数据库记录,takeheart_transaction_flow
     *
     * @param record
     */
    int updateByPrimaryKey(TakeheartTransactionFlow record);
    /**
     *  动态字段,写入数据库记录,takeheart_transaction_flow
     *	返回主键
     * @param record
     */
    int insertSelectiveReturnKey(TakeheartTransactionFlow record);

    /**
     * 根据条件查询随心取交易流水
     * @param uid 用户id
     * @param tid 交易id
     * @param pid 产品id
     * @param limit 条数
     * @return
     */
	List<TakeheartTransactionFlow> findTakeheartTransactionFlow(Map<String, Object> map);
}