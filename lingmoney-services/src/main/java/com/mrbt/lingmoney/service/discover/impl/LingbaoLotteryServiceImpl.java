package com.mrbt.lingmoney.service.discover.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.LingbaoExchangeInfoMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftCartMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftMapper;
import com.mrbt.lingmoney.mapper.LingbaoLotteryRatioMapper;
import com.mrbt.lingmoney.mapper.LingbaoLotteryTypeMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.LingbaoExchangeInfo;
import com.mrbt.lingmoney.model.LingbaoGift;
import com.mrbt.lingmoney.model.LingbaoGiftCart;
import com.mrbt.lingmoney.model.LingbaoGiftWithBLOBs;
import com.mrbt.lingmoney.model.LingbaoLotteryType;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.discover.LingbaoLotteryService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.LotteryUtil;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年4月17日 下午3:58:14
 * @version 1.0
 * @description
 **/
@Service
public class LingbaoLotteryServiceImpl implements LingbaoLotteryService {
    private static int item; // 保存中奖下标

	@Autowired
	private RedisDao redisDao;
	@Autowired
	private LingbaoLotteryTypeMapper lingbaoLotteryTypeMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private LingbaoGiftCartMapper lingbaoGiftCartMapper;
	@Autowired
	private LingbaoExchangeInfoMapper lingbaoExchangeInfoMapper;
	@Autowired
	private LingbaoLotteryRatioMapper lingbaoLotteryRatioMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private LingbaoGiftMapper lingbaogiftMapper;

	@Override
	public int queryLotteryRatio(String id) {
		// 默认值为 50
        int ratio = 50;
		if (redisDao.hasKey(AppCons.DAY_LOTTERY_RATIO + id)) {
			ratio = (int) redisDao.get(AppCons.DAY_LOTTERY_RATIO + id);
		}
		return ratio;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public JSONObject validateChoujiang(String uid, Integer typeID) {
		JSONObject json = new JSONObject();
		
		if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(typeID)) {
            json.put("code", ResultInfo.PARAM_MISS.getCode());
			json.put("msg", "参数错误");
			return json;
		}
		
		LingbaoLotteryType llt = lingbaoLotteryTypeMapper.selectByPrimaryKey(typeID);
		UsersAccount usersAccount = usersAccountMapper.selectByUid(uid);
		
		if (null != llt) {
			if (usersAccount.getCountLingbao() > llt.getIntegral()) {
				// 奖项信息
				List<Map<String, Object>> list = lingbaoLotteryRatioMapper.queryByTypeIdForCount(llt.getId());

				// 进行抽奖
				getLotteryItem(uid, list);
				
				// 入库领宝使用信息
				recordLotteryInfo(llt, usersAccount);
				
				Map<String, Object> gift = list.get(item);
				
				System.out.println("中奖下标：" + item);
				System.out.println("抽奖结果信息：奖项ID：" + gift.get("id") + " 礼品id：" + gift.get("giftId") + " 礼品名称："
						+ gift.get("name"));
				
				// 中奖概率KEY（虚假概率，页面展示用）
				String key = AppCons.DAY_LOTTERY_RATIO + uid;
				
				if (null != gift.get("giftId")) {
					// 中奖处理
					handleLotteryResult(uid, json, usersAccount, list, gift, key);

				} else {
					// 未中奖：修改中奖概率，页面展示用
                    if (redisDao.hasKey(key)) { // 最高90
						int dayLotteryRatio = (int) redisDao.get(key);
						dayLotteryRatio = dayLotteryRatio >= 90 ? 90 : dayLotteryRatio + 10;
						
						redisDao.set(key, dayLotteryRatio);
                    } else { // 如果第一次，默认50+10=60
						redisDao.set(key, 60);
					}
					
					// 设置第二天过期
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					redisDao.expire(key, cal.getTime());
					
					System.out.println("日中奖几率过期时间：" + DateUtils.getDateStr(cal.getTime(), DateUtils.sft));
					
					json.put("dayLotteryRatio", (int) redisDao.get(key));
				}
				
                json.put("code", ResultInfo.SUCCESS.getCode());
				json.put("msg", "success");
				json.put("item", item);
				json.put("lingbao", usersAccount.getCountLingbao());
			} else {
                json.put("code", ResultInfo.LINGBAO_INSUFFICIENT.getCode());
				json.put("msg", "领宝不足");
			}
		} else {
            json.put("code", ResultInfo.ACTIVITY_NOT_LINE.getCode());
			json.put("msg", "该活动已下线");
		}
		
		return json;
	}

