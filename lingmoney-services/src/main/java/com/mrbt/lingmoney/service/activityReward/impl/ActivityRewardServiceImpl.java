package com.mrbt.lingmoney.service.activityReward.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.mapper.ReferralAllMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketMapper;
import com.mrbt.lingmoney.mapper.UsersRedPacketUnionMapper;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.model.UsersPropertiesExample;
import com.mrbt.lingmoney.model.UsersRedPacket;
import com.mrbt.lingmoney.model.UsersRedPacketExample;
import com.mrbt.lingmoney.model.UsersRedPacketUnion;
import com.mrbt.lingmoney.model.UsersRedPacketUnionExample;
import com.mrbt.lingmoney.mongo.ActivityRewardMongo;
import com.mrbt.lingmoney.mongo.WapActivityRewardMongo;
import com.mrbt.lingmoney.service.activityReward.ActivityRewardService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年5月10日
 */
@Service
public class ActivityRewardServiceImpl implements ActivityRewardService {

	SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final String START_TIME = "2018-05-22 21:00:00";
	private final String END_TIME = "2018-06-30 23:59:59";
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UsersRedPacketMapper usersRedPacketMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private RedisSet redisSet;
	@Autowired
	private RedisGet redisGet;
	@Autowired
	private RedisDao redisDao;
	
	private final static String SHOW_COUPON_ALERT = "SHOW_COUPON_ALERT";
	
	@Autowired
	private UsersRedPacketUnionMapper usersRedPacketUnionMapper;

	// /**
	// * 查询用户红包个数
	// * @params uId 用户id
	// * @author suyb
	// * @date 2018-05-11 09:27:56
	// * @return JSONObject
	// */
	// @Override
	// @Transactional
	// public JSONObject findUserRedPacketCount(String uId) {
	// JSONObject jsonObject = new JSONObject();
	// // 查询用户所有未点击的红包个数
	// List<ActivityRewardMongo> findList = mongoTemplate.find(new
	// Query(Criteria.where("uId").is(uId).and("status").is(0)),
	// ActivityRewardMongo.class);
	// int count = 0; // 初始化可点击红包个数
	// if (!findList.isEmpty()) {
	// count = findList.size();
	// }
	// jsonObject.put("count", count);
	// return jsonObject;
	// }
	//
	// /**
	// * 点击红包操作
	// * @param uId 用户id
	// * @author suyb
	// * @date 2018-05-11 09:54:17
	// * @return JSONObject
	// */
	// @Override
	// @Transactional
	// public JSONObject clickRedPacketDo(String uId) {
	// JSONObject jsonObject = new JSONObject();
	// // 按时间正序查找出用户可领取的一张红包
	// ActivityRewardMongo mongo = mongoTemplate.findOne(new
	// Query(Criteria.where("uId").is(uId).and("status").is(0)).with(new
	// Sort(new Order(Direction.ASC, "createDate"))).limit(1),
	// ActivityRewardMongo.class);
	// if (null != mongo) {
	// // 返回奖励红包金额
	// jsonObject.put("money", mongo.getMoney());
	// jsonObject.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
	// // 变更状态 （未发放---发放中）
	// mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())),
	// new Update().set("status", 1), ActivityRewardMongo.class);
	// } else {
	// // 已无可领取红包
	// jsonObject.put("code", ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
	// }
	// return jsonObject;
	// }

	/**
	 * 查询用户红包个数
	 * @params uId 用户id
	 * @author suyb
	 * @date 2018-05-11 09:27:56
	 * @return JSONObject
	 */
	@Override
	@Transactional
	public JSONObject findUserRedPacketCount(String uId) {
		JSONObject jsonObject = new JSONObject();
		// 查询用户所有未点击的红包个数
		List<WapActivityRewardMongo> findList = mongoTemplate.find(new Query(Criteria.where("uId").is(uId).and("status").is(0)), WapActivityRewardMongo.class);
		int count = 0; // 初始化可点击红包个数
		if (!findList.isEmpty()) {
			count = findList.size();
		}
		jsonObject.put("count", count);
		return jsonObject;
	}

	/**
	 * 点击红包操作
	 * @param uId 用户id
	 * @author suyb
	 * @date 2018-05-11 09:54:17
	 * @return JSONObject
	 */
	@Override
	@Transactional
	public JSONObject clickRedPacketDo(String uId) {
		JSONObject jsonObject = new JSONObject();
		// 按时间正序查找出用户可领取的一张红包
		WapActivityRewardMongo mongo = mongoTemplate.findOne(new Query(Criteria.where("uId").is(uId).and("status").is(0)).with(new Sort(new Order(Direction.ASC, "createDate"))).limit(1), WapActivityRewardMongo.class);
		if (null != mongo) {
			// 返回奖励红包金额
			jsonObject.put("money", mongo.getMoney());
			jsonObject.put("code", ResultParame.ResultInfo.SUCCESS.getCode());
			// 变更状态 （未发放---发放中）
			mongoTemplate.upsert(new Query(Criteria.where("id").is(mongo.getId())), new Update().set("status", 1), WapActivityRewardMongo.class);
		} else {
			// 已无可领取红包
			jsonObject.put("code", ResultParame.ResultInfo.NOT_FOUND_DATA.getCode());
		}
		return jsonObject;
	}

