package com.mrbt.lingmoney.admin.service.product;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.ProductCategoryFixRate;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置——》固定产品分类收益表
 *
 */
public interface ProductCategoryFixRateService {

	/**
	 * 列表
	 * 
	 * @param vo
	 *            ProductCategoryFixRate
	 * @param rowBounds
	 *            RowBounds
	 * @return 数据返回
	 */
	GridPage<ProductCategoryFixRate> listGrid(ProductCategoryFixRate vo, RowBounds rowBounds);

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据id
	 */
	void delete(int int1);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            ProductCategoryFixRate
	 */
	void save(ProductCategoryFixRate vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            ProductCategoryFixRate
	 */
	void update(ProductCategoryFixRate vo);

}
