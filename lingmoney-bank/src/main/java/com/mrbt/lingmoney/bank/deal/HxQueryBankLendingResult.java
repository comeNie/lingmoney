package com.mrbt.lingmoney.bank.deal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * @author syb
 * @date 2017年6月6日 下午1:50:49
 * @version 1.0
 * @description 6.28放款结果查询 (OGW00066)
 **/
@Component
public class HxQueryBankLendingResult extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxQueryBankLendingResult.class);
	private static final String TRANSCODE = "OGW00066";

	/**
	 * 请求查询放款结果
	 * 
	 * @param clientType
	 *            客户端类型 0PC 1MOBILE
	 * @param appId
	 *            应用标识 PC APP WX
	 * @param oldReqseqNo
	 *            原放款交易流水
	 * @param loanNo
	 *            借款编号
	 * @param oldTTJNL
	 *            原投标流水号 （选填）
	 * @param logGroup
	 *            日志头
	 * @return 解密后响应报文
	 */
	public Document requestQueryBankLenddingResult(String appId, String oldReqseqNo, String loanNo, String oldTTJNL,
			String logGroup) {
		Document xml = createQueryBankLendingResultReqXml(logGroup, appId, oldReqseqNo, loanNo, oldTTJNL);
		
		String xmlData = xml.asXML().replaceAll("\n", "");

		String privateResult = MyRSA.getDataSignature(xmlData);
		
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的查询放款结果请求报文。\n{}", logGroup, content);
		
		String responseMsg = SendMessage.sendMessage(content);
		if (!StringUtils.isEmpty(responseMsg)) {
			logger.info("{}查询放款结果请求成功，返回信息：\n{}", logGroup, responseMsg);
		
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info("{}查询放款结果请求失败，返回数据为空。", logGroup);
			
			return null;
		}
	}

	/**
	 * 创建 查询放款结果 请求XML
	 * 
	 * @param logGroup
	 * @param transCode
	 * @param appId
	 * @param oldReqseqNo
	 * @param loanNo
	 * @param oldTTJNL
	 * @return
	 */
	private Document createQueryBankLendingResultReqXml(String logGroup, String appId, String oldReqseqNo,
			String loanNo, String oldTTJNL) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");
		
		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("APPID").setText(appId);
		xmlparaElem.addElement("OLDREQSEQNO").setText(oldReqseqNo);
		xmlparaElem.addElement("LOANNO").setText(loanNo);
		if (!StringUtils.isEmpty(oldTTJNL)) {
			xmlparaElem.addElement("OLDTTJNL").setText(oldTTJNL);
		} else {
			xmlparaElem.addElement("OLDTTJNL").setText("");
		}
		
		System.out.println(logGroup + "生成查询银行放款结果请求xmlpara明文：\n" + xmlparaElem.asXML());

		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(xmlparaElem.asXML());
			xmlpara.setText(encryptXmlPara);
		} catch (Exception e) {
			logger.info("{}加密查询银行放款结果请求XMLPARA失败，系统错误。\n", logGroup, e.toString());

			e.printStackTrace();
		}

		return doc;
	}
}
