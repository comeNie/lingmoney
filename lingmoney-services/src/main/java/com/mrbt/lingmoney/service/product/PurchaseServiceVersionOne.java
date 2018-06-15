package com.mrbt.lingmoney.service.product;

import com.mrbt.lingmoney.utils.PageInfo;

/**
  *
  *@author yiban
  *@date 2018年3月13日 上午9:22:34
  *@version 1.0
 **/
public interface PurchaseServiceVersionOne {

    /**
     * 购买验证新版（20180309）
     * 
     * @param uid 用户ID
     * @param pCode 产品CODE
     * @param buyMoney 购买金额
     * @param platForm 购买平台 0 WEB 1 MOBILE
     * @param userRedPacketId 使用优惠券id
     * @return pageinfo
     */
    PageInfo validBuyProduct(String uid, String pCode, String buyMoney, int platForm, Integer userRedPacketId);
}
