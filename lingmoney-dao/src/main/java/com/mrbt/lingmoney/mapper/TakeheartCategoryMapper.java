package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.TakeheartCategory;
import com.mrbt.lingmoney.model.TakeheartCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TakeheartCategoryMapper {
    /**
     *  根据指定的条件获取数据库记录数,takeheart_category
     *
     * @param example
     */
    long countByExample(TakeheartCategoryExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,takeheart_category
     *
     * @param example
     */
    int deleteByExample(TakeheartCategoryExample example);

    /**
     *  根据主键删除数据库的记录,takeheart_category
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,takeheart_category
     *
     * @param record
     */
    int insert(TakeheartCategory record);

    /**
     *  动态字段,写入数据库记录,takeheart_category
     *
     * @param record
     */
    int insertSelective(TakeheartCategory record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,takeheart_category
     *
     * @param example
     */
    List<TakeheartCategory> selectByExample(TakeheartCategoryExample example);

    /**
     *  根据指定主键获取一条数据库记录,takeheart_category
     *
     * @param id
     */
    TakeheartCategory selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,takeheart_category
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") TakeheartCategory record, @Param("example") TakeheartCategoryExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,takeheart_category
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") TakeheartCategory record, @Param("example") TakeheartCategoryExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,takeheart_category
     *
     * @param record
     */
    int updateByPrimaryKeySelective(TakeheartCategory record);

    /**
     *  根据主键来更新符合条件的数据库记录,takeheart_category
     *
     * @param record
     */
    int updateByPrimaryKey(TakeheartCategory record);

    /**
     * 根据上限查询下一档信息
     * @param lowerLimit
     * @return
     */
	TakeheartCategory queryNextLevelByUpperLimit(int lowerLimit);
}