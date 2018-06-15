package com.mrbt.lingmoney.service.inviting;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月20日
 */
public interface InvitingFriendsService {

	/**
	 * 我的邀请好友列表
	 * @param uId uId
	 * @param request request
	 * @return pageInfo
	 */ 
	JSONObject invitingFriendsList(String uId, HttpServletRequest request);

	/**
	 * 推荐人邀请等级
	 * @return pageInfo
	 */
	JSONObject invitingFriendsLevel();

	/**
	 * 我的邀请奖励
	 * @param token token
	 * @param request request
	 * @return PageInfo
	 */
	JSONObject invitingFriends(String uId, HttpServletRequest request);

}
