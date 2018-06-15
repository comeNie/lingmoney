package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Inserttest;
import com.mrbt.lingmoney.model.InserttestExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InserttestMapper {
    /**
     *  根据指定的条件获取数据库记录数,inserttest
     *
     * @param example
     */
    long countByExample(InserttestExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,inserttest
     *
     * @param example
     */
    int deleteByExample(InserttestExample example);

    /**
     *  根据主键删除数据库的记录,inserttest
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,inserttest
     *
     * @param record
     */
    int insert(Inserttest record);

    /**
     *  动态字段,写入数据库记录,inserttest
     *
     * @param record
     */
    int insertSelective(Inserttest record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,inserttest
     *
     * @param example
     */
    List<Inserttest> selectByExample(InserttestExample example);

    /**
     *  根据指定主键获取一条数据库记录,inserttest
     *
     * @param id
     */
    Inserttest selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,inserttest
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Inserttest record, @Param("example") InserttestExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,inserttest
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Inserttest record, @Param("example") InserttestExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,inserttest
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Inserttest record);

    /**
     *  根据主键来更新符合条件的数据库记录,inserttest
     *
     * @param record
     */
    int updateByPrimaryKey(Inserttest record);
}