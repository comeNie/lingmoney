package com.mrbt.lingmoney.admin.service.bank;

import java.math.BigDecimal;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 单笔投标结果查询相关接口
 * 
 * @author lihq
 * @date 2017年6月6日 下午4:24:29
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface SingleBiddingResultService {

	/**
	 * 单笔投标结果查询
	 * 
	 * @param clientType	clientType
	 * @param appId	appId
	 * @param uId	uId
	 * @param oldReqseqno	oldReqseqno
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo requestSingleBiddingResult(int clientType, String appId, String uId, String oldReqseqno)
			throws Exception;

	/**
	 * 处理京东支付的订单
	 * @param id	交易ID
	 * @param getpId	产品ID
	 * @param buyMoney	购买金额
	 */
	void updateProductReachMoney(Integer id, Integer getpId,
			BigDecimal buyMoney);

}
