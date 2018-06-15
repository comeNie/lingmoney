package com.mrbt.lingmoney.model;

public class LingbaoGiftVo extends LingbaoGift{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String typeName;// 礼品类型名称
	private String supplierName;// 供应商名称


	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}