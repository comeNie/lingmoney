package com.mrbt.lingmoney.mapper;

import com.mrbt.lingmoney.model.ProductStatistics;
import com.mrbt.lingmoney.model.ProductStatisticsExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProductStatisticsMapper {
    /**
     *  根据指定的条件获取数据库记录数,product_statistics
     *
     * @param example
     */
    long countByExample(ProductStatisticsExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product_statistics
     *
     * @param example
     */
    int deleteByExample(ProductStatisticsExample example);

    /**
     *  根据主键删除数据库的记录,product_statistics
     *
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product_statistics
     *
     * @param record
     */
    int insert(ProductStatistics record);

    /**
     *  动态字段,写入数据库记录,product_statistics
     *
     * @param record
     */
    int insertSelective(ProductStatistics record);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product_statistics
     *
     * @param example
     */
    List<ProductStatistics> selectByExample(ProductStatisticsExample example);

    /**
     *  根据指定主键获取一条数据库记录,product_statistics
     *
     * @param id
     */
    ProductStatistics selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product_statistics
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductStatistics record, @Param("example") ProductStatisticsExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product_statistics
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") ProductStatistics record, @Param("example") ProductStatisticsExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product_statistics
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ProductStatistics record);

    /**
     *  根据主键来更新符合条件的数据库记录,product_statistics
     *
     * @param record
     */
    int updateByPrimaryKey(ProductStatistics record);

    /**
	 * 获取所有产品前日(凌晨24：00以后跑，所以是前日)销售汇总记录
	 * 
	 * @return
	 */
	List<ProductStatistics> findProductStatistics();

	/**
	 * 获取该产品的前一天统计记录
	 * 
	 * @param pid
	 * @return
	 */
	ProductStatistics findYesterdayRecommendLineStatistics(Integer pid);
}