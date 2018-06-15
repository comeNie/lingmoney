package com.mrbt.lingmoney.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.SellBatchInfo;
import com.mrbt.lingmoney.model.SellBatchInfoExample;

public interface SellBatchInfoMapper {
    /**
     *  根据指定的条件获取数据库记录数,sell_batch_info
     *
     * @param example
     */
    long countByExample(SellBatchInfoExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,sell_batch_info
     *
     * @param example
     */
    int deleteByExample(SellBatchInfoExample example);

    /**
     *  根据主键删除数据库的记录,sell_batch_info
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,sell_batch_info
     *
     * @param record
     */
    int insert(SellBatchInfo record);

    /**
     *  动态字段,写入数据库记录,sell_batch_info
     *
     * @param record
     */
    int insertSelective(SellBatchInfo record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,sell_batch_info
     *
     * @param example
     */
    List<SellBatchInfo> selectByExample(SellBatchInfoExample example);

    /**
     *  根据指定主键获取一条数据库记录,sell_batch_info
     *
     * @param id
     */
    SellBatchInfo selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,sell_batch_info
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SellBatchInfo record, @Param("example") SellBatchInfoExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,sell_batch_info
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SellBatchInfo record, @Param("example") SellBatchInfoExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,sell_batch_info
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SellBatchInfo record);

    /**
     *  根据主键来更新符合条件的数据库记录,sell_batch_info
     *
     * @param record
     */
    int updateByPrimaryKey(SellBatchInfo record);

	/**
	 * 查询用户基本信息
	 * 
	 * @param uId
	 * @return map
	 */
	Map<String, Object> findUserInfoByUid(String uId);

	/**
	 * 查询理财经理基本信息
	 * 
	 * @param uId
	 * @return map
	 */
	Map<String, Object> findOrderInfoByUid(String uId);
}