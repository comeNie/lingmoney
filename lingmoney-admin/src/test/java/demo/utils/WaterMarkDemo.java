package demo.utils;

import com.enjoysign.sdk.pdf.draw.ImageUtil;
import com.enjoysign.sdk.pdf.draw.WaterMarkUtil;

/**
 * 
 * PDF增加图片或文字水印DEMO 重要！！！ 对已签名PDF进行水印操作，会破坏签章，导致文档验签失败失去法律效应
 */
public class WaterMarkDemo {
	public static void main(String[] args) throws Exception {

		// 从文件路径加载文件到字节数组
		byte[] imageBytes = WaterMarkUtil.getBytes("stamp.png");

		// 透明化处理,如果需要将图片的白色背景变成透明，使用此工具函数
		imageBytes = ImageUtil.transferAlpha(imageBytes);

		// 从文件路径加载PDF文件到字节数组
		byte[] pdfBytes = WaterMarkUtil.getBytes("sample.pdf");

		// 转换后的结果pdf字节数组
		byte[] markedPdfBytes;

		// 将图片水印以覆盖方式打在pdf指定页上
		markedPdfBytes = WaterMarkUtil.addImageCoverOnPage(pdfBytes, 1,
				imageBytes, 0.25f, 0.3f, 0.3f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.1.pdf");
		// 将图片水印以覆盖方式打在pdf所有页上
		markedPdfBytes = WaterMarkUtil.addImageCoverOnAllPage(pdfBytes,
				imageBytes, 0.25f, 0.3f, 0.3f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.2.pdf");

		// 将图片水印以背景方式打在pdf指定页上
		markedPdfBytes = WaterMarkUtil.addImageBackgroundOnPage(pdfBytes, 1,
				imageBytes, 0.25f, 0.3f, 0.3f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.3.pdf");
		// 将图片水印以背景方式打在pdf所有页上
		markedPdfBytes = WaterMarkUtil.addImageBackgroundOnAllPage(pdfBytes,
				imageBytes, 0.25f, 0.3f, 0.3f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.4.pdf");

		// 将文字水印以覆盖方式打在pdf指定页上
		markedPdfBytes = WaterMarkUtil.addTextCoverOnPage(pdfBytes,
				"中文，中文，测试中文!!!", 1, 18, "#ff0000", -45, 0.3f, 0.1f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.5.pdf");
		// 将文字水印以覆盖方式打在pdf所有页上
		markedPdfBytes = WaterMarkUtil.addTextCoverOnAllPage(pdfBytes,
				"中文，中文，测试中文!!!", 18, "#ff0000", -45, 0.3f, 0.1f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.6.pdf");

		// 将文字水印以背景方式打在pdf指定页上
		markedPdfBytes = WaterMarkUtil.addTextBackgroundOnPage(pdfBytes,
				"中文，中文，测试中文!!!", 1, 18, "#ff0000", -45, 0.3f, 0.1f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.7.pdf");
		// 将文字水印以背景方式打在pdf所有页上
		markedPdfBytes = WaterMarkUtil.addTextBackgroundOnAllPage(pdfBytes,
				"中文，中文，测试中文!!!", 18, "#ff0000", -45, 0.3f, 0.1f);
		// 输出结果pdf字节数组到pdf文件
		ImageUtil.saveImgFile(markedPdfBytes,
				"d:/water_mark/waterMarked.8.pdf");

		System.out.println("处理完毕，文档已保存到 waterMarked.pdf");
	}

}
