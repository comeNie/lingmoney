package com.mrbt.lingmoney.model.trading.service;

import org.apache.log4j.Logger;

import com.mrbt.lingmoney.model.trading.buy.TradingBuyHelper;
import com.mrbt.lingmoney.utils.MyUtils;

/**
 * 买入的service
 * 
 * @author lzp
 *
 */
public abstract class TradingBuyService extends TradingService {
	Logger log = MyUtils.getLogger(TradingBuyService.class);

	public abstract Boolean buy(TradingBuyHelper helper);

}
