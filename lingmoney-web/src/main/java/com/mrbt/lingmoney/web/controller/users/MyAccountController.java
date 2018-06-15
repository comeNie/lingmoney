package com.mrbt.lingmoney.web.controller.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.ExchangeCode;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.ExchangeCodeService;
import com.mrbt.lingmoney.service.web.MyAccountService;
import com.mrbt.lingmoney.service.web.UsersIndexService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.GridPage;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.controller.common.CommonMethodUtil;

/**
 * @author syb
 * @date 2017年5月10日 下午2:33:51
 * @version 1.0
 * @description 我的账户
 **/
@Controller
@RequestMapping("/myAccount")
public class MyAccountController {
	private static final Logger LOG = LogManager.getLogger(MyAccountController.class);
	
	@Autowired
	private UsersIndexService usersIndexService;
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private UsersAccountSetService usersAccountSetService;
	@Autowired
	private ExchangeCodeService exchangeCodeService;
	@Autowired
	private UsersService usersService;
	
	/**
	 * 我的账户，存管账户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/depository")
	public String depository(HttpServletRequest request, ModelMap model) {
		try {
			String uId = CommonMethodUtil.getUidBySession(request); //获取Uid
			if (!StringUtils.isEmpty(uId)) {
				
				usersIndexService.queryHxAccountInfo(model, uId);
				
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService); //用户信息
			} else {
				return "users/login";
			}
		} catch (Exception e) {
			LOG.error("进入我的资料首页失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		return "myAccount/depository";
	}

	/**
	 * 我的资料页面
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/myInfo")
	public String myInfo(HttpServletRequest request, ModelMap model) {
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("进入我的资料首页失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}
		return "myAccount/myInfo";
	}

	/**
	 * 我的领宝页面
	 * 
	 * @param model
	 *            数据包装
	 * @param pageNo
	 *            当前页数
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/lingbaoRecord")
	public String lingbaoRecord(ModelMap model, Integer pageNo, HttpServletRequest request) {
		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);

		if (StringUtils.isNotBlank(uid)) {
			try {
				pi = myAccountService.listLingbaoRecord(pageNo, uid, null);
				if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					GridPage<ActivityRecord> gridPage = new GridPage<ActivityRecord>();
					gridPage.setRows(pi.getRows());
					gridPage.setTotal(pi.getTotal());
					model.addAttribute("gridPage", gridPage);
					model.addAttribute("pageNo", pi.getNowpage());
					model.addAttribute("pageSize", pi.getSize());
				}
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("查询我的领宝数据失败，系统错误" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myAccount/lingbaoRecord";
	}

	/**
	 * 修改用户属性信息
	 * 
	 * @author YJQ 2017年5月20日
	 * @param sex
	 *            性别
	 * @param picture
	 *            头像
	 * @param education
	 *            教育
	 * @param job
	 *            工作
	 * @param email
	 *            邮箱
	 * @param wechat
	 *            微信
	 * @param nickName
	 *            昵称
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping("/updateUsers")
	public @ResponseBody Object updateUsers(String sex, String picture, String education, String job, String email,
			String wechat, String nickName, HttpServletRequest request) {
		PageInfo result = new PageInfo();
		try {
			UsersProperties usersProperties = new UsersProperties();
			usersProperties.setSex(sex);
			usersProperties.setPicture(picture);
			usersProperties.setEducation(education);
			usersProperties.setJob(job);
			usersProperties.setEmail(email);
			usersProperties.setWechat(wechat);
			usersProperties.setNickName(nickName);
			result = usersAccountSetService.modifyUsersInfo(usersProperties, AppCons.SESSION_USER
					+ request.getSession().getId());
		} catch (Exception e) {
			result.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			result.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
			LOG.error("更新用户属性信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 银行卡绑定页面
	 * 
	 * @param model
	 *            数据包装
	 * @param request
	 *            请求
	 * @return 返回信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/bindBankCard")
	public String bindBankCard(ModelMap model, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);

		if (StringUtils.isNotBlank(uid)) {
			// 查询用户认证信息
			try {
				myAccountService.packageBindBankCardInfo(model, uid);
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
				
				// 京东绑卡是否可用 N不可用 Y可用
//				String result = (String) redisGet.getRedisStringResult(AppCons.JD_BINDCARD_AVALIABLE);
//				if (StringUtils.isBlank(result)) {
//					result = "N";
//				}
//				model.addAttribute("jdBindCardAvailable", result);
				model.addAttribute("jdBindCardAvailable", "Y");
//
//				LOG.info("京东绑卡是否可用：" + result);

			} catch (Exception e) {
				LOG.error("进入用户绑卡页面失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "myAccount/bindBankCard";
	}

	/**
	 * 设置默认银行卡
	 * 
	 * @author YJQ 2017年5月18日
	 * @param request
	 *            请求
	 * @param id
	 *            银行卡索引
	 * @return 返回信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/setBankCardDefault")
	@ResponseBody
	public Object setBankCardDefault(HttpServletRequest request, Integer id) {
		PageInfo pageInfo = new PageInfo();
		try {
			String uid = CommonMethodUtil.getUidBySession(request);
			pageInfo = usersAccountSetService.setDefaultBinkCard(id, uid);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, LOG, "设置默认银行卡失败，系统错误");
		}

		return pageInfo;
	}
	
	/**
	 * 删除银行卡
	 * 
	 * @param request
	 *            请求
	 * @param id
	 *            银行卡索引
	 * @return 返回信息
	 */
	@RequestMapping("/deleteBankCard")
	public @ResponseBody Object deleteBankCard(HttpServletRequest request, Integer id) {
		PageInfo pi = new PageInfo();
		
		String uid = CommonMethodUtil.getUidBySession(request);
		if (!StringUtils.isEmpty(uid)) {
			pi = myAccountService.deleteBankCard(uid, id);

		} else {
			pi.setCode(ResultParame.ResultInfo.LOGIN_TIMEOUT.getCode());
			pi.setMsg(ResultParame.ResultInfo.LOGIN_TIMEOUT.getMsg());
		}
		
		return pi;
	}

