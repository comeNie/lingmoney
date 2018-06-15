package com.mrbt.lingmoney.model;

public class ProductRecommendCustomer extends ProductRecommend {

	private static final long serialVersionUID = 2952425110618221426L;
	private String pName;// 产品名称
	private Integer pStatus;// 产品状态
	private String bStatus;// 标的状态
	private String actName;// 关联活动名称

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getpStatus() {
		return pStatus;
	}

	public void setpStatus(Integer pStatus) {
		this.pStatus = pStatus;
	}

	public String getbStatus() {
		return bStatus;
	}

	public void setbStatus(String bStatus) {
		this.bStatus = bStatus;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

}