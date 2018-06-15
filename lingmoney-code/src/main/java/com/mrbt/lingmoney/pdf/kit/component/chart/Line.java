package com.mrbt.lingmoney.pdf.kit.component.chart;

import lombok.Data;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
public class Line {
    private double yValue;
    private String  xValue;
    private String groupName;
    
    public double getyValue() {
		return yValue;
	}
	public void setyValue(double yValue) {
		this.yValue = yValue;
	}
	public String getxValue() {
		return xValue;
	}
	public void setxValue(String xValue) {
		this.xValue = xValue;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Line(){

    }
    public Line(double yValue, String xValue, String groupName){
        this.yValue=yValue;
        this.xValue=xValue;
        this.groupName=groupName;
    }



}
