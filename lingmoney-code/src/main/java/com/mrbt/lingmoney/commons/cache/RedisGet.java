package com.mrbt.lingmoney.commons.cache;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.commons.cache.annotation.OperType;
import com.mrbt.lingmoney.commons.cache.annotation.RedisOper;

/**
 * 缓存获取类
 * @author lzp
 */
@Component
public class RedisGet {
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	RedisDao redisDao;

	/**
	 * spring el表达式解析类
	 */
	private ExpressionParser parser = new SpelExpressionParser();

	/**
	 * 根据cmd命令获取结果
	 * @param oper	操作
	 * @param key	key
	 * @param ec	ec
	 * @return	返回结果
	 */
	public Object getRedisResult(RedisOper oper, String key, EvaluationContext ec) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		if (oper != null) {
			if (oper.cmd() == OperType.HGET) {
				return getRedisHGetResult(oper, key, ec);
			}
			if (oper.cmd() == OperType.ZADD) {
				return null;
			}
		} else if (StringUtils.isNotBlank(key)) {
			return getRedisStringResult(key);
		}
		return null;
	}

	/**
	 * 返回string的key值
	 * @param key	key
	 * @return	返回string的key
	 */
	public Object getRedisStringResult(String key) {
		return redisDao.get(key);
	}

	/**
	 * 获取hset的值
	 * @param oper 操作
	 * @param key	key
	 * @param ec	EvaluationContext
	 * @return	返回值
	 */
	public Object getRedisHGetResult(RedisOper oper, String key, EvaluationContext ec) {
		String setKey = oper.key();
		setKey = parser.parseExpression(setKey).getValue(ec, String.class);
		return redisDao.getHashByHGET(key, setKey);
	}
	
	/**
	 * @param key	key
	 * @return	return
	 */
	public Object getListLastElement(String key) {
		return redisDao.getListLastElement(key);
	}
	
	/**
	 * 
	 * @param key	key
	 * @return	return
	 */
	public Object getListLeftLastElement(String key) {
		return redisDao.getListLeftLastElement(key);
	}
	
	/**
	 * 获取剩余时间
	 * @author YJQ  2017年5月5日
	 * @param key	key
	 * @param timeUnit	timeUnit
	 * @return	剩余时间
	 */
	public Long getRestTime(String key, TimeUnit timeUnit) {
		 return redisDao.getExpire(key, timeUnit);
	}
	
	/**
	 * 
	 * @param key	key
	 * @return	list列表
	 */
	public long getListLength(String key) {
		return redisDao.getListSize(key);
	}
	
	/**
	 * 
	 * @param key	key
	 * @return	return
	 */
	public BoundHashOperations<String, Object, HashMap<?, ?>> getSessionValue(String key) {
		return redisDao.getHashValue(key);
	}
}
