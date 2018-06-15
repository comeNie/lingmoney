package com.mrbt.lingmoney.bank.withdraw;

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
 * 6.8 单笔提现结果查询(OGW00048) 由第三方发起， 交易提交我行5分钟后，可通过该接口查询银行处理结果。
 * 
 * @author lihq
 * @date 2017年6月6日 上午9:06:36
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Component
public class HxCashWithdrawResult extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxCashWithdrawResult.class);

	// 打印日志头
	static String logHeard = "\nHxCashWithdrawResult_";

	private static final String TRANSCODE = "OGW00048";

	public static void main(String[] args) {

		// 发起请求
		int clientType = 1;
		String appId = "PC";// PC,APP,WX
		String transDt = "20170605";
		String oldReqseqno = "DFJGOIDUFOSIOUTWO49023840458";
		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";
		try {
			HxCashWithdrawResult hx = new HxCashWithdrawResult();
			hx.requestCashWithdrawResult(appId, transDt, oldReqseqno, logGroup);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 单笔提现结果查询
	 * 
	 * @param clientType
	 * @param appId
	 * @param transDt
	 * @param oldReqseqno
	 * @return 请求报文
	 * @throws Exception
	 */
	public String requestCashWithdrawResult(String appId, String transDt, String oldReqseqno, String logGroup)
			throws Exception {
		String transCode = TRANSCODE;

		logger.info(logGroup + "请求参数:" + "\t" + appId + "\t" + transDt + "\t" + oldReqseqno);

		// 生成数据xml文件
		Document xml = createCashWithdrawResultXml(appId, transDt, transCode, oldReqseqno, logGroup);

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
	 * @param transDt
	 * @param transCode
	 * @param oldReqseqno
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	private static Document createCashWithdrawResultXml(String appId, String transDt, String transCode,
			String oldReqseqno, String logGroup) throws Exception {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");

		// 交易码
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("MERCHANTID", MERCHANTID);
		paraMap.put("APPID", appId == null ? "PC" : appId);
		paraMap.put("TRANSDT", transDt);
		paraMap.put("OLDREQSEQNO", oldReqseqno);

		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(paraMap, logGroup);

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
	public Document handleCashWithdrawResultAsyncMsg(String message, String logGroup) throws Exception {

		// 解析异步报文
		Document document = analysisAsyncMsg(message, logGroup);
		if (StringUtils.isEmpty(document)) {
			return null;
		}
		return document;
	}

	/**
	 * 查询提现结果
	 * 
	 * @param appId
	 *            应用标识
	 * @param transDt
	 *            交易日期
	 * @param oldReqseqno
	 *            原提现交易报文头的渠道流水号
	 * @param logGroup
	 *            日志头
	 * @param url
	 *            回调url
	 * @return
	 */
	public Map<String, Object> queryCashWithdrawResult(String appId, String transDt, String oldReqseqno,
			String logGroup, String url) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			String content = requestCashWithdrawResult(appId, transDt, oldReqseqno, logGroup);

			Map<String, String> cf = splitContent(content, "拆分模拟查询提现结果报文");

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
	 * 模拟查询提现结果报文
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
			body.addElement("TRANSCODE").setText("AAA");
			body.addElement("BANKID").setText(BANKID);

			// 生成要加密的xml，XMLPARA区域
			Document document2 = DocumentHelper.createDocument();
			Element docu = document2.addElement("Document");
			docu.addElement("OLDREQSEQNO").setText("AAA");// 原交易流水号
			docu.addElement("RESJNLNO").setText("AAA");// 银行交易流水号，成功才返回
			docu.addElement("TRANSDT").setText(hbdu.getNowDate());// 交易日期
			docu.addElement("TRANSTM").setText(hbdu.getNowTime());// 交易时间
			/**
			 * R 页面处理中（客户仍停留在页面操作，25分钟商户后仍收到是此状态可置为失败） N
			 * 未知（已提交后台，商户需再次发查询接口。但2小时后状态仍未变的则可置为异常，无需再发起查询，
			 * 后续在对账文件中确认交易状态或线下人工处理） P 预授权成功（非实时到账，2小时内到账） D
			 * 后台支付系统处理中（商户需再次发查询接口。但1小时后状态仍未变的则可置为异常，无需再发起查询，
			 * 后续在对账文件中确认交易状态或线下人工处理） S 成功 F 失败
			 * 
			 */
			docu.addElement("RETURN_STATUS").setText("S");
			// 未知（已提交后台，需再次发查询接口。）
			docu.addElement("ERRORMSG").setText("AAAAAAAAAA");// 失败原因，失败才返回
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
