package com.mrbt.lingmoney.commons.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.commons.cache.annotation.OperType;
import com.mrbt.lingmoney.commons.cache.annotation.RedisOper;
import java.util.Date;
/**
 * 缓存保存类
 * 
 * @author lzp
 *
 */
@Component
public class RedisSet {
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	RedisDao redisDao;

	/**
	 * spring el表达式解析类
	 */
	private ExpressionParser parser = new SpelExpressionParser();
	
	/**
	 * 设置
	 * @param oper	操作
	 * @param key	key
	 * @param ec	ec
	 * @param result res
	 * @return	返回结果
	 */
	public boolean setRedisResult(RedisOper oper, String key,
			EvaluationContext ec, Object result) {
		if (oper != null) {
			if (oper.cmd() == OperType.HSET) {
				return setRedisHSetResult(oper, key, ec, result);
			}
			if (oper.cmd() == OperType.ZADD) {
				return setRedisZAddResult(oper, key, ec, result);
			}
		} else if (StringUtils.isNotBlank(key)) {
			return setRedisStringResult(key, result);
		}
		return false;
	}

	/**
	 * 设置key的值
	 * @param key	key
	 * @param result	result
	 * @return return
	 */
	public boolean setRedisStringResult(String key, Object result) {
		return redisDao.set(key, result);
	}
	
	
	/**
	 * 存入Object
	 * @author YJQ  2017年4月13日
	 * @param key	key
	 * @param result	result
	 * @param timeout 时间
	 * @param timeUinit 单位
	 * @return	return
	 */
	public boolean setRedisStringResult(String key, Object result, long timeout, TimeUnit timeUinit) {
		return redisDao.set(key, result) && redisDao.expire(key, timeout, timeUinit);
	}

	
	/**
	 * 存入list
	 * @author YJQ  2017年4月13日
	 * @param key	key
	 * @param list	list
	 * @return	return
	 */
	public boolean setList(String key, List<Object> list) {
		return redisDao.setList(key, list);
	}
	
	
	/**
	 * 存入list
	 * @author YJQ  2017年4月13日
	 * @param key	key
	 * @param list	list
	 * @param timeout	timeout
	 * @param timeUnit	timeUnit
	 * @return	return
	 */
	public boolean setList(String key, List<Object> list, long timeout, TimeUnit timeUnit) {
		return redisDao.setList(key, list) && redisDao.expire(key, timeout, timeUnit);
	}
	
	/**
	 * 添加list元素[rightpush][不允许重复]
	 * @author YJQ  2017年4月13日
	 * @param key	key
	 * @param obj	obj
	 * @return	return
	 */
	public boolean setListElement(String key, Object obj) {
		// 如果有重复 先删掉 
		redisDao.deleteByIndex(key, 0, obj);
		// 如果有空值 先删掉 
		redisDao.deleteByIndex(key, 0, null);
		// 如果有空值 先删掉 
		redisDao.deleteByIndex(key, 0, "");
		return redisDao.setListElement(key, obj);
	}
	
	/**
	 * 添加list元素
	 * @author YJQ  2017年4月13日
	 * @param key	key
	 * @param obj	obj
	 * @param timeout	timeout
	 * @param timeUnit	timeUnit
	 * @return	return
	 */
	public boolean setListElement(String key, Object obj, long timeout, TimeUnit timeUnit) {
		return redisDao.setListElement(key, obj) && redisDao.expire(key, timeout, timeUnit);
	}
	
	/**
	 * 设置HSet结果
	 * @param oper	oper
	 * @param key	key
	 * @param ec	ec
	 * @param result	result
	 * @return return
	 */
	public boolean setRedisHSetResult(RedisOper oper, String key, EvaluationContext ec, Object result) {
		String setKey = oper.key();
		setKey = parser.parseExpression(setKey).getValue(ec, String.class);
		return redisDao.setHashByHSET(key, setKey, result);
	}

	/**
	 * 有序集合的zadd操作
	 * @param oper	oper
	 * @param key	key
	 * @param ec	ec
	 * @param result	result
	 * @return		return	
	 */
	public boolean setRedisZAddResult(RedisOper oper, String key, EvaluationContext ec, Object result) {
		String score = oper.score();
		if (StringUtils.isNotBlank(score)) {
			Long scoreVal = parser.parseExpression(score).getValue(ec,
					Long.class);
			return redisDao.ZAdd(key, scoreVal, result);
		}
		return false;
	}
	
	/**
	 * 设置redis超时（单位时间）
	 * @param key	key
	 * @param timeout	timeout
	 * @param timeUinit	timeUinit
	 * @return	return
	 */
	public boolean expire(String key, long timeout, TimeUnit timeUinit) {
		return redisDao.expire(key, timeout, timeUinit);
	}
	
	/**
	 * 设置redis超时（指定时间）
	 * @param key	key
	 * @param dt	dt
	 * @return	return
	 */
	public boolean expire(String key, Date dt) {
		return redisDao.expire(key, dt);
	}
}
