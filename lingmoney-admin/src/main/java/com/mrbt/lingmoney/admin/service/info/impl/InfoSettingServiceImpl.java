package com.mrbt.lingmoney.admin.service.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.service.info.InfoSettingService;
import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.utils.PageInfo;

/**
 * 页面设置——》小功能设置
 *
 */
@Service
public class InfoSettingServiceImpl implements InfoSettingService {
	private static String crabcountRedisKey = "crabcount";
	@Autowired
	private RedisDao redisDao;

	/**
	 * 设置赠送螃蟹个数
	 */
	@Override
	public void setCrabCount(Integer crabCount) {
		redisDao.set(crabcountRedisKey, crabCount);
	}

	/**
	 * 后台展示赠送螃蟹个数
	 */
	@Override
	public PageInfo showCrabCount() {
		PageInfo pageInfo = new PageInfo();
		int crabCount = 0;
		if (redisDao.hasKey(crabcountRedisKey) && (Integer) redisDao.get(crabcountRedisKey) > 0) {
			crabCount = (Integer) redisDao.get(crabcountRedisKey);
		}
		pageInfo.setObj(crabCount);
		return pageInfo;
	}
}
