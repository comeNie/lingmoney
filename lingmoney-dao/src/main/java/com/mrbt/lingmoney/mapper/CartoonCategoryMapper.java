package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.CartoonCategory;
import com.mrbt.lingmoney.model.CartoonCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartoonCategoryMapper {
    /**
     *  根据指定的条件获取数据库记录数,cartoon_category
     * @param example
     */
    long countByExample(CartoonCategoryExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,cartoon_category
     * @param example
     */
    int deleteByExample(CartoonCategoryExample example);

    /**
     *  根据主键删除数据库的记录,cartoon_category
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,cartoon_category
     * @param record
     */
    int insert(CartoonCategory record);

    /**
     *  动态字段,写入数据库记录,cartoon_category
     * @param record
     */
    int insertSelective(CartoonCategory record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,cartoon_category
     * @param example
     */
    List<CartoonCategory> selectByExample(CartoonCategoryExample example);

    /**
     *  根据指定主键获取一条数据库记录,cartoon_category
     * @param id
     */
    CartoonCategory selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,cartoon_category
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") CartoonCategory record, @Param("example") CartoonCategoryExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,cartoon_category
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") CartoonCategory record, @Param("example") CartoonCategoryExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,cartoon_category
     * @param record
     */
    int updateByPrimaryKeySelective(CartoonCategory record);

    /**
     *  根据主键来更新符合条件的数据库记录,cartoon_category
     * @param record
     */
    int updateByPrimaryKey(CartoonCategory record);
}