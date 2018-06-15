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
 * 开通华兴E账号
 * 
 * @author ruochen.yu
 *
 */
@Component
public class HxAccountOpen extends HxBaseData {
	
	private static final Logger logger = LogManager.getLogger(HxAccountOpen.class);
	
	//打印日志头
	private static String logHeard = "\nHxAccountOpen_";
	
	private static String TRANSCODE_PC = "OGW00042";
	
	private static String TRANSCODE_MOBILE = "OGW00090";
	
	private static String QUERY_TRANSCODE = "OGW00043";
	
	/**
	 * 发送开通E账号请求 requestType 请求客户端类型
	 * @param email 邮箱
	 * @param mobile 手机号
	 * @param idNo 身份证号
	 * @param acName 姓名
	 * @param appId 应用标识
	 * @param uId 用户ID
	 * @param returnUrl 
	 * @param args
	 * @return
	 */
	public Map<String, String> requestAccountOpen(int clientType, String appId, String acName, String idNo, String mobile, String email, String uId, String returnUrl) {
		String transCode = "";
		
		if(clientType == 0){
			transCode = TRANSCODE_PC;
		}else{
			transCode = TRANSCODE_MOBILE;
		}
		
		Map<String, String> contentMap = new HashMap<String, String>();
		
		//生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";

		logger.info(logGroup + "请求参数:" + clientType + "\t" + appId + "\t" + acName + "\t" + idNo.substring(0, 4) + "******" + idNo.substring(14) + "\t" + mobile.substring(7) + "\t" + uId);
		
		//生成数据xml文件
		Document xml = createHxAccountOpenXml(clientType, appId, acName, idNo, mobile, email, logGroup, uId, returnUrl, transCode);
		
		//转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		//生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		
		logger.info(logGroup + "生成私钥的签名");
		//组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		
		contentMap.put("requestData", content);
		
		if(clientType == 0){
			contentMap.put("transCode", TRANSCODE_PC);
		}else{
			contentMap.put("transCode",  TRANSCODE_MOBILE);
		}
		
		//拆分报文
		Map<String, String> resMap = splitContent(contentMap.get("requestData"), "解析异步通知报文");
		//解析请求数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		logger.info(logGroup + "请求报文验签：\n" + signCheck);
		
		//获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);
		
		return contentMap;
	}

	/**
	 * 生成xml
	 * @param clientType	客户端类型
	 * @param appId	应用标识
	 * @param acName	姓名
	 * @param idNo	身份证号
	 * @param mobile	手机号
	 * @param email	邮箱
	 * @param logGroup	日志头
	 * @param uId	用户ID
	 * @param accountOpenReturnUrl	返回商户URL
	 * @param transCode	交易码
	 * @return
	 */
	private static Document createHxAccountOpenXml(int clientType, String appId, String acName, String idNo, String mobile, String email, String logGroup, String uId, String returnUrl, String transCode) {
		Document document = getDocHeardElement(logGroup, transCode);
		
		String channelFlow = queryTextFromXml(document.asXML(), "channelFlow");
		
		Element body = document.getRootElement().addElement("body");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");
		
		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("APPID").setText(appId);
		docu.addElement("TTRANS").setText("6");
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("ACNAME").setText(acName);
		docu.addElement("IDTYPE").setText("1010");
		docu.addElement("IDNO").setText(idNo);
		docu.addElement("MOBILE").setText(mobile);
		docu.addElement("EMAIL").setText("");
		docu.addElement("RETURNURL").setText(returnUrl + uId + "/" + channelFlow);
		docu.addElement("CUSTMNGRNO").setText(CUSTMNGRNO);
		
		logger.info(logGroup + "生成xml明文" + formatXml(document.asXML()) + "\n" + formatXml(docu.asXML()));
		
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
	 * 解析异步通知报文
	 */
	public Document accountOpenAsyncMsg(String message){
		//拆分报文
		Map<String, String> resMap = splitContent(message, "解析异步通知报文");
		//解析到的数据进行验签
		boolean signCheck = dataCheckSign(resMap); 
		System.out.println("数据验签:" + signCheck);
		if(signCheck){
			//解密数据
			Document document = decryptRetrunMessage(resMap.get("contentXml"));   
			if(document != null){
				return document;
			}
		}
		return null;
	}

	/**
	 * 生成测试异步应答报文
	 * @param channelFlow 
	 * @param transCode 
	 */
	public String generatingAsyncMsg(String channelFlow, String transCode){
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
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170722090QUV5Z8H2G02");
		ele1.addElement("ACNAME").addText("段增瑞");
		ele1.addElement("IDTYPE").addText("1010");
		ele1.addElement("IDNO").addText("1101*********2014");
		ele1.addElement("MOBILE").addText("135****0357");
		ele1.addElement("ACNO").addText("8970660100000266507");
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(ele1.asXML());
			
			xmlPara.addText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML());
		//组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + document.asXML();
		
//		//拆分报文
//		Map<String, String> resMap = splitContent(content, "解析异步通知报文");
//		//解析到的数据进行验签
//		boolean signCheck = dataCheckSign(resMap);
//		System.out.println("数据验签通过:" + signCheck);
		
		return content;
	}
	
