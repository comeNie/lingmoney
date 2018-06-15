package com.mrbt.lingmoney.admin.utils;

/**
 * 
 * @author Administrator
 *
 */
public class SmsSendThread implements Runnable {
	
	ThreadPoolSmsSend threadPoolSmsSend;

	public SmsSendThread(ThreadPoolSmsSend threadSmsSend) {
		this.threadPoolSmsSend = threadSmsSend;

	}

	@Override
	public void run() {
		try {
			threadPoolSmsSend.call();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
