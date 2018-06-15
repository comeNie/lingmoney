package com.mrbt.lingmoney.bank.repayment;

import java.util.HashMap;
import java.util.Map;

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
 * @date 2017年6月7日 下午1:40:51
 * @version 1.0
 * @description 6.34自动单笔还款（可选）(OGW00072)
 **/
@Component
public class HxAutoSingleRepayment extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxAutoSingleRepayment.class);
	private static final String TRANSCODE = "OGW00072";

	/**
	 * 请求 自动单笔还款
	 * 
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            还款账号
	 * @param feeAmt
	 *            手续费（选填）扣借款人的平台手续费
	 * @param amount
	 *            还款金额 保留两位小数 0.00
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public Map<String, Document> requestAutoSingleRepayment(String appId, String loanNo, String bwacName,
			String bwacNo, String feeAmt, String amount, String remark, String logGroup) {
		Document xml = createAutoSingleRepaymentReqXml(appId, loanNo, bwacName, bwacNo, feeAmt, amount, remark,
				logGroup);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的 自动单笔还款 请求报文：\n{}", logGroup, content);
		
		// 返回解密后银行响应报文
		String responseMsg = SendMessage.sendMessage(content);
		
		Map<String, Document> resultMap = new HashMap<String, Document>();

		if (!StringUtils.isEmpty(responseMsg)) {
			logger.info("{}自动单笔还款 请求成功。返回信息。\n{}", logGroup, responseMsg);
			// 有响应 返回响应信息
			resultMap.put("success", analysisAsyncMsg(responseMsg, logGroup));
		} else {
			logger.info("{}自动单笔还款 请求失败。返回信息为空。", logGroup);
			// 无响应 返回请求信息
			resultMap.put("fail", xml);
		}

		return resultMap;
	}

	/**
	 * 创建 自动单笔还款请求 XML
	 * 
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            还款账号
	 * @param feeAmt
	 *            手续费（选填）扣借款人的平台手续费
	 * @param amount
	 *            还款金额 保留两位小数 0.00
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	private Document createAutoSingleRepaymentReqXml(String appId, String loanNo, String bwacName, String bwacNo,
			String feeAmt, String amount, String remark, String logGroup) {
		Document document = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = document.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("APPID").setText(appId);
		docu.addElement("LOANNO").setText(loanNo);
		docu.addElement("BWACNAME").setText(bwacName);
		docu.addElement("BWACNO").setText(bwacNo);
		docu.addElement("AMOUNT").setText(amount);
		if (!StringUtils.isEmpty(feeAmt)) {
			docu.addElement("FEEAMT").setText(feeAmt.toString());
		} else {
			docu.addElement("FEEAMT").setText("");
		}

		if (!StringUtils.isEmpty(remark)) {
			docu.addElement("REMARK").setText(remark);
		} else {
			docu.addElement("REMARK").setText("");
		}
		
		System.out.println(logGroup + "生成自动单笔还款请求xml明文" + formatXml(document2.asXML()));
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return document;
	}
}
