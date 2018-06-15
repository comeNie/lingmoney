package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.UsresDegree;
import com.mrbt.lingmoney.model.UsresDegreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsresDegreeMapper {
    /**
     *  根据指定的条件获取数据库记录数,usres_degree
     *
     * @param example
     */
    long countByExample(UsresDegreeExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,usres_degree
     *
     * @param example
     */
    int deleteByExample(UsresDegreeExample example);

    /**
     *  根据主键删除数据库的记录,usres_degree
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,usres_degree
     *
     * @param record
     */
    int insert(UsresDegree record);

    /**
     *  动态字段,写入数据库记录,usres_degree
     *
     * @param record
     */
    int insertSelective(UsresDegree record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,usres_degree
     *
     * @param example
     */
    List<UsresDegree> selectByExample(UsresDegreeExample example);

    /**
     *  根据指定主键获取一条数据库记录,usres_degree
     *
     * @param id
     */
    UsresDegree selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,usres_degree
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") UsresDegree record, @Param("example") UsresDegreeExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,usres_degree
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") UsresDegree record, @Param("example") UsresDegreeExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,usres_degree
     *
     * @param record
     */
    int updateByPrimaryKeySelective(UsresDegree record);

    /**
     *  根据主键来更新符合条件的数据库记录,usres_degree
     *
     * @param record
     */
    int updateByPrimaryKey(UsresDegree record);
}