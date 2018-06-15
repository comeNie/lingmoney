package com.mrbt.lingmoney.bank.messagecode;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * 6.1	获取短信验证码
 * @author luox
 * @Date 2017年6月5日
 */
@Component
public class HxMessageCode extends HxBaseData{
	
	public static final String transCode = "OGW00041";

	private static final Logger logger = LogManager.getLogger(HxMessageCode.class);
	
	public static void main(String[] args) {
		try {
			HxMessageCode hxMessageCode = new HxMessageCode();
			String APPID = "APP";//应用标识
			String ACNAME = "喻龙";//用户姓名
			String ACNO = "6236882280001844773";//银行账号
			String TRSTYPE = "1";//操作类型，1：自动投标撤销   2：自动还款授权撤销    0：默认
			String MOBILE_NO = "13683173295";//手机号
			Document document = hxMessageCode.getHxMessageCode(APPID, ACNAME, TRSTYPE, ACNO, MOBILE_NO);
			System.out.println("==============================");
			System.out.println(document.asXML());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 6.1	获取短信验证码(OGW00041)
	 * 
	 * <Document>
			<header>
				<encryptData/>
				<serverFlow>OGW0120170706100000000027418335</serverFlow>
				<channelCode>GHB</channelCode>
				<status>0</status>
				<serverTime>143758</serverTime>
				<errorCode>0</errorCode>
				<errorMsg>success</errorMsg>
				<channelFlow>P2P20220170706041QP94ISZHQAU</channelFlow>
				<serverDate>20170706</serverDate>
				<transCode>OGW00041</transCode>
			</header>
			<body>
				<TRANSCODE>OGW00041</TRANSCODE>
				<BANKID>GHB</BANKID>
				<XMLPARA>
					<OTPSEQNO>PAD2017070614355200086919</OTPSEQNO>
					<EXT_FILED2/>
					<OTPINDEX>07</OTPINDEX>
					<EXT_FILED3/>
					<EXT_FILED1/>
				</XMLPARA>
				<MERCHANTID>LQE</MERCHANTID>
			</body>
		</Document>

	 */
	public Document getHxMessageCode(String appid, String acname, String trstype
			, String acno, String mobile_no) throws Exception {
		// 生成日志头
		String logGroup = "\ngetHxMessageCode_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appid + "\t" + acno.substring(0, 4) + "******"
				+ acno.substring(acno.length() - 4, acno.length()) + "\t" + trstype + "\t" + mobile_no);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appid);
//		paraMap.put("ACNAME", acname);
		paraMap.put("TRSTYPE", trstype);
		paraMap.put("ACNO", acno);
		paraMap.put("MOBILE_NO", mobile_no);
		
		Document xml = createRequestXML(logGroup, transCode, paraMap);
		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
	
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"获取短信验证码响应");
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
}
