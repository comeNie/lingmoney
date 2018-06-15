package com.mrbt.lingmoney.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证 网站域名 联系电话 手机号码 邮政编码 邮箱
 * 
 * @author Administrator
 *
 */
public class Validation {

	/**
	 * 正则验证方法
	 * 
	 * @param regexstr
	 * @param str
	 * @return
	 */
	public static boolean Match(String regexstr, String str) {
		Pattern regex = Pattern.compile(regexstr, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean MatchMail(String mail) {
		String mailregex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return Match(mailregex, mail);
	}

	/**
	 * 手机验证
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean MatchMobile(String mobile) {
		String mobileregex = "(^1(3|4|7|5|8)([0-9]{9}))";
		return Match(mobileregex, mobile);
	}

	/**
	 * 电话验证,包话400,800电话
	 * 
	 * @param Tel
	 * @return
	 */
	public static boolean MatchTel(String Tel) {
		String telregex = "(^[0-9]{3,4}-[0-9]{7,8}-[0-9]{3,4}$)|(^[0-9]{3,4}-[0-9]{7,8}$)|(^[0-9]{7,8}-[0-9]{3,4}$)|^[400]-[0-9]{0,12}|^[800]{10}";
		return Match(telregex, Tel);
	}

	/**
	 * 手机号验证
	 * 
	 * @param Tel
	 * @return
	 */
	public static boolean MatchTelphone(String Tel) {
		String telregex = "(^1(3|4|7|5|8)([0-9]{9}))|(^0[0-9]{2,3}-[1-9]{8})|(^0[0-9]{2,3}[1-9]{8})|(^400[0-9]{7})|(^800[0-9]{7})";
		return Match(telregex, Tel);
	}

	/**
	 * 域名验证
	 * 
	 * @param webdomain
	 * @return
	 */
	public static boolean Webdomain(String webdomain) {
		String webdomainregex = "http://([^/]+)/*";
		return Match(webdomainregex, webdomain);
	}

	/**
	 * 验证6位数字
	 * 
	 * @param zipcode
	 * @return
	 */
	public static boolean ZipCode(String zipcode) {
		String zipcoderegex = "^[0-9]{6}$";
		return Match(zipcoderegex, zipcode);
	}

	/**
	 * 匹配至少一个汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean verifyName(String str) {
		Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m = p_str.matcher(str);

		if (m.find() && m.group(0).equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证居住地址
	 * 
	 * @author YJQ 2017年5月19日
	 * @param str
	 * @return
	 */
	public static boolean verifyAddress(String str) {
		Pattern p_str = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z\\d\\-\\_]{0,255}$");
		Matcher m = p_str.matcher(str);

		if (m.find() && m.group(0).equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
	 * 
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) throws Exception {
		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
		String[] versionArray2 = version2.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
		int diff = 0;
		while (idx < minLength && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0// 先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {// 再比较字符
			++idx;
		}
		// 如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}

	/**
	 * 验证账号
	 * 
	 * @author YJQ 2017年5月20日
	 * @param account
	 * @return
	 */
	public static boolean MatchAccount(String account) {
		String reg = "^(?![0-9]+$)[a-zA-Z0-9_]+$";
		return Match(reg, account) && (account.length() > 5 && account.length() < 17);
	}

	/**
	 * 验证微信号
	 * 
	 * @author YJQ 2017年5月20日
	 * @param wechat
	 * @return
	 */
	public static boolean MatchWechat(String wechat) {
		String reg = "^[a-zA-Z\\d_]{5,}$";
		return Match(reg, wechat);
	}

	/**
	 * 验证昵称
	 * 
	 * @author YJQ 2017年5月24日
	 * @param nickName
	 * @return
	 */
	public static boolean MatchNickName(String nickName) {
		String reg = "^[\\u4e00-\\u9fa5a-zA-Z\\d_]{1,100}$";
		return Match(reg, nickName);
	}

	/**
	 * 验证职业
	 * 
	 * @author YJQ 2017年5月24日
	 * @param str
	 * @return
	 */
	public static boolean verifyJob(String str) {
		String reg = "^[\\u4e00-\\u9fa5/]+";
		return Match(reg, str);
	}

	/**
	 * 验证密码
	 * 
	 * @author YJQ 2017年5月24日
	 * @param str
	 * @return
	 */
	public static boolean verifyPwd(String str) {
		String reg = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,16}$";
		return Match(reg, str);
	}

	/**
	 * 验证银行账号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean MatchBankCard(String str) {
		String reg = "^([1-9]{1})(\\d{14}|\\d{18})$";
		return Match(reg, str);
	}

	/**
	 * 华兴银行接口金额格式验证
	 * 
	 * @author YJQ 2017年6月8日
	 * @param money
	 * @return
	 */
	public static boolean MatchMoney(String money) {
		// 规则 正整数+. 整数部分不得超过15位且第一位不得为0 ；小数部分必须为2位
		String reg = "^([1-9]\\d{0,14}|0)(\\.\\d{1,2})?$";
		return Validation.Match(reg, money);
	}

	/**
	 * 验证身份证号 身份证号码的格式：610821-20061222-612-X
	 * 由18位数字组成：前6位为地址码，第7至14位为出生日期码，第15至17位为顺序码，
	 * 第18位为校验码。检验码分别是0-10共11个数字，当检验码为“10”时，为了保证公民身份证号码18位，所以用“X”表示。虽然校验码为“X”不能更换，但若需全用数字表示，只需将18位公民身份号码转换成15位居民身份证号码，去掉第7至8位和最后1位3个数码。
	 * 当今的身份证号码有15位和18位之分。1985年我国实行居民身份证制度，当时签发的身份证号码是15位的，1999年签发的身份证由于年份的扩展（由两位变为四位）和末尾加了效验码，就成了18位。
	 * （1）前1、2位数字表示：所在省份的代码； （2）第3、4位数字表示：所在城市的代码； （3）第5、6位数字表示：所在区县的代码；
	 * （4）第7~14位数字表示：出生年、月、日； （5）第15、16位数字表示：所在地的派出所的代码；
	 * （6）第17位数字表示性别：奇数表示男性，偶数表示女性 （7）第18位数字是校检码：根据一定算法生成
		@param idcard
	 *  @return boolean
	 */
	public static boolean IdCardNo(String IDStr) {
		boolean tipInfo = true;// 记录错误信息
		String Ai = "";
		// 判断号码的长度 15位或18位
		if (IDStr.length() != 15 && IDStr.length() != 18) {
//			tipInfo = "身份证号码长度应该为15位或18位。";
			return false;
		}

		// 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
//			tipInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}

		// 判断出生年月是否有效
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 日期
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
//			tipInfo = "身份证出生日期无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
//				tipInfo = "身份证生日不在有效范围。";
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
//			tipInfo = "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
//			tipInfo = "身份证日期无效";
			return false;
		}

		// 判断地区码是否有效
		Hashtable<String, String> areacode = GetAreaCode();
		// 如果身份证前两位的地区码不在Hashtable，则地区码有误
		if (areacode.get(Ai.substring(0, 2)) == null) {
//			tipInfo = "身份证地区编码错误。";
			return false;
		}

		if (isVarifyCode(Ai, IDStr) == false) {
//			tipInfo = "身份证校验码无效，不是合法的身份证号码";
			return false;
		}

		return tipInfo;
	}

	/*
	 * 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i =
	 * 0, ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码
	 * 对应关系为： Y值： 0 1 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
	 */
	private static boolean isVarifyCode(String Ai, String IDStr) {
		String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = sum % 11;
		String strVerifyCode = VarifyCode[modValue];
		Ai = Ai + strVerifyCode;
		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				return false;

			}
		}
		return true;
	}

	/**
	 * 将所有地址编码保存在一个Hashtable中
	 * 
	 * @return Hashtable 对象
	 */

	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 判断字符串是否为数字,0-9重复0次或者多次
	 * 
	 * @param strnum
	 * @return
	 */
	private static boolean isNumeric(String strnum) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(strnum);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isDate(String strDate) {

		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			// String reg="^([1-9]{1,15}|0).\\d{2}$";
			// String reg="^([1-9]\\d{0,14}|0)(\\.\\d{1,2})?$";
			// System.out.println(Validation.Match(reg, "100000"));
			System.out.println(Validation.IdCardNo("13022919890120406x"));
//			System.out.println(Validation.MatchTelphone(""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
