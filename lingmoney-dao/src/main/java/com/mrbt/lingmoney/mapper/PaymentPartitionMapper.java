package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.PaymentPartition;
import com.mrbt.lingmoney.model.PaymentPartitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentPartitionMapper {
    /**
     *  根据指定的条件获取数据库记录数,payment_partition
     *
     * @param example
     */
    long countByExample(PaymentPartitionExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,payment_partition
     *
     * @param example
     */
    int deleteByExample(PaymentPartitionExample example);

    /**
     *  根据主键删除数据库的记录,payment_partition
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,payment_partition
     *
     * @param record
     */
    int insert(PaymentPartition record);

    /**
     *  动态字段,写入数据库记录,payment_partition
     *
     * @param record
     */
    int insertSelective(PaymentPartition record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,payment_partition
     *
     * @param example
     */
    List<PaymentPartition> selectByExample(PaymentPartitionExample example);

    /**
     *  根据指定主键获取一条数据库记录,payment_partition
     *
     * @param id
     */
    PaymentPartition selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,payment_partition
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") PaymentPartition record, @Param("example") PaymentPartitionExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,payment_partition
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") PaymentPartition record, @Param("example") PaymentPartitionExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,payment_partition
     *
     * @param record
     */
    int updateByPrimaryKeySelective(PaymentPartition record);

    /**
     *  根据主键来更新符合条件的数据库记录,payment_partition
     *
     * @param record
     */
    int updateByPrimaryKey(PaymentPartition record);
}