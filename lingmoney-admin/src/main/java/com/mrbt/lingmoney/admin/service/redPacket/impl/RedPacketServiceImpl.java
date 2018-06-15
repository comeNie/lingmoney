package com.mrbt.lingmoney.admin.service.redPacket.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.admin.controller.task.AutoSendRedPacketTask;
import com.mrbt.lingmoney.admin.service.bank.HxQueryTradingResultService;
import com.mrbt.lingmoney.admin.service.info.AppMsgPushService;
import com.mrbt.lingmoney.admin.service.redPacket.RedPacketService;
import com.mrbt.lingmoney.bank.reward.HxSingleRewardOrCutAMelon;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.AccountFlowMapper;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.mapper.HxRedPacketMapper;
import com.mrbt.lingmoney.mapper.ProductMapper;
import com.mrbt.lingmoney.mapper.ReferralAllMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.model.AccountFlow;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.model.HxRedPacket;
import com.mrbt.lingmoney.model.HxRedPacketExample;
import com.mrbt.lingmoney.model.Product;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.UsersAccountExample;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.SendMessageUtils;
import com.mrbt.lingmoney.youmengpush.android.AndroidListcast;
import com.mrbt.lingmoney.youmengpush.android.AndroidNotification.DisplayType;
import com.mrbt.lingmoney.youmengpush.ios.IOSListcast;

