package com.mrbt.lingmoney.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 单笔提现结果查询相关接口
 * 
 * @author lihq
 * @date 2017年6月6日 上午9:48:13
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface CashWithdrawResultService {

	/**
	 * 用户发起单笔提现结果查询请求
	 * 
	 * @param appId 
	 * @param oldReqseqno 原请求流水号
	 * @param uId 用户id
	 * @return pageinfo
	 * @throws Exception 
	 */
	PageInfo queryAccountWithdraw(String appId, String oldReqseqno, String uId) throws Exception;
	
}
