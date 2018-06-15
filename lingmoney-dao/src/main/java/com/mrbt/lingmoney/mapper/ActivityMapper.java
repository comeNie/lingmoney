package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.Activity;
import com.mrbt.lingmoney.model.ActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityMapper {
    /**
     *  根据指定的条件获取数据库记录数,activity
     *
     * @param example
     */
    long countByExample(ActivityExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,activity
     *
     * @param example
     */
    int deleteByExample(ActivityExample example);

    /**
     *  根据主键删除数据库的记录,activity
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,activity
     *
     * @param record
     */
    int insert(Activity record);

    /**
     *  动态字段,写入数据库记录,activity
     *
     * @param record
     */
    int insertSelective(Activity record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,activity
     *
     * @param example
     */
    List<Activity> selectByExample(ActivityExample example);

    /**
     *  根据指定主键获取一条数据库记录,activity
     *
     * @param id
     */
    Activity selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,activity
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,activity
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,activity
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     *  根据主键来更新符合条件的数据库记录,activity
     *
     * @param record
     */
    int updateByPrimaryKey(Activity record);
}