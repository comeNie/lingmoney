package com.mrbt.lingmoney.admin.vo.trading;

import java.net.Proxy;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @descript http链接属性
 * @author luozp
 * @create 2014-5-4
 * @update 2014-5-4
 */
public final class HttpParams {
	
	private static final int CT = 5000;
	private static final int RT = 30000;
	/**
	 * http ConnectTimeout
	 */
	private int connectionTimeout = CT;
	/**
	 * Http RequestMethod
	 */
	private String requestMethod = "GET";
	/**
	 * Http DoOutput
	 */
	private boolean doOutput = true;
	/**
	 * Http DoInput
	 */
	private boolean doInput = true;
	/**
	 * Http UseCaches
	 */
	private boolean useCaches = false;
	/**
	 * Http ReadTimeout
	 */
	private int readTimeout = RT;
	/**
	 * Http RequestProperty
	 */
	private HashMap<String, String> requestProperty = new HashMap<String, String>();
	/**
	 * Http URL
	 */
	private String url = null;
	/**
	 * Http proxy
	 */
	private Proxy proxy = null;
	/**
	 * 返回值得编码
	 */
	private String encode = "UTF-8";

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public boolean isDoOutput() {
		return doOutput;
	}

	public boolean isDoInput() {
		return doInput;
	}

	public boolean isUseCaches() {
		return useCaches;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public HashMap<String, String> getRequestProperty() {
		return requestProperty;
	}

	public String getUrl() {
		return url;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public String getEncode() {
		return encode;
	}

	private HttpParams() {
	}

	/**
	 * 
	 * @descript httpRequestMethod的类型
	 * @author luozp
	 * @create 2014-5-4
	 * @update 2014-5-4
	 */
	public enum HttpRequestMethodEnum {
		GET("GET"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PUT("PUT"), DELETE(
				"DELETE"), TRACE("TRACE");
		String value = "";

		HttpRequestMethodEnum(String value) {
			this.value = value;
		}

		String getValue() {
			return this.value;
		}
	}

	/**
	 * 
	 * @descript httpParam的工厂
	 * @author luozp
	 * @create 2014-5-4
	 * @update 2014-5-4
	 */
	public static class HttpParamsBuilder {
		private HttpParams hp;

		public HttpParamsBuilder() {
			hp = new HttpParams();
		}

		/**
		 * 
		 * @param connectionTimeout	connectionTimeout
		 * @return	return
		 */
		public HttpParamsBuilder connectionTimeout(int connectionTimeout) {
			hp.connectionTimeout = connectionTimeout;
			return this;
		}

		/**
		 * 
		 * @descript 请求类型
		 * @param requestMethod
		 *            支持 GET POST HEAD OPTIONS PUT DELETE TRACE
		 * @return HttpParamsBuilder 工厂类
		 */
		public HttpParamsBuilder requestMethod(
				HttpRequestMethodEnum requestMethod) {
			if (requestMethod == null) {
				requestMethod = HttpRequestMethodEnum.GET;
			}
			hp.requestMethod = requestMethod.getValue();
			return this;
		}

		/**
		 * 
		 * @param doOutput	doOutput
		 * @return	return
		 */
		public HttpParamsBuilder doOutput(boolean doOutput) {
			hp.doOutput = doOutput;
			return this;
		}

		/**
		 * 
		 * @param str	str
		 * @return	return
		 */
		public HttpParamsBuilder encode(String str) {
			hp.encode = str;
			return this;
		}

		/**
		 * 
		 * @param doInput	doInput	
		 * @return		return	
		 */
		public HttpParamsBuilder doInput(boolean doInput) {
			hp.doInput = doInput;
			return this;
		}

		/**
		 * 
		 * @param useCaches	useCaches
		 * @return	return
		 */
		public HttpParamsBuilder useCaches(boolean useCaches) {
			hp.useCaches = useCaches;
			return this;
		}

		/**
		 * 
		 * @param readTimeout	readTimeout
		 * @return return
		 */
		public HttpParamsBuilder readTimeout(int readTimeout) {
			hp.readTimeout = readTimeout;
			return this;
		}

		/**
		 * 
		 * @param key	key
		 * @param value	value
		 * @return	return
		 */
		public HttpParamsBuilder requestProperty(String key, String value) {
			hp.requestProperty.put(key, value);
			return this;
		}

		/**
		 * 
		 * @param url	url
		 * @return	return
		 */
		public HttpParamsBuilder url(String url) {
			hp.url = url.replace(" ", "%20");
			return this;
		}

		/**
		 * @param proxy	proxy
		 * @return	return
		 */
		public HttpParamsBuilder proxy(Proxy proxy) {
			hp.proxy = proxy;
			return this;
		}

		/**
		 * 
		 * @descript build，返回HttpParams实例
		 * @return	return
		 */
		public HttpParams build() {
			if (StringUtils.isBlank(hp.url)) {
				throw new IllegalArgumentException("url地址不能为空");
			}
			return hp;
		}
	}
}
