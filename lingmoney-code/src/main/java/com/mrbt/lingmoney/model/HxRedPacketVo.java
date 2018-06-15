package com.mrbt.lingmoney.model;

import com.mrbt.lingmoney.model.HxRedPacket;

/**
 * @author luox
 * @Date 2017年7月7日
 */
public class HxRedPacketVo extends HxRedPacket{
	private static final long serialVersionUID = -476973639172332419L;
	private String productName;
	private String productTypeName;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

}
