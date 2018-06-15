package com.mrbt.lingmoney.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

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
	 * 找出今天的前后日期
	 * 
	 * @param i
	 * @return
	 */
	public static String giveDay(int i) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, i);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today_1 = sdf.format(cal.getTime());
		return today_1;
	}

	/**
	 * 返回当前时间
	 * 
	 * @return
	 */
	public static String getTimeStr() {
		return sf_t.format(new Date());
	}

	/**
	 * 返回订单生成日期(pattren='yyyyMMdd')
	 * 
	 * @return
	 */
	public static String getDateStr() {
		return sf_m.format(new Date());
	}

	/**
	 * 购买的交易码日期(pattren='yyyyMMddHHmmss')
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDateStrByBiz(Date dt) {
		return sfBiz.format(dt);
	}

	/**
	 * 生成日期类的字符串(pattren='yyyy-MM-dd')
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDtStr(Date dt) {
		return getDtStr(dt, sf);
	}
	
	/**
	 * 生成日期类的字符串(pattren='yyyy-MM-dd HH:mm:ss')
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDtStr2(Date dt) {
		return getDtStr(dt, sft);
	}

	/**
	 * 生成日期类的字符串(pattren='yyyy-MM-dd')
	 * 
	 * @param dt
	 * @return
	 */
	public static String getDtStr(Date dt, String pattren) {
		sf.applyPattern(pattren);
		return sf.format(dt);
	}

	public static String getDtStr(Date dt, SimpleDateFormat st) {
		return st.format(dt);
	}

	/**
	 * 格式化字符串，(pattren='yyyy-MM-dd HH:mm:ss')
	 * 
	 * @param strDt
	 * @return
	 */
	public static Date parseDate(String strDt) {
		try {
			return sft.parse(strDt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否为当天
	 * 如果是16点前，包含16点为当天。
	 * 
	 * @param dt
	 * @return
	 */
	public static boolean isToDay(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int second = c.get(Calendar.SECOND);
		int minute = c.get(Calendar.MINUTE);
		// 包含16点整的都算今天
		return hour < AppCons.TRAD_HOUR || (hour == AppCons.TRAD_HOUR && second == 0 && minute == 0) ? true : false;
	}

    /**
     * 获取交易日期，已当日16点前为当天计算，16点后为次日。
     * 遇到周末或者节假日顺延，EX:
     * 2018-02-23为周五，如果是下午四点以后交易，顺延一天至2018-02-24，因为24号为周六再次顺延，直到周一。所以计算的日期为2018-02-26号。
     * 
     * @param dt 交易日期
     * @param holiday 节假日map
     * @return
     */
	public static Date getTradeDate(Date dt, HashMap<String, String> holiday) {
		if (isToDay(dt)) {
			return getWorkDay(dt, holiday);
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DAY_OF_YEAR, 1);
			return getWorkDay(c.getTime(), holiday);
		}
	}

	/**
	 * 获取卖出的交易日期
	 * 
	 * @param dt
	 * @param holiday
	 * @return
	 */
	public static Date getSellTradeDate(Date dt, HashMap<String, String> holiday) {
		Date valueDt = getTradeDate(dt, holiday);
		if (dt.getTime() == valueDt.getTime()) {
			Calendar c = Calendar.getInstance();
			c.setTime(valueDt);
			c.add(Calendar.DAY_OF_YEAR, -1);
			return new Date(c.getTimeInMillis());
		}
		return valueDt;
	}

	/**
	 * 返回整日，不包含时间，分，秒
	 * 
	 * @param dt
	 * @return
	 */
	public static Date getDay(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 判断是否为工作日
	 * 
	 * @param dt
	 */
	public static int isWorkDay(Date dt) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取下一个工作日
	 * 
	 * @param dt
	 *            当前日期
	 * @param holiday
	 *            节假日的map
	 * @return 递归操作返回非节假日
	 */
	public static Date getWorkDay(Date dt, HashMap<String, String> holiday) {
		int dIndex = isWorkDay(dt);
		if (dIndex != 1 && dIndex != 7 && !holiday.containsKey(sf.format(dt))) {
			return dt;
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			int addDay = 1;
			// 周六+2天
			if (dIndex == 7) {
				addDay = 2;
			}
			c.add(Calendar.DAY_OF_YEAR, addDay);
			return getWorkDay(c.getTime(), holiday);
		}
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
	 * 返回整天
	 * @param dt
	 * @param day
	 * @return
	 */
	public static Date addDay2(Date dt, Integer day) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sf.parse(sf.format(dt)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
		return (int) ((dest.getTime() - src.getTime()) / (24 * 60 * 60 * 1000));
	}

	/**
	 * 时间相减，目标时间-原时间=分钟
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static int minuteDiff(Date src, Date dest) {
		return (int) ((dest.getTime() - src.getTime()) / (60 * 1000));
	}

	/**
	 * 相差的月数
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static int DateDiffByMonth(Date src, Date dest) {
		Calendar cs = Calendar.getInstance();
		cs.setTime(src);
		Calendar ds = Calendar.getInstance();
		ds.setTime(dest);
		int m_begin = cs.get(Calendar.MONTH) + 1; // 获得合同开始日期月份
		int m_end = ds.get(Calendar.MONTH) + 1;
		return m_end - m_begin + (ds.get(Calendar.YEAR) - cs.get(Calendar.YEAR)) * 12;
	}

	/**
	 * 格式化日期
	 * 
	 * @param dt
	 *            日期
	 * @param dateFormat
	 *            格式
	 * @return
	 */
	public static String getDateStr(Date dt, SimpleDateFormat dateFormat) {
		return dateFormat.format(dt);
	}

	public static Date getStringTODate(String date_str) {
		try {
			return sft.parse(date_str);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 生成最小卖出时间
	 * 
	 * @param type
	 * @param moreDay
	 * @param holiday
	 * @return
	 */
	public static Date createMinSellDt(int type, Date valueDt, int moreDay, HashMap<String, String> holiday) {
		Date minSellDt = null;
		if (type == AppCons.UNIT_TIME_MONTH) {
			minSellDt = DateUtils.addMonth(valueDt, moreDay);

		} else if (type == AppCons.UNIT_TIME_DAY) {
			minSellDt = DateUtils.addDay(valueDt, moreDay);
		}
		if (minSellDt != null) {
			return getTradeDate(DateUtils.getDay(minSellDt), holiday);
		}
		return null;
	}

	/**
	 * 添加天数
	 * 
	 * @param dt
	 * @param day
	 * @return
	 */
	public static Date addDay(Date dt, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DAY_OF_YEAR, day);
		return c.getTime();
	}

	/**
	 * 添加月
	 * 
	 * @param dt
	 * @param m
	 * @return
	 */
	public static Date addMonth(Date dt, int m) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, m);
		return c.getTime();
	}

	/**
	 * 加分钟运算
	 * 
	 * @author YJQ 2017年5月31日
	 * @param dt
	 *            目标时间
	 * @param m
	 *            分钟数
	 * @return
	 */
	public static Date addMinute(Date dt, Integer m) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MINUTE, m);
		return c.getTime();
	}

	/**
	 * 加秒运算
	 * 
	 * @author YJQ 2017年5月31日
	 * @param dt
	 *            目标时间
	 * @param m
	 *            秒数
	 * @return
	 */
	public static Date addSecond(Date dt, Integer m) {
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.SECOND, m);
		return c.getTime();
	}

	/**
	 * 获取时间差
	 * 
	 * @param src
	 *            传入的时间
	 * @param dest
	 *            当前时间
	 * @return 分钟
	 */
	public static long dateDiffMins(Date src, Date dest) {
		return ((dest.getTime() - src.getTime()) / (60 * 1000));
	}

	/**
	 * 距离明天多少秒
	 * 
	 * @return
	 */
	public static Long secsFromTomorrow() {
		return getTomorrowMorning().getTime() / 1000 - System.currentTimeMillis() / 1000;
	}

	public static void main(String[] args) {
		System.out.println(addDay2(new Date(), 4));
	}

	/**
	 * 获取明天的凌晨
	 * 
	 * @return
	 */
	public static Date getTomorrowMorning() {
		try {
			Date date = new Date();// 取时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往前减少一天，若想把日期向后推一天则将负数改为正数
			date = calendar.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			return formatter.parse(formatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getMonth(String startDate, String endDate) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
		int monthday;
		try {
			Date startDate1 = f.parse(startDate);
			// 开始时间与今天相比较
			Date endDate1 = f.parse(endDate);
			Calendar starCal = Calendar.getInstance();
			starCal.setTime(startDate1);
			int sYear = starCal.get(Calendar.YEAR);
			int sMonth = starCal.get(Calendar.MONTH);
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate1);
			int eYear = endCal.get(Calendar.YEAR);
			int eMonth = endCal.get(Calendar.MONTH);

			monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

			return monthday;
		} catch (ParseException e) {
			monthday = 0;
		}
		return monthday;
	}

	public static Date getFirstDayofMonth(int sYear, int sMonth) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, sYear);
		c.set(Calendar.MONTH, sMonth);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(c.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();

	}

	public static Date getEndDayofMonth(int sYear, int sMonth) {
		Calendar c = Calendar.getInstance();
		c.set(c.YEAR, sYear);
		c.set(c.MONTH, sMonth);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(c.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();

	}

	public static long minusMinute() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		long timeoutLong = c.getTimeInMillis() - new Date().getTime();
		long timeout = timeoutLong / 1000 / 60;
		return timeout;
	}

}
