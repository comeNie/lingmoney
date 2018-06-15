package com.mrbt.lingmoney.utils;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.mrbt.lingmoney.model.pdfProperties.TemplateBO;
import com.mrbt.lingmoney.pdf.kit.component.PDFKit;

public class PdfUtils {
	
	private static String templatePath = PropertiesUtil.getPropertiesByKey("PDF_OUTPUT_PATH");//pdf输出位置
	
	public static String createPDF(TemplateBO data, String fileName) {
		// pdf保存路径
		try {
			// 设置自定义PDF页眉页脚工具类
//			PDFHeaderFooter headerFooter = new PDFHeaderFooter();
			PDFKit kit = new PDFKit();
//			kit.setHeaderFooterBuilder(headerFooter);
			// 设置输出路径
			
			kit.setSaveFilePath(templatePath + fileName);
			String saveFilePath = kit.exportToFile(fileName, data);
			return saveFilePath;
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			// log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
			return null;
		}

	}

}
