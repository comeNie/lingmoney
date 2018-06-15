package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.ProductCategory;

/**
 * 
 * 产品设置——》产品分类表
 *
 */
public interface ProductCategoryService {

	/**
	 * 查询ProductCategory
	 * 
	 * @param int1
	 *            数据id
	 * @return 数据返回
	 */
	ProductCategory findByPk(int int1);

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据id
	 */
	void delete(int int1);

	/**
	 * 列表
	 * 
	 * @param vo
	 *            ProductCategory
	 * @param rowBounds
	 *            RowBounds
	 * @return 数据返回
	 */
	Object listGrid(ProductCategory vo, RowBounds rowBounds);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            ProductCategory
	 * @throws Exception
	 *             异常
	 */
	void save(ProductCategory vo) throws Exception;

	/**
	 * 更新
	 * 
	 * @param pc
	 *            ProductCategory
	 */
	void update(ProductCategory pc);

	/**
	 * ProductCategory集合
	 * 
	 * @param vo
	 *            ProductCategory
	 * @param offset
	 *            offset
	 * @param limit
	 *            limit
	 * @return 数据返回
	 */
	List<ProductCategory> list(ProductCategory vo, int offset, int limit);

}
