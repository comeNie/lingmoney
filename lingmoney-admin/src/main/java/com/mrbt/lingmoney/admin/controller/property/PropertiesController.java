package com.mrbt.lingmoney.admin.controller.property;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.commons.cache.RedisGet;
import com.mrbt.lingmoney.commons.cache.RedisSet;
import com.mrbt.lingmoney.utils.AppCons;
import com.mrbt.lingmoney.utils.ResultParame.ResultNumber;

/**
 *@author syb
 *@date 2017年6月20日 下午5:54:02
 *@version 1.0
 *@description 配置属性控制器 
 **/
@Controller
@RequestMapping("/properties")
public class PropertiesController {
	
	private static final Logger LOGGER = LogManager.getLogger(PropertiesController.class);
	 
	private static final String RECHARGE_LIMIT = "RECHARGE_LIMIT";

	
	@Autowired
	private RedisGet redisGet;
	
	@Autowired
	private RedisSet redisSet;
	
	@Autowired
	private RedisDao redisDao;
	
	/**
	 * 查询京东绑卡通道开关
	 * @return 返回
	 */
	@RequestMapping("/queryJDBindCardSwitch")
	public @ResponseBody String queryJDBindCardSwitch() {
		
		String key = AppCons.JD_BINDCARD_AVALIABLE;
		
		// 0关，1开
		String result = "N";
		if (!StringUtils.isEmpty(redisGet.getRedisStringResult(key))) {
			result = (String) redisGet.getRedisStringResult(key);
		}
		
		LOGGER.info("查询京东绑卡开关：" + result);
		
		return result;
	}
	
	/**
	 * 更新京东绑卡通道开关
	 * @param type	类型
	 * @return	结果
	 */
	@RequestMapping("/updateJDBindCardSwitch")
	public @ResponseBody String queryJDBindCardSwitch(String type) {
		
		LOGGER.info("修改京东绑卡开关：" + type);
		
		String key = AppCons.JD_BINDCARD_AVALIABLE;
		
		try {
			redisSet.setRedisStringResult(key, type);
			redisSet.expire(key, ResultNumber.EIGHT.getNumber(), TimeUnit.HOURS);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	/**
	 * 充值最小金额设置
	 * @param amount 金额
	 * @return 返回
	 */
	@RequestMapping("/setMinimumAmount")
	public @ResponseBody String setMinimumAmount(double amount) {
		
		try {
			redisDao.set(RECHARGE_LIMIT, amount);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
	
}
