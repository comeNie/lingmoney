package com.mrbt.lingmoney.service.bank;

import org.dom4j.Document;

import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 6.12 单笔投标相关接口
 * 
 * @author lihq
 * @date 2017年6月6日 下午2:48:39
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface SingleBiddingService {

	/**
	 * 用户发起单笔投标请求
	 * 
	 * @param clientType 客户端类型 0,PC 1,APP
	 * @param appId 应用标识
	 * @param tId 交易id
	 * @param uId 用户ID
	 * @return pageinfo 
	 * @throws Exception 
	 */
	PageInfo requestSingleBidding(int clientType, String appId, Integer tId, String uId) throws Exception;

	/**
	 * 单笔投标回调执行方法
	 * 
	 * @param document doc
	 * @return 应答报文
	 * @throws Exception 
	 */
	String singleBiddingCallBack(Document document) throws Exception;

	/**
	 * 测试方法
	 * 
	 * @param tId 交易id
	 * @param flag 回调类型枚举
	 * @return boolean
	 * @throws Exception 
	 */
	boolean singleBiddingCallBack(Integer tId, CallbackType flag) throws Exception;
	
	/**
	 * 购买稳赢宝送领宝
	 *
	 * @param uid 用户ID
	 * @param tid 交易id
	 */
    void buyWeiyingbaoGetLingbao(String uid, int tid);

    /**
     * 用户发起单笔投标请求（新版）
     * 
     * @param clientType 客户端类型 0,PC 1,APP
     * @param appId 应用标识
     * @param tId 交易id
     * @param uId 用户ID
     * @return pageinfo 
     * @throws Exception 
     */
    PageInfo requestSingleBiddingVersionOne(int clientType, String appId, Integer tId, String uId) throws Exception;
}
