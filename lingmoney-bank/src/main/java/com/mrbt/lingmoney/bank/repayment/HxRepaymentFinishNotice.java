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
 * @date 2017年8月14日 下午4:01:33
 * @version 1.0
 * @description 标的结清通知 1） 由 P2P 商户发起。 2） 借款人还清借款后，商户必须同步发起该接口通知银行标的已结清。 3）
 *              该接口涉及后续借款人借款额度限制，若标的结清后不传送该通知接 口，则对借款人在续贷上会存影响。 4）
 *              标的结清后将不再受理还款交易。5） 平台需将存量标的批量传送标的结清通知给到我行，并发数需控制在 10 笔/秒
 **/
@Component
public class HxRepaymentFinishNotice extends HxBaseData{
	private static final Logger logger = LogManager.getLogger(HxRepaymentFinishNotice.class);
	private static final String TRANSCODE = "OGW00250";
	
	/**
	 * 
	 * @description 还款完成通知 请求
	 * @author syb
	 * @date 2017年8月14日 下午4:10:55
	 * @version 1.0
	 * @param loanNo
	 *            借款编号
	 * @param logGroup
	 *            日志头
	 * @return
	 *
	 */
	public Document requestRepaymentFinish(String loanNo, String logGroup) {

		Document xml = createRepaymentFinishXml(logGroup, loanNo);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;

		logger.info(logGroup + "生成的标的结清通知请求报文\n" + content);

		String responseMsg = SendMessage.sendMessage(content);

		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "标的结清通知请求成功，返回信息：\n" + responseMsg);

			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "标的结清通知请求失败，返回信息为空。");

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
	private Document createRepaymentFinishXml(String logGroup, String loanNo) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);

		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");

		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("LOANNO").setText(loanNo);

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
