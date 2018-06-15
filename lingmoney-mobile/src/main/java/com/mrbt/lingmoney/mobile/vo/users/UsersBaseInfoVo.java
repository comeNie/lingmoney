package com.mrbt.lingmoney.mobile.vo.users;

import java.io.Serializable;
import java.util.Date;

import com.mrbt.lingmoney.utils.EncryptUserInfo;
import com.mrbt.lingmoney.utils.StringOpertion;

/**
 * 用户基础信息实体
 * 
 * @author YJQ
 *
 */
public class UsersBaseInfoVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String picture;
	private String nickName;
	private String codePath;
	private Integer certification;
	private Integer bank;
	private String telephone;
	private String email;
	private Integer isSetAddress;
	private String gesturePwd;
	private Integer isUseGesture;
	private Integer isUsePush;
	private String token;
	private String acNo;
	private String acName;
	private String referralCode;
    private String riskAssessment;// 风险评估

    public String getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(String riskAssessment) {
        this.riskAssessment = riskAssessment;
    }

	private Date lastLogin;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}

	public Integer getCertification() {
		return certification;
	}

	public void setCertification(Integer certification) {
		this.certification = certification;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = EncryptUserInfo.encryptTelephone(telephone);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = EncryptUserInfo.encryptEmail(email);
	}

	public Integer getIsSetAddress() {
		return isSetAddress;
	}

	public void setIsSetAddress(Integer isSetAddress) {
		this.isSetAddress = isSetAddress;
	}

	public String getGesturePwd() {
		return gesturePwd;
	}

	public void setGesturePwd(String gesturePwd) {
		this.gesturePwd = gesturePwd;
	}

	public Integer getIsUseGesture() {
		return isUseGesture;
	}

	public void setIsUseGesture(Integer isUseGesture) {
		this.isUseGesture = isUseGesture;
	}

	public Integer getIsUsePush() {
		return isUsePush;
	}

	public void setIsUsePush(Integer isUsePush) {
		this.isUsePush = isUsePush;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
//		this.acNo = EncryptUserInfo.encryptCommon(acNo);
		this.acNo = acNo;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = StringOpertion.hideName(acName);
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}
	
}
