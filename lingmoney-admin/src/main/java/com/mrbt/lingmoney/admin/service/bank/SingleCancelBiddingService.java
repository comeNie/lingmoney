package com.mrbt.lingmoney.admin.service.bank;

import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 6.20 单笔撤标相关接口
 * 
 * @author lihq
 * @date 2017年6月6日 下午5:02:31
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public interface SingleCancelBiddingService {

	/**
	 * 用户发起单笔撤标请求
	 * 
	 * @param clientType	客户端类型
	 * @param appId	appId
	 * @param tId	tId
	 * @param cancelReason	cancelReason
	 * @return	return
	 * @throws Exception	Exception
	 */
	PageInfo requestSingleCancelBidding(int clientType, String appId, int tId, String cancelReason) throws Exception;

	/**
	 * 
	 * @description 撤标成功处理
	 * @author syb
	 * @date 2017年8月14日 上午10:27:13
	 * @version 1.0
	 * @param logGroup
	 *            日志头
	 * @param loanNo
	 *            接口编号
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @throws Exception	Exception
	 */
	void handelCancelBidding(String logGroup, String loanNo, String uid, Integer tid) throws Exception;

	/**
	 * 
	 * @description 查询撤标结果
	 * @author syb
	 * @date 2017年8月14日 上午9:39:45
	 * @version 1.0
	 * @param appId	appId
	 * @param tid	交易ID
	 * @param logGroup 	统一日志头
	 * @return	return
	 *
	 */
	PageInfo querySingleBiddingCancelResult(String appId, Integer tid, String logGroup);
}
