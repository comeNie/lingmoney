package com.mrbt.lingmoney.test.factory.impl;

import com.mrbt.lingmoney.test.factory.Sender;

public class QQSender implements Sender{

	@Override
	public void Send() {
		System.out.println("this is QQ sender!");
	}
	
}
