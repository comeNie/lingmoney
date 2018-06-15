package com.mrbt.lingmoney.web2.controller.activityRewad;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.service.activityReward.ActivityRewardService;
import com.mrbt.lingmoney.web2.controller.common.CommonMethodUtil;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月10日
 */
@Controller
@RequestMapping(value = "/activityReward")
public class ActivityRewardController {
	@Autowired
	private ActivityRewardService activityRewardService;

	/**
	 * 查询用户红包个数
	 * 
	 * @param request 请求
	 * @author suyb
	 * @date 2018-05-11 09:26:53
	 * @return JSONObject
	 */
	@RequestMapping("/findUserRedPacketCount")
	public @ResponseBody Object findUserRedPacketCount(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = CommonMethodUtil.getUidBySession(request);
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			} else {
				// 查询用户可开红包次数
				object = activityRewardService.findUserRedPacketCount(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 点击红包操作
	 * @param request 请求
	 * @author suyb
	 * @date 2018-05-11 09:50:15
	 * @return JSONObject
	 */
	@RequestMapping("/clickRedPacketDo")
	public @ResponseBody Object clickRedPacketDo(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = CommonMethodUtil.getUidBySession(request);
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			} else {
				// 点击红包操作及数据返回
				object = activityRewardService.clickRedPacketDo(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 查询用户是否有未查看红包
	 * 
	 * @param request
	 *            请求
	 * @return JSONObject
	 */
	@RequestMapping("/notCheckedRedPacket")
	public @ResponseBody Object notCheckedRedPacket(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = CommonMethodUtil.getUidBySession(request);
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			} else {
				// 查询用户是否有未查看红包
				object = activityRewardService.notCheckedRedPacket(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 未查看-->已查看
	 * 
	 * @param request
	 *            请求
	 * @return JSONObject
	 */
	@RequestMapping("/checkedRedPacket")
	public @ResponseBody Object checkedRedPacket(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = CommonMethodUtil.getUidBySession(request);
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			} else {
				activityRewardService.checkedRedPacket(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 是否符合弹框要求
	 * 
	 * @param request 请求
	 * @return JSONObject
	 */
	@RequestMapping("/alertControl")
	public @ResponseBody Object alertControl(HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = CommonMethodUtil.getUidBySession(request);
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			} else {
				object = activityRewardService.alertControl(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
