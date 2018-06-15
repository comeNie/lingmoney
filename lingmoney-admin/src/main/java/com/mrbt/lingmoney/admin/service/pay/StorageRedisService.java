package com.mrbt.lingmoney.admin.service.pay;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.admin.vo.trading.NotifyObject;
import com.mrbt.lingmoney.admin.vo.trading.PayVo;
import com.mrbt.lingmoney.commons.cache.RedisDao;

/**
 * 
 * StorageRedisService
 *
 */
@Service
public class StorageRedisService {
    @Autowired
	private RedisDao redisDao;
    
	/**
	 * saveNofityToRedis
	 * 
	 * @param object
	 *            NotifyObject
	 */
    public void saveNofityToRedis(NotifyObject object) {
    	redisDao.setList(PayVo.ReceivePayNotifyList, object);
    }
    
	/**
	 * getNofityFromRedis
	 * 
	 * @return 数据返回
	 */
    public NotifyObject getNofityFromRedis() {
		//存储到处理中队列中
		return (NotifyObject) redisDao.getListObject(PayVo.ReceivePayNotifyList);
    }
    
	/**
	 * getNofityFromRedis
	 * 
	 * @param key
	 *            key
	 * @return 数据返回
	 */
    public Object getNofityFromRedis(String key) {
		//存储到处理中队列中
		return redisDao.get(key);
    }
    
	/**
	 * del
	 * 
	 * @param key
	 *            key
	 */
    public void del(String key) {
    	//存储到处理中队列中
    	redisDao.delete(key);
    }
    
	/**
	 * save
	 * 
	 * @param key
	 *            key
	 * @param objec
	 *            objec
	 */
	public void save(String key, Object objec) {
    	//存储到处理中队列中
    	redisDao.set(key, objec);
    }
    
	/**
	 * getProObjectList
	 * 
	 * @param key
	 *            key
	 * @return 数据返回
	 */
    public Set getProObjectList(String key) {
		//存储到处理中队列中
		return redisDao.findKeys(key);
    }

}