/**
 * @author suyibo
 * @date 创建时间：2017年11月2日
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {
	
	private static final Logger LOGGER = LogManager.getLogger(RedPacketServiceImpl.class);
	
	SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String END_TIME = "2018-07-01 00:00:00";

	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private HxRedPacketMapper hxRedPacketMapper;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private AccountFlowMapper accountFlowMapper;
	@Autowired
	private HxAccountMapper hxAccountMapper;
	@Autowired
	private HxQueryTradingResultService hxQueryTradingResultService;
	@Autowired
	private HxSingleRewardOrCutAMelon hxSingleRewardOrCutAMelon;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private AppMsgPushService appMsgPushService;
	@Autowired
	private ReferralAllMapper referralAllMapper;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private RedisGet redisGet;

	/**
	 * 查询用户是否领取过，未领取过才能领取，不能重复领取
	 * 
	 * @param userId
	 *            用户id
	 * @param actType
	 *            活动类型
	 * @param amount
	 *            金额/比例
	 * @return 返回信息
	 */
	public PageInfo findUserIsGetRedPacket(String userId, Integer actType, Double amount) {
		PageInfo pageInfo = new PageInfo();
		// 查询用户是否已经领取过活动优惠券
		Map<String, Object> map = new HashMap<>();
		List<Double> amountList = new ArrayList<>();
		amountList.add(amount);
		map.put("userId", userId);
		map.put("actType", actType);
		map.put("amountList", amountList);
		List<String> getRedPacketIdList = usersRedPacketMapper.findUserIsGetRedPacket(map);
		// 如果actType=4（首次理财），判断是否为首次理财
		if (actType == ResultParame.ResultNumber.FOUR.getNumber()) {
			// 查询用户是否有理财记录
			Integer count = accountFlowMapper.selectAccountFlowOfRechargeByUserId(map);
			if (getRedPacketIdList.isEmpty() && count == 1) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			}
		} else {
			if (getRedPacketIdList.isEmpty()) {
				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
			}
		}
		return pageInfo;
	}

	@Override
	public void rewardRedPackage(String userId, Integer actType, Double amount) {

		PageInfo pageInfo = findUserIsGetRedPacket(userId, actType, amount);
		if (pageInfo.getCode() == ResultParame.ResultInfo.SUCCESS.getCode()) {
			Map<String, Object> map = new HashMap<>();
			map.put("actType", actType);
			map.put("amount", amount);
			List<HxRedPacket> redPacketList = hxRedPacketMapper.selectRedPacketByParams(map);

			// 奖励用户
			if (!redPacketList.isEmpty()) {
				for (HxRedPacket redPacket : redPacketList) {
					saveUserRedPacket(userId, redPacket);
				}
			}
		}

	}

	/**
	 * 奖励用户红包
	 * 
	 * @param userId
	 *            用户id
	 * @param redPacket
	 *            红包
	 */
	private void saveUserRedPacket(String userId, HxRedPacket redPacket) {
		UsersRedPacket usersRedPacket = new UsersRedPacket();
		usersRedPacket.setuId(userId);
		usersRedPacket.setRpId(redPacket.getId());
		usersRedPacket.setStatus(0);
		usersRedPacket.setCreateTime(new Date());
		usersRedPacket.setIsCheck(0);
		if (redPacket.getDelayed() != null) {
			usersRedPacket.setValidityTime(DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1));
		} else {
			usersRedPacket.setValidityTime(redPacket.getValidityTime());
		}
		usersRedPacketMapper.insert(usersRedPacket);
	}

	/**
	 * 产品成立送红包
	 *
	 * @author suyibo 2017年10月31日
	 * @throws PageInfoException
	 *             异常
	 */
	
	private static Map<String, Integer> hrpTypeMap = new HashMap<String, Integer>();
	
	
	@Override
	public void doGiveRedPacketToUser() throws PageInfoException {
		// 获取兑付返现红包对象
		UsersRedPacketExample example = new UsersRedPacketExample();
		List<Integer> values = new ArrayList<Integer>();
		values.add(0);
		values.add(1);
		example.createCriteria().andStatusEqualTo(1).andCashStatusIn(values);
		List<UsersRedPacket> usersRedPacketList = usersRedPacketMapper.selectByExample(example);
		// 数据处理
		if (!usersRedPacketList.isEmpty()) {
			for (UsersRedPacket usersRedPacket : usersRedPacketList) {
				
				if (hrpTypeMap.containsKey(usersRedPacket.getRpId())) {
					if (hrpTypeMap.get(usersRedPacket.getRpId()) != 2) {
						continue;
					}
				} else {
					HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
					hrpTypeMap.put(usersRedPacket.getRpId(), hxRedPacket.getHrpType());
					if (hxRedPacket.getHrpType() != 2 && hxRedPacket.getHrpType() != 3) {
						continue;
					}
				}
				
				// 查看用户使用的红包的已成立产品
				Trading trading = tradingMapper.selectByPrimaryKey(usersRedPacket.gettId());
				
				if (trading == null) {
					continue;
				}
				
				Product product = productMapper.selectByPrimaryKey(trading.getpId());
				
				if (product == null) {
					continue;
				}
				
				if (product.getStatus() != 1 || product.getStatus() != 2 || product.getStatus() != 3
						|| product.getStatus() != 4) {
					continue;
				}
				//获取用户使用的红包对象
				
				UsersAccountExample ex = new UsersAccountExample();
				ex.createCriteria().andUIdEqualTo(usersRedPacket.getuId());
				List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
				if (resLi == null || resLi.size() == 0) {
					throw new PageInfoException("用户账户信息不存在", ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
				}
				UsersAccount userAcc = resLi.get(0);
				// 华兴账户
				HxAccountExample hxAccountExample = new HxAccountExample();
				hxAccountExample.createCriteria().andUIdEqualTo(usersRedPacket.getuId()).andStatusEqualTo(1);
				List<HxAccount> accountList = hxAccountMapper.selectByExample(hxAccountExample);
				if (accountList == null || accountList.size() == 0) {
					throw new PageInfoException("该账户无效", ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
				}

				HxRedPacket hxRedPacket = hxRedPacketMapper.selectByPrimaryKey(usersRedPacket.getRpId());
				if (usersRedPacket.getCashStatus() == 0) { // 使用未兑付
					HxAccount account = accountList.get(0);
					String acNo = account.getAcNo();
					String acName = account.getAcName();
					// 银行账户更新
					String logGroup = "\nrequestSingleRewardOrCutAMelon_" + System.currentTimeMillis() + "_";
					
					//兑现红包
					Document xml = hxSingleRewardOrCutAMelon.requestSingleRewardOrCutAMelon("PC", acNo, acName, String.valueOf(hxRedPacket.getHrpNumber()), "返现红包奖励", logGroup);
					// 转生String类型
					String xmlData = xml.asXML().replaceAll("\n", "");
					// 获取流水号
					String channelFlow = queryTextFromXml(xmlData, "channelFlow");

					/**
					 * 更新用户红包对象
					 */
					usersRedPacket.setActualAmount(hxRedPacket.getHrpNumber());
					usersRedPacket.setCashStatus(1); // 兑付中
					usersRedPacket.setNumber(channelFlow); // 流水号
					usersRedPacket.setLogGroup(logGroup);
					usersRedPacketMapper.updateByPrimaryKeySelective(usersRedPacket);

				} else { // 兑付中
					Map<String, Object> resultMap = hxQueryTradingResultService.queryHxTradingResult(usersRedPacket.getNumber(), null, usersRedPacket.getLogGroup());
					if (resultMap != null && "S".equals(resultMap.get("STATUS"))) {

						/**
						 * 数据库账户更新
						 */
						// 更新用户红包表
						usersRedPacket.setCashStatus(2); // 兑现完成
						usersRedPacketMapper.updateByPrimaryKeySelective(usersRedPacket);

						// 更新用户账户表
						UsersAccount userAccNew = new UsersAccount();
						userAccNew.setBalance(userAcc.getBalance().add(BigDecimal.valueOf(hxRedPacket.getHrpNumber())));
						usersAccountMapper.updateByExampleSelective(userAccNew, ex);

						/**
						 * 增加奖励流水
						 */
						AccountFlow accountFlow = new AccountFlow();
						accountFlow.setaId(userAcc.getId());
						accountFlow.setOperateTime(new Date());
						accountFlow.setMoney(BigDecimal.valueOf(hxRedPacket.getHrpNumber()));
						accountFlow.setStatus(1); // 0，处理中；1，已完成；2，处理失败；3，撤标成功；4.流标  5,支付完成;6取消支付； 7支付中； 8待支付
						accountFlow.setType(ResultParame.ResultNumber.FOUR.getNumber()); // 0：充值，1：取现，2：理财，3：赎回,4:奖励
						accountFlow.setNumber(usersRedPacket.getNumber());
						accountFlowMapper.insert(accountFlow);
						/**
						 * 短信通知用户
						 */
						// 获取用户信息
						Users user = usersMapper.selectByPrimaryKey(usersRedPacket.getuId());
						UsersProperties usersProperties = usersPropertiesMapper.selectByuId(usersRedPacket.getuId());

						String content = AppCons.reward_redpacket;
						content = MessageFormat.format(content, usersProperties.getName(), product.getName(), hxRedPacket.getHrpNumber());
						SendMessageUtils.sendSMS(user.getTelephone(), content);
					}
				}
			}
		}
	}



	/**
	 * 查询xml中指标签的text
	 * 
	 * @param xml
	 *            xml数据
	 * @param emlName
	 *            节点名称
	 * @return
	 */
	private static String queryTextFromXml(String xml, String emlName) {
		try {
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(xml);
			Document doc = reader.read(in);

			String emlText = doc.selectSingleNode("//" + emlName).getText();

			if (!StringUtils.isBlank(emlText)) {
				return emlText;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PageInfo activityRedPacketReward(String userId, BigDecimal amount) {
		PageInfo pageInfo = new PageInfo();
		// 更新用户余额
		UsersAccountExample ex = new UsersAccountExample();
		ex.createCriteria().andUIdEqualTo(userId);
		List<UsersAccount> resLi = usersAccountMapper.selectByExample(ex);
		if (resLi == null || resLi.size() == 0) {
			pageInfo.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_THREE.getNumber());
			pageInfo.setMsg("用户账户信息不存在");
		}
		UsersAccount userAcc = resLi.get(0);
		// 华兴账户
		HxAccountExample hxAccountExample = new HxAccountExample();
		hxAccountExample.createCriteria().andUIdEqualTo(userId).andStatusEqualTo(1);
		List<HxAccount> accountList = hxAccountMapper.selectByExample(hxAccountExample);
		if (accountList == null || accountList.size() == 0) {
			pageInfo.setCode(ResultParame.ResultNumber.ONE_ZERO_ZERO_ONE.getNumber());
			pageInfo.setMsg("账户无效");
		}

		HxAccount account = accountList.get(0);
		String acNo = account.getAcNo();
		String acName = account.getAcName();

		// 银行账户更新
		String logGroup = "\nrequestSingleRewardOrCutAMelon_" + System.currentTimeMillis() + "_";
		Document xml = hxSingleRewardOrCutAMelon.requestSingleRewardOrCutAMelon("PC", acNo, acName,
				String.valueOf(amount), "返现红包奖励", logGroup);
		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");

		// 3分钟后发送返现红包交易结果查询
		try {
			Thread.sleep(3 * 60 * 1000);

			Map<String, Object> resultMap = hxQueryTradingResultService.queryHxTradingResult(channelFlow, null,
					logGroup);

			if (resultMap != null) {
				// 更新用户账户表
				UsersAccount userAccNew = new UsersAccount();
				userAccNew.setBalance(userAcc.getBalance().add(amount));
				usersAccountMapper.updateByExampleSelective(userAccNew, ex);

				/**
				 * 增加奖励流水
				 */
				AccountFlow accountFlow = new AccountFlow();
				accountFlow.setaId(userAcc.getId());
				accountFlow.setOperateTime(new Date());
				accountFlow.setMoney(amount);
				accountFlow.setStatus(1); // 0，处理中；1，已完成；2，处理失败；3，撤标成功；4.流标 5,支付完成;6取消支付； 7支付中； 8待支付
				accountFlow.setType(ResultParame.ResultNumber.FOUR.getNumber()); // 0：充值，1：取现，2：理财，3：赎回,4:奖励
				accountFlow.setNumber(channelFlow);
				accountFlowMapper.insert(accountFlow);

				pageInfo.setCode(ResultParame.ResultInfo.SUCCESS.getCode());
				pageInfo.setMsg("发送成功");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return pageInfo;
	}

	/**
	 * 赠送红包定时任务
	 */
	@Override
	public void autoSendRedPacketTask() {
		try {
			Date endDate = SF.parse(END_TIME);
			Date nowDate = new Date();
			// 是否在活动内
			while (nowDate.getTime() < endDate.getTime()) {
				
				LOGGER.info("发放注册红包autoSendRedPacketTask:" + nowDate);
				
				String key = AppCons.LAST_REGIST_REDPACKET_TIME;
				// 从redis中获取最后一位赠送红包用户的注册时间
				Date lastTime = (Date) redisGet.getRedisStringResult(key);
				if (null == lastTime) {
					redisSet.setRedisStringResult(key, nowDate);
				} else {
					// 查询新注册的所有用户
					UsersPropertiesExample usersPropertiesExample = new UsersPropertiesExample();
					usersPropertiesExample.createCriteria().andPlatformTypeEqualTo(0).andRegDateGreaterThan(lastTime);
					List<UsersProperties> usersList = usersPropertiesMapper.selectByExample(usersPropertiesExample);
					if (usersList.isEmpty()) {
						Thread.sleep(3 * 1000);
						continue;
					}

					// 遍历usersList，符合条件赠送红包
					for (UsersProperties user : usersList) {
						// 判断用户的推荐人，理财经理用户、公司员工或有返佣行为的除外

						// if (null != user.getReferralId()) {
						// ReferralAllExample example = new
						// ReferralAllExample();
						// // 判断用户是否为公司员工
						// example.createCriteria().andUIdEqualTo(user.getuId());
						// List<ReferralAll> exampleList =
						// referralAllMapper.selectByExample(example);
						// // exampleList不为空，说明该用户为公司员工
						// if (!exampleList.isEmpty()) {
						// continue;
						// }
						//
						// // 为空，继续判断用户是否为理财经理客户
						// example.createCriteria().andUIdEqualTo(user.getReferralId());
						// List<ReferralAll> financeList =
						// referralAllMapper.selectByExample(example);
						// // financeList不为空，则推荐人为理财经理
						// if (!financeList.isEmpty()) {
						// continue;
						// }
						// }

						// 符合活动奖励要求 (目前所有注册用户都赠送)
						// 520返现红包赠送
						rewardRedPacket(user);

						nowDate = new Date();
						redisSet.setRedisStringResult(key, user.getRegDate());
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 赠送红包
	 * 
	 * @param uId 用户id
	 */
	private void rewardRedPacket(UsersProperties user) {
		// 查询出520投资返现红包
		HxRedPacketExample example = new HxRedPacketExample();
		example.createCriteria().andAcquisModeEqualTo(1).andStatusEqualTo(1); // 注册且发布
		List<HxRedPacket> exampleList = hxRedPacketMapper.selectByExample(example);
		if (!exampleList.isEmpty()) {
			// 查询用户是否已经领取过活动优惠券
			Map<String, Object> map = new HashMap<>();
			List<Double> amountList = new ArrayList<>();
			for (HxRedPacket hxRedPacket : exampleList) {
				amountList.add(hxRedPacket.getHrpNumber());
			}
			map.put("userId", user.getuId());
			map.put("actType", 1);
			map.put("amountList", amountList);
			List<String> getRedPacketIdList = usersRedPacketMapper.findUserIsGetRedPacket(map);
			if (getRedPacketIdList.isEmpty()) {
				saveBatchUserRedPacket(user.getuId(), exampleList);
				// 消息推送
				sendMSG(user);
			}
		}
	}

	/**
	 * 奖励用户红包
	 * 
	 * @param userId
	 *            用户id
	 * @param redPacket
	 *            红包
	 */
	private void saveBatchUserRedPacket(String userId, List<HxRedPacket> redPacketList) {
		// 10元投资红包2个，单笔认购一个月及以上期限常规产品满3000元时使用；
		// 20元投资红包2个，单笔认购一个月及以上期限常规产品满5000元时使用；
		// 50元投资红包1个，单笔认购一个月及以上期限常规产品满10000元时使用；
		// 110元投资红包1个，单笔认购三个月及以上期限常规产品满20000元时使用；
		// 300元投资红包1个，单笔认购三个月及以上期限常规产品满50000元时使用；
		List<UsersRedPacket> usersRedPacketList = new ArrayList<>();
		for (HxRedPacket redPacket : redPacketList) {
			UsersRedPacket usersRedPacket = new UsersRedPacket();
			usersRedPacket.setuId(userId);
			usersRedPacket.setRpId(redPacket.getId());
			usersRedPacket.setStatus(0);
			usersRedPacket.setCreateTime(new Date());
			usersRedPacket.setIsCheck(0);
			if (redPacket.getDelayed() != null) {
				usersRedPacket.setValidityTime(DateUtils.addDay2(new Date(), redPacket.getDelayed() + 1));
			} else {
				usersRedPacket.setValidityTime(redPacket.getValidityTime());
			}
			usersRedPacketList.add(usersRedPacket);
		}
		usersRedPacketMapper.insertBatch(usersRedPacketList);
	}

	/**
	 * 消息推送
	 * 
	 * @param uId 用户id
	 */
	private void sendMSG(UsersProperties up) {
		try {
			if (up != null && up.getDeviceType() != null && up.getYoumengToken() != null) {
				String content = "您有520元投资红包，请及时使用。";

				distinguishPushDevice(up.getDeviceType(), 1, content, up.getYoumengToken());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 区分设备，推送消息
	 * 
	 * @param deviceType 设备类型 1android 2 ios
	 * @param giftType 1 加息券 2红包
	 * @param content 消息内容
	 * @param token 设备token
	 *
	 */
	private void distinguishPushDevice(int deviceType, int giftType, String content, String token) {
		try {
			com.alibaba.fastjson.JSONObject customjson = new com.alibaba.fastjson.JSONObject();
			if (deviceType == 1) {
				// android
				AndroidListcast listcast = appMsgPushService.getAndroidListcast();
				listcast.setTitle("领钱儿");
				listcast.setTicker("领钱儿");
				listcast.setText(content);
				JSONObject object = new JSONObject();
				try {
					JSONObject object2 = new JSONObject();
					JSONObject object3 = new JSONObject();
					object3.put("status", 1);
					object3.put("money", "");
					object3.put("code", "0");
					object3.put("couponId", -1);
					object2.put("pushClassName", "mrbt.lingqian.main.mine.Coupon.CouponActivity");
					object2.put("androidJumpType", "5");
					object2.put("pushClassPropertys", object3);
					object.put("imgUrl", "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
					object.put("androidObj", object2);

					// customjson.put("imgUrl",
					// "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
					// customjson.put("androidObj",
					// "{\"pushClassName\":\"mrbt.lingqian.main.mine.Coupon.CouponActivity\",\"androidJumpType\":\"5\",\"pushClassPropertys\":{\"status\":0,\"money\":\"1\",\"couponId\":0,\"code\":\"0\"}}");
					listcast.goCustomAfterOpen(object);
					listcast.setDisplayType(DisplayType.NOTIFICATION);
					listcast.setProductionMode();
					listcast.setDeviceToken(token);
					appMsgPushService.pushMsg(listcast);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (deviceType == 2) {
				// ios
				IOSListcast listcast = appMsgPushService.getIOSListcast();
				JSONObject json = new JSONObject();
				json.put("title", "领钱儿");
				json.put("body", content);
				customjson.put("pushClassName", "ConcessionsListVC"); // 点击图片要前往Class
				customjson.put("pushClassPropertys", new com.alibaba.fastjson.JSONObject()); // 点击图片要前往Class传递的参数
				listcast.setCustomizedField("imgUrl",
						"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1521774965&di=cbba08a55fdbbac63df17a69148dad38&src=http://img.zcool.cn/community/0175ac57c83b160000018c1b520d0f.jpg");
				listcast.setCustomizedField("iosObj", customjson.toJSONString());
				listcast.setPredefinedKeyValue("alert", json);
				listcast.setProductionMode();
				listcast.setDeviceToken(token);
				appMsgPushService.pushMsg(listcast);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
