package com.mrbt.lingmoney.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author yiban sun
 * @date 2016年11月3日 下午3:41:00
 * @version 1.0
 * @description
 * @since
 **/
public class IpToAddressUtils {
	/**
	 * 根据IP地址获取省市（格式化后）
	 * 
	 * @Description
	 * @param ip
	 *            IP或者域名
	 * @param ak
	 *            许可证
	 * @return 省_市_城市代码
	 */
	public static String getIpProvinceAndCity(String ip, String ak) {
		String addressJson = getRs(ip, ak);
		try {
			JSONObject json = JSONObject.fromObject(addressJson);
			if(json == null){
				return null;
			}
			if (json.has("content")) {
				String province = json.getJSONObject("content").getJSONObject("address_detail").getString("province");
				String city = json.getJSONObject("content").getJSONObject("address_detail").getString("city");
				Integer cityCode = json.getJSONObject("content").getJSONObject("address_detail").getInt("city_code");
				return province + "_" + city + "_" + cityCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getLocationProvinceAndCity(double lat, double lng, String ak) {
		String addressJson = getAddressByLocation(lat, lng, ak);
		try {
			JSONObject json = JSONObject.fromObject(addressJson);
			if ((Integer) json.get("status") == 0) {
				String province = json.getJSONObject("result").getJSONObject("addressComponent").getString("province");
				String city = json.getJSONObject("result").getJSONObject("addressComponent").getString("city");
				int cityCode = json.getJSONObject("result").getInt("cityCode");
				return province + "_" + city + "_" + cityCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据域名获取IP
	 *
	 * @Description
	 * @param name
	 *            域名
	 * @return
	 */
	public static String getIP(String name) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(name);
			return address.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据IP或者域名获取地址信息
	 * 
	 * @Description
	 * @param ip
	 *            ip地址或者域名
	 * @param ak
	 *            许可证
	 * @return 反编译后地址信息JSON字符串
	 */
	public static String getRs(String ip, String ak) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			// 如果是域名，先转换为IP
			String args[] = ip.split("\\.");
			if (!NumberUtils.isNumber(args[0])) {
				ip = getIP(ip);
			}
			url = new URL("http://api.map.baidu.com/location/ip?ak=" + ak + "&coor=bd09ll&ip=" + ip);
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoInput(true);// 是否打开输出 true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return decodeUnicode(buffer.toString());

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}

		return null;
	}

	/**
	 * 根据经纬度坐标查询地址
	 * 
	 * @Description
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 * @return
	 */
	public static String getAddressByLocation(double lat, double lng, String ak) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(
					"http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&location=" + lat + "," + lng + "&output=json");
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoInput(true);// 是否打开输出 true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return decodeUnicode(buffer.toString());

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}

		return null;
	}

	/**
	 * unicode转换成中文
	 * 
	 * @Description
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * 根据请求获取IP
	 * 
	 * @Description
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

	public static void main(String[] args) {
		System.out.println(getLocationProvinceAndCity(39.983424, 116.322987, "272PtrfuAbxX6MBFyyeQBde8aq20G2GF"));
		System.out.println(getIpProvinceAndCity("124.126.155.251", "272PtrfuAbxX6MBFyyeQBde8aq20G2GF"));
	}
}
