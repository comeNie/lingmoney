package com.mrbt.lingmoney.admin.controller.inviting;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.admin.service.inviting.InvitingFriendsService;
import com.mrbt.lingmoney.mongo.MonthInvitingRewardMongo2;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月19日
 */
@Controller
@RequestMapping("/inviting")
public class InvitingFriendsController {
	@Autowired
	private InvitingFriendsService invitingFriendsService;

	/**
	 * 获取邀请好友列表
	 * 
	 * @return 邀请好友列表
	 */
	@RequestMapping("/invitingFriendsList")
	@ResponseBody
	public Object invitingFriendsList(HttpServletRequest request, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		Integer pageNumber = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		try {
			pageInfo = invitingFriendsService.invitingFriendsList(pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
	
	/**
	 * 推荐人的邀请好友详情
	 * @param id InvitingFriendsMongoId 
	 * @return 邀请好友详情列表
	 */
	@RequestMapping("/invitingFriendsInfo")
	@ResponseBody
	public Object invitingFriendsInfo(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = invitingFriendsService.invitingFriendsInfo(id);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 编辑奖励状态
	 * @param id id
	 * @param rewardContent 奖励对象（1：星巴克 2：哈根达斯）
	 * @param rewardCode 电子码
	 * @param rewardTime 奖励时间
	 * @return 信息返回
	 */
	@RequestMapping("/updateInvitingFriends")
	@ResponseBody
	public Object updateInvitingFriends(String id, BigDecimal money, String rewardTime) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = invitingFriendsService.updateInvitingFriends(id, money, rewardTime);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 人脉风云排行榜（每月）
	 * 
	 * @return人脉风云排行榜（每月）列表
	 */
	@RequestMapping("/monthInvitingRewardList")
	@ResponseBody
	public Object monthInvitingRewardList(HttpServletRequest request, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		Integer pageNumber = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		try {
			pageInfo = invitingFriendsService.monthInvitingRewardList(pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 编辑奖励信息
	 * @param id id
	 * @param rewardContent 奖励内容
	 * @param rewardCode 电子码
	 * @param rewardTime 奖励时间
	 * @return 信息返回
	 */
	@RequestMapping("/monthUpdateInvitingReward")
	@ResponseBody
	public Object monthUpdateInvitingReward(String id, String rewardContent, String rewardCode, String rewardTime) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = invitingFriendsService.monthUpdateInvitingReward(id, rewardContent, rewardCode, rewardTime);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 添加邀请好友等级
	 * @param MonthInvitingRewardMongo2 等级对象
	 * @return 信息返回
	 */
	@RequestMapping("/addInvitingFriendsLevel")
	@ResponseBody
	public Object addInvitingFriendsLevel(MonthInvitingRewardMongo2 vo) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = invitingFriendsService.addInvitingFriendsLevel(vo);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 查询添加的人脉风云排行榜
	 * 
	 * @return 添加人脉风云排行榜（每月）列表
	 */
	@RequestMapping("/findInvitingLevelList")
	@ResponseBody
	public Object findInvitingLevelList(HttpServletRequest request, HttpServletResponse response) {
		PageInfo pageInfo = new PageInfo();
		Integer pageNumber = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		try {
			pageInfo = invitingFriendsService.findInvitingLevelList(pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}

	/**
	 * 删除添加的人脉
	 * 
	 * @return 数据返回
	 */
	@RequestMapping("/deleteInvitingLevelData")
	@ResponseBody
	public Object deleteInvitingLevelData(String id) {
		PageInfo pageInfo = new PageInfo();
		try {
			pageInfo = invitingFriendsService.deleteInvitingLevelData(id);
		} catch (Exception e) {
			e.printStackTrace();
			pageInfo.setCode(ResultInfo.SERVER_ERROR.getCode());
			pageInfo.setMsg(ResultInfo.SERVER_ERROR.getMsg());
		}
		return pageInfo;
	}
}
