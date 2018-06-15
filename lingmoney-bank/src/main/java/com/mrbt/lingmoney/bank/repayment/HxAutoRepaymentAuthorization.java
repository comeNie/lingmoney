package com.mrbt.lingmoney.bank.repayment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;

/**
 * @author syb
 * @date 2017年6月6日 下午4:28:43
 * @version 1.0
 * @description 自动还款授权
 **/
@Component
public class HxAutoRepaymentAuthorization extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxAutoRepaymentAuthorization.class);

	private static final String TRANSCODE_PC = "OGW00069";

	private static final String TRANSCODE_MOBILE = "OGW00175";

	/**
	 * 请求 自动还款授权
	 * 
	 * @param clientType
	 *            商户类型
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            还款账号
	 * @param remark
	 *            备注（选填）
	 * @param returnUrl
	 *            返回商户URL（选填）
	 * @return
	 */
	public Map<String, String> requestAutoRepaymentAuthorization(int clientType, String appId, String loanNo,
			String bwacName, String bwacNo, String remark, String returnUrl, String logGroup) {
		String transCode = TRANSCODE_PC;
		if (clientType == 1) {
			transCode = TRANSCODE_MOBILE;
		}
		
		Document xml = createAutoRepaymentAuthorizationReqXml(logGroup, transCode, appId, loanNo, bwacName, bwacNo,
				remark, returnUrl);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		
		logger.info("{}生成自动还款授权请求报文\n{}", logGroup, content);

		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("requestData", content);
		contentMap.put("transCode", transCode);
		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);

		return contentMap;
	}

	/**
	 * 创建 自动还款授权请求XML
	 * 
	 * @param transCode
	 *            交易码
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            还款账号
	 * @param remark
	 *            备注
	 * @param returnUrl
	 *            返回商户url
	 * @return
	 */
	private Document createAutoRepaymentAuthorizationReqXml(String logGroup, String transCode, String appId,
			String loanNo, String bwacName, String bwacNo, String remark, String returnUrl) {
		Document document = getDocHeardElement(logGroup, transCode);
		
		Element body = document.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("APPID").setText(appId);
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("TTRANS").setText("10");
		docu.addElement("LOANNO").setText(loanNo);
		docu.addElement("BWACNAME").setText(bwacName);
		docu.addElement("BWACNO").setText(bwacNo);
		if (!StringUtils.isEmpty(remark)) {
			docu.addElement("REMARK").setText(remark);
		} else {
			docu.addElement("REMARK").setText("");
		}

		if (!StringUtils.isEmpty(returnUrl)) {
			docu.addElement("RETURNURL").setText(returnUrl + loanNo);
		} else {
			docu.addElement("RETURNURL").setText("");
		}

		System.out.println(logGroup + "生成自动还款授权请求xml明文" + docu.asXML());
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			logger.error("{}加密数据失败。\n {}", logGroup, e.toString());
			
			e.printStackTrace();
		}
		
		return document;
	}

	/**
	 * 自动还款授权响应
	 * 
	 * @param resMap
	 * @return
	 */
	public String responseAutoRepaymentAuthorization(Map<String, Object> resMap, String logGroup) {
		try {
			Document doc = getResponseDocHeardElement(resMap.get("TRANSCODE").toString(), resMap.get("channelFlow")
					.toString(), "0", "0", "交易成功", logGroup);
			
			Element body = doc.getRootElement().addElement("body");
			
			// 数据加密
			Document doc1 = DocumentHelper.createDocument();
			Element docu = doc1.addElement("Document");
			docu.addElement("RETURNCODE").setText("000000");
			docu.addElement("RETURNMSG").setText("交易成功");
			docu.addElement("REQSEQNO").setText(resMap.get("OLDREQSEQNO").toString());
			docu.addElement("RESJNLNO").setText(resMap.get("RESJNLNO").toString());

			System.out.println(logGroup + "加密前xmlpara: \n" + docu.asXML());
			
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			body.addElement("XMLPARA").setText(encryptXmlPara);

			String privateResult = MyRSA.getDataSignature(doc.asXML());
			String content = CONTENT_HEARED_NOASYN + privateResult + doc.asXML();
			
			logger.info("{}生成自动还款授权应答返回报文成功:\n{}", logGroup, content);

			return content;
		} catch (Exception e) {
			logger.error("{}生成 自动还款授权响应报文失败，系统错误。 \n{}", logGroup, e.toString());

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
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170801067KCQCZ45RRZ2");
		ele1.addElement("RESJNLNO").addText("P2P20220170801067KCQCZ45RRZ2");
		
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
