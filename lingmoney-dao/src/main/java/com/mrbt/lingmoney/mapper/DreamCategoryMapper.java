package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.DreamCategory;
import com.mrbt.lingmoney.model.DreamCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DreamCategoryMapper {
    /**
     *  根据指定的条件获取数据库记录数,dream_category
     *
     * @param example
     */
    long countByExample(DreamCategoryExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,dream_category
     *
     * @param example
     */
    int deleteByExample(DreamCategoryExample example);

    /**
     *  根据主键删除数据库的记录,dream_category
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,dream_category
     *
     * @param record
     */
    int insert(DreamCategory record);

    /**
     *  动态字段,写入数据库记录,dream_category
     *
     * @param record
     */
    int insertSelective(DreamCategory record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,dream_category
     *
     * @param example
     */
    List<DreamCategory> selectByExample(DreamCategoryExample example);

    /**
     *  根据指定主键获取一条数据库记录,dream_category
     *
     * @param id
     */
    DreamCategory selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,dream_category
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") DreamCategory record, @Param("example") DreamCategoryExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,dream_category
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") DreamCategory record, @Param("example") DreamCategoryExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,dream_category
     *
     * @param record
     */
    int updateByPrimaryKeySelective(DreamCategory record);

    /**
     *  根据主键来更新符合条件的数据库记录,dream_category
     *
     * @param record
     */
    int updateByPrimaryKey(DreamCategory record);
}