	/**
	 * 查询用户是否有未查看红包
	 * 
	 * @param uId 用户id
	 * @return JSONObject
	 */
	@Override
	public JSONObject notCheckedRedPacket(String uId) {
		JSONObject object = new JSONObject();
		int count = 0; // 未查看红包数量
		UsersRedPacketExample example = new UsersRedPacketExample();
		example.createCriteria().andStatusEqualTo(0).andIsCheckEqualTo(0).andValidityTimeGreaterThan(new Date()).andUIdEqualTo(uId);
		List<UsersRedPacket> list = usersRedPacketMapper.selectByExample(example);
		if (!list.isEmpty()) {
			count = list.size();
		}
		object.put("count", count);
		return object;
	}

	/**
	 * 未查看-->已查看
	 * 
	 * @param uId 用户id
	 */
	@Override
	public void checkedRedPacket(String uId) {
		// 获取用户所有符合类型的优惠券集合
		UsersRedPacketExample example = new UsersRedPacketExample();
		example.createCriteria().andStatusEqualTo(0).andIsCheckEqualTo(0).andValidityTimeGreaterThan(new Date()).andUIdEqualTo(uId);
		List<UsersRedPacket> list = usersRedPacketMapper.selectByExample(example);
		if (!list.isEmpty()) {
			for (UsersRedPacket usersRedPacket : list) {
				usersRedPacket.setIsCheck(1);
				usersRedPacketMapper.updateByPrimaryKey(usersRedPacket);
			}
		}
	}

	/**
	 * 是否符合弹框要求
	 * 
	 * @param request 请求
	 * @return JSONObject
	 */
	@Override
	public JSONObject alertControl(String uId) {
		JSONObject object = new JSONObject();
		int count = 0; // 不弹出
		// // 判断用户的推荐人，理财经理用户、公司员工或有返佣行为的除外
		// ReferralAllExample example = new ReferralAllExample();
		// // 判断用户是否为公司员工
		// example.createCriteria().andUIdEqualTo(uId);
		// List<ReferralAll> exampleList =
		// referralAllMapper.selectByExample(example);
		// // exampleList不为空，说明该用户为公司员工
		// if (exampleList.isEmpty()) {
		// // 为空，继续判断用户是否为理财经理客户
		// UsersPropertiesExample usersPropertiesExample = new
		// UsersPropertiesExample();
		// usersPropertiesExample.createCriteria().andReferralIdEqualTo(uId).andPlatformTypeEqualTo(0);
		// List<UsersProperties> referralList =
		// usersPropertiesMapper.selectByExample(usersPropertiesExample);
		// if (!referralList.isEmpty()) {
		// // 判断推荐人是否是理财经理
		// for (UsersProperties referral : referralList) {
		// example.createCriteria().andUIdEqualTo(referral.getuId());
		// List<ReferralAll> financeList =
		// referralAllMapper.selectByExample(example);
		// // financeList为空则推荐人不为理财经理
		// if (financeList.isEmpty()) {
		// count = 1;
		// }
		// }
		// } else {
		// // referralList为空，则为自然用户
		// count = 1;
		// }
		// }
		
		try {
			UsersPropertiesExample ue = new UsersPropertiesExample();
			ue.createCriteria().andRegDateBetween(SF.parse(START_TIME), SF.parse(END_TIME)).andUIdEqualTo(uId).andPlatformTypeEqualTo(0);
			List<UsersProperties> list = usersPropertiesMapper.selectByExample(ue);
			if (!list.isEmpty()) {
				String key = AppCons.FIVE_TWO_ZERO_REDPACKET + uId;
				Object stringResult = redisGet.getRedisStringResult(key);
				if (null == stringResult) {
					count = 1;
					redisSet.setRedisStringResult(key, key);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		object.put("count", count);

		return object;
	}

	@Override
	public PageInfo showCouponReminding(String uId) {
		PageInfo pageinfo = new PageInfo();
		
		if (redisDao.get(SHOW_COUPON_ALERT + uId) != null) {
			pageinfo.setCode(ResultInfo.NO_DATA.getCode());
			pageinfo.setMsg(ResultInfo.NO_DATA.getMsg());
		} else {
			UsersRedPacketUnionExample urpuExample = new UsersRedPacketUnionExample();
			urpuExample.createCriteria().andUIdEqualTo(uId).andValidityTimeGreaterThan(new Date()).andUserStatusEqualTo(ResultNumber.ZERO.getNumber());
			
			List<UsersRedPacketUnion> urpuList = usersRedPacketUnionMapper.selectByExample(urpuExample);
			if (urpuList != null && urpuList.size() > 0) {
				JSONObject jsonObject = new JSONObject();
				int rateCoup = 0;
				int redEnve = 0;
				for(UsersRedPacketUnion urpu : urpuList) {
					if (urpu.getHrpType() == 1) {
						redEnve++;
					} else if (urpu.getHrpType() == 2) {
						rateCoup++;
					}
				}
				
				jsonObject.put("rateCoup", rateCoup);
				jsonObject.put("redEnve", redEnve);
				jsonObject.put("coupCount", urpuList.size());
				
				pageinfo.setCode(ResultInfo.SUCCESS.getCode());
				pageinfo.setMsg(ResultInfo.SUCCESS.getMsg());
				pageinfo.setObj(jsonObject);
				
				redisDao.set(SHOW_COUPON_ALERT + uId, "");
				redisDao.expire(SHOW_COUPON_ALERT + uId, DateUtils.getTomorrowMorning());
				
			} else {
				pageinfo.setCode(ResultInfo.NO_DATA.getCode());
				pageinfo.setMsg(ResultInfo.NO_DATA.getMsg());
			}
		}
		return pageinfo;
	}
}
