package com.mrbt.lingmoney.service.discover.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.LingbaoGiftCartMapper;
import com.mrbt.lingmoney.mapper.LingbaoSignMapper;
import com.mrbt.lingmoney.mapper.LingbaoSignRecordMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.LingbaoGiftCartExample;
import com.mrbt.lingmoney.model.LingbaoSign;
import com.mrbt.lingmoney.model.LingbaoSignExample;
import com.mrbt.lingmoney.model.LingbaoSignRecord;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.service.discover.MyDiscoverService;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * @author syb
 * @date 2017年4月12日 下午2:56:34
 * @version 1.0
 * @description
 **/
@Service
public class MyDiscoverServiceImpl implements MyDiscoverService {
	private static final Logger LOG = LogManager.getLogger(MyDiscoverServiceImpl.class);
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private LingbaoSignRecordMapper lingbaoSignRecordMapper;
	@Autowired
	private LingbaoSignMapper lingbaoSignMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	@Autowired
	private LingbaoGiftCartMapper lingbaoGiftCartMapper;

	@Override
	public PageInfo getUserInfo(String uid) {
		PageInfo pageInfo = new PageInfo();
		
		// 1.查询用户信息
		Map<String, Object> userInfo = usersPropertiesMapper.queryUserInfoMyPlace(uid);
		if (userInfo == null) {
			userInfo = new HashMap<String, Object>();
		}
		
		// 2.查询签到活动是否可用
		LingbaoSignExample lsExample = new LingbaoSignExample();
		lsExample.createCriteria().andEndTimeGreaterThanOrEqualTo(new Date()).andStartTimeLessThanOrEqualTo(new Date());
		List<LingbaoSign> signList = lingbaoSignMapper.selectByExample(lsExample);
		
		// 如果可用，查询用户今日是否签到
		if (signList != null && signList.size() > 0) {
            if (isSignInToday(uid)) { // 已签到
				userInfo.put("isSignToday", 1);
            } else { // 未签到
				userInfo.put("isSignToday", 0);
			}
        } else { // 不存在签到,保存值为-1
			userInfo.put("isSignToday", -1);
		}
		
		// 3.购物车中礼品数量查询
		LingbaoGiftCartExample lgcExample = new LingbaoGiftCartExample();
		lgcExample.createCriteria().andUIdEqualTo(uid);
		int cartGiftNum = (int) lingbaoGiftCartMapper.countByExample(lgcExample);
		userInfo.put("cartGiftNum", cartGiftNum);
		
        pageInfo.setCode(ResultInfo.SUCCESS.getCode());
		pageInfo.setMsg("success");
		pageInfo.setObj(userInfo);
		
		return pageInfo;
	}

	@Override
	public boolean isSignInToday(String uid) {
		// 先从redis查询
		Object obj = redisDao.get(AppCons.SINGIN_REDIS_KEY + uid);
        if (null == obj) { // redis中无数据，从数据库查询
			int i = lingbaoSignRecordMapper.queryTodaySignRecord(uid);
			System.out.println("redis未查询到签到数据，从数据库查询，结果：" + i);
            if (i > 0) { // 向redis中保存一条数据
				setSingIn(uid);
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	@Override
	public boolean setSingIn(String uid) {
		try {
			redisDao.set(AppCons.SINGIN_REDIS_KEY + uid, true);
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			System.out.println("获取当前时间:" + DateUtils.getDateStr(cal.getTime(), DateUtils.sft));
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			System.out.println("设置过期时间:" + DateUtils.getDateStr(cal.getTime(), DateUtils.sft));
			redisDao.expire(AppCons.SINGIN_REDIS_KEY + uid, cal.getTime());
			return true;
		} catch (Exception e) {
			System.out.println("REDIS设置失败，系统错误！");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PageInfo signIn(String uid) throws ParseException {
		PageInfo pi = new PageInfo();
		
		if (StringUtils.isBlank(uid)) {
            pi.setCode(ResultInfo.PARAM_MISS.getCode());
			pi.setMsg("登陆超时");
			return pi;
		} 
		
		LingbaoSign singRecord = lingbaoSignMapper.queryAvailableRecord();
		if (null == singRecord) {
            pi.setCode(ResultInfo.EMPTY_DATA.getCode());
			pi.setMsg("暂无签到活动");
			return pi;
		}

		if (isSignInToday(uid)) {
            pi.setResultInfo(ResultInfo.SIGN_SUCCESS);
			return pi;
		}

		// 向签到记录表中插入一条记录。
		LingbaoSignRecord record = new LingbaoSignRecord();
		record.setSignDate(new Date());
		record.setuId(uid);

		// 查询用户最近签到时间，设置连续签到次数
		LingbaoSignRecord oldRecord = lingbaoSignRecordMapper.getLastSingRecord(uid);

		// 连续签到
		if (null != oldRecord) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date1 = sdf.format(oldRecord.getSignDate());
			String date2 = sdf.format(date);
			// 根据日期间隔判断是否连续签到
			if (DateUtils.dateDiff(sdf.parse(date1), sdf.parse(date2)) == 1) {
				record.setSignContinus(oldRecord.getSignContinus() + 1);
			} else {
				record.setSignContinus(1);
			}

		} else {
			record.setSignContinus(1);
		}

		int i = lingbaoSignRecordMapper.insert(record);

		if (i > 0) {
            pi.setCode(ResultInfo.SUCCESS.getCode());
			pi.setMsg("签到成功");
			
			// 保存到redis
			setSingIn(uid);
			
			// 如果送领宝，向领宝记录表插入一条记录。
			if (singRecord.getNum() > 0) {
				// 向领宝记录表插入一条记录
				ActivityRecord ar = new ActivityRecord();
				ar.setAmount(singRecord.getNum());
				ar.setName("签到");
				ar.setContent("签到送领宝");
				ar.setuId(uid);
				ar.setUseDate(new Date());
				ar.setType(0);
				ar.setStatus(1);
				activityRecordMapper.insertSelective(ar);
				// 更新用户账户领宝数量
				UsersAccount userAccount = usersAccountMapper.selectByUid(uid);
				userAccount.setCountLingbao(userAccount.getCountLingbao() + singRecord.getNum());
				usersAccountMapper.updateByPrimaryKeySelective(userAccount);
				System.out.println("赠送领宝：" + singRecord.getNum());
				pi.setObj(singRecord.getNum());
				
				LOG.info("用户" + uid + "签到送领宝成功，获得领宝：" + singRecord.getNum());
			}

		} else {
            pi.setResultInfo(ResultInfo.SIGN_NOT_SUCCESS);

			LOG.error("用户" + uid + "签到送领宝失败，插入领宝记录到数据库失败");
		}

		return pi;
	}

}
