package com.mrbt.lingmoney.service.common.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.commons.exception.PageInfoException;
import com.mrbt.lingmoney.mapper.HxAccountMapper;
import com.mrbt.lingmoney.model.HxAccount;
import com.mrbt.lingmoney.model.HxAccountExample;
import com.mrbt.lingmoney.service.common.UtilService;
import com.mrbt.lingmoney.service.users.VerifyService;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 * 工具类
 */
@Service
public class UtilServiceImpl implements UtilService {

	@Autowired
	private VerifyService verifyService;

	@Autowired
	private HxAccountMapper hxAccountMapper;

	@Autowired
	private RedisGet redisGet;

	@Autowired
	private RedisSet redisSet;

	@Override
	public String queryUid(String key) throws Exception {
		Object uidRedis = redisGet.getRedisStringResult(key);
		verifyService.verifyUserId(uidRedis);
		return uidRedis.toString();
	}

	@Override
	public Integer dealLoginError(String key, Integer times, String item) throws Exception {
		Object errCount = redisGet.getRedisStringResult(key);
		if (StringUtils.isEmpty(errCount) || !(errCount instanceof Integer)) {
			errCount = 0;
		}
		// +1操作
		Integer reErrCount = (Integer) errCount + 1;

		if (reErrCount >= times) {
			// 如果本次输错后已经大于等于times 那么限定时间为4小时 再次登录需此条数据失效
            final int overTime = 4; //四小时超时
            redisSet.setRedisStringResult(key, reErrCount, overTime, TimeUnit.HOURS);
            throw new PageInfoException("您的" + item + "已锁定，请您4小时后重新登录!", ResultInfo.RE_EMAIL.getCode());
		}
		redisSet.setRedisStringResult(key, reErrCount);

		return reErrCount;
	}

	@Override
	public HxAccount queryUserHxAccount(String uId) throws Exception {
		HxAccountExample accEx = new HxAccountExample();
		accEx.createCriteria().andUIdEqualTo(uId).andStatusEqualTo(ResultNumber.ONE.getNumber());
		List<HxAccount> accLi = hxAccountMapper.selectByExample(accEx);
		if (StringUtils.isEmpty(accLi) || accLi.size() == 0) {
            throw new PageInfoException("请开通华兴E账户", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		HxAccount acc = accLi.get(0);
        if (StringUtils.isEmpty(acc.getAcNo())) {
            throw new PageInfoException("华兴E账户账号为空", ResultInfo.NOT_HX_ACCOUNT.getCode());
		}
		return acc;
	}
}
