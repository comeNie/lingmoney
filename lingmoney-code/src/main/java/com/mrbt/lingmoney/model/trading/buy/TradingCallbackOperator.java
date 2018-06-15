package com.mrbt.lingmoney.model.trading.buy;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.model.trading.buy.TradingBuyCallback.CallbackType;
import com.mrbt.lingmoney.model.trading.service.TradingCallbackService;
import com.mrbt.lingmoney.model.trading.valid.CallbackValidCode;
import com.mrbt.lingmoney.utils.ProductUtils;

@Component
public class TradingCallbackOperator
{
    public TradingCallbackHelper helper;
    public HashMap<String, TradingCallbackService> callBackServices;

	public TradingCallbackOperator() {
	}

    public TradingCallbackOperator(TradingCallbackHelper helper, HashMap<String, TradingCallbackService> callBackServices)
    {
	this.helper = helper;
	this.callBackServices = callBackServices;
    }

    /**
     * build方法
     * 
     * @return
     */
	public boolean build() {
		System.out.println(helper.getFlag());
		
		if (helper.getValidCode() == null) {
			boolean reFlag = true;
			// 钱包模式
//			if (helper.getTrading().getpModel() == AppCons.PRODUCT_P_MODE_WALLET) {
//				if (helper.getFlag() == CallbackType.ok) {
//					reFlag = callBackServices.get("buyCallbackWalletService").callbackOk(helper);
//				} else if (helper.getFlag() == CallbackType.failure) {
//					reFlag = callBackServices.get("buyCallbackWalletService").callbackFail(helper);
//				} else if (helper.getFlag() == CallbackType.Frozen) {
//					reFlag = callBackServices.get("buyCallbackWalletService").callbackFroken(helper);
//				}
//				if (!reFlag && helper.getValidCode() == CallbackValidCode.callback_info_status_check) {
//					return callBackServices.get("buyCallbackWalletService").checkInfoExistUpdate(helper,
//							helper.getFlag().getValue(), helper.getWalletCallbackStatus());
//				}
//				return reFlag;
//			}
//			// 随心取模式
//			else 
			if (helper.getTrading().getpId() == ProductUtils.getIntContent("takeHeart_pID")) {
				
				System.out.println(helper.getTrading().getpId() + "\t" + helper.getFlag());
				if (helper.getFlag() == CallbackType.ok) {
					reFlag = callBackServices.get("buyCallbackTakeHeartService").callbackOk(helper);
					
				} else if (helper.getFlag() == CallbackType.failure) {
					reFlag = callBackServices.get("buyCallbackTakeHeartService").callbackFail(helper);
					
				} else if (helper.getFlag() == CallbackType.trading) {
					reFlag = callBackServices.get("buyCallbackTakeHeartService").callbackFroken(helper);
					
				}
				if (!reFlag && helper.getValidCode() == CallbackValidCode.callback_info_status_check) {
					return callBackServices.get("buyCallbackTakeHeartService").checkInfoExistUpdate(helper, helper.getFlag().getValue(), helper.getWalletCallbackStatus());
					
				}
				return reFlag;

			}

			// 普通产品模式
			else {
				if (helper.getFlag() == CallbackType.ok) {
					reFlag = callBackServices.get("buyCallbackService").callbackOk(helper);

				} else if (helper.getFlag() == CallbackType.failure) {
					reFlag = callBackServices.get("buyCallbackService").callbackFail(helper);
					
				} else if (helper.getFlag() == CallbackType.trading) {
					reFlag = callBackServices.get("buyCallbackService").callbackFroken(helper);
					
				} 
				
				if (!reFlag && helper.getValidCode() == CallbackValidCode.callback_info_status_check) {
					
					return callBackServices.get("buyCallbackService").checkInfoExistUpdate(helper, helper.getFlag().getValue(), helper.getCallbackStatus());
				}
				return reFlag;
			}
		}
		return false;
	}

    public TradingCallbackHelper getHelper()
    {
	return helper;
    }

    public void setHelper(TradingCallbackHelper helper)
    {
	this.helper = helper;
    }

}