	/**
	 * 修改密码页面
	 * 
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@RequestMapping("/changePassword")
	public String changePassword(HttpServletRequest request, ModelMap model) {
		try {
			CommonMethodUtil.modelUserBaseInfo(model, request, usersService);
		} catch (Exception e) {
			LOG.error("进入修改密码页面失败，系统错误" + e.getMessage());
			e.printStackTrace();
			return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		}

		return "myAccount/changePassword";
	}

	/**
	 * 验证旧密码
	 * 
	 * @param oldPsw
	 *            旧密码
	 * @param response
	 *            响应
	 * @param request
	 *            请求
	 * @throws IOException
	 *             io异常
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/oldPsw")
	public void oldPsw(String oldPsw, HttpServletResponse response, HttpServletRequest request) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charet=utf-8");

			String uid = CommonMethodUtil.getUidBySession(request);
			if (uid != null) {
				boolean result = usersAccountSetService.validUserPassword(uid, oldPsw);
				if (!result) {
					out.print("旧密码有误");
				}

			} else {
				out.print("overtime");
			}

		} catch (Exception e) {
			LOG.error("验证用户密码失败，系统错误" + e.getMessage());
			e.printStackTrace();

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}

	}

	/**
	 * 修改密码
	 * 
	 * @param newPsw
	 *            新密码
	 * @param oldPsw
	 *            旧密码
	 * @param response
	 *            响应
	 * @param session
	 *            缓存
	 * @throws IOException
	 *             io异常
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping(value = "/updatePsw")
	public void updatePsw(String newPsw, String oldPsw, HttpServletResponse response, HttpSession session) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charet=utf-8");
			String key = AppCons.SESSION_USER + session.getId();
			PageInfo pi = usersAccountSetService.modifyPassword(oldPsw, newPsw, key);

			if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				out.print("success");
			} else {
				out.print(pi.getMsg());
			}

		} catch (Exception e) {
			LOG.error("更改密码失败" + e.getMessage());
			e.printStackTrace();

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 我的领钱儿--》理财日历展示数据
	 * 
	 * @param dateTime
	 *            时间
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	@RequestMapping("/statisticsMonth")
	public void statisticsMonth(String dateTime, HttpServletRequest request, HttpServletResponse response) {
		try {
			String uid = CommonMethodUtil.getUidBySession(request);

			LOG.info("MyUtils" + "用户：" + uid + "\t" + "理财记录流水表当前月份是：" + dateTime);

			if (StringUtils.isNotBlank(uid)) {
				JSONObject jsonObject = myAccountService.queryStaticMonth(dateTime, uid);
				jsonObject.write(response.getWriter());
			}

		} catch (IOException e) {
			e.printStackTrace();
			LOG.error("查询用户某月流水信息失败，系统错误" + e.getMessage());
		}

	}

	/**
	 * 我的礼品
	 * 
	 * @param pageNo
	 *            当前页
	 * @param request
	 *            请求
	 * @param model
	 *            数据包装
	 * @return 返回信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectGiftDetail")
	public String selectGiftDetail(Integer pageNo, HttpServletRequest request, ModelMap model) {
		PageInfo pi = new PageInfo();

		String uid = CommonMethodUtil.getUidBySession(request);
		if (StringUtils.isNotBlank(uid)) {
			// 查询用户认证信息
			try {
				pi = myAccountService.listGiftInfo(uid, pageNo);
				if (pi.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
					GridPage<Map<String, Object>> gridPage = new GridPage<Map<String, Object>>();
					gridPage.setRows(pi.getRows());
					gridPage.setTotal(pi.getTotal());
					model.addAttribute("gridPage", gridPage);
					model.addAttribute("pageNo", pi.getNowpage());
					model.addAttribute("pageSize", pi.getSize());
					model.addAttribute("totalSize", gridPage.getTotal());
				}
				CommonMethodUtil.modelUserBaseInfo(model, request, usersService);

			} catch (Exception e) {
				LOG.error("进入用户礼品页面失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			LOG.info("进入用户礼品页面失败，用户session已过期");
			return "/users/login";
		}

		return "/myAccount/myGift";
	}

	/**
	 * 验证兑换码是否可用
	 * 
	 * @param code
	 *            兑换码
	 * @param response
	 *            响应
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping("/checkGift")
	public void check(String code, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");

			List<ExchangeCode> list = exchangeCodeService.findByCode(code, 0);
			if (list.size() > 0) {
				out.print("该兑换码可用");
			} else {
				out.print("该兑换码不存在或已过期");
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("查询礼品兑换码失败" + e.getMessage());

		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 兑换礼品
	 * 
	 * @param code
	 *            兑换码
	 * @param request
	 *            请求
	 * @return 返回信息
	 * @throws Exception
	 *             异常
	 */
	@RequestMapping("/exchangeGift")
	public String exchange(String code, HttpServletRequest request) {
		String uid = CommonMethodUtil.getUidBySession(request);
		if (StringUtils.isNotBlank(uid)) {
			// 查询用户认证信息
			try {
				exchangeCodeService.exchangeGift(code, uid);
			} catch (Exception e) {
				LOG.error("兑换礼品失败" + e.getMessage());
				e.printStackTrace();
				return String.valueOf(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			}

		} else {
			return "users/login";
		}

		return "redirect:/myAccount/lingbaoRecord";
	}

	/**
	 * 修改头像
	 * 
	 * @author YJQ 2017年5月20日
	 * @param request
	 *            请求
	 * @throws Exception
	 *             异常
	 * @return 返回信息
	 */
	@RequestMapping("/modifyAvatar")
	@ResponseBody
	public Object modifyAvatar(MultipartHttpServletRequest request) {
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyAvatar(request.getFile("__avatar1"), AppCons.SESSION_USER
					+ request.getSession().getId());

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("修改头像失败");
			e.printStackTrace();
			LOG.error("修改头像失败:" + e);
		}

		return pageInfo;
	}
	
