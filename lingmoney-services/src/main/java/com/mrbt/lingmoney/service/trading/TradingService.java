package com.mrbt.lingmoney.service.trading;

import java.util.Map;

import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * @author syb
 * @date 2017年5月8日 上午10:30:11
 * @version 1.0
 * @description
 **/
public interface TradingService {

	/**
	 * 查询所有所有购买该产品用户记录
	 * 
	 * @param pid
	 *            产品ID
	 * @param pageNo
	 *            分页
	 * @return financialMoney 理财金额 buyDt 理财时间 name,nickName 理财人
	 */
	GridPage<Map<String, Object>> listTRUserGrid(int pid, Integer pageNo);

	/**
	 * 赎回
	 * @param uid 用户id
	 * @param tid 交易id
	 * @param moneyInput  赎回金额
	 * @param redeemType  赎回类型
	 * @param logGroup 日志头
	 * @return sellresult
	 */
	SellResult sellProduct(String uid, Integer tid, Double moneyInput, Integer redeemType, String logGroup);

	/**
	 * 取消支付--目前只有针对固定交易类(trading_fix_info)操作，后续添加新类型需要修改此接口逻辑
	 * 
	 * @param uid 用户id
	 * @param tId 交易id
	 * @return pageinfo 
	 */
	PageInfo cancelPayment(String uid, Integer tId);

	/**
	 * 查询用户未支付订单，返回订单信息及剩余可够时间
	 * 
	 * @param uid 用户id
	 * @return pageinfo
	 */
	PageInfo unPayOrder(String uid);

	/**
	 * 获取合同id
	 * 
	 * @param uid 用户id
	 * @return 合同id
	 */
	String getContractId(String uid);
	
	/**
	 * 根据用户id查询订单ID
	 * @param uid 用户id
	 * @return 订单id
	 */
	String getTidByUserId(String uid);
	
	/**
	 * 查询
	 * 
	 * @param uid 用户id
	 * @return 交易信息
	 *
	 */
	Trading selectByExample(String uid);

    /**
     * 新版取消支付--目前只有针对固定交易类(trading_fix_info)操作，后续添加新类型需要修改此接口逻辑
     * 
     * @param uid 用户id
     * @param tId 交易id
     * @return pageinfo 
     */
    PageInfo cancelPaymentVersionOne(String uid, Integer tId);

}
