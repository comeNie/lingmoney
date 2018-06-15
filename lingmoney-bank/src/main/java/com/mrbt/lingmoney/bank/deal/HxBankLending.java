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
 * @date 2017年6月6日 上午11:17:11
 * @version 1.0
 * @description 放款
 **/
@Component
public class HxBankLending extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxBankLending.class);
	private static final String TRANSCODE = "OGW00065";

	/**
	 * 请求银行放款
	 * 
	 * @param clientType
	 *            客户端类型 0 PC 1MOBILE
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            借款人姓名
	 * @param bwacNo
	 *            借款人账号
	 * @param acmngAmt
	 *            账户管理费（选填）
	 * @param guarantAmt
	 *            风险保证金（选填）
	 * @param remark
	 *            备注 （选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public Document requestHxBankLending(String appId, String loanNo, String bwacName, String bwacNo, String acmngAmt,
			String guarantAmt, String remark, String logGroup) {
		Document xml = createHxBankLendingRequestXml(logGroup, appId, loanNo, bwacName, bwacNo, acmngAmt, guarantAmt,
				remark);
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的放款请求报文。\n{}", logGroup, content);
		
		String responseMsg = SendMessage.sendMessage(content);
		if (!StringUtils.isEmpty(responseMsg)) {
			logger.info("{}放款请求成功，返回信息：\n{}", logGroup, responseMsg);
			
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info("{}放款请求失败，返回数据为空。", logGroup);
			
			return null;
		}
	}

	/**
	 * 创建银行放款请求 XML
	 * 
	 * @param logGroup
	 * @param transCode
	 * @param appId
	 * @param loanNo
	 * @param bwacName
	 * @param bwacNo
	 * @param acmngAmt
	 * @param guarantAmt
	 * @param remark
	 * @return
	 */
	private Document createHxBankLendingRequestXml(String logGroup, String appId, String loanNo, String bwacName,
			String bwacNo, String acmngAmt, String guarantAmt, String remark) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");
		
		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		xmlparaElem.addElement("APPID").setText(appId);
		xmlparaElem.addElement("LOANNO").setText(loanNo);
		xmlparaElem.addElement("BWACNAME").setText(bwacName);
		xmlparaElem.addElement("BWACNO").setText(bwacNo);
		if (!StringUtils.isEmpty(acmngAmt)) {
			xmlparaElem.addElement("ACMNGAMT").setText(acmngAmt.toString());
		} else {
			xmlparaElem.addElement("ACMNGAMT").setText("");
		}
		
		if (!StringUtils.isEmpty(guarantAmt)) {
			xmlparaElem.addElement("GUARANTAMT").setText(guarantAmt.toString());
		} else {
			xmlparaElem.addElement("GUARANTAMT").setText("");
		}
		
		if (!StringUtils.isEmpty(remark)) {
			xmlparaElem.addElement("REMARK").setText(remark);
		} else {
			xmlparaElem.addElement("REMARK").setText("");
		}

		System.out.println(logGroup + "生成xmlpara明文：\n" + xmlparaElem.asXML());

		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(xmlparaElem.asXML());
			// 加密数据放入XMLPARA元素中
			xmlpara.setText(encryptXmlPara);
		} catch (Exception e) {
			logger.info("{}加密银行放款请求XMLPARA失败，系统错误。\n{}", logGroup, e.toString());

			e.printStackTrace();
		}

		return doc;
	}
}
