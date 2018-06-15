package com.mrbt.lingmoney.mobile.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.AccountRechargeService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 用户充值
 * 
 * @author ruochen.yu
 *
 */

@Controller
@RequestMapping("bank")
public class AccountRechargeController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AccountRechargeController.class);

	@Autowired
	private AccountRechargeService accountRechargeService;
	/**
	 * @param request req
	 * @param response res
	 * @param token tk
	 * @param amount am
	 * @param model md
	 * @return string
	 */
	@RequestMapping(value = "/accountRechargePage", method = RequestMethod.POST)
	public String accountRechargePage(HttpServletRequest request, HttpServletResponse response, String token,
			String amount, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		paramsJson.put("amount", amount);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		model.addAttribute("reqUrl", "/bank/accountRecharge");
		return "hxBank/hxBankAction";
	}

	/**
	 * 发起账户充值请求
	 * 
	 * @param request req
	 * @param response res
	 * @param token tk
	 * @param model model
	 * @param amount amount
	 * @return json
	 */
	@RequestMapping(value = "/accountRecharge", method = RequestMethod.POST)
	public @ResponseBody Object accountRecharge(HttpServletRequest request, HttpServletResponse response, Model model,
			String token, String amount) {
		LOGGER.info("用户发起充值请求并跳转到华兴银行");
		response.setContentType("text/html;charset=UTF-8");

		JSONObject json = new JSONObject();

		PageInfo pageInfo = new PageInfo();
		String uId = null;
		int clientType = 1; // 0:pc 1：mobile
		String appId = "APP"; //
		try {
			uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = accountRechargeService.accountRechargeRequest(clientType, appId, uId, amount);
			if (pageInfo.getCode() ==  ResultInfo.SUCCESS.getCode()) {
				json.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
				json.put("obj", pageInfo.getObj());
			} else {
				json.put("code", pageInfo.getCode());
				json.put("msg", pageInfo.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("单笔充值失败，失败原因是：" + e.getMessage());
			json.put("code", ResultInfo.SERVER_ERROR.getCode());
			json.put("obj",  ResultInfo.SERVER_ERROR.getMsg());
		}
		return json;
	}

	/**
	 * 账户充值银行异步通知报文
	 * @param request req
	 * @param response res
	 * @param note note 用户uId
	 * @return null
	 */
	@RequestMapping(value = "/accountRechargeCallBack/{note}", method = RequestMethod.POST)
	public String accountRechargeCallBack(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String note) {

		try {
			String xml = getXmlDocument(request); // 接收银行发送的报文
			LOGGER.info("账户开通银行异步通知报文" + xml);

			String res = accountRechargeService.accountRechargeCallBack(xml, note);
			response.getWriter().write(res);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	/**
	 * @param request req
	 * @param response res
	 * @param token token
	 * @param number number
	 * @return pageInfo
	 */
	@RequestMapping("/queryAccountRecharge")
	public @ResponseBody Object queryAccountRecharge(HttpServletRequest request, HttpServletResponse response,
			String token, String number) {
		LOGGER.info("查询充值结果");

		String uId = getUid(AppCons.TOKEN_VERIFY + token);

		PageInfo pageInfo = accountRechargeService.queryAccountRecharge(uId, number, "APP");

		return pageInfo;
	}
}
