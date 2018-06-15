package com.mrbt.lingmoney.web.controller.common;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.mrbt.lingmoney.service.users.UsersService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.web.vo.users.UsersBaseInfoVo;

/**
 * @author syb
 * @date 2017年5月12日 下午1:48:22
 * @version 1.0
 * @description 公共方法类
 **/
@Component
public final class CommonMethodUtil {

	private CommonMethodUtil() {

	}
	/**
	 * 从session获取用户ID
	 * 
	 * @param request
	 *            请求
	 * @return 信息
	 */
	public static String getUidBySession(HttpServletRequest request) {
		String uid = (String) request.getSession().getAttribute("uid");
		return uid;
	}

	/**
	 * 查询用户基本信息放入model
	 * 
	 * @param model
	 *            数据模型
	 * @param request
	 *            请求
	 * @param usersService
	 *            业务逻辑接口
	 * @throws Exception
	 *             异常
	 * @throws IllegalAccessException
	 *             非法异常
	 * @throws InvocationTargetException
	 *             数据异常
	 */
	public static void modelUserBaseInfo(ModelMap model, HttpServletRequest request, UsersService usersService)
			throws Exception, IllegalAccessException, InvocationTargetException {
		PageInfo pageInfo = usersService.queryUsersInfo(AppCons.SESSION_USER + request.getSession().getId());
		if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
			UsersBaseInfoVo v = new UsersBaseInfoVo();
			BeanUtils.copyProperties(v, pageInfo.getObj());
			// 用户信息返回页面
			model.addAttribute(AppCons.SESSION_USER, v);
		} 
	}

	/**
	 * 
	 * @description 异常捕获统一处理
	 * @author syb
	 * @date 2017年8月24日 下午3:42:53
	 * @version 1.0
	 * @param pi
	 *            pageinfo对象
	 * @param e
	 *            异常对象
	 * @param log
	 *            日志对象
	 * @param errorMsg
	 *            错误信息
	 *
	 */
	public static void handelCatchedException(PageInfo pi, Exception e, Logger log, String errorMsg) {
		pi.setCode(ResultParame.ResultInfo.SERVER_ERROR.getCode());
		pi.setMsg(ResultParame.ResultInfo.SERVER_ERROR.getMsg());
		e.printStackTrace();
		log.error(errorMsg + e.toString());
	}

	/**
	 * 
	 * @description 根据请求获取用户reids中uid的key
	 * @author syb
	 * @date 2017年8月25日 上午10:01:55
	 * @version 1.0
	 * @param request
	 *            请求
	 * @return 信息
	 *
	 */
	public static String getRedisUidKeyByRequest(HttpServletRequest request) {
		return AppCons.SESSION_USER + request.getSession().getId();
	}
}