	@Override
	public List<Map<String, Object>> queryLotteryInfo() {
		return lingbaoExchangeInfoMapper.queryLotteryInfo();
	}

	@Override
	public JSONObject queryChoujiangItem(Integer typeID) {
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(typeID)) {
            json.put("code", ResultInfo.PARAM_MISS.getCode());
			json.put("msg", "参数缺失");
			return json;
		}
		
		LingbaoLotteryType llt = lingbaoLotteryTypeMapper.selectByPrimaryKey(typeID);
		long now = new Date().getTime();
		if (null == llt || llt.getStatus() == 0 || llt.getStartTime().getTime() > now
				|| llt.getEndTime().getTime() < now) {
            json.put("code", ResultInfo.ACTIVITY_NOT_LINE.getCode());
			json.put("msg", "该活动已下线");
			return json;
		}
		
		List<Map<String, Object>> list = lingbaoLotteryRatioMapper.queryByTypeId(llt.getId());
		if (null == list || list.size() == 0) {
            json.put("code", ResultInfo.LUCK_DRAW_INFO_ERROR.getCode());
			json.put("msg", "抽奖信息有误");
			System.out.println("抽奖---》活动下无有效奖项。 活动id:" + llt.getId());
			return json;
		}
		
		System.out.println("查询奖项成功：");
		
        json.put("code", ResultInfo.SUCCESS.getCode());
		json.put("msg", "查询成功");
		json.put("data", JSONArray.fromObject(list));
		json.put("cost", llt.getIntegral());
		json.put("rule", llt.getRule());
		json.put("picture", llt.getPicture());
		
		System.out.println(json);
		
