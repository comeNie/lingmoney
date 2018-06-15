package com.mrbt.lingmoney.bank.eaccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * E账号充值
 * @author ruochen.yu
 *
 */
@Component
public class HxAccountRecharge extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxAccountOpen.class);

	// 打印日志头
	private static String logHeard = "\nHxAccountRecharge_";

	private static final String TRANSCODE_PC = "OGW00045";

	private static final String TRANSCODE_MOBILE = "OGW00092";
	
	private static final String QUERY_TRANSCODE = "OGW00046";

	public static void main(String[] args) {
		int clientType = 1; // 0:pc 1：mobile
		String appId = "APP"; //
		String uId = "630010BAE66B251903CC6357D9DF0B24";
		String returnUrl = "aaaaaaaaaaaaaaaaaa";
		String acNo = "1234567894534851445";
		String acName = "张晓";
		String amount = "100.00";
		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";
		Map<String, String> contentMap = new HxAccountRecharge().accountRecharge(clientType, appId, uId, returnUrl,
				acNo, acName, amount, logGroup);
	}

	/**
	 * 账户充值请求
	 * @param clientType
	 * @param appId
	 * @param uId
	 * @param returnUrl
	 * @param acNo
	 * @param acName
	 * @param amount
	 * @return
	 */
	public Map<String, String> accountRecharge(int clientType, String appId, String uId, String returnUrl, String acNo,String acName, String amount, String logGroup) {
		String transCode = "";

		if (clientType == 0) {
			transCode = TRANSCODE_PC;
		} else {
			transCode = TRANSCODE_MOBILE;
		}

		Map<String, String> contentMap = new HashMap<String, String>();


		// 生成数据xml文件
		Document xml = createHxAccountRechargeXml(clientType, appId, logGroup, uId, returnUrl, transCode, acNo, acName,
				amount);

		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");

		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);

		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		contentMap.put("requestData", content);

		if (clientType == 0) {
			contentMap.put("transCode", TRANSCODE_PC);
		} else {
			contentMap.put("transCode", TRANSCODE_MOBILE);
		}

		// 拆分报文
		Map<String, String> resMap = splitContent(contentMap.get("requestData"), "解析异步通知报文");
		// 解析到的数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		System.out.println(signCheck);

		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);

		return contentMap;
	}

	/**
	 * 生成请求xml
	 * @param clientType
	 * @param appId
	 * @param logGroup
	 * @param uId
	 * @param returnUrl
	 * @param transCode
	 * @param acNo
	 * @param acName
	 * @param amount
	 * @return
	 */
	private Document createHxAccountRechargeXml(int clientType, String appId, String logGroup, String uId,
			String returnUrl, String transCode, String acNo, String acName, String amount) {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("APPID").setText(appId);
		docu.addElement("TTRANS").setText("7");
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("ACNAME").setText(acName);
		docu.addElement("AMOUNT").setText(amount);
		docu.addElement("REMARK").setText("");
		docu.addElement("RETURNURL").setText(returnUrl + queryTextFromXml(document,"channelFlow"));

		logger.info(logGroup + "生成xml明文" + formatXml(document.asXML()) + "\n" + formatXml(document2.asXML()));
		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	/**
	 *  数据验签
	 * @param xml
	 * @return
	 */
	public Document accountRechargeAsyncMsg(String message) {
		//拆分报文
		Map<String, String> resMap = splitContent(message, "解析异步通知报文");
		//解析到的数据进行验签
		boolean signCheck = dataCheckSign(resMap); 
		if(signCheck){
			//解密数据
			Document document = decryptRetrunMessage(resMap.get("contentXml"));   
			if(document != null){
				return document;
			}
		}
		return null;
	}

	public String generatinAsynsReply(Map<String, Object> resMap, String loggroup) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());
		
		try {
			
			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText(resMap.get("TRANSCODE").toString());
			header.addElement("channelFlow").setText(resMap.get("channelFlow").toString());
			header.addElement("serverFlow").setText(resMap.get("channelFlow").toString());
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());
			
			Element body = root.addElement("body");
			body.addElement("TRANSCODE").setText("");
			
			Document doc = DocumentHelper.createDocument();
			
			Element ele = doc.addElement("XMLPARA");
			ele.addElement("RETURNCODE").setText("000000");;
			ele.addElement("RETURNMSG").setText("交易成功");;
			ele.addElement("OLDREQSEQNO").setText("aaaaaaaaaaa");
			
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(doc.asXML());
			
			body.addElement("XMLPARA").setText(encryptXmlPara);
			
			//生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());
			
			//组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");
			logger.info(loggroup + "生成的应答返回报文:\n" + content);
			
			return content;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Map<String, Object> queryAccountRecharge(String olderQseqNo, String appId, String url, String logGroup) {
		String content = createQueryAccoutRechargeXml(olderQseqNo, appId, logGroup);
		//访问银行接口
		String resXml = SendMessage.sendBankMessage(content, url);
		//解析报文
		Document document =  accountRechargeAsyncMsg(resXml);
		return xmlToMap(document);
	}

	private String createQueryAccoutRechargeXml(String olderQseqNo, String appId, String logGroup) {
		Document document = getDocHeardElementQuery(logGroup, QUERY_TRANSCODE, olderQseqNo);
		
		Element body = document.getRootElement().addElement("body");
		
		body.addElement("TRANSCODE").setText(QUERY_TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");
		
		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("APPID").setText(appId);
		docu.addElement("OLDREQSEQNO").setText(olderQseqNo);

		logger.info(logGroup + "生成xml明文" + document2.asXML());
		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info(logGroup + "生成xml密文\n"+ encryptXmlPara +"\n添加到XMLPARA中");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info(logGroup + "\n" + document.asXML());
		
		//生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML());
		
		logger.info(logGroup + "生成私钥的签名");
		//组成报文
		String content = "001X11          00000256" + privateResult + document.asXML().replace("\n", "");
		logger.info(logGroup + "生成的请求报文\n" + content);
		
		return content;
	}
	
	/**
	 * 模拟查询账户充值结果报文
	 * @param toMap 
	 * @param toMap 
	 * @param content
	 * @param url
	 * @return
	 */
	private String sendBankMessageTest(Map<String, Object> toMap) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());
		
		try {
			
			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText(toMap.get("TRANSCODE").toString());
			header.addElement("channelFlow").setText(toMap.get("channelFlow").toString());
			header.addElement("serverFlow").setText(toMap.get("channelFlow").toString());
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());
			
			Element body = root.addElement("body");
			body.addElement("TRANSCODE").setText(toMap.get("TRANSCODE").toString());
			body.addElement("MERCHANTID").addText(MERCHANTID);
			body.addElement("BANKID").setText(BANKID);
			
			// 生成要加密的xml，XMLPARA区域
			Document document2 = DocumentHelper.createDocument();
			Element docu = document2.addElement("Document");
			docu.addElement("OLDREQSEQNO").setText(toMap.get("channelFlow").toString());//原交易流水号
			docu.addElement("RESJNLNO").setText("AAAAAAAAAAAAAAAA");
			docu.addElement("TRANSDT").setText(hbdu.getNowDate());
			docu.addElement("TRANSTM").setText(hbdu.getNowTime());
			docu.addElement("RETURN_STATUS").setText("S");
			docu.addElement("ERRORMSG").setText("");
			
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			
			body.addElement("XMLPARA").setText(encryptXmlPara);
			
			//生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());
			
			//组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");
			
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generatingAsyncMsg(String channelFlow, String transCode) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");
		
		header.addElement("channelCode").addText("GHB");
		header.addElement("TRANSCODE").addText(transCode);
		header.addElement("channelFlow").addText(channelFlow);
		header.addElement("serverFlow").addText(channelFlow);
		header.addElement("serverDate").addText(hbdu.getNowDate());
		header.addElement("serverTime").addText(hbdu.getNowTime());
		header.addElement("status").addText("0");
		header.addElement("errorCode").addText("0");
		header.addElement("errorMsg").addText("");
		
		Element body = root.addElement("body");
		
		body.addElement("MERCHANTID").addText("XJP");
		body.addElement("BANKID").addText(BANKID);
		body.addElement("TRANSCODE").addText(transCode);
		
		Element xmlPara = body.addElement("XMLPARA");
		
		Document doc = DocumentHelper.createDocument(); 
		
		Element ele1 = doc.addElement("Document");
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170716092AMFECG5T9HL");
		ele1.addElement("ORDERSTATUS").addText("ORDER_COMPLETED");
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(ele1.asXML());
			
			xmlPara.addText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML());
		//组成报文
		String content = "001X12          00000256" + privateResult + document.asXML();
		
		return content;
	}
}
