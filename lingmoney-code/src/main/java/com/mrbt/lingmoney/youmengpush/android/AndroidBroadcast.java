package com.mrbt.lingmoney.youmengpush.android;

/**
 * 
 * @author Administrator
 *
 */
public class AndroidBroadcast extends AndroidNotification {
	
	/**
	 * 
	 * @param appkey	appkey
	 * @param appMasterSecret	appMasterSecret
	 * @throws Exception	Exception
	 */
	public AndroidBroadcast(String appkey, String appMasterSecret)
			throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");
	}
}
