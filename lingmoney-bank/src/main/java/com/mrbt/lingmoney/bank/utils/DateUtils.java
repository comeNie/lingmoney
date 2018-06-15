package com.mrbt.lingmoney.bank.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期辅助类
 * 
 * @author lzp
 *
 */
public class DateUtils {
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sf_L = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sfBiz = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat sf_m = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat sfM = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat sf_t = new SimpleDateFormat("HHmmss");

	public static String getFormatDateString(Date d) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d);
	}

	/**
	 * 添加天数
	 * 
	 * @param dt
	 * @param day
	 * @return
	 */
	public static Date addDay(Date dt, Integer day) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DAY_OF_YEAR, (day == null || day == 0) ? 1 : day);
		return c.getTime();
	}

	/**
	 * 添加月
	 * 
	 * @param dt
	 * @param m
	 * @return
	 */
	public static Date addMonth(Date dt, Integer m) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, (m == null || m == 0) ? 1 : m);
		return c.getTime();
	}

	/**
	 * 添加周
	 * 
	 * @param dt
	 * @param w
	 * @return
	 */
	public static Date addWeek(Date dt, Integer w) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.WEEK_OF_YEAR, (w == null || w == 0) ? 1 : w);
		return c.getTime();
	}

	/**
	 * 日期相减，目标日期-原日期=天数
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static int dateDiff(Date src, Date dest) {
		return  new Long((dest.getTime() - src.getTime()) / (24 * 60 * 60 * 1000)).intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.addDay(new Date(), -1));
	}

	public static String getDateStrByYMD(Date dt) {
		return sf_m.format(dt);
	}

	public static String getDateStrByHMS(Date dt) {
		return sf_t.format(dt);
	}
}
