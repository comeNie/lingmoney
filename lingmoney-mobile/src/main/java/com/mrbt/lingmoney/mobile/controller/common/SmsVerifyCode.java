package com.mrbt.lingmoney.mobile.controller.common;

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
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

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
	 * @param response req
	 * @param request res
	 * @param phone phone
	 * @param picKey picKey
	 * @param code code
	 * @return pageInfo
	 */ 
	@RequestMapping(value = "/sendreg", method = RequestMethod.POST)
	public @ResponseBody Object sendreg(HttpServletResponse response, HttpServletRequest request, String phone, String picKey, String code) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.saveSendRegister(phone, pageInfo, picKey, code);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 忘记密码短信
	 * @param response res
	 * @param request req
	 * @param phone phone
	 * @param picKey picKey
	 * @param code code
	 * @return pageInfo
	 */
	@RequestMapping(value = "/sendModiPw", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPw(HttpServletResponse response, HttpServletRequest request, String phone, String picKey, String code) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.saveSendModiPw(phone, pageInfo, picKey, code);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 修改手机号-验证原手机号
	 * @author YJQ  2017年6月1日
	 * @param response res
	 * @param request req
	 * @param phone 原手机号
	 * @param picKey picKey
	 * @param picCode picCode
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/sendModiPhoneVerifyOld", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPhoneVerifyOld(HttpServletResponse response, HttpServletRequest request, String phone, String token, String picKey, String picCode) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.sendModiPhoneVerifyOld(AppCons.TOKEN_VERIFY + token, phone, pageInfo,  picKey,  picCode);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}
	/**
	 * 修改注册手机号短信
	 * 
	 * @param response req
	 * @param request  res
	 * @param picKey   picKey
	 * @param picCode  picCode
	 * @param targetPhone targetPhone
	 * @param token    token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/sendModiPhone", method = RequestMethod.POST)
	public @ResponseBody Object sendModiPhone(HttpServletResponse response, HttpServletRequest request, String targetPhone, String token, String picKey, String picCode) {

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.sendModiPhone(targetPhone, AppCons.TOKEN_VERIFY + token, pageInfo, picKey, picCode);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pageInfo;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param response req
	 * @param request res
	 * @param phone phone
	 * @param verifyCode verifyCode
	 * @return pageInfo
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
	 * @param response req
	 * @param request res
	 * @param phone phone
	 * @return pageInfo
	 */
	@RequestMapping(value = "/querySmsVerify", method = RequestMethod.POST)
	public @ResponseBody Object querySmsVerify(HttpServletResponse response, HttpServletRequest request, String phone) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = smsService.querySmsVerify(phone);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
