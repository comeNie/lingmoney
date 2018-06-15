package com.mrbt.lingmoney.service.pay.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.UsersBankMapper;
import com.mrbt.lingmoney.model.UsersBank;
import com.mrbt.lingmoney.service.pay.UsersBankService;

/** 
 * @author  suyibo 
 * @date 创建时间：2018年1月2日
 */
@Service
public class UsersBankServiceImpl implements UsersBankService {
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private UsersBankMapper usersBankMapper;

	/**
	 * 判断redis中身份证号是否存在
	 * 
	 * @param idCard
	 *            身份证号
	 * @param type
	 *            类型 1发送验证码 2提交
	 * @return
	 */
	@Override
	public boolean checkBankRedis(String idCard, Integer type) {
		boolean flag = false;
		if (type == 1) {// 验证码
			try {
				// 查询redis
				String resultStr = (String) redisDao.get(idCard + "_1");
				// 如果存在，则false
				if (resultStr != null && !resultStr.equals("")) {
					flag = false;
				} else {
					// 不存在，则放入redis，过期时间1分钟
					redisDao.set(idCard + "_1", idCard + "_1");
					redisDao.expire(idCard + "_1", 1, TimeUnit.MINUTES);
					flag = true;
				}
			} catch (Exception e) {

			}
		} else if (type == 2) {// 提交
			try {
				// 查询redis
				String resultStr = (String) redisDao.get(idCard + "_2");
				// 如果存在，则false
				if (resultStr != null && !resultStr.equals("")) {
					flag = false;
				} else {
					// 不存在，则放入redis，过期时间2分钟
					redisDao.set(idCard + "_2", idCard + "_2");
					redisDao.expire(idCard + "_2", 2, TimeUnit.MINUTES);
					flag = true;
				}
			} catch (Exception e) {

			}
		}
		return flag;
	}

	/**
	 * 清除身份证号redis
	 * 
	 * @param idCard
	 *            身份证号
	 * @param type
	 *            类型 1发送验证码 2提交
	 * @return
	 */
	@Override
	public void deleteBankRedis(String idCard, Integer type) {
		if (type == 1) {// 验证码
			try {
				// 清除redis
				redisDao.delete(idCard + "_1");
			} catch (Exception e) {

			}
		} else if (type == 2) {// 提交
			try {
				// 清除redis
				redisDao.delete(idCard + "_2");
			} catch (Exception e) {

			}
		}
	}

	@Override
	public UsersBank findByUserBankId(Integer parseInt) {
		return usersBankMapper.findByUserBankId(parseInt);
	}

}
