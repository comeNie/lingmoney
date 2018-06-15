package com.mrbt.lingmoney.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.net.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * wap端接口测试工具类
 * @author Administrator
 *
 */
public class PostTest {
	
	private static final Logger LOGGER = LogManager.getLogger(PostTest.class);

	/**
	 * 访问服务器地址
	 */
	private static final String URL_HEARD = "http://10.10.12.76:8080";
//	private static final String URL_HEARD = "http://www2.lingmoney.cn";
	
	private static final PostTest pt = new PostTest();
	private static final Map<String, Object> params = new HashMap<String, Object>();
	private static final Map<String, Object> headers = new HashMap<String, Object>();
	private static String url = "";

	public static void main(String[] args) {
//		headers.put("Cookie", "JSESSIONID=14FE72C9E27FA4D15366F74B59B145DD");
		
//		pt.login();
//		pt.loginOut();
//		pt.popularActivityList();
//		pt.onlineCallBack();
	}
	
	public void onlineCallBack() {
//		url = URL_HEARD + "/purchase/onlineNotity/2974_28557_0FB0CBBA4BBBD06CB5D84F8E3683D586";
//		url = URL_HEARD + "/purchase/onlineCallBank/2974_28557_0FB0CBBA4BBBD06CB5D84F8E3683D586";
		url = URL_HEARD + "/purchase/quickNotice/8bea49a87a41a1e521515387725632_17370_17370_0FB0CBBA4BBBD06CB5D84F8E3683D586";
		params.put("v_oid", "20180108-110226189002-0961519ef4acb09861515366069109");
		params.put("v_pstatus", 20);
		params.put("v_amount", 1.00);
		params.put("v_md5str", "AE6662E26BCF0A66F53D4FAFEDAA636E");
		pt.resquestPost(url, params);
	}
	
	/**
	 * 热门活动列表
	 */
	public void popularActivityList() {
		url = URL_HEARD + "/activity/activityList";
		params.put("pageNo", 1);
		params.put("pageSize", 10);
		pt.resquestPost(url, params);
	}
	
	/**
	 * 用户退出
	 */
	public void loginOut() {
		url = URL_HEARD + "/users/logout";
		pt.resquestPost(url, params);
	}
	/**
	 * 用户登录
	 */
	public void login() {
		url = URL_HEARD + "/users/login";
		params.put("account", "13683173295");
		params.put("password", "000000");
		pt.resquestPost(url, params);
	}

	/**
	 * 发送url请求
	 * @param url
	 * @param paramsJson
	 */
	private void resquestPost(String url, Map<String, Object> params) {
		String reString;
		try {
			reString = HttpClientUtil.httpPostRequest(url, headers, params);
			System.out.println(reString);
//			System.out.println(formatJson(reString));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 格式化 json
	 * @param jsonStr	json字符串
	 * @return	字符串
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr))
			return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				indent++;
				addIndentBlank(sb, indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent--;
				addIndentBlank(sb, indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\') {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}
		return sb.toString();
	}

	/**
	 * 添加space
	 * @param sb
	 * @param indent
	 * @author lizhgb
	 * @Date 2015-10-14 上午10:38:04
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
	}
}

