package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Templet;
import com.mrbt.lingmoney.model.TempletExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TempletMapper {
    /**
     *  根据指定的条件获取数据库记录数,templet
     *
     * @param example
     */
    int countByExample(TempletExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,templet
     *
     * @param example
     */
    int deleteByExample(TempletExample example);

    /**
     *  根据主键删除数据库的记录,templet
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,templet
     *
     * @param record
     */
    int insert(Templet record);

    /**
     *  动态字段,写入数据库记录,templet
     *
     * @param record
     */
    int insertSelective(Templet record);

    /**
     * ,templet
     *
     * @param example
     */
    List<Templet> selectByExampleWithBLOBs(TempletExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,templet
     *
     * @param example
     */
    List<Templet> selectByExample(TempletExample example);

    /**
     *  根据指定主键获取一条数据库记录,templet
     *
     * @param id
     */
    Templet selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,templet
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Templet record, @Param("example") TempletExample example);

    /**
     * ,templet
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") Templet record, @Param("example") TempletExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,templet
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Templet record, @Param("example") TempletExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,templet
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Templet record);

    /**
     * ,templet
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(Templet record);

    /**
     *  根据主键来更新符合条件的数据库记录,templet
     *
     * @param record
     */
    int updateByPrimaryKey(Templet record);
}