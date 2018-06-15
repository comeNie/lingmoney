package com.mrbt.lingmoney.admin.service.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.lingmoney.model.GiftExchangeInfo;
import com.mrbt.lingmoney.model.GiftExchangeInfoNew;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 产品设置——》拉新活动礼品兑换信息
 */
public interface GiftExchangeInfoService {

	/**
	 * 定时查询满足送话费条件的记录，排除gift_exchange_info中已经存在的记录，批量插入gift_exchange_info表
	 * 
	 * @param category
	 *            数据id
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	void batchInsertGiftExchangeInfo(int category);

	/**
	 * 查询数据
	 * 
	 * @param pageInfo
	 *            PageInfo
	 */
	void findDataGrid(PageInfo pageInfo);

	/**
	 * 发货
	 * 
	 * @param vo
	 *            GiftExchangeInfo
	 * @return 数据返回
	 */
	PageInfo processingDelivery(GiftExchangeInfo vo);

	/**
	 * 确认收货
	 * 
	 * @param ids
	 *            ids
	 * @return 数据返回
	 */
	PageInfo comfirmReceipt(String ids);

	/**
	 * 导出到excel
	 * 
	 * @description
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @param json
	 *            后台查询后的rows组成的json串
	 * @return 数据返回
	 */
	PageInfo exportExcel(HttpServletRequest request, HttpServletResponse response, String json);

	/**
	 * 发货（拉新第二季）
	 */
    PageInfo processingDeliveryNew(GiftExchangeInfoNew vo);

    /**
     * 确认收货 （拉新第二季）
     */
    PageInfo comfirmReceiptNew(String ids);

}
