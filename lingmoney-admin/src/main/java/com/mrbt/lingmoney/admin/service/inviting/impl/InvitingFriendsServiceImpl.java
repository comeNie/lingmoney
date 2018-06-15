package com.mrbt.lingmoney.admin.service.inviting.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.controller.redPacket.RedPacketController;
import com.mrbt.lingmoney.admin.mongo.InvitingFriendInfoMongo;
import com.mrbt.lingmoney.admin.mongo.InvitingFriendsMongo;
import com.mrbt.lingmoney.admin.service.info.AppMsgPushService;
import com.mrbt.lingmoney.admin.service.inviting.InvitingFriendsService;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ReferralAllMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AppPushMsg;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import com.mrbt.lingmoney.model.ReferralAll;
import com.mrbt.lingmoney.model.ReferralAllExample;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.mongo.MonthInvitingRewardMongo;
import com.mrbt.lingmoney.mongo.MonthInvitingRewardMongo2;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年3月15日
 */
@Service
public class InvitingFriendsServiceImpl implements InvitingFriendsService {
	private static final Logger LOGGER = LogManager.getLogger(InvitingFriendsServiceImpl.class);

	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String START_DATE = "2018-04-16 00:00:00";
	private String END_DATE = "2018-06-30 23:59:59";

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private RedPacketController redPacketController;
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
    @Autowired
    private AppMsgPushService appMsgPushService;
	@Autowired
	private ReferralAllMapper referralAllMapper;

