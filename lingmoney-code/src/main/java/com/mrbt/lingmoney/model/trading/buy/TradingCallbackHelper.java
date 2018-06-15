package com.mrbt.lingmoney.model.trading.buy;

import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.schedule.SellTrading;
import com.mrbt.lingmoney.model.trading.buy.TradingBuyCallback.CallbackType;
import com.mrbt.lingmoney.model.trading.valid.CallbackValidCode;
import com.mrbt.lingmoney.utils.AppCons;

@Component
public class TradingCallbackHelper {
	public Integer infoId;
	String bizCode;
	CallbackType flag;
	CallbackValidCode validCode;
	SellTrading trading;

	/**
	 * 根据CallbackType的值返回int的status值
	 * 
	 * @return
	 */
	public int getCallbackStatus() {
		if (flag == CallbackType.ok) {
			return AppCons.BUY_OK;
		}
		if (flag == CallbackType.failure) {
			return AppCons.BUY_FAIL;
		}
		if (flag == CallbackType.Frozen) {
			return AppCons.BUY_FROKEN;
		}
		return -1;
	}

	public int getWalletCallbackStatus() {
		if (flag == CallbackType.ok) {
			return AppCons.WALLET_FLOW_STATE_SUCCESS;
		}
		if (flag == CallbackType.failure) {
			return AppCons.WALLET_FLOW_STATE_FROKEN;
		}
		if (flag == CallbackType.Frozen) {
			return AppCons.WALLET_FLOW_STATE_FAIL;
		}
		return -1;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public CallbackType getFlag() {
		return flag;
	}

	public void setFlag(CallbackType flag) {
		this.flag = flag;
	}

	public CallbackValidCode getValidCode() {
		return validCode;
	}

	public void setValidCode(CallbackValidCode validCode) {
		this.validCode = validCode;
	}

	public SellTrading getTrading() {
		return trading;
	}

	public void setTrading(SellTrading trading2) {
		this.trading = trading2;
	}

}
