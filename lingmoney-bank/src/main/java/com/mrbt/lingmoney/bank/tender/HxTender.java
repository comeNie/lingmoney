package com.mrbt.lingmoney.bank.tender;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * 投标
 * @author luox
 * @Date 2017年6月6日
 */
@Component
public class HxTender extends HxBaseData{
	private static final Logger logger = LogManager.getLogger(HxTender.class);
	
	/**
	 * 自动单笔投标返回
	 */
	public String generatinAsynsReply(Map<String, Object> asyncMsg) throws Exception {
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
			ele.addElement("RETURNCODE").setText("000000");;
			ele.addElement("RETURNMSG").setText("交易成功");;
			ele.addElement("OLDREQSEQNO").setText(asyncMsg.get("OLDREQSEQNO").toString());
			
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(doc.asXML());
			
			body.addElement("XMLPARA").setText(encryptXmlPara);
			
			//生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());
			
			//组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");
			logger.info("\nautoTenderOneAsynsReply_" + "生成的应答返回报文:\n" + content);
			
			return content;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 6.19	自动单笔投标（可选）(OGW00059)
	 * 
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630xP8ETd</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>161802</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P20220170630059JIF41XNT3UT</channelFlow>
		    <serverDate>20170630</serverDate>
		  </header>
		  <body>
		    <TRANSCODE>OGW00059</TRANSCODE>
		    <BANKID>GHB</BANKID>
		    <XMLPARA/>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>

	 */
	public Document autoTenderOne(String appid, String loanno, String acno, String acname, 
			String amount, String remark) throws Exception {
		String logGroup = "\nautoTenderOne_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + loanno + "\t" + 
				acno + "\t" + acname + "\t" + amount + "\t" + remark);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("LOANNO", loanno);
		paraMap.put("ACNO", acno);
		paraMap.put("ACNAME", acname);
		paraMap.put("AMOUNT", amount);
		paraMap.put("REMARK", remark);
		
		Document xml = createRequestXML(logGroup,"OGW00059", paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"自动单笔投标响应");
	}
	
	/**
	 * 6.18	自动投标授权撤销（可选）(OGW00058)
	 * 
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630lO9LVy</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>161506</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P20220170630058OSHIAX0EFBZ</channelFlow>
		    <serverDate>20170630</serverDate>
		  </header>
		  <body>
		    <TRANSCODE>OGW00058</TRANSCODE>
		    <BANKID>GHB</BANKID>
		    <XMLPARA/>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>
	 */
	public Document autoTenderAuthorizeCancel(String appid, String otpseqno,String otpno,
			String acno,String acname,String remark) throws Exception {
		String logGroup = "\nautoTenderAuthorizeCancel_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + otpseqno + "\t" + otpno + 
				"\t" + acno + "\t" + acname + "\t" + remark);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("OTPSEQNO", otpseqno);
		paraMap.put("OTPNO", otpno);
		paraMap.put("ACNO", acno);
		paraMap.put("ACNAME", acname);
		paraMap.put("REMARK", remark);
		
