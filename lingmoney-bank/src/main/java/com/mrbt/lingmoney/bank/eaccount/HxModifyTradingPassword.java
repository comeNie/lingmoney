package com.mrbt.lingmoney.bank.eaccount;

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

/**
 * 5.54 个人客户进行重置交易密码操作
 * 
 * 由第三方公司发起，跳转到银行官网完成进行该操作。客户在页面流程操作共不可超过20分钟，否则请求超时。
 * 
 * @author suyibo
 * @date 创建时间：2018年4月19日
 */
@Component
public class HxModifyTradingPassword extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxModifyTradingPassword.class);

	// 打印日志头
	static String logHeard = "\nHxModifyTradingPassword_";

	private static final String TRANSCODE_PC = "OGW00266";

	private static final String TRANSCODE_MOBILE = "OGW00269";

	public static void main(String[] args) {

		// 发起请求
		int clientType = 0;
		String appId = "PC";
		String acNo = "6228481090891240517";
		String uId = "ABCD765D1C52A04ABE7EFBDAEA722E87";
		String returnUrl = "http://www.baidu.com/";

		try {
			HxModifyTradingPassword hx = new HxModifyTradingPassword();
			hx.requestHxModifyTradingPassword(clientType, appId, acNo, returnUrl, uId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, String> requestHxModifyTradingPassword(Integer clientType, String appId, String acNo,
			String returnUrl,
			String uid) {
		String transCode = "";

		if (clientType == 0) {
			transCode = TRANSCODE_PC;
		} else {
			transCode = TRANSCODE_MOBILE;
		}

		Map<String, String> contentMap = new HashMap<String, String>();

		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";

		// 生成数据xml文件
		Document xml = createHxModifyTradingPasswordXml(clientType, appId, transCode, acNo, returnUrl, logGroup, uid);

		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);

		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		contentMap.put("requestData", content);

		if (clientType == 0) {
			contentMap.put("transCode", TRANSCODE_PC);
		} else {
			contentMap.put("transCode", TRANSCODE_MOBILE);
		}

		// 拆分报文
		Map<String, String> resMap = splitContent(contentMap.get("requestData"), "解析异步通知报文");
		// 解析请求数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		logger.info(logGroup + "请求报文验签：\n" + signCheck);

		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);

		return contentMap;
	}

	private static Document createHxModifyTradingPasswordXml(int clientType, String appId, String transCode,
			String acNo, String returnUrl, String logGroup, String uid) {
		Document document = getDocHeardElement(logGroup, transCode);

		queryTextFromXml(document.asXML(), "channelFlow");

		Element body = document.getRootElement().addElement("body");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("APPID").setText(appId);
		docu.addElement("TTRANS").setText("46");
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("ACNO").setText(acNo);
//		docu.addElement("RETURNURL").setText(returnUrl + uid);

		logger.info(logGroup + "生成xml明文" + formatXml(document.asXML()) + "\n" + formatXml(docu.asXML()));

		try {
			// 加密
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			// 添加加密数据到XMLPARA中
			xmlPara.setText(encryptXmlPara);
			logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

}
