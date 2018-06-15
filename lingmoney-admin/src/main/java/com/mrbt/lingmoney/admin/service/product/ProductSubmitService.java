package com.mrbt.lingmoney.admin.service.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.ProductSubmit;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 产品设置——》产品确认流程表
 *
 */
public interface ProductSubmitService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 查询列表
	 * 
	 * @param type
	 *            类型
	 * @param rowBounds
	 *            条数
	 * @return 列表
	 */
	GridPage<ProductSubmit> listGridType(int type, RowBounds rowBounds);

	/**
	 * 查询
	 * 
	 * @param vo
	 *            封装
	 * @param rowBounds
	 *            条数
	 * @return 列表
	 */
	GridPage<ProductSubmit> listGrid(ProductSubmit vo, RowBounds rowBounds);

	/**
	 * 更新
	 * 
	 * @param session
	 *            session
	 * @param vo
	 *            封装
	 * @param request
	 *            request
	 * @return 结果
	 */
	int update(ProductSubmit vo, HttpServletRequest request, HttpSession session);

}
