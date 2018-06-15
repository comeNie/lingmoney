package com.mrbt.lingmoney.bank.tiedcard;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.tiedcard.dto.HxTiedCardReqDto;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseBodyDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseHeadDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;

/**
 * 华兴-绑卡
 * 
 * @author YJQ
 *
 */
@Component
public class HxTiedCard extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxTiedCard.class);

	private String TRANSCODE_PC = "OGW00044";
	private String TRANSCODE_MOBILE = "OGW00091";
	private String TRANSCODE_ASYNC = "OGWR0002";

	public static void main(String[] args) {
		/*
		 * // 发起请求 String appId = "PC";// PC,APP,WX String acNo =
		 * "6222020409012367804"; String uId =
		 * "ABCD765D1C52A04ABE7EFBDAEA722E87"; try { //new
		 * HxTiedCard().requestTiedCard(appId, acNo, uId); } catch (Exception e)
		 * { e.printStackTrace(); }
		 */
		/*
		 * //生成测试异步通知报文 String channelFlow = "P2P00120170601042W0XTXHHBQLK";
		 * String asyncMsg = new HxTiedCard().generatingAsyncMsg(channelFlow);
		 * System.out.println(asyncMsg);
		 * 
		 * //解析测试异步通知报文 Document strXml = new
		 * HxTiedCard().accountOpenAsyncMsg(asyncMsg);
		 * 
		 * //生成异步通知报文后回复的报文 System.out.println(new
		 * HxTiedCard().generatinAsynsReply(strXml));
		 */
	}

	/**
	 * 发起绑卡请求
	 * 
	 * @author YJQ 2017年6月1日
	 * @param clientType
	 *            请求平台
	 * @param appId
	 *            应用标识
	 * @param acNo
	 *            银行卡号
	 * @param uId
	 *            用户id
	 * @return 流水号
	 */
	public PageResponseDto requestTiedCard(HxTiedCardReqDto req) throws Exception {

		// 生成日志头
		String logGroup = "\nHxTiedCard_" + System.currentTimeMillis() + "_";

		logger.info(logGroup + "请求获取绑卡报文");

		// 生成数据xml文件
		String transCode = req.getAPPID().equals("PC") ? TRANSCODE_PC : TRANSCODE_MOBILE;

		// 返回请求报文的流水号
		return queryRequestContent(req, transCode, logGroup);
	}

	/**
	 * 处理异步返回结果
	 * 
	 * @author YJQ 2017年6月2日
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String receiveTiedCardAsync(String message) throws Exception {
		// 生成日志头
		String logGroup = "\nHxTiedCardAsyncReceive_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "接收绑卡异步回应结果");
		Document receDoc = analysisAsyncMsg(message, logGroup);
		if (StringUtils.isEmpty(receDoc)) {
			logger.info(logGroup + "接收绑卡异步回应结果失败 ，报文为空");
			throw new ResponseInfoException("接收绑卡异步回应结果失败 ，报文为空", 1005);
		}

		String channalFlow = receDoc.selectSingleNode("//OLDREQSEQNO").getText();
		if (StringUtils.isEmpty(channalFlow)) {
			logger.info(logGroup + "接收绑卡异步回应结果失败 ，流水号为空");
			throw new ResponseInfoException("接收绑卡异步回应结果失败 ，流水号为空", 1005);
		}
		logger.info(logGroup + "接收到的原渠道流水号为:" + channalFlow);
		
		return channalFlow;
	}

	/**
	 * 回应异步应答结果
	 * 
	 * @author YJQ 2017年6月5日
	 * @param channelFlow
	 * @param returnCode
	 * @param returnMsg
	 * @return
	 * @throws Exception
	 */
	public String responseTiedCardAsync(ResponseBodyDto dto) throws Exception {
		String logGroup = "\nHxTiedCardAsyncResponse_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "回应绑卡异步回应结果");

		// 组织head参数
		ResponseHeadDto headDto = new ResponseHeadDto();
		headDto.setchannelFlow(dto.getOLDREQSEQNO());
		headDto.settransCode(TRANSCODE_ASYNC);

		// 生成xml
		Document responseDoc = createAsyncXml(TRANSCODE_ASYNC, headDto, dto, logGroup);
		return responseDoc.asXML().replaceAll("\n", "");
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
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170731044YVT0NHK33UB");
		
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
