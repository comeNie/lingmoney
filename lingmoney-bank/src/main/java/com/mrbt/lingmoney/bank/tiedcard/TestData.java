package com.mrbt.lingmoney.bank.tiedcard;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;
import com.mrbt.lingmoney.bank.utils.dto.AsynsResponse;
import com.mrbt.lingmoney.bank.utils.dto.AsynsResponse2;
import com.mrbt.lingmoney.bank.utils.dto.PageResponseDto;

/**
 * 测试数据类
 * 
 * @author YJQ
 *
 */
public class TestData extends HxBaseData {
	public static void main(String[] args) {
		TestData t = new TestData();
		System.out.println(t.generatinAsynsReply());
	}

	// 同步应答
	// 公司垫付还款 借款人单标还款结果查询 还款收益明细提交 还款收益结果查询 日终对账请求

	// 异步应答
	// 绑卡 借款人单笔还款

	public String generatingAsyncMsg(String channelFlow) {
		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		Element root = document.addElement("Document");
		Element header = root.addElement("header");

		header.addElement("channelCode").addText("GHB");
		header.addElement("channelFlow").addText(channelFlow);
		header.addElement("channelDate").addText(hbdu.getNowDate());
		header.addElement("channelTime").addText(hbdu.getNowTime());
		header.addElement("encryptData").addText("");

		Element body = root.addElement("body");

		body.addElement("MERCHANTID").addText("XJP");
		body.addElement("BANKID").addText("GHB");
		body.addElement("TRANSCODE").addText("OGWR0001");

		Element xmlPara = body.addElement("XMLPARA");

		Document doc = DocumentHelper.createDocument();

		Element ele1 = doc.addElement("Document");

		ele1.addElement("OLDREQSEQNO").addText("P2P0012016062304202HyMS9O");
		ele1.addElement("ACNAME").addText("张晓");
		ele1.addElement("IDTYPE").addText("1010");
		ele1.addElement("IDNO").addText("4401**********0044");
		ele1.addElement("MOBILE").addText("134****0587");
		ele1.addElement("ACNO").addText("6236882280000414248");

		System.out.println(doc.getRootElement().asXML());

		try {
			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(ele1.asXML());

			xmlPara.addText(encryptXmlPara);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成签名
		String privateResult = MyRSA.getDataSignature(document.asXML());
		// 组成报文
		String content = "001X11          00000256" + privateResult + document.asXML();

		return content;
	}

	// 异步
	public String generatinAsynsReply() {
		try {
			//提现
//			String url = "http://192.168.1.146:8888/bank/cashWithdrawCallBack/EDE363A7FBCF6B1005A9348203C9B293";
//			return SendMessage.sendBankMessage(create("OGWR0004", "P2P00120170627093L0D9G0BYAS9", "ORDER_COMPLETED"), url);
			//投标
//			String url = "http://192.168.1.153:8081/bank/singleBiddingCallBack/EDE363A7FBCF6B1005A9348203C9B293";
//			return SendMessage.sendBankMessage(create("OGWR0005", "P2P20220170629067561IAAF028L"), url);
			// 绑卡
			String url = "http://192.168.1.153:8083/bank/tiedCardCallBack/4e3be77430834a3d840eefba15bb69cb";
			return SendMessage.sendBankMessage(create("OGWR0002", "P2P20220170717044I09ATT7S61D"), url);
			
			// 单笔还款
//			String url = "http://192.168.1.153:8081/rest/bank/singlePayment/callBack";
//			return SendMessage.sendBankMessage(create("OGWR0008", "P2P20220170629067561IAAF028L"), url);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String create(String transCode, String flowCode, String status) throws Exception {
		// 装载返回报文

		AsynsResponse2 bodyPara = new AsynsResponse2();
		bodyPara.setOLDREQSEQNO(flowCode);
		bodyPara.setORDERSTATUS(status);
		PageResponseDto res=queryRequestContent(bodyPara,transCode,"模拟银行异步回调测试报文");
		return res.getrequestData();
	}
	private String create(String transCode, String flowCode) throws Exception {
		// 装载返回报文
		
		AsynsResponse bodyPara = new AsynsResponse();
		bodyPara.setOLDREQSEQNO(flowCode);
		PageResponseDto res=queryRequestContent(bodyPara,transCode,"模拟银行异步回调测试报文");
		return res.getrequestData();
	}

}
