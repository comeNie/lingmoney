package com.mrbt.lingmoney.mobile.controller.users;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.mobile.vo.users.UserAddressVo;
import com.mrbt.lingmoney.mobile.vo.users.UsersBaseInfoVo;
import com.mrbt.lingmoney.service.users.UsersAccountSetService;
import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.Common;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

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
	public Object queryUserAccountSetInfo(HttpServletRequest request, String token) {
		log.info("users-获取用户账户设置界面用户信息");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersService.queryUsersInfo(AppCons.TOKEN_VERIFY + token);
			// #start set vo
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				UsersBaseInfoVo v = new UsersBaseInfoVo();
				BeanUtils.copyProperties(v, pageInfo.getObj());
				pageInfo.setObj(v);
			}
			// #end
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("用户信息查询失败");
			e.printStackTrace();
			log.error("用户信息查询失败:" + e);
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
	public Object modifyAvatar(HttpServletRequest request, String token, String image) {
		log.info("users-修改头像");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyAvatar(request, image, AppCons.TOKEN_VERIFY + token);
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
	public Object modifyNickName(HttpServletRequest request, String nickName, String token) {
		log.info("users-修改昵称");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyNickName(nickName, AppCons.TOKEN_VERIFY + token);
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
	 * 发送验证邮件
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *              request
	 * @param token
	 *              token
	 * @param email
	 *              email
	 * @return pageInfo
	 */
	@RequestMapping(value = "/sendVailEmail")
	@ResponseBody
	public Object sendVailEmail(HttpServletRequest request, String email, String token) {
		log.info("users-发送验证邮件[" + email + "]");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.sendVailEmail(email, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("发送邮件失败");
			e.printStackTrace();
			log.error("发送邮件失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 设置邮箱
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *             request
	 * @param email
	 *             email
	 * @param vailCode
	 *             vailCode
	 * @param token
	 *             token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/setEmail")
	@ResponseBody
	public Object setEmail(HttpServletRequest request, String email, String vailCode, String token) {
		log.info("users-设置邮箱[" + email + "]");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.setEmail(email, vailCode, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("设置邮箱失败");
			e.printStackTrace();
			log.error("设置邮箱失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 查询用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *             request
	 * @param token
	 *             token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/queryAddress")
	@ResponseBody
	public Object queryAddress(HttpServletRequest request, String token) {
		log.info("users-查询用户地址");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.queryAddress(1, ResultNumber.TEN.getNumber(), AppCons.TOKEN_VERIFY + token);

			// #start set vo
			if (pageInfo.getCode() == ResultInfo.SUCCESS.getCode()) {
				List<?> addLi = pageInfo.getRows();
				pageInfo.setRows(Common.copyPropertyToList(addLi, UserAddressVo.class));
			}
			// #end

		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("查询用户地址失败");
			e.printStackTrace();
			log.error("查询用户地址失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 修改用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request
	 *            request
	 * @param id
	 *            地址id
	 * @param name
	 *            name
	 * @param telephone
	 *            telephone
	 * @param province
	 *            province
	 * @param city
	 *            city
	 * @param town
	 *            town
	 * @param address
	 *            address
	 * @param token
	 *            token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/modifyAddress")
	@ResponseBody
	public Object modifyAddress(HttpServletRequest request, Integer id, String name, String telephone, String province,
			String city, String town, String address, String token) {
		log.info("users-修改用户地址");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyAddress(id, name, telephone, province, city, town, address,
					AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("修改用户地址失败");
			e.printStackTrace();
			log.error("修改用户地址失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 添加用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request request
	 * @param name name
	 * @param telephone telephone
	 * @param province province
	 * @param city city
	 * @param town town
	 * @param address address
	 * @param isFirst
	 *            是否首次填写地址 0-否 1-是
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/addAddress")
	@ResponseBody
	public Object addAddress(HttpServletRequest request, String name, String telephone, String province, String city,
			String town, String address, Integer isFirst, String token) {
		log.info("users-添加用户地址");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.addAddress(name, telephone, province, city, town, address, isFirst,
					AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("添加用户地址失败");
			e.printStackTrace();
			log.error("添加用户地址失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 设置默认地址
	 * 
	 * @author YJQ 2017 年4月18日
	 * @param request request
	 * @param id id
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/setDefaultAddress")
	@ResponseBody
	public Object setDefaultAddress(HttpServletRequest request, Integer id, String token) {
		log.info("users-设置默认地址[" + id + "]");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.setDefaultAddress(id, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("设置用户地址失败");
			e.printStackTrace();
			log.error("设置用户地址失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 删除用户地址
	 * 
	 * @author YJQ 2017年4月17日
	 * @param request request
	 * @param id id
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/deleteAddress")
	@ResponseBody
	public Object deleteAddress(HttpServletRequest request, Integer id, String token) {
		log.info("users-删除用户地址[" + id + "]");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.deleteAddress(id, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("删除用户地址失败");
			e.printStackTrace();
			log.error("删除用户地址失败:" + e);
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
	 * @param token token
	 * @return pageInfo
	 */ 
	@RequestMapping(value = "/modifyPassword")
	@ResponseBody
	public Object modifyPassword(HttpServletRequest request, String oldPassword, String password, String token) {
		log.info("users-修改密码");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyPassword(oldPassword, password, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("修改密码失败");
			e.printStackTrace();
			log.error("修改密码失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 忘记密码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request request
	 * @param telephone  telephone
	 * @param password password
	 * @param verificationCode verificationCode
	 * @return pageInfo
	 */
	@RequestMapping(value = "/retrievePassword")
	@ResponseBody
	public Object retrievePassword(HttpServletRequest request, String telephone, String password,
			String verificationCode) {
		log.info("users-忘记密码");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.retrievePassword(telephone, password, verificationCode);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("密码修改失败");
			e.printStackTrace();
			log.error("密码修改失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 修改手机号
	 * 
	 * @author YJQ 2017年6月1日
	 * @param request request
	 * @param oldTelephone oldTelephone
	 * @param oldVerificationCode oldVerificationCode
	 * @param password password
	 * @param telephone telephone
	 * @param verificationCode verificationCode
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/modifyTelephone")
	@ResponseBody
	public Object modifyTelephone(HttpServletRequest request, String oldTelephone, String oldVerificationCode,
			String password, String telephone, String verificationCode, String token) {
		log.info("users-修改手机号[" + telephone + "]");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.modifyTelephone(oldTelephone, oldVerificationCode, password, telephone,
					verificationCode, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("手机号码修改失败");
			e.printStackTrace();
			log.error("手机号码修改失败:" + e);
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
	public Object recommendMe(String token) {
		log.info("users-查询推荐我的人");
		PageInfo pageInfo = null;
		try {
			pageInfo = usersAccountSetService.queryMyrecommender(AppCons.TOKEN_VERIFY + token);
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
	public Object myRecommender(String token) {
		log.info("users-查询推荐我的人");
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
		return pageInfo;
	}
}
