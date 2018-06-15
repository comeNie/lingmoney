package com.mrbt.lingmoney.admin.service.product;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.Gift;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置——》礼品
 *
 */
public interface GiftService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 * @return 数据返回
	 */
	int delete(int id);

	/**
	 * 列表
	 * 
	 * @param vo
	 *            Gift
	 * @param rowBounds
	 *            RowBounds
	 * @return 数据返回
	 */
	GridPage<Gift> listGrid(Gift vo, RowBounds rowBounds);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            Gift
	 */
	void save(Gift vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            Gift
	 * @return 数据返回
	 */
	int update(Gift vo);

	/**
	 * Gift集合
	 * 
	 * @param vo
	 *            Gift
	 * @return 数据返回
	 */
	List<Gift> selectByGiftProduct(Gift vo);
}
