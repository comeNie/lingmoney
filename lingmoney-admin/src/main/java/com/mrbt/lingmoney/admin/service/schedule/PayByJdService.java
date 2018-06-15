package com.mrbt.lingmoney.admin.service.schedule;

import java.math.BigDecimal;

import com.mrbt.lingmoney.admin.vo.trading.PayByJdResult;

/**
 *@author syb
 *@date 2017年6月27日 下午3:56:08
 *@version 1.0
 *@description 
 **/
public interface PayByJdService {
	/**
	 * 代付接口
	 * 
	 * @param cardBank
	 *            持卡人支付卡号发卡行 例如ABC（中国农业银行）
	 * @param cardNo
	 *            持卡人支付的卡号
	 * @param cardName
	 *            持卡人的姓名
	 * @param outTradeNo
	 *            交易流水号
	 * @param cardPhone
	 *            持卡人的手机号
	 * @param tradeAmount
	 *            交易的金额 *
	 * @param bizType
	 *            交易类型，(String)1是稳赢宝，2是随心取全部赎回，3是随心取部分赎回
	 * @param uid
	 *            用户id
	 * @param bizId
	 *            业务的id，可以记录trading的id，也可以记录流水表的id，程序员自己定'
	 * @return RebackResultVo 交易结果类
	 */
	PayByJdResult defrayPay(String cardBank, String cardNo, String cardName, String outTradeNo,
			String cardPhone, BigDecimal tradeAmount, String bizType, String uid, int bizId);

	/**
	 * 根据账号进行查询
	 * 
	 * @param outBizCode
	 *            outBizCode
	 * @return 返回数据
	 */
	PayByJdResult tradeQuery(String outBizCode);
}
