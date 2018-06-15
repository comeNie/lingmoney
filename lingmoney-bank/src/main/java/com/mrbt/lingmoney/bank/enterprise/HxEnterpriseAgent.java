package com.mrbt.lingmoney.bank.enterprise;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * 企业经办人
 * 
 * @author syb
 * @date 2017年9月28日 下午2:53:40
 * @version 1.0
 **/
@Component
public class HxEnterpriseAgent extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxEnterpriseAgent.class);

	private static final String TRANSCODE_PC = "OGW00254";
	private static final String TRANSCODE_MOBILE = "OGW00258";
	private static final String TRANSCODE_QUERY_RESULT = "OGW00255";
	private static final String TRANSCODE_RESPONSE = "OGW00252";

	/**
	 * 企业经办人信息修改/经办人变更(OGW00254) （跳转我行页面处理） 1）由 P2P 商户发起。
	 * 2）该接口提供给企业客户经办人进行手机号修改，或者是变更经办人。 3）经办人变更手机号，如经办人原手机号无法收到短信，则需验证法人手 机号信息。
	 * 4）经办人变更需验证法人信息。 5）若法人开户手机无法接收到短信验证码，则只能通过到我行柜面修改法 人手机号后才可进行变更经办人操作。
	 * 
	 * @author syb
	 * @date 2017年9月28日 下午3:02:35
	 * @version 1.0
	 * @param clientType
	 *            客户端类型 0 pc 1app
	 * @param appId
	 * @param acNo
	 *            银行存管账户
	 * @param returnUrl
	 *            返回链接
	 * @param logGroup
	 *            日志头
	 * @return
	 *
	 */
	public Map<String, String> requestUpdateEnterpriseAgentInfo(int clientType, String appId, String acNo,
			String returnUrl, String logGroup) {
		String transCode = "";

		if (clientType == 0) {
			transCode = TRANSCODE_PC;
		} else {
			transCode = TRANSCODE_MOBILE;
		}

		Map<String, String> contentMap = new HashMap<String, String>();

		logger.info("{}请求企业开户参数：acNo:{}, returnUrl:{}", logGroup, acNo, returnUrl);

		// 生成数据xml文件
		Document xml = createHxEnterpriseOpenAccountXml(clientType, appId, transCode, acNo, returnUrl, logGroup);

		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);

		logger.info("{}生成私钥的签名:{}", logGroup, privateResult);

		// 组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;

		logger.info("{}生成的请求报文\n{}", logGroup, content);

		// 拆分报文
		Map<String, String> resMap = splitContent(content, "解析异步通知报文");
		// 解析请求数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		logger.info("{}请求报文验签结果：{}", logGroup, signCheck);

		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);
		contentMap.put("requestData", content);
		contentMap.put("transCode", transCode);

		return contentMap;
	}

	private Document createHxEnterpriseOpenAccountXml(int clientType, String appId, String transCode, String acNo,
			String returnUrl, String logGroup) {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		docu.addElement("APPID").setText(appId);
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("TTRANS").setText("42");// 42：经办人信息/经办人变更
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("RETURNURL").setText(returnUrl);

		logger.info("{}生成xml明文{}{}", logGroup, document.asXML(), docu.asXML());

		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info("{}生成xml密文\n{}", logGroup, document.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	/**
	 * 生应答报文返回
	 * 
	 * @param asyncMsg
	 * @param loggroup
	 * @return
	 */
	public String generatinAsynsReply(Map<String, Object> asyncMsg, String loggroup, boolean t) {

		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		try {

			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText(TRANSCODE_RESPONSE);
			header.addElement("channelFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("serverFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());

			Element body = root.addElement("body");
			body.addElement("MERCHANTID").setText(MERCHANTID);
			body.addElement("TRANSCODE").setText(TRANSCODE_RESPONSE);
			body.addElement("BANKID").setText(BANKID);

			Document doc = DocumentHelper.createDocument();

			Element ele = doc.addElement("XMLPARA");

			if (t) {
				ele.addElement("RETURNCODE").setText("000000");
				ele.addElement("RETURNMSG").setText("交易成功");
			} else {
				ele.addElement("RETURNCODE").setText("000001");
				ele.addElement("RETURNMSG").setText("交易失败");
			}

			ele.addElement("REQSEQNO").setText(asyncMsg.get("channelFlow").toString());

			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(doc.asXML());

			body.addElement("XMLPARA").setText(encryptXmlPara);

			// 生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());

			// 组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");
			logger.info(loggroup + "生成的应答返回报文:\n" + content);

			return content;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 企业经办人信息修改/经办人变更结果查询 (OGW00255) 1）由 P2P 商户发起。
	 * 2）在经办人信息修改/经办人变更请求后，可通过该接口查询修改/变更结 果。 3）该接口控制单个交易流水号每 每 5 5 分钟只能查询一次。
	 * 
	 * @param olderQseqNo
	 *            原账户开立交易报文头的渠道流水号
	 * @param appId
	 *            应用标识
	 * @return
	 */
	public Map<String, Object> queryEnterpriseOpenAccount(String oldReqSeqNo, String logGroup) {
		String content = createQueryEnterpriseOpenAccountXml(oldReqSeqNo, logGroup);
		// 访问银行接口
		String resXml = SendMessage.sendMessage(content);
		// String resXml = SendMessage.sendBankMessageTest(content, url);
		logger.info("{}查询的返回报文:\n{}", logGroup, resXml);
		// 解析报文
		Document document = analysisAsyncMsg(resXml, logGroup);

		logger.info("{}解析后返回报文：\n{}", logGroup, document.asXML());

		return xmlToMap(document);
	}

	/**
	 * 查询查询开户接口报文
	 * 
	 * @author syb
	 * @date 2017年9月28日 下午2:42:29
	 * @version 1.0
	 * @param olderQseqNo
	 *            原请求开户流水号
	 * @param logGroup
	 *            日志头
	 * @return
	 *
	 */
	private String createQueryEnterpriseOpenAccountXml(String olderQseqNo, String logGroup) {
		Document document = getDocHeardElementQuery(logGroup, TRANSCODE_QUERY_RESULT, olderQseqNo);

		Element body = document.getRootElement().addElement("body");

		body.addElement("TRANSCODE").setText(TRANSCODE_QUERY_RESULT);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("OLDREQSEQNO").setText(olderQseqNo);
		// docu.addElement("TRANTYPE").setText(""); 本字段暂不使用

		logger.info("{}生成xml明文{}", logGroup, document2.asXML());
		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info("{}生成xml密文：{}", logGroup, encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML().replaceAll("\n", ""));

		logger.info("{}生成私钥的签名{}", logGroup, privateResult);
		// 组成报文
		String content = "001X11          00000256" + privateResult
				+ document.asXML().replaceAll("\n", "").replaceAll("\r", "");
		logger.info("{}生成的请求报文\n{}", logGroup, content);

		return content;
	}

}
