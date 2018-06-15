package com.mrbt.lingmoney.service.trading;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import net.sf.json.JSONObject;

import com.mrbt.lingmoney.utils.PageInfo;

/**
  * 京东支付
  *@author yiban
  *@date 2018年1月4日 下午5:02:49
  *@version 1.0
 **/
public interface JDPurchaseService {

    /**
     * 支付第一步 获取验证码
     */
    PageInfo getSecretCode(String uId, Integer tid, String pCode, Integer usersBankId);

    /**
     * 支付第二步 确认支付
     */
    PageInfo jdPay(String uId, Integer tid, String pCode, Integer usersBankId, String verifyCode, int clent);

    /**
     * 快捷支付  通知
     * 
     * @author yiban
     * @date 2018年1月5日 上午11:30:48
     * @version 1.0
     * @param request
     * @param note
     * @return
     *
     */
    JSONObject quickNotice(HttpServletRequest request, String note);

    /**
     * 跳转京东支付页面
     * 
     * @author yiban
     * @date 2018年1月6日 下午5:54:04
     * @version 1.0
     * @param jdTradingId
     *
     */
    void gotoPurchase(Integer jdTradingId, HttpServletResponse response, Integer takeheartTid);

    /**
     * 查询所有随心取费率
     * 
     * @author yiban
     * @date 2018年1月7日 下午6:12:49
     * @version 1.0
     * @return
     *
     */
    PageInfo getAllTakeHeartFixRate();

    /**
     * 京东网银通知
     * @param request request
     * @param note  tId + "_" + infoId + "_" + uId
     * @return 返回字符串
     */
	String onlineNotity(HttpServletRequest request, String note);

	/**
	 * 网银支付返回商户
	 * @param request request
	 * @param note	tId + "_" + infoId + "_" + uId
	 * @param modelMap 
	 * @return 返回字符串
	 */
	String onlineCallBank(HttpServletRequest request, String note, ModelMap modelMap);

}
