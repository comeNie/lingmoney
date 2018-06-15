package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.BayWindow;
import com.mrbt.lingmoney.model.BayWindowExample;

public interface BayWindowMapper {
    /**
     *  根据指定的条件获取数据库记录数,bay_window
     * @param example
     */
    long countByExample(BayWindowExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,bay_window
     * @param example
     */
    int deleteByExample(BayWindowExample example);

    /**
     *  根据主键删除数据库的记录,bay_window
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,bay_window
     * @param record
     */
    int insert(BayWindow record);

    /**
     *  动态字段,写入数据库记录,bay_window
     * @param record
     */
    int insertSelective(BayWindow record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,bay_window
     * @param example
     */
    List<BayWindow> selectByExample(BayWindowExample example);

    /**
     *  根据指定主键获取一条数据库记录,bay_window
     * @param id
     */
    BayWindow selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,bay_window
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") BayWindow record, @Param("example") BayWindowExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,bay_window
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") BayWindow record, @Param("example") BayWindowExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,bay_window
     * @param record
     */
    int updateByPrimaryKeySelective(BayWindow record);

    /**
     *  根据主键来更新符合条件的数据库记录,bay_window
     * @param record
     */
    int updateByPrimaryKey(BayWindow record);
}