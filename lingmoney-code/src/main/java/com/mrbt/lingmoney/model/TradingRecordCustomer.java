package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 自定义购买记录bean
 * 
 * @author lihq
 * @date 2017年4月20日 下午4:29:30
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
public class TradingRecordCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 交易id
	private String usersId;// 用户id
	private String usersName;// 用户姓名
	private String usersNickName;// 用户昵称
	private String usersTel;// 用户手机号
	private Date buyDt;// 购买时间
	private BigDecimal financialMoney;// 理财金额
	private Integer proId;// 产品id
	private String proName;// 产品名称
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public String getUsersName() {
		return usersName;
	}
	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}
	public String getUsersNickName() {
		return usersNickName;
	}
	public void setUsersNickName(String usersNickName) {
		this.usersNickName = usersNickName;
	}
	public String getUsersTel() {
		return usersTel;
	}
	public void setUsersTel(String usersTel) {
		this.usersTel = usersTel;
	}
	public Date getBuyDt() {
		return buyDt;
	}
	public void setBuyDt(Date buyDt) {
		this.buyDt = buyDt;
	}
	public BigDecimal getFinancialMoney() {
		return financialMoney;
	}
	public void setFinancialMoney(BigDecimal financialMoney) {
		this.financialMoney = financialMoney;
	}
	public Integer getProId() {
		return proId;
	}
	public void setProId(Integer proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}

    @Override
    public String toString() {
        return "TradingRecordCustomer [id=" + id + ", usersId=" + usersId + ", usersName=" + usersName
                + ", usersNickName=" + usersNickName + ", usersTel=" + usersTel + ", buyDt=" + buyDt
                + ", financialMoney=" + financialMoney + ", proId=" + proId + ", proName=" + proName + "]";
    }
	
}