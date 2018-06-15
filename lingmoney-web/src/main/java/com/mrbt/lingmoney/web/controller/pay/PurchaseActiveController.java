package com.mrbt.lingmoney.web.controller.pay;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.pay.PurchaseActiveService;
import com.mrbt.lingmoney.service.pay.UsersBankService;
import com.mrbt.lingmoney.service.trading.JDPurchaseService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import net.sf.json.JSONObject;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseActiveController {

	Logger log = LogManager.getLogger(PurchaseActiveController.class);
	
	@Autowired
	private PurchaseActiveService purchaseActiveService;
	@Autowired
	private UsersBankService usersBankService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private JDPurchaseService jdPurchaseService;

	/**
	 * 跳转到选择交易方式页面
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	@RequestMapping("/active")
	public String toPurchassActive(HttpServletResponse response, HttpServletRequest request, ModelMap modelMap) {
		try {
			
			CommonMethodUtil.modelUserBaseInfo(modelMap, request, usersService);
			String uId = CommonMethodUtil.getUidBySession(request);
			return purchaseActiveService.toPurchassActive(request, uId, modelMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
	}

	/**
	 * 快捷支付-签约
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	@RequestMapping("/quickPaymentSign")
	public @ResponseBody Object quickPaymentSign(HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PageInfo pageInfo = new PageInfo();
		try {
			
			
			CommonMethodUtil.modelUserBaseInfo(modelMap, request, usersService);
			String uId = CommonMethodUtil.getUidBySession(request);
			
			//通过订单号获取对应的支付数据
			String pCode = request.getParameter("pCode");
			Integer tid = Integer.parseInt(request.getParameter("tId"));
			Integer usersBankId = Integer.parseInt(request.getParameter("userBankId"));
			
			pageInfo = jdPurchaseService.getSecretCode(uId, tid, pCode, usersBankId);
			
		} catch (Exception e) {
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			usersBankService.deleteBankRedis(request.getParameter("dizNumber"),
					ResultParame.ResultNumber.ONE.getNumber());
		}
		return pageInfo;
	}

	/**
	 * 快捷支付-支付
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	@RequestMapping("/quickPaymentPay")
	public @ResponseBody Object quickPaymentPay(HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PageInfo pageInfo = new PageInfo();
		try {
			CommonMethodUtil.modelUserBaseInfo(modelMap, request, usersService);
			String uId = CommonMethodUtil.getUidBySession(request);
			
			Integer usersBankId = Integer.parseInt(request.getParameter("userBankId"));
			String verifyCode = request.getParameter("telCode");
			String pCode = request.getParameter("pCode");
			Integer tid = Integer.parseInt(request.getParameter("tId"));
			
			pageInfo = jdPurchaseService.jdPay(uId, tid, pCode, usersBankId, verifyCode, 0);

		} catch (Exception e) {
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			usersBankService.deleteBankRedis(request.getParameter("dizNumber"),
					ResultParame.ResultNumber.TWO.getNumber());
		}
		return pageInfo;
	}
	
	/**
	 * 快捷支付-通知
	 * 
	 * @param response
	 *            response
	 * @param note
	 *            note
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	@RequestMapping("/quickNotice/{note}")
	public @ResponseBody Object quickNotice(HttpServletResponse response, @PathVariable String note,
			HttpServletRequest request, ModelMap modelMap) {
		 return jdPurchaseService.quickNotice(request, note);
	}

	/**
	 * 网银支付
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return return
	 */
	@RequestMapping("/onlinePayment")
	public @ResponseBody Object onlinePayment(HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		JSONObject json = new JSONObject();
		try {
			CommonMethodUtil.modelUserBaseInfo(modelMap, request, usersService);
			String uId = CommonMethodUtil.getUidBySession(request);

			json = purchaseActiveService.onlinePayment(request, uId);
		} catch (Exception e) {
			json.put("code", String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode()));
			json.put("msg", "程序错误");
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 网银支付-通知URL NOTITY
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return return
	 */
	@RequestMapping("/onlineNotity/{note}")
	public @ResponseBody Object onlineNotity(HttpServletResponse response, @PathVariable String note,
			HttpServletRequest request, ModelMap modelMap) {
		
		String resStr = "";
		try {
			resStr = jdPurchaseService.onlineNotity(request, note);
		} catch (Exception e) {
			e.printStackTrace();
			resStr = "error";
		}
		return resStr;
	}

	/**
	 * 网银支付-返回商户URL
	 * 
	 * @param response
	 *            response
	 * @param request
	 *            request
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	@RequestMapping("/onlineCallBank/{note}")
	public String onlineCallBank(HttpServletResponse response, @PathVariable String note, HttpServletRequest request,
			ModelMap modelMap) {
		
		String resStr = "";
		try {
			resStr = jdPurchaseService.onlineCallBank(request, note, modelMap);
		} catch (Exception e) {
			e.printStackTrace();
			resStr = "error";
		}
		return resStr;
	}
}
