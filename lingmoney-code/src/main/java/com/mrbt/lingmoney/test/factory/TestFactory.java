package com.mrbt.lingmoney.test.factory;

import com.mrbt.lingmoney.test.factory.impl.QQSender;
import com.mrbt.lingmoney.test.factory.impl.SendMailFactory;
import com.mrbt.lingmoney.test.factory.impl.SendQQFactory;
import com.mrbt.lingmoney.test.factory.impl.SendSmsFactory;

public class TestFactory {

	/**
	 * 工厂模式测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/**
		 * 简单工厂模式
		 */
		System.out.println("开始简单工厂模式....................................");
		SendFactory send = new SendFactory();
		Sender sendSender1 = send.product("mail");
		sendSender1.Send();

		Sender sendSender2 = send.product("sms");
		sendSender2.Send();

		/**
		 * 多个工厂方法模式，是对普通工厂方法模式的改进，在普通工厂方法模式中，如果传递的字符串出错，
		 * 则不能正确创建对象，而多个工厂方法模式是提供多个工厂方法，分别创建对象
		 */
		System.out.println("开始多个工厂模式....................................");
		SendFactory send2 = new SendFactory();
		Sender send2Sender1 = send2.ProductMail();
		send2Sender1.Send();
		Sender send2Sender2 = send2.ProductSms();
		send2Sender2.Send();

		/**
		 * 静态工厂方法模式，将上面的多个工厂模式里的方法设置为经纬的，不需要创建示例，直接调用即可
		 */
		System.out.println("开始静态工厂模式....................................");
		Sender staticSender1 = SendFactory.StaticProductMail();
		staticSender1.Send();

		Sender staticSender2 = SendFactory.StaticProductSms();
		staticSender2.Send();

		/**
		 * 工厂方法模式有一个问题就是，类的创建依赖工厂类，也就是说，
		 * 如果想要拓展程序，必须对工厂类进行修改，这违背了闭包原则，所以，从设计角度考虑，
		 * 有一定的问题，如何解决？就用到抽象工厂模式，创建多个工厂类， 
		 * 这样一旦需要增加新的功能，直接增加新的工厂类就可以了，不需要修改之前的代码。
		 */
		System.out.println("抽象工厂模式....................................");
		Provider providerMail = new SendMailFactory();
		Sender senderMail = providerMail.produce();
		senderMail.Send();
		
		Provider providerSms = new SendSmsFactory();
		Sender senderSms = providerSms.produce();
		senderSms.Send();
		
		Provider providerQQ = new SendQQFactory();
		Sender senderQQ = providerQQ.produce();
		senderQQ.Send();
	}

}
