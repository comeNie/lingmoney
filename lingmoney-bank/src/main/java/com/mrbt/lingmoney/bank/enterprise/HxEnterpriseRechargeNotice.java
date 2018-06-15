package com.mrbt.lingmoney.bank.enterprise;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.HxBankDateUtils;
import com.mrbt.lingmoney.bank.utils.MyRSA;

/**
 * 企业充值来账通知 (OGW00265) （通知接口） 1 1 ） 由银行发起。 2）当企业客户通过他行来账充值时，我行将到账结果主动通知 P2P 商户。
 *
 * @author syb
 * @date 2017年9月28日 下午3:36:46
 * @version 1.0
 **/
@Component
public class HxEnterpriseRechargeNotice extends HxBaseData {
	private static final Logger logger = LogManager.getLogger(HxEnterpriseRechargeNotice.class);

	private static final String TRANSCODE = "OGW00265";

	/**
	 * 生应答报文返回
	 * 
	 * @param asyncMsg
	 * @param loggroup
	 * @return
	 */
	public String generatinAsynsReply(Map<String, Object> asyncMsg, String loggroup, boolean t) {

		HxBankDateUtils hbdu = new HxBankDateUtils(new Date());

		try {

			Document reply = DocumentHelper.createDocument();
			Element root = reply.addElement("Document");
			Element header = root.addElement("header");
			header.addElement("encryptData").setText("");
			header.addElement("channelCode").setText(CHANNEL_CODE);
			header.addElement("transCode").setText(TRANSCODE);
			header.addElement("channelFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("serverFlow").setText(asyncMsg.get("channelFlow").toString());
			header.addElement("status").setText("0");
			header.addElement("serverTime").setText(hbdu.getNowTime());
			header.addElement("errorCode").setText("0");
			header.addElement("errorMsg").setText("");
			header.addElement("serverDate").setText(hbdu.getNowDate());

			Element body = root.addElement("body");
			body.addElement("MERCHANTID").setText(MERCHANTID);
			body.addElement("TRANSCODE").setText(TRANSCODE);
			body.addElement("BANKID").setText(BANKID);

			Document doc = DocumentHelper.createDocument();

			Element ele = doc.addElement("XMLPARA");

			if (t) {
				ele.addElement("RETURNCODE").setText("000000");
				ele.addElement("RETURNMSG").setText("交易成功");
			} else {
				ele.addElement("RETURNCODE").setText("000001");
				ele.addElement("RETURNMSG").setText("交易失败");
			}
			ele.addElement("OLDREQSEQNO").setText(asyncMsg.get("channelFlow").toString());

			String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(doc.asXML());

			body.addElement("XMLPARA").setText(encryptXmlPara);

			// 生成签名
			String privateResult = MyRSA.getDataSignature(reply.asXML());

			// 组成报文
			String content = "001X11          00000256" + privateResult + reply.asXML().replaceAll("\n", "");
			logger.info(loggroup + "生成的应答返回报文:\n" + content);

			return content;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
