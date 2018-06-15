package com.mrbt.lingmoney.youmengpush.android;

/**
 * 
 * @author Administrator
 *
 */
public class AndroidListcast extends AndroidNotification {

	/**
	 * 
	 * @param appkey	appkey
	 * @param appMasterSecret	appMasterSecret
	 * @throws Exception	Exception
	 */
	public AndroidListcast(String appkey, String appMasterSecret)
			throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "listcast");
	}

	/**
	 * 
	 * @param tokens	tokens
	 * @throws Exception	Exception
	 */
	public void setDeviceToken(String tokens) throws Exception {
		setPredefinedKeyValue("device_tokens", tokens);
	}
}
