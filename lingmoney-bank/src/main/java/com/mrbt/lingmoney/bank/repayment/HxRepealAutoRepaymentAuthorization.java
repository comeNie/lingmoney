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
 * @date 2017年6月7日 上午10:30:53
 * @version 1.0
 * @description 自动还款授权撤销
 **/
@Component
public class HxRepealAutoRepaymentAuthorization extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxRepealAutoRepaymentAuthorization.class);
	private static final String TRANSCODE = "OGW00071";

	/**
	 * 请求 撤销自动还款授权
	 * 
	 * @param appId
	 *            应用标识
	 * @param otpSeqNo
	 *            动态密码唯一标识
	 * @param otpNo
	 *            动态密码
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            还款账号
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public static void main(String[] args) {
		Document xml = new HxRepealAutoRepaymentAuthorization().requestRepealAutoRepaymentAuthorization("PC", "a8526ebc3dcd4fce8d2504f7d498c217", "111111", "41001603012064", "北京睿博众盈科技发展有限公司", "8971660100000010853", "阿斯顿发顺丰的", "000000000");
		System.out.println(xmlToMap(xml));
	}
	
	public Document requestRepealAutoRepaymentAuthorization(String appId, String otpSeqNo, String otpNo, String loanNo,
			String bwacName, String bwacNo, String remark, String logGroup) {
		Document xml = createRepealAutoRepaymentAuthReqXml(appId, otpSeqNo, otpNo, loanNo, bwacName, bwacNo, remark,
				logGroup);

		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info("{}生成的 撤销自动还款授权 请求报文：\n{}", logGroup, content);
		
		// 返回解密后银行响应报文
		String responseMsg = SendMessage.sendMessage(content);
		if (StringUtils.isNotBlank(responseMsg)) {
			logger.info("{}撤销自动还款授权请求。返回信息：\n{}", logGroup, responseMsg);
			
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info("{}撤销自动还款授权请求返，回信息为空。", logGroup);
			
			return null;
		}
	}

	/**
	 * 创建 撤销自动还款授权请求 XML
	 * 
	 * @param appId
	 *            应用标识
	 * @param otpSeqNo
	 *            动态密码唯一标识
	 * @param otpNo
	 *            动态密码
	 * @param loanNo
	 *            借款编号
	 * @param bwacName
	 *            还款账号户名
	 * @param bwacNo
	 *            换狂账号
	 * @param remark
	 *            备注（选填）
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	private Document createRepealAutoRepaymentAuthReqXml(String appId, String otpSeqNo, String otpNo, String loanNo,
			String bwacName, String bwacNo, String remark, String logGroup) {
		Document doc = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = doc.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");
		
		// 加密XMLPARA
		Document paraDoc = DocumentHelper.createDocument();
		Element paraElem = paraDoc.addElement("Document");
		paraElem.addElement("MERCHANTID").setText(MERCHANTID);
		paraElem.addElement("APPID").setText(appId);
		paraElem.addElement("OTPSEQNO").setText(otpSeqNo);
		paraElem.addElement("OTPNO").setText(otpNo);
		paraElem.addElement("LOANNO").setText(loanNo);
		paraElem.addElement("BWACNAME").setText(bwacName);
		paraElem.addElement("BWACNO").setText(bwacNo);
		if (StringUtils.isNotBlank(remark)) {
			paraElem.addElement("REMARK").setText(remark);
		} else {
			paraElem.addElement("REMARK").setText("");
		}
		
		System.out.println(logGroup + "创建撤销自动还款授权请求报文XMLPARA,加密前：\n" + formatXml(paraElem.asXML()));
		
		try {
			String encryptXmlpara = DES3EncryptAndDecrypt.DES3EncryptMode(paraElem.asXML());
			xmlPara.setText(encryptXmlpara);
		} catch (Exception e) {
			logger.error("{}加密撤销自动还款授权请求报文XMLPARA失败，系统错误。\n{}", logGroup, e.toString());

			e.printStackTrace();
		}
		
		return doc;
	}
}
