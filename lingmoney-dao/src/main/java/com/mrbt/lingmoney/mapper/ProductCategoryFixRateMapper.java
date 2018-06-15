package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.model.ProductCategoryFixRateExample;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductCategoryFixRateMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_category_fix_rate
     *
     * @param example
     */
    long countByExample(ProductCategoryFixRateExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_category_fix_rate
     *
     * @param example
     */
    int deleteByExample(ProductCategoryFixRateExample example);

    /**
     *  根据主键删除数据库的记录,product_category_fix_rate
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_category_fix_rate
     *
     * @param record
     */
    int insert(ProductCategoryFixRate record);

    /**
     *  动态字段,写入数据库记录,product_category_fix_rate
     *
     * @param record
     */
    int insertSelective(ProductCategoryFixRate record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_category_fix_rate
     *
     * @param example
     */
    List<ProductCategoryFixRate> selectByExample(ProductCategoryFixRateExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_category_fix_rate
     *
     * @param id
     */
    ProductCategoryFixRate selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_category_fix_rate
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductCategoryFixRate record, @Param("example") ProductCategoryFixRateExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_category_fix_rate
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductCategoryFixRate record, @Param("example") ProductCategoryFixRateExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_category_fix_rate
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductCategoryFixRate record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_category_fix_rate
     *
     * @param record
     */
    int updateByPrimaryKey(ProductCategoryFixRate record);

    /**
     * 根据产品ID和天数查询利率
     * @param product_category_fix_rate
     * @return
     */
	BigDecimal findRate(ProductCategoryFixRate product_category_fix_rate);
}