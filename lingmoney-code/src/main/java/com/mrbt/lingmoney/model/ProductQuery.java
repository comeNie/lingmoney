package com.mrbt.lingmoney.model;
/**
 *@author syb
 *@date 2017年5月5日 下午5:27:30
 *@version 1.0
 *@description 
 **/
public class ProductQuery  implements java.io.Serializable
{
	private static final long serialVersionUID = -6887347566034528132L;
	
	private Integer isRecommend;
	private double lRate;
	private double hRate;
	private Integer  lCycle;
	private Integer hCycle;
	private  double minMenoy;
	private double maxMenoy;
	private String keyword;
	private Integer pcStatus;
	private Integer pcId;
	private String cityCode;
	private Integer start;
	private Integer number;
    private Integer excludPcId; //需要排除的类型id，列表查询不展示新手类型产品用

    public Integer getExcludPcId() {
        return excludPcId;
    }

    public void setExcludPcId(Integer excludPcId) {
        this.excludPcId = excludPcId;
    }

    public void changeRate(int rateType)
	{
		if (rateType==1)
		{
			lRate = 0.0001;
			hRate = 0.05;
		}
		else if(rateType==2)
		{
			lRate = 0.05;
			hRate = 0.07;
		}
		else if(rateType==3)
		{
			lRate = 0.07;
			hRate = 0.09;
		}else if(rateType==4)
		{
			lRate = 0.09;
			hRate = 2;
		}
		
	}
	
	
	public void changeCycle(int cycleType)
	{
		if (cycleType==1)
		{
			hCycle = 29;
			lCycle = -1;
		}
		else if(cycleType==2)
		{
			lCycle = 30;
			hCycle = 90;
		}
		else if(cycleType==3)
		{
			lCycle = 91;
			hCycle = 180;
		}else if(cycleType==4)
		{
			lCycle = 180;
			hCycle = 10*365;
		}
		
	}
	
	
	public void changeMinMoney(int minMoneyType)
	{
		if (minMoneyType==1)
		{
			minMenoy = 10;
			maxMenoy =100;
		}
		else if(minMoneyType==2)
		{
			minMenoy = 100;
			maxMenoy = 1000;
		}
		else if(minMoneyType==3)
		{
			minMenoy = 1000;
			maxMenoy = 10000;
		}else if(minMoneyType==4)
		{
			minMenoy = 10000;
			
		}
		
	}


	public Integer getIsRecommend()
	{
		return isRecommend;
	}


	public void setIsRecommend(Integer isRecommend)
	{
		this.isRecommend = isRecommend;
	}


	public double getlRate()
	{
		return lRate;
	}


	public void setlRate(double lRate)
	{
		this.lRate = lRate;
	}


	public double gethRate()
	{
		return hRate;
	}


	public void sethRate(double hRate)
	{
		this.hRate = hRate;
	}


	public Integer getlCycle()
	{
		return lCycle;
	}


	public void setlCycle(Integer lCycle)
	{
		this.lCycle = lCycle;
	}


	public Integer gethCycle()
	{
		return hCycle;
	}


	public void sethCycle(Integer hCycle)
	{
		this.hCycle = hCycle;
	}


	public double getMinMenoy()
	{
		return minMenoy;
	}


	public void setMinMenoy(double minMenoy)
	{
		this.minMenoy = minMenoy;
	}


	public double getMaxMenoy()
	{
		return maxMenoy;
	}


	public void setMaxMenoy(double maxMenoy)
	{
		this.maxMenoy = maxMenoy;
	}


	public String getKeyword()
	{
		return keyword;
	}


	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}


	public Integer getPcStatus()
	{
		return pcStatus;
	}


	public void setPcStatus(Integer pcStatus)
	{
		this.pcStatus = pcStatus;
	}


	public Integer getPcId()
	{
		return pcId;
	}


	public void setPcId(Integer pcId)
	{
		this.pcId = pcId;
	}


	public String getCityCode() {
		return cityCode;
	}


	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public Integer getStart() {
		return start;
	}


	public void setStart(Integer start) {
		this.start = start;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}
	

}
