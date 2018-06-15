package com.mrbt.lingmoney.admin.service.product;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.ProductParam;

/**
 * 
 * 产品设置——》产品参数表 产品分类表的分类信息，现在没有用到 节假日信息：现在在用
 *
 */
public interface ProductParamService {

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据ID
	 */
	void delete(int int1);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            ProductParam
	 */
	void update(ProductParam vo);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            ProductParam
	 * @param type
	 *            type
	 * @throws Exception
	 *             Exception
	 */
	void save(ProductParam vo, String type) throws Exception;

	/**
	 * 列表
	 * 
	 * @param name
	 *            name
	 * @param rowBounds
	 *            rowBounds
	 * @return 数据返回
	 */
	Object listGrid(String name, RowBounds rowBounds);

}
