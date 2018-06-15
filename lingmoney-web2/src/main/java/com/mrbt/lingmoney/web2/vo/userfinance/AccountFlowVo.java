package com.mrbt.lingmoney.web2.vo.userfinance;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wzy
 * @Date 2017年12月22日
 */
public class AccountFlowVo implements Serializable {
	private static final long serialVersionUID = 226746998793873677L;

	private Integer type;
	private String typeName;
	private String operateTime;
	private Integer status;
	private BigDecimal money;
    private Integer pType;
    private String cardPan;
	
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

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public String getCardPan() {
        return cardPan;
    }

    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

}
