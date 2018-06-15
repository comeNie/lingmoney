package com.mrbt.lingmoney.mobile.controller.activityRewad;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.service.activityReward.ActivityRewardService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月10日
 */
@Controller
@RequestMapping(value = "/activityReward")
public class ActivityRewardController {
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private ActivityRewardService activityRewardService;

	/**
	 * 查询用户红包个数
	 * 
	 * @param token token
	 * @param request 请求
	 * @author suyb
	 * @date 2018-05-11 09:26:53
	 * @return JSONObject
	 */
	@RequestMapping("/findUserRedPacketCount")
	public @ResponseBody Object findUserRedPacketCount(@RequestParam(required = true) String token, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
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
	 * @param token token
	 * @param request 请求
	 * @author suyb
	 * @date 2018-05-11 09:50:15
	 * @return JSONObject
	 */
	@RequestMapping("/clickRedPacketDo")
	public @ResponseBody Object clickRedPacketDo(@RequestParam(required = true) String token, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
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
	 * @param token token
	 * @param request 请求
	 * @return JSONObject
	 */
	@RequestMapping("/notCheckedRedPacket")
	public @ResponseBody Object notCheckedRedPacket(@RequestParam(required = true) String token, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			if (StringUtils.isEmpty(uId)) {
				pageInfo.setCode(ResultInfo.REQUEST_AGAIN.getCode());
				pageInfo.setMsg("用户没有登录");
			} else {
				// 查询用户是否有未查看红包
				JSONObject object = activityRewardService.notCheckedRedPacket(uId);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setObj(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 未查看-->已查看
	 * @param token token
	 * @param request 请求
	 * @return JSONObject
	 */
	@RequestMapping("/checkedRedPacket")
	public @ResponseBody Object checkedRedPacket(@RequestParam(required = true) String token, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			if (StringUtils.isEmpty(uId)) {
				pageInfo.setCode(ResultInfo.REQUEST_AGAIN.getCode());
				pageInfo.setMsg("用户没有登录");
			} else {
				activityRewardService.checkedRedPacket(uId);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 是否符合弹框要求
	 * @param token token
	 * @param request 请求
	 * @return JSONObject
	 */
	@RequestMapping("/alertControl")
	public @ResponseBody Object alertControl(@RequestParam(required = true) String token, HttpServletRequest request) {
		PageInfo pageInfo = new PageInfo();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			if (StringUtils.isEmpty(uId)) {
				pageInfo.setCode(ResultInfo.REQUEST_AGAIN.getCode());
				pageInfo.setMsg("用户没有登录");
			} else {
				JSONObject object = activityRewardService.alertControl(uId);
				pageInfo.setCode(ResultInfo.SUCCESS.getCode());
				pageInfo.setObj(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
	
	/**
	 * 查询用户优惠券的个数
	 * 
	 * @param request 请求
	 * @return JSONObject
	 */
	@RequestMapping("/showCouponReminding")
	public @ResponseBody Object showCouponReminding(String token) {
		PageInfo pageInfo = new PageInfo();
		try {
			// 获取登录用户
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			if (StringUtils.isEmpty(uId)) {
				pageInfo.setCode(ResultInfo.LOGIN_TIMEOUT.getCode());
				pageInfo.setMsg(ResultInfo.LOGIN_TIMEOUT.getMsg());
			} else {
				pageInfo = activityRewardService.showCouponReminding(uId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageInfo;
	}
}
