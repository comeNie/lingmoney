package com.mrbt.lingmoney.admin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luox
 * @Date 2017年6月26日
 */
public final class DateFormatUtils {

	private DateFormatUtils() {
	}

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * @param d	d
	 * @return	return
	 */
	public static String getFormatDateString(Date d) {
		return format.format(d);
	}

	/**
	 * 
	 * @param d	d
	 * @param pattern	pattern
	 * @return	return	return
	 */
	public static String getFormatDateString(Date d, String pattern) {
		format2 = new SimpleDateFormat(pattern);
		return format2.format(d);
	}

	/**
	 * 
	 * @param date	date
	 * @return	return
	 * @throws ParseException	ParseException
	 */
	public static Date parseString(String date) throws ParseException {
		return format.parse(date);
	}

	/**
	 * 
	 * @param date	date
	 * @param pattern	pattern
	 * @return	return
	 * @throws ParseException	ParseException
	 */
	public static Date parseString(String date, String pattern)
			throws ParseException {
		format2 = new SimpleDateFormat(pattern);
		return format2.parse(date);
	}

}
