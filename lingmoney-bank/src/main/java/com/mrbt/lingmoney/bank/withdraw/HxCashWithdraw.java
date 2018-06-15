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

/**
 * 6.7 单笔提现(OGW00047/OGW00093) （跳转我行页面处理）
 * 
 * @author lihq
 * @date 2017年6月2日 下午3:01:15
 * @description 由第三方公司发起，跳转到银行官网完成进行该操作。交易提交我行5分钟后，可通过该接口查询银行处理结果。
 *              客户在页面流程操作共不可超过20分钟，否则请求超时。
 * 
 * @version 1.0
 * @since 2017-04-12
 */
@Component
public class HxCashWithdraw extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxCashWithdraw.class);

	// 打印日志头
	static String logHeard = "\nHxCashWithdraw_";

	private static final String TRANSCODE_PC = "OGW00047";

	private static final String TRANSCODE_MOBILE = "OGW00093";

	public static void main(String[] args) {

		// 发起请求
		int clientType = 1;
		String appId = "PC";// PC,APP,WX
		String acNo = "6228481090891240517";// 姓名
		String acName = "张三";
		String amount = "100.00";
		String remark = "rgsdfasd";
		String uId = "ABCD765D1C52A04ABE7EFBDAEA722E87";
		String cashWithdrawReturnUrl = "http://www.baidu.com/";
		// 生成日志头
		String logGroup = logHeard + System.currentTimeMillis() + "_";
		try {
			HxCashWithdraw hx = new HxCashWithdraw();
			hx.requestCashWithdraw(clientType, appId, acNo, acName, amount, remark, uId, cashWithdrawReturnUrl,
					logGroup);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 用户发起单笔提现请求
	 * 
	 * @param clientType
	 *            客户端类型 0,PC 1,APP
	 * @param appId
	 *            应用标识
	 * @param acNo
	 *            银行账号
	 * @param acName
	 *            账号户名
	 * @param amount
	 *            交易金额
	 * @param remark
	 *            备注
	 * @param uId
	 *            用户ID
	 * @param cashWithdrawReturnUrl
	 *            返回商户URL
	 * @return
	 */
	public Map<String, String> requestCashWithdraw(int clientType, String appId, String acNo, String acName,
			String amount, String remark, String uId, String cashWithdrawReturnUrl, String logGroup) throws Exception {
		String transCode = "";

		if (clientType == 0) {
			transCode = TRANSCODE_PC;
		} else if (clientType == 1) {
			transCode = TRANSCODE_MOBILE;
		}

		Map<String, String> contentMap = new HashMap<String, String>();

		logger.info(logGroup + "请求参数:" + clientType + "\t" + appId + "\t" + acNo.substring(0, 6) + "******"
				+ acNo.substring(acNo.length() - 4) + "\t" + acName + "\t" + amount + "\t" + remark + "\t" + uId + "\t"
				+ cashWithdrawReturnUrl);

		// 生成数据xml文件
		Document xml = createCashWithdrawXml(appId, acNo, acName, amount, remark, uId, cashWithdrawReturnUrl, transCode,
				logGroup);

		// 转生String类型
		String xmlData = xml.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		// 获取流水号
		String channelFlow = queryTextFromXml(xmlData, "channelFlow");
		contentMap.put("channelFlow", channelFlow);
		contentMap.put("requestData", content);
		contentMap.put("transCode", transCode);

		// 拆分报文
		Map<String, String> resMap = splitContent(contentMap.get("requestData"), "解析异步通知报文");
		// 解析请求数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		logger.info(logGroup + "请求报文验签：\n" + signCheck);

		return contentMap;
	}

	/**
	 * 生成xml
	 * 
	 * @param clientType
	 * @param appId
	 * @param acNo
	 * @param acName
	 * @param amount
	 * @param remark
	 * @param uId
	 * @param cashWithdrawReturnUrl
	 * @param transCode
	 * @param logGroup
	 * @return
	 */
	private static Document createCashWithdrawXml(String appId, String acNo, String acName, String amount,
			String remark, String uId, String cashWithdrawReturnUrl, String transCode, String logGroup)
			throws Exception {
		Document document = getDocHeardElement(logGroup, transCode);
		Element body = document.getRootElement().addElement("body");
		String channelFlow = queryTextFromXml(document, "channelFlow");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");

		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
		docu.addElement("APPID").setText(appId == null ? "PC" : appId);
		docu.addElement("TTRANS").setText("8");
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("ACNAME").setText(acName);
		docu.addElement("AMOUNT").setText(amount);
		docu.addElement("REMARK").setText(remark);
		docu.addElement("RETURNURL").setText(cashWithdrawReturnUrl + channelFlow + "/" + uId);

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

	/**
	 * 处理异步返回结果
	 * 
	 * @param message
	 *            银行发送的应答报文
	 * @return 报文的body部分
	 * @throws Exception
	 */
	public Document handleCashWithdrawAsyncMsg(String message, String logGroup) throws Exception {

		// 解析异步报文
		Document document = analysisAsyncMsg(message, logGroup);
		if (StringUtils.isEmpty(document)) {
			return null;
		}
		return document;
	}

	/**
	 * 创建接收单笔取现异步通知后的回复报文
	 * 
	 * @param clientType
	 * @param channelFlow
	 * @param status
	 * @param errorCode
	 * @param errorMsg
	 * @param returnCode
	 * @param returnMsg
	 * @param oldReqseqno
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	public String createCashWithdrawAsyncMsg(String transCode, String channelFlow, String status, String errorCode,
			String errorMsg, String retrunCode, String returnMsg, String olderQseqNo, String logGroup)
			throws Exception {

		// 创建header
		Document document = getResponseDocHeardElement(transCode, channelFlow, status, errorCode, errorMsg, logGroup);
		// body
		Element body = document.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("RETURNCODE", retrunCode);
		paraMap.put("RETURNMSG", returnMsg);
		paraMap.put("OLDREQSEQNO", olderQseqNo);

		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(paraMap, logGroup);

		// 添加加密数据到XMLPARA中
		xmlPara.setText(encryptXmlPara);
		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");

		// 转生String类型
		String xmlData = document.asXML().replaceAll("\n", "");
		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");
		// 组成报文
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);
		return content;
	}

	public String generatingAsyncMsg(String channelFlow, String transCode) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");
		
		header.addElement("channelCode").addText("GHB");
		header.addElement("TRANSCODE").addText(transCode);
		header.addElement("channelFlow").addText(channelFlow);
		header.addElement("serverFlow").addText(channelFlow);
		header.addElement("serverDate").addText(hbdu.getNowDate());
		header.addElement("serverTime").addText(hbdu.getNowTime());
		header.addElement("status").addText("0");
		header.addElement("errorCode").addText("0");
		header.addElement("errorMsg").addText("");
		
		Element body = root.addElement("body");
		
		body.addElement("MERCHANTID").addText("XJP");
		body.addElement("BANKID").addText(BANKID);
		body.addElement("TRANSCODE").addText(transCode);
		
		Element xmlPara = body.addElement("XMLPARA");
		
		Document doc = DocumentHelper.createDocument(); 
		
		Element ele1 = doc.addElement("Document");
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170801047YKPD0YYCLPA");
		ele1.addElement("ORDERSTATUS").addText("ORDER_COMPLETED");
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(ele1.asXML());
			
			xmlPara.addText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML());
		//组成报文
		String content = "001X12          00000256" + privateResult + document.asXML();
		
		return content;
	}

}
