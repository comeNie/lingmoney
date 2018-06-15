package com.mrbt.lingmoney.bank.enterprise;

import java.util.HashMap;
import java.util.Map;

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
 * 企业绑定卡
 *
 * @author syb
 * @date 2017年9月28日 下午3:21:27
 * @version 1.0
 **/
@Component
public class HxEnterpriseBindCard extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxEnterpriseBindCard.class);
	
	private static final String TRANSCODE_QUERY = "OGW00256";
	private static final String TRANSCODE_UNBIND_PC = "OGW00262";
	private static final String TRANSCODE_UNBIND_MOBILE = "OGW00263";
	
	public static void main(String[] args) {
		Document xml = new HxEnterpriseBindCard().queryEnterpriseBindCardInfo("6236882280001926539", "1", "2", "000000000");
		System.out.println(xmlToMap(xml));
	}

    /**
     * 5.44 绑定卡信息查询 (OGW00256) 接口说明
     * 1）由 P2P 商户发起。
     * 2）根据存管账户查询客户在我行绑卡信息。
     * 3）可查询个人绑定卡信息和企业绑定卡信息。
     * 4）该接口控制单个交易流水号每 5 分钟只能查询一次。
     * 
     * @author syb
     * @date 2017年9月28日 下午3:29:42
     * @version 1.0
     * @param acNo 银行存管账户
     * @param currentPage 当前页码
     * @param pageNumber 每页条数
     * @param logGroup
     * @return
     *
     */
	public Document queryEnterpriseBindCardInfo(String acNo, String currentPage, String pageNumber,
			String logGroup) {
		String content = createQueryEnterpriseBindCardInfoXml(acNo, currentPage, pageNumber, logGroup);
		// 访问银行接口
		String resXml = SendMessage.sendMessage(content);
		// String resXml = SendMessage.sendBankMessageTest(content, url);
		logger.info("{}查询的返回报文:\n{}", logGroup, resXml);
		// 解析报文
		Document document = analysisAsyncMsg(resXml, logGroup);

		logger.info("{}解析后返回报文：\n{}", logGroup, document.asXML());

		return document;
	}

	private String createQueryEnterpriseBindCardInfoXml(String acNo, String currentPage, String pageNumber, String logGroup) {
		Document document = getDocHeardElementQuery(logGroup, TRANSCODE_QUERY, null);

		Element body = document.getRootElement().addElement("body");

		body.addElement("TRANSCODE").setText(TRANSCODE_QUERY);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("CURRENTPAGE").setText(currentPage);
		docu.addElement("PAGENUMBER").setText(pageNumber);
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
	
	/**
	 * 企业绑定卡解绑(OGW00262) （跳转我行页面处理） 1）由 P2P 商户发起。
	 * 2）企业客户可在平台通过跳转到我行的形式进行解绑绑定卡的操作。 3）解绑前必须保证企业存管账户资产为 0。
	 * 
	 * @author syb
	 * @date 2017年9月28日 下午3:34:29
	 * @version 1.0
	 * @param clientType
	 * @param appId
	 * @param acNo
	 * @param returnUrl
	 * @param logGroup
	 * @return
	 *
	 */
	public Map<String, String> requestEnterpriseUnbindCard(int clientType, String appId, String acNo,
			String returnUrl, String logGroup) {
		String transCode = "";

		if (clientType == 0) {
			transCode = TRANSCODE_UNBIND_PC;
		} else {
			transCode = TRANSCODE_UNBIND_MOBILE;
		}

		Map<String, String> contentMap = new HashMap<String, String>();

		logger.info("{}请求企业开户参数：acNo:{}, returnUrl:{}", logGroup, acNo, returnUrl);

		// 生成数据xml文件
		Document xml = createHxEnterpriseUnbindCardXml(clientType, appId, transCode, acNo, returnUrl, logGroup);

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

	private Document createHxEnterpriseUnbindCardXml(int clientType, String appId, String transCode, String acNo,
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
		docu.addElement("TTRANS").setText("43");// 43：解绑
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("RETURNURL").setText(returnUrl);

		logger.info("{}生成xml明文{}{}", logGroup, document.asXML(), docu.asXML());

		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info("{}生成xml密文后{}", logGroup, document.asXML());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

}
