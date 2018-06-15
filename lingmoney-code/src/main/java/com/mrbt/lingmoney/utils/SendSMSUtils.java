package com.mrbt.lingmoney.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class SendSMSUtils {

	private String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private String account;
	private String password;

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static void main(String[] args) {
		SendSMSUtils ssm = new SendSMSUtils();
		ssm.setAccount("cf_rbyt");
		ssm.setPassword("DjwXrS");
		
		System.out.println(ssm.sendSMS("13161170904", "您正在注册领钱儿，您的短信验证码是111111，为了保障您的账号安全，验证短信请勿泄露给其他人，此验证码2分钟内有效，请尽快使用。"));
	}

	public boolean sendSMS(String mobile, String content) {

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(getUrl());
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		NameValuePair[] data = { new NameValuePair("account", getAccount()),
				new NameValuePair("password", getPassword()), new NameValuePair("mobile", mobile),
				new NameValuePair("content", content), };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
			if ("2".equals(code)) {
				return true;
			} else {
				return false;
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}