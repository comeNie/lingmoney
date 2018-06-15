package com.mrbt.lingmoney.bank.bid;

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
 * @date 2017年8月14日 上午9:19:04
 * @version 1.0
 * @description 单笔撤标结果查询 1）由 P2P 商户发起。 2）交易提交我行 2 分钟后，可通过该接口查询银行处理结果。 3）同一流水 5
 *              分钟内不可重复发起查询。
 **/
@Component
public class HxSingleBiddingCancelResult extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxSingleBiddingCancelResult.class);
	private static final String TRANSCODE = "OGW00212";

	public Document requestBiddingCancelResult(String appId, String oldReqSeqNo, String logGroup) {

		Document xml = createBiddingLossXml(logGroup, appId, oldReqSeqNo);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;

		logger.info(logGroup + "生成的查询撤标结果请求报文\n" + content);

		String responseMsg = SendMessage.sendMessage(content);

		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "查询撤标结果请求成功，返回信息：\n" + responseMsg);

			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "查询撤标结果请求失败，返回信息为空。");

			return null;
		}
	}

	private Document createBiddingLossXml(String logGroup, String appId, String oldReqSeqNo) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);

		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");

		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("APPID").setText(appId);
		xmlparaElem.addElement("OLDREQSEQNO").setText(oldReqSeqNo);

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
