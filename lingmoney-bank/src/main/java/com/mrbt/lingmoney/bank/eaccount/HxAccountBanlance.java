package com.mrbt.lingmoney.bank.eaccount;

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
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;

/**
 * 华兴账户余额
 * @author luox
 * @Date 2017年6月5日
 */
@Component
public class HxAccountBanlance extends HxBaseData{

	private static final Logger logger = LogManager.getLogger(HxAccountBanlance.class);
	
	private static final String QUERY_TRANSCODE = "OGW00049";
	
	private static final String CHAECK_TRANSCODE = "OGW00050";
	
	/**
	 * 6.10 账户余额校检(OGW00050) 
	 * 校验结果： 000000  	余额充足 540026 	余额为零 540009 	余额不足 540008
	 * 账户没有关联 OGW001 账户与户名不匹配
	 */
	public Document checkAccountBalance(String appId, String amount, String acno) throws Exception {
		String logGroup = "\ncheckHxBalance_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appId + "\t" + acno.substring(0, 4) + "******"
				+ acno.substring(acno.length() - 4, acno.length()) + "\t" + amount);
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appId);
		paraMap.put("AMOUNT", amount);
		paraMap.put("ACNO", acno);
		Document xml = createRequestXML(logGroup,CHAECK_TRANSCODE, paraMap);
		
		String xmlData = xml.asXML();
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"校检账户余额响应");
	}
	
	public static void main(String[] args) {
		try {
			
			String str ="8970800100102645054	刘姣姣";
			
			String[] strs = str.split(",");
			
			HxAccountBanlance banlance = new HxAccountBanlance();
			
			String appId = "PC";
			String amount = "0.0";

			StringBuffer sBuffer = new StringBuffer();
			
			for (int i = 0; i < strs.length; i++) {
				String string = strs[i];
				String acName = string.split("\t")[1];
				String acNO = string.split("\t")[0];
				
				System.out.println(acName + "\t" + acNO);
				
				Document document = banlance.requestAccountBalance(appId, acName, acNO);
				
				Map<String, Object> map = HxBaseData.xmlToMap(document);
				sBuffer.append("账户名称：" + map.get("ACNAME") + "\t账户号：" + map.get("ACNO") + "\t冻结金额：" + map.get("FROZBL") + "\t账户余额:" + map.get("ACCTBAL") + "\t可用余额：" + map.get("AVAILABLEBAL") + "\n");
			}
			
			System.out.println(sBuffer);
			
			
			// 账户余额校验
//			Document document = banlance.checkAccountBalance(appId, amount, acNO);
//			System.out.println("==============================================");
//			System.out.println(formatXml(document.asXML()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 6.9	账户余额查询(OGW00049)
	 * 
	 * <Document>
			<header>
				<encryptData/>
				<serverFlow>OGW0120170706100000000027357051</serverFlow>
				<channelCode>GHB</channelCode>
				<status>0</status>
				<serverTime>094128</serverTime>
				<errorCode>0</errorCode>
				<errorMsg>success</errorMsg>
				<channelFlow>P2P202201707060499E936FFM56N</channelFlow>
				<serverDate>20170706</serverDate>
				<transCode>OGW00049</transCode>
			</header>
			<body>
				<TRANSCODE>OGW00049</TRANSCODE>
				<BANKID>GHB</BANKID>
				<XMLPARA>
					<ACNAME>李白</ACNAME>
					<ACNO>8970660100000024369</ACNO>
					<FROZBL>1007</FROZBL>
					<ACCTBAL>20000</ACCTBAL>
					<EXT_FILED1>1</EXT_FILED1>
					<AVAILABLEBAL>18993</AVAILABLEBAL>
				</XMLPARA>
				<MERCHANTID>LQE</MERCHANTID>
			</body>
		</Document>
	 */
	public Document requestAccountBalance(String appId, String acname, String acno) throws Exception {
		// 生成日志头
		String logGroup = "\nhxBalance_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求参数:"  + "\t" + appId + "\t" + acno.substring(0, 4) + "******"
				+ acno.substring(acno.length() - 4, acno.length()));
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("APPID", appId);
		paraMap.put("ACNAME", acname);
		paraMap.put("ACNO", acno);
		paraMap.put("BUSTYPE", "");
		
		Document xml = createRequestXML(logGroup,QUERY_TRANSCODE, paraMap);
		// 转生String类型
		String xmlData = xml.asXML();
	
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		String returnMsg = SendMessage.sendMessage(content);
		
		return decryptResponseXML(returnMsg,"查询账户余额响应");
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
			throw new ResponseInfoException(errorMsg.getText(),1004);
		}
		return decryptRetrunMessage(contentXml);
	}
}
