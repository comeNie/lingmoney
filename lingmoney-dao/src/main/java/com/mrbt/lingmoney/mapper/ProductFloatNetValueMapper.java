package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductFloatNetValue;
import com.mrbt.lingmoney.model.ProductFloatNetValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductFloatNetValueMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_float_net_value
     *
     * @param example
     */
    long countByExample(ProductFloatNetValueExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_float_net_value
     *
     * @param example
     */
    int deleteByExample(ProductFloatNetValueExample example);

    /**
     *  根据主键删除数据库的记录,product_float_net_value
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_float_net_value
     *
     * @param record
     */
    int insert(ProductFloatNetValue record);

    /**
     *  动态字段,写入数据库记录,product_float_net_value
     *
     * @param record
     */
    int insertSelective(ProductFloatNetValue record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_float_net_value
     *
     * @param example
     */
    List<ProductFloatNetValue> selectByExample(ProductFloatNetValueExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_float_net_value
     *
     * @param id
     */
    ProductFloatNetValue selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_float_net_value
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductFloatNetValue record, @Param("example") ProductFloatNetValueExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_float_net_value
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductFloatNetValue record, @Param("example") ProductFloatNetValueExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_float_net_value
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductFloatNetValue record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_float_net_value
     *
     * @param record
     */
    int updateByPrimaryKey(ProductFloatNetValue record);
}