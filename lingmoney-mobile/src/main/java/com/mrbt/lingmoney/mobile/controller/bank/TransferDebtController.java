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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.bank.TransferDebtService;
import com.mrbt.lingmoney.utils.AES256Encryption;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 债权转让
 * @author luox
 * @Date 2017年6月8日
 */
@Controller
@RequestMapping(value = "/bank")
public class TransferDebtController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(TransferDebtController.class);
	@Autowired
	private TransferDebtService transferDebtService;
	
	/**
	 * 6.11	债权转让结果查询
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/transferDebtAssignmentSearch", method = RequestMethod.POST)
	public @ResponseBody Object transferDebtAssignmentSearch(@RequestParam(required = true) String token
			) {
		LOGGER.info("债权转让结果查询");
		PageInfo pageInfo = null;
		try {
			String uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = transferDebtService.transferDebtAssignmentSearch("APP", uId);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(e.getMessage());
		}
		return pageInfo;
	}
	
	/**
	 * 债权转让申请
	 * @param response req
	 * @param model model
	 * @param token token
	 * @param amount amount
	 * @param remark remark
	 * @param pCode pCode
	 * @return json
	 */
	@RequestMapping(value = "/transferDebt", method = RequestMethod.POST)
	@ResponseBody
	public Object transferDebt(HttpServletResponse response, Model model, String token, 
			String amount, String remark, String pCode) {
			
		LOGGER.info("债权转让申请");
		PageInfo pageInfo = new PageInfo();
		JSONObject json = new JSONObject();
		try {
			String uId = getUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = transferDebtService.transferDebtAssignment(uId, 1, "APP", amount, remark, pCode);
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				json.put("code", pageInfo.getCode());
				json.put("obj", pageInfo.getObj());
			} else {
				json.put("code", pageInfo.getCode());
				json.put("obj", pageInfo.getMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", ResultInfo.SERVER_ERROR.getCode());
			json.put("obj", e.getMessage());
		}
		return json;
		
	}
	
	/**
	 * 债权转让申请 请求页面
	 * @param request req
	 * @param response res
	 * @param model model
	 * @param token token
	 * @param amount amount
	 * @param remark remark
	 * @param pCode pCode
	 * @return string
	 */
	@RequestMapping(value = "/transferDebtPage")
	public String transferDebtPage(HttpServletRequest request, HttpServletResponse response, Model model, 
			String token, String amount, String remark, String pCode) {
			 
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("token", token);
		paramsJson.put("amount", amount);
		paramsJson.put("remark", remark);
		paramsJson.put("pCode", pCode);
		
		model.addAttribute("params", AES256Encryption.aes256Encrypt(paramsJson.toString()));
		model.addAttribute("reqUrl", "/bank/transferDebt");
		return "hxBank/hxBankAction";
	}
}