		return json;
	}

	    /**
     * 用户中奖处理：
     *     如果是一等奖，需要累计理财额度达到五万以上，否则礼品下降一档。
     *     如果中奖礼品库存不足，礼品下降一档。
     *     降档操作最多降到第四档，即当礼品为四挡时不在判断是否存在库存。
     *     确定奖品后，奖品记录入库lingbao_exchange_info
     * @param uid 用户id
     * @param json json
     * @param usersAccount 用户账户信息
     * @param list list
     * @param gift 礼品信息
     * @param key 中奖概率的key
     */
	private void handleLotteryResult(String uid, JSONObject json, UsersAccount usersAccount,
			List<Map<String, Object>> list, Map<String, Object> gift, String key) {
		// 奖品档位
		int prizeLevel = (Integer) gift.get("prizeLevel");
		
		// 一等奖，需要验证该用户的理财额度是否达标
		if (prizeLevel == 1) {
			BigDecimal fmoney = lingbaoLotteryRatioMapper.queryYearFinancing(uid);
			// 理财额度在50000以上的才能获得一等奖，否则降级为二等奖(遍历礼品取第一个二等奖）
			if (fmoney == null || fmoney.compareTo(new BigDecimal(50000)) != 1) {
				for (int k = 0; k < list.size(); k++) {
					if (null != list.get(k).get("prizeLevel") && (Integer) list.get(k).get("prizeLevel") == 2
							&& null != list.get(k).get("giftId")) {
						gift = list.get(k);
						item = k;
						prizeLevel = 2;
						
						System.out.println("该用户抽中一等的，理财额不达标，修改奖品：中奖下标：" + item);
						System.out.println("修改后抽奖结果信息：礼品级别：" + gift.get("prizeLevel") + " 奖项ID："
								+ gift.get("id") + " 礼品id：" + gift.get("giftId") + " 礼品名称：" + gift.get("name"));
					}
				}
			}
		}
		
		System.out.println("中奖信息：礼品ID" + gift.get("id") + " 礼品级别:" + prizeLevel + " 礼品库存:" + gift.get("store"));
		
		LingbaoGift lg = lingbaogiftMapper.selectByPrimaryKey((Integer) gift.get("giftId"));
		
		// 库存不足，降档
		if (lg != null && lg.getStore() < 1 && prizeLevel < 4) {
			item = lowerPrize(prizeLevel + 1, list);
			gift = list.get(item);
			
			System.out.println("抽奖结果信息：奖项ID：" + gift.get("id") + " 礼品id：" + gift.get("giftId") + " 礼品名称："
					+ gift.get("name"));
		}
		
		// 中奖扣除库存
		LingbaoGift lgift = lingbaogiftMapper.selectByPrimaryKey((Integer) gift.get("giftId"));
		LingbaoGiftWithBLOBs lwb = new LingbaoGiftWithBLOBs();
		lwb.setId(lgift.getId());
        lwb.setStore(lgift.getStore() - 1); // 库存-1
		lingbaogiftMapper.updateByPrimaryKeySelective(lwb);

		// 中奖产品序列号
		String seriaNumber = System.currentTimeMillis() + "";
		/*
		 * 如果抽中的为领宝礼品（type=2），
		 * 则兑换记录表新增一条状态为已收货的记录（status=2），领宝记录表新增记录，用户账户表增加领宝
		 */
        if (lgift.getType() == 2) { // 如果抽中的为领宝礼品
			// 兑换记录表新增一条状态为已收货的记录（status=2）
			LingbaoExchangeInfo initLei = new LingbaoExchangeInfo();
			initLei.setuId(uid);
			initLei.setGiftId((Integer) list.get(item).get("giftId"));
			initLei.setName((String) list.get(item).get("name"));
			initLei.setNum(1);
			initLei.setIntegral(0);
			initLei.setActivityId((Integer) list.get(item).get("typeId"));
			initLei.setSerialNumber(seriaNumber);
            initLei.setStatus(2); // 已收货
            initLei.setType(1); // 抽奖
			initLei.setExchangeTime(new Date());
			initLei.setReceiveTime(new Date());
			lingbaoExchangeInfoMapper.insertSelective(initLei);
			
			// activity_record新增记录
			ActivityRecord activityRecord = new ActivityRecord();
			activityRecord.setAmount(lgift.getIntegral());
			activityRecord.setContent("抽中" + lgift.getName());
			activityRecord.setName("抽奖");
			activityRecord.setStatus(0);
			activityRecord.setType(0);
			activityRecord.setuId(uid);
			activityRecord.setUseDate(new Date());
			activityRecordMapper.insertSelective(activityRecord);
			
			// 用户账户增加领宝
			usersAccount.setCountLingbao(usersAccount.getCountLingbao() + lgift.getIntegral());
			usersAccountMapper.updateByPrimaryKeySelective(usersAccount);

        } else { // 抽中为普通礼品
			LingbaoGiftCart lgc = new LingbaoGiftCart();
			lgc.setGiftId((Integer) gift.get("giftId"));
			lgc.setNum(1);
			lgc.setIntegral(0);
			lgc.setuId(uid);
			lgc.setAddTime(new Date());
			lgc.setSerialNumber(seriaNumber);
			lingbaoGiftCartMapper.insert(lgc);
			
			// 向兑换记录表中初始化一条记录
			LingbaoExchangeInfo initLei = new LingbaoExchangeInfo();
			initLei.setuId(uid);
			initLei.setGiftId((Integer) list.get(item).get("giftId"));
			initLei.setName((String) list.get(item).get("name"));
			initLei.setNum(1);
			initLei.setIntegral(0);
			initLei.setActivityId((Integer) list.get(item).get("typeId"));
			initLei.setSerialNumber(seriaNumber);
            initLei.setStatus(3); // 初始化状态
			initLei.setType(1);
			initLei.setExchangeTime(new Date());
			lingbaoExchangeInfoMapper.insertSelective(initLei);
			
			System.out.println("初始化抽奖记录数据：" + initLei.toString());
		}
		
		// 中奖后页面中奖概率恢复到50
		json.put("dayLotteryRatio", 50);
		redisDao.set(key, 50);
		
		// 礼品类型：0实物礼品 1虚拟礼品 2领宝
		int type = (Integer) gift.get("typename");
		
		// 设置《礼品价值》--页面展示数据
		if (type == 2) {
			json.put("price", "价值：" + gift.get("price") + "领宝");
			json.put("buycart", false);

		} else {
			json.put("price", "价值：" + gift.get("price") + "元");
			json.put("buycart", true);
		}
		
	}

	/**
	 * 抽奖：修改用户领宝余额，保存领宝使用记录 
	 * @param llt 领宝抽奖类型
	 * @param usersAccount 用户账户信息
	 */
	private void recordLotteryInfo(LingbaoLotteryType llt, UsersAccount usersAccount) {
		// 扣除账户领宝
		usersAccount.setCountLingbao(usersAccount.getCountLingbao() - llt.getIntegral());
		usersAccountMapper.updateByPrimaryKeySelective(usersAccount);
		
		// 领宝记录新增一条记录
		ActivityRecord ar = new ActivityRecord();
		ar.setAmount(-llt.getIntegral());
		ar.setName("抽奖");
		ar.setContent("抽奖花费领宝");
		ar.setStatus(0);
		ar.setType(0);
		ar.setuId(usersAccount.getuId());
		ar.setUseDate(new Date());
		activityRecordMapper.insertSelective(ar);
	}

	/**
	 * 进行抽奖，获取奖项下标
	 *     第一次抽奖,100%中100领宝：购物车和兑换记录中都无中奖记录即为初次抽奖
	 *     二次抽奖，通过抽奖工具类获取中奖下标
	 * @param uid 用户id
	 * @param list 奖项
	 */
	private void getLotteryItem(String uid, List<Map<String, Object>> list) {
		int i = lingbaoGiftCartMapper.queryLotteryByUid(uid);
		int j = lingbaoExchangeInfoMapper.queryLotteryByUid(uid);
		if (i == 0 && j == 0) {
			item = 0;
		} else { // 进行抽奖
			List<Double> ratioList = new ArrayList<Double>();
			for (Map<String, Object> map : list) {
				ratioList.add((Double) map.get("ratio"));
			}
			LotteryUtil lu = new LotteryUtil(ratioList);
			item = lu.randomColunmIndex();
			if (item == -1) {
				throw new IllegalArgumentException("概率集合设置不合理！");
			}
		}
	}

	/**
	 * 抽奖礼品降档
	 *
	 * @Description
	 * @param prizeLevel
	 *            当前礼品等级
	 * @param list
	 *            所有礼品列表
	 * @return 降级后奖项下标
	 */
	private int lowerPrize(int prizeLevel, List<Map<String, Object>> list) {
		System.out.println("奖品库存不足，降档");
		for (int k = 0; k < list.size(); k++) {
			if (null != list.get(k).get("prizeLevel") && (Integer) list.get(k).get("prizeLevel") == prizeLevel) {
				Map<String, Object> gift = list.get(k);
				prizeLevel = (Integer) gift.get("prizeLevel");
				
				System.out.println("降档礼品信息：礼品级别：" + prizeLevel + " 礼品id：" + gift.get("giftId") + " 礼品名称："
						+ gift.get("name"));
				
				if (gift.get("giftId") != null) {
					LingbaoGift lg = lingbaogiftMapper.selectByPrimaryKey((Integer) gift.get("giftId"));
					if (lg != null && lg.getStore() < 1 && prizeLevel < 4) {
						lowerPrize(prizeLevel + 1, list);
					} else {
						item = k;
					}
				} else {
					item = k;
				}
			}
		}
		
		return item;
	}
}
