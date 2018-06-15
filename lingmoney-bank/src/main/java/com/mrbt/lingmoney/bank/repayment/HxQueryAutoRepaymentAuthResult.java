package com.mrbt.lingmoney.bank.repayment;

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
 * @date 2017年6月7日 上午8:47:22
 * @version 1.0
 * @description 自动还款授权结果查询(OGW00070)
 **/
@Component
public class HxQueryAutoRepaymentAuthResult extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxQueryAutoRepaymentAuthResult.class);
	private static final String TRANSCODE = "OGW00070";

	/**
	 * 请求查询自动还款授权结果
	 * 
	 * @param clientType
	 *            客户端类型
	 * @param appId
	 *            应用标识 （不送则默认PC）
	 * @param oldReqSeqNo
	 *            原 自动还款授权 交易流水号
	 * @return
	 */
	public Document requestQueryAutoRepaymentAuthResult(String appId, String oldReqSeqNo, String logGroup) {
		Document xml = createQueryAutoRepaymentAuthResultReqXml(logGroup, appId, oldReqSeqNo);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的 查询自动还款授权结果 请求报文：\n{}", logGroup, content);
		
		String responseMsg = SendMessage.sendMessage(content);
		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info("{}查询自动还款授权结果成功。返回信息。\n{}", logGroup, responseMsg);
		
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info("{}查询自动还款授权结果失败。返回信息为空。", logGroup);
			
			return null;
		}
	}

	/**
	 * 创建 自动还款授权结果查询XML
	 * 
	 * @param appId
	 * @param oldReqSeqNo
	 * @return
	 */
	private Document createQueryAutoRepaymentAuthResultReqXml(String logGroup, String appId, String oldReqSeqNo) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");
		
		// 加密XMLPARA
		Document paraDoc = DocumentHelper.createDocument();
		Element paraElem = paraDoc.addElement("Document");
		paraElem.addElement("MERCHANTID").setText(MERCHANTID);
		paraElem.addElement("APPID").setText(appId);
		paraElem.addElement("OLDREQSEQNO").setText(oldReqSeqNo);
		
		System.out.println(logGroup + "创建自动还款授权结果查询报文XMLPARA,加密前：\n" + formatXml(paraElem.asXML()));
		
		try {
			String encryptXmlpara = DES3EncryptAndDecrypt.DES3EncryptMode(paraElem.asXML());
			xmlPara.setText(encryptXmlpara);
		} catch (Exception e) {
			logger.error("{}加密自动还款授权结果查询报文XMLPARA失败，系统错误。\n{}", logGroup, e.getMessage());
			
			e.printStackTrace();
		}
		
		return doc;
	}
}
