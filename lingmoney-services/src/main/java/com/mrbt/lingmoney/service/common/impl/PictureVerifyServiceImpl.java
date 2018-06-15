package com.mrbt.lingmoney.service.common.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.service.common.PictureVerifyService;
import com.mrbt.lingmoney.utils.PageInfo;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 图片验证码
 */
@Service
public class PictureVerifyServiceImpl implements PictureVerifyService {
	
	@Autowired
	private RedisDao redisDao;
	
	private static String keyHeard = "picVer_";

	@Override
	public void save(String picKey, String code) {
		code = code.toUpperCase();
		redisDao.set(keyHeard + picKey, code);
        final int keyOverTime = 300; //超时时间为300秒
        redisDao.expire(keyHeard + picKey, keyOverTime, TimeUnit.SECONDS);
	}

	@Override
	public void varifyPicCode(String picKey, String code, PageInfo pageInfo) {
        if (varifyCode(picKey, code, redisDao, 0)) {
            pageInfo.setCode(ResultInfo.SUCCESS.getCode());
			pageInfo.setMsg("sucess");
			pageInfo.setObj(true);
        } else {
            pageInfo.setCode(ResultInfo.VERIFICATION_CODE_ERROR.getCode());
			pageInfo.setMsg("验证码错误或失效");
			pageInfo.setObj(false);
		}
	}
	
	/**
	 * 验证图片验证码公共方法
	 * @param picKey key
	 * @param picCode 验证码
	 * @param redis redis
	 * @param n	0只验证，1验证后删除KEY，发短信调用
	 * @return 通过true 不通过false
	 */
    public static boolean varifyCode(String picKey, String picCode, RedisDao redis, int n) {
		picCode = picCode.toUpperCase();
        if (redis.get(keyHeard + picKey) != null) {
            if (picCode.equals(redis.get(keyHeard + picKey))) {
                if (n > 0) {
					redis.delete(keyHeard + picKey);
				}
				return true;
			}
		}
		return false;
	}

}
