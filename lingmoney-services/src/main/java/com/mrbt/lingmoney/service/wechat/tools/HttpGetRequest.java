package com.mrbt.lingmoney.service.wechat.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Allen
 * @version 1.0 创建时间：2016年4月12日 下午4:30:23
 */
public class HttpGetRequest {
    protected HttpGetRequest() {
    }
	
	/**
	 * Get Request
	 * 
	 * @param url 
	 * @return 请求的返回值
	 * @throws Exception 
	 */
	public static String doGet(String url) throws Exception {
        final int failCode = 300; // 请求失败返回码

		URL localURL = new URL(url);
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/text");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

        if (httpURLConnection.getResponseCode() >= failCode) {
			throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
		}
		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}

}
