package com.mrbt.lingmoney.test;

import java.math.BigDecimal;

public class Test1 {
	public static void main(String[] args) {
		
		BigDecimal sumInterest = new BigDecimal("0");
		BigDecimal sumInterest2 = new BigDecimal("1000");
		sumInterest = sumInterest.add(sumInterest2);
		
		System.out.println(sumInterest);
	}
}
