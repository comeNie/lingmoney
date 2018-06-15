package com.mrbt.lingmoney.mobile.vo.users;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户设置用户基础信息实体
 * @author YJQ
 *
 */
public class UserAccountSetVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String picture;
    private String nickName;
    private String codePath;
    private Integer certification;
    private String telephone;
    private String email;
    private Integer isSetAddress;
    
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
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIsSetAddress() {
		return isSetAddress;
	}

	public void setIsSetAddress(Integer isSetAddress) {
		this.isSetAddress = isSetAddress;
	}
    
   
}
