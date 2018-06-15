package com.mrbt.lingmoney.service.wechat.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信签名
 *
 */
public class WechatSign {
    protected WechatSign() {
    }

    //	public static void main(String[] args) {
    //		String jsapi_ticket = "jsapi_ticket";
    //
    //		// 注意 URL 一定要动态获取，不能 hardcode
    //		String url = "http://example.com";
    //		Map<String, String> ret = sign(jsapi_ticket, url);
    //		for (Map.Entry entry : ret.entrySet()) {
    //			System.out.println(entry.getKey() + ", " + entry.getValue());
    //		}
    //	};

    /**
     * 签名
     * 
     * @param jsapiTicket 
     * @param url 
     * @return 签名信息map
     *
     */
	public static Map<String, String> sign(String jsapiTicket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

    /**
     * 将byte数组格式化为两位字符串，不足两位前补零
     * 
     * @param hash 
     * @return 转化结果
     *
     */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

    /**
     * 获取uuid
     * @return 不带“-”的uuid
     *
     */
	private static String createNonceStr() {
		return UUID.randomUUID().toString().replace("-", "");
	}

    /**
     * 获取系统当前时间（秒）
     * @return 当前时间，string类型
     *
     */
    private static String createTimestamp() {
        final int second = 1000; // 转换单位
        return Long.toString(System.currentTimeMillis() / second);
	}
}
