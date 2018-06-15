package com.mrbt.lingmoney.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 华兴银行发送请求
 * @author Administrator
 *
 */
public class SendMessage {
	public static void main(String[] args) {
		accountOpenCallBack();
	}
	
	/**
	 * 账户开立异步应答,只有当成功的时候才有返回
	 */
	public static void accountOpenCallBack(){
		
		String accountXml = "001X11          000002560594665699886F112328C1CE0153C966B89AD9F26F9EE9C0A727E8D7604991B6A94E5271B289C1CF5B35EFADFB9A0AE2BAD5E6662734185CBB2540ABC8F47D0EEB777CCA86077034F1323B17136D8474C23DB70CC3613E7F2B06196721EAB04E83127633B7334D32DCF2EE703E0B67CE850A96045D2D25D600D4733EED637A73<?xml version=\"1.0\" encoding=\"UTF-8\"?><Document><header><channelCode>GHB</channelCode><channelFlow>OGW0120180122zqaJLw</channelFlow><channelDate>20180122</channelDate><channelTime>144026</channelTime><encryptData></encryptData></header><body><MERCHANTID>LQE</MERCHANTID><BANKID>GHB</BANKID><TRANSCODE>OGWR0001</TRANSCODE><XMLPARA>AUlvsJBoOMWZUDFk0YOdWItYan2hfetM90hwplzmZy8RbEKoE87tAdfCK9OLYEaVW3s6BxCnKqsQtBPtwSa54e2Cn53TwUH1yM541HFX8uSvY9EULw9Bx4s1DRza73Pthked3syvFeaMyX86IVXt0RIJlPAST1tOALYNHD5c/EHHHRMDd0aB1BsnSkssBLFGeLESCvXbauyCTjrj+7ShpuyZbojESFkDC5NQjbapBJP69taa5rFwTqqezBOZLAxSwNQWiAAltT4=</XMLPARA></body></Document>";
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, "P2P20220180122090KF73V2Y1N33"));
	}
	
	/**
	 * 模拟异步交易发送报文
	 */
	public static String sendMessage(String content, String note) {
		
//		String url = "https://103.235.254.242:8443/";
		String url = "http://10.10.12.76:8443/";
	
		URL postUrl = null;
		
		BufferedReader buffer = null;
		HttpURLConnection urlcon = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			//content为加密签名后的报文
			
			postUrl = new URL(url); //URL请求地址
			urlcon =  (HttpURLConnection) postUrl.openConnection();
			
			int contentLength = content.getBytes().length;  //获取报文长度
			urlcon.setConnectTimeout(1000*15);
			urlcon.setReadTimeout(1000*60*2);
			urlcon.setRequestMethod("POST");  //post请求方式
			urlcon.setUseCaches(false);       //post请求不能使用缓存
			urlcon.setRequestProperty("Content-Length", String.valueOf(contentLength));
			urlcon.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");
			urlcon.setDoInput(true);  //默认为true
			urlcon.setDoOutput(true); //默认为true
			urlcon.connect();       //urlcon.getOutputStream()会隐含的进行connect();
			DataOutputStream output =  new DataOutputStream(urlcon.getOutputStream());
			output.writeBytes(content);
			output.flush();
			output.close();
			
			buffer =new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
			String str = null;
			while((str=buffer.readLine())!=null){
				strBuffer.append(str);
			}
			System.out.println("返回报文为： " + strBuffer);

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}finally{
			try {
				if (buffer != null) {
					buffer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (urlcon != null) {
				urlcon.disconnect();
			}
		}
		
		return strBuffer.toString();
	}
}

