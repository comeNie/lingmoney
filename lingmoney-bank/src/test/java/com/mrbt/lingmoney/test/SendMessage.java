package com.mrbt.lingmoney.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mrbt.lingmoney.bank.eaccount.HxAccountOpen;

/**
 * 华兴银行发送请求
 * @author Administrator
 *
 */
public class SendMessage {
	
	private static final Logger logger = LogManager.getLogger(SendMessage.class);
	
	public static void main(String[] args) {
		
		String channelFlow = "P2P00120170606090P2ZPS2BAQ7J"; //请求流水号
		String transCode = "OGWR0001";//交易码   PC端:OGW00042 移动端：OGW00090

		String accountXml = new HxAccountOpen().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, "630010BAE66B251903CC6357D9DF0B24"));
	}
	
	/**
	 * 模拟同步交易发送报文
	 */
	public static String sendMessage(String content, String note) {
	
		URL postUrl = null;
		
		BufferedReader buffer = null;
		HttpURLConnection urlcon = null;
		StringBuffer strBuffer = new StringBuffer();
		try {
			//content为加密签名后的报文
			
			postUrl = new URL("http://192.168.1.155:8080/bank/accountOpenCallBack/" + note); //URL请求地址
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

