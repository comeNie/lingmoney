package com.mrbt.lingmoney.bank.utils;

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
		sendMessage("001X11          0000025683D42520B2517A4E0061131444B3645D7465529282E30B535353ABC89BE0472B17195B08A564B56C87FA7802537632DCF247A1ADF10524CBF38F24B296BC8CA7B10E2D013DD80AE31EC17954232CCB560A5452088F8EAB71FB35BC168F6EDF889068F6BB817C721CA578371729CEFB23F25A65557093B6B2991EFAF6C2816D5E<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<Document><header><channelCode>P2P202</channelCode><channelFlow>P2P20220170629051B7T88CX6QZX</channelFlow><channelDate>20170629</channelDate><channelTime>171812</channelTime><encryptData></encryptData></header><body><TRANSCODE>OGW00051</TRANSCODE><XMLPARA></XMLPARA></body></Document>");
	}
	
	public static String sendBankMessageTest(String content, String url){
		return "001X11          0000025695127A3128629D7D8545F190A442A44616AC6F5709177DD5B5F1B44F7DC1D846652183B9B30E98224454F21E6E620589EEAA6D9106499245BE22C296D4EA420AC295D10D1A441B768B3489C1E8C724973A841608D9BE27C79E98F1A1C6968038DB55EF7900DA2340A794492E0AF098598D13A3A701607CCCBDEA516DDDBC69F5<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Document><header><encryptData></encryptData><serverFlow>OGW0120170725100000000117476829</serverFlow><channelCode>GHB</channelCode><status>0</status><serverTime>155759</serverTime><errorCode>0</errorCode><errorMsg>success</errorMsg><channelFlow>P2P20220170725043TAU2NKD3WHG</channelFlow><serverDate>20170725</serverDate><transCode>OGW00043</transCode></header><body><TRANSCODE>OGW00043</TRANSCODE><BANKID>GHB</BANKID><XMLPARA>yA+9ZoSeRfgdw1FJDHo3D7XcomDEUdXUqw7fGDsAr6w2CmaUCmKhl6PViW8ptHX1s6KVHza8yJE8MD/5WoEpUKDSksJ87WGLRKiScrpTO4ZHTXXIL7VC6ddUt4+r/EQH4pTFW9iehHZ7cyQ241hDBsYttO9K/UsIUyjtRRNfMVI56s0F2V/gVjucaLcBykUjAUlvsJBoOMWZUDFk0YOdWMNm1Kn6NunA8iaQsxAxO5TkJW23eSl+vSDEL3J5xxgeW3s6BxCnKqvWghjATD5JKuOjq8+acvoY5TixVXdNDRrHhN7PQNida0V0N6EJMRy09d9fjLIQTHEiCId7CMO1xSZTVyv3J1+Q6BNN2e3ewGosAASFC0UfiQKmdavplAtKLLZhpAxOuUIQLw7GGOabwulnwus9ylQH</XMLPARA><MERCHANTID>LQE</MERCHANTID></body></Document>";
		
	}
	
	/**
	 * 模拟同步交易发送报文
	 */
	public static String sendBankMessage(String content, String url) {
	
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
			//urlcon.connect();       //urlcon.getOutputStream()会隐含的进行connect();
			DataOutputStream output =  new DataOutputStream(urlcon.getOutputStream());
			output.writeBytes(content);
			output.flush();
			output.close();
			
			buffer =new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
			String str = null;
			while((str=buffer.readLine())!=null){
				strBuffer.append(str);
			}

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
	
	/**
	 * 模拟同步交易发送报文
	 */
	public static String sendMessage(String content) {
		String url = PropertiesUtil.getPropertiesByKey("BANK_POST_URL");
		URL postUrl = null;
		
		BufferedReader buffer = null;
		HttpURLConnection urlcon = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			//content为加密签名后的报文
			
			postUrl = new URL(url); // URL请求地址
			
			// 挡板
			//postUrl = new URL("http://183.63.131.106:40015/extServiceTest/ghbExtService.do"); //URL请求地址
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
			//urlcon.connect();       //urlcon.getOutputStream()会隐含的进行connect();
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
	
	
	/**
	 * 模拟同步交易发送报文
	 */
	public static String testSendMessage(String content) {
		URL postUrl = null;
		BufferedReader buffer = null;
		HttpURLConnection urlcon = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			//content为加密签名后的报文
			
			postUrl = new URL("http://192.168.1.153:8081/bank/accountOpenCallBack"); //URL请求地址
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
			//urlcon.connect();       //urlcon.getOutputStream()会隐含的进行connect();
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

