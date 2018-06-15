package com.mrbt.lingmoney.web2.controller.user;

import java.util.List;

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

import com.mrbt.lingmoney.model.LingbaoAddress;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;
import com.mrbt.lingmoney.web2.vo.user.UserAddressVo;
import com.mrbt.lingmoney.web2.vo.user.UsersBaseInfoVo;

/**
 * 用户账户设置接口
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/users")
public class UserAccountSetController {
	Logger log = LogManager.getLogger(UserAccountSetController.class);

	@Autowired
	private UsersAccountSetService usersAccountSetService;
    @Autowired
    private UsersService usersService;

	/**
	 * 查询用户所有地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @return 返回信息
	 */
	@RequestMapping(value = "/queryAddress")
	@ResponseBody
	public Object queryAddress(HttpServletRequest request) {
		log.info("users-查询用户地址");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = usersAccountSetService.queryAddress(ResultParame.ResultNumber.ONE.getNumber(),
					ResultParame.ResultNumber.TEN.getNumber(), CommonMethodUtil.getRedisUidKeyByRequest(request));

			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				@SuppressWarnings("unchecked")
				List<LingbaoAddress> addLi = pageInfo.getRows();
				pageInfo.setRows(Common.copyPropertyToList(addLi, UserAddressVo.class));
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "查询用户地址失败");
		}

		return pageInfo;
	}

	/**
	 * 查询用户地址详情
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param id
	 *            地址id
	 * @return 返回信息
	 */
	@RequestMapping(value = "/queryAddressDetail")
	@ResponseBody
	public Object queryAddressDetail(HttpServletRequest request, Integer id) {
		log.info("users-查询用户地址");
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = usersAccountSetService.queryAddressDetail(id, CommonMethodUtil.getRedisUidKeyByRequest(request));

			if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
				UserAddressVo v = new UserAddressVo();
				BeanUtils.copyProperties(v, pageInfo.getObj());
				pageInfo.setObj(v);
			}

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "查询用户地址失败");
		}

		return pageInfo;
	}

	/**
	 * 修改用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param id
	 *            地址id
	 * @param name
	 *            姓名
	 * @param telephone
	 *            电话
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param town
	 *            区县
	 * @param address
	 *            地址
	 * @return 返回信息
	 */
	@RequestMapping(value = "/modifyAddress")
	@ResponseBody
	public Object modifyAddress(HttpServletRequest request, Integer id, String name, String telephone, String province,
			String city, String town, String address) {
		log.info("users-修改用户地址");

		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = usersAccountSetService.modifyAddress(id, name, telephone, province, city, town, address,
					CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "修改用户地址失败");
		}
		return pageInfo;
	}

	/**
	 * 添加用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param name
	 *            姓名
	 * @param telephone
	 *            电话
	 * @param province
	 *            省份
	 * @param city
	 *            城市
	 * @param town
	 *            区县
	 * @param address
	 *            地址
	 * @param isFirst
	 *            是否首次填写地址 0-否 1-是
	 * @return 返回信息
	 */
	@RequestMapping(value = "/addAddress")
	@ResponseBody
	public Object addAddress(HttpServletRequest request, String name, String telephone, String province, String city,
			String town, String address, Integer isFirst) {
		log.info("users-添加用户地址");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.addAddress(name, telephone, province, city, town, address, isFirst,
					CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "添加用户地址失败");
		}

		return pageInfo;
	}

	/**
	 * 设置默认地址
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request
	 *            请求
	 * @param id
	 *            地址id
	 * @return 返回信息
	 */
	@RequestMapping(value = "/setDefaultAddress")
	@ResponseBody
	public Object setDefaultAddress(HttpServletRequest request, Integer id) {
		log.info("users-设置默认地址[" + id + "]");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.setDefaultAddress(id, CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "设置默认地址失败");
		}

		return pageInfo;
	}

	/**
	 * 删除用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param id
	 *            地址id
	 * @return 返回信息
	 */
	@RequestMapping(value = "/deleteAddress")
	@ResponseBody
	public Object deleteAddress(HttpServletRequest request, Integer id) {
		log.info("users-删除用户地址[" + id + "]");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.deleteAddress(id, CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "删除用户地址失败");
		}

		return pageInfo;
	}

	// #end

	/**
	 * 忘记密码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request
	 *            请求
	 * @param telephone
	 *            电话
	 * @param password
	 *            密码
	 * @param verificationCode
	 *            验证码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/retrievePassword")
	@ResponseBody
	public Object retrievePassword(HttpServletRequest request, String telephone, String password,
			String verificationCode) {
		log.info("users-忘记密码");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.retrievePassword(telephone, password, verificationCode);

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "密码修改失败");
		}

		return pageInfo;
	}

	/**
	 * 修改手机号
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request
	 *            请求
	 * @param oldTelephone
	 *            旧电话号码
	 * @param oldVerificationCode
	 *            旧验证码
	 * @param password
	 *            密码
	 * @param telephone
	 *            电话
	 * @param verificationCode
	 *            验证码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/modifyTelephone")
	@ResponseBody
	public Object modifyTelephone(HttpServletRequest request, String oldTelephone, String oldVerificationCode,
			String password, String telephone, String verificationCode) {
		log.info("users-修改手机号[" + telephone + "]");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.modifyTelephone(oldTelephone, oldVerificationCode, password, telephone,
					verificationCode, CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "手机号码修改失败");
		}

		return pageInfo;
	}

	/**
	 * 发送验证邮件
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param email
	 *            邮箱
	 * @return 返回信息
	 */
	@RequestMapping(value = "/sendVailEmail")
	@ResponseBody
	public Object sendVailEmail(HttpServletRequest request, String email) {
		log.info("users-发送验证邮件[" + email + "]");

		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.sendVailEmail(email, CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "发送邮件失败");
		}

		return pageInfo;
	}
	
	/**
	 * 设置邮箱
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            请求
	 * @param email
	 *            邮箱
	 * @param vailCode
	 *            验证码
	 * @return 返回信息
	 */
	@RequestMapping(value = "/setEmail")
	@ResponseBody
	public Object setEmail(HttpServletRequest request, String email, String vailCode) {
		log.info("users-设置邮箱[" + email + "]");
		PageInfo pageInfo = new PageInfo();

		try {
			pageInfo = usersAccountSetService.setEmail(email, vailCode,
					CommonMethodUtil.getRedisUidKeyByRequest(request));

		} catch (Exception e) {
			CommonMethodUtil.handelCatchedException(pageInfo, e, log, "设置邮箱失败");
		}

		return pageInfo;
	}

    /**
     * 查询推荐我的人
     * 
     * @param token token
     * @return pageInfo
     */
    @RequestMapping(value = "/recommendMe")
    @ResponseBody
    public Object recommendMe(HttpServletRequest request) {
        log.info("users-查询推荐我的人");
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.queryMyrecommender(CommonMethodUtil.getRedisUidKeyByRequest(request));
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("查询推荐我的人失败");
            e.printStackTrace();
            log.error("查询推荐我的人失败:" + e);
        }
        return pageInfo;
    }

    /**
     * 查询我推荐的人
     * 
     * @param token token
     * @return pageInfo
     */
    @RequestMapping(value = "/myRecommenders")
    @ResponseBody
    public Object myRecommender(HttpServletRequest request) {
        log.info("users-查询推荐我的人");
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.queryRecomUsersByUid(CommonMethodUtil.getRedisUidKeyByRequest(request));
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("查询我推荐的人失败");
            e.printStackTrace();
            log.error("查询我推荐的人失败:" + e);
        }
        return pageInfo;
    }

    /**
     * 修改头像
     * 
     * @author YJQ 2017年4月17日
     * @param request
     *             request
     * @param token
     *             token
     * @param image
     *             image
     * @return pageInfo
     */
    @RequestMapping(value = "/modifyAvatar")
    @ResponseBody
    public Object modifyAvatar(HttpServletRequest request, String image) {
        log.info("users-修改头像");
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.modifyAvatar(request, image,
                    CommonMethodUtil.getRedisUidKeyByRequest(request));
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("修改头像失败");
            e.printStackTrace();
            log.error("修改头像失败:" + e);
        }
        return pageInfo;
    }

    /**
     * 修改昵称
     * 
     * @author YJQ 2017年4月17日
     * @param request
     *             request
     * @param nickName
     *             nickName
     * @param token
     *             token
     * @return pageInfo
     */
    @RequestMapping(value = "/modifyNickName")
    @ResponseBody
    public Object modifyNickName(HttpServletRequest request, String nickName) {
        log.info("users-修改昵称");
        PageInfo pageInfo = null;
        try {
            pageInfo = usersAccountSetService.modifyNickName(nickName,
                    CommonMethodUtil.getRedisUidKeyByRequest(request));
        } catch (Exception e) {
            pageInfo = new PageInfo();
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("修改昵称失败");
            e.printStackTrace();
            log.error("修改昵称失败:" + e);
        }
        return pageInfo;
    }

    /**
     * 获取用户账户设置界面用户信息
     * 
     * @author YJQ 2017年4月17日
     * @param request
     *              request
     * @param token
     *              token
     * @return pageInfo
     */
    @RequestMapping(value = "/queryUserAccountSetInfo", method = RequestMethod.POST)
    @ResponseBody
    public Object queryUserAccountSetInfo(HttpServletRequest request) {
        log.info("users-获取用户账户设置界面用户信息");
        PageInfo pageInfo = new PageInfo();
        try {
        pageInfo = usersService.queryUsersInfo(request.getSession().getId());
        if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
            UsersBaseInfoVo v = new UsersBaseInfoVo();
            BeanUtils.copyProperties(v, pageInfo.getObj());
            pageInfo.setObj(v);
        }
        } catch (Exception e) {
            pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
            pageInfo.setMsg("用户信息查询失败");
            e.printStackTrace();
            log.error("用户信息查询失败:" + e);
        }
        return pageInfo;
    }
}
