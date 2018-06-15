package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.model.ProductSubmitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductSubmitMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_submit
     *
     * @param example
     */
    long countByExample(ProductSubmitExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_submit
     *
     * @param example
     */
    int deleteByExample(ProductSubmitExample example);

    /**
     *  根据主键删除数据库的记录,product_submit
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_submit
     *
     * @param record
     */
    int insert(ProductSubmit record);

    /**
     *  动态字段,写入数据库记录,product_submit
     *
     * @param record
     */
    int insertSelective(ProductSubmit record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_submit
     *
     * @param example
     */
    List<ProductSubmit> selectByExample(ProductSubmitExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_submit
     *
     * @param id
     */
    ProductSubmit selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_submit
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductSubmit record, @Param("example") ProductSubmitExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_submit
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductSubmit record, @Param("example") ProductSubmitExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_submit
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductSubmit record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_submit
     *
     * @param record
     */
    int updateByPrimaryKey(ProductSubmit record);
}