package com.mrbt.lingmoney.model.youmengpush;

/**
 * @author luox
 * @Date 2017年5月25日
 */
public class UserDeivceVo {

	/**
     * 友盟推送的用户token
     * 表字段 : users_properties.youmeng_token
     */
    private String youmengToken;

    /**
     * 用户手机类型，1：安卓；2：IOS
     * 表字段 : users_properties.device_type
     */
    private Integer deviceType;

	public String getYoumengToken() {
		return youmengToken;
	}

	public void setYoumengToken(String youmengToken) {
		this.youmengToken = youmengToken;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
    
}
