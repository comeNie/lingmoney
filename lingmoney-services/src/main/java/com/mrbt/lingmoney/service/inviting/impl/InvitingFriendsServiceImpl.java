package com.mrbt.lingmoney.service.inviting.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.mongo.InvitingFriendInfoMongo;
import com.mrbt.lingmoney.mongo.InvitingFriendsMongo;
import com.mrbt.lingmoney.mongo.MonthInvitingRewardMongo2;
import com.mrbt.lingmoney.service.inviting.InvitingFriendsService;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * 邀请好友实现类
 * 
 * @author suyibo
 * @date 创建时间：2018年3月20日
 */
@Service
public class InvitingFriendsServiceImpl implements InvitingFriendsService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UsersMapper usersMapper;

	/**
	 * 我的邀请好友列表
	 * @param uId uId
	 * @param request request
	 * @return pageInfo
	 */
	@Override
	public JSONObject invitingFriendsList(String uId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 查找推荐人对象
		InvitingFriendsMongo invitingFriends = mongoTemplate.findOne(new Query(Criteria.where("userId").is(uId)), InvitingFriendsMongo.class);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (null != invitingFriends && invitingFriends.getCount() > 0) {
			// 查找推荐人的邀请客户对象
			List<InvitingFriendInfoMongo> invitingFriendInfoList = mongoTemplate.find(new Query(Criteria.where("ifId").is(invitingFriends.getId())), InvitingFriendInfoMongo.class);
			if (!invitingFriendInfoList.isEmpty()) {
				BigDecimal totalMoney = BigDecimal.ZERO;
				// 循环遍历，封装数据
				for (InvitingFriendInfoMongo invitingFriendInfo : invitingFriendInfoList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("count", invitingFriends.getCount()); // 邀请人数
					// 获取邀请人电话
					Users users = usersMapper.selectByPrimaryKey(invitingFriendInfo.getuId());
					map.put("telephone", StringOpertion.hideTelephone(users.getTelephone())); // 手机号
					map.put("rewardMoney", invitingFriendInfo.getRewardMoney()); // 首投奖励
					map.put("cardStatus", invitingFriendInfo.getCardStatus()); // 绑卡状态（0：未绑卡 1：已绑卡）
					map.put("regDate", invitingFriendInfo.getRegDate()); // 注册时间
					if (invitingFriendInfo.getRewardMoney().compareTo(BigDecimal.ZERO) > 0) {
						totalMoney = totalMoney.add(invitingFriendInfo.getRewardMoney());
					}
					listMap.add(map);
				}
			}
		}
		jsonObject.put("listMap", listMap);
		return jsonObject;
	}

	/**
	 * 推荐人邀请等级
	 * @param token token
	 * @param request request
	 * @return pageInfo
	 */
	@Override
	public JSONObject invitingFriendsLevel() {
		JSONObject jsonObject = new JSONObject();
		List<MonthInvitingRewardMongo2> monthInvitingLevelList = mongoTemplate
				.find(new Query().with(new Sort(new Order(Direction.ASC, "level"))), MonthInvitingRewardMongo2.class);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (!monthInvitingLevelList.isEmpty()) {
			for (MonthInvitingRewardMongo2 monthInvitingLevel : monthInvitingLevelList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("level", monthInvitingLevel.getLevel()); // 等级
				// Users users = usersMapper.selectByPrimaryKey(monthInvitingLevel.getUserId());
				map.put("telephone", StringOpertion.hideTelephone(monthInvitingLevel.getTelephone())); // 手机号
				map.put("count", monthInvitingLevel.getCount()); // 邀请人数
				map.put("rewardContent", monthInvitingLevel.getRewardContent()); // 奖励内容
				map.put("rewardDate", monthInvitingLevel.getRewardDate());
				map.put("status", monthInvitingLevel.getStatus());
				listMap.add(map);
			}
		}
		jsonObject.put("listMap", listMap);
		return jsonObject;
	}

	/**
	 * 我的邀请奖励
	 * @param token token
	 * @param request request
	 * @return pageInfo
	 */
	@Override
	public JSONObject invitingFriends(String uId, HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 查找推荐人对象
		InvitingFriendsMongo invitingFriends = mongoTemplate.findOne(new Query(Criteria.where("userId").is(uId)), InvitingFriendsMongo.class);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (null != invitingFriends) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status1", invitingFriends.getStatus1()); // 0.5%加息卷(0:未获得 1:未赠送 2:已赠送)
			map.put("status2", invitingFriends.getStatus2()); // 1%加息卷(0:未获得 1:未赠送 2:已赠送)
			map.put("status3", invitingFriends.getStatus3()); // 爱奇艺视频会员卡（半年卡）(0:未获得 1:未赠送 2:已赠送)
			listMap.add(map);
			jsonObject.put("listMap", listMap);
		}
		return jsonObject;
	}

}
