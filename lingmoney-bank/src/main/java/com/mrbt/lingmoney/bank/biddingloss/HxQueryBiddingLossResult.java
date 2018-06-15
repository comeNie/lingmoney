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
 * @date 2017年6月6日 上午9:21:28
 * @version 1.0
 * @description 流标结果查询 (OGW00064)
 **/
@Component
public class HxQueryBiddingLossResult extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxQueryBiddingLossResult.class);
	private static final String TRANSCODE = "OGW00064";

	/**
	 * 请求 查询流标结果
	 * 
	 * @param clientType
	 *            客户端类型
	 * @param appId
	 *            应用标识
	 * @param oldReqSeqNo
	 *            原流标流水号
	 * @param oldTTJNL
	 *            原投标流水号（选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public Document requestQueryBiddingLossResult(String appId, String oldReqSeqNo, String oldTTJNL, String logGroup) {
		Document xml = createQueryBiddingLossResultReqXml(logGroup, appId, oldReqSeqNo, oldTTJNL);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info(logGroup + "生成的 查询流标结果 请求报文：\n" + content);
		
		String responseMsg = SendMessage.sendMessage(content);
		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "查询流标结果成功。返回信息。\n" + responseMsg);
			
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "查询流标结果失败。返回信息为空。");
			
			return null;
		}
	}

	/**
	 * 创建 流标结果查询 请求报文XML
	 * 
	 * @param logGroup
	 *            日志头
	 * @param transCode
	 *            交易码
	 * @param appId
	 *            应用标识
	 * @param oldReqSeqNo
	 *            原流标交易流水号
	 * @param oldTTJNL
	 *            原投标流水号
	 * @return
	 */
	private Document createQueryBiddingLossResultReqXml(String logGroup, String appId, String oldReqSeqNo,
			String oldTTJNL) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);
		Element body = doc.getRootElement().addElement("body");
		// 交易码
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");
		
		// 加密XMLPARA
		Document paraDoc = DocumentHelper.createDocument();
		Element paraElem = paraDoc.addElement("Document");
		paraElem.addElement("MERCHANTID").setText(MERCHANTID);
		paraElem.addElement("APPID").setText(appId);
		paraElem.addElement("OLDREQSEQNO").setText(oldReqSeqNo);
		if(!StringUtils.isEmpty(oldTTJNL)) {
			paraElem.addElement("OLDTTJNL").setText(oldTTJNL);
		} else {
			paraElem.addElement("OLDTTJNL").setText("");
		}
		
		logger.info(logGroup + "创建流标结果查询报文XMLPARA,加密前：\n" + paraElem.asXML());
		
		try {
			String encryptXmlpara = DES3EncryptAndDecrypt.DES3EncryptMode(paraElem.asXML());
			xmlPara.setText(encryptXmlpara);
		} catch (Exception e) {
			logger.error(logGroup + "加密流标结果查询报文XMLPARA失败，系统错误。\n" + e.getMessage());
			
			e.printStackTrace();
		}
		
		return doc;
	}
}
