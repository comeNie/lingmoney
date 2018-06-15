package com.mrbt.lingmoney.web.controller.bank;

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

import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.service.bank.ModifyTradingPasswordService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web.vo.view.ResultPageInfo;

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
	@Autowired
	private UsersService usersService;

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
	@RequestMapping(value = "/modifyTradingPasswordPage")
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
			HxTiedCardReqDto req = new HxTiedCardReqDto();
			req.setAPPID("PC");
			uId = CommonMethodUtil.getUidBySession(request);
			pageInfo = modifyTradingPasswordService.modifyTradingPassword(0, "PC", uId);
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
	public String modifyTradingPasswordCallBack(@PathVariable String note, ModelMap model, HttpServletRequest request) {
		LOGGER.info("个人客户进行重置交易密码结果");

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResultPageInfo info = new ResultPageInfo();
		info.setType(ResultParame.ResultNumber.THREE.getNumber());
		info.setAutoReturnName("会员首页");
		info.setAutoReturnUrl("/myLingqian/show");

		model.addAttribute("resultInfo", info);

		return "hxCallBack";
	}
}
