package com.mrbt.lingmoney.youmengpush.ios;

/**
 * 
 * @author Administrator
 *
 */
public class IOSBroadcast extends IOSNotification {
	
	/**
	 * 
	 * @param appkey	appkey
	 * @param appMasterSecret	appMasterSecret
	 * @throws Exception	Exception
	 */
	public IOSBroadcast(String appkey, String appMasterSecret)
			throws Exception {
		setAppMasterSecret(appMasterSecret);
		setPredefinedKeyValue("appkey", appkey);
		this.setPredefinedKeyValue("type", "broadcast");

	}
}
