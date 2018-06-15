package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.Footprint;
import com.mrbt.lingmoney.model.FootprintExample;

public interface FootprintMapper {
    /**
     *  根据指定的条件获取数据库记录数,footprint
     *
     * @param example
     */
    int countByExample(FootprintExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,footprint
     *
     * @param example
     */
    int deleteByExample(FootprintExample example);

    /**
     *  根据主键删除数据库的记录,footprint
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,footprint
     *
     * @param record
     */
    int insert(Footprint record);

    /**
     *  动态字段,写入数据库记录,footprint
     *
     * @param record
     */
    int insertSelective(Footprint record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,footprint
     *
     * @param example
     */
    List<Footprint> selectByExample(FootprintExample example);

    /**
     *  根据指定主键获取一条数据库记录,footprint
     *
     * @param id
     */
    Footprint selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,footprint
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Footprint record, @Param("example") FootprintExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,footprint
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Footprint record, @Param("example") FootprintExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,footprint
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Footprint record);

    /**
     *  根据主键来更新符合条件的数据库记录,footprint
     *
     * @param record
     */
    int updateByPrimaryKey(Footprint record);
}