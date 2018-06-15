package com.mrbt.lingmoney.youmengpush.ios;

import java.util.Arrays;
import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;
import com.mrbt.lingmoney.youmengpush.UmengNotification;

/**
 * 
 * @author Administrator
 *
 */
public abstract class IOSNotification extends UmengNotification {

	// Keys can be set in the aps level
	protected static final HashSet<String> APS_KEYS = new HashSet<String>(
			Arrays.asList(new String[]{"alert", "badge", "sound",
					"content-available"}));

	@Override
	public boolean setPredefinedKeyValue(String key, Object value)
			throws Exception {
		if (ROOT_KEYS.contains(key)) {
			// This key should be in the root level
			rootJson.put(key, value);
		} else if (APS_KEYS.contains(key)) {
			// This key should be in the aps level
			JSONObject apsJson = null;
			JSONObject payloadJson = null;
			if (rootJson.containsKey("payload")) {
				payloadJson = rootJson.getJSONObject("payload");
			} else {
				payloadJson = new JSONObject();
				rootJson.put("payload", payloadJson);
			}
			if (payloadJson.containsKey("aps")) {
				apsJson = payloadJson.getJSONObject("aps");
			} else {
				apsJson = new JSONObject();
				payloadJson.put("aps", apsJson);
			}
			apsJson.put(key, value);
		} else if (POLICY_KEYS.contains(key)) {
			// This key should be in the body level
			JSONObject policyJson = null;
			if (rootJson.containsKey("policy")) {
				policyJson = rootJson.getJSONObject("policy");
			} else {
				policyJson = new JSONObject();
				rootJson.put("policy", policyJson);
			}
			policyJson.put(key, value);
		} else {
			if ("payload".equals(key) || "aps".equals(key)
					|| "policy".equals(key)) {
				throw new Exception("You don't need to set value for " + key
						+ " , just set values for the sub keys in it.");
			} else {
				throw new Exception("Unknownd key: " + key);
			}
		}

		return true;
	}

	/**
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return return
	 * @throws Exception
	 *             Exception
	 */
	// Set customized key/value for IOS notification
	public boolean setCustomizedField(String key, String value)
			throws Exception {
		// rootJson.put(key, value);
		JSONObject payloadJson = null;
		if (rootJson.containsKey("payload")) {
			payloadJson = rootJson.getJSONObject("payload");
		} else {
			payloadJson = new JSONObject();
			rootJson.put("payload", payloadJson);
		}
		payloadJson.put(key, value);
		return true;
	}

	/**
	 * 
	 * @param token
	 *            token
	 * @throws Exception
	 *             Exception
	 */
	public void setAlert(String token) throws Exception {
		setPredefinedKeyValue("alert", token);
	}

	/**
	 * 
	 * @param badge
	 *            badge
	 * @throws Exception
	 *             Exception
	 */
	public void setBadge(Integer badge) throws Exception {
		setPredefinedKeyValue("badge", badge);
	}

	/**
	 * 
	 * @param sound
	 *            sound
	 * @throws Exception
	 *             Exception
	 */
	public void setSound(String sound) throws Exception {
		setPredefinedKeyValue("sound", sound);
	}

	/**
	 * 
	 * @param contentAvailable
	 *            contentAvailable
	 * @throws Exception
	 *             Exception
	 */
	public void setContentAvailable(Integer contentAvailable) throws Exception {
		setPredefinedKeyValue("content-available", contentAvailable);
	}
}
