package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.SellBatch;
import com.mrbt.lingmoney.model.SellBatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellBatchMapper {
    /**
     *  根据指定的条件获取数据库记录数,sell_batch
     *
     * @param example
     */
    long countByExample(SellBatchExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,sell_batch
     *
     * @param example
     */
    int deleteByExample(SellBatchExample example);

    /**
     *  根据主键删除数据库的记录,sell_batch
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,sell_batch
     *
     * @param record
     */
    int insert(SellBatch record);

    /**
     *  动态字段,写入数据库记录,sell_batch
     *
     * @param record
     */
    int insertSelective(SellBatch record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,sell_batch
     *
     * @param example
     */
    List<SellBatch> selectByExample(SellBatchExample example);

    /**
     *  根据指定主键获取一条数据库记录,sell_batch
     *
     * @param id
     */
    SellBatch selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,sell_batch
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") SellBatch record, @Param("example") SellBatchExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,sell_batch
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") SellBatch record, @Param("example") SellBatchExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,sell_batch
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SellBatch record);

    /**
     *  根据主键来更新符合条件的数据库记录,sell_batch
     *
     * @param record
     */
    int updateByPrimaryKey(SellBatch record);
}