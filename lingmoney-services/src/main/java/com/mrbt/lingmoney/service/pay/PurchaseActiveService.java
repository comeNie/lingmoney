package com.mrbt.lingmoney.service.pay;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import net.sf.json.JSONObject;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
public interface PurchaseActiveService {

	/**
	 * 快捷支付-签约
	 * 
	 * @param request
	 *            request
	 * @param usersSession
	 *            usersSession
	 * @return return
	 */
	JSONObject validParames(HttpServletRequest request, String uId);

	/**
	 * 快捷支付-支付
	 * 
	 * @param request
	 *            request
	 * @param usersSession
	 *            usersSession
	 * @param session
	 *            session
	 * @return return
	 */
	JSONObject quickPaymentPay(HttpServletRequest request, String uId);

	/**
	 * 购买稳赢宝送领宝
	 * 
	 * @param uId
	 *            uId
	 * @param tId
	 *            tId
	 * @param infoId
	 *            infoId
	 */
	void buyGetLingbao(String uId, Integer tId, Integer infoId);

	/**
	 * 支付完成处理方法
	 * 
	 * @param i
	 *            i
	 * @param tId
	 *            tId
	 * @param bizNumber
	 *            bizNumber
	 * @param infoId
	 *            infoId
	 * @param uId
	 *            uId
	 * @return return
	 */
	boolean payOptertion(int i, int tId, String bizNumber, int infoId, String uId);

	/**
	 * 快捷支付-签约
	 * 
	 * @param request
	 *            request
	 * @param usersSession
	 *            usersSession
	 * @return return
	 */
	JSONObject onlinePayment(HttpServletRequest request, String uId);

	/**
	 * 页面跳转
	 * 
	 * @param request
	 *            request
	 * @param usersSession
	 *            usersSession
	 * @param modelMap
	 *            modelMap
	 * @return return
	 */
	String toPurchassActive(HttpServletRequest request, String uId, ModelMap modelMap);

}
