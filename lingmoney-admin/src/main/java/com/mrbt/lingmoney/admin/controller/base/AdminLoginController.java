package com.mrbt.lingmoney.admin.controller.base;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.base.AdminUserService;
import com.mrbt.lingmoney.model.AdminUser;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.session.UserSession;

/**
 * 登录
 * 
 * @author lihq
 * @date 2017年5月3日 下午5:24:07
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Controller
public class AdminLoginController {
	private Logger log = MyUtils.getLogger(AdminLoginController.class);
	
	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 用户登录
	 * @param loginName	登录名称
	 * @param loginPsw	登录密码
	 * @param session	session
	 * @return	返回结果	
	 */
	@RequestMapping("login")
	public @ResponseBody Object login(String loginName, String loginPsw, HttpSession session) {
		log.info("/login");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(loginName) && StringUtils.isNotBlank(loginPsw)) {
				adminUserService.login(pageInfo, loginName, loginPsw, session);
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;

	}

	/**
	 *  用户退出
	 * @param session	登录session
	 * @return	返回
	 */
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		log.info("/logout");
		session.invalidate();
		return "redirect:/index.html";
	}

	/**
	 * 修改密码
	 * @param session	session
	 * @param newpass	新密码
	 * @return	返回处理结果
	 */
	@RequestMapping("editpassword")
	public @ResponseBody Object editpassword(HttpSession session, String newpass) {
		log.info("/editpassword");
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(newpass)) {
				AdminUser user = ((UserSession) session.getAttribute(AppCons.SESSION_USER)).getUser();
				AdminUser updateUser = new AdminUser();
				updateUser.setId(user.getId());
				updateUser.setLoginPsw(MD5Utils.MD5(newpass));
				adminUserService.update(updateUser);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
			} else {
				pageInfo.setCode(ResultInfo.NO_DATA.getCode());
				pageInfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
