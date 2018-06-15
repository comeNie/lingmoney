package com.mrbt.lingmoney.web.controller.product;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.model.SellResult;
import com.mrbt.lingmoney.service.trading.TradingService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * @author syb
 * @date 2017年5月25日 下午5:08:15
 * @version 1.0
 * @description 交易相关
 **/
@Controller
@RequestMapping("/trading")
public class TradingController {
	private static final Logger LOG = LogManager.getLogger(TradingController.class);
	
	@Autowired
	private TradingService tradingService;
	
	/**
	 * 随心取赎回
	 * 
	 * @param tid
	 *            交易ID
	 * @param moneyInput
	 *            赎回份额
	 * @param redeemType
	 *            赎回类型 1 部分赎回 2全部赎回
	 * @param request
	 *            请求
	 * @return 返回信息
	 * 
	 */
	@RequestMapping("/sellProduct")
	@ResponseBody
	public Object sellProduct(Integer tid, Double moneyInput, Integer redeemType, HttpServletRequest request) {
		
		String logGroup = "\nsellProduct" + System.currentTimeMillis() + "_";
		
		String uid = CommonMethodUtil.getUidBySession(request);

		LOG.info(logGroup + "用户" + uid + "卖出 " + tid + "号理财");

		SellResult sellResult = new SellResult();
		if (!StringUtils.isEmpty(uid)) {
			try {
				sellResult = tradingService.sellProduct(uid, tid, moneyInput, redeemType, logGroup);

			} catch (Exception e) {
				sellResult.setCode(String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode()));
				sellResult.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
				e.printStackTrace();
				LOG.error(logGroup + "随心取赎回失败。系统错误。" + e.getMessage());
			}

		} else {
			sellResult.setFlag(1);
			sellResult.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		
		return sellResult;
	}
	
	/**
	 * 取消支付
	 * 
	 * @param request
	 *            请求
	 * @param tId
	 *            交易id
	 * @return 返回信息
	 */
	@RequestMapping("/cancelPayment")
	public @ResponseBody Object cancelPayment(HttpServletRequest request, Integer tId) {
		PageInfo pi = new PageInfo();
		
		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(uid)) {
			pi = tradingService.cancelPayment(uid, tId);

		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		
		return pi;
	}
	
}
