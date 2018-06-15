package com.mrbt.lingmoney.test.factory;

import com.mrbt.lingmoney.test.factory.impl.MailSender;
import com.mrbt.lingmoney.test.factory.impl.SmsSender;

public class SendFactory {
	
	/**
	 * 简单工厂模式,通过字符串来选择调用的方法
	 * @param type
	 * @return
	 */
	public Sender product(String type){
		if("mail".equals(type)){
			return new MailSender();
		}else if("sms".equals(type)){
			return new SmsSender();
		}else{
			System.out.println("请输入正确的类型");
			return null;
		}
	}
	
	public Sender ProductMail(){
		return new MailSender();
	}
	
	public Sender ProductSms(){
		return new SmsSender();
	}
	
	public static Sender StaticProductMail(){
		return new MailSender();
	}
	
	public static Sender StaticProductSms(){
		return new SmsSender();
	}
	
}
