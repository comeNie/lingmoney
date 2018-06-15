package com.mrbt.lingmoney.web.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 
 * 查询用户信息
 *
 */
@Controller
public class DefaultController {
	@Autowired
	private UsersService usersService;

	Logger log = LogManager.getLogger(DefaultController.class);

	/**
	 * 查询用户信息
	 * 
	 * @param name
	 *            名称
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/{name}")
	public String forwardToAbout(@PathVariable String name, ModelMap model, HttpServletRequest request) {
		if ("login".equals(name)) {
			return "users/login";
		} else if ("regist".equals(name)) {
			return "users/regist";
		} else if (String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode()).equals(name)) {
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		// 查询用户信息
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户信息异常：" + e.getMessage());
		} finally {
			// 直接访问页面放在此文件夹中
			return "aboutLingqian/" + name;
		}

	}

	/**
	 * 查询用户信息
	 * 
	 * @param name
	 *            名称
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@SuppressWarnings("finally")
	@RequestMapping("/*/{name}")
	public String forward(@PathVariable String name, ModelMap model, HttpServletRequest request) {
		String url = request.getRequestURI();
		if (url.startsWith("/")) {
			url = url.replaceFirst("/", "");
		}
		if (url.endsWith(".html")) {
			url = url.substring(0, url.lastIndexOf("."));
		}
		// 查询用户信息
		// 如果是 login/regist/forgetpassword 不查询
		if ("login".equals(name) || "regist".equals(name) || "forgetpassword".equals(name)) {
			return url;
		}

		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户信息异常：" + e.getMessage());
		} finally {
			// 无论是否抛出异常，都执行跳转
            // 原青岛站，杭州站跳转请求处理，统一发回默认首页
            if (url.contains("qdjsp/") || url.contains("hangzhou/")) {
                return "redirect:/index";
            }
			return url;
		}
	}
}
