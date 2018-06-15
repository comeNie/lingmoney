package com.mrbt.lingmoney.model;

public class BuyFrozenObject implements java.io.Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tid;
	private int infoId;
	private String bizCode;
	private String uid;
	private  int type;
	public BuyFrozenObject()
	{
		
	}

	
	public BuyFrozenObject(int tid, int infoId, String bizCode, String uid, int type)
	{
		this.tid = tid;
		this.infoId=infoId;
		this.bizCode = bizCode;
		this.uid=uid;
		this.type = type;
	}
	
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	
	public int getTid()
	{
		return tid;
	}
	public void setTid(int tid)
	{
		this.tid = tid;
	}
	public int getInfoId()
	{
		return infoId;
	}
	public void setInfoId(int infoId)
	{
		this.infoId = infoId;
	}
	public String getBizCode()
	{
		return bizCode;
	}
	public void setBizCode(String bizCode)
	{
		this.bizCode = bizCode;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}
	

}
