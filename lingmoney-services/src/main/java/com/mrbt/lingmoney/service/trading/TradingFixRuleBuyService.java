package com.mrbt.lingmoney.service.trading;

import java.math.BigDecimal;

import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 稳赢宝购买 接口
 * 
 * @author yb
 *
 */
public interface TradingFixRuleBuyService {
	/**
	 * 购买带规则的固定类产品
	 * 
	 * @param prCode
	 *            产品code
	 * @param uid
	 *            用户id
	 * @param platForm
	 *            操作平台 0PC 1APP
	 * @param buyMoney
	 *            购买金额
	 * @param minute
	 *            支付有效时间
	 * @param userRedPacketId
	 *            用户优惠券id
	 * @return pageinfo
	 */
	PageInfo buy(String prCode, String uid, Integer platForm, BigDecimal buyMoney, Integer minute,
			Integer userRedPacketId);

	/**
	 * 处理购买产品
	 * 
	 * @param tId 交易id
	 * @param bizCode 交易流水号
	 * @param uId 用户id
	 * @param flag 处理类型
	 * @return true处理成功  false处理失败
	 */
    boolean handleBuyProduct(Integer tId, String bizCode, String uId, CallbackType flag);

	/**
	 * 产品成立
	 * 
	 * @param lastTrading 最后一次交易信息
	 * @param manual 是否手点成立
	 * @return true 成功   false 失败
	 */
	boolean updateProductByRun(Trading lastTrading, boolean manual);

    /**
     * 处理购买产品
     * 
     * @param tId 交易id
     * @param bizCode 交易流水号
     * @param uId 用户id
     * @param flag 处理类型
     * @return true处理成功  false处理失败
     */
    boolean handleBuyProductVersionOne(Integer tId, String bizCode, String uId, CallbackType trading);

    /**
     * 购买带规则的固定类产品
     * 
     * @param prCode
     *            产品code
     * @param uid
     *            用户id
     * @param platForm
     *            操作平台 0PC 1APP
     * @param buyMoney
     *            购买金额
     * @param minute
     *            支付有效时间
     * @param userRedPacketId
     *            用户优惠券id
     * @return pageinfo
     */
    PageInfo buyVersionOne(String pCode, String uid, int platForm, BigDecimal buyMoney, Integer minute,
            Integer userRedPacketId);
}
