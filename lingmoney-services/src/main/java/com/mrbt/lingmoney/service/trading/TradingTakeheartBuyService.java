package com.mrbt.lingmoney.service.trading;

import java.math.BigDecimal;

import com.mrbt.lingmoney.service.trading.impl.TradingFixRuleBuyServiceImpl.CallbackType;
import com.mrbt.lingmoney.utils.PageInfo;

/**
  *
  *@author yiban
  *@date 2018年1月6日 下午3:22:21
  *@version 1.0
 **/
public interface TradingTakeheartBuyService {
    /**
     * 随心取购买
     * 
     * @author yiban
     * @date 2018年1月6日 下午3:24:54
     * @version 1.0
     * @param pCode 产品code
     * @param uid 用户id
     * @param platForm PC APP
     * @param buyMoney 购买金额
     * @return
     *
     */
    PageInfo buy(String pCode, String uid, BigDecimal buyMoney);

    /**
     * 随心取支付结果处理
     * 
     * @author yiban
     * @date 2018年1月7日 上午11:53:17
     * @version 1.0
     * @param tid takeheart_transaction_flow.id
     * @param type 处理类型
     *
     */
    void handleTradingResult(Integer tid, CallbackType type);
}
