package com.mrbt.lingmoney.wap.controller.bank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.bank.AutoTenderAuthorizeService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

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
	public @ResponseBody Object autoTenderAuthorize(HttpServletResponse response, Model model, HttpServletRequest request, 
			String remark) {
			
		LOGGER.info("自动投标授权");
		
		PageInfo pageInfo = new PageInfo();
		try {
            String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = autoTenderAuthorizeService.autoTenderAuthorize(uId, 1, APP_ID, remark);
            }
		} catch (Exception e) {
            pageInfo.setResultInfo(ResultInfo.SERVER_ERROR);
		}

        return pageInfo;
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
    public String accountRechargePage(HttpServletRequest request, HttpServletResponse response, Model model,
            String remark) {
		JSONObject paramsJson = new JSONObject();
		paramsJson.put("remark", remark);
        model.addAttribute("params", paramsJson.toString());
		model.addAttribute("reqUrl", "/bank/autoTenderAuthorize");
		return "hxBank/hxBankAction";
	}
}

