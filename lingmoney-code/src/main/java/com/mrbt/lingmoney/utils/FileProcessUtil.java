package com.mrbt.lingmoney.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 文件处理类
 * 
 * @author YJQ
 *
 */
public class FileProcessUtil {

	/**
	 * 根据模板生成文件
	 * 
	 * @author YJQ 2017年4月17日
	 * @param dataMap
	 * @param fileName
	 * @param fileType
	 * @param templetFile
	 * @return
	 * @throws Exception
	 */
	public static String createFile(Map<String, Object> dataMap, String fileName, String fileType, byte[] templetFile)
			throws Exception {
		// 文件保存路径
		String pathPro = PropertiesUtil.getPropertiesByKey("orderFilePath");
		
		// 生成的文件名
		String outPutFileName = pathPro + fileName + "." + fileType;

		// 配置模板
		Configuration configuration = new Configuration();
		StringTemplateLoader stringTemplate = new StringTemplateLoader();
		String templateStr = new String(templetFile);
		stringTemplate.putTemplate("template", templateStr);
		configuration.setTemplateLoader(stringTemplate);

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding("UTF-8");

		Template template = null;
		template = configuration.getTemplate("template", "UTF-8");
		template.setEncoding("UTF-8");
		if ("pdf".equals(fileType)) {
			// 按照html生成
			outPutFileName = pathPro + fileName + ".html";
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPutFileName), "UTF-8"));

			template.process(dataMap, writer);
			writer.close();
			// 转换成pdf
			// outPutFileName = FileProcessUtil.createPDF(outPutFileName,
			// pathPro + fileName + ".pdf");

		} else {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPutFileName), "UTF-8"));

			template.process(dataMap, writer);
			writer.close();
		}

		return outPutFileName;
	}

	/**
	 * 根据模板生成字符串
	 * 
	 * @param dataMap
	 * @param fileName
	 * @param fileType
	 * @param templetFile
	 * @return 文件内容
	 * @throws Exception
	 */
	public static String createFileReturnContent(Map<String, Object> dataMap, byte[] templetFile) throws Exception {

		// 配置模板
		Configuration configuration = new Configuration();
		StringTemplateLoader stringTemplate = new StringTemplateLoader();

		String templateStr = new String(templetFile);
		stringTemplate.putTemplate("template", templateStr);
		configuration.setTemplateLoader(stringTemplate);

		configuration.setObjectWrapper(new DefaultObjectWrapper());
		configuration.setDefaultEncoding("UTF-8");

		Template template = null;
		template = configuration.getTemplate("template", "UTF-8");
		template.setEncoding("UTF-8");

		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		template.process(dataMap, writer);
		String res = stringWriter.toString();
		writer.flush();
		writer.close();
		return res;
	}

	public static void main(String[] args) {

		try {
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
