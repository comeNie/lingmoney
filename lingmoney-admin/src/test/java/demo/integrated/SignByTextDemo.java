package demo.integrated;

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
import com.enjoysign.sdk.pdf.exception.SDKException;
import com.enjoysign.sdk.pdf.external.APIPoster;
import com.enjoysign.sdk.pdf.security.MakeSignature;
import com.enjoysign.sdk.pdf.security.SignAppearanceGenerator;
import com.enjoysign.sdk.pdf.security.TSAClient;

import demo.SDKConstants;

/**
 *
 * 生成签章外观,使用正文文字作为印章进行签章，文字会为文本方式保存，适用于对文字内容有严格追溯需求的业务。
 *
 */
public class SignByTextDemo {

	private static final Logger log = LoggerFactory
			.getLogger(SignByTextDemo.class);

	public static void main(String[] args) {

		try {
			// 载入待签署的pdf文件，如果SDK版本包含文档转换，此处可以传入word，excel，ppt，txt，jpg，png，pdf格式的文件，
			// 如果需要对文档进行上限限制，可以调用 new PdfReader(filename,int limit);方法，
			// limit 在传入word,ppt,txt时为最大页数，超出即报错，limit 在传入excel时，为总单元格数，超出即报错。
			PdfReader pdfReader = new PdfReader("sample.pdf");

			// 签章的序列号，最长18位，该序列号在同一PDF中必须唯一，建议与数据库保持映射关系，以便日后可查该签章相关信息
			String serialNo = "demo:" + System.currentTimeMillis();

			// 在PDF上签章，的可见正文，如果使用图片签章时，
			String signText = "签字正文内容";
			// 签章在PDF中哪一页
			int signOnPage = 1;

			// 签名者名称，该设置不会显式的在pdf阅读器中可见，pdf阅读器默认情况下会用证书中的拥有者来显示签名者，该名称可以在验签时取出显示，或者在pdf阅读器校验失败时提示
			String signerName = "李晓明";

			// 签署合同标题，在PDF阅读器中可以显式可见，可以根据具体业务需求填写
			String reason = "《短期融资借款合同》";

			// 请将签署时短信确认电话号码及验证码合并写入签署合同标题，以备留证，如无短信确认签署环节可跳过本步骤。
			reason = reason + "　[签署时验证电话：13600223322，验证码：4723]";

			// 签署地点，在PDF阅读器中可以显式可见，可以根据具体业务需求填写
			String location = "北京";

			// 联系方式，该字段禁用中文。在PDF阅读器中一般不可见，该名称可以在验签时取出显示，可以根据业务需求随意填写
			String signerContactInfo = "tel:400-6028-928";

			// 签章正文字体大小，该设定在使用signText参数时生效
			int fontSize = 10;

			// 签章正文字体颜色，该设定在使用signText参数时生效
			String fontColorHex = "#0000ff"; // chocolate

			// 生成签章外观,使用正文文字作为印章进行签章，文字会为文本方式保存，适用于对文字内容有严格追溯需求的业务。
			PdfSignatureAppearance sap = SignAppearanceGenerator
					.signTextAutoFit(pdfReader, serialNo, signText, signOnPage,
							fontSize, fontColorHex, 0.6f, 0.14f, 1);

			// 创建发送服务器签章的json
			Map<String, Object> jsonFields = new HashMap<String, Object>();

			// 设定用户鉴权，签章id等业务数据
			// appId，唯一标示公司平台，由1号签提供
			jsonFields.put("appId", SDKConstants.APP_ID);

			// SDK版本号
			jsonFields.put("version", SDKConstants.SDK_VERSION);

			// 第三方平台业务订单号
			String orderId = "A15" + System.currentTimeMillis();
			jsonFields.put("orderId", orderId);

			// 合同标题
			jsonFields.put("signTitle", "SDK合同标题");

			// 时间戳
			String timestamp = String
					.valueOf(Calendar.getInstance().getTimeInMillis());
			jsonFields.put("timestamp", timestamp);

			// 签名通信验证加密数据
			String signature = AppSecretUtils.getSignature(SDKConstants.APP_ID,
					orderId, SDKConstants.SDK_VERSION, timestamp,
					SDKConstants.APP_TOKEN);
			jsonFields.put("signature", signature);

			// 签署类型，1为不上传合同
			jsonFields.put("signType", "1");

			// 最后一个签署人值为1，其余可以不传
			jsonFields.put("endFlag", "0");
			// CA授权机构，0默认是CFCA
			jsonFields.put("authority", "0");
			// CA证书类型，包括：0=平台证书，1=企业长期证书，2=企业临时证书，3=个人长期证书，4=个人临时证书
			int caType = 4;
			jsonFields.put("caType", String.valueOf(caType));

			// 当前签署人信息
			Map<String, String> receiver = new HashMap<String, String>();
			// 签署人手机号(个人签署时必须传递)
			receiver.put("phone", "18522335321");
			// 签署人证件类型 0=居民身份证,1=护照,8=企业营业执照
			receiver.put("cardType", "0");
			// 签署人证件号
			receiver.put("cardNumber", "110123198911021011");
			// 签署人真实姓名
			receiver.put("realName", "张弎");
			// 签署人邮箱（企业签署时必须传递）
			receiver.put("email", "123456@qq.com");

			jsonFields.put("receiver", receiver);

			// 创建发动服务器签章的文件，如需最终保全或其他业务可以提交文件发送至服务器
			List<File> files = new ArrayList<File>();
			// files.add(new File("sample.png"));

			// 通讯调试开关，请在生产环境关闭
			APIPoster.setDebug(true);

			// 创建时间戳实体，如果用户自有时间戳服务器，此处进行创建，如果需要使用一号签时间戳服务，tasClient创建为null即可
			TSAClient tsaClient = null;
			// TSAClient tsaClient = new
			// TSAClientBouncyCastle("http://210.74.42.17/timestamp","", "");

			// 发起远程签名
			long signStart = System.currentTimeMillis();
			byte[] signedPdfBytes = MakeSignature.signRemote(sap, signerName,
					reason, location, signerContactInfo,
					SDKConstants.API_SIGN_INTEGRATED_URL, tsaClient, jsonFields,
					files);
			log.info("远程签名结束，耗时：{}ms：",
					(System.currentTimeMillis() - signStart));

			PdfReader.savePdfFile(signedPdfBytes, "d:/textAutoFit.signed.pdf");
			log.info("签署完毕，文档已保存到" + "d:/" + "textAutoFit.signed.pdf");
		} catch (SDKException e) {
			log.error("errorCode:{},errorMessage:{}", e.getErrorCode(),
					e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("error:{}", e.getMessage());
			e.printStackTrace();
		}

	}

}
