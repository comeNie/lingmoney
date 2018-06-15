package com.mrbt.lingmoney.model;

/**
 * 卖出结果
 * @author Administrator
 *
 */
public class SellResult implements java.io.Serializable{
	private static final long serialVersionUID = 4586770045761149894L;
	private int flag;
	private String buyMoney;
	private String sellMoney;
	private String sellDate;
	private String buyDate;
	private String pName;
	private String pRate;
	private Integer lCount;
	private int dayCount;
	private String code;
	private double feesRate;
	private String msg;
	
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getpName()
	{
		return pName;
	}
	public void setpName(String pName)
	{
		this.pName = pName;
	}
	public String getpRate()
	{
		return pRate;
	}
	public void setpRate(String pRate)
	{
		this.pRate = pRate;
	}
	public Integer getlCount()
	{
		return lCount;
	}
	public void setlCount(Integer lCount)
	{
		this.lCount = lCount;
	}
	public int getDayCount()
	{
		return dayCount;
	}
	public void setDayCount(int dayCount)
	{
		this.dayCount = dayCount;
	}
	public int getFlag()
	{
		return flag;
	}
	public void setFlag(int flag)
	{
		this.flag = flag;
	}
	
	public String getBuyMoney() {
		return buyMoney;
	}
	public void setBuyMoney(String buyMoney) {
		this.buyMoney = buyMoney;
	}
	public String getSellMoney() {
		return sellMoney;
	}
	public void setSellMoney(String sellMoney) {
		this.sellMoney = sellMoney;
	}
	public String getSellDate()
	{
		return sellDate;
	}
	public void setSellDate(String sellDate)
	{
		this.sellDate = sellDate;
	}
	public String getBuyDate()
	{
		return buyDate;
	}
	public void setBuyDate(String buyDate)
	{
		this.buyDate = buyDate;
	}
	public double getFeesRate()
	{
		return feesRate;
	}
	public void setFeesRate(double feesRate)
	{
		this.feesRate = feesRate;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "SellResult [flag=" + flag + ", buyMoney=" + buyMoney + ", sellMoney=" + sellMoney + ", sellDate="
				+ sellDate + ", buyDate=" + buyDate + ", pName=" + pName + ", pRate=" + pRate + ", lCount=" + lCount
				+ ", dayCount=" + dayCount + ", code=" + code + ", feesRate=" + feesRate + ", msg=" + msg + "]";
	}
	
}
