package com.mrbt.lingmoney.bank.eaccount;

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
 * @date 2017年8月14日 上午11:43:54
 * @version 1.0
 * @description 实名认证查询、可通过该接口查询客户是否完成了实名认证及账户的开户状态及联 网核查状态
 **/
@Component
public class HxRealNameAuthentication extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxRealNameAuthentication.class);
	private static final String TRANSCODE = "OGW00223";
	
	public static void main(String[] args) {
		Document xml = new HxRealNameAuthentication().requestQueryRealNameAuthentication("6236882202000000694", "000000000000000");
		
		System.out.println(HxBaseData.xmlToMap(xml));
	}
	

	/**
	 * 
	 * @description 实名认证查询请求
	 * @author syb
	 * @date 2017年8月14日 下午2:00:45
	 * @version 1.0
	 * @param custId
	 * @param logGroup
	 * @return STATUSID 账户状态 C(20) 否 0：未激活 1：正常 2：自助冻结 3：销户 4: 开户失败 5：强制冻结 6：预销户
	 *         7：只收不付（即联网核查无结 果状态） NETCHECKRESULT 联网核查结果 C(1) 否 0：无结果 1：通过 2：不通过
	 *         3:无需联网核查 CERTIFICATIONRE SULT 实名认证结果 C(1) 否 0：未实名认证 1：已实名认证
	 *         2：实名认证审批中 3：无需实名认证 4：实名认证等待
	 *
	 */
	public Document requestQueryRealNameAuthentication(String custId, String logGroup) {

		Document xml = createBiddingLossXml(logGroup, custId);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;

		logger.info(logGroup + "生成的实名认证查询结果请求报文\n" + content);

		String responseMsg = SendMessage.sendMessage(content);

		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info(logGroup + "查询实名认证查询请求成功，返回信息：\n" + responseMsg);

			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "查询实名认证查询请求失败，返回信息为空。");

			return null;
		}
	}

	private Document createBiddingLossXml(String logGroup, String custId) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);

		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlpara = body.addElement("XMLPARA");

		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");
		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("CUSTID").setText(custId);

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
