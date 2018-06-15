package com.mrbt.lingmoney.model.trading.buy;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.trading.service.TradingCallbackService;
import com.mrbt.lingmoney.model.trading.valid.CallbackValidCode;


/**
 * 购买的callback类
 * 
 * @author lzp
 *
 */

@Component
public class TradingBuyCallback {

	// @Resource
	// @Qualifier
	// public HashMap<String, TradingCallbackService> callBackServices;

	HashMap<String, TradingCallbackService> callBackServices = new HashMap<String, TradingCallbackService>();

	/**
	 * callback状态
	 * 
	 * @author lzp
	 *
	 */
	public enum CallbackType {

		Frozen("CALLBACK_FROZEN"), ok("CALLBACK_OK"), failure("CALLBACK_FAILURE"), trading("TRADING_CONDU");
		String value;

		CallbackType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	/**
	 * 创建交易类型
	 * 
	 * @param tid
	 *            交易id
	 * @param infoId
	 *            详细交易表中的id
	 * @param flag
	 *            成功标志，true:支付成功,false:支付失败
	 * @return
	 */
	public TradingCallbackOperator buildTrading(Integer tid, Integer infoId, String bizCode, String uid,
			CallbackType flag) {

		SellTrading trading;
		TradingCallbackHelper helper = new TradingCallbackHelper();

		if (tid == null || (trading = callBackServices.get("buyCallbackService").findTrading(tid)) == null) {
			throw new IllegalArgumentException("交易错误，没有此类交易");
		}

		helper.setTrading(trading);
		if (infoId == null) {
			throw new IllegalArgumentException("详细交易信息错误");
		}

		helper.setInfoId(infoId);
		if (!trading.getUid().equals(uid)) {
			helper.setValidCode(CallbackValidCode.callback_user_error);
		}

		helper.setFlag(flag);
		helper.setBizCode(bizCode);

		return new TradingCallbackOperator(helper, callBackServices);
	}

}
