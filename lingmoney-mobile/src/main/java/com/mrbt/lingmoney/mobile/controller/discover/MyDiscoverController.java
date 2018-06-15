package com.mrbt.lingmoney.mobile.controller.discover;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.discover.MyDiscoverService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年5月4日 上午11:33:35
 * @version 1.0
 * @description 《发现》
 **/
@Controller
@RequestMapping("/myDiscover")
public class MyDiscoverController extends BaseController {
	private static final Logger LOG = LogManager.getLogger(MyDiscoverController.class);

	@Autowired
	private MyDiscoverService myDiscoverService;

	/**
	 * 获取用户账户信息（领宝，签到）
	 * 
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/getUserAccount", method = RequestMethod.POST)
	public @ResponseBody Object getUserAccount(String token) {
		LOG.info("获取用户账户信息（领宝，签到）token:" + token);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myDiscoverService.getUserInfo(getUid(tokenKey));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("获取发现首页信息失败--系统错误 token" + token + " \n" + e.getMessage());
		}
		return pageInfo;
	}

	/**
	 * 签到
	 * 
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public @ResponseBody Object signIn(String token) {
		LOG.info("签到token:" + token);
		PageInfo pageInfo = new PageInfo();
		try {
			String tokenKey = AppCons.TOKEN_VERIFY + token;
			pageInfo = myDiscoverService.signIn(getUid(tokenKey));
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
			e.printStackTrace();
			LOG.error("签到失败，系统错误。token:" + token + "\n" + e.getMessage());
		}
		return pageInfo;
	}
}
