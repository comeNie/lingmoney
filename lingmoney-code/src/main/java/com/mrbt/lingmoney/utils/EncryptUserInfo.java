package com.mrbt.lingmoney.utils;

import org.springframework.util.StringUtils;

/**
 * 用户信息去敏类
 * 
 * @author YJQ
 *
 */
public class EncryptUserInfo {
	/**
	 * 去敏邮箱
	 * 
	 * @author YJQ 2017年5月25日
	 * @param email
	 * @return
	 */
	public static String encryptEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return email;
		}
		if (!email.contains("@")) {
			return encryptCommon(email);
		}
		String[] enArr = email.split("@");
		return encryptCommon(enArr[0]) + "@" + enArr[1];
	}
	
	/**
	 * 去敏手机号
	 * @author YJQ  2017年5月26日
	 * @param telephone
	 * @return
	 */
	public static String encryptTelephone(String telephone){
		if(StringUtils.isEmpty(telephone)){
			return telephone;
		}
		if(telephone.length()!=11){
			return encryptCommon(telephone);
		}
		return telephone.substring(0, 3) + "****"+ telephone.substring(telephone.length() - 4, telephone.length());
	}

	/**
	 * 去敏信息 隐藏 userInfo.length/2 个字符
	 * 
	 * @author YJQ 2017年5月25日
	 * @param userInfo
	 * @return
	 */
	public static String encryptCommon(String userInfo) {
		if (StringUtils.isEmpty(userInfo)) {
			return userInfo;
		}
		int len = userInfo.length();
		int lenHalf = len / 2;
		int subLen = (len - lenHalf) / 2;
		return userInfo.substring(0, subLen) + "****" + userInfo.substring(subLen + lenHalf);
	}

	public static void main(String[] args) {
		
		System.out.println(encryptCommon("123456789123456789"));
	}
}
