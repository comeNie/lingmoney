package demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoysign.sdk.pdf.PdfReader;
import com.enjoysign.sdk.pdf.exception.SDKException;
import com.enjoysign.sdk.pdf.security.MakeSignature;

/**
 * 添加预制签名位，签章区显示一条签名，在pdf表层有显示的签名区域，用户点击可以进行签名。
 *
 */
public class MakeSignField {

	private static final Logger log = LoggerFactory
			.getLogger(MakeSignField.class);

	public static void main(String[] args) {

		try {

			// 载入待签署的pdf文件，如果SDK版本包含文档转换，此处可以传入word，excel，ppt，txt，jpg，png，pdf格式的文件，
			// 如果需要对文档进行上限限制，可以调用 new PdfReader(filename,int limit);方法，
			// limit 在传入word,ppt,txt时为最大页数，超出即报错，limit 在传入excel时，为总单元格数，超出即报错。
			PdfReader pdfReader = new PdfReader("sample.pdf");

			// 签章的序列号，最长18位，该序列号在同一PDF中必须唯一，建议与数据库保持映射关系，以便日后可查该签章相关信息
			String serialNo = "demo:" + System.currentTimeMillis();

			// 添加预制签名位，传入pdf实体，序列号，页码，横纵坐标，宽高，此处均使用百分比单位
			byte[] signedPdfBytes = MakeSignature.addSignField(pdfReader,
					serialNo, 1, 0.6f, 0.128f, 0.1f, 0.03f);

			PdfReader.savePdfFile(signedPdfBytes, "d:/signfield.pdf");
			log.info("签署完毕，文档已保存到" + "d:/" + "signfield.pdf");
		} catch (SDKException e) {
			log.error("errorCode:{},errorMessage:{}", e.getErrorCode(),
					e.getMessage());
			e.printStackTrace();
		}

		catch (Exception e) {
			log.error("error:{}", e.getMessage());
			e.printStackTrace();
		}

	}

}
