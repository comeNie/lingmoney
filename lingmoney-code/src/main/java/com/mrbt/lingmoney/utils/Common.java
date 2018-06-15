package com.mrbt.lingmoney.utils;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.mrbt.lingmoney.model.Trading;
import com.mrbt.lingmoney.model.schedule.SellTrading;

/**
 * 公用工具类
 * 
 * @author yjq
 *
 */
public class Common {
	
	private static final Random RANDOM = new Random();
	
	/**
	 * 读取配置文件中指定的key
	 * 
	 * @param fileName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getPropertyVal(String fileName, String key){
		try {
			Common c = new Common();
			String classPath = c.getPath();
			Properties pps = new Properties();
			pps.load(new FileInputStream(classPath + fileName));
			String strValue = pps.getProperty(key);
			return strValue;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取路径
	 * 
	 * @return
	 */
	public String getPath() {
		return this.getClass().getResource("/").getPath();
	}

	
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static boolean isNotBlank(Map<?, ?> map) {
		if (map != null && map.entrySet().size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(Collection<?> collection) {
		if (collection != null && collection.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取一个类(包括父类) 所有的属性的值
	 * 
	 * @param obj
	 * @return {title=welcome!, description=欢迎来到你的保！,
	 *         url=http://test.lingmoney.cn/}
	 * @throws Exception
	 * @author yjq
	 */
	public static <T> Map<String, Object> getField(T obj) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		//
		Class<?> clazz = obj.getClass();
		Field[] field = clazz.getDeclaredFields();
		List<Field> fieldList = new ArrayList<Field>(Arrays.asList(field));
		if (!clazz.getSuperclass().getName().equals("java.lang.Object")) {
			// 获取父类
			Class<?> fatherClazz = clazz.getSuperclass();
			Field[] fatherField = fatherClazz.getDeclaredFields();
			List<Field> fFieldList = Arrays.asList(fatherField);
			// 有父类
			fieldList.addAll(fFieldList);
		}

		// 遍历
		for (int i = 0; i < fieldList.size(); i++) {
			String fieldName = fieldList.get(i).getName();
			String newName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			String fieldMethod = "get" + newName;
			Method m = clazz.getMethod(fieldMethod);
			Object value = m.invoke(obj);
			// resMap.put(fieldName, value)
			if (value != null) {
				if (!value.getClass().getName().equals("java.lang.String")) {
					Map<String, Object> sonResMap = new HashMap<String, Object>();
					sonResMap = Common.getField(value);
					resMap.put(fieldName, sonResMap);
				} else {
					resMap.put(fieldName, value);
				}
			}

		}
		return resMap;
	}

	/**
	 * 随机生成 num位数字字符数组
	 * 
	 * @param num
	 * @return
	 */
	public static char[] generateRandomArray(int num) {
		String chars = "0123456789";
		char[] rands = new char[num];
		for (int i = 0; i < num; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	/**
	 * 获取字母和数字的随机串
	 * 
	 * @param num
	 *            位数
	 * @return
	 */
	public static String generateRandomCharArray(int num) {
		String chars = "abcdefghjkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
		String res = "";
		for (int i = 0; i < num; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 获取ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 实体类转换成map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 将list<a>中的相同字段的值复制到list<b> (b与a均为实体对象，且存在交集)
	 * 
	 * @author YJQ 2017年4月24日
	 * @param li
	 *            list<a>
	 * @param clazz
	 *            b对象的类
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> copyPropertyToList(List<?> li, Class<T> clazz) throws Exception {
		List<T> resLi = new ArrayList<>();
		for (Object obj : li) {
			T a = clazz.newInstance();
			BeanUtils.copyProperties(a, obj);
			resLi.add(a);
		}
		return resLi;
	}

	/**
	 * 接收报文
	 * 
	 * @author YJQ 2017年6月5日
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getXmlDocument(HttpServletRequest request) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String buffer = null;

		StringBuffer xml = new StringBuffer();

		while ((buffer = br.readLine()) != null) {
			xml.append(buffer);
		}
		return xml.toString();
	}

	/**
	 * 将Map对象通过反射机制转换成Bean对象
	 * 
	 * @param map
	 *            存放数据的map对象
	 * @param clazz
	 *            待转换的class
	 * @return 转换后的Bean对象
	 * @throws Exception
	 */
	public static Object mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
		Object obj = clazz.newInstance();
		if (map != null && map.size() > 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String propertyName = entry.getKey();
				Object value = entry.getValue();
				String setMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);

				Field field = getClassField(clazz, propertyName);
				if(field==null){
					continue;
				}
				Class<?> fieldTypeClass = field.getType();

				value = convertValType(value, fieldTypeClass);
				clazz.getMethod(setMethodName, field.getType()).invoke(obj, value);
			}
		}
		return obj;
	}

	/**
	 * 将Object类型的值，转换成bean对象属性里对应的类型值
	 * @param <T>
	 * 
	 * @param value
	 *            Object对象值
	 * @param fieldTypeClass
	 *            属性的类型
	 * @return 转换后的值
	 */
	private  static  <T>  T  convertValType(Object value, Class<T> fieldTypeClass) {

		@SuppressWarnings("unchecked")
		T t=(T)value;

		return t;
	}

	/**
	 * 获取指定字段名称查找在class中的对应的Field对象(包括查找父类)
	 * 
	 * @param clazz
	 *            指定的class
	 * @param fieldName
	 *            字段名称
	 * @return Field对象
	 */
	private static Field getClassField(Class<?> clazz, String fieldName) {
		if (Object.class.getName().equals(clazz.getName())) {
			return null;
		}
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {// 简单的递归一下
			return getClassField(superClass, fieldName);
		}
		return null;
	}
	
    /**
     * 创建卖出的交易码
     */
    public static void buildOutBizCode(SellTrading sellTrading) {
        if (sellTrading != null && sellTrading.getStatus() == AppCons.SELL_STATUS) {
            sellTrading.setOutBizCode(DateUtils.getDateStrByBiz(sellTrading.getSellDt()) + sellTrading.getpCode()
                    + sellTrading.getStatus() + sellTrading.getTid() + getFourRandom());
        } else {
            throw new IllegalArgumentException("创建卖出交易码错误");
        }
    }

    /**
     * 创建卖出的交易码
     */
    public static void buildOutBizCode(Trading trading, String logGroup) {
        if (trading != null && trading.getStatus() == AppCons.SELL_STATUS) {
            trading.setOutBizCode(DateUtils.getDateStrByBiz(trading.getSellDt()) + trading.getpCode()
                    + trading.getStatus() + trading.getId() + getFourRandom());
            System.out.println(logGroup + "创建产品卖出交易码成功.tid:" + trading.getId() + "; outBizCode:"
                    + trading.getOutBizCode());
        } else {
            System.out.println(logGroup + "创建产品卖出交易码失败.");
            throw new IllegalArgumentException("创建卖出交易码错误");
        }
    }

    /**
     * 产生4位随机数(0000-9999)
     * 
     * @return 4位随机数
     */
    private static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }

	public static void main(String[] args) {
		/*List<String> li=new ArrayList<>();
		li.add("lili");
		Map<String,Object> map=new HashMap<>();
		map.put("string", "string");
		map.put("list", li);
		try {
			mapToBean(map,TestBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 获取指定区间内的随机数
	 * @param sectionNum
	 * @param reqNum
	 * @return
	 */
	public static int[] generatingRandomNumber(int sectionNum, int reqNum) {
		int[] rs = new int[10];// 存放返回结果
		int y = 0;//返回结果下标
		//存放已经生成的数
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		while (map.size() < reqNum) {
			int x = RANDOM.nextInt(sectionNum);
			if (!map.containsKey(x)) {
				map.put(x, "");
				rs[y] = x;
				y++;
			}
		}
		return rs;
	}
}
