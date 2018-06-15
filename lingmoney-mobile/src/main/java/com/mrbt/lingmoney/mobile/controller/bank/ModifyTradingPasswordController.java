package com.mrbt.lingmoney.mobile.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.ModifyTradingPasswordService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 个人客户进行重置交易密码操作
 * 
 * @author suyibo
 * @date 创建时间：2018年4月19日
 */
@Controller
@RequestMapping("/bank")
public class ModifyTradingPasswordController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(BinkCardController.class);
	@Autowired
	private ModifyTradingPasswordService modifyTradingPasswordService;

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
	public String changePersonalBindCardPage(HttpServletRequest request, HttpServletResponse response, Model model,  String token) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
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
	public @ResponseBody Object modifyTradingPassword(HttpServletRequest request, String token) {
		LOGGER.info("个人客户进行重置交易密码");
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		String uId = "";
		try {
			HxTiedCardReqDto req = new HxTiedCardReqDto();
			req.setAPPID("APP");
			uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = modifyTradingPasswordService.modifyTradingPassword(1, "APP", uId);
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

}
