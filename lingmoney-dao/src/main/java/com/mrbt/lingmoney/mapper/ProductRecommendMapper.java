package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.ProductRecommend;
import com.mrbt.lingmoney.model.ProductRecommendExample;

public interface ProductRecommendMapper {
	/**
	 * 根据指定的条件获取数据库记录数,product_recommend
	 *
	 * @param example
	 */
	long countByExample(ProductRecommendExample example);

	/**
	 * 根据指定的条件删除数据库符合条件的记录,product_recommend
	 *
	 * @param example
	 */
	int deleteByExample(ProductRecommendExample example);

	/**
	 * 根据主键删除数据库的记录,product_recommend
	 *
	 * @param id
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 新写入数据库记录,product_recommend
	 *
	 * @param record
	 */
	int insert(ProductRecommend record);

	/**
	 * 动态字段,写入数据库记录,product_recommend
	 *
	 * @param record
	 */
	int insertSelective(ProductRecommend record);

	/**
	 * 根据指定的条件查询符合条件的数据库记录,product_recommend
	 *
	 * @param example
	 */
	List<ProductRecommend> selectByExample(ProductRecommendExample example);

	/**
	 * 根据指定主键获取一条数据库记录,product_recommend
	 *
	 * @param id
	 */
	ProductRecommend selectByPrimaryKey(Integer id);

	/**
	 * 动态根据指定的条件来更新符合条件的数据库记录,product_recommend
	 *
	 * @param record
	 * @param example
	 */
	int updateByExampleSelective(@Param("record") ProductRecommend record,
			@Param("example") ProductRecommendExample example);

	/**
	 * 根据指定的条件来更新符合条件的数据库记录,product_recommend
	 *
	 * @param record
	 * @param example
	 */
	int updateByExample(@Param("record") ProductRecommend record, @Param("example") ProductRecommendExample example);

	/**
	 * 动态字段,根据主键来更新符合条件的数据库记录,product_recommend
	 *
	 * @param record
	 */
	int updateByPrimaryKeySelective(ProductRecommend record);

	/**
	 * 根据主键来更新符合条件的数据库记录,product_recommend
	 *
	 * @param record
	 */
	int updateByPrimaryKey(ProductRecommend record);

}