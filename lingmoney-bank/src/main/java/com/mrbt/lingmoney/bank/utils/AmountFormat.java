package com.mrbt.lingmoney.bank.utils;

import java.text.DecimalFormat;

/**
 * 华兴银行，金额格式
 * @author Administrator
 *
 */
public class AmountFormat {
	
	static DecimalFormat amount_format = new DecimalFormat("#############.##");
	static DecimalFormat rate_format = new DecimalFormat("########.#####");
	static DecimalFormat rate2_format = new DecimalFormat("###.######");
	
	private double amount;

	public AmountFormat(double amount) {
		this.amount = amount;
	}
	
	/**
	 * 金额格式化   整数最多13位，小数最多2位
	 * @return
	 */
	public String getAmountFormat() {
		return amount_format.format(amount);
	}
	
	/**
	 * 利率汇率格式化，整数最多8位，小数最多5位
	 * @return
	 */
	public String getRateFormat(){
		if(amount > 99999999.99999){
			return "";
		}
		return rate_format.format(amount);
	}
	
	/**
	 * 整数最多3位，小数最多 6位
	 * @return
	 */
	public String getRate2Format(){
		if(amount > 999.999999){
			return "";
		}
		return rate2_format.format(amount);
	}

	public static void main(String[] args) {
		AmountFormat af = new AmountFormat(100000000.306065);
		System.out.println(af.getAmountFormat());
		System.out.println(af.getRateFormat());
		System.out.println(af.getRate2Format());
	}

}
