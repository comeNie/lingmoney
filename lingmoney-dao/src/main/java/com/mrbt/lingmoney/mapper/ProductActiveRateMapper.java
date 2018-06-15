package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductActiveRate;
import com.mrbt.lingmoney.model.ProductActiveRateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductActiveRateMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_active_rate
     *
     * @param example
     */
    long countByExample(ProductActiveRateExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_active_rate
     *
     * @param example
     */
    int deleteByExample(ProductActiveRateExample example);

    /**
     *  根据主键删除数据库的记录,product_active_rate
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_active_rate
     *
     * @param record
     */
    int insert(ProductActiveRate record);

    /**
     *  动态字段,写入数据库记录,product_active_rate
     *
     * @param record
     */
    int insertSelective(ProductActiveRate record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_active_rate
     *
     * @param example
     */
    List<ProductActiveRate> selectByExample(ProductActiveRateExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_active_rate
     *
     * @param id
     */
    ProductActiveRate selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_active_rate
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductActiveRate record, @Param("example") ProductActiveRateExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_active_rate
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductActiveRate record, @Param("example") ProductActiveRateExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_active_rate
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductActiveRate record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_active_rate
     *
     * @param record
     */
    int updateByPrimaryKey(ProductActiveRate record);
}