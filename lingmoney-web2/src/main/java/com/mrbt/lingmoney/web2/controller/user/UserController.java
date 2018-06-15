package com.mrbt.lingmoney.web2.controller.user;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.MyAccountService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;
import com.mrbt.lingmoney.web2.controller.BaseController;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web2.vo.user.UsersBaseInfoVo;

/**
 * 用户基础接口
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

	Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private UsersService usersService;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private MyAccountService myAccountService;
	@Autowired
	private UsersAccountSetService usersAccountSetService;

	/**
	 * 用户注册
	 * 
	 * @author YJQ 2017年4月12日
	 * @param request
	 *            request
	 * @param telephone
	 *            手机号
	 * @param password
	 *            密码
	 * @param invitationCode
	 *            推荐码
	 * @param verificationCode
	 *            验证码
	 * @param channel
	 *            channel
	 * @return pageInfo
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody Object regist(HttpServletRequest request, String telephone, String password,
            String invitationCode, String verificationCode, Integer channel) {
		log.info("users-用户注册");
		if (null == channel) {
			channel = 0;
		}
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = usersService.regist(telephone, password, invitationCode, verificationCode,
					request.getSession().getServletContext().getRealPath(""), channel);
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("注册失败");
			e.printStackTrace();
			log.error("手机：" + telephone + "注册失败:" + e);
		}
		return pageInfo;
	}
	
	/**
	 * 用户登录
	 * @param request request
	 * @param account 登录手机号
	 * @param password 登录密码
	 * @param pwa 访问客户端
	 * @return 登录结果
	 */
	@RequestMapping(value = "/login")
	public @ResponseBody Object login(HttpServletRequest request, String account, String password) {
		log.info("users-用户登录:" + request.getSession().getId());
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = usersService.loginForWeb(request, account, password);
			
			// #start set data
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				UserUnionInfo userUnionInfo = (UserUnionInfo) pageInfo.getObj();
                UsersBaseInfoVo v = new UsersBaseInfoVo();
                BeanUtils.copyProperties(v, userUnionInfo);
                pageInfo.setObj(v);
                request.getSession().setAttribute("uid", userUnionInfo.getId());
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "登录失败");
		}

		return pageInfo;
	}


	/**
	 * 验证用户密码
	 * @author YJQ 2017年8月7日
	 * @param request request
	 * @param password 密码
	 * @return pageInfo	返回数据包装
	 */
	@RequestMapping(value = "/verifyUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyUserPwd(HttpServletRequest request, String password) {
		log.info("users-验证用户密码");
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = usersService.verifyUserPwd(uId, password);
            }

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("验证用户密码失败");
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**用户退出
	 * @author YJQ 2017年8月7日
	 * @param request request
	 * @return pageInfo
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpServletRequest request) {
		log.info("users-用户退出\\t" + request.getSession().getId());
		PageInfo pageInfo = null;
		try {
			pageInfo = usersService.logout(CommonMethodUtil.getRedisUidKeyByRequest(request));
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("注销失败");
			e.printStackTrace();
			log.error("注销失败:" + e);
		}
        request.getSession().invalidate();
		return pageInfo;
	}

	/**
	 * 用户实名认证
	 * 
	 * @author YJQ 2017年4月14日
	 * @param request 
	 *             request
	 * @param name
	 *             name
	 * @param idCard
	 *            idCard
	 * @return pageInfo
	 */
	@RequestMapping(value = "/userVerified")
	@ResponseBody
	public Object userVerified(HttpServletRequest request, String name, String idCard) {
		log.info("users-用户实名认证");
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = usersService.userVerified(null, uId, name, idCard);
            }

		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("实名认证失败");
			e.printStackTrace();
			log.error(name + "：实名认证失败:" + e);
		}
		return pageInfo;
	}

	/**
     * 返回用户状态
     * 
     * @author YJQ 2017年4月14日
     * @param request request
     * @return 1009 登录超时 3010 用户已登录未实名 200 用户已登录已实名 500 服务器错误
     */
	@RequestMapping(value = "/queryUserStatus")
	@ResponseBody
	public Object queryUserStatus(HttpServletRequest request) {
		log.info("users-返回用户状态:" + request.getSession().getId());
		PageInfo pageInfo = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = usersService.queryUserStatus(uId);
            }
		} catch (Exception e) {
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("返回用户状态失败");
			e.printStackTrace();
			log.error("返回用户状态失败:" + e);
		}
		return pageInfo;
	}

	// =================================↓↓↓↓↓↓ 京东绑卡相关接口
	// ↓↓↓↓↓↓==================================================

	/**
	 * 京东绑卡--获取验证码
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
	 * @return pi
	 */
	@RequestMapping(value = "/jdBindCardGetSecurityCode", method = RequestMethod.POST)
	public @ResponseBody Object jdBindCardGetSecurityCode(HttpServletRequest request, String tel, String name, String idCard,
			String number, String bankShort) {
		PageInfo pi = new PageInfo();
		
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = usersService.getJDBindCardSecurityCode(uId, tel, name, idCard, number, bankShort);
            }
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	/**
	 * 京东绑卡--绑卡验证
	 * @param request
	 *            request
	 * @param idCard
	 *             身份证号
	 * @param name
	 *             姓名
	 * @param number
	 *             卡号
	 * @param tel
	 *             手机号
	 * @param bankShort
	 *             银行简称
	 * @param msgcode
	 *             验证码
	 * @return pi
	 */
	@RequestMapping(value = "/jdBindCardCertification", method = RequestMethod.POST)
	public @ResponseBody Object jdBindCardCertification(HttpServletRequest request, String idCard, String name, String number,
			String tel, String bankShort, String msgcode) {
		PageInfo pi = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = usersService.jdBindCardCertification(uId, idCard, name, number, tel, bankShort, msgcode);
            }
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	/**
	 * 查询支持银行（目前仅京东绑卡使用）
	 * @return pi
	 */
	@RequestMapping(value = "/listSupportBank", method = RequestMethod.POST)
	public @ResponseBody Object listSupportBank() {
		PageInfo pi = usersService.listSupportBank();
		return pi;
	}

	/**
	 * 查询已绑定的京东银行卡
	 * @param request request
	 * @return pi
	 */
	@RequestMapping(value = "/listJDBankCard", method = RequestMethod.POST)
	public @ResponseBody Object listJDBankCard(HttpServletRequest request) {
		PageInfo pi = new PageInfo();
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = usersService.listJDBankCard(uId);
            }

		} catch (Exception e) {
			e.printStackTrace();

			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	/**
	 * 京东绑卡是否可用
	 * 
	 * @return pi
	 */
	@RequestMapping(value = "/isJDBindCardAvailable", method = RequestMethod.POST)
	public @ResponseBody Object isJDBindCardAvailable() {
		PageInfo pi = new PageInfo();

		// 京东绑卡是否可用
		String result = null;
		try {
			result = (String) redisGet.getRedisStringResult(AppCons.JD_BINDCARD_AVALIABLE);
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		if (StringUtils.isBlank(result)) {
			result = "N";
		}
		log.info("京东绑卡是否可用：" + result);

		if ("Y".equals(result)) {
			pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("可用");
		} else {
			pi.setCode(ResultNumber.ONE_ZERO_ZERO_SEVEN.getNumber());
			pi.setMsg("不可用");
		}

		return pi;
	}

	/**
	 * 设置默认银行卡
	 * @param request request
	 * @param id id
	 * @return pageInfo
	 */
	@RequestMapping(value = "/setBankCardDefault", method = RequestMethod.POST)
	public @ResponseBody Object setBankCardDefault(HttpServletRequest request, Integer id) {
		PageInfo pageInfo = new PageInfo();

		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = usersAccountSetService.setDefaultBinkCard(id, uId);
            }

		} catch (Exception e) {
			e.printStackTrace();

			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("设置默认银行卡失败");
		}

		return pageInfo;
	}

	/**
	 * 删除银行卡 
	 * @param request request
	 * @param  id  id
	 * @return pi
	 */
	@RequestMapping(value = "/deleteBankCard", method = RequestMethod.POST)
	public @ResponseBody Object deleteBankCard(HttpServletRequest request, Integer id) {
		PageInfo pi = new PageInfo();

		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pi.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pi = myAccountService.deleteBankCard(uId, id);
            }

		} catch (Exception e) {
			e.printStackTrace();

			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg("删除银行卡失败");
		}

		return pi;
	}

	/**
	 * 根据卡号前六位检索银行卡信息
	 * @param number number
	 * @return pi
	 */
	@RequestMapping("/queryBankInfoByTopSix")
	public @ResponseBody Object queryBankInfoByTopSix(String number) {
		PageInfo pi = new PageInfo();

		try {
			pi = myAccountService.queryBankInfoByTopSix(number);
		} catch (Exception e) {
			e.printStackTrace();

			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	// =================================↑↑↑↑↑↑ 京东绑卡相关接口
	// ↑↑↑↑↑↑========================================

	/**
	 * 返回用户加息券
	 * 
	 * @author YJQ 2017年 7月4日
	 * @param request request
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @return pageInfo
	 */
	@RequestMapping("/queryRedPackageByUser")
	public Object queryRedPackage(HttpServletRequest request, Integer pageNo, Integer pageSize) {
		log.info("users-返回用户加息券");
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			String uId = CommonMethodUtil.getUidBySession(request);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo = usersService.queryRedPackage(pageInfo, uId);
            }

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("返回用户状态失败");
			e.printStackTrace();
			log.error("返回用户状态失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 返回指定金额加息券
	 * 
	 * @author YJQ 2017年7月4日
	 * @param request request
	 * @param pageNo pageNo
	 * @param pageSize pageSize
	 * @param actualAmount actualAmount
	 * @return pageInfo
	 */
	@RequestMapping("/queryRedPackageByAmount")
	public Object queryRedPackage(HttpServletRequest request, Integer pageNo, Integer pageSize, Double actualAmount) {
		log.info("users-返回指定金额加息券");
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			pageInfo = usersService.queryRedPackage(pageInfo, actualAmount);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("返回用户状态失败");
			e.printStackTrace();
			log.error("返回用户状态失败:" + e);
		}
		return pageInfo;
	}

    /**
     * 修改密码
     * 
     * @author YJQ 2017年4月18日
     * @param request request
     * @param oldPassword oldPassword
     * @param password password
     * @return pageInfo
     */
    @RequestMapping(value = "/modifyPassword")
    @ResponseBody
    public Object modifyPassword(HttpServletRequest request, String oldPassword, String password) {
        log.info("users-修改密码");
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.modifyPassword(oldPassword, password,
                    CommonMethodUtil.getRedisUidKeyByRequest(request));
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("修改密码失败");
            e.printStackTrace();
            log.error("修改密码失败:" + e);
        }
        return pageInfo;
    }
    
}
