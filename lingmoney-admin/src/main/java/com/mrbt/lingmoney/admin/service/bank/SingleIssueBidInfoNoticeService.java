package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 6.11 单笔发标信息通知
 * 
 * @author lihq
 * @date 2017年6月6日 下午1:49:06
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface SingleIssueBidInfoNoticeService {

    /**
     * 单笔发标信息通知
     * @param appId		appId	
     * @param biddingId	biddingId
     * @return	return
     * @throws Exception	Exception
     */
	PageInfo requestSingleIssueBidInfoNotice(String appId, String biddingId) throws Exception;

}
