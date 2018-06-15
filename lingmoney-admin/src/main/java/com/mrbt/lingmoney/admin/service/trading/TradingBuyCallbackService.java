package com.mrbt.lingmoney.admin.service.trading;

import com.mrbt.lingmoney.admin.vo.trading.CallbackValidCode;

/**
 * 
 * TradingBuyCallbackController实现类接口
 *
 */
public interface TradingBuyCallbackService {

	/**
	 * 更新交易操作
	 * 
	 * @param pid
	 *            pid
	 * @param days
	 *            days
	 * @param auto
	 *            auto
	 * @return 数据返回
	 */
	CallbackValidCode manualEstablish(int pid, Integer days, boolean auto);

}
