package com.mrbt.lingmoney.bank.recall;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;

/**
 * @author syb
 * @date 2017年6月5日 上午9:25:01
 * @version 1.0
 * @description 银行主动单笔撤标(OGW0014T) 标的放款前，银行发送
 **/
@Component
public class HxInitiativeSingleRecall extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxInitiativeSingleRecall.class);
	private static final String TRANSCODE = "OGW0014T";

	/**
	 * 银行单笔撤标响应
	 * 
	 * @param channelFlow
	 *            渠道流水
	 * @param oldReqseqNo
	 *            原投标流水号
	 * @param errorCode
	 *            错误码
	 * @param errorMsg
	 *            错误信息
	 * @param status
	 *            状态
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public String responseInitiativeSingleRecall(String channelFlow, String oldReqseqNo, String errorCode,
			String errorMsg, String status, String logGroup) {
		Document xml = createInitiativeSingleRecallResXml(logGroup, channelFlow, oldReqseqNo, errorCode, errorMsg,
				status);
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的银行单笔撤标响应报文\n {}", logGroup, content);
		
		// 发送响应报文
		return content;
	}

	/**
	 * 创建银行单笔撤标响应XML
	 * 
	 * @param logGroup
	 *            日志头
	 * @param transCode
	 *            交易码
	 * @param channelFlow
	 *            渠道流水号
	 * @param oldReqseqNo
	 *            原投标流水号
	 * @param errorCode
	 *            错误码
	 * @param errorMsg
	 *            错误信息
	 * @param status
	 *            业务状态
	 * @return
	 */
	private Document createInitiativeSingleRecallResXml(String logGroup, String channelFlow, String oldReqseqNo,
			String errorCode, String errorMsg, String status) {
		Document document = getResponseDocHeardElement(TRANSCODE, channelFlow, status, errorCode, errorMsg, logGroup);
		Element body = document.getRootElement().addElement("body");

		// 交易码
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		if ("0".equals(errorCode)) {
			docu.addElement("RETURNCODE").setText("000000");
			docu.addElement("RETURNMSG").setText("交易成功");
		} else {
			docu.addElement("RETURNCODE").setText(errorCode);
			docu.addElement("RETURNMSG").setText(errorMsg);
		}
		docu.addElement("OLDREQSEQNO").setText(oldReqseqNo);

		logger.info("{}生成银行单笔撤标响应XMLPARA明文 \n{}", logGroup, docu.asXML());
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			logger.error("{} 生成银行单笔撤标响应xml密文失败，系统错误。 \n{}", logGroup, e.toString());
			
			e.printStackTrace();
		}
		
		return document;
	}
}
