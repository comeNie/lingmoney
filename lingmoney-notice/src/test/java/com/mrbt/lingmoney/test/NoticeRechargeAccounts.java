package com.mrbt.lingmoney.test;

import org.dom4j.Document;
import org.dom4j.Element;

import com.mrbt.lingmoney.bank.HxBaseData;

/**
 * 来账充值测试类
 * @author Administrator
 *
 */
public class NoticeRechargeAccounts extends HxBaseData {
	
	static String transCode = "OGW00265";
	
	public static void main(String[] args) {
		//生成测试数据
		
		Document document = getDocHeardElement("00000000000", "OGW00265");
		
		Element body = document.getRootElement().addElement("body");
		// 交易码，说明请求客户端类型
		body.addElement("TRANSCODE").setText(transCode);

		// 生成要加密的xml，XMLPARA区域
		Element xmlPara = body.addElement("XMLPARA");
//		para.put("MERCHANTID", MERCHANTID);
//		
//		// 生成要加密的xml，XMLPARA区域
//		String encryptXmlPara = getDocParaEncryptStr(para, logGroup);
//
//		// 添加加密数据到XMLPARA中
//		xmlPara.setText(encryptXmlPara);
//		logger.info(logGroup + "生成xml密文,添加到XMLPARA中");
		
		//发送通知请求
	}
	
}
