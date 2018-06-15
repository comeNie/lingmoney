package com.mrbt.lingmoney.bank.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Properties;
import java.util.Set;

/**
 *  获取properties文件内容的工具类
 * @author ruochen.yu
 */
public class PropertiesUtil {
	
	//存放配置文件的所有的key-value
	public static Map<String,String> allParam = new HashMap<String, String>();
	
	//文件路径
	private static String CONFIG_NAME = PropertiesUtil.class.getResource("/").getPath() + "prop.properties";
	
	//根据文件名称-key，返回相应key的值
    public static String getPropertiesByKey2(String key){
		try {
			 if(allParam.containsKey(key)){
				return allParam.get(key);
			 }else{
				InputStream in = new FileInputStream(new File(CONFIG_NAME));
				// 解决中文乱码
				BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));
				Properties p = new Properties();
				p.load(bf);
				Set<Entry<Object, Object>> allKey = p.entrySet();
				for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(entry.getKey()),
							String.valueOf(entry.getValue()));
				}
				in.close();
				return allParam.get(key);
			 }
	   	} catch (Exception e) {
	   		e.printStackTrace();
	    }
		return null;
    }
    
  //根据文件名称-key，返回相应key的值
    public static String getPropertiesByKey(String key){
		try {
			 if(allParam.containsKey(key)){
				return allParam.get(key);
			 }else{
				 allParam.clear();
				 InputStream in = new FileInputStream(new File(getPropertiesByKey2("PUBLIC_PROP_FILE") + "RSAProp.properties"));
					// 解决中文乱码
				 BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));
		       	 Properties p = new Properties();
		       	 p.load(bf);
		       	 Set<Entry<Object, Object>> allKey = p.entrySet();
		       	 for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
				 }
		       	 in.close();
		       	 return allParam.get(key);
			 }
	   	} catch (Exception e) {
	   		e.printStackTrace();
	    }
		return null;
    }
    
    
	//初始化config配置文件
	public static void initProperties() {
		try {
			if(!allParam.isEmpty()) {
				allParam.clear();
			}
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
			// 解决中文乱码
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(in,"utf-8"));
			Properties p = new Properties();
			p.load(bf);
			Set<Entry<Object, Object>> allKey = p.entrySet();
			for (Entry<Object, Object> entry : allKey) {
				allParam.put(String.valueOf(entry.getKey()),
						String.valueOf(entry.getValue()));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getPropertiesByKey("LINGMONEY_PRIVATE"));
		System.out.println(getPropertiesByKey("LINGMONEY_PRIVATE"));
	}
}
