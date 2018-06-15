package com.mrbt.lingmoney.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileOpertion{
	/**
	 * 创建文件夹 
	 * @param folder	目录名称,多个用“,”号隔开  
	 */
	public static void createFolder(String folder){
		if(folder.indexOf(",") > 0){
			String folders[] = folder.split(",");
			for (int i = 0; i < folders.length; i++) {
				File f = new File(folders[i]);
				if(!f.exists()){
//					log.info(folders[i] + "不存在，创建文件夹");
					f.mkdirs();
				}else{
//					log.info(folders[i] + "已存在");
				}
			}
		}else{
			File f = new File(folder);
			if(!f.exists()){
//				log.info(folder + "不存在，创建文件夹");
				f.mkdirs();
			}else{
//				log.info(folder + "已存在");
			}
		}
	}
	
	/**
	 * 读取文件到LSIT列表中
	 * @param filename	文件名称
	 * @param filecode	文件编码
	 * @param field	需要写入LIST的列号，空：将整行写入的LIST，如果多列传入列号中间用“,”号隔开
	 * @param fenge 文件的字段分隔符,没有为空
	 * @return
	 */
	public static List<String> readFileToList(String filename, String filecode, String field, String fenge){
		List<String> list = new ArrayList<String>();
		File f = new File(filename);
		if(f.exists()){
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),filecode));
				while(br.ready()){
					String line = br.readLine();
					if(field.equals("")){
						list.add(line);
					}else{
						String fields[] = field.split(",");
						String data = "";
						for (int i = 0; i < fields.length; i++) {
							if(i == fields.length -1){
								data = data + line.split(fenge)[Integer.parseInt(fields[i])].replace("\"", "");
							}else{
								data = data + line.split(fenge)[Integer.parseInt(fields[i])].replace("\"", "") + ",";
							}
						}
						list.add(data);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			return null;
		}
		return list;
	}
	
	/**
	 * 将制定的文件读入到内存中
	 * 
	 * @param f1
	 *            文件对象
	 * @param fileCod
	 *            文件编码
	 * @return
	 */
	public static String readFile(File f1, String fileCod) {
		StringBuffer fileValue = new StringBuffer();
		try {
			FileInputStream fis = new FileInputStream(f1);
			InputStreamReader isr = new InputStreamReader(fis, fileCod);
			BufferedReader br = new BufferedReader(isr);
			while (br.ready()) {
				fileValue.append(br.readLine() + "\r\n");
			}
			br.close();
			isr.close();
			fis.close();
		} catch (Exception e) {
			System.out.println(e + "\t" + f1);
		}
		return fileValue.toString();
	}
	
	/**
	 * 将数据写入的文件
	 * 
	 * @param line
	 *            数据
	 * @param filecode
	 *            写入的编码
	 * @param filename
	 *            写入的文件目录
	 */
	public static void writerToFile(String line, String filecode, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			Writer out = new OutputStreamWriter(fos, filecode);
			out.write(line + "\r\n");
			out.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writerBatchToFile(String line, String filecode, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			Writer out = new OutputStreamWriter(fos, filecode);
			out.write(line);
			out.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 正则匹配数据，只能匹配在被匹配数据中，出现一次，并且只有一个值
	 * 
	 * @param regex
	 *            正则，只能有一个值
	 * @param html
	 *            被匹配的数据
	 * @return
	 */
	public static String patterns(String regex, String html) {
		String pValue = "";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(html);
		if (m.find()) {
			pValue = m.group(1);
		}
		return pValue;
	}
	
	public static void main(String[] args) {
//		new FileOpertion().createFolder("f:/temp/a/,f:/temp/b/,f:/temp/c/,f:/temp/d/");
//		new FileOpertion().createFolder("f:/temp/e/");
//		List<String> list = FileOpertion.readFileToList("D:/6-火车票/20130820/checiFull.csv","gb2312","0,1,2,3,4,5,8","\",\"");
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.toArray()[i].toString());
//		}
		Map<String, String> map = readFileToMap("F:/SouFun/Nid_url20130605.csv","gb2312",",", 0);
		System.out.println(map);
	}

	public static void deleteFile(File file) {
		file.delete();
	}

	public static Map<String, String> readFileToMap(String filename, String filecode, String string,int x) {
		Map<String, String> map = new HashMap<String, String>();
		File f = new File(filename);
		if(f.exists()){
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),filecode));
				while(br.ready()){
					String line = br.readLine();
					String key = line.split(string)[x];
					String value = line;
					map.put(key, value);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static Map<String, String> readFileToMap2(String filename, String filecode) {
		Map<String, String> map = new HashMap<String, String>();
		File f = new File(filename);
		if(f.exists()){
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),filecode));
				while(br.ready()){
					String line = br.readLine();
					String key = line;
					String value = line;
					map.put(key, value);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
