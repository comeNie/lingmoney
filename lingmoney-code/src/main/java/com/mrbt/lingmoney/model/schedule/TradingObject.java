package com.mrbt.lingmoney.model.schedule;


public class TradingObject implements java.io.Serializable {
	private static final long serialVersionUID = 6296279368320159786L;

	/**
	 * trading.id
	 */
	private Integer id;
	/**
	 * trading.u_id
	 */
	private String uId;
	/**
	 * product.id
	 */
	private Integer pId;

	/**
	 * trading.id
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * trading.u_id
	 */
	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	/**
	 * product.id
	 */
	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

}
