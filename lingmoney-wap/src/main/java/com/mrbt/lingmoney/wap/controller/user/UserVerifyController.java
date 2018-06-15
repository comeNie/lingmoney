package com.mrbt.lingmoney.wap.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.wap.controller.common.CommonMethodUtil;

/**
 * 用户基础接口
 * 
 *
 */
@Controller
@RequestMapping("/usersVerify")
public class UserVerifyController {

	Logger log = LogManager.getLogger(UserVerifyController.class);

	@Autowired
	private UsersService usersService;
	@Autowired
	private VerifyService verifyService;

	/**
	 * 用户注册
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            参数存放model
	 * @param telephone
	 *            手机号
	 * @param loginPsw
	 *            登录密码
	 * @param referralTel
	 *            推荐码
	 * @param verificationCode
	 *            验证码
	 * @param channel
	 *            渠道号
	 * @return 重定向到首页
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(HttpServletRequest request, ModelMap model, String telephone, String loginPsw,
			String referralTel, String verificationCode, Integer channel) {
		log.info("users-用户注册");

		PageInfo pageInfo = new PageInfo();

		String path = request.getSession().getServletContext().getRealPath("");
		try {

			pageInfo = usersService.regist(telephone, loginPsw, referralTel, verificationCode, path, channel);
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				// 注册成功后执行登录操作
				pageInfo = usersService.loginForWeb(request, telephone, loginPsw);

				if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
					UserUnionInfo userUnionInfo = (UserUnionInfo) pageInfo.getObj();
					request.getSession().setAttribute("uid", userUnionInfo.getId());
				}

			} else {
				model.addAttribute("msg", pageInfo.getMsg());
				return null;
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "手机：" + telephone + "注册失败");
			return "500";
		}

		return "redirect:/index";
	}

	/**
	 * 验证用户注册表单账号
	 * 
	 * @param account
	 *            用户账号
	 * @return pageinfo类型处理结果
	 */
	@RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyAccount(String account) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyAccount(account);
			pageInfo.setResultInfo(ResultInfo.SUCCESS);

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证用户注册表单手机号
	 * 
	 * @param telephone
	 *            手机号
	 * @return pageinfo类型验证结果
	 */
	@RequestMapping(value = "/verifyTelephone", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyTelephone(String telephone) {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyService.verifyTelephone(telephone, 0);
			pageInfo.setResultInfo(ResultInfo.SUCCESS);

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证用户注册表单短信验证码
	 * 
	 * @author YJQ 2017年5月11日
	 * @param telephone
	 *            手机号
	 * @param msgCode
	 *            验证码
	 * @return pageinfo类型验证结果
	 */
	@RequestMapping(value = "/verifyMsgCode", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyMsgCode(String telephone, String msgCode) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyMsgCode(telephone, msgCode);
			pageInfo.setCode(ResultInfo.SUCCESS.getCode());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "验证码：" + msgCode + "验证失败");
		}

		return pageInfo;
	}

}
