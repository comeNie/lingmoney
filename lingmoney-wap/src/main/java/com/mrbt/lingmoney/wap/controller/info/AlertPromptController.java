package com.mrbt.lingmoney.wap.controller.info;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisGet;
 
 
import com.mrbt.lingmoney.service.info.AlertPromptService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.wap.controller.BaseController;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 弹框提示接口
 * 
 * @author lihq
 * @date 2017年7月6日 下午4:28:08
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
@RequestMapping("/alertPrompt")
public class AlertPromptController extends BaseController {
	private static final Logger LOGGER = LogManager.getLogger(AlertPromptController.class);

	@Autowired
	private AlertPromptService alertPromptService;

	@Autowired
	private RedisGet redisGet;

	/**
	 * 首页弹框提示
	 * @param type
	 *            客户端类型 0，ios 1，安卓
	 * @param needLogin
	 *            是否登录：Y是，N否
	 * @return pageInfo
	 */
	@RequestMapping(value = "/alertPromptInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object alertPromptInfo(HttpServletRequest request, Integer type, String needLogin) {
		LOGGER.info("alertPrompt/alertPromptInfo" + "手机类型：" + type);
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
			if (type == null || (type != ResultNumber.ONE.getNumber() && type != ResultNumber.ONE.getNumber()) || StringUtils.isBlank(needLogin)
					|| (!"N".equals(needLogin) && !"Y".equals(needLogin))) {
				pageInfo.setCode(ResultInfo.PARAMETER_ERROR.getCode());
				pageInfo.setMsg(ResultInfo.PARAMETER_ERROR.getMsg());
				return pageInfo;
			}
			if ("Y".equals(needLogin)) {
				if (StringUtils.isBlank(uId) || uId == null) {
					pageInfo.setCode(ResultInfo.LOGIN_FAILURE.getCode());
					pageInfo.setMsg(ResultInfo.LOGIN_FAILURE.getMsg());
					return pageInfo;
				}
			}
			pageInfo = alertPromptService.alertPromptInfo(type, uId, needLogin);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("弹框提示查询失败");
			e.printStackTrace();
		}
		return pageInfo;
	}
}
