package com.mrbt.lingmoney.youmengpush.ios;

/**
 * 
 * @author Administrator
 *
 */
public class IOSListcast extends IOSNotification {

	/**
	 * 
	 * @param appkey
	 * @param appMasterSecret
	 * @throws Exception
	 */
	public IOSListcast(String appkey, String appMasterSecret) throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}
	
	/**
	 * 
	 * @param token	token
	 * @throws Exception	Exception
	 */
	public void setDeviceToken(String token) throws Exception {
		setPredefinedKeyValue("device_tokens", token);
	}
}
