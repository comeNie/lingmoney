package com.mrbt.lingmoney.admin.service.info;

import com.mrbt.lingmoney.model.SupportBank;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author luox
 * @Date 2017年5月11日
 */
public interface SupportBankService {

	/**
	 * 删除
	 * 
	 * @param id
	 *            id
	 */
	void delete(Integer id);

	/**
	 * 列表
	 * 
	 * @param vo
	 *            vo
	 * @param pageInfo
	 *            PageInfo
	 */
	void listGrid(SupportBank vo, PageInfo pageInfo);

	/**
	 * 保存
	 * 
	 * @param vo
	 *            SupportBank
	 */
	void save(SupportBank vo);

	/**
	 * 更新
	 * 
	 * @param vo
	 *            SupportBank
	 */
	void update(SupportBank vo);

	/**
	 * 更新订单
	 * 
	 * @param vo
	 *            SupportBank
	 */
	void updateOrder(SupportBank vo);

	/**
	 * 获取最新的Order
	 * 
	 * @return 数据返回
	 */
	Integer getOrderOrder();

	/**
	 * 添加
	 * 
	 * @param bankcode
	 *            银行CODE
	 * @return 数据返回
	 */
	Integer isExistsBankCode(String bankcode);

	/**
	 * 编辑
	 * 
	 * @param bankcode
	 *            银行CODE
	 * @param id
	 *            数据ID
	 * @return 数据返回
	 */
	Integer isExistsBankCodeEdit(String bankcode, int id);

	/**
	 * 添加银行维护状态
	 * 
	 * @param bankId
	 *            数据ID
	 * @return 数据返回
	 */
	Integer appendBankMaintain(Integer bankId);

	/**
	 * 删除
	 * 
	 * @param bankId
	 *            数据id
	 * @return 数据返回
	 */
	Integer deleteBankMaintain(Integer bankId);

	/**
	 * 停止购买随心取
	 * 
	 * @param status
	 *            状态
	 * @param msg
	 *            提示语
	 * @return 数据返回
	 */
	Integer takeHeartAllowBuy(Integer status, String msg);

}
