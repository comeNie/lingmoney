package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.model.GiftProduct;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置——》活动产品关联礼品
 */
public interface GiftProductService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(int id);

	/**
	 * 查找，根据ProductGift
	 * 
	 * @param vo
	 *            ProductGift的实例
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            记录条数
	 * @return ProductGift的集合
	 */
	List<GiftProduct> list(GiftProduct vo, int offset, int limit);


	/**
	 * 生成datagrid表格需要的结果
	 * 
	 * @param vo
	 *            ProductGift实体bean
	 * @param page
	 *            翻页信息
	 * @return GridPage<ProductGift>
	 */
	GridPage<GiftProduct> listGrid(GiftProduct vo, RowBounds page);

	/**
	 * 保存实体bean
	 * 
	 * @param vo
	 *            GiftProduct
	 */
	@Transactional
	void save(GiftProduct vo);

	/**
	 * 更新实体bean
	 * 
	 * @param vo
	 *            GiftProduct
	 */
	@Transactional
	void update(GiftProduct vo);

	/**
	 * GiftProduct集合
	 * 
	 * @param vo
	 *            GiftProduct
	 * @return 数据返回
	 */
	List<GiftProduct> selectByPid(GiftProduct vo);

}