	/**
	 * 生应答报文返回
	 * @param asyncMsg
	 * @param loggroup 
	 * @return
	 */
	public String generatinAsynsReply(Map<String, Object> asyncMsg, String loggroup, boolean t) {
		
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());
		
		try {
			
			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText(asyncMsg.get("TRANSCODE").toString());
			header.addElement("channelFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("serverFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());
			
			Element body = root.addElement("body");
			body.addElement("TRANSCODE").setText("");
			
			Document doc = DocumentHelper.createDocument();
			
			Element ele = doc.addElement("XMLPARA");
			
			if(t){
				ele.addElement("RETURNCODE").setText("000000");;
				ele.addElement("RETURNMSG").setText("交易成功");;
			}else{
				ele.addElement("RETURNCODE").setText("000001");;
				ele.addElement("RETURNMSG").setText("交易失败");;
			}
			ele.addElement("OLDREQSEQNO").setText(asyncMsg.get("channelFlow").toString());
			
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(doc.asXML());
			
			body.addElement("XMLPARA").setText(encryptXmlPara);
			
			//生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());
			
			//组成报文
			String content = CONTENT_HEARED_NOASYN + privateResult + reply.asXML().replaceAll("\n", "");
			logger.info(loggroup + "生成的应答返回报文:\n" + content);
			
			return content;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询账户开立结果
	 * @param olderQseqNo	原账户开立交易报文头的渠道流水号
	 * @param appId	应用标识
	 * @return
	 */
	public Map<String, Object> queryAccountOpen(String olderQseqNo, String appId, String logGroup, String url){
		String content = createQueryAccoutOpenXml(olderQseqNo, appId, logGroup);
		//访问银行接口
		String resXml = SendMessage.sendBankMessage(content, url);
//		String resXml = SendMessage.sendBankMessageTest(content, url);
		logger.info(logGroup + "查询的返回报文:\n" + resXml);
		//解析报文
		Document document =  accountOpenAsyncMsg(resXml);
		System.out.println(formatXml(document.asXML()));
		return xmlToMap(document);
	}

	/**
	 * 创建请求查询账户开立结果xml
	 * @param olderQseqNo
	 * @param appId
	 * @return
	 */
	private String createQueryAccoutOpenXml(String olderQseqNo, String appId, String logGroup) {
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
		String privateResult = MyRSA.getDataSignature(document.asXML().replaceAll("\n", ""));
		
		logger.info(logGroup + "生成私钥的签名");
		//组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + document.asXML().replaceAll("\n", "").replaceAll("\r", "");
		logger.info(logGroup + "生成的请求报文\n" + content);
		
		return content;
	}

	public static void main(String[] args) {
		new HxAccountOpen().queryAccountOpen("P2P20220171115042XD0CGJDWXZ3", "PC", "AAAAAAAAAAAAAAAAA", "https://ogws.ghbank.com.cn/extService/ghbExtService.do");
	}
}


