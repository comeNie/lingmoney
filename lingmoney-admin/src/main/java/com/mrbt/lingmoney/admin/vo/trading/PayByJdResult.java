package com.mrbt.lingmoney.admin.vo.trading;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 京东支付返回结果
 * 
 * @author airgilbert
 *
 */
public class PayByJdResult {
	/**
	 * 成功200
	 */
	private static final int SUCCESS = 200;
	/**
	 * 从数据库中取出来的数据长度
	 */
	private int dataListSize = 0;
	/**
	 * 成功的列表，string为订单号
	 */
	private List<String> successList = new ArrayList<String>();
	/**
	 * 等待的列表，string为订单号
	 */
	private List<String> waitList = new ArrayList<String>();
	/**
	 * 失败的列表，string为订单号
	 */
	private List<String> failList = new ArrayList<String>();

	/**
	 * 返回的信息吗
	 */
	private int code;
	/**
	 * 返回信息
	 */
	private String msg;

	private boolean submit = true;

	/**
	 * 已打款金额
	 */
	private BigDecimal reachMoney = new BigDecimal(0);
	/**
	 * 等待确认金额
	 */
	private BigDecimal waitMoney = new BigDecimal(0);
	/**
	 * 失败金额
	 */
	private BigDecimal failMoney = new BigDecimal(0);
	/**
	 * 不是失败的所有金额
	 */
	private BigDecimal notFailMoney = new BigDecimal(0);

	private BigDecimal allMoney = new BigDecimal(0);
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 是否所有打款信息都进入到数据库初始化
	 * 
	 * @param tradeAccount
	 *            交易金额
	 * @return return
	 */
	public int isAllPayInDatabase(BigDecimal tradeAccount) {
		// 如果是初始化数据，成功列表，失败列表，等待列表都为空，则返回所有的金额是否==交易金额
		if (this.getSuccessList().size() == 0 && this.getFailList().size() == 0
				&& this.getWaitList().size() == 0) {
			return this.getAllMoney().compareTo(tradeAccount);
		}
		return this.getNotFailMoney().compareTo(tradeAccount);
	}
	
	/**
	 * 
	 * @return	return
	 */
	public boolean isOk() {
		if (getWaitList().size() == 0 && getSuccessList().size() > 0
				&& getNotFailMoney().compareTo(getReachMoney()) == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 是否等待中
	 * 
	 * @return	return
	 */
	public boolean isWait() {
		if (getWaitList() != null && getWaitList().size() > 0) {
			return true;
		}
		return false;
	}
	public BigDecimal getReachMoney() {
		return reachMoney;
	}
	public void setReachMoney(BigDecimal reachMoney) {
		this.reachMoney = reachMoney;
	}
	public BigDecimal getWaitMoney() {
		return waitMoney;
	}
	public void setWaitMoney(BigDecimal waitMoney) {
		this.waitMoney = waitMoney;
	}
	public BigDecimal getFailMoney() {
		return failMoney;
	}
	public void setFailMoney(BigDecimal failMoney) {
		this.failMoney = failMoney;
	}
	public BigDecimal getAllMoney() {
		return allMoney;
	}
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}
	public List<String> getSuccessList() {
		return successList;
	}
	public void setSuccessList(List<String> successList) {
		this.successList = successList;
	}
	public List<String> getWaitList() {
		return waitList;
	}
	public void setWaitList(List<String> waitList) {
		this.waitList = waitList;
	}
	public List<String> getFailList() {
		return failList;
	}
	public void setFailList(List<String> failList) {
		this.failList = failList;
	}
	public int getDataListSize() {
		return dataListSize;
	}
	public void setDataListSize(int dataListSize) {
		this.dataListSize = dataListSize;
	}
	public BigDecimal getNotFailMoney() {
		return notFailMoney;
	}
	public void setNotFailMoney(BigDecimal notFailMoney) {
		this.notFailMoney = notFailMoney;
	}
	public boolean isSubmit() {
		return submit;
	}
	public void setSubmit(boolean submit) {
		this.submit = submit;
	}

}
