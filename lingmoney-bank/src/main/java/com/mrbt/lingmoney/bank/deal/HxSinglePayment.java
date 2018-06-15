package com.mrbt.lingmoney.bank.deal;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.HxQueryPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSinglePaymentReqDto;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseBodyDto;
import com.mrbt.lingmoney.bank.utils.dto.ResponseHeadDto;
import com.mrbt.lingmoney.bank.utils.exception.ResponseInfoException;

/**
 * 华兴E账户- 借款人单标还款
 * 
 * @author YJQ
 *
 */
@Component
public class HxSinglePayment extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxSinglePayment.class);

	private String TRANSCODE_PC = "OGW00067";
	private String TRANSCODE_MOBILE = "OGW00095";
	private String TRANSCODE_ASYNC = "OGWR0008";
	private String TRANSCODE_QUERY = "OGW00068";
	public static void main(String[] args) {

		// 发起请求
		try {
			HxSinglePaymentReqDto paymentDto = new HxSinglePaymentReqDto();
			paymentDto.setAPPID("PC");

			new HxSinglePayment().requestPayment(paymentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 请求还款
	 * 
	 * @author YJQ 2017年6月6日
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageResponseDto requestPayment(HxSinglePaymentReqDto paymentDto) throws Exception {

		String logGroup = "\nHxSinglePayment_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求借款人单笔还款");

		String transCode = paymentDto.getAPPID().equals("PC") ? TRANSCODE_PC : TRANSCODE_MOBILE;
		// 请求
		return queryRequestContent(paymentDto, transCode, logGroup);
	}

	/**
	 * 接收异步回应
	 * 
	 * @author YJQ 2017年6月6日
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public String receiveAsync(String message) throws Exception {
		// 生成日志头
		String logGroup = "\nHxSinglePaymentAsyncReceive_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "接收借款人单笔还款异步回应结果");

		Document receDoc = analysisAsyncMsg(message, logGroup);
		
		if(StringUtils.isEmpty(receDoc)){
			logger.info(logGroup + "接收借款人单笔还款异步回应结果失败 ，验签失败");
			throw new ResponseInfoException("接收借款人单笔还款异步回应结果 ，验签失败", 1005);
		}
		String channalFlow = queryTextFromXml(receDoc.asXML(), "OLDREQSEQNO");
		logger.info(logGroup + "接收到的原渠道流水号为:" + channalFlow);
		return channalFlow;
	}

	/**
	 * 返回异步回应结果
	 * 
	 * @author YJQ 2017年6月6日
	 * @param bodyDto
	 * @return
	 * @throws Exception
	 */
	public String responseAsync(ResponseBodyDto bodyDto) throws Exception {
		String logGroup = "\nHxSinglePaymentAsyncResponse_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "回应借款人单笔还款异步回应结果");

		// #start 组织参数
		ResponseHeadDto dto = new ResponseHeadDto();
		dto.setchannelFlow(bodyDto.getOLDREQSEQNO());
		dto.settransCode(TRANSCODE_ASYNC);
		// #end

		// 生成xml
		return createAsyncXml(TRANSCODE_ASYNC, dto, bodyDto, logGroup).asXML();
	}

	/**
	 * 查询
	 * 
	 * @author YJQ 2017年6月6日
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> requestQueryResult(HxQueryPaymentReqDto dto) throws Exception {
		String logGroup = "\nHxQuerySinglePaymentResult_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "查询借款人单笔还款结果");

		// 请求
		Document res = request(dto, TRANSCODE_QUERY, logGroup);

		Map<String, Object> resMap = xmlToMap(res);
		return resMap;
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
		
		ele1.addElement("OLDREQSEQNO").addText("P2P20220170801067KCQCZ45RRZ2");
		
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
