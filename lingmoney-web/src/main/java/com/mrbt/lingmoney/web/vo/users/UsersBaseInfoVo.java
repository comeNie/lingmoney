package com.mrbt.lingmoney.web.vo.users;

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

	private String loginAccount;
	private String picture;
	private String nickName;
	private String codePath;
	private Integer certification;
	private String telephone;
	private String email;
	private Integer isSetAddress;
	private Integer unRead;
	private Date lastLogin;

	private String degreeName;
	private String degreePicture;
	private String wechat;
	private String sex;
	private String education;
	private String job;
	private Integer bank;
	private String referralCode;
	
	private String acNo;
	private String acName;

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

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public Integer getUnRead() {
		return unRead;
	}

	public void setUnRead(Integer unRead) {
		this.unRead = unRead;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getDegreePicture() {
		return degreePicture;
	}

	public void setDegreePicture(String degreePicture) {
		this.degreePicture = degreePicture;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = EncryptUserInfo.encryptCommon(wechat);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getBank() {
		return bank;
	}

	public void setBank(Integer bank) {
		this.bank = bank;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
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

	@Override
	public String toString() {
		return "UsersBaseInfoVo [loginAccount=" + loginAccount + ", picture=" + picture + ", nickName=" + nickName
				+ ", codePath=" + codePath + ", certification=" + certification + ", telephone=" + telephone
				+ ", email=" + email + ", isSetAddress=" + isSetAddress + ", unRead=" + unRead + ", lastLogin="
				+ lastLogin + ", degreeName=" + degreeName + ", degreePicture=" + degreePicture + ", wechat=" + wechat
				+ ", sex=" + sex + ", education=" + education + ", job=" + job + ", bank=" + bank + ", referralCode="
				+ referralCode + "]";
	}

}
