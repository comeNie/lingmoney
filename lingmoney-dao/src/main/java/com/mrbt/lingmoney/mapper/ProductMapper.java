package com.mrbt.lingmoney.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.ProductExample;
import com.mrbt.lingmoney.model.ProductWithBLOBs;

public interface ProductMapper {
    /**
     *  根据指定的条件获取数据库记录数,product
     * @param example
     */
    Integer countByExample(ProductExample example);

    /**
     *  根据指定的条件删除数据库符合条件的记录,product
     * @param example
     */
    int deleteByExample(ProductExample example);

    /**
     *  根据主键删除数据库的记录,product
     * @param id
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *  新写入数据库记录,product
     * @param record
     */
    int insert(ProductWithBLOBs record);

    /**
     *  动态字段,写入数据库记录,product
     * @param record
     */
    int insertSelective(ProductWithBLOBs record);

    /**
     * ,product
     * @param example
     */
    List<ProductWithBLOBs> selectByExampleWithBLOBs(ProductExample example);

    /**
     *  根据指定的条件查询符合条件的数据库记录,product
     * @param example
     */
    List<Product> selectByExample(ProductExample example);

    /**
     *  根据指定主键获取一条数据库记录,product
     * @param id
     */
    ProductWithBLOBs selectByPrimaryKey(Integer id);

    /**
     *  动态根据指定的条件来更新符合条件的数据库记录,product
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") ProductWithBLOBs record, @Param("example") ProductExample example);

    /**
     * ,product
     * @param record
     * @param example
     */
    int updateByExampleWithBLOBs(@Param("record") ProductWithBLOBs record, @Param("example") ProductExample example);

    /**
     *  根据指定的条件来更新符合条件的数据库记录,product
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    /**
     *  动态字段,根据主键来更新符合条件的数据库记录,product
     * @param record
     */
    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    /**
     * ,product
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    /**
     *  根据主键来更新符合条件的数据库记录,product
     * @param record
     */
    int updateByPrimaryKey(Product record);

	/**
	 * 获取产品信息
	 * 
	 * @param code
	 *            code
	 * @return return
	 */
	Product selectProductByCode(String code);
}