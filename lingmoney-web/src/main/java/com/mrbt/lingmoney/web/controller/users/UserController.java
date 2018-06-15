package com.mrbt.lingmoney.web.controller.users;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * 用户基础接口
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/users")
public class UserController {

	Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private UsersService usersService;
	@Autowired
	private VerifyService verifyService;
	@Autowired
	private UtilService utilService;
	@Autowired
	private RedisSet redisSet;

	/**
	 * 用户登录 1.uid存入redis。key为AppCons.SESSION_USER + sessionid。详细用户信息在各自接口用uid取出；
	 * 2.用户基础共享信息存入session。key为AppCons.SESSION_USER，值实体类型为UsersBaseInfoVo。
	 * 
	 * @author YJQ 2017年4月12日
	 * @param request
	 *            请求
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param location
	 *            链接
	 * @return 返回信息
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, String account, String password, String location) {
		log.info("users-用户登录");
		PageInfo pageInfo = new PageInfo();
		try {
			// 验证
			pageInfo = usersService.loginForWeb(request, account, password);
			// #start set data
			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				UserUnionInfo userUnionInfo = (UserUnionInfo) pageInfo.getObj();
				request.getSession().setAttribute("uid", userUnionInfo.getId());
				pageInfo.setObj(StringUtils.isEmpty(location) ? "/index" : "/" + location + "/index");

				// ↓↓↓↓↓↓↓↓↓↓↓↓↓设置我的领钱儿首次进入标识redis，用于展示广告。↓↓↓↓↓↓↓↓↓↓↓↓↓
				String key = AppCons.AFTERLOGIN_FIRST_IN_MYLINGQIAN + userUnionInfo.getId();
				redisSet.setRedisStringResult(key, "1");
				redisSet.expire(key, ResultParame.ResultNumber.THIRTY.getNumber(), TimeUnit.MINUTES);
				// ↑↑↑↑↑↑↑↑↑↑↑↑↑设置我的领钱儿首次进入标识redis，用于展示广告。↑↑↑↑↑↑↑↑↑↑↑↑↑
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "登录失败");
		}

		return pageInfo;
	}

	/**
	 * 用户注销
	 * 
	 * @author YJQ 2017年5月11日
	 * @param request
	 *            请求
	 * @param domainCityCode
	 *            domainCityCode
	 * @return 返回信息
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, String domainCityCode) {
		log.info("users-用户注销");
		try {
			usersService.logout(CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			e.printStackTrace();
			log.error("注销失败:" + e);
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		request.getSession().invalidate();

		return "redirect:/index?domainCityCode=" + domainCityCode;
	}

	/**
	 * 用户注册
	 * 
	 * @author YJQ 2017年5月11日
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @param telephone
	 *            电话
	 * @param loginPsw
	 *            登录密码
	 * @param referralTel
	 *            推荐码
	 * @param verificationCode
	 *            验证码
	 * @param channel
	 *            注册渠道
	 * @return 返回信息
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String regist(HttpServletRequest request, ModelMap model, String telephone, String loginPsw,
			String referralTel, String verificationCode, Integer channel) {
		log.info("users-用户注册");

		PageInfo pageInfo = new PageInfo();

		String path = request.getSession().getServletContext().getRealPath("");
		try {
			pageInfo = usersService.regist(telephone, loginPsw, referralTel, verificationCode, path, channel);

			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				// 注册成功后执行登录操作
				pageInfo = usersService.loginForWeb(request, telephone, loginPsw);

				if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					UserUnionInfo userUnionInfo = (UserUnionInfo) pageInfo.getObj();
					request.getSession().setAttribute("uid", userUnionInfo.getId());
				}

			} else {
				model.addAttribute("msg", pageInfo.getMsg());
				return null;
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "手机：" + telephone + "注册失败");
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "redirect:/index";
	}

	// #start 注册表单验证controller
	/**
	 * 验证用户注册表单账号
	 * 
	 * @author YJQ 2017年5月11日
	 * @param account
	 *            账号
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyAccount", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyAccount(String account) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyAccount(account);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

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
	 * @author YJQ 2017年5月11日
	 * @param telephone
	 *            电话
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyTelephone", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyTelephone(String telephone) {
		PageInfo pageInfo = new PageInfo();
		try {
			verifyService.verifyTelephone(telephone, 0);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证用户注册表单推荐码
	 * 
	 * @author YJQ 2017年5月11日
	 * @param referralTel
	 *            推荐码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyReferralTel", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyReferralTel(String referralTel) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyReferralTel(referralTel);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "推荐码：" + referralTel + "验证失败:");
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
	 *            短信验证码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyMsgCode", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyMsgCode(String telephone, String msgCode) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyMsgCode(telephone, msgCode);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "验证码：" + msgCode + "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证手机号-忘记密码
	 * 
	 * @author YJQ 2017年5月22日
	 * @param telephone
	 *            手机号
	 * @param idcard
	 *            身份证号
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyTelephoneRequire", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyTelephoneRequire(String telephone, String idcard) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyTelephone(telephone, 1);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "手机号：" + telephone + "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证邮箱-修改用户信息
	 * 
	 * @author YJQ 2017年5月22日
	 * @param request
	 *            请求
	 * @param email
	 *            邮箱
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyEmail(HttpServletRequest request, String email) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyEmailNoRepeat(email,
					utilService.queryUid(AppCons.SESSION_USER + request.getSession().getId()));
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "邮箱：" + email + "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 验证密码强度
	 * 
	 * @author YJQ 2017年5月27日
	 * @param pwd
	 *            密码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyPwd(String pwd) {
		PageInfo pageInfo = new PageInfo();

		try {
			verifyService.verifyPwd(pwd);
			pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SUCCESS.getMsg());

		} catch (PageInfoException pe) {
			pageInfo.setCode(pe.getCode());
			pageInfo.setMsg(pe.getMessage());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "用户注册密码：" + pwd + "验证失败");
		}

		return pageInfo;
	}

	/**
	 * 京东绑卡--获取验证码
	 * 
	 * @param tel
	 *            手机号
	 * @param name
	 *            姓名
	 * @param idCard
	 *            身份证号
	 * @param number
	 *            银行卡号
	 * @param bankShort
	 *            银行简称
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping(value = "/jdBindCardGetSecurityCode", method = RequestMethod.POST)
	public @ResponseBody Object jdBindCardGetSecurityCode(String tel, String name, String idCard, String number,
			String bankShort, HttpServletRequest request) {

		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);

		if (!StringUtils.isEmpty(uid)) {
			pi = usersService.getJDBindCardSecurityCode(uid, tel, name, idCard, number, bankShort);
		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());
		}

		return pi;
	}

	/**
	 * 京东绑卡--绑卡验证
	 * 
	 * @param idCard
	 *            身份证号
	 * @param name
	 *            银行名称
	 * @param number
	 *            银行卡号
	 * @param tel
	 *            电话
	 * @param bankShort
	 *            银行简称
	 * @param msgcode
	 *            验证码
	 * @param modelMap
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping(value = "/jdBindCardCertification", method = RequestMethod.POST)
	public @ResponseBody Object jdBindCardCertification(String idCard, String name, String number, String tel,
			String bankShort, String msgcode, ModelMap modelMap, HttpServletRequest request) {
		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			pi = usersService.jdBindCardCertification(uid, idCard, name, number, tel, bankShort, msgcode);

		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());
		}

		return pi;
	}

	/**
	 * 返回用户状态
	 * 
	 * @author YJQ 2017年4月14日
	 * @param request
	 *            请求
	 * @return 3009 用户未登录 3010 用户已登录未实名 200 用户已登录已实名
	 */
	@RequestMapping(value = "/queryUserStatus")
	@ResponseBody
	public Object queryUserStatus(HttpServletRequest request) {
		log.info("users-返回用户状态");
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersService.queryUserStatus(AppCons.SESSION_USER + request.getSession().getId());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "返回用户状态失败");
		}

		return pageInfo;
	}

	/**
	 * 处理未登录请求
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/unLogin")
	public @ResponseBody Object handleUnLogin() {
		PageInfo pi = new PageInfo();
		pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
		pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());

		return pi;
	}

	/**
	 * 返回用户加息券
	 * 
	 * @author YJQ 2017年7月4日
	 * @param request
	 *            请求
	 * @param pageNo
	 *            当前页数
	 * @param pageSize
	 *            行数
	 * @return 返回信息
	 */
	@RequestMapping("/queryRedPackageByUser")
	public Object queryRedPackage(HttpServletRequest request, Integer pageNo, Integer pageSize) {
		log.info("users-返回用户加息券");

		PageInfo pageInfo = new PageInfo(pageNo, pageSize);

		try {
			pageInfo = usersService.queryRedPackage(pageInfo, AppCons.SESSION_USER + request.getSession().getId());

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "返回用户加息券失败");
		}

		return pageInfo;
	}

	/**
	 * 返回指定金额加息券
	 * 
	 * @author YJQ 2017年7月4日
	 * @param request
	 *            请求
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            行数
	 * @param actualAmount
	 *            加息金额
	 * @return 返回信息
	 */
	@RequestMapping("/queryRedPackageByAmount")
	public Object queryRedPackage(HttpServletRequest request, Integer pageNo, Integer pageSize, Double actualAmount) {
		log.info("users-返回指定金额加息券");
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);

		try {
			pageInfo = usersService.queryRedPackage(pageInfo, actualAmount);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "返回指定金额加息券失败");
		}

		return pageInfo;
	}

	/**
	 * 处理未登录请求
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/systemUpdate")
	public @ResponseBody Object systemUpdate() {
		PageInfo pi = new PageInfo();
		pi.setCode(ResultParame.ResultInfo.SYSTEM_UPDATE.getCode());
		pi.setMsg(ResultParame.ResultInfo.SYSTEM_UPDATE.getMsg());

		return pi;
	}

}
