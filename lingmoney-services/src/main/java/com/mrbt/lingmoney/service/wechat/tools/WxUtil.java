package com.mrbt.lingmoney.service.wechat.tools;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.commons.cache.RedisDao;
import com.mrbt.lingmoney.utils.PropertiesUtil;
import com.mrbt.lingmoney.wechat.model.TicketJson;
import com.mrbt.lingmoney.wechat.model.TokenJson;

/**
 * @author Allen
 * @version 1.0 创建时间：2016年4月12日 下午4:28:41
 */
@Component
public class WxUtil {
    protected WxUtil() {
    }

	// 此处的appid与wx.config 参数appId一致 微信公众账号提供给开发者的信息，以下同理
    public static final String APPID = PropertiesUtil.getPropertiesByKey("appId");
    public static final String SECRET = PropertiesUtil.getPropertiesByKey("appSecret");

	//分享的域名
    public static final String WECHAT_DOMAIN = PropertiesUtil.getPropertiesByKey("wechat_domain");
	
	//获取token的url
    private static final String WECHAT_TOKEN_URL = PropertiesUtil.getPropertiesByKey("wechat_token_url");
	//获取ticket的url
    private static final String WECHAT_TICKET_URL = PropertiesUtil.getPropertiesByKey("wechat_ticket_url");
	
	

    /**
     * 获取token
     * 
     * @author yiban
     * @date 2017年12月8日 上午11:59:33
     * @version 1.0
     * @return TokenJson
     *
     */
    public static TokenJson getAccessToken() {

		String url = WECHAT_TOKEN_URL + APPID + "&secret=" + SECRET;
		try {
			String result = HttpGetRequest.doGet(url);
			JSONObject rqJsonObject = JSONObject.fromObject(result);
			TokenJson tokenJson = (TokenJson) JSONObject.toBean(rqJsonObject, TokenJson.class);
			return tokenJson;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    /**
     * 获取ticket
     * 
     * @param token 
     * @return TicketJson
     *
     */
	public static TicketJson getTicket(String token) {
		String url = WECHAT_TICKET_URL + token + "&type=jsapi";
		try {
			String result = HttpGetRequest.doGet(url);
			JSONObject rqJsonObject = JSONObject.fromObject(result);
			TicketJson ticket = (TicketJson) JSONObject.toBean(rqJsonObject, TicketJson.class);
			return ticket;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取js sdk 认证信息
	 * 
	 * @author
	 * @date 创建时间 2016年7月28日 上午11:25:01
	 * @param url 
	 * @param redisDao 
	 * @return 计算后的签名map
	 */
	public static Map<String, String> getSign(String url, RedisDao redisDao) {
        final int second = 7200;
        final int errorCode = 40001;

		String token = "";
		if (redisDao.get("wxAccess_token") == null) {
            TokenJson tokenJson = getAccessToken();
			token = tokenJson.getAccess_token();
			redisDao.set("wxAccess_token", token);
            redisDao.expire("wxAccess_token", second, TimeUnit.SECONDS);
		} else {
			token = redisDao.get("wxAccess_token").toString();
		}

		TicketJson ticketJson = getTicket(token);
        if (ticketJson.getErrcode() == errorCode) {
            TokenJson tokenJson = getAccessToken();
			token = tokenJson.getAccess_token();
			redisDao.set("wxAccess_token", token);
            redisDao.expire("wxAccess_token", second, TimeUnit.SECONDS);
			ticketJson = getTicket(token);
		}

		Map<String, String> ret = WechatSign.sign(ticketJson.getTicket(), url);
		return ret;
	}

	public static void main(String[] args) {
		getAccessToken();
	}

}
