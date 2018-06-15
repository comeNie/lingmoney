package com.mrbt.lingmoney.bank.biddingloss;

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
 * @date 2017年6月5日 下午3:36:35
 * @version 1.0
 * @description 银行主动流标（必须）(OGW0015T)
 **/
@Component
public class HxInitiativeBiddingLoss extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxInitiativeBiddingLoss.class);
	private static final String TRANSCODE = "OGW0015T";

	/**
	 * 银行主动流标响应
	 * 
	 * @param transCode
	 *            交易码
	 */
	public String responseInitiativeBiddingLoss(String channelFlow, String loanNo, String errorCode, String errorMsg,
			String status, String logGroup) {
		Document xml = createInitiativeBiddingLossResXml(logGroup, channelFlow, loanNo, errorCode, errorMsg, status);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		
		String privateResult = MyRSA.getDataSignature(xmlData);
		
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info(logGroup + "生成的银行主动流标响应报文\n" + content);
		// 发送响应报文
		return content;
	}

	/**
	 * 创建银行主动流标响应报文
	 * 
	 * @param logGroup
	 *            日志头
	 * @param transCode
	 *            交易码
	 * @param channelFlow
	 *            渠道流水
	 * @param loanNo
	 *            借款编号
	 * @param errorCode
	 *            错误码
	 * @param errorMsg
	 *            错误信息
	 * @param status
	 *            业务状态
	 * @return
	 */
	private Document createInitiativeBiddingLossResXml(String logGroup, String channelFlow, String loanNo,
			String errorCode, String errorMsg, String status) {
		Document document = getResponseDocHeardElement(TRANSCODE, channelFlow, status, errorCode, errorMsg, logGroup);
		Element body = document.getRootElement().addElement("body");
		
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
		docu.addElement("LOANNO").setText(loanNo);

		logger.info(logGroup + "生成银行主动流标响应XMLPARA明文 \n" + docu.asXML());
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			logger.error(logGroup + "\t 生成银行主动流标响应xml密文失败，系统错误。 \n" + e.getMessage());
			
			e.printStackTrace();
		}
		logger.info(logGroup + " 创建银行主动流标响应XML成功。\n" + document.asXML());
		
		return document;
	}

}
