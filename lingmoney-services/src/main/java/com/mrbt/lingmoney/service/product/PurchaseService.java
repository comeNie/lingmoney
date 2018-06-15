package com.mrbt.lingmoney.service.product;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年4月12日 上午11:29:42
 * @version 1.0
 * @description
 **/
public interface PurchaseService {

	/**
	 * 购买验证
	 * 
	 * @param uid 用户ID
	 * @param pCode 产品CODE
	 * @param buyMoney 购买金额
	 * @param platForm 购买平台 0 WEB 1 MOBILE
	 * @param userRedPacketId 使用优惠券id
	 * @return pageinfo
	 */
	PageInfo validBuyProduct(String uid, String pCode, String buyMoney, Integer platForm, Integer userRedPacketId);

	/**
	 * 验证购买用户信息
	 * 
	 * @param pCode 产品code
	 * @param uid 用户id
	 * @param buyMoney 购买金额
	 * @return pageinfo
	 */
	PageInfo validBuyerInfo(String uid, String buyMoney, String pCode);

}
