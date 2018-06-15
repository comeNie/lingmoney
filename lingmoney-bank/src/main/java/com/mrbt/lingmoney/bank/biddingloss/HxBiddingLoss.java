package com.mrbt.lingmoney.bank.biddingloss;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * @author syb
 * @date 2017年6月5日 下午3:34:47
 * @version 1.0
 * @description 流标(OGW00063)
 **/
@Component
public class HxBiddingLoss extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxBiddingLoss.class);
	private static final String TRANSCODE = "OGW00063";

	/**
	 * 流标请求
	 * 
	 * @param clientType
	 *            客户端类型
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号（原投标借款编号）
	 * @param cancelReason
	 *            流标原因
	 * @return
	 */
	public Document requestBiddingLoss(String appId, String loanNo, String cancelReason, String logGroup) {

		Document xml = createBiddingLossXml(logGroup, appId, loanNo, cancelReason, TRANSCODE);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info(logGroup + "生成的流标请求报文\n" + content);
		
		String responseMsg = SendMessage.sendMessage(content);
		
		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "流标请求成功，返回信息：\n" + responseMsg);
			
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "流标请求失败，返回信息为空。");
			
			return null;
		}
	}

	/**
	 * 创建请求报文
	 * 
	 * @param logGroup
	 * @param appId
	 * @param loanNo
	 * @param cancelReason
	 * @param transCode
	 * @return
	 */
	private Document createBiddingLossXml(String logGroup, String appId, String loanNo,
			String cancelReason, String transCode) {
		Document doc = getDocHeardElement(logGroup, transCode);
		
		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlpara = body.addElement("XMLPARA");
		
		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("APPID").setText(appId);
		xmlparaElem.addElement("LOANNO").setText(loanNo);
		if (!StringUtils.isEmpty(cancelReason)) {
			xmlparaElem.addElement("CANCELREASON").setText(cancelReason);
		} else {
			xmlparaElem.addElement("CANCELREASON").setText("");
		}

		logger.info(logGroup + " 生成xmlpara明文：\n" + xmlparaElem.asXML());

		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(xmlparaElem.asXML());
			xmlpara.setText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}
}
