package com.mrbt.lingmoney.admin.service.product;

import org.apache.ibatis.session.RowBounds;

import com.mrbt.lingmoney.model.GiftDetail;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 产品设置———》礼品详情
 *
 */
public interface GiftDetailService {

	/**
	 * 查询列表
	 * 
	 * @param vo
	 *            GiftDetail
	 * @param rowBounds
	 *            RowBounds
	 * @return 数据返回
	 */
	GridPage<GiftDetail> listGrid(GiftDetail vo, RowBounds rowBounds);

	/**
	 * 删除
	 * 
	 * @param int1
	 *            数据id
	 * @return 数据返回
	 */
	int delete(int int1);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            GiftDetail
	 */
	void save(GiftDetail vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            GiftDetail
	 * @return 数据返回
	 */
	int update(GiftDetail vo);

	/**
	 * 更新状态
	 * 
	 * @param gid
	 *            gid
	 * @param vo
	 *            GiftDetail
	 */
	void updateStatus(String[] gid, GiftDetail vo);

}
