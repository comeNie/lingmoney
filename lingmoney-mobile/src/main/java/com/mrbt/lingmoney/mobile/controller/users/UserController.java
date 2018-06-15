package com.mrbt.lingmoney.mobile.controller.users;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.mobile.vo.users.UsersBaseInfoVo;
import com.mrbt.lingmoney.model.UserUnionInfo;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.userfinance.UserFinanceHomeService;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.service.web.MyAccountService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

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
	@Autowired
	private UtilService utilService;
    @Autowired
    private UserFinanceHomeService userFinanceHomeService;

	/**
	 * 用户注册
	 * 
	 * @author YJQ 2017年4月12日
	 * @param request request
	 * @param telephone 
	 *            手机号
	 * @param password
	 *            密码
	 * @param invitationCode
	 *            推荐码
	 * @param verificationCode
	 *            验证码
	 * @param channel channel
	 * @return pageInfo
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Object regist(HttpServletRequest request, String telephone, String password, String invitationCode,
			String verificationCode, Integer channel) {
		log.info("users-用户注册");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersService.regist(telephone, password, invitationCode, verificationCode,
					request.getSession().getServletContext().getRealPath(""), channel);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("注册失败");
			e.printStackTrace();
			log.error("手机：" + telephone + "注册失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 用户登录
	 * 
	 * @author YJQ 2017年4月12日
	 * @param request 
	 *             request
	 * @param account
	 *             account
	 * @param password
	 *             password
	 * @return pageInfo
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, String account, String password) {
		log.info("users-用户登录");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersService.loginForMobile(request, account, password);

			// #start set vo
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				UserUnionInfo users = (UserUnionInfo) pageInfo.getObj();
				UsersBaseInfoVo v = new UsersBaseInfoVo();
				BeanUtils.copyProperties(v, users);
				pageInfo.setObj(v);
			}
			// #end

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("登录失败");
			e.printStackTrace();
			log.error(account + "：登录失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 验证用户密码
	 * 
	 * @author YJQ 2017年8月7日
	 * @param request
	 *             request
	 * @param token
	 *             token
	 * @param password
	 *             password
	 * @return pageInfo
	 */
	@RequestMapping(value = "/verifyUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Object verifyUserPwd(HttpServletRequest request, String token, String password) {
		log.info("users-验证用户密码");
		PageInfo pageInfo = null;
		try {
			String userId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersService.verifyUserPwd(userId, password);

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("验证用户密码失败");
			e.printStackTrace();
		}
		return pageInfo;
	}
	/**
	 * @author YJQ 2017年8月7日
	 * @param request
	 *             request
	 * @param token
	 *             token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Object logout(HttpServletRequest request, String token) {
		log.info("users-用户登录");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersService.logout(AppCons.TOKEN_VERIFY + token);

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("注销失败");
			e.printStackTrace();
			log.error("注销失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 用户实名认证
	 * 
	 * @author YJQ 2017年4月14日
	 * @param request 
	 *             request
	 * @param token
	 *             token
	 * @param name
	 *             name
	 * @param idCard
	 *            idCard
	 * @return pageInfo
	 */
	@RequestMapping(value = "/userVerified")
	@ResponseBody
	public Object userVerified(HttpServletRequest request, String name, String idCard, String token) {
		log.info("users-用户实名认证");
		PageInfo pageInfo = null;
		try {
			String userId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersService.userVerified(token, userId, name, idCard);
		} catch (Exception e) {
			pageInfo = new PageInfo();
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
	 * @param token token
	 * @return 3009 用户未登录 3010 用户已登录未实名 200 用户已登录已实名
	 */
	@RequestMapping(value = "/queryUserStatus")
	@ResponseBody
	public Object queryUserStatus(HttpServletRequest request, String token) {
		log.info("users-返回用户状态");
		PageInfo pageInfo = null;
		try {
			String userId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersService.queryUserStatus(userId);
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
	 * 更新友盟推送token
	 * 
	 * @author YJQ 2017年5月24日
	 * @param request request
	 * @param token token
	 * @param deviceToken deviceToken
	 * @param deviceType deviceType
	 * @return pageInfo
	 */
	@RequestMapping(value = "/updateUserDeviceToken")
	@ResponseBody
	public Object updateUserDeviceToken(HttpServletRequest request, String token, String deviceToken,
			Integer deviceType) {
		log.info("users-保存用户deviceToken和deviceType");
		PageInfo pageInfo = null;
		try {
			String userId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersService.updateUserDeviceToken(userId, deviceToken, deviceType);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("操作失败");
			e.printStackTrace();
			log.error("保存用户deviceToken和deviceType失败:" + e);
		}
		return pageInfo;
	}

	// =================================↓↓↓↓↓↓ 京东绑卡相关接口
	// ↓↓↓↓↓↓==================================================

	/**
	 * 京东绑卡--获取验证码
	 * @param token
	 *            token
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
	public @ResponseBody Object jdBindCardGetSecurityCode(String token, String tel, String name, String idCard,
			String number, String bankShort) {
		PageInfo pi = new PageInfo();

		String tokenKey = AppCons.TOKEN_VERIFY + token;

		try {
			pi = usersService.getJDBindCardSecurityCode(getUid(tokenKey), tel, name, idCard, number, bankShort);
		} catch (Exception e) {
			e.printStackTrace();
			pi.setCode(ResultInfo.SERVER_ERROR.getCode());
			pi.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}

		return pi;
	}

	/**
	 * 京东绑卡--绑卡验证
	 * @param token
	 *             token
	 * @param idCard
	 *             idCard
	 * @param name
	 *             name
	 * @param number
	 *             number
	 * @param tel
	 *             tel
	 * @param bankShort
	 *             bankShort
	 * @param msgcode
	 *             msgcode
	 * @return pi
	 */
	@RequestMapping(value = "/jdBindCardCertification", method = RequestMethod.POST)
	public @ResponseBody Object jdBindCardCertification(String token, String idCard, String name, String number,
			String tel, String bankShort, String msgcode) {
		PageInfo pi = new PageInfo();

		String tokenKey = AppCons.TOKEN_VERIFY + token;
		try {
			pi = usersService.jdBindCardCertification(getUid(tokenKey), idCard, name, number, tel, bankShort, msgcode);
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
	 * @param token token
	 * @return pi
	 */
	@RequestMapping(value = "/listJDBankCard", method = RequestMethod.POST)
	public @ResponseBody Object listJDBankCard(String token) {
		String tokenKey = AppCons.TOKEN_VERIFY + token;
		PageInfo pi = new PageInfo();

		try {
			pi = usersService.listJDBankCard(getUid(tokenKey));
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
	 * @param token token
	 * @param id id
	 * @return pageInfo
	 */
	@RequestMapping(value = "/setBankCardDefault", method = RequestMethod.POST)
	public @ResponseBody Object setBankCardDefault(String token, Integer id) {
		PageInfo pageInfo = new PageInfo();

		try {
			String uId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersAccountSetService.setDefaultBinkCard(id, uId);
		} catch (Exception e) {
			e.printStackTrace();

			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("设置默认银行卡失败");
		}

		return pageInfo;
	}

	/**
	 * 删除银行卡 
	 * @param token token
	 * @param  id  id
	 * @return pi
	 */
	@RequestMapping(value = "/deleteBankCard", method = RequestMethod.POST)
	public @ResponseBody Object deleteBankCard(String token, Integer id) {
		PageInfo pi = new PageInfo();

		String tokenKey = AppCons.TOKEN_VERIFY + token;

		try {
			pi = myAccountService.deleteBankCard(getUid(tokenKey), id);
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
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping("/queryRedPackageByUser")
	public Object queryRedPackage(HttpServletRequest request, Integer pageNo, Integer pageSize, String token) {
		log.info("users-返回用户加息券");
		PageInfo pageInfo = new PageInfo(pageNo, pageSize);
		try {
			String userId = utilService.queryUid(AppCons.TOKEN_VERIFY + token);
			pageInfo = usersService.queryRedPackage(pageInfo, userId);
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
     * 进入风险测评问答页面
     * 
     * @author yiban
     * @date 2018年3月23日 上午11:25:36
     * @version 1.0
     * @param token
     * @param model
     * @param type 1表示重新测试
     * @return
     *
     */
    @RequestMapping(value = "/riskAssessmentQA")
    public String riskAssessmentQA(String token, Model model, Integer type) {
        if (null == type) {
            // 如果用户登录，查看是否有测评结果，如果有，直接进入结果页
            String uid = getUid(AppCons.TOKEN_VERIFY + token);
            if (StringUtils.isNotEmpty(uid)) {
                Integer score = usersService.hasTestRisAssessment(uid);
                if (score != null && score.intValue() > 0) {
                    model.addAttribute("token", token);
                    model.addAttribute("score", score);
                    return "riskAssessmentResult";
                }
            }
        }
        model.addAttribute("token", token);
        return "riskAssessmentQA";
    }

    /**
     * 查看测评结果
     * 
     * @author yiban
     * @date 2018年3月23日 上午11:30:10
     * @version 1.0
     * @param score
     * @param uid
     * @param model
     * @return
     *
     */
    @RequestMapping(value = "/riskAssessmentResult")
    public String riskAssessmentResult(Integer score, String token, Model model) {
        String uid = getUid(AppCons.TOKEN_VERIFY + token);
        usersService.saveOrUpdateAssessmentResult(uid, score);
        model.addAttribute("score", score);
        model.addAttribute("token", token);
        return "riskAssessmentResult";
    }

    /**
     * 进入我的推荐码页面
     * 
     * @author yiban
     * @date 2018年3月23日 下午5:37:58
     * @version 1.0
     * @param token
     * @param model
     * @return
     *
     */
    @RequestMapping(value = "/gotoMyRefferCodePage")
    public String gotoMyRefferCodePage(String token, Model model) {
        model.addAttribute("token", token);
        return "myRefferCode";
    }

    /**
     * 进入我推荐的人页面
     * 
     * @author yiban
     * @date 2018年3月23日 下午5:37:58
     * @version 1.0
     * @param token
     * @param model
     * @return
     *
     */
    @RequestMapping(value = "/gotoListMyRecommendersPage")
    public String gotoListMyRecommendersPage(String token, Model model) {
        model.addAttribute("token", token);
        return "myRefferPerson";
    }

    /**
     * 查询推荐码 ，二维码
     * @param token
     *            token
     * @param request
     *            request
     * @return pageInfo codePath 图片 referralCode 推荐码
     */
    @RequestMapping("/getMyRefferCode")
    public @ResponseBody Object myCode(@RequestParam(required = true) String token, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        PageInfo pageInfo = null;
        try {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
            pageInfo.setMsg(ResultInfo.SUCCESS.getMsg());
            String uId = getUid(AppCons.TOKEN_VERIFY + token);
            if (StringUtils.isEmpty(uId)) {
                pageInfo.setResultInfo(ResultInfo.LOGIN_OVERTIME);
            } else {
                pageInfo.setObj(userFinanceHomeService.findUserReferralCode(uId, request));
                json.put("obj", pageInfo.getObj());
            }

        } catch (Exception e) {
            e.printStackTrace();
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
        }
        json.put("code", pageInfo.getCode());
        json.put("msg", pageInfo.getMsg());

        return json;
    }

    /**
     * 查询我推荐的人
     * 
     * @param token token
     * @return pageInfo 
     */
    @RequestMapping(value = "/listMyRecommenders/{token}")
    @ResponseBody
    public Object myRecommender(@PathVariable("token") String token) {
        log.info("users-查询推荐我的人");
        JSONObject json = new JSONObject();
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.queryRecomUsersByUid(AppCons.TOKEN_VERIFY + token);
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("查询我推荐的人失败");
            e.printStackTrace();
            log.error("查询我推荐的人失败:" + e);
        }

        json.put("rows", pageInfo.getRows());
        json.put("total", pageInfo.getTotal());
        json.put("code", pageInfo.getCode());
        json.put("msg", pageInfo.getMsg());

        return json;
    }

    /**
     * wonder+备用接口
     * 
     * @author yiban
     * @date 2018年3月24日 下午12:02:36
     * @version 1.0
     * @return
     *
     */
    @RequestMapping(value = "/getWonderPlusUrl")
    @ResponseBody
    public Object getWonderPlusUrl() {
        return null;
    }

}
