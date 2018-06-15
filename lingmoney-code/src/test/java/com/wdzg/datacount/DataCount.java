package com.wdzg.datacount;

import org.apache.commons.lang.StringUtils;

public class DataCount {
	
	public static void main(String[] args) {
		String op = "ssss";
		String tp = "aaa";
		String u_id = "aaaa";
		
		System.out.println(StringUtils.isBlank(op));
		System.out.println(StringUtils.isBlank(tp));
		System.out.println(StringUtils.isBlank(u_id));
		
		System.out.println(StringUtils.isBlank(op) || StringUtils.isBlank(op) || StringUtils.isBlank(op));
	}
}

