package com.mrbt.lingmoney.bank.deal;

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
 * @date 2017年8月14日 下午2:03:37
 * @version 1.0
 * @description 交易结果查询1） 由 P2P 商户发起。 2） 该接口可用于单笔撤标、公司垫付还款、单笔奖励或分红、自动投标
 *              和自动还款交易后状态查询。 3） 发起单笔撤标、公司垫付还款、单笔奖励或分红、自动投标和自动还 款交易 3
 *              分钟后，可通过本接口查询交易的处理结果。 4） 单个流水号查询时间需间隔 5 分钟。
 **/
@Component
public class HxQueryTradingResult extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxQueryTradingResult.class);
	private static final String TRANSCODE = "OGW00247";

	
	public static void main(String[] args) {
		Document xml = new HxQueryTradingResult().requestQueryTradingResult("P2P202201803270732FTLPNE4JAK", "", "0000000000000");
		
		System.out.println(xmlToMap(xml));
	}
	/**
	 * 
	 * @description 交易结果查询 请求
	 * @author syb
	 * @date 2017年8月14日 下午2:06:47
	 * @version 1.0
	 * @param oldReqSeqNo
	 *            原交易的请求报文头的渠道流 水号
	 * @param tranType
	 *            交易类型：目前为空
	 * @param logGroup
	 *            日志头
	 * @return
	 *
	 */
	
	public Document requestQueryTradingResult(String oldReqSeqNo, String tranType, String logGroup) {

		Document xml = createBiddingLossXml(logGroup, oldReqSeqNo, tranType);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;

		logger.info(logGroup + "生成的交易结果查询请求报文\n" + content);

		String responseMsg = SendMessage.sendMessage(content);

		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "交易结果查询请求成功，返回信息：\n" + responseMsg);
			return analysisAsyncMsg(responseMsg, logGroup);

		} else {
			logger.info(logGroup + "交易结果查询请求失败，返回信息为空。");
			return null;
		}
	}

	private Document createBiddingLossXml(String logGroup, String oldReqSeqNo, String tranType) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);

		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");

		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("OLDREQSEQNO").setText(oldReqSeqNo);
		if (StringUtils.isNotBlank(tranType)) {
			xmlparaElem.addElement("TRANTYPE").setText(tranType);
		} else {
			xmlparaElem.addElement("TRANTYPE").setText("");
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
