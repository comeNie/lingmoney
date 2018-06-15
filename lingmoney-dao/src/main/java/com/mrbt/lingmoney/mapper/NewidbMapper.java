package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Newidb;
import com.mrbt.lingmoney.model.NewidbExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewidbMapper {
    /**
     *  根据指定的条件获取数据库记录数,newidb
     *
     * @param example
     */
    long countByExample(NewidbExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,newidb
     *
     * @param example
     */
    int deleteByExample(NewidbExample example);

    /**
     *  新写入数据库记录,newidb
     *
     * @param record
     */
    int insert(Newidb record);

    /**
     *  动态字段,写入数据库记录,newidb
     *
     * @param record
     */
    int insertSelective(Newidb record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,newidb
     *
     * @param example
     */
    List<Newidb> selectByExample(NewidbExample example);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,newidb
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Newidb record, @Param("example") NewidbExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,newidb
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Newidb record, @Param("example") NewidbExample example);
}