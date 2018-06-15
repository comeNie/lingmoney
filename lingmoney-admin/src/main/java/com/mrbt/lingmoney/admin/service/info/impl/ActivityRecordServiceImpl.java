package com.mrbt.lingmoney.admin.service.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.info.ActivityRecordService;
import com.mrbt.lingmoney.mapper.ActivityRecordMapper;

/**
 * 领宝消耗记录
 * 
 */
@Service
public class ActivityRecordServiceImpl implements ActivityRecordService {
	@Autowired
	private ActivityRecordMapper activityRecordMapper;

	/**
	 * 查询
	 */
	@Override
	public int selectCountByTid(int tId) {
		return activityRecordMapper.selectCountByTid(tId);
	}
}
