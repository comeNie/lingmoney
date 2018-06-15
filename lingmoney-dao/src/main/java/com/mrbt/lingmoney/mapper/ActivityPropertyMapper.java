package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ActivityProperty;
import com.mrbt.lingmoney.model.ActivityPropertyExample;
import com.mrbt.lingmoney.model.ActivityPropertyWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityPropertyMapper {
    /**
     *  根据指定的条件获取数据库记录数,activity_property
     *
     * @param example
     */
    long countByExample(ActivityPropertyExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,activity_property
     *
     * @param example
     */
    int deleteByExample(ActivityPropertyExample example);

    /**
     *  根据主键删除数据库的记录,activity_property
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,activity_property
     *
     * @param record
     */
    int insert(ActivityPropertyWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,activity_property
     *
     * @param record
     */
    int insertSelective(ActivityPropertyWithBLOBs record);

    /**
     * ,activity_property
     *
     * @param example
     */
    List<ActivityPropertyWithBLOBs> selectByExampleWithBLOBs(ActivityPropertyExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,activity_property
     *
     * @param example
     */
    List<ActivityProperty> selectByExample(ActivityPropertyExample example);

    /**
     *  根据指定主键获取一条数据库记录,activity_property
     *
     * @param id
     */
    ActivityPropertyWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,activity_property
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ActivityPropertyWithBLOBs record, @Param("example") ActivityPropertyExample example);

    /**
     * ,activity_property
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") ActivityPropertyWithBLOBs record, @Param("example") ActivityPropertyExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,activity_property
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ActivityProperty record, @Param("example") ActivityPropertyExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,activity_property
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ActivityPropertyWithBLOBs record);

    /**
     * ,activity_property
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(ActivityPropertyWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,activity_property
     *
     * @param record
     */
    int updateByPrimaryKey(ActivityProperty record);
}