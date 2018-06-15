package com.mrbt.lingmoney.service.wechat;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.mapper.UsersMapper;
import com.mrbt.lingmoney.mapper.UsersPropertiesMapper;
import com.mrbt.lingmoney.model.Users;
import com.mrbt.lingmoney.model.UsersExample;
import com.mrbt.lingmoney.model.UsersExample.Criteria;
import com.mrbt.lingmoney.model.UsersProperties;
import com.mrbt.lingmoney.service.wechat.tools.WxUtil;
import com.mrbt.lingmoney.utils.MD5Utils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

/**
 * 微信
 *
 */
@Service
public class WechatService {
	
	@Autowired
	private RedisDao redisDao;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private UsersPropertiesMapper usersPropertiesMapper;
	
    /**
     * 根据url获取签名
     * @param url 请求url
     * @return 签名后数据
     *
     */
	public Map<String, String> wechatChareUrl(String url) {
		Map<String, String> map = WxUtil.getSign(url, redisDao);
		return map;
	}

    /**
     * 
     * 查询用户推荐码
     * @param loginAccount 登录账户
     * @param telephone 手机号
     * @param loginPsw 登录密码
     * @return json{code:'错误码',msg:'错误信息'}
     *
     */
	public JSONObject queryUserRecomCode(String loginAccount, String telephone, String loginPsw) {
		JSONObject json = new JSONObject();
		
		//判断用户是否存在
		UsersExample example = new UsersExample();
		Criteria cri = example.createCriteria();
		cri.andLoginAccountEqualTo(loginAccount);
		
		Criteria cri2 = example.createCriteria();
		cri2.andTelephoneEqualTo(telephone);
		
		example.or(cri2);
		
		List<Users> users = usersMapper.selectByExample(example);
        final int errorCode = 300;
        if (users != null && users.size() > 0) {
            if (users.get(0).getLoginPsw().equals(MD5Utils.MD5(loginPsw))) {
                //获取用户的推荐码
                UsersProperties up = usersPropertiesMapper.selectByuId(users.get(0).getId());
                json.put("code", ResultInfo.SUCCESS.getCode());
                json.put("msg", up.getReferralCode());
            } else {
                json.put("code", errorCode);
                json.put("msg", "密码错误");
            }
        } else {
            json.put("code", errorCode);
            json.put("msg", "用户不存在");
        }
		return json;
	}

}