	@Override
	public void invitingFriendsTask() {
		/**
		 * 一、推荐好友注册得好礼
		 */
		// 获取活动时间内注册的所有客户
		UsersPropertiesExample example = new UsersPropertiesExample();
		try {
			List<Integer> bankList = new ArrayList<Integer>();
			bankList.add(2);
			bankList.add(3);
			example.createCriteria().andRegDateBetween(fmt.parse(START_DATE), fmt.parse(END_DATE)).andPlatformTypeEqualTo(0).andBankIn(bankList);
		} catch (ParseException e) {
			LOGGER.info("时间格式化异常");
			e.printStackTrace();
		}
		List<UsersProperties> usersPropertiesList = usersPropertiesMapper.selectByExample(example);

		if (!usersPropertiesList.isEmpty()) {
			for (UsersProperties usersProperties : usersPropertiesList) {
				if (StringUtils.isEmpty(usersProperties.getReferralId())) {
					continue;
				}

				// 推荐人是公司员工则不参与活动
				ReferralAllExample raExample = new ReferralAllExample();
				raExample.createCriteria().andUIdEqualTo(usersProperties.getuId()).andDepartmentEqualTo("财富管理部");
				List<ReferralAll> referralAllList = referralAllMapper.selectByExample(raExample);
				if (!referralAllList.isEmpty()) {
					continue;
				}

				// 查找注册用户的推荐人
				UsersPropertiesExample recommendExample = new UsersPropertiesExample();
				recommendExample.createCriteria().andUIdEqualTo(usersProperties.getReferralId());
				List<UsersProperties> recommendUserList = usersPropertiesMapper.selectByExample(recommendExample);
				if (recommendUserList.isEmpty()) {
					continue;
				}
				UsersProperties recommendUser = recommendUserList.get(0);

				// mongo中查询此推荐人是否存在
				InvitingFriendsMongo findRecommendUser = mongoTemplate.findOne(
						new Query(Criteria.where("userId").is(recommendUser.getuId())), InvitingFriendsMongo.class);
				if (null == findRecommendUser) {
					InvitingFriendsMongo invitingFriends = new InvitingFriendsMongo();
					invitingFriends.setUserId(recommendUser.getuId());
					invitingFriends.setUserName(recommendUser.getName());
					invitingFriends.setCount(1);
					String strUserId = invitingFriends.getInvitingFriends();
					if (StringUtils.isNotEmpty(strUserId)) {
						StringBuilder stringBuilder = new StringBuilder(strUserId);
						stringBuilder.append(usersProperties.getuId());
						invitingFriends.setInvitingFriends(stringBuilder.toString());
					} else {
						strUserId = usersProperties.getuId();
						invitingFriends.setInvitingFriends(strUserId);
					}
					invitingFriends.setFirstCount(0);
					invitingFriends.setStatus1(0);
					invitingFriends.setStatus2(0);
					invitingFriends.setStatus3(0);
					invitingFriends.setStatus4(0);
					invitingFriends.setStatus5(0);
					invitingFriends.setStatus6(0);
					mongoTemplate.insert(invitingFriends);
				} else {
					Update update = new Update();
					Integer count = findRecommendUser.getCount();
					String strUserIds = findRecommendUser.getInvitingFriends();
					if (StringUtils.isNotEmpty(strUserIds)) {
						// 包含则表示此客户已经邀请，不能重复计入
						if (strUserIds.indexOf(usersProperties.getuId()) != -1) {
							continue;
						}
						StringBuilder stringBuilder = new StringBuilder(strUserIds);
						stringBuilder.append("," + usersProperties.getuId());
						update.set("invitingFriends", stringBuilder.toString());
					} else {
						strUserIds = usersProperties.getuId();
						update.set("invitingFriends", strUserIds);
					}
					if (count == 2) {
						// 一张30元投资红包（有效期7天，限三月期以上产品满5000元使用，产品成立后，返现至账户余额，不与其他活动、加息券同时享受，不支持债权转让）
						String rpId = "ccae95319d8b49ab9aab1a87402cfebc";
						String uId = recommendUser.getuId();
						rewardRedPacket(uId, rpId);

						update.set("status1", 2);
						update.set("rewardTime1", fmt.format(new Date()));
					} else if (count == 4) {
						// 奖励10元现金红包
						PageInfo pageInfo = redPacketController.activityRedPacketReward(recommendUser.getuId(), new BigDecimal(10));
						if (pageInfo.getCode() == 200) {
							update.set("status2", 2);
							update.set("rewardTime2", fmt.format(new Date()));
						} else if (pageInfo.getCode() == 1003) {
							update.set("status2", 3);
							update.set("rewardTime2", fmt.format(new Date()));
						} else {
							update.set("status2", 4);
							update.set("rewardTime2", fmt.format(new Date()));
						}
						
					} else if (count == 12) {
						// 奖励50元现金红包
						PageInfo pageInfo = redPacketController.activityRedPacketReward(recommendUser.getuId(), new BigDecimal(10));
						if (pageInfo.getCode() == 200) {
							update.set("status3", 2);
							update.set("rewardTime3", fmt.format(new Date()));
						} else if (pageInfo.getCode() == 1003) {
							update.set("status3", 3);
							update.set("rewardTime3", fmt.format(new Date()));
						} else {
							update.set("status3", 4);
							update.set("rewardTime3", fmt.format(new Date()));
						}
					}
					update.set("count", count + 1);
					mongoTemplate.upsert(new Query(Criteria.where("id").is(findRecommendUser.getId())), update, InvitingFriendsMongo.class);
				}
			}
		}

		/**
		 * 人脉变现（3个月）
		 */
		// 获取mongo中所有的推荐人
		List<InvitingFriendsMongo> recommendUserList = mongoTemplate.find(new Query(), InvitingFriendsMongo.class);
		if (!recommendUserList.isEmpty()) {
			for (InvitingFriendsMongo invitingFriend : recommendUserList) {
				// 邀请好友id集合
				String strUserIds = invitingFriend.getInvitingFriends();
				// 查询每一位邀请人的首投信息
				if (StringUtils.isEmpty(strUserIds)) {
					continue;
				}
				String[] invitingFriendIdList = strUserIds.split(",");
				for (String uId : invitingFriendIdList) {
					// mongo中查询邀请人是否存在
					InvitingFriendInfoMongo friend = mongoTemplate.findOne(new Query(Criteria.where("uId").is(uId)),InvitingFriendInfoMongo.class);
					
					// 获取邀请人首投信息
					Trading firstTrading = tradingMapper.findUserFirstTradingInfo(uId);

					if (null == friend) {

						InvitingFriendInfoMongo invitingFriendInfo = new InvitingFriendInfoMongo();
						invitingFriendInfo.setIfId(invitingFriend.getId());
						invitingFriendInfo.setuId(uId);
						// 查询用户的邀请好友
						UsersPropertiesExample invitExample = new UsersPropertiesExample();
						invitExample.createCriteria().andUIdEqualTo(uId);
						List<UsersProperties> invitingFriendList = usersPropertiesMapper.selectByExample(invitExample);
						invitingFriendInfo.setInvitingName(invitingFriendList.get(0).getName());
						invitingFriendInfo.setRegDate(sdf.format(invitingFriendList.get(0).getRegDate()));
						if (invitingFriendList.get(0).getBank() == 2 || invitingFriendList.get(0).getBank() == 3) {
							invitingFriendInfo.setCardStatus(1); // 已绑卡
						} else {
							invitingFriendInfo.setCardStatus(0); // 未绑卡
						}

						if (null == firstTrading) {
							invitingFriendInfo.setFirstMoney(BigDecimal.ZERO);
							invitingFriendInfo.setRewardMoney(BigDecimal.ZERO);
						} else {
							invitingFriendInfo.setFirstMoney(firstTrading.getFinancialMoney());
							BigDecimal money = rewardMoney(firstTrading, invitingFriend.getId());
							invitingFriendInfo.setRewardMoney(money);
							// 更新invitingFriends中首投人数
							mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend.getId())), new Update().set("firstCount", invitingFriend.getFirstCount() + 1), InvitingFriendsMongo.class);
							invitingFriendInfo.setRewardDate(sdf.format(new Date()));
						}
						mongoTemplate.insert(invitingFriendInfo);
					} else {
						// mongo中查询邀请人首投獎勵是否存在，存在则不添加
						if (friend.getRewardMoney().compareTo(BigDecimal.ZERO) > 0) {
							continue;
						}
						if (null == firstTrading) {
							continue;
						}
						BigDecimal money = rewardMoney(firstTrading, invitingFriend.getId());
						mongoTemplate.upsert(new Query(Criteria.where("id").is(friend.getId())),new Update().set("firstMoney", firstTrading.getFinancialMoney()).set("rewardMoney", money).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendInfoMongo.class);
						// 更新invitingFriends中首投人数
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend.getId())), new Update().set("firstCount", invitingFriend.getFirstCount() + 1), InvitingFriendsMongo.class);
					}
				}
			}
		}

		/**
		 * 人脉风云排行榜（每月）
		 */
		// 获取当前月的第一天
		Calendar cale = Calendar.getInstance(); // 获取当前日期
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号,当前日期既为本月第一天
		String nowDate = sdf.format(new Date());
		String firstDate = sdf.format(cale.getTime());
		if (nowDate.equals(firstDate)) {
			if (!recommendUserList.isEmpty()) {
				for (InvitingFriendsMongo usersProperties : recommendUserList) {
					// 获取前一个月第一天
					cale.add(Calendar.MONTH, -1);
					cale.set(Calendar.DAY_OF_MONTH, 1); // 设置为1号,当前日期既为本月第一天
					String startDate = sdf.format(cale.getTime()); // 查询开始日期

					cale.set(Calendar.DAY_OF_MONTH, 0);
					String lastDayOfMonth = sdf.format(cale.getTime()); // 查询结束时间

					// 每月只查询一次，存在则不重复查询
					MonthInvitingRewardMongo recommendUser = mongoTemplate.findOne(new Query(Criteria.where("userId").is(usersProperties.getUserId())),MonthInvitingRewardMongo.class);
					if (null != recommendUser) {
						String time1 = recommendUser.getMonth().substring(0, 4) + "-" + recommendUser.getMonth().substring(5, 7);
						String time2 = sdf.format(lastDayOfMonth).substring(0, 4) + "-" + sdf.format(lastDayOfMonth).substring(5, 7);
						if (time1.equals(time2)) {
							continue;
						}
					}
					
					// 初始化mongo数据
					MonthInvitingRewardMongo monthInviting = new MonthInvitingRewardMongo();
					monthInviting.setUserId(usersProperties.getUserId());
					monthInviting.setUserName(usersProperties.getUserName());
					monthInviting.setStatus(0); // 未奖励

					// 查询邀请人前一个月邀请的人数
					UsersPropertiesExample upExample = new UsersPropertiesExample();
					try {
						upExample.createCriteria().andReferralIdEqualTo(usersProperties.getUserId()).andRegDateBetween(
							fmt.parse(startDate + " 00:00:00"), fmt.parse(sdf.format(lastDayOfMonth) + " 23:59:59"));
					} catch (ParseException e) {
						LOGGER.info("人脉风云排行榜（每月）时间格式化异常");
						e.printStackTrace();
					}
					List<UsersProperties> list = usersPropertiesMapper.selectByExample(upExample);
					if (list.isEmpty()) {
						continue;
					}
					monthInviting.setCount(list.size());
					monthInviting.setMonth(sdf.format(lastDayOfMonth).substring(0, 4) + "年" + sdf.format(lastDayOfMonth).substring(5, 7) + "月");
					monthInviting.setLevel(0);
					monthInviting.setRewardContent("");
					mongoTemplate.insert(monthInviting);
				}
			}

			// 更新用户邀请等级
			List<MonthInvitingRewardMongo> dataList = mongoTemplate.find(new Query(Criteria.where("count").gte(10)),
					MonthInvitingRewardMongo.class);
			if (!dataList.isEmpty()) {
				// 邀请人数对象
				InvitingFriend[] invitingFriend = new InvitingFriend[dataList.size()];
				for (int i = 0; i < dataList.size(); i++) {
					invitingFriend[i] = new InvitingFriend(dataList.get(i).getCount(), dataList.get(i).getId());
				}
				
				if (null != invitingFriend) {
					InvitingFriend temp; // 排序使用的临时对象
					// 按count排序
					for (int i = 0; i < invitingFriend.length; i++) {
						for (int j = i + 1; j < invitingFriend.length; j++) {
							if (invitingFriend[j].count > invitingFriend[i].count) {
								temp = invitingFriend[i];
								invitingFriend[i] = invitingFriend[j];
								invitingFriend[j] = temp;
							}
						}
					}
					int level = 1; // 名次

					for (int i = 0; i < invitingFriend.length - 1; i++) {
						int n = checkContinue(invitingFriend, invitingFriend[i].count);
						if (n == 1) {
							invitingFriend[i].level = level++;
						} else {
							// 总分相同，名次相同
							for (int j = 0; j < n; j++) {
								invitingFriend[i + j].level = level;
							}
							i = i + n - 1; // 连续n个相同的总分，排名一样
						}
					}
					invitingFriend[invitingFriend.length - 1].level = level;
					// for (int i = 0; i < invitingFriend.length; i++) {
					// System.out.println(invitingFriend[i]);
					// }
				}

				// 更新月排行等级数据对象
				for (int i = 0; i < invitingFriend.length; i++) {
					if (invitingFriend[i].level == 1) {
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend[i].id)), new Update().set("level", invitingFriend[i].level).set("rewardContent", "现金红包1666元"),MonthInvitingRewardMongo.class);
					} else if (invitingFriend[i].level == 2) {
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend[i].id)), new Update().set("level", invitingFriend[i].level).set("rewardContent", "现金红包888元"), MonthInvitingRewardMongo.class);
					} else if (invitingFriend[i].level == 3) {
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend[i].id)), new Update().set("level", invitingFriend[i].level).set("rewardContent", "现金红包666元"), MonthInvitingRewardMongo.class);
					} else if (invitingFriend[i].level == 4 || invitingFriend[i].level == 5 || invitingFriend[i].level == 6) {
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend[i].id)), new Update().set("level", invitingFriend[i].level).set("rewardContent", "爱奇艺视频会员卡（年卡）"), MonthInvitingRewardMongo.class);
					} else if (invitingFriend[i].level == 7 || invitingFriend[i].level == 8 || invitingFriend[i].level == 9 || invitingFriend[i].level == 10) {
						mongoTemplate.upsert(new Query(Criteria.where("id").is(invitingFriend[i].id)), new Update().set("level", invitingFriend[i].level).set("rewardContent", "30元星巴克电子券"), MonthInvitingRewardMongo.class);
					}
				}
			}
		}
	}

	/**
	 * 红包奖励
	 * @param uId uId
	 * @param rpId rpId
	 */
	private void rewardRedPacket(String uId, String rpId) {
		HxRedPacketExample hxRedPacketExample = new HxRedPacketExample();
		hxRedPacketExample.createCriteria().andIdEqualTo(rpId);
		List<HxRedPacket> hxRedPacketList = hxRedPacketMapper.selectByExample(hxRedPacketExample);
		if (!hxRedPacketList.isEmpty()) {
			HxRedPacket hxRedPacket = hxRedPacketList.get(0);
			UsersRedPacket usersRedPacket = new UsersRedPacket();
			usersRedPacket.setuId(uId);
			usersRedPacket.setRpId(hxRedPacket.getId());
			usersRedPacket.setStatus(0);
			usersRedPacket.setCreateTime(new Date());
			usersRedPacket.setIsCheck(0);
			if (hxRedPacket.getDelayed() != null) {
				usersRedPacket.setValidityTime(DateUtils.addDay2(new Date(), hxRedPacket.getDelayed() + 1));
			} else {
				usersRedPacket.setValidityTime(hxRedPacket.getValidityTime());
			}
			usersRedPacketMapper.insert(usersRedPacket);

            // 推送消息
            /*1、   红包/加息券发放，消息提示内容如下：
            1）  【变量1】个红包已经送至您的账户，有效期【变量】天，超值福利不容错过。
            2）  【变量1】张加息券已经送至您的账户，有效期【变量】天，超值福利不容错过。*/
            try {
                UsersProperties up = usersPropertiesMapper.selectByuId(uId);
                if (up != null && up.getDeviceType() != null && up.getYoumengToken() != null) {
                    AppPushMsg msg = new AppPushMsg();
                    msg.setMsgTitle("温馨提示");
                    if (hxRedPacket.getHrpType() == 1) {
                        msg.setMsgContent("一张加息券已经送至您的账户，有效期"
                                + DateUtils.dateDiff(new Date(), usersRedPacket.getValidityTime()) + "天，超值福利不容错过。");
                    } else if (hxRedPacket.getHrpType() == 2) {
                        msg.setMsgContent("一个红包已经送至您的账户，有效期"
                                + DateUtils.dateDiff(new Date(), usersRedPacket.getValidityTime()) + "天，超值福利不容错过。");
                    }
                    List<String> token = new ArrayList<String>();
                    token.add(up.getYoumengToken());
                    if (up.getDeviceType() == 1) {
                        //android
                        appMsgPushService.publishAndroidListcast(msg, token);
                    } else if (up.getDeviceType() == 2) {
                        //ios
                        appMsgPushService.publishIOSListcast(msg, token);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

		}
	}

	/**
	 * 获取奖励金额
	 * @param firstTrading Trading
	 * @return 奖励金额
	 */
	private BigDecimal rewardMoney(Trading firstTrading, String ifId) {
		BigDecimal money = BigDecimal.ZERO;
		if (firstTrading.getBuyMoney().compareTo(new BigDecimal(100)) >= 0 && firstTrading.getBuyMoney().compareTo(new BigDecimal(1000)) < 0) {
			money = new BigDecimal(2);
			// 红包奖励
			PageInfo pageInfo = redPacketController.activityRedPacketReward(firstTrading.getuId(), money);
			// 更新InvitingFriendsMongo奖励状态
			if (pageInfo.getCode() == 200) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status4", 2).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else if (pageInfo.getCode() == 1003) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status4", 3).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status4", 4).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			}
		} else if (firstTrading.getBuyMoney().compareTo(new BigDecimal(1000)) >= 0 && firstTrading.getBuyMoney().compareTo(new BigDecimal(2000)) < 0) {
			money = new BigDecimal(6);
			// 红包奖励
			PageInfo pageInfo = redPacketController.activityRedPacketReward(firstTrading.getuId(), money);
			// 更新InvitingFriendsMongo奖励状态
			if (pageInfo.getCode() == 200) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status5", 2).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else if (pageInfo.getCode() == 1003) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status5", 3).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status5", 4).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			}
		} else {
			money = new BigDecimal(12);
			// 红包奖励
			PageInfo pageInfo = redPacketController.activityRedPacketReward(firstTrading.getuId(), money);
			// 更新InvitingFriendsMongo奖励状态
			if (pageInfo.getCode() == 200) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status6", 2).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else if (pageInfo.getCode() == 1003) {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status6", 3).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			} else {
				mongoTemplate.upsert(new Query(Criteria.where("id").is(ifId)),new Update().set("status6", 4).set("cardStatus", 1).set("rewardDate", sdf.format(new Date())),InvitingFriendsMongo.class);
			}
		}
		return money;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @param date
	 *            date
	 * @return return
	 */
	public Date lastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);// 设定当前时间为每月一号
		// 当前日历的天数上-1变成最大值 , 此方法不会改变指定字段之外的字段
		calendar.roll(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 判断是否连续
	 * 
	 * @param invitingFriend
	 *            InvitingFriend
	 * @param count
	 *            邀请人数
	 * @return return
	 */
	private static int checkContinue(InvitingFriend[] invitingFriend, int count) {
		int con = 0; // 统计多少个连续相同的count
		for (int i = 0; i < invitingFriend.length; i++) {
			if (invitingFriend[i].count == count) {
				con++;
			}
		}
		return con;
	}

	/**
	 * 获取邀请好友列表
	 * 
	 * @return 邀请好友列表
	 */
	@Override
	public PageInfo invitingFriendsList(Integer pageNumber, Integer pageSize) {
		PageInfo pageInfo = new PageInfo();
		List<InvitingFriendsMongo> findList = mongoTemplate
				.find(new Query().skip(pageInfo.getFrom()).limit(pageInfo.getSize()), InvitingFriendsMongo.class);
		pageInfo.setCode(200);
		pageInfo.setTotal(mongoTemplate.findAll(InvitingFriendsMongo.class).size());
		pageInfo.setRows(findList);
		return pageInfo;
	}

	/**
	 * 推荐人的邀请好友详情
	 * @param id InvitingFriendsMongoId 
	 * @return 邀请好友详情列表
	 */
	@Override
	public PageInfo invitingFriendsInfo(String id) {
		PageInfo pageInfo = new PageInfo();
		List<InvitingFriendInfoMongo> findList = mongoTemplate.find(new Query(Criteria.where("ifId").is(id)),
				InvitingFriendInfoMongo.class);
		pageInfo.setCode(200);
		pageInfo.setRows(findList);
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
	@Override
	public PageInfo updateInvitingFriends(String id, BigDecimal money, String rewardTime) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isEmpty(rewardTime)) {
			pageInfo.setCode(ResultParame.ResultInfo.PARAMS_ERROR.getCode());
			pageInfo.setMsg("奖励时间不能为空");
			return pageInfo;
		}
		// 获取奖励对象
		InvitingFriendsMongo findOne = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), InvitingFriendsMongo.class);
		Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式  
	    Matcher match = pattern.matcher(String.valueOf(money));   
	    if(match.matches() == true){   
			pageInfo = redPacketController.activityRedPacketReward(findOne.getUserId(), money);
			if (pageInfo.getCode() == 200) {
	    		Update update = new Update();
	    		if (money.compareTo(new BigDecimal(10)) == 0) {
	    			update.set("status2", 2);
	    			update.set("rewardTime2", rewardTime);
	    		} else if (money.compareTo(new BigDecimal(50)) == 0) {
	    			update.set("status3", 2);
	    			update.set("rewardTime3", rewardTime);
	    		} else if (money.compareTo(new BigDecimal(2)) == 0) {
	    			update.set("status4", 2);
	    			update.set("rewardTime4", rewardTime);
	    		} else if (money.compareTo(new BigDecimal(6)) == 0) {
	    			update.set("status5", 2);
	    			update.set("rewardTime5", rewardTime);
	    		} else if (money.compareTo(new BigDecimal(12)) == 0) {
	    			update.set("status6", 2);
	    			update.set("rewardTime6", rewardTime);
	    		}  
	    		mongoTemplate.upsert(new Query(Criteria.where("id").is(id)), update, InvitingFriendsMongo.class);
	    	}
	    }
		
		return pageInfo;
	}

	/**
	 * 人脉风云排行榜（每月）
	 * @param pageNumber 当前页数
	 * @param pageSize 每页显示行数
	 * @return 人脉风云排行榜（每月）列表
	 */
	@Override
	public PageInfo monthInvitingRewardList(Integer pageNumber, Integer pageSize) {
		PageInfo pageInfo = new PageInfo();
		List<MonthInvitingRewardMongo> findList = mongoTemplate.find(new Query()
				.with(new Sort(new Order(Direction.DESC, "level"))).skip(pageInfo.getFrom()).limit(pageInfo.getSize()),
				MonthInvitingRewardMongo.class);
		pageInfo.setCode(200);
		pageInfo.setTotal(mongoTemplate.findAll(MonthInvitingRewardMongo.class).size());
		pageInfo.setRows(findList);
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
	@Override
	public PageInfo monthUpdateInvitingReward(String id, String rewardContent, String rewardCode, String rewardTime) {
		PageInfo pageInfo = new PageInfo();
		if (StringUtils.isEmpty(rewardTime)) {
			pageInfo.setCode(ResultParame.ResultInfo.PARAMS_ERROR.getCode());
			pageInfo.setMsg("奖励时间不能为空");
			return pageInfo;
		}

		// 获取奖励数据对象
		MonthInvitingRewardMongo monthInvitingRewardMongo = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), MonthInvitingRewardMongo.class);
		String str2 = "";
		if (rewardContent != null && !"".equals(rewardContent)) {
			for (int i = 0; i < rewardContent.length(); i++) {
				if (rewardContent.charAt(i) >= 48 && rewardContent.charAt(i) <= 57) {
					str2 += rewardContent.charAt(i);
				}
			}
		}
		Update update = new Update();
		if (!"".equals(str2)) {
			if (str2.equals("1666") || str2.equals("888") || str2.equals("666")) {
				PageInfo resultInfo = redPacketController.activityRedPacketReward(monthInvitingRewardMongo.getUserId(), new BigDecimal(str2));
				if (resultInfo.getCode() == 200) {
					update.set("status", 2);
				} else if (resultInfo.getCode() == 1003) {
					update.set("status", 3);
				} else {
					update.set("status", 4);
				}
			}
		} else {
			update.set("status", 2);
		}
		update.set("rewardContent", rewardContent);
		update.set("rewardDate", rewardTime);
		update.set("singNumber", rewardCode);
		mongoTemplate.upsert(new Query(Criteria.where("id").is(id)), update, MonthInvitingRewardMongo.class);
		pageInfo.setCode(200);
		return pageInfo;
	}

	@Override
	public PageInfo addInvitingFriendsLevel(MonthInvitingRewardMongo2 vo) {
		PageInfo pageInfo = new PageInfo();
		vo.setMonth(vo.getRewardDate().substring(0, 4) + "年" + vo.getRewardDate().substring(5, 7) + "月");
		vo.setStatus(2);
		mongoTemplate.insert(vo);
		pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("新增成功");
		return pageInfo;

	}

	@Override
	public PageInfo findInvitingLevelList(Integer pageNumber, Integer pageSize) {
		PageInfo pageInfo = new PageInfo();
		String nowTime = sdf.format(new Date());
		List<MonthInvitingRewardMongo2> findList = mongoTemplate
				.find(new Query(Criteria.where("rewardDate").gte(nowTime.substring(0, 8) + "01 00:00:00").lte(nowTime + " 23:59:59"))
				.with(new Sort(new Order(Direction.DESC, "level"))).skip(pageInfo.getFrom()).limit(pageInfo.getSize()),
				MonthInvitingRewardMongo2.class);
		pageInfo.setCode(200);
		pageInfo.setTotal(mongoTemplate.findAll(MonthInvitingRewardMongo2.class).size());
		pageInfo.setRows(findList);
		return pageInfo;
	}

	@Override
	public PageInfo deleteInvitingLevelData(String id) {
		PageInfo pageInfo = new PageInfo();
		Query query = Query.query(Criteria.where("id").is(id));
		mongoTemplate.remove(query, MonthInvitingRewardMongo2.class);
		return pageInfo;
	}

}

/**
 * 邀请好友等级实体类
 * 
 * @author suyb
 *
 */
class InvitingFriend {
	public int count;
	public String id;
	public int level;

	InvitingFriend(int count, String id) {
		this.count = count;
		this.id = id;
	}

	@Override
	public String toString() {
		return "InvitingFriend [count=" + count + ", id=" + id + ", level=" + level + "]";
	}

}
