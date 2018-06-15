package com.mrbt.lingmoney.admin.service.schedule.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.schedule.AutoBuyRankingService;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;
import com.mrbt.lingmoney.mapper.ScheduleLogMapper;
import com.mrbt.lingmoney.mapper.TradingMapper;
import com.mrbt.lingmoney.mapper.UsersAccountMapper;
import com.mrbt.lingmoney.model.ActivityRecord;
import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.UsersAccount;
import com.mrbt.lingmoney.model.schedule.ScheduleLog;
import com.mrbt.lingmoney.utils.DateUtils;
import com.mrbt.lingmoney.utils.ResultParame;

/**
 *@author syb
 *@date 2017年5月18日 下午1:39:30
 *@version 1.0
 *@description 
 **/
@Service
public class AutoBuyRankingServiceImpl implements AutoBuyRankingService {
	private static final Logger LOG = LogManager.getLogger(AutoBuyRankingServiceImpl.class);
	@Autowired
	private ScheduleLogMapper scheduleLogMapper;
	@Autowired
	private TradingMapper tradingMapper;
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private ActivityRecordMapper activityRecordMapper;
	
	@Override
	public void monthlySendLingbao() {
		//logger.info("执行每月赠送领宝定时器，每月运行一次，执行时间为："  + DateUtils.getDtStr(new Date(),DateUtils.sft));;
		LOG.info("执行每月赠送领宝定时器，每月运行一次，执行时间为：" + DateUtils.getDateStr(new Date(), DateUtils.sft));
		ScheduleLog log = new ScheduleLog();
		log.setExecuteTime(new Date());
		log.setDescription("每月赠送领宝定时器开始执行");
		scheduleLogMapper.insertSelective(log);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, ResultParame.ResultNumber.MINUS_ONE.getNumber());
		String dateStr = DateUtils.getDtStr(calendar.getTime(), DateUtils.sfM);
		//查询每月第一个购买用户
		Trading tradingFirst = tradingMapper.selectMonthlyFirstBuyer(dateStr);
		if (tradingFirst != null) {
			//如果存在，更新用户账户领宝
			UsersAccount account = usersAccountMapper.selectByUid(tradingFirst.getuId());
			account.setCountLingbao(account.getCountLingbao() + ResultParame.ResultNumber.FIFTY.getNumber());
			usersAccountMapper.updateByPrimaryKeySelective(account);
			//插入一条领宝兑换记录
			ActivityRecord record = new ActivityRecord();
			record.setAmount(ResultParame.ResultNumber.FIFTY.getNumber());
			record.setuId(tradingFirst.getuId());
			record.settId(tradingFirst.getId());
			record.setName("月奖励");
			record.setContent("在" + dateStr + "您是第一个购买产品，特奖励领宝");
			activityRecordMapper.insertSelective(record);
		}

		//查询每月最后一个购买用户
		Trading tradingLast = tradingMapper.selectMonthlyLastBuyer(dateStr);
		if (tradingLast != null) {
			//如果存在，更新用户账户领宝
			UsersAccount account = usersAccountMapper.selectByUid(tradingLast.getuId());
			account.setCountLingbao(account.getCountLingbao() + ResultParame.ResultNumber.FIFTY.getNumber());
			usersAccountMapper.updateByPrimaryKeySelective(account);
			//插入一条领宝兑换记录
			ActivityRecord record = new ActivityRecord();
			record.setAmount(ResultParame.ResultNumber.FIFTY.getNumber());
			record.setuId(tradingLast.getuId());
			record.settId(tradingLast.getId());
			record.setName("月奖励");
			record.setContent("在" + dateStr + "您是最后一个购买产品，特奖励领宝");
			activityRecordMapper.insertSelective(record);
		}

		//月购买总额前三名赠送领宝
		List<Trading> result = tradingMapper.selectMonthlyBuyTopThree(dateStr);
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				Trading trading = result.get(i);
				//如果存在，更新用户账户领宝
				UsersAccount account = usersAccountMapper.selectByUid(trading.getuId());
				account.setCountLingbao(account.getCountLingbao() + ResultParame.ResultNumber.FIFTY.getNumber());
				usersAccountMapper.updateByPrimaryKeySelective(account);
				//插入一条领宝兑换记录
				ActivityRecord record = new ActivityRecord();
				record.setAmount(ResultParame.ResultNumber.FIFTY.getNumber());
				record.setuId(trading.getuId());
				record.settId(trading.getId());
				record.setName("月奖励");
				record.setContent("在" + dateStr + "您是购买产品总额为第" + (i + 1) + "名，特奖励领宝");
				activityRecordMapper.insertSelective(record);
			}
		}
		
		ScheduleLog logEnd = new ScheduleLog();
		logEnd.setExecuteTime(new Date());
		logEnd.setDescription("每月赠送领宝定时器执行完毕");
		scheduleLogMapper.insertSelective(logEnd);
		LOG.info("执行每月赠送领宝定时器，执行结束时间为：" + DateUtils.getDtStr(new Date(), DateUtils.sft));
	}
	
}
