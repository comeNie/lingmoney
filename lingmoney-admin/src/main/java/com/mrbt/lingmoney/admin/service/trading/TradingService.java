package com.mrbt.lingmoney.admin.service.trading;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.utils.GridPage;

/**
 * 
 * 交易
 *
 */
public interface TradingService {

	/**
	 * 根据条件查询稳赢宝交易记录
	 * 
	 * @param map
	 *            参数集合
	 * @return 数据返回
	 */
	GridPage<Map<String, Object>> listGridWenYingBao(Map<String, Object> map);

	/**
	 * 稳赢宝提前兑付
	 * 
	 * @param parseInt
	 *            交易ID
	 * @param minSellDt
	 *            最小兑付时间
	 * @return 数据返回
	 */
	boolean cashAhead(int parseInt, String minSellDt);

	/**
	 * 查询交易记录
	 * 
	 * @param id
	 *            交易记录id
	 * @return 数据返回
	 */
	Trading findByPk(int id);

	/**
	 * 查询
	 * 
	 * @param key
	 *            key
	 * @return 数据返回
	 */
	boolean checkTakeHeart(String key);

	/**
	 * 设置
	 * 
	 * @param key
	 *            key
	 */
	void setTakeHeart(String key);

	/**
	 * 删除
	 * 
	 * @param key
	 *            key
	 */
	void delTakeHeart(String key);

	/**
	 * 浮动产品的计算
	 * 
	 * @param tid
	 *            交易id
	 * @return 数据返回
	 */
	BigDecimal getFolatFeesRate(int tid);

	/**
	 * 查询购买时间超过十五分钟，并且状态为0（买入）的交易记录
	 * 
	 * @Description
	 * @return 数据返回
	 */
	List<Trading> selectPayFailList();

	/**
	 * 更新产品已购金额，更新交易状态
	 * 
	 * @param id
	 *            id
	 * @param getpId
	 *            getpId
	 * @param buyMoney
	 *            金额
	 */
	void updateProductReachMoney(Integer id, Integer getpId, BigDecimal buyMoney);

	/**
	 * 流标处理
	 * 
	 * @param loanNo
	 *            借款编号
	 */
	void handelBiddingLoss(String loanNo);

	/**
	 * 提前兑付，重新计算加息金额
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @return 数据返回
	 */
	boolean cashAheadRecountRedPacket(String uid, Integer tid);

    /**
	 * 赎回
	 * 
	 * @param uid
	 *            用户id
	 * @param tid
	 *            交易id
	 * @param moneyInput
	 *            赎回金额
	 * @param redeemType
	 *            赎回类型
	 * @param logGroup
	 *            交易流水
	 * @return 返回数据
	 */
    SellResult sellProduct(String uid, Integer tid, Double moneyInput, Integer redeemType, String logGroup);

    /**
	 * 支付超时处理
	 * 
	 * @author yiban
	 * @date 2017年11月28日 上午11:04:09
	 * @version 1.0
	 * @param trading
	 *            交易
	 *
	 */
    void handelTimeoutTrading(Trading trading);
}
