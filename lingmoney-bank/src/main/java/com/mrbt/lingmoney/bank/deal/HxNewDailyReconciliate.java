package com.mrbt.lingmoney.bank.deal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.utils.DES3EncryptAndDecrypt;
import com.mrbt.lingmoney.bank.utils.MyRSA;
import com.mrbt.lingmoney.bank.utils.SendMessage;

/**
  *新日终对账
  *@author yiban
  *@date 2018年3月27日 上午11:53:53
  *@version 1.0
  *
  *接口说明
  *1） 由第三方公司发起。
  *2） 商户需在 D 日 D-1 日内所有交易操作上送给到银行。
  *3） 交易操作包括投标、撤标、流标、放款、还款、专属账户充值、公司
  *垫付、投标优惠返回、单笔奖励或分红、提现、自动投标 、自动还款。
  *4） 需将交易操作按照格式生成 txt 文件，并压缩为 ZIP 文件。
  *5） 每个 txt 格式文件数据量限定为 10 万笔，超过 10 万笔需要另外生成
  *txt 格式文件，并单独压缩为 ZIP 文件。
  *6） 每个 txt 格式文件必须单独进行压缩成 ZIP 文件。
  *7） 对账时间：
  *a) D 日 0:00---5:00 期间，第三方公司需在调用该接口将 D-1 日 ZIP 格式
  *文件依次上送到银行，银行收到文件后，将实时返回受理结果；
  *b) D 日 5:00 至 7:00 期间，银行将对平台上送 D-1 日对账文件进行对账
  *处理；
  *c) D 日 7:30 后，第三方公司需通过该接口查询对账结果。
  *8） 补充说明：
  *a) 银行的对账是以银行实际处理日期为准，可能存在平台记录该笔交易
  *为 D-1 日完成，但实际银行处理时间为 D 日的情况，该情况平台 D 日
  *的交易记录会体现在银行 D+1 日对账文件中。
  *接口文档
  *b) 若商户记录的 D日发生的的交易流水在 D和 D+1日的对账文件都不存
  *在，则此交易即为未送到银行。可视交易为失败。
 **/
@Component
public class HxNewDailyReconciliate extends HxBaseData {

    private static final Logger logger = LogManager.getLogger(HxNewDailyReconciliate.class);
    private static final String TRANSCODE = "OGW00270";

    public static void main(String[] args) {
        Document doc = new HxNewDailyReconciliate().requestHxNewDailyReconciliate("D", "20180327", "", "1", "1", "",
                null);

        System.out.println(doc.asXML());
    }

    /**
     * 请求日终对账
     * 
     * @author yiban
     * @date 2018年3月27日 下午1:57:53
     * @version 1.0
     * @param oprType U:上传 D：下载
     * @param fileDate YYYYMMDD，传昨天的日期
     * @param fileContext 生成明细文件，然后压缩成ZIP文件，读取ZIP文件流进行BASE64后放到该字段值。
     * @param fileCount 当文件条数超过N时需分多个文件处理，每个文件最大条数不超过N。此N值后续测试时提供
     * @param fileIndex 当只有一个文件时，这个序号为1
     * @param logGroup 日志头
     * @return
     *
     */
    public Document requestHxNewDailyReconciliate(String oprType, String fileDate, String fileContext,
            String fileCount,
            String fileIndex, String fileName, String logGroup) {
        Document xml = createHxNewDailyReconciliateRequestXml(oprType, fileDate, fileContext, fileCount, fileIndex,
                fileName, logGroup);
        String xmlData = xml.asXML().replaceAll("\n", "");
        // 生成签名
        String privateResult = MyRSA.getDataSignature(xmlData);

        // 组成报文
        String content = CONTENT_HEARED_NOASYN + privateResult + xmlData;

        logger.info("{}生成的日终对账请求报文。\n{}", logGroup, content);

        String responseMsg = SendMessage.sendMessage(content);
        if (!StringUtils.isEmpty(responseMsg)) {
            logger.info("{}日终对账请求成功，返回信息：\n{}", logGroup, responseMsg);

            return analysisAsyncMsg(responseMsg, logGroup);
        } else {
            logger.info("{}日终对账请求失败，返回数据为空。", logGroup);

            return null;
        }
    }

    private Document createHxNewDailyReconciliateRequestXml(String oprType, String fileDate, String fileContext,
            String fileCount, String fileIndex, String fileName, String logGroup) {
        Document doc = getDocHeardElement(logGroup, TRANSCODE);

        Element body = doc.getRootElement().addElement("body");
        body.addElement("TRANSCODE").setText(TRANSCODE);
        Element xmlpara = body.addElement("XMLPARA");

        // 加密xmlpara
        Document xmlparaDoc = DocumentHelper.createDocument();
        Element xmlparaElem = xmlparaDoc.addElement("Document");
        xmlparaElem.addElement("MERCHANTID").setText(MERCHANTID);
        xmlparaElem.addElement("BUSTYPE").setText("P2P");
        xmlparaElem.addElement("OPERFLAG").setText("TCK");
        xmlparaElem.addElement("OPERTYPE").setText(oprType);
        xmlparaElem.addElement("FILEDATE").setText(fileDate);
        //上传是必传；下载无需上送。
        if (oprType.equals("U")) {
            //GHB_{商户唯一编号}_{业务类型}_{文件类型}_{操作类型}_{文件日期}_{目前上送的文件序号}.zip
            xmlparaElem.addElement("FILENAME").setText(fileName);
        }
        xmlparaElem.addElement("FILECONTEXT").setText(fileContext);
        xmlparaElem.addElement("FILECOUNT").setText(fileCount);
        xmlparaElem.addElement("FILEINDEX").setText(fileIndex);

        System.out.println(xmlparaElem.asXML());
        logger.info(logGroup + "生成日终对账xmlpara明文：\n" + xmlparaElem.asXML());

        try {
            String encryptXmlPara = DES3EncryptAndDecrypt.DES3EncryptMode(xmlparaElem.asXML());
            System.out.println("加密后：" + encryptXmlPara);
            // 加密数据放入XMLPARA元素中
            xmlpara.setText(encryptXmlPara);
        } catch (Exception e) {
            logger.info("{}加密日终对账请求XMLPARA失败，系统错误。\n{}", logGroup, e.toString());

            e.printStackTrace();
        }

        return doc;
    }

}
