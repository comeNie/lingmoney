package com.mrbt.lingmoney.bank.reward;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.HxQueryTradingResult;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
 * @author syb
 * @date 2017年6月7日 下午2:37:33
 * @version 1.0
 * @description 单笔奖励或分红
 **/
@Component
public class HxSingleRewardOrCutAMelon extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxSingleRewardOrCutAMelon.class);
	private static final String TRANSCODE = "OGW00076";
	
	
	public static void main(String[] args) {
		String logGroup = "000000000000000";
		String appId = "PC";
		String acNo = "6236882280001844773";
		String acName = "喻龙";
		String amount = "1";
		String remark = "测试红包发放";
//		Document document = new HxSingleRewardOrCutAMelon().requestSingleRewardOrCutAMelon(appId, acNo, acName, amount, remark, logGroup);
//		System.out.println(formatXml(document.asXML()));
		
		String resjnlno = "P2P20220180110076U8PVCZZFT52"; 
		Document document = new HxQueryTradingResult().requestQueryTradingResult(resjnlno, "发红包查询", logGroup);
		System.out.println(formatXml(document.asXML()));
	}

	/**
	 * 单笔奖励或分红 请求
	 * 
	 * @param appId
	 *            应用标识
	 * @param acNo
	 *            收款账号
	 * @param acName
	 *            收款户名
	 * @param amount
	 *            交易金额
	 * @param remark
	 *            备注
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	public Document requestSingleRewardOrCutAMelon(String appId, String acNo, String acName, String amount,
			String remark, String logGroup) {
		Document xml = createSingleRewardOrCutAMelonReqXml(appId, acNo, acName, amount, remark, logGroup);
		
		String xmlData = xml.asXML().replaceAll("\n", "");
		String privateResult = MyRSA.getDataSignature(xmlData);
		String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;
		
		logger.info(logGroup + "生成的 单笔奖励或分红 请求报文：\n" + content);
		
		// 返回解密后银行响应报文
		String responseMsg = SendMessage.sendMessage(content);
		if (!StringUtils.isEmpty(responseMsg)) {
			logger.info(logGroup + "单笔奖励或分红 请求成功。返回信息。\n" + responseMsg);
			
			return analysisAsyncMsg(responseMsg, logGroup);
		} else {
			logger.info(logGroup + "单笔奖励或分红 请求失败。返回信息为空。");
			
			return null;
		}
	}

	/**
	 * 创建 单笔奖励或分红 请求XML
	 * 
	 * @param appId
	 *            应用标识
	 * @param acNo
	 *            收款账号
	 * @param acName
	 *            收款户名
	 * @param amount
	 *            交易金额
	 * @param remark
	 *            备注
	 * @param logGroup
	 *            日志头
	 * @return
	 */
	private Document createSingleRewardOrCutAMelonReqXml(String appId, String acNo, String acName, String amount,
			String remark, String logGroup) {
		Document document = getDocHeardElement(logGroup, TRANSCODE);
		
		Element body = document.getRootElement().addElement("body");
		body.addElement("TRANSCODE").setText(TRANSCODE);
		Element xmlPara = body.addElement("XMLPARA");

		// 生成要加密的xml，XMLPARA区域
		Document document2 = DocumentHelper.createDocument();
		Element docu = document2.addElement("Document");
		docu.addElement("MERCHANTID").setText(MERCHANTID);
		docu.addElement("APPID").setText(appId);
		docu.addElement("ACNO").setText(acNo);
		docu.addElement("ACNAME").setText(acName);
		docu.addElement("AMOUNT").setText(amount);
		if (!StringUtils.isEmpty(remark)) {
			docu.addElement("REMARK").setText(remark);
		} else {
			docu.addElement("REMARK").setText("");
		}
		
		logger.info(logGroup + "生成单笔奖励或分红请求xml明文" + formatXml(document2.asXML()));
		
		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());
			xmlPara.setText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(logGroup + "加密数据失败。" + e.getMessage());
		}
		
		return document;
	}
}
