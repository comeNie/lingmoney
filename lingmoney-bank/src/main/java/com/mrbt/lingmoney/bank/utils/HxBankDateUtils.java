package com.mrbt.lingmoney.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 华兴银行日期时间格式
 * @author Administrator
 *
 */
public class HxBankDateUtils {
	
	static SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat time_format = new SimpleDateFormat("HHmmss");
	static SimpleDateFormat date_time_format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private Date date;
	
	/**
	 * 当前日期
	 * @param date
	 */
	public HxBankDateUtils(Date date) {
		this.date = date;
	}

	/**
	 * 返回现在的日期     yyyyMMdd
	 */
	public String getNowDate(){
		return date_format.format(date);
	}
	
	/**
	 * 返回现在的时间     HHmmss
	 */
	public String getNowTime(){
		return time_format.format(date);
	}
	
	/**
	 * 返回现在的日期时间     yyyyMMdd
	 */
	public String getNowDateTime(){
		return date_time_format.format(date);
	}
	
	
	public static void main(String[] args) {
		HxBankDateUtils hxDate = new HxBankDateUtils(new Date());
		
		System.out.println(hxDate.getNowDate());
		System.out.println(hxDate.getNowTime());
		System.out.println(hxDate.getNowDateTime());
	}

}
