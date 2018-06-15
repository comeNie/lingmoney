package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.ModifyTradingPasswordService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

import net.sf.json.JSONObject;

/**
 * 个人客户进行重置交易密码操作
 * 
 * @author suyibo
 * @date 创建时间：2018年4月19日
 */
@Controller
@RequestMapping("/bank")
public class ModifyTradingPasswordController {
	private static final Logger LOGGER = LogManager.getLogger(BinkCardController.class);
	@Autowired
	private ModifyTradingPasswordService modifyTradingPasswordService;

	/**
	 * 华兴银行APPID
	 */
	public static final String APP_ID = "WX";

	/**
	 * 个人客户进行重置交易密码操作
	 * 
	 * @author suyibo
	 * @param model
	 *            model
	 * @param request
	 *            req
	 * @return
	 *
	 */
	@RequestMapping(value = "/modifyTradingPasswordPage", method = RequestMethod.POST)
	public String changePersonalBindCardPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("params", "");
		model.addAttribute("reqUrl", "/bank/modifyTradingPassword");
		return "hxBank/hxBankAction";
	}

	/**
	 * 个人客户进行重置交易密码
	 * 
	 * @param request
	 *            req
	 * @return json
	 */
	@RequestMapping(value = "/modifyTradingPassword", method = RequestMethod.POST)
	public @ResponseBody Object modifyTradingPassword(HttpServletRequest request) {
		LOGGER.info("个人客户进行重置交易密码");
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		String uId = "";
		try {

			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = modifyTradingPasswordService.modifyTradingPassword(1, APP_ID, uId);
			json.put("obj", pageInfo.getObj());

		} catch (Exception e) {
			LOGGER.info("个人客户进行重置交易密码异常：" + e.toString()); // 抛出堆栈信息
			e.printStackTrace();
			pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}
		json.put("code", pageInfo.getCode());
		json.put("msg", pageInfo.getMsg());

		return json;
	}

	/**
	 * 返回商户操作
	 * 
	 * @param note
	 *            note
	 * @param model
	 *            数据模型包装类
	 * @param request
	 *            请求
	 * @return 操作信息
	 */
	@RequestMapping("/modifyTradingPasswordCallBack/{note}")
	public void modifyTradingPasswordCallBack(HttpServletResponse response, @PathVariable String note, ModelMap model) {
		LOGGER.info("个人客户进行重置交易密码结果");

		try {
			response.sendRedirect("http://static.lingmoney.cn/wap/user/index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
