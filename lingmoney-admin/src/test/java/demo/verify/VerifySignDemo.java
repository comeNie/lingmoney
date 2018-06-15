package demo.verify;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.enjoysign.sdk.api.AppSecretUtils;
import com.enjoysign.sdk.pdf.external.APIPoster;
import com.enjoysign.sdk.pdf.security.PdfVerifier;

import demo.SDKConstants;

/**
 * 
 * 验签DEMO，实现本地验签，一号签云验签 及PDF签章信息的精确字段取出
 *
 */
public class VerifySignDemo {

	public static void main(String[] args) {

		try {

			// 加载pdf文件或字节流，创建验签实体
			PdfVerifier result = new PdfVerifier("image.signed.pdf");

			// 创建发送服务器签章的json
			Map<String, Object> jsonFields = new HashMap<String, Object>();
			// 设定用户鉴权，签章id等业务数据
			// appId，唯一标示公司平台，由1号签提供
			jsonFields.put("appId", SDKConstants.APP_ID);

			// SDK版本号
			jsonFields.put("version", SDKConstants.SDK_VERSION);

			// 第三方平台业务订单号
			String orderId = "A151498127323083";
			jsonFields.put("orderId", orderId);

			// 时间戳
			String timestamp = Calendar.getInstance().getTimeInMillis() + "";
			jsonFields.put("timestamp", timestamp);

			// 验签通信验证加密数据
			String signature = AppSecretUtils.getSignature(SDKConstants.APP_ID,
					orderId, SDKConstants.SDK_VERSION, timestamp,
					SDKConstants.APP_TOKEN);
			jsonFields.put("signature", signature);

			// 通讯调试开关，请在生产环境关闭
			APIPoster.setDebug(true);

			// 从PDF中分离出签名数据，发送签名数据及必要用户信息到一号签平台进行验签
			JSONObject remoteVerifyJSON = result.getRemoteVerifyResult(
					jsonFields, SDKConstants.API_VERIFY_URL);
			System.out.println(remoteVerifyJSON);

			// 本地验签信息
			JSONObject localVerifyJSON = result.toJSON();
			System.out.println(localVerifyJSON);

			// 以下为签章验证信息的单项取出查询
			// 该文档是否有效，如果被篡改过此处输出false
			System.out.println("isAllValid:" + result.isAllValid());

			// 该文档共计签章次数
			System.out.println("Total Revision:" + result.getTotalRevisions());

			// 该文档是否可以继续进行认证签章
			// 即
			// sap.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_FORM_FILLING)
			// 或
			// sap.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_FORM_FILLING_AND_ANNOTATIONS)
			// 或
			// sap.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED)
			System.out.println("Is cert sign able:" + result.isCertSignAble());

			// 该文档是否可以继续进行认可签章即
			// sap.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED)
			System.out.println(
					"Is approval sign able:" + result.isApprovalSignAble());

			// 循环所有次签章
			for (String name : result.getSigNameValid().keySet()) {

				// 签章序列号（域），pdf中的签章唯一标识（签章时自定义输入）
				System.out.println("SerialNo:" + name);
				// 当前签章的次序号，从1开始
				System.out.println(
						"Revision Num:" + result.getRevisionNumBySigName(name));

				// 本次签章是否有效，如果本次签章被篡改，则输出false，并且本次之后的签章均为false
				System.out.println("Signature valid:"
						+ result.getSigNameValid().get(name));

				// 签章EnjoysignAppID，可能为空
				System.out.println("EnjoysignAppID:"
						+ (result.getEnjoysignAppIDBySigName(name) == null
								? "empty"
								: result.getEnjoysignAppIDBySigName(name)));

				// 签章EnjoysignSignID，可能为空
				System.out.println("EnjoysignSignID:"
						+ (result.getEnjoysignSignIDBySigName(name) == null
								? "empty"
								: result.getEnjoysignSignIDBySigName(name)));

				// 签章EnjoysignSignClient，可能为空
				System.out.println("EnjoysignSignClient:"
						+ (result.getEnjoysignSignClientBySigName(name) == null
								? "empty"
								: result.getEnjoysignSignClientBySigName(
										name)));

				// 签章EnjoysignSignType，可能为空
				System.out.println("EnjoysignSignType:"
						+ (result.getEnjoysignSignTypeBySigName(name) == null
								? "empty"
								: result.getEnjoysignSignTypeBySigName(name)));

				// 签章联系方式，可能为空
				System.out.println(
						"Contact:" + (result.getContactBySigName(name) == null
								? "empty"
								: result.getContactBySigName(name)));

				// 本次签章是否是最后一次签章并对整个文档有效
				System.out.println("SigNameCoverAll:"
						+ result.getSigNameCoverAll().get(name));

				// 本次签章时间戳是否通过ADOBE验证
				System.out.println("isTimeStampVerified:"
						+ result.isTimeStampVerifiedBySigName(name));

				// 本次签章使用加密算法
				System.out.println("EncryptionAlgorithm:"
						+ result.getEncryptionAlgorithmBySigName(name));

				// 签名信息在pdf中的存储方式
				System.out.println("FilterSubtype:"
						+ result.getFilterSubtypeBySigName(name));
				// 本次签章使用摘要算法
				System.out.println("HashAlgorithm:"
						+ result.getHashAlgorithmBySigName(name));
				// 签章原因信息（签章时自定义输入）
				System.out.println("Reason:" + result.getReasonBySigName(name));
				// 签章地点信息（签章时自定义输入）
				System.out.println(
						"Location:" + result.getLocationBySigName(name));

				// 签章证书的持有人信息，此处显式的在阅读器中签名信息显示
				System.out.println(
						"SignerName:" + result.getSignerNameBySigName(name));

				// 签章证书的完整标题，包括整除持有人，证件，证书序列号等一系列信息
				System.out
						.println("Subject:" + result.getSubjectBySigName(name));

				// 本签章时间戳服务
				System.out.println("TimeStampService:"
						+ result.getTimeStampServiceBySigName(name));
				// 本签章时间戳记录时间
				System.out.println("TimeStampDate:"
						+ result.getTimeStampDateBySigName(name));

				// 签章证书的创建人信息，creator信息，（签章时自定义输入）
				System.out.println("SignerAlternativeName:"
						+ result.getSignerAlternativeNameBySigName(name));

				// 签章创建时间，如果时间戳有效，则同时覆盖此处时间
				System.out.println(
						"SignDate:" + result.getSignDateBySigName(name));

				// 本签章的签字信息段，可以取出到一号签平台验证
				System.out.println("Encode Signature Contents:" + new String(
						result.getEncodeSignatureContentsBySigName(name)));

				// 本签章的签章类型，certification为认证签章，approval为认可签章
				System.out.println("Signature type:"
						+ new String(result.getSignatureTypeBySigName(name)));
				// 本签章是否允许表单填写
				System.out.println(
						"Filling out fields allowed:" + String.valueOf(result
								.getFillingOutFieldsAllowedBySigName(name)));
				// 本签章是否允许添加备注
				System.out.println(
						"Adding annotations allowed:" + String.valueOf(result
								.getAddingAnnotationsAllowedBySigName(name)));
				// 本签章的生效范围锁定字段
				System.out.println("Locked Field Names:" + new String(
						result.getLockedFieldNamesBySigName(name)));

				// 导出本次签章的分支PDF文件
				// getFile(result.getRevisionBySigName(name),"d:/","Revision_"+result.getRevisionNumBySigName(name)+"-"+name+".pdf");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

	}

}
