package com.mrbt.lingmoney.bank.transferdebt;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
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
 * 债权转让
 * @author luox
 * @Date 2017年6月8日
 */
@Component
public class TransferDebt extends HxBaseData{
	private static final Logger logger = LogManager.getLogger(TransferDebt.class);
	
	/**
	 * 债权转让异步应答
	 */
	public String generatinAsynsReply(Map<String, Object> asyncMsg) {
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
	 * 6.23	债券债权转让结果查询(OGW00062)
	 * 
	 * <Document>
		  <header>
		    <encryptData>N</encryptData>
		    <serverFlow>OGW0120170630w9i6I8</serverFlow>
		    <channelCode>GHB</channelCode>
		    <status>testMode</status>
		    <serverTime>162407</serverTime>
		    <errorCode>0</errorCode>
		    <errorMsg/>
		    <channelFlow>P2P20220170630062E1YO2BSA1CE</channelFlow>
		    <serverDate>20170630</serverDate>
		    <transCode>OGW00062</transCode>
		  </header>
		  <body>
		    <TRANSCODE>0</TRANSCODE>
		    <BANKID>0</BANKID>
		    <XMLPARA>
		      <OLDREQSEQNO>ada</OLDREQSEQNO>
		    </XMLPARA>
		    <MERCHANTID>LQE</MERCHANTID>
		  </body>
		</Document>

	 */
	public Document transferDebtAssignmentSearch(String appid, String oldreqseqno) throws Exception {
		String logGroup = "\nautoTenderOne_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + oldreqseqno);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("OLDREQSEQNO", oldreqseqno);
		
		Document xml = createRequestXML(logGroup,"OGW00062", paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"债券债权转让结果查询响应");
	}
	
	/**
	 * 6.22	债券债权转让申请(OGW00061)creditor （可选，跳转我行页面处理）
	 */
	public Map<String, String> transferDebtAssignment(int clientType, String appid, String ttrans, 
			String oldreqseqno, String oldreqnumber, String oldreqname, String accno, 
			String custname, String amount, String preincome, String remark,String returnurl) throws Exception {
		String transCode = null;
		
		if(clientType == 0){
			transCode = "OGW00061";
		}else if(clientType == 1){
			transCode = "OGW00096";
		}
		
		Map<String, String> contentMap = new HashMap<String, String>();
		
		//生成日志头
		String logGroup = "\nclaimsAssignment_" + System.currentTimeMillis() + "_";

		logger.info(logGroup + "请求参数:" + clientType + "\t" + appid + "\t" + ttrans + "\t" + 
				oldreqseqno + "\t" + oldreqnumber + "\t" + oldreqname + "\t" + accno + "\t" + 
				custname + "\t" + amount + "\t" + preincome + "\t" + remark + "\t" + returnurl);
		
		//生成数据xml文件
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
		paraMap.put("TTRANS", ttrans);
		paraMap.put("OLDREQSEQNO", oldreqseqno);
		paraMap.put("OLDREQNUMBER", oldreqnumber);
		paraMap.put("OLDREQNAME", oldreqname);
		paraMap.put("ACCNO", accno);
		paraMap.put("CUSTNAME", custname);
		paraMap.put("AMOUNT", amount);
		paraMap.put("PREINCOME", preincome);
		paraMap.put("REMARK", remark);
		paraMap.put("RETURNURL", returnurl);
		
		Document xml = createRequestXML(logGroup,transCode, paraMap);
		
		//转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		//生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		//组成报文
		String content = "001X11          00000256" + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		
		contentMap.put("requestData", content);
		
		contentMap.put("transCode", transCode);
		
		System.out.println("channelFlow:" + xml.selectSingleNode("//channelFlow").getText());
		return contentMap;
	}

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

	public Document handleAutoTenderAuthorizeMsg(String xml) {
		// 生成日志头
		String logGroup = "nautoTenderAuthorize_" + System.currentTimeMillis() + "_";

		// 解析异步报文
		return analysisAsyncMsg(xml, logGroup);
	}

}
