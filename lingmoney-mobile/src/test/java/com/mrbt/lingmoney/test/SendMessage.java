package com.mrbt.lingmoney.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.mrbt.lingmoney.bank.bid.HxSingleBidding;
import com.mrbt.lingmoney.bank.deal.HxSinglePayment;
import com.mrbt.lingmoney.bank.eaccount.HxAccountOpen;
import com.mrbt.lingmoney.bank.eaccount.HxAccountRecharge;
import com.mrbt.lingmoney.bank.repayment.HxAutoRepaymentAuthorization;
import com.mrbt.lingmoney.bank.tiedcard.HxTiedCard;
import com.mrbt.lingmoney.bank.withdraw.HxCashWithdraw;

/**
 * 华兴银行发送请求
 * @author Administrator
 *
 */
public class SendMessage {
	
	public static void main(String[] args) {
//		accountOpenCallBack();
//		bindCardCallBack();
//		accountRechargeCallBack();
//		cashWithdrawCallBack();
//		singleBiddingCallBack();
//		receivePaymentResult();
		autoRepaymentAuthCallBack();
	}
	
	public static void autoRepaymentAuthCallBack(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0011";//交易码

		String accountXml = new HxAutoRepaymentAuthorization().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}
	
	public static void receivePaymentResult(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0008";//交易码

		String accountXml = new HxSinglePayment().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}
	
	public static void singleBiddingCallBack(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0005";//交易码

		String accountXml = new HxSingleBidding().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}

	
	public static void cashWithdrawCallBack(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0004";//交易码

		String accountXml = new HxCashWithdraw().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}

	
	public static void accountRechargeCallBack(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0003";//交易码

		String accountXml = new HxAccountRecharge().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}

	
	public static void bindCardCallBack(){
		String channelFlow = "ASADASDD"; //请求流水号
		String transCode = "OGWR0002";//交易码

		String accountXml = new HxTiedCard().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, ""));
	}

	
	/**
	 * 账户开立异步应答,只有当成功的时候才有返回
	 */
	public static void accountOpenCallBack(){
		String channelFlow = "P2P00120170607090BWWH40SF71A"; //请求流水号
		String transCode = "OGWR0001";//交易码

		String accountXml = new HxAccountOpen().generatingAsyncMsg(channelFlow, transCode);
		
		System.out.println("生成的异步应答报文:" + accountXml);
		
		System.out.println("应答返回报文:\n" + sendMessage(accountXml, "630010BAE66B251903CC6357D9DF0B24"));
	}
	
	/**
	 * 模拟异步交易发送报文
	 */
	public static String sendMessage(String content, String note) {
		
		String url = "http://10.10.12.84:8080";
	
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

