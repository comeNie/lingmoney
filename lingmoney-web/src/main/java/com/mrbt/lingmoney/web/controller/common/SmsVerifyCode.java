package com.mrbt.lingmoney.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.common.SmsService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 * 短信验证码
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/sms")
public class SmsVerifyCode {

	@Autowired
	private SmsService smsService;

	/**
	 * 注册短信
	 * 
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @param phone
	 *            电话号码
	 * @param picKey
	 *            图片key
	 * @param code
	 *            编码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/sendreg", method = RequestMethod.POST)
	public @ResponseBody Object sendreg(HttpServletResponse response, HttpServletRequest request, String phone,
			String picKey, String code) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.saveSendRegister(phone, pageInfo, picKey, code);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 忘记密码短信
	 * 
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @param phone
	 *            电话号码
	 * @param picKey
	 *            图片key
	 * @param code
	 *            编码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/sendModiPw", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPw(HttpServletResponse response, HttpServletRequest request, String phone, String picKey, String code) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.saveSendModiPw(phone, pageInfo, picKey, code);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 修改手机号-验证原手机号
	 * 
	 * @author YJQ 2017年6月1日
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @param phone
	 *            原手机号
	 * @param picKey
	 *            图片key
	 * @param picCode
	 *            编码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/sendModiPhoneVerifyOld", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPhoneVerifyOld(HttpServletResponse response, HttpServletRequest request,
			String phone, String picKey, String picCode) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.sendModiPhoneVerifyOld(AppCons.SESSION_USER + request.getSession().getId(), phone,
					pageInfo, picKey, picCode);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 修改注册手机号短信
	 * 
	 * @param request
	 *            请求
	 * @param phone
	 *            原手机号
	 * @param picKey
	 *            图片key
	 * @param picCode
	 *            编码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/sendModiPhone", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPhone(HttpServletRequest request, String phone, String picKey, String picCode) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.sendModiPhone(phone, AppCons.SESSION_USER + request.getSession().getId(),
					pageInfo, picKey, picCode);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @param phone
	 *            原手机号
	 * @param verifyCode
	 *            编码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public @ResponseBody Object verify(HttpServletResponse response, HttpServletRequest request, String phone,
			String verifyCode) {
		PageInfo pageInfo = new PageInfo();
		pageInfo = smsService.verifyRegisterCode(phone, verifyCode, pageInfo);
		return pageInfo;
	}

	/**
	 * 查询短信验证码
	 * 
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @param phone
	 *            原手机号
	 * @return 返回响应
	 */
	@RequestMapping(value = "/querySmsVerify", method = RequestMethod.POST)
	public @ResponseBody Object querySmsVerify(HttpServletResponse response, HttpServletRequest request, String phone) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.querySmsVerify(phone);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
