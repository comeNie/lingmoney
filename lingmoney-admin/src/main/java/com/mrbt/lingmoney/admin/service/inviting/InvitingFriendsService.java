package com.mrbt.lingmoney.admin.service.inviting;

import java.math.BigDecimal;

import com.mrbt.lingmoney.mongo.MonthInvitingRewardMongo2;
import com.mrbt.lingmoney.utils.PageInfo;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月15日
 */
public interface InvitingFriendsService {
	/**
	 * 邀请好友活动
	 */
	void invitingFriendsTask();

	/**
	 * 获取邀请好友列表
	 * 
	 * @return 邀请好友列表
	 */
	PageInfo invitingFriendsList(Integer pageNumber, Integer pageSize);

	/**
	 * 推荐人的邀请好友详情
	 * @param id InvitingFriendsMongoId 
	 * @return 邀请好友详情列表
	 */
	PageInfo invitingFriendsInfo(String id);

	/**
	 * 编辑奖励状态
	 * @param id id
	 * @param rewardContent 奖励对象（1：星巴克 2：哈根达斯）
	 * @param rewardCode 电子码
	 * @param rewardTime 奖励时间
	 * @return 信息返回
	 */
	PageInfo updateInvitingFriends(String id, BigDecimal money, String rewardTime);

	/**
	 * 人脉风云排行榜（每月）
	 * @param pageNumber 当前页数
	 * @param pageSize 每页显示行数
	 * @return 人脉风云排行榜（每月）列表
	 */
	PageInfo monthInvitingRewardList(Integer pageNumber, Integer pageSize);

	/**
	 * 编辑奖励信息
	 * @param id id
	 * @param rewardContent 奖励内容
	 * @param rewardCode 电子码
	 * @param rewardTime 奖励时间
	 * @return 信息返回
	 */
	PageInfo monthUpdateInvitingReward(String id, String rewardContent, String rewardCode, String rewardTime);

	/**
	 * 添加邀请好友等级
	 * @param MonthInvitingRewardMongo2 等级对象
	 * @return 信息返回
	 */
	PageInfo addInvitingFriendsLevel(MonthInvitingRewardMongo2 vo);

	/**
	 * 查询添加的人脉风云排行榜）
	 * @param pageNumber 当前页数
	 * @param pageSize 每页显示行数
	 * @return 添加的人脉风云排行榜列表
	 */
	PageInfo findInvitingLevelList(Integer pageNumber, Integer pageSize);

	/**
	 * 删除添加的人脉
	 * 
	 * @return 数据返回
	 */
	PageInfo deleteInvitingLevelData(String id);

}
