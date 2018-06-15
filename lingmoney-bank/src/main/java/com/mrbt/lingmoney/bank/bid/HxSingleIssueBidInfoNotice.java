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
 * 6.11 单笔发标信息通知(OGW00051)
 * 由第三方公司发起。发标的融资标的信息，可通过单笔通讯或者文件同步方式。这涉及到投标、流标等控制。具体方式根据协商确定。（首次投产时，
 * 建议第三方公司提供一个全量文件给银行一次性导入，以后就单笔通知）。如没收到银行的处理结果，可对同一标的多次发送，但发请求时，报文头的流水号需每次保证唯一性
 * ，当报文头返回错误码是“EAS020420026”时则代表原标的已成功，无需再重复发送。 此接口支持新标的通知和债权转让标的通知。
 * 债权转让申请(OGW00063)我行返回成功后，第三方公司审核允许转让后需调用此接口进行转让标的发标申请。
 * 
 * @author lihq
 * @date 2017年6月6日 上午11:40:33
 * @description
 * @version 1.0
 * @since 2017-04-12
 */
@Component
public class HxSingleIssueBidInfoNotice extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxSingleIssueBidInfoNotice.class);

	// 打印日志头
	static String logHeard = "\nHxSingleIssueBidInfoNotice_";

	private static final String TRANSCODE = "OGW00051";

	public static void main(String[] args) {

		try {
			HxSingleIssueBidInfoNotice hxCashWithdraw = new HxSingleIssueBidInfoNotice();
			hxCashWithdraw.requestHxSingleIssueBidInfoNotice("A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
					"A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
					"A");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String requestHxSingleIssueBidInfoNotice(String appId, String loanNo, String investId,
			String investObjName, String investObjInfo, String minInvestAmt, String maxInvestAmt, String investObjAmt, String investBeginDate, String investEndDate,
			String repayDate, String yearRate, String investRange, String rateStype, String repayStype, String investObjState, String bwTotalNum, String remark1, String zrFlag,
			String refLoanNo, String oldReqseq, String bwAcname, String bwIdtype, String bwIdno, String bwAcno, String bwAcbankid, String bwAcbankname, String bwAmt, String mortgageId,
			String mortgageInfo, String checkDate, String remark2, String logGroup) throws Exception {
		String transCode = TRANSCODE;

		// 生成数据xml文件
		Document xml = createHxSingleIssueBidInfoNotice(transCode, appId, loanNo, investId,
				investObjName, investObjInfo, minInvestAmt, maxInvestAmt, investObjAmt, investBeginDate, investEndDate,
				repayDate, yearRate, investRange, rateStype, repayStype, investObjState, bwTotalNum, remark1, zrFlag,
				refLoanNo, oldReqseq, bwAcname, bwIdtype, bwIdno, bwAcno, bwAcbankid, bwAcbankname, bwAmt, mortgageId,
				mortgageInfo, checkDate, remark2, logGroup);

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

	// 生成xml
	private static Document createHxSingleIssueBidInfoNotice(String transCode, String appId, String loanNo, String investId,
			String investObjName, String investObjInfo, String minInvestAmt, String maxInvestAmt, String investObjAmt, String investBeginDate, String investEndDate,
			String repayDate, String yearRate, String investRange, String rateStype, String repayStype, String investObjState, String bwTotalNum, String remark1, String zrFlag,
			String refLoanNo, String oldReqseq, String bwAcname, String bwIdtype, String bwIdno, String bwAcno, String bwAcbankid, String bwAcbankname, String bwAmt, String mortgageId,
			String mortgageInfo, String checkDate, String remark2, String logGroup) throws Exception {
		Document document = getDocHeardElement(logGroup, transCode);

		Element body = document.getRootElement().addElement("body");

		// 交易码
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");

		// 加密xmlpara
		Document xmlparaDoc = DocumentHelper.createDocument();
		Element xmlparaElem = xmlparaDoc.addElement("Document");

		xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
		xmlparaElem.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		xmlparaElem.addElement("APPID").setText(appId == null ? "PC" : appId);
		xmlparaElem.addElement("LOANNO").setText(loanNo);
		xmlparaElem.addElement("INVESTID").setText(loanNo);
		xmlparaElem.addElement("INVESTOBJNAME").setText(investObjName);
		xmlparaElem.addElement("INVESTOBJINFO").setText(investObjInfo == null ? "" : investObjInfo);
		xmlparaElem.addElement("MININVESTAMT").setText(minInvestAmt == null ? "" : minInvestAmt);
		xmlparaElem.addElement("MAXINVESTAMT").setText(maxInvestAmt == null ? "" : maxInvestAmt);
		xmlparaElem.addElement("INVESTOBJAMT").setText(investObjAmt);
		xmlparaElem.addElement("INVESTBEGINDATE").setText(investBeginDate);
		xmlparaElem.addElement("INVESTENDDATE").setText(investEndDate);
		xmlparaElem.addElement("REPAYDATE").setText(repayDate == null ? "" : repayDate);
		xmlparaElem.addElement("YEARRATE").setText(yearRate);
		xmlparaElem.addElement("INVESTRANGE").setText(investRange);
		xmlparaElem.addElement("RATESTYPE").setText(rateStype == null ? "" : rateStype);
		xmlparaElem.addElement("REPAYSTYPE").setText(repayStype == null ? "" : repayStype);
		xmlparaElem.addElement("INVESTOBJSTATE").setText(investObjState);
		xmlparaElem.addElement("BWTOTALNUM").setText(bwTotalNum);
		xmlparaElem.addElement("REMARK").setText(remark1 == null ? "" : remark1);
		xmlparaElem.addElement("ZRFLAG").setText(zrFlag == null ? "" : zrFlag);
		xmlparaElem.addElement("REFLOANNO").setText((zrFlag != null && "1".equals(zrFlag)) ? refLoanNo : "");
		xmlparaElem.addElement("OLDREQSEQ").setText((zrFlag != null && "1".equals(zrFlag)) ? oldReqseq : "");
		xmlparaElem.addElement("EXT_FILED1").setText("");

		// 借款人列表<BWLIST>(目前只支持一个)
		Element bwlistElem = xmlparaElem.addElement("BWLIST");
		bwlistElem.addElement("BWACNAME").setText(bwAcname);
		bwlistElem.addElement("BWIDTYPE").setText(bwIdtype == null ? "" : bwIdtype);
		bwlistElem.addElement("BWIDNO").setText(bwIdno == null ? "" : bwIdno);
		bwlistElem.addElement("BWACNO").setText(bwAcno);
		bwlistElem.addElement("BWACBANKID").setText(bwAcbankid == null ? "" : bwAcbankid);
		bwlistElem.addElement("BWACBANKNAME").setText(bwAcbankname == null ? "" : bwAcbankname);
		bwlistElem.addElement("BWAMT").setText(bwAmt);
		bwlistElem.addElement("MORTGAGEID").setText(mortgageId == null ? "" : mortgageId);
		bwlistElem.addElement("MORTGAGEINFO").setText(mortgageInfo == null ? "" : mortgageInfo);
		bwlistElem.addElement("CHECKDATE").setText(checkDate == null ? "" : checkDate);
		bwlistElem.addElement("REMARK").setText(remark2 == null ? "" : remark2);
		bwlistElem.addElement("EXT_FILED2").setText("");
		bwlistElem.addElement("EXT_FILED3").setText("");

		logger.info(logGroup + " 生成xmlpara明文：\n" + xmlparaElem.asXML());

		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(xmlparaElem.asXML());
			logger.info(logGroup + " 生成xmlpara密文：\n" + encryptXmlPara);
			// 加密数据放入XMLPARA元素中
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public Document handleHxSingleIssueBidInfoNoticeAsyncMsg(String message) throws Exception {
		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";

		// 解析异步报文
		Document document = analysisAsyncMsg(message, logGroup);
		if (StringUtils.isEmpty(document)) {
			return null;
		}
		return document;
	}
	public Map<String, Object> singleIssueBidInfoNotice(String appId, String loanNo, String investId,
			String investObjName, String investObjInfo, String minInvestAmt, String maxInvestAmt, String investObjAmt, String investBeginDate, String investEndDate,
			String repayDate, String yearRate, String investRange, String rateStype, String repayStype, String investObjState, String bwTotalNum, String remark1, String zrFlag,
			String refLoanNo, String oldReqseq, String bwAcname, String bwIdtype, String bwIdno, String bwAcno, String bwAcbankid, String bwAcbankname, String bwAmt, String mortgageId,
			String mortgageInfo, String checkDate, String remark2, String logGroup, String url) {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			String content = requestHxSingleIssueBidInfoNotice(appId, loanNo, investId,
					investObjName, investObjInfo, minInvestAmt, maxInvestAmt, investObjAmt, investBeginDate, investEndDate,
					repayDate, yearRate, investRange, rateStype, repayStype, investObjState, bwTotalNum, remark1, zrFlag,
					refLoanNo, oldReqseq, bwAcname, bwIdtype, bwIdno, bwAcno, bwAcbankid, bwAcbankname, bwAmt, mortgageId,
					mortgageInfo, checkDate, remark2, logGroup);

			Map<String, String> cf = splitContent(content, "拆分模拟单笔发标信息通知报文");

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
	 * 模拟银行返回单笔发标信息通知报文
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
