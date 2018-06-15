package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 账户余额
 * @author luox
 * @Date 2017年6月5日
 */
public interface HxTenderService {

	/**
	 * 投标优惠返回 （此接口已废弃）
	 * 
	 * @param appId 应用标识 个人电脑:PC （不送则默认PC）手机：APP 微信：WX
	 * @param uId 用户id
	 * @param loanno 借款编号
	 * @param bwacname 账户名
	 * @param bwacno 账号
	 * @param amount 金额
	 * @param totalnum 
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
    @Deprecated
	PageInfo tenderReturn(String appId, String uId, String loanno, String bwacname, String bwacno, String amount,
			String totalnum) throws Exception;

	/**
	 * 投标优惠返回结果查询 （此接口已废弃）
	 * 
	 * @param appId 应用标识 个人电脑:PC （不送则默认PC）手机：APP 微信：WX
	 * @param uId 用户id
	 * @param oldreqseqno 原请求流水号
	 * @param subseqno 
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
    @Deprecated
	PageInfo tenderReturnSearch(String appId, String uId, String oldreqseqno, String subseqno)throws Exception;

	/**
	 * 自动投标授权结果查询
	 * 
	 * @param appId 应用标识 个人电脑:PC （不送则默认PC）手机：APP 微信：WX
	 * @param uId 用户id
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
    PageInfo tenderReturnSearch(String appId, String uId) throws Exception;

	/**
	 * 自动投标授权撤销
	 * 
	 * @param uId 用户id
	 * @param appId 应用标识 个人电脑:PC （不送则默认PC）手机：APP 微信：WX
	 * @param otpseqno 原请求流水号
	 * @param otpno 动态密码
	 * @param remark 备注
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo autoTenderAuthorizeCancel(String uId, String appId, String otpseqno, String otpno, String remark) throws Exception;

	/**
	 * 自动单笔投标
	 * 
	 * @param uId 用户id
	 * @param appId 应用标识 个人电脑:PC （不送则默认PC）手机：APP 微信：WX
	 * @param loanno 借款编号
	 * @param amount 金额
	 * @param remark 备注
	 * @return pageinfo
	 * @throws Exception 
	 *
	 */
	PageInfo autoTenderOne(String uId, String appId, String loanno, String amount, String remark) throws Exception;



}
