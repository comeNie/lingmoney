package com.mrbt.lingmoney.youmengpush;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.utils.MyUtils;
import com.mrbt.lingmoney.utils.ResultParame.ResultInfo;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class PushClient {
	private Logger log = MyUtils.getLogger(PushClient.class);

	// The user agent
	protected static final String USER_AGENT = "Mozilla/5.0";

	// This object is used for sending the post request to Umeng
	protected HttpClient client = new DefaultHttpClient();

	// The host
	protected static final String HOST = "http://msg.umeng.com";

	// The upload path
	protected static final String UP_LOAD_PATH = "/upload";

	// The post path
	protected static final String POST_PATH = "/api/send";
	
	protected static final int TIME_LONG = 1000;

	/**
	 * 
	 * @param msg	msg
	 * @return	return
	 * @throws Exception	Exception
	 */
	public boolean send(UmengNotification msg) throws Exception {
		String timestamp = Integer
				.toString((int) (System.currentTimeMillis() / TIME_LONG));
		msg.setPredefinedKeyValue("timestamp", timestamp);
		String url = HOST + POST_PATH;
		String postBody = msg.getPostBody();
		String sign = DigestUtils
				.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret())
						.getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		int status = response.getStatusLine().getStatusCode();
		log.info("Response Code : " + status);
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info(result.toString());
		if (status == ResultInfo.SUCCESS.getCode()) {
			log.info("Notification sent successfully.");
			return true;
		} else {
			log.error("Failed to send the notification!");
			return false;
		}
	}

	// Upload file with device_tokens to Umeng
	/**
	 * 
	 * @param appkey	appkey
	 * @param appMasterSecret	appMasterSecret
	 * @param contents	contents
	 * @return	return
	 * @throws Exception	Exception
	 */
	public String uploadContents(String appkey, String appMasterSecret,
			String contents) throws Exception {
		// Construct the json string
		JSONObject uploadJson = new JSONObject();
		uploadJson.put("appkey", appkey);
		String timestamp = Integer
				.toString((int) (System.currentTimeMillis() / TIME_LONG));
		uploadJson.put("timestamp", timestamp);
		uploadJson.put("content", contents);
		// Construct the request
		String url = HOST + UP_LOAD_PATH;
		String postBody = uploadJson.toString();
		String sign = DigestUtils.md5Hex(
				("POST" + url + postBody + appMasterSecret).getBytes("utf8"));
		url = url + "?sign=" + sign;
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		StringEntity se = new StringEntity(postBody, "UTF-8");
		post.setEntity(se);
		// Send the post request and get the response
		HttpResponse response = client.execute(post);
		log.info("Response Code : " + response.getStatusLine().getStatusCode());
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		log.info(result.toString());
		// Decode response string and get file_id from it
		JSONObject respJson = JSONObject.fromObject(result.toString());
		String ret = respJson.getString("ret");
		if (!ret.equals("SUCCESS")) {
			throw new Exception("Failed to upload file");
		}
		JSONObject data = respJson.getJSONObject("data");
		String fileId = data.getString("file_id");
		// Set file_id into rootJson using setPredefinedKeyValue

		return fileId;
	}

}
