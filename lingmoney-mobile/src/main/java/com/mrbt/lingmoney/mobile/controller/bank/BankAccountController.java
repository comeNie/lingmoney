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
import com.mrbt.lingmoney.service.bank.BankAccountService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 银行账户开通相关接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bank")
public class BankAccountController extends BaseController {

	private static final Logger  LOGGER = LogManager.getLogger(BankAccountController.class);

	@Autowired
	private BankAccountService bankAccountService;
	/**
	 * @param request req
	 * @param response res
	 * @param token token
	 * @param acName acName
	 * @param mobile mobile
	 * @param idNo idNo
	 *  @param model model
	 * @return string
	 */
	@RequestMapping(value = "/accountOpenPage", method = RequestMethod.POST)
	public String accountOpenPage(HttpServletRequest request, HttpServletResponse response, String token, String acName, String mobile, String idNo, Model model) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		paramsJson.put("acName", acName);
		paramsJson.put("idNo", idNo);
		paramsJson.put("mobile", mobile);
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		model.addAttribute("reqUrl", "/bank/accountOpen");
		return "hxBank/hxBankAction";
	}

	/**
	 * 发起账户开立请求
	 * 
	 * @param request req
	 * @param response res
	 * @param token token 
	 * @param acName acName
	 * @param mobile mobile
	 * @param idNo idNo
	 * @param model model
	 * @return json
	 */
	@RequestMapping(value = "/accountOpen", method = RequestMethod.POST)
	public @ResponseBody Object accountOpen(HttpServletRequest request, HttpServletResponse response, String token, String acName, String mobile, String idNo, Model model) {
		LOGGER.info("银行账户开立");
		response.setContentType("text/html;charset=UTF-8");
		JSONObject json = new JSONObject();
		// 发送投标报文
		try {
			String uId = getUid(AppCons.TOKEN_VERIFY + token);
			PageInfo pageInfo =  bankAccountService.requestAccountOpen(uId, acName, idNo, mobile, 1, "APP");
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				json.put("code", ResultInfo.SUCCESS.getCode());
				json.put("obj", pageInfo.getObj());
			} else {
				json.put("code", pageInfo.getCode());
				json.put("msg", pageInfo.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("银行账户开立失败，失败原因是：" + e.getMessage());
			json.put("code", ResultInfo.SERVER_ERROR.getCode());
			json.put("msg", ResultInfo.SERVER_ERROR.getMsg());
		}
		return json;
	}
	/**
	 * @param request req
	 * @param response res
	 * @param token token 
	 * @param seqNo seqNo
	 * @return pageInfo
	 */
	@RequestMapping("/queryAccountOpen")
	public @ResponseBody Object queryAccountOpen(HttpServletRequest request, HttpServletResponse response,
			String token, String seqNo) {
		LOGGER.info("查询银行账户开立结果");

		String uId = getUid(AppCons.TOKEN_VERIFY + token);

		PageInfo pageInfo = bankAccountService.queryAccountOpen(uId, seqNo);

		return pageInfo;
	}

	/**
	 * 查询用户开通E账户状态
	 * @param response req
	 * @param request res
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/accountOpenStatus")
	public @ResponseBody Object accountOpenStatus(HttpServletRequest request, HttpServletResponse response,
			String token) {
		PageInfo pageInfo = new PageInfo();
		String uId = getUid(AppCons.TOKEN_VERIFY + token);
		pageInfo = bankAccountService.accountOpenStatus(uId);
		return pageInfo;
	}
}
