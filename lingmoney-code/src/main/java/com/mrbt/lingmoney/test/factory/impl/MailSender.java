package com.mrbt.lingmoney.test.factory.impl;

import com.mrbt.lingmoney.test.factory.Sender;

public class MailSender implements Sender {

	@Override
	public void Send() {
		System.out.println("this is mailsender!");
	}

}
