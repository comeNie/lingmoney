package com.mrbt.lingmoney.test.factory.impl;

import com.mrbt.lingmoney.test.factory.Provider;
import com.mrbt.lingmoney.test.factory.Sender;

public class SendSmsFactory implements Provider {

	@Override
	public Sender produce() {
		return new SmsSender();
	}

}
