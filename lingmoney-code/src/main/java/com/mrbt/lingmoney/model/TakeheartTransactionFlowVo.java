package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 随心取交易流水查询结果类
 * 
 * @author lihq
 * @date 2017年2月10日09:55:54
 * @version 1.0
 * @description 记录年月、当月理财金额、当月累计收益[已清算]、当月随心取交易流水
 *
 */
public class TakeheartTransactionFlowVo extends TakeheartTransactionFlow {
	private String yearmonth;
	private BigDecimal money;
	private BigDecimal interest;
	private List<TakeheartTransactionFlow> list;

	public String getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(String yearmonth) {
		this.yearmonth = yearmonth;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public List<TakeheartTransactionFlow> getList() {
		return list;
	}

	public void setList(List<TakeheartTransactionFlow> list) {
		this.list = list;
	}

}
