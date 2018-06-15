package com.mrbt.lingmoney.testng;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.mrbt.pay.jd.reback.RebackResultVo;
import com.mrbt.pay.jd.reback.ReturnByJd;

public class AutoRedeemTaskTestNg extends SpringTestNG  {
	@Autowired
	public ReturnByJd returnByJd;
	
	private static final String BIZ_NUMBER = "201803220002114100160301165421930198981";
	
	private static final String BANK_SOTR = "CMB";
	
	private static final String BANK_NO = "6225880140356867";
	
	private static final String USER_NAME = "喻龙"; 
	
	private static final String USER_TEL = "13683173295";
	
	@Test
	public void testNgHeard() {
//		testAccountQuery();
		
//		testDefrayPay();
		
		testTradeQuery();
	}
	
	
	/**
	 * 查询京东企业账户余额
	 */
	public void testAccountQuery() {
		RebackResultVo vo = returnByJd.accountQuery();
		if (vo.isOk()) {
			System.out.println("avail_monut" + vo.getAvail_amount().toString());
			System.out.println("frozen_monut" + vo.getFrozen_amonut().toString());
			System.out.println("account_monut" + vo.getAccount_amount().toString());
		} else {
			System.out.println(vo.getResultInfo());
		}

	}
	/**
	 * 测试兑付信息
	 */
	public void testDefrayPay() {
		// 卡号我注了，换***了，你自己写别的
		RebackResultVo vo = returnByJd.defrayPay(BANK_SOTR, BANK_NO, USER_NAME, BIZ_NUMBER, USER_TEL, new BigDecimal("1.00"));
		if (vo.isOk()) {
			System.out.println("交易成功");
		} else {
			System.out.println(vo.getResultInfo());
		}
	}
	
	/**
	 * 兑付查询
	 */
	public void testTradeQuery() {
		RebackResultVo vo = returnByJd.tradeQuery(BIZ_NUMBER);
		if (vo.isOk()) {
			System.out.println("交易成功");
		} else {
			System.out.println("iswait:========" + vo.isWait());
			System.out.println(vo.getResultInfo());
		}
	}
}
