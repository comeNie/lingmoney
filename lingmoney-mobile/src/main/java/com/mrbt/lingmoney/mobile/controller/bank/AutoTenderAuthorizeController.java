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

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.AutoTenderAuthorizeService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 自动投标授权
 * @author luox
 * @Date 2017年6月6日
 */
@Controller
@RequestMapping(value = "/bank")
public class AutoTenderAuthorizeController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(AutoTenderAuthorizeController.class);
	@Autowired
	private AutoTenderAuthorizeService autoTenderAuthorizeService;
	
	/**
	 * 自动投标授权 
	 * @param response res
	 * @param model model
	 * @param token token
	 * @param remark remark
	 * @return json
	 */
	@RequestMapping(value = "/autoTenderAuthorize", method = RequestMethod.POST)
	public @ResponseBody Object autoTenderAuthorize(HttpServletResponse response, Model model, String token, 
			String remark) {
			
		LOGGER.info("自动投标授权");
		
		JSONObject json = new JSONObject();
		
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = autoTenderAuthorizeService.autoTenderAuthorize(uId, 1, "APP", remark);
			
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				json.put("code", pageInfo.getCode());
				json.put("obj", pageInfo.getObj());
			} else {
				json.put("code", pageInfo.getCode());
				json.put("obj", pageInfo.getMsg());
			}
		} catch (Exception e) {
			json.put("code", ResultInfo.SERVER_ERROR.getCode());
			json.put("obj", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 自动投标授权 请求页面
	 * @param request req 
	 * @param response res
	 * @param model model
	 * @param token token
	 * @param remark remark
	 * @return string
	 */
	@RequestMapping(value = "/autoTenderAuthorizePage", method = RequestMethod.POST)
	public String accountRechargePage(HttpServletRequest request, HttpServletResponse response, Model model, String token, 
			 String remark) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		paramsJson.put("remark", remark);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		model.addAttribute("reqUrl", "/bank/autoTenderAuthorize");
		return "hxBank/hxBankAction";
	}
}

