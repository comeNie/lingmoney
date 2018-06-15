package com.mrbt.lingmoney.bank.bid;

import java.util.Date;
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
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * 6.20 单笔撤标(OGW00060) 标的放款前，由第三方公司发起。撤标金额须与投标金额一致，不可部分撤标。
 * 
 * @author lihq
 * @date 2017年6月6日 下午4:48:57
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Component
public class HxSingleCancelBidding extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxSingleCancelBidding.class);

	// 打印日志头
	static String logHeard = "\nHxSingleCancelBidding_";

	private static final String TRANSCODE = "OGW00060";

	public static void main(String[] args) {

		// 发起请求
		String appId = "APP";
		String oldReqseqno = "DFJGOIDUFOSIOUTWO49023840458";
		String loanNo = "KSDFHGIS238947HJ";
		String acNo = "6228481090891240517";
		String acName = "张三";
		String cancelReason = "不和法规";
		String logGroup = "";
		try {
			HxSingleCancelBidding hx = new HxSingleCancelBidding();
			hx.requestSingleCancelBidding(appId, loanNo, oldReqseqno, acNo, acName, cancelReason, logGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 单笔撤标
	 * 
	 * @param clientType
	 * @param appId
	 * @param oldReqseqno
	 * @return
	 * @throws Exception
	 */
	public String requestSingleCancelBidding(String appId, String loanNo, String oldReqseqno, String acNo,
			String acName, String cancelReason, String logGroup) throws Exception {
		String transCode = TRANSCODE;

		logger.info(logGroup + "请求参数:" + appId + "\t" + oldReqseqno);

		// 生成数据xml文件
		Document xml = createSingleCancelBiddingXml(appId, transCode, loanNo, oldReqseqno, acNo, acName, cancelReason,
				logGroup);

		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		return content;
	}

	/**
	 * 生成xml
	 * 
	 * @param appId
	 * @param transCode
	 * @param oldReqseqno
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	private static Document createSingleCancelBiddingXml(String appId, String transCode, String loanNo,
			String oldReqseqno, String acNo, String acName, String cancelReason, String logGroup) throws Exception {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");

		// 交易码
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("MERCHANTID", MERCHANTID);
		paraMap.put("APPID", appId == null ? "PC" : appId);
		paraMap.put("LOANNO", loanNo);
		paraMap.put("OLDREQSEQNO", oldReqseqno);
		paraMap.put("ACNO", acNo);
		paraMap.put("ACNAME", acName);
		paraMap.put("CANCELREASON", cancelReason);

		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(paraMap, logGroup);
		// 解密
		System.out.println(DES3EncryptAndDecrypt.DES3DecryptMode(encryptXmlPara));
		// 添加加密数据到XMLPARA中
		xmlPara.setText(encryptXmlPara);
		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		return document;
	}

	/**
	 * 处理异步返回结果
	 * 
	 * @param message
	 *            银行发送的应答报文
	 * @return 报文的body部分
	 * @throws Exception
	 */
	public Document handleSingleCancelBiddingAsyncMsg(String message) throws Exception {
		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";

		// 解析异步报文
		Document document = analysisAsyncMsg(message, logGroup);
		if (StringUtils.isEmpty(document)) {
			return null;
		}
		return document;
	}

	/**
	 * 单笔撤标
	 * 
	 * @param appId
	 *            应用标识
	 * @param loanNo
	 *            借款编号
	 * @param oldReqseqno
	 *            撤标流水号
	 * @param acNo
	 *            银行账号
	 * @param acName
	 *            账户姓名
	 * @param cancelReason
	 *            撤标原因
	 * @param logGroup
	 * @return
	 */
	public Map<String, Object> singleCancelBidding(String appId, String loanNo, String oldReqseqno, String acNo,
			String acName, String cancelReason, String logGroup, String url) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			String content = requestSingleCancelBidding(appId, loanNo, oldReqseqno, acNo, acName, cancelReason,
					logGroup);

			Map<String, String> cf = splitContent(content, "拆分模拟单笔撤标报文");

			Map<String, Object> toMap = xmlToMap(DocumentHelper.parseText(cf.get("contentXml")));

			// 访问银行接口
			String resXml = SendMessage.sendBankMessage(content, url);

			// String resXml = sendBankMessageTest(toMap);

			// 解析报文
			Document document = analysisAsyncMsg(resXml, logGroup);
			return xmlToMap(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resMap;
	}

	/**
	 * 模拟银行返回单笔撤标报文
	 * 
	 * @param toMap
	 * @param toMap
	 * @param content
	 * @param url
	 * @return
	 */
	private String sendBankMessageTest(Map<String, Object> toMap) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		try {

			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText("AAA");
			header.addElement("channelFlow").setText("AAA");
			header.addElement("serverFlow").setText("???");
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());

			Element body = root.addElement("body");
			body.addElement("MERCHANTID").setText(MERCHANTID);
			body.addElement("TRANSCODE").setText("OGW00060");
			body.addElement("BANKID").setText(BANKID);

			// 生成要加密的xml，XMLPARA区域
			Document document2 = DocumentHelper.createDocument();
			Element docu = document2.addElement("Document");
			docu.addElement("RESJNLNO").setText("AAA");// 银行交易流水号，成功才返回
			docu.addElement("TRANSDT").setText(hbdu.getNowDate());// 交易日期
			docu.addElement("TRANSTM").setText(hbdu.getNowTime());// 交易时间
			docu.addElement("EXT_FILED1").setText("1");//
			docu.addElement("EXT_FILED2").setText("2");//
			docu.addElement("EXT_FILED3").setText("3");//

			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

			body.addElement("XMLPARA").setText(encryptXmlPara);

			// 生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());

			// 组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");

			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
