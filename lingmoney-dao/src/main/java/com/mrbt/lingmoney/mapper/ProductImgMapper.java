package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductImg;
import com.mrbt.lingmoney.model.ProductImgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductImgMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_img
     *
     * @param example
     */
    long countByExample(ProductImgExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_img
     *
     * @param example
     */
    int deleteByExample(ProductImgExample example);

    /**
     *  根据主键删除数据库的记录,product_img
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_img
     *
     * @param record
     */
    int insert(ProductImg record);

    /**
     *  动态字段,写入数据库记录,product_img
     *
     * @param record
     */
    int insertSelective(ProductImg record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_img
     *
     * @param example
     */
    List<ProductImg> selectByExample(ProductImgExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_img
     *
     * @param id
     */
    ProductImg selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_img
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductImg record, @Param("example") ProductImgExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_img
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductImg record, @Param("example") ProductImgExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_img
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductImg record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_img
     *
     * @param record
     */
    int updateByPrimaryKey(ProductImg record);

}