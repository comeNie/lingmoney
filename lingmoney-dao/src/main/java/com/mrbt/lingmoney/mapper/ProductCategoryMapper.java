package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductCategory;
import com.mrbt.lingmoney.model.ProductCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductCategoryMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_category
     *
     * @param example
     */
    long countByExample(ProductCategoryExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_category
     *
     * @param example
     */
    int deleteByExample(ProductCategoryExample example);

    /**
     *  根据主键删除数据库的记录,product_category
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_category
     *
     * @param record
     */
    int insert(ProductCategory record);

    /**
     *  动态字段,写入数据库记录,product_category
     *
     * @param record
     */
    int insertSelective(ProductCategory record);

    /**
     * ,product_category
     *
     * @param example
     */
    List<ProductCategory> selectByExampleWithBLOBs(ProductCategoryExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_category
     *
     * @param example
     */
    List<ProductCategory> selectByExample(ProductCategoryExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_category
     *
     * @param id
     */
    ProductCategory selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_category
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    /**
     * ,product_category
     *
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_category
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_category
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductCategory record);

    /**
     * ,product_category
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(ProductCategory record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_category
     *
     * @param record
     */
    int updateByPrimaryKey(ProductCategory record);
}