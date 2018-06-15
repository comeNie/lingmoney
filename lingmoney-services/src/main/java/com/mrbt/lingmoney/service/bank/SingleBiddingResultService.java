package com.mrbt.lingmoney.service.bank;

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
	 * @param clientType 客户端类型 0PC 1APP
	 * @param appId 应用标识 个人电脑:PC（不送则默认PC）手机：APP 微信：WX
	 * @param uId 用户id
	 * @param oldReqseqno 原请求流水号
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo requestSingleBiddingResult(int clientType, String appId, String uId, String oldReqseqno)
			throws Exception;

	/**
	 * 返回商户url
	 * @author YJQ  2017年8月1日
	 * @param appId 应用标识 个人电脑:PC（不送则默认PC）手机：APP 微信：WX 
	 * @param oldReqseqno 原请求流水号
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo requestSingleBiddingResult(String appId, String oldReqseqno) throws Exception;

}
