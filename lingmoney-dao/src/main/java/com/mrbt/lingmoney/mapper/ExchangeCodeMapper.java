package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ExchangeCode;
import com.mrbt.lingmoney.model.ExchangeCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExchangeCodeMapper {
    /**
     *  根据指定的条件获取数据库记录数,exchange_code
     *
     * @param example
     */
    long countByExample(ExchangeCodeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,exchange_code
     *
     * @param example
     */
    int deleteByExample(ExchangeCodeExample example);

    /**
     *  根据主键删除数据库的记录,exchange_code
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,exchange_code
     *
     * @param record
     */
    int insert(ExchangeCode record);

    /**
     *  动态字段,写入数据库记录,exchange_code
     *
     * @param record
     */
    int insertSelective(ExchangeCode record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,exchange_code
     *
     * @param example
     */
    List<ExchangeCode> selectByExample(ExchangeCodeExample example);

    /**
     *  根据指定主键获取一条数据库记录,exchange_code
     *
     * @param id
     */
    ExchangeCode selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,exchange_code
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ExchangeCode record, @Param("example") ExchangeCodeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,exchange_code
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ExchangeCode record, @Param("example") ExchangeCodeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,exchange_code
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ExchangeCode record);

    /**
     *  根据主键来更新符合条件的数据库记录,exchange_code
     *
     * @param record
     */
    int updateByPrimaryKey(ExchangeCode record);
}