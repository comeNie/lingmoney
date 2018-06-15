package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductParam;
import com.mrbt.lingmoney.model.ProductParamExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductParamMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_param
     *
     * @param example
     */
    long countByExample(ProductParamExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_param
     *
     * @param example
     */
    int deleteByExample(ProductParamExample example);

    /**
     *  根据主键删除数据库的记录,product_param
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_param
     *
     * @param record
     */
    int insert(ProductParam record);

    /**
     *  动态字段,写入数据库记录,product_param
     *
     * @param record
     */
    int insertSelective(ProductParam record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_param
     *
     * @param example
     */
    List<ProductParam> selectByExample(ProductParamExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_param
     *
     * @param id
     */
    ProductParam selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_param
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductParam record, @Param("example") ProductParamExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_param
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductParam record, @Param("example") ProductParamExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_param
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductParam record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_param
     *
     * @param record
     */
    int updateByPrimaryKey(ProductParam record);

    /**
     * 查询节假日列表
     * @return
     */
	List<String> findHolidayList();

}