	/**
	 * 查询所有快捷支付银行 京东绑卡用
	 * 
	 * @return 返回信息
	 */
	@RequestMapping("/listQuickPaymentBank")
	public @ResponseBody Object listQuickPaymentBank() {
		return myAccountService.listQuickPaymentBank();
	}
	
	/**
	 * 验证银行卡是否被绑定
	 * 
	 * @param number
	 *            卡号
	 * @return 返回信息
	 */
	@RequestMapping("/isCardNumberBeBinded")
	public @ResponseBody Object isCardNumberBeBinded(String number) {
		return myAccountService.isCardNumberBeBinded(number);
	}
	
	/**
	 * 根据卡号前六位检索银行卡信息
	 * 
	 * @param number
	 *            卡号
	 * @return 返回信息
	 */
	@RequestMapping("/queryBankInfoByTopSix")
	public @ResponseBody Object queryBankInfoByTopSix(String number) {
		return myAccountService.queryBankInfoByTopSix(number);
	}
	
	/**
	 * 验证身份证号是否绑定
	 * 
	 * @param request
	 *            请求
	 * @param idCard
	 *            身份证号
	 * @return 返回信息
	 */
	@RequestMapping("/testBindCardID")
	public @ResponseBody Object testBindCardID(HttpServletRequest request, String idCard) {
		String uid = CommonMethodUtil.getUidBySession(request);
		
		return myAccountService.testBindCardId(uid, idCard);
	}
}
