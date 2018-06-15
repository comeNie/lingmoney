package com.mrbt.lingmoney.mobile.controller.inviting;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.mobile.controller.BaseController;
import com.mrbt.lingmoney.service.inviting.InvitingFriendsService;
import com.mrbt.lingmoney.utils.AppCons;

import freemarker.cache.StrongCacheStorage;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月20日
 */
@Controller
@RequestMapping("/invitingFriends")
public class InvitingFriendsController extends BaseController {
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private InvitingFriendsService invitingFriendsService;

	/**
	 * @return string
	 */
	@RequestMapping("/invitingHtml")
	public String invitingHtml(String token, Model model) {
		model.addAttribute("token", token);
		return "/inviting";
	}

	/**
	 * @return string
	 */
	@RequestMapping("/myInviting")
	public String myInviting(String token, Model model) {
		model.addAttribute("token", token);
		return "/myInviting";
	}

	/**
	 * 我的邀请好友列表
	 * @param token token
	 * @param request request
	 * @return pageInfo
	 */ 
	@RequestMapping("/invitingFriendsList")
	public @ResponseBody Object invitingFriendsList(@RequestParam(required = true) String token, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			object = invitingFriendsService.invitingFriendsList(uId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 推荐人邀请等级
	 * @return pageInfo
	 */
	@RequestMapping("/invitingFriendsLevel")
	public @ResponseBody Object invitingFriendsLevel() {
		JSONObject object = new JSONObject();
		try {
			object = invitingFriendsService.invitingFriendsLevel();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return object;
	}
	
	/**
	 * 我的邀请奖励
	 * @param token token
	 * @param request request
	 * @return pageInfo
	 */
	@RequestMapping("/invitingRewards")
	public @ResponseBody Object invitingRewards(@RequestParam(required = true) String token,
			HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			String uId = redisGet.getRedisStringResult(AppCons.TOKEN_VERIFY + token).toString();
			if (StringUtils.isEmpty(uId)) {
				object.put("code", 1006);
				object.put("msg", "用户没有登录");
			}else {
				object = invitingFriendsService.invitingFriends(uId, request);
				if (object != null && object.size() > 0) {
					object.put("code", 200);
				}else {
					object.put("code", 1007);
					object.put("msg", "用户没有邀请好友");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
}
