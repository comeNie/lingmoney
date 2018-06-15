package com.mrbt.lingmoney.admin.service.base;

import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.HxPaymentBidBorrowUnionInfo;
import com.mrbt.lingmoney.model.Product;

/**
 * 验证服务
 * 
 * @author YJQ
 *
 */
public interface VerifyService {
	/**
	 * 验证注册账号
	 * 
	 * @author YJQ 2017年5月11日
	 * @param account
	 *            account
	 * @throws Exception
	 *             Exception
	 */
	void verifyAccount(String account) throws Exception;


	/**
	 * 验证是否为空
	 * 
	 * @author YJQ 2017年5月19日
	 * @param obj
	 *            obj
	 * @param msg
	 *            msg
	 * @throws Exception
	 *             Exception
	 */
	void verifyEmpty(Object obj, String msg) throws Exception;


	/**
	 * 验证redis存储结果
	 * 
	 * @author YJQ 2017年5月19日
	 * @param key
	 *            key
	 * @param targetVal
	 *            targetVal
	 * @throws Exception
	 *             Exception
	 */
	void verifyRedisCode(String key, String targetVal) throws Exception;

	/**
	 * 验证0/1标志项
	 * 
	 * @author YJQ 2017年5月19日
	 * @param val
	 *            val
	 * @throws Exception
	 *             Exception
	 */
	void verifyStatus(Integer val) throws Exception;
	
	/**
	 * 验证银行卡
	 * 
	 * @author YJQ 2017年6月5日
	 * @param accId
	 *            accId
	 * @throws Exception
	 *             Exception
	 */
	void verifyAcc(String accId) throws Exception;

	/**
	 * 验证华兴E账户
	 * 
	 * @author YJQ 2017年6月7日
	 * @param acNo
	 *            acNo
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	HxAccount verifyAcNo(String acNo) throws Exception;

	/**
	 * 验证金额格式
	 * 
	 * @author YJQ 2017年6月8日
	 * @param money
	 *            money
	 * @param msg
	 *            msg
	 * @throws Exception
	 *             Exception
	 */
	void verifyMoney(String money, String msg) throws Exception;

	/**
	 * 验证借款信息
	 * 
	 * @author YJQ 2017年6月9日
	 * @param lomoNo
	 *            借款编号
	 * @throws Exception
	 *             Exception
	 * @return 数据返回
	 */
	HxBidBorrowUnionInfo verifyBorrowInfo(String lomoNo) throws Exception;

	/**
	 * 验证还款流水号
	 * 
	 * @author YJQ 2017年6月19日
	 * @param channelFlow
	 *            channelFlow
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	HxPaymentBidBorrowUnionInfo verifyPaymentBorrowInfo(Object channelFlow) throws Exception;

	/**
	 * 验证还款状态
	 * 
	 * @author YJQ 2017年7月1日
	 * @param loanNo
	 *            loanNo
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	HxPaymentBidBorrowUnionInfo verifyPaymentStatus(String loanNo) throws Exception;

	/**
	 * 验证产品状态是否可以还款
	 * 
	 * @author YJQ 2017年8月3日
	 * @param pId
	 *            pId
	 * @return 数据返回
	 * @throws Exception
	 *             Exception
	 */
	Product verifyProductStatus(Integer pId) throws Exception;
}
