package com.mrbt.lingmoney.bank.tiedcard;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;

/**
  *更换个人绑定卡(OGW00272)（可选，跳转我行页面处理）
  *@author yiban
  *@date 2018年3月8日 上午11:44:53
  *@version 1.0
  *@desc  接口说明
  * 1） 由第三方公司发起，本接口只支持个人客户进行换卡操作。
  * 2） 必须符合如下规则：
  *  （1）客户资产为零。
  *  （2）客户已完成实名认证操作。
  * 3） 对公客户绑卡成功后，客户在我行的所有存管账号都同步绑定该卡。
  * 4） 本接口对企业用户不适用。
  * 5） 此接口不提供回查接口和异步通知，如需查询最新绑卡信息，可调用
  *   “绑定卡信息查询 (OGW00256)”查询。
  * 6） 客户在页面流程操作不可超过 25 分钟，否则请求超时。
 **/
@Component
public class HxChangePersonalBindCard extends HxBaseData {

    private static final Logger LOG = LogManager.getLogger(HxChangePersonalBindCard.class);
    private static String logHeard = "\nHxChangePersonalBindCard_";
    private static String TRANSCODE_PC = "OGW00273";
    private static String TRANSCODE_MOBILE = "OGW00272";

    public Map<String, String> requestAccountOpen(Integer clientType, String appId, String acNo, String returnUrl,
            String uid) {
        String transCode = "";

        if (clientType == 0) {
            transCode = TRANSCODE_PC;
        } else {
            transCode = TRANSCODE_MOBILE;
        }

        Map<String, String> contentMap = new HashMap<String, String>();

        //生成日志头
        String logGroup = logHeard + System.currentTimeMillis() + "_";

        //生成数据xml文件
        Document xml = createHxChangePersonalBindCardXml(clientType, appId, transCode, acNo, returnUrl, logGroup, uid);

        //转生String类型
        String xmlData = xml.asXML().replaceAll("\n", "");
        //生成签名
        String privateResult = MyRSA.getDataSignature(xmlData);

        LOG.info(logGroup + "生成私钥的签名");
        //组成报文
        String content = CONTENT_HEARED_ASYN + privateResult + xmlData;
        LOG.info(logGroup + "生成的请求报文\n" + content);

        contentMap.put("requestData", content);

        if (clientType == 0) {
            contentMap.put("transCode", TRANSCODE_PC);
        } else {
            contentMap.put("transCode", TRANSCODE_MOBILE);
        }

        //拆分报文
        Map<String, String> resMap = splitContent(contentMap.get("requestData"), "解析异步通知报文");
        //解析请求数据进行验签
        boolean signCheck = dataCheckSign2(resMap);
        LOG.info(logGroup + "请求报文验签：\n" + signCheck);

        //获取流水号
        String channelFlow = queryTextFromXml(xmlData, "channelFlow");
        contentMap.put("channelFlow", channelFlow);

        return contentMap;
    }

    private static Document createHxChangePersonalBindCardXml(int clientType, String appId, String transCode,
            String acNo, String returnUrl, String logGroup, String uid) {
        Document document = getDocHeardElement(logGroup, transCode);

        queryTextFromXml(document.asXML(), "channelFlow");

        Element body = document.getRootElement().addElement("body");

        // 交易码，说明请求客户端类型
        body.addElement("TRANSCODE").setText(transCode);
        Element xmlPara = body.addElement("XMLPARA");

        // 生成要加密的xml，XMLPARA区域
        Document document2 = DocumentHelper.createDocument();
        Element docu = document2.addElement("Document");

        docu.addElement("MERCHANTID").setText(MERCHANTID);
        docu.addElement("APPID").setText(appId);
        docu.addElement("TTRANS").setText("47");
        docu.addElement("MERCHANTNAME").setText(MERCHANTNAME);
        docu.addElement("ACNO").setText(acNo);
        docu.addElement("RETURNURL").setText(returnUrl + uid);

        LOG.info(logGroup + "生成xml明文" + formatXml(document.asXML()) + "\n" + formatXml(docu.asXML()));

        try {
            // 加密
            String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(docu.asXML());

            // 添加加密数据到XMLPARA中
            xmlPara.setText(encryptXmlPara);
            LOG.info(logGroup + "生成xml密文,添加到XMLPARA中");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

}
