package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Ermidb;
import com.mrbt.lingmoney.model.ErmidbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErmidbMapper {
    /**
     *  根据指定的条件获取数据库记录数,ermidb
     *
     * @param example
     */
    long countByExample(ErmidbExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,ermidb
     *
     * @param example
     */
    int deleteByExample(ErmidbExample example);

    /**
     *  新写入数据库记录,ermidb
     *
     * @param record
     */
    int insert(Ermidb record);

    /**
     *  动态字段,写入数据库记录,ermidb
     *
     * @param record
     */
    int insertSelective(Ermidb record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,ermidb
     *
     * @param example
     */
    List<Ermidb> selectByExample(ErmidbExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,ermidb
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Ermidb record, @Param("example") ErmidbExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,ermidb
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Ermidb record, @Param("example") ErmidbExample example);
}