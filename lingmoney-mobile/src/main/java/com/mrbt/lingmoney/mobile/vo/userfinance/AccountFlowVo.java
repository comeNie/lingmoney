package com.mrbt.lingmoney.mobile.vo.userfinance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 罗鑫
 * @Date 2017年4月17日
 */
public class AccountFlowVo implements Serializable {
	private static final long serialVersionUID = 226746998793873677L;

	private Integer type;
	private String typeName;
	private String operateTime;
	private Integer status;
	private BigDecimal money;
    private Integer pType; // 2 华兴 0京东
	private String cardPan; // 京东赎回银行卡号
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCardPan() {
		return cardPan;
	}

	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}

}
