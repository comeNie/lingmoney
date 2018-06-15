package com.mrbt.lingmoney.mobile.controller.users;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.service.users.UserMobileSetService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 用户移动端属性接口
 * 
 * @author YJQ
 *
 */
@Controller
@RequestMapping("/users")
public class UserMobileSetController {

	Logger log = LogManager.getLogger(UserMobileSetController.class);

	@Autowired
	private UserMobileSetService userMobileSetService;

	/**
	 * 查询用户移动端属性
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request request
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/queryUserMobileProp", method = RequestMethod.POST)
	@ResponseBody
	public Object queryUserMobileProp(HttpServletRequest request, String token) {
		log.info("users-查询用户移动端属性 ");
		PageInfo pageInfo = null;
		try {
			pageInfo = userMobileSetService.queryUserMobileProp(AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("查询用户移动端属性失败");
			e.printStackTrace();
			log.error("查询用户移动端属性失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 修改手势密码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request request
	 * @param gesturePwd
	 *            0-表删除 其他-正常手势密码
	 * @param token token
	 * @return pageInfo
	 */
	@RequestMapping(value = "/modifyGesturePwd", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyGesturePwd(HttpServletRequest request, String gesturePwd, String token) {
		log.info("users-修改手势密码 ");
		PageInfo pageInfo = null;
		try {
			pageInfo = userMobileSetService.modifyGesturePwd(gesturePwd, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg("修改手势密码失败");
			e.printStackTrace();
			log.error("修改手势密码失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 开启/关闭手势密码
	 * 
	 * @author YJQ 2017年4月18日
	 * @param request request
	 * @param token token
	 * @param status
	 *            0-关闭 1-开启
	 * @return pageInfo
	 */
	@RequestMapping(value = "/modifyGestureStatus", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyGestureStatus(HttpServletRequest request, Integer status, String token) {
		log.info("users-" + (status.equals(0) ? "关闭" : "开启") + "手势密码 ");
		PageInfo pageInfo = null;
		try {
			pageInfo = userMobileSetService.modifyGestureStatus(status, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg((status.equals(0) ? "关闭" : "开启") + "手势密码失败");
			e.printStackTrace();
			log.error((status.equals(0) ? "关闭" : "开启") + "手势密码失败:" + e);
		}
		return pageInfo;
	}

	/**
	 * 开启/关闭消息推送
	 * 
	 * @author YJQ 2017年4月18日
	 * @param token token
	 * @param request request
	 * @param status
	 *            0-关闭 1-开启
	 * @return pageInfo
	 */
	@RequestMapping(value = "/modifyPushStatus", method = RequestMethod.POST)
	@ResponseBody
	public Object modifyPushStatus(HttpServletRequest request, Integer status, String token) {
		log.info("users-" + (status.equals(0) ? "关闭" : "开启") + "消息推送");
		PageInfo pageInfo = null;
		try {
			pageInfo = userMobileSetService.modifyPushStatus(status, AppCons.TOKEN_VERIFY + token);
		} catch (Exception e) {
			pageInfo = new PageInfo();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg((status.equals(0) ? "关闭" : "开启") + "推送失败:");
			e.printStackTrace();
			log.error((status.equals(0) ? "关闭" : "开启") + "推送失败:" + e);
		}
		return pageInfo;
	}

}
