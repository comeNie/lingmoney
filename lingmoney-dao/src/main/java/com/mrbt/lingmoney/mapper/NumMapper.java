package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Num;
import com.mrbt.lingmoney.model.NumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NumMapper {
    /**
     *  根据指定的条件获取数据库记录数,num
     *
     * @param example
     */
    long countByExample(NumExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,num
     *
     * @param example
     */
    int deleteByExample(NumExample example);

    /**
     *  新写入数据库记录,num
     *
     * @param record
     */
    int insert(Num record);

    /**
     *  动态字段,写入数据库记录,num
     *
     * @param record
     */
    int insertSelective(Num record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,num
     *
     * @param example
     */
    List<Num> selectByExample(NumExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,num
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Num record, @Param("example") NumExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,num
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Num record, @Param("example") NumExample example);
}