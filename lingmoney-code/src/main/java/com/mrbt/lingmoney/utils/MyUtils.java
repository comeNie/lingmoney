package com.mrbt.lingmoney.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MyUtils {
	/** 
	 * 统一获取log日志的方法
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class clazz) {
		return LogManager.getLogger(clazz);
	}

	public static Logger getBuyLogger() {
		return LogManager.getLogger("buyLog");
	}

	public static Logger getSellLogger() {
		return LogManager.getLogger("sellLog");
	}
	
	public static Logger getProductStatisticsLogger() {
		return LogManager.getLogger("productStatisticsLog");
	}
	
	public static Logger getRechargeCashStatisticsLogger() {
		return LogManager.getLogger("rechargeCashStatisticsLog");
	}
	
	public static Logger getWalletInterestLogger() {
		return LogManager.getLogger("walletInterestLog");
	}
	 
	public static Logger getBuyRankingLogger() {
		return LogManager.getLogger("buyRankingLog");
	}
	
	public static Logger getAutoFixedInvestmentLogger() {
		return LogManager.getLogger("autoFixedInvestmentLog");
	}
	
	public static Logger getAutoSellLogger() {
		return LogManager.getLogger("autoSellLog");
	}

	public static Logger getUserAutoReconciliationLogger() {
		return LogManager.getLogger("userAutoReconciliationLog");
	}
	
	public static Logger getAutoSellNoviceBuyLogger() {
		return LogManager.getLogger("autoSellNoviceLog");
	}
	public static Logger getUpdateProductEndStatus() {
		return LogManager.getLogger("updateProductEndStatusLog");
	}
	
	public static Logger getTakeheartInterestLogger() {
		return LogManager.getLogger("takeheartInterestLog");
	}
	
	public static Logger getTakeheartStatisticsLogger() {
		return LogManager.getLogger("takeheartStatisticsLog");
	}
	
	public static void main(String[] args)
	{
		getAutoSellLogger().info("ddddddddddddddddddddddddddddd");
	}

	public static Logger getSmsSendLogger() {
		return LogManager.getLogger("smsSend");
	}

}

