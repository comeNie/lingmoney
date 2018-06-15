package com.mrbt.lingmoney.contract;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoysign.sdk.api.AppSecretUtils;
import com.enjoysign.sdk.pdf.PdfReader;
import com.enjoysign.sdk.pdf.PdfSignatureAppearance;
import com.enjoysign.sdk.pdf.draw.ImageUtil;
import com.enjoysign.sdk.pdf.exception.SDKException;
import com.enjoysign.sdk.pdf.parser.KeywordLocaton;
import com.enjoysign.sdk.pdf.parser.KeywordLocator;
import com.enjoysign.sdk.pdf.security.MakeSignature;
import com.enjoysign.sdk.pdf.security.SignAppearanceGenerator;
import com.enjoysign.sdk.pdf.security.TSAClient;
import com.mrbt.lingmoney.model.ContractBorrowerInfo;
import com.mrbt.lingmoney.model.ContractProductInfo;
import com.mrbt.lingmoney.utils.PropertiesUtil;

/**
 * 
 * 定位pdf中的指定关键字，在关键字坐标中心处自动叠加盖章，支持图片类型的签章
 *
 */
public class SignByKeywordDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignByKeywordDemo.class);
	
	private static final String templatePath = PropertiesUtil.getPropertiesByKey("PDF_OUTPUT_PATH");//pdf输出位置
	
	private static final String signaturePicturePath = PropertiesUtil.getPropertiesByKey("SIGNATURE_PICTURE_PATH");//公司章存放位置
	
	/**
	 * 三方合同签章
	 * @param cpi 产品购买信息
	 * @param cbi	投资人信息
	 * @param sourcePdf	合同文件路径
	 * @return
	 * @throws InterruptedException 
	 */
	public static String contractOfgeneration(ContractProductInfo cpi, ContractBorrowerInfo cbi, String sourcePdf) throws InterruptedException {
		
		ContractModel cModel = new ContractModel();
		cModel = getIntermissionService(cpi, cbi, sourcePdf, templatePath + cpi.getTid() + "_contract1.pdf");
		electronicSignatu(cModel);
		Thread.sleep(100);
		
		cModel = getBorrower(cpi, cbi, templatePath + cpi.getTid() + "_contract1.pdf", templatePath + cpi.getTid() + "_contract2.pdf");
		electronicSignatu(cModel);
		Thread.sleep(100);
		
		cModel = getLenders(cpi, cbi, templatePath + cpi.getTid() + "_contract2.pdf", templatePath + cpi.getTid() + "_contract3.pdf");
		electronicSignatu(cModel);
		Thread.sleep(100);
		
		//删除生成的中间文件
		deleteFilePdf(sourcePdf);
		deleteFilePdf(templatePath + cpi.getTid() + "_contract1.pdf");
		deleteFilePdf(templatePath + cpi.getTid() + "_contract2.pdf");
		
		return templatePath + cpi.getTid() + "_contract3.pdf";
	}
	
	//删除中间文件
	private static void deleteFilePdf(String sourcePdf) {
		File file = new File(sourcePdf);
		if (file.exists()) {
			file.delete();
		}
	}

	private static ContractModel getLenders(ContractProductInfo cpi, ContractBorrowerInfo cbi, String sourcePdf, String outFile) {
		ContractModel cModel = new ContractModel();
		cModel.setDebug(true);
		cModel.setChapterType(0);
		cModel.setSourceFile(sourcePdf);
		cModel.setKeyword(cpi.getName());
		cModel.setSignOnPage(3);
		cModel.setSignerName(cpi.getName());
		cModel.setReason("《借款协议》");
		cModel.setSerialNoHeard(cpi.getpName());
		cModel.setSignaturePicture(templatePath + "personStamp.png");
		cModel.setOrderId(cpi.getBizCode());
		cModel.setCaType(4);
		cModel.setSignedPdfBytes(outFile);
		cModel.setCardType("0");
		cModel.setCardNumber(cpi.getIdCard());
		cModel.setRealName(cpi.getName());
		cModel.setPhone(cpi.getTelephone());
		return cModel;
	}

	private static ContractModel getBorrower(ContractProductInfo cpi, ContractBorrowerInfo cbi, String sourcePdf, String outFile) {
		ContractModel cModel = new ContractModel();
		cModel.setDebug(true);
		cModel.setChapterType(0);
		cModel.setSourceFile(sourcePdf);
		cModel.setKeyword(cbi.getEnterpriseName());
		cModel.setSignOnPage(3);
		cModel.setSignerName(cbi.getEnterpriseName());
		cModel.setReason("《借款协议》");
		cModel.setSerialNoHeard(cpi.getpName());
		cModel.setCardNumber(cbi.getAllIdNumber());
		
		if (cbi.getBwIdtype().equals("2020")) {
			cModel.setSignaturePicture(templatePath + cbi.getAllIdNumber() + ".png");
			cModel.setCaType(2);
			cModel.setCardType("8");
			cModel.setEmail(cbi.getEmail());
		} else {
			cModel.setSignaturePicture(templatePath + "personStamp.png");
			cModel.setCaType(4);
			cModel.setCardType("0");
			cModel.setPhone(cbi.getPhone());
		}
		
		cModel.setOrderId(cpi.getBizCode());
		cModel.setSignedPdfBytes(outFile);
		cModel.setRealName(cbi.getEnterpriseName());
		
		return cModel;
	}

	private static ContractModel getIntermissionService(ContractProductInfo cpi, ContractBorrowerInfo cbi, String sourcePdf, String outFile) {
		ContractModel cModel = new ContractModel();
		cModel.setDebug(true);
		cModel.setChapterType(0);
		cModel.setSourceFile(sourcePdf);
		cModel.setKeyword("北京睿博盈通网络科技有限公司");
		cModel.setSignOnPage(3);
		cModel.setSignerName("北京睿博盈通网络科技有限公司");
		cModel.setReason("《借款协议》");
		cModel.setSerialNoHeard(cpi.getpName());
		cModel.setSignaturePicture(signaturePicturePath + "simpleStamp.png");
		cModel.setOrderId(cpi.getBizCode());
		cModel.setCaType(1);
		cModel.setSignedPdfBytes(outFile);
		cModel.setCardType("8");
		cModel.setCardNumber("91110107318334043P");
		cModel.setRealName("北京睿博盈通网络科技有限公司");
		cModel.setEmail("malan@wdzggroup.com");
		return cModel;
	}

	/**
	 * 生成签章合同
	 * @param cModel
	 */
	public static void electronicSignatu(ContractModel cModel) {
		try {

			// 载入待签署的pdf文件，如果SDK版本包含文档转换，此处可以传入word，excel，ppt，txt，jpg，png，pdf格式的文件，
			// 如果需要对文档进行上限限制，可以调用 new PdfReader(filename,int limit);方法，
			// limit 在传入word,ppt,txt时为最大页数，超出即报错，limit 在传入excel时，为总单元格数，超出即报错。
			PdfReader pdfReader = new PdfReader(cModel.getSourceFile());
			// 要定位的关键字
			String keyword = cModel.getKeyword();
			// 关键字所在页
			int signOnPage = cModel.getSignOnPage();
			// 取出所在页所有的匹配关键字定位，也可使用getAllLocations取出整个文档的匹配关键字
			List<KeywordLocaton> keywords = KeywordLocator.getLocationsOnPage(pdfReader, keyword, signOnPage);
			
			if (keywords != null) {
				// 取出第一个匹配关键字定位，用于签章演示
				KeywordLocaton firstKeyword = keywords.get(0);

				// 签名者名称，该设置不会显式的在pdf阅读器中可见，pdf阅读器默认情况下会用证书中的拥有者来显示签名者，该名称可以在验签时取出显示，或者在pdf阅读器校验失败时提示
				String signerName = cModel.getSignerName();

				// 签署合同标题，在PDF阅读器中可以显式可见，可以根据具体业务需求填写
				String reason = cModel.getReason();

				// 请将签署时短信确认电话号码及验证码合并写入签署合同标题，以备留证，如无短信确认签署环节可跳过本步骤。
//				reason = reason + "　[签署时验证电话：13600223322，验证码：4723]";

				// 签署地点，在PDF阅读器中可以显式可见，可以根据具体业务需求填写
				String location = "北京";

				// 联系方式，该字段禁用中文。在PDF阅读器中一般不可见，该名称可以在验签时取出显示，可以根据业务需求随意填写
				String signerContactInfo = "tel:400-0051-655";

				// 签章的序列号，最长18位，该序列号在同一PDF中必须唯一，建议与数据库保持映射关系，以便日后可查该签章相关信息
				String serialNo = cModel.getSerialNoHeard() + System.currentTimeMillis();

				// 读取图片文件到字节流
				byte[] imageBytes = ImageUtil.getBytes(cModel.getSignaturePicture());

				// 透明化处理图片，如不须透明可跳过该步骤
				imageBytes = ImageUtil.transferAlpha(imageBytes);

				// 生成签章外观,使用图片作为印章进行签章，如果不需要使用背景，背景字段传入null。适用于一般图片印章签章。
				// 关键字定位签章时，同样支持背景字段
				PdfSignatureAppearance sap = SignAppearanceGenerator.signImage(pdfReader, imageBytes, serialNo, firstKeyword, 0.1875f, null);

				// 创建发送服务器签章的json
				Map<String, Object> jsonFields = new HashMap<String, Object>();

				// 设定用户鉴权，签章id等业务数据
				// appId，唯一标示公司平台，由1号签提供
				jsonFields.put("appId", SDKConstants.APP_ID);

				// SDK版本号
				jsonFields.put("version", SDKConstants.SDK_VERSION);

				// 第三方平台业务订单号
				String orderId = cModel.getOrderId() + ":" + System.currentTimeMillis();
				jsonFields.put("orderId", orderId);

				// 合同标题
				jsonFields.put("signTitle", cModel.getReason());

				// 时间戳
				String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
				jsonFields.put("timestamp", timestamp);

				// 签名通信验证加密数据
				String signature = AppSecretUtils.getSignature(SDKConstants.APP_ID, orderId, SDKConstants.SDK_VERSION, timestamp, SDKConstants.APP_TOKEN);
				jsonFields.put("signature", signature);

				// 签署类型，1为不上传合同
				jsonFields.put("signType", "1");

				// 最后一个签署人值为1，其余可以不传
				jsonFields.put("endFlag", "0");
				// CA授权机构，0默认是CFCA
				jsonFields.put("authority", "0");
				// CA证书类型，包括：0=平台证书，1=企业长期证书，2=企业临时证书，3=个人长期证书，4=个人临时证书
				int caType = cModel.getCaType();
				jsonFields.put("caType", String.valueOf(caType));

				// 当前签署人信息
				Map<String, String> receiver = new HashMap<String, String>();
				// 签署人手机号(个人签署时必须传递)
				
				if (cModel.getCaType() == 3 || cModel.getCaType() == 4) {
					receiver.put("phone", cModel.getPhone());
				}
				// 签署人证件类型 0=居民身份证,1=护照,8=企业营业执照
				receiver.put("cardType", cModel.getCardType());
				// 签署人证件号
				receiver.put("cardNumber", cModel.getCardNumber());
				// 签署人真实姓名
				receiver.put("realName", cModel.getRealName());
				// 签署人邮箱（企业签署时必须传递）对应的企业邮箱
				if (cModel.getCaType() == 1 || cModel.getCaType() == 2) {
					receiver.put("email", cModel.getEmail());
				}
				jsonFields.put("receiver", receiver);
				// 创建发动服务器签章的文件，如需最终保全或其他业务可以提交文件发送至服务器
				List<File> files = new ArrayList<File>();
				// files.add(new File("sample.png"));
				
				// 通讯调试开关，请在生产环境关闭
//				APIPoster.setDebug(cModel.isDebug());
				// 创建时间戳实体，如果用户自有时间戳服务器，此处进行创建，如果需要使用一号签时间戳服务，tasClient创建为null即可
				TSAClient tsaClient = null;
				// TSAClient tsaClient = new
				// TSAClientBouncyCastle("http://210.74.42.17/timestamp","",
				// "");

				// 发起远程签名
				long signStart = System.currentTimeMillis();
				byte[] signedPdfBytes = MakeSignature.signRemote(sap, signerName, reason, location, signerContactInfo, SDKConstants.API_SIGN_INTEGRATED_URL, tsaClient,
						jsonFields, files);
				LOGGER.info("远程签名结束，耗时：{}ms：", (System.currentTimeMillis() - signStart));

				PdfReader.savePdfFile(signedPdfBytes, cModel.getSignedPdfBytes());

				LOGGER.info("签署完毕，文档已保存到" + cModel.getSignedPdfBytes());

			} else {
				throw new SDKException("51010", "PDF中未找到制定的关键字");
			}
		} catch (SDKException e) {
			LOGGER.error("errorCode:{},errorMessage:{}", e.getErrorCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("error:{}", e.getMessage());
			e.printStackTrace();
		}

	}
}