		Document xml = createRequestXML(logGroup,"OGW00058", paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"自动投标授权撤销响应");
	}
	
	
	/**
	 * 6.17	自动投标授权结果查询（可选）(OGW00057)
	 * 
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630jlov2y</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>161249</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P20220170630057UXMSBOKRO5L</channelFlow>
		    <serverDate>20170630</serverDate>
		  </header>
		  <body>
		    <TRANSCODE>OGW00057</TRANSCODE>
		    <BANKID>GHB</BANKID>
		    <XMLPARA>
		      <OLDREQSEQNO>P2P20220170630050AHFR93T6WOS</OLDREQSEQNO>
		    </XMLPARA>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>

	 */
	public Document autoTenderAuthorizeSearch(String appid, String oldreqseqno) throws Exception {
		String logGroup = "\nautoTenderAuthorizeSearch_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + oldreqseqno);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("OLDREQSEQNO", oldreqseqno);
		Document xml = createRequestXML(logGroup,"OGW00057", paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"自动投标授权结果查询响应");
	}
	
	/**
	 * 6.16	自动投标授权(OGW00056) （可选，跳转我行页面处理）
	 */
	public Map<String, String> autoTenderAuthorize(int clientType, String appid, String ttrans, 
			String acno, String acname, String remark, String returnurl) throws Exception {
		String transCode = null;
		
		if(clientType == 0){
			transCode = "OGW00056"; // pc
		}else if(clientType == 1){
			transCode = "OGW00183"; // mobile
		}
		
		Map<String, String> contentMap = new HashMap<String, String>();
		
		//生成日志头
		String logGroup = "\nautoTenderAuthorize" + System.currentTimeMillis() + "_";

		logger.info(logGroup + "请求参数:" + clientType + "\t" + appid + "\t" + ttrans + "\t" + 
				acno + "\t" + acname + "\t" + remark + "\t" + returnurl);
		
		//生成数据xml文件
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("MERCHANTNAME", MERCHANTNAME);
		paraMap.put("TTRANS", ttrans);
		paraMap.put("ACNO", acno);
		paraMap.put("ACNAME", acname);
		paraMap.put("REMARK", remark);
		paraMap.put("RETURNURL", returnurl);
		Document xml = createRequestXML(logGroup,transCode, paraMap);
		
		//转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		//生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		//组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		
		contentMap.put("requestData", content);
		
		contentMap.put("transCode", transCode);
		System.out.println("channelFlow:" + xml.selectSingleNode("//channelFlow").getText());
		return contentMap;
	}
	
	
	/**
	 * 6.15	投标优惠返回结果查询（可选）(OGW00055)
	 * 
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630vdJlFL</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>101928</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P20220170630055WWRMZ78MHN5</channelFlow>
		    <serverDate>20170630</serverDate>
		  </header>
		  <body>
		    <TRANSCODE>0</TRANSCODE>
		    <BANKID>0</BANKID>
		    <XMLPARA>
		      <RESJNLNO>P2P20220170630050AHFR93T6WOS</RESJNLNO>
		    </XMLPARA>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>
	 */
    /**
     * 此接口已废弃 （经银行方确认）
     */
    @Deprecated
	public Document tenderReturnSearch(String appid, String oldreqseqno,String subseqno) throws Exception {
		String logGroup = "\ntenderReturnSearch_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + oldreqseqno + "\t" + subseqno);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("OLDREQSEQNO", oldreqseqno);
		paraMap.put("SUBSEQNO", subseqno);
		Document xml = createRequestXML(logGroup,"OGW00055", paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"投标优惠返回结果查询响应");
	}
	
	/**
	 * 6.14	投标优惠返回（可选）(OGW00054)
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630zOsHQy</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>115534</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P202201706300546QIYPOSCRY3</channelFlow>
		    <serverDate>20170630</serverDate>
		  </header>
		  <body>
		    <TRANSCODE>OGW00054</TRANSCODE>
		    <BANKID>GHB</BANKID>
		    <XMLPARA/>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>
	 */
    /**
     * 此接口已废弃 （经银行方确认）
     */
    @Deprecated
	public Document tenderReturn(String appid, String loanno,String bwacname,
			String bwacno,String amount,String totalnum,List<Map<String, String>> feedbacklist) throws Exception {
		
		String logGroup = "\ncheckHxBalance_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + "******"
			+ "\t" + amount);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("LOANNO", loanno);
		paraMap.put("BWACNAME", bwacname);
		paraMap.put("BWACNO", bwacno);
		paraMap.put("AMOUNT", amount);
		paraMap.put("TOTALNUM", totalnum);
		//循环列表
		
		Document xml = createRequestXML(logGroup,"OGW00054", paraMap,feedbacklist);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"投标优惠返回响应");
	}
	
	private Document createRequestXML(String logGroup, String transCode, Map<String, String> para,
			List<Map<String, String>> feedbacklist) throws Exception {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");
		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");
		para.put("MERCHANTID", MERCHANTID);
		
		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(para,feedbacklist, logGroup);

		// 添加加密数据到XMLPARA中
		xmlPara.setText(encryptXmlPara);
		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		return document;
	}

	private String getDocParaEncryptStr(Map<String, String> para, List<Map<String, String>> feedbacklist, 
			String logGroup) throws Exception {
		Document doc = DocumentHelper.createDocument();
		Element docuEle = doc.addElement("Document");
		
		for (Map.Entry<String, String> entry : para.entrySet()) {
			String paraName = entry.getKey();
			String paraVal = entry.getValue();
			docuEle.addElement(paraName).setText(paraVal);
		}
		
		for (Map<String, String> feedBack : feedbacklist) {
			Element ele = docuEle.addElement("FEEDBACKLIST");
			for (Map.Entry<String, String> entry : feedBack.entrySet()) {
				String paraName = entry.getKey();
				String paraVal = entry.getValue();
				ele.addElement(paraName).setText(paraVal);
			}
		}
		
		logger.info(logGroup + "生成xml明文" + formatXml(docuEle.asXML()));
		// 加密
		String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docuEle.asXML());
		return encryptXmlPara;
	}

	/**
	 * 生成请求报文
	 */
	private Document createRequestXML(String logGroup,String transCode, 
			Map<String,String> para) throws Exception{
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");
		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");
		para.put("MERCHANTID", MERCHANTID);
		
		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(para, logGroup);

		// 添加加密数据到XMLPARA中
		xmlPara.setText(encryptXmlPara);
		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		return document;
	}

	/**
	 * 解析响应数据
	 */
	private Document decryptResponseXML(String message,String requestInfo) throws Exception{
		Map<String, String> splitContent = splitContent(message, requestInfo);
		
		if(!dataCheckSign(splitContent)) return null;
		
		String contentXml = splitContent.get("contentXml");
		
		SAXReader reader = new SAXReader();
		StringReader in = new StringReader(contentXml);
		
		Document doc = reader.read(in);
		Element ele = (Element) doc.selectSingleNode("//errorCode");
		String text = ele.getText();
		if(!"0".equals(text)){
			Element errorMsg = (Element) doc.selectSingleNode("//errorMsg");
			throw new RuntimeException(errorMsg.getText());
		}
		return decryptRetrunMessage(contentXml);
	}
	
	/**
	 * 解析银行请求的xml
	 */

	public Document handleAutoTenderAuthorizeMsg(String xml) {
		// 生成日志头
		String logGroup = "nautoTenderAuthorize_" + System.currentTimeMillis() + "_";

		// 解析异步报文
		return analysisAsyncMsg(xml, logGroup);
	}

}
