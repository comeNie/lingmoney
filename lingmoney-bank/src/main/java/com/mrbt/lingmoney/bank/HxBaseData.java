package com.mrbt.lingmoney.bank;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseHeadDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;

/**
 * 华兴银行公共参数
 * 
 * @author Administrator
 *
 */
public class HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxBaseData.class);

	/**
	 * 报文异步请求头
	 */
	public final static String CONTENT_HEARED_ASYN = "001X12          00000256";

	/**
	 * 报文同步步请求头
	 */
	public final static String CONTENT_HEARED_NOASYN = "001X11          00000256";
	/**
	 * 渠道标识
	 */
	public final static String CHANNEL_CODE = "P2P202";

	/**
	 * 商户唯一编号
	 */
	public final static String MERCHANTID = "LQE";

	/**
	 * 商户名称
	 */
	public final static String MERCHANTNAME = "P2P领钱儿";

	/**
	 * 客户经理编号
	 */
	public final static String CUSTMNGRNO = "";

	/**
	 * 银行标识
	 */
	public final static String BANKID = "GHB";

	/**
	 * 生成渠道流水号 商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】），
	 */
	public static String getChannelFlow(String transCode) {
		return CHANNEL_CODE + new HxBankDateUtils(new Date()).getNowDate() + transCode.substring(5, 8)
				+ getRandomReqNo().toUpperCase();
	}

	/**
	 * 获取随机的流水号(11位)
	 * 
	 * @return
	 */
	public static String getRandomReqNo() {
		int length = 11;
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成xml文档头
	 * 
	 * @param logGroup
	 * @param transCode
	 * 
	 * @param requestType
	 * @return
	 */
	public static Document getDocHeardElement(String logGroup, String transCode) {

		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");

		// 请求头
		header.addElement("channelCode").setText(CHANNEL_CODE);
		header.addElement("channelFlow").setText(getChannelFlow(transCode));
		header.addElement("channelDate").setText(hbdu.getNowDate());
		header.addElement("channelTime").setText(hbdu.getNowTime());
		header.addElement("encryptData").setText("");

		logger.info(logGroup + "_生成xml头");

		return document;
	}

	/**
	 * 生成回应异步应答xml头
	 * 
	 * @author YJQ 2017年6月5日
	 * @param transCode
	 *            交易码
	 * @param channelFlow
	 *            原渠道流水号
	 * @param status
	 *            交易状态 0：受理成功 1：受理失败 2：受理中 3：受理超时，不确定 如查询交易返回状态为1时交易可置为失败
	 * 
	 * @param errorCode
	 *            错误代码 单个0：受理成功 其它：错误码
	 * @param errorMsg
	 *            错误代码非0时，返回具体的错误原因
	 * @param logGroup
	 * @return
	 */
	protected Document getResponseDocHeardElement(String transCode, String channelFlow, String status, String errorCode,
			String errorMsg, String logGroup) {

		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");
		// 请求头
		header.addElement("channelCode").setText(CHANNEL_CODE);
		header.addElement("channelFlow").setText(channelFlow);
		header.addElement("transCode").setText(transCode);
		header.addElement("serverFlow").setText("???");
		header.addElement("serverDate").setText(hbdu.getNowDate());
		header.addElement("serverTime").setText(hbdu.getNowTime());
		header.addElement("encryptData").setText("");
		header.addElement("status").setText(status);
		header.addElement("errorCode").setText(errorCode);
		header.addElement("errorMsg").setText(errorMsg);
		logger.info(logGroup + "_生成回应异步应答xml");

		return document;
	}

	/**
	 * 生成回应异步应答xml头 重载 传入dto类型参数
	 * 
	 * @author YJQ 2017年6月6日
	 * @param transCode
	 * @param responseHeadDto
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	protected Document getResponseDocHeardElement(String transCode, ResponseHeadDto responseHeadDto, String logGroup)
			throws Exception {
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");
		// 请求头
		Field[] field = responseHeadDto.getClass().getDeclaredFields();
		for (Field f : field) {
			String fieldName = f.getName();
			Method m = responseHeadDto.getClass().getMethod("get" + fieldName);
			String value = (String) m.invoke(responseHeadDto); // 调用getter方法获取属性值
			header.addElement(fieldName).setText(value);
		}
		logger.info(logGroup + "_生成回应异步应答xml");
		return document;
	}

	/**
	 * 生成xml参数部分加密串
	 * 
	 * @author YJQ 2017年6月6日
	 * @param para
	 *            参数数据传输实体 /参数数据hashmap key-参数名 value-参数值
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	protected static <T> String getDocParaEncryptStr(T para, String logGroup) throws Exception {
		Document doc = DocumentHelper.createDocument();
		Element docu = doc.addElement("Document");

		// 请求头
		if (para.getClass().isAssignableFrom(HashMap.class)) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (HashMap<String, String>) para;
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String paraName = entry.getKey();
				String paraVal = entry.getValue();
				logger.info(logGroup + "请求参数名:" + paraName + ",值:" + paraVal);
				docu.addElement(paraName).setText(paraVal);
			}
		} else {
			Field[] field = para.getClass().getDeclaredFields();
			for (Field f : field) {
				String fieldName = f.getName();
				Method m = para.getClass().getMethod("get" + fieldName);
				Object val = m.invoke(para);

				if (val instanceof String) {
					String value = (String) val;
					logger.info(logGroup + "请求参数名:" + fieldName + ",值:" + value);
					docu.addElement(fieldName).setText(value);
				}
				// 参数中嵌套list的情况
				if (val instanceof List) {
					@SuppressWarnings("unchecked")
					List<T> valLi = (List<T>) val;
					logger.info(logGroup + "请求参数List:" + fieldName + ",个数:" + valLi.size());

					for (T v : valLi) {
						// 添加list的标签
						Element liDoc = docu.addElement(fieldName);
						Field[] liField = v.getClass().getDeclaredFields();

						for (Field lf : liField) {
							String liFieldName = lf.getName();
							Method lim = v.getClass().getMethod("get" + liFieldName);
							Object liV = lim.invoke(v);
							liDoc.addElement(liFieldName).setText((String) liV);
							logger.info(logGroup + "请求列表参数" + fieldName + ",列表属性名:" + liFieldName + ",属性值：" + liV);
						}
					}
				}
			}
		}
		logger.info(logGroup + "生成xml明文" + formatXml(docu.asXML()));
		// 加密
		String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
		return encryptXmlPara;
	}

	/**
	 * 解析银行回应报文
	 * 
	 * @author
	 * @param message
	 * @return
	 */
	public static Document analysisAsyncMsg(String message, String logGroup) {
		logger.info(logGroup + "解析应答报文");
		// 拆分报文
		Map<String, String> resMap = splitContent(message, "解析通知报文");
		// 解析到的数据进行验签
		// boolean signCheck = dataCheckSign(resMap);
		boolean signCheck = true;
		if (signCheck) {
			// 解密数据
			Document document = decryptRetrunMessage(resMap.get("contentXml"));
			if (document != null) {
				return document;
			}
		}
		return null;
	}

	/**
	 * 格式化xml
	 * 
	 * @param str
	 * @return
	 */
	public static String formatXml(String str) {
		try {
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(str);
			Document doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setExpandEmptyElements(true);
			formater.setEncoding("UTF-8");
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
			return out.toString();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 拆分报文
	 * 
	 * @param content
	 *            报文
	 * @param requestInfo
	 *            logger打印头，便于查找问题
	 * @return
	 */
	public static Map<String, String> splitContent(String content, String requestInfo) {
		Map<String, String> resMap = new HashMap<String, String>();

		StringBuffer logSb = new StringBuffer();

		if (StringUtils.isBlank(content)) {
			return null;
		}
		String contentHeard = content.substring(0, 24);
		resMap.put("contentHeard", contentHeard);

		logSb.append("\n报文头:" + contentHeard);

		String contentSign = content.substring(24, 280);
		resMap.put("contentSign", contentSign);

		logSb.append("\n报文签名:" + contentSign);

		String contentXml = content.substring(280);
		resMap.put("contentXml", contentXml.replace("\n", ""));

		logSb.append("\n报文xml:" + contentXml);

		logger.info(requestInfo + logSb.toString());

		return resMap;
	}

	/**
	 * 查询xml中指标签的text
	 * 
	 * @param xml
	 *            xml数据
	 * @param emlName
	 *            节点名称
	 * @return
	 */
	public static String queryTextFromXml(String xml, String emlName) {
		try {
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(xml);
			Document doc = reader.read(in);

			String emlText = doc.selectSingleNode("//" + emlName).getText();

			if (!StringUtils.isBlank(emlText)) {
				return emlText;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询xml中指标签的text
	 * 
	 * @param xml
	 *            xml数据
	 * @param emlName
	 *            节点名称
	 * @return
	 */
	public static String queryTextFromXml(Document doc, String emlName) {
		String emlText = doc.selectSingleNode("//" + emlName).getText();

		if (!StringUtils.isBlank(emlText)) {
			return emlText;
		}
		return null;
	}

	/**
	 * 返回报文数据验签
	 * 
	 * @param resMap
	 *            报文拆分后的map
	 * @return
	 */
	public static boolean dataCheckSign(Map<String, String> resMap) {
		try {
			String dataMd5 = MyRSA.MD5(resMap.get("contentXml"));
			String keyCheck = MyRSA.getPublicKeyCheck(resMap.get("contentSign"));

			if (dataMd5.equals(keyCheck)) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 请求报文数据验签
	 * 
	 * @param resMap
	 *            报文拆分后的map
	 * @return
	 */
	public static boolean dataCheckSign2(Map<String, String> resMap) {
		try {
			String dataMd5 = MyRSA.MD5(resMap.get("contentXml"));
			String keyCheck = MyRSA.getPublicKeyCheck2(resMap.get("contentSign"));

			if (dataMd5.equals(keyCheck)) {
				return true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 解密参数数据
	 * 
	 * @author
	 * @param xmlStr
	 * @return
	 */
	public static Document decryptRetrunMessage(String xmlStr) {
		SAXReader reader = new SAXReader();
		StringReader in = new StringReader(xmlStr);
		try {
			Document doc = reader.read(in);

			// 获取xmlpara节点
			Element xmlParaEle = (Element) doc.selectSingleNode("//XMLPARA");
			if (xmlParaEle != null) {
				// 查询加密数据
				String encryptData = xmlParaEle.getText();
				// 解密
				String decryptData = "<XMLPARA>" + DES3EncryptAndDecrypt.DES3DecryptMode(encryptData) + "</XMLPARA>";
				String fullXml = doc.asXML().replaceAll("<XMLPARA>.*?</XMLPARA>", decryptData);
				return DocumentHelper.parseText(fullXml);
			} else {
				return doc;
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从指定节点开始,递归遍历所有子节点
	 * 
	 * @author chenleixing
	 * @param xmlParaEle
	 * @return
	 */
	public static Element getNodes(Element node, Element xmlParaEle) {
		System.out.println("--------------------");

		// 当前节点的名称、文本内容和属性
		System.out.println("当前节点名称：" + node.getName());// 当前节点名称
		System.out.println("当前节点的内容：" + node.getTextTrim());// 当前节点名称
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();// 属性名称
			String value = attr.getText();// 属性的值
			xmlParaEle.addElement(name).setText(value);
			System.out.println("属性名称：" + name + " 属性值：" + value);
		}

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			xmlParaEle = getNodes(e, xmlParaEle);// 递归
		}
		return xmlParaEle;
	}

	/**
	 * xml转map 第个一级标签中的数据map的名称后面加上标签序列号
	 * 
	 * @param document
	 * @return
	 */
	public static Map<String, Object> xmlToMap(Document document) {
		// 读取文件 转换成Document
		Element root = document.getRootElement();
		// 遍历
		Map<String, Object> resMap = new HashMap<String, Object>();
		listNodes(root, resMap);
		return resMap;
	}

	

	// 遍历当前节点下的所有节点
	public static Map<String, Object> listNodes(Element node, Map<String, Object> resMap) {

		System.out.println("当前节点的名称：" + node.getName() + "\t" + node.getText());
		// 首先获取当前节点的所有属性节点
		List<Attribute> list = node.attributes();
		// 遍历属性节点
		for (Attribute attribute : list) {
			System.out.println("属性" + attribute.getName() + ":" + attribute.getValue());
		}
		// 如果当前节点内容不为空，则输出
		if (!(node.getTextTrim().equals(""))) {
			resMap.put(node.getName(), node.getText());
		}
		// 同时迭代当前节点下面的所有子节点
		// 使用递归
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e, resMap);
		}

		return resMap;
	}

	/**
	 * 生成请求报文
	 * 
	 * @author YJQ 2017年6月6日
	 * @param transCode
	 * @param para
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	protected <T> Document createXml(String transCode, T para, String logGroup) throws Exception {
		// 头
		Document document = getDocHeardElement(logGroup, transCode);

		// body
		Element body = document.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，填充XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");
		String encryptXmlPara = getDocParaEncryptStr(para, logGroup);
		xmlPara.setText(encryptXmlPara);

		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		return document;
	}

	/**
	 * 生成应答报文
	 * 
	 * @author YJQ 2017年6月6日
	 * @param transCode
	 * @param responseHeadDto
	 * @param bodyParaMap
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	protected <T> Document createAsyncXml(String transCode, ResponseHeadDto responseHeadDto, T bodyPara,
			String logGroup) throws Exception {

		Document document = getResponseDocHeardElement(transCode, responseHeadDto, logGroup);
		Element body = document.getRootElement().addElement("body");

		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		String encryptXmlPara = getDocParaEncryptStr(bodyPara, logGroup);

		// 添加加密数据到XMLPARA中
		xmlPara.setText(encryptXmlPara);
		logger.info(logGroup + "生成应答xml密文,添加到XMLPARA中");
		return document;
	}

	/**
	 * 发送请求，并返回请求报文的流水号 [同步请求用]
	 * 
	 * @author YJQ 2017年6月6日
	 * @param paraDto
	 *            报文体参数 dto或map
	 * @param transCode
	 * @param logGroup
	 * @return resLi[0]-请求结果 resLi[1]-请求流水号
	 * @throws Exception
	 */
	protected <T> Document request(T paraDto, String transCode, String logGroup) throws Exception {

		Document xml = createXml(transCode, paraDto, logGroup);

		String xmlData = xml.asXML();

		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");

		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
//		String content = "001X11          000002566850B324BE338FEEDF7BBCAB577BD231EBD2725D7FDFB44AD78544C16B151A989B830F6D13EDFD4D8B8ED6F90835C455122810BF888332E32FC6461DC8BD8F13F380F17DF93F648F4FECF40B958C56D8786ADB8D267B86BFA3252CB5D9C4704F158268A156533E0815EA0C9DC5E7FCC06FE943166D2E32910D433084C4ECB95F<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		
		logger.info(logGroup + "生成的请求报文\n" + content);

		// 发送
		String res = SendMessage.sendMessage(content);
		
//		String res = "001X11          00000256B07AFB03052CAA6C3B7837EB69C283E193434530536B877A4D9FEA2B2F7038F37F05CEAF2986AD79D19DDD3AD888533A9B5496270615CE506802ED6F1C2F62D75BA612C0663193832688D9D45EBDD9F88F5B60F6F8BA2D49ED07994CDA1C28EA0638459195DF6B599B50BFDC5DB5E596BFB7CA16594FD2303DFEC7A39A2B9A4F<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Document><header><encryptData></encryptData><serverFlow>OGW0120180328100000000093770340</serverFlow><channelCode>GHB</channelCode><status>0</status><serverTime>090313</serverTime><errorCode>0</errorCode><errorMsg>success</errorMsg><channelFlow>P2P20220180328073Z3LALZMF4FU</channelFlow><serverDate>20180328</serverDate><transCode>OGW00073</transCode></header><body><TRANSCODE>OGW00073</TRANSCODE><BANKID>GHB</BANKID><XMLPARA>ePJGb24ofODFg4hcQHY5w1421z+gEQSG6TsqA1ZKvDcc6aGA0qkUU6AKGjmbEraMqMb0XkAuawWwOy13xeswcuyFxqHkDeCI8GGcEzeWTazcPPv5qVpNffpFDf9nuutxwVDqJzMlO/t6FySiJQtHet1qgaEykzmxp5+1dU1+IDKEMxiy9QXQwBp1luaPLf+9lu3w0HehL3CrTve4bS6RZyflQHQ385rzB2Isi4rPhG6Q4U0AI4PH3A==</XMLPARA><MERCHANTID>LQE</MERCHANTID></body></Document>";

		// 验证返回结果
		if (StringUtils.isEmpty(res)) {
			logger.info(logGroup + "发送请求失败，无应答");
			throw new ResponseInfoException("发送请求失败，无应答", 1005);
		}
		// 返回了错误页面的情况
		if (res.contains("<!DOCTYPE HTML>")) {
			logger.info(logGroup + "应答了错误页面");
			throw new ResponseInfoException(res, 6032);
		}
		// 验签
		Document resDoc = analysisAsyncMsg(res, logGroup);

		// 验证应答报文的error code
		String errorCode = queryTextFromXml(resDoc, "errorCode");

		// 提示接口访问过于频繁：OGW100200009时不能当失败处理
		if (StringUtils.isNotEmpty(errorCode) && !"0".equals(errorCode)) {
			String errorMsg = queryTextFromXml(resDoc, "errorMsg");
			if ("OGW100200009".equals(errorCode)) {
				logger.info(logGroup + "发送请求失败: 错误码 :" + errorCode + ",错误信息:" + errorMsg);
				throw new ResponseInfoException(errorMsg, 1006);
			}
			
			logger.info(logGroup + "发送请求失败: 错误码 :" + errorCode + ",错误信息:" + errorMsg);
			throw new ResponseInfoException(errorMsg, 1005);
		}

		// 返回请求报文的结果
		return resDoc;
	}

	/**
	 * 发送请求，并返回请求报文的流水号 [同步请求用] 重载
	 * 
	 * @author YJQ 2017年6月6日
	 * @param paraDto
	 *            报文体参数 dto或map
	 * @param transCode
	 * @param logGroup
	 * @return resLi[0]-请求结果 resLi[1]-请求流水号
	 * @throws Exception
	 */
	protected <T> List<Object> request(T paraDto, String transCode, String logGroup, boolean type) throws Exception {
		List<Object> resLi = new ArrayList<>();

		Document xml = createXml(transCode, paraDto, logGroup);

		String channelFlow = queryTextFromXml(xml, "channelFlow");

		resLi.add(0, channelFlow);

		String xmlData = xml.asXML();

		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");

		// 组成报文
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		// 发送
		String res = SendMessage.sendMessage(content);

		// 验证返回结果
		if (StringUtils.isEmpty(res)) {
			logger.info(logGroup + "发送请求失败，无应答");
			throw new ResponseInfoException("发送请求失败，无应答", 1005);
		}
		// 返回了错误页面的情况
		if (res.contains("<!DOCTYPE HTML>")) {
			logger.info(logGroup + "应答了错误页面");
			throw new ResponseInfoException(res, 6032);
		}
		// 验签
		Document resDoc = analysisAsyncMsg(res, logGroup);

		// 验证应答报文的error code
		String errorCode = queryTextFromXml(resDoc, "errorCode");

		if (StringUtils.isNotEmpty(errorCode) && !"0".equals(errorCode)) {
			String errorMsg = queryTextFromXml(resDoc, "errorMsg");
			logger.info(logGroup + "发送请求失败: 错误码 :" + errorCode + ",错误信息:" + errorMsg);
			if("OGW100200009".equals(errorCode)){
				throw new ResponseInfoException(errorMsg, 6033);
			}else{
				throw new ResponseInfoException(errorMsg, 1005);
			}
			
		}
		resLi.add(1, resDoc);
		// 返回请求报文的结果
		return resLi;
	}

	/**
	 * 生成返回表单的报文体 [异步请求]
	 * 
	 * @author YJQ 2017年6月12日
	 * @param paraDto
	 *            参数map/dto
	 * @param transCode
	 * @param logGroup
	 * @return
	 * @throws Exception
	 */
	protected <T> PageResponseDto queryRequestContent(T paraDto, String transCode, String logGroup) throws Exception {

		// 生成xml
		Document xml = createXml(transCode, paraDto, logGroup);
		String xmlData = xml.asXML().replaceAll("\n", "");

		// 生成签名
		String privateResult = MyRSA.getDataSignature(xmlData);
		logger.info(logGroup + "生成私钥的签名");

		// 组成报文 001X12异步请求 001X11为测试用
		String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
		logger.info(logGroup + "生成的请求报文\n" + content);

		// 拆分报文
		Map<String, String> resMap = splitContent(content, "解析异步通知报文");
		// 解析请求数据进行验签
		boolean signCheck = dataCheckSign2(resMap);
		logger.info(logGroup + "请求报文验签：\n" + signCheck);

		PageResponseDto res = new PageResponseDto();
		res.setrequestData(content);
		res.settransCode(transCode);
		res.setchannelFlow(queryTextFromXml(splitContent(content, logGroup + "请求报文拆分").get("contentXml").toString(),
				"channelFlow"));
		return res;
	}

	/**
	 * 生成查询xml文档头
	 * 
	 * @param logGroup
	 * @param transCode
	 * @param olderQseqNo
	 * @param requestType
	 * @return
	 */
	public static Document getDocHeardElementQuery(String logGroup, String transCode, String olderQseqNo) {

		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");

		// 请求头
		header.addElement("channelCode").setText(CHANNEL_CODE);
		header.addElement("channelFlow").setText(getChannelFlow(transCode));
		header.addElement("channelDate").setText(hbdu.getNowDate());
		header.addElement("channelTime").setText(hbdu.getNowTime());
		header.addElement("encryptData").setText("");

		logger.info(logGroup + "_生成xml头");

		return document;
	}

	public static void main(String[] args) {
		String a = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Document><header><encryptData></encryptData><serverFlow>OGW0120170704100000000027076537</serverFlow><channelCode>GHB</channelCode><status>0</status><serverTime>171158</serverTime><errorCode>0</errorCode><errorMsg>success</errorMsg><channelFlow>P2P20220170704066U0XGBICKV7S</channelFlow><serverDate>20170704</serverDate><transCode>OGW00066</transCode></header><body><TRANSCODE>OGW00066</TRANSCODE><BANKID>GHB</BANKID><XMLPARA>WCdKqNJYkxZOhT/y7OZiRPr7JXJZGioLwhEjQ6Bt5y2W7fDQd6EvcF1/d7ww8UZcvT5Jr5N5deVdmia+d2DME7LWnW7t5CBaIMrF0W052QaZGjRB0K58ehm5onbSxxuXDo2yCVj36Na3Eq91IiJU1qd3iMnu09+VQA/5XZoJtF3BDLdmOEINjME082aQNZDRNpvF0F1eIIw/k+H1wDlckluolTDednbI2yO8vhHR7AbnOurJfkgTb3P97b7Gbqo3CV6D0ijdymls4+OwPKluw6XOan/N5/7CyljiK7Hg+aqTvJYHUrc6DnIw8QCjG7GrtNTkHbr4OA6rWzypR/7suD9jClsvAMOLyL8Oy2EbCjE4Sir9KXGJ9wOst9IN7/Bf6aS+Y4ZWP+t0APNoFHn6b/QR4A+KkTs5qfL0exGcnmelvMzhSySc2Bcffd1aVIlN/qZZfYllXY/Pxbl6AoFUNODK2ENSfcvHlbeeTRj1U0y3rw8074HNpuYHwTmUxcomMjvfHlRCgp7+wXzowwg9CKq4RJRglckzX2IX5+V6mte9w2NNPGKfN4a+iXOhEPYHvtnri+/5kgPJz+FRJ1Ev6A6NsglY9+jWtxKvdSIiVNa3EQc/RTOPUw6DMHIO1TNdxcxAi3Kzakj/ngfXT2wWG3mmD83q0Uz4qDp40zV16IDmdtLHLYb34g5SdJ+K2VjwGahjkGAYVha1B10D2rfBZARC9mOFh3v4x3GygoGAJ3h+r90oqR7iSXnfb03RTpZf8qK33uIu1b8hdHxG6NrmfyTUNQ7l0KKujReg6795v+Onn7V1TX4gMlTSldhm6xeeW0sEF2ogWMYHgALq3Vkk8OA9cz+2HQ7Zxwm6IzDQsF0=</XMLPARA><MERCHANTID>LQE</MERCHANTID></body></Document>";

		Document doc = decryptRetrunMessage(a);
		System.out.println(formatXml(doc.asXML()));
	}
}
