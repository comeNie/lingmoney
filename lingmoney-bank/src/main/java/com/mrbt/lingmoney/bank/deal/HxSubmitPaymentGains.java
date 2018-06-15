package com.mrbt.lingmoney.bank.deal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.mrbt.lingmoney.bank.HxBaseData;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentReqDto;
import com.mrbt.lingmoney.bank.deal.dto.HxQuerySubmitPaymentResDto;
import com.mrbt.lingmoney.bank.deal.dto.HxSubmitPaymentGainsReqDto;
import com.mrbt.lingmoney.bank.deal.dto.RepayListResDto;

/**
 * 华兴E账户-还款收益明细提交
 * 
 * @author YJQ
 *
 */
@Component
public class HxSubmitPaymentGains extends HxBaseData {

	private static final Logger logger = LogManager.getLogger(HxSubmitPaymentGains.class);

	private String TRANSCODE = "OGW00074";
	private String TRANSCODE_QUERY = "OGW00075";

	public static void main(String[] args) {
		// 发起请求
		try {
			HxSubmitPaymentGainsReqDto paymentDto = new HxSubmitPaymentGainsReqDto();
			paymentDto.setAPPID("PC");

			new HxSubmitPaymentGains().requestPayment(paymentDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 请求还款收益明细提交
	 * 
	 * @author YJQ 2017年6月7日
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> requestPayment(HxSubmitPaymentGainsReqDto paymentDto) throws Exception {

		String logGroup = "\nHxSubmitPaymentGains_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "请求还款收益明细提交");

		// 请求
		List<Object> response = request(paymentDto, TRANSCODE, logGroup, true);
		Document resDoc = (Document) response.get(1);
		Map<String, Object> res = xmlToMap(resDoc);
		res.put("channelFlow", response.get(0));
		return res;
	}

	/**
	 * 查询还款收益明细提交结果
	 * 
	 * @author YJQ 2017年6月7日
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HxQuerySubmitPaymentResDto requestQueryResult(HxQuerySubmitPaymentReqDto dto) throws Exception {
		String logGroup = "\nHxQuerySubmitPaymentGainsResult_" + System.currentTimeMillis() + "_";
		logger.info(logGroup + "查询还款收益明细提交结果");
		// 请求
		Document res = request(dto, TRANSCODE_QUERY, logGroup);

		logger.info("{}返回报文：\n{}", logGroup, res.asXML());
		return docToMap(res);
	}
	
	private HxQuerySubmitPaymentResDto docToMap(Document paraDoc) throws Exception{
		HxQuerySubmitPaymentResDto res=new HxQuerySubmitPaymentResDto();
		res.setBWACNAME(paraDoc.selectSingleNode("//BWACNAME").getText());
		res.setBWACNO(paraDoc.selectSingleNode("//BWACNO").getText());
		res.setDFFLAG(paraDoc.selectSingleNode("//DFFLAG").getText());
		res.setERRORMSG(paraDoc.selectSingleNode("//ERRORMSG").getText());
		res.setLOANNO(paraDoc.selectSingleNode("//LOANNO").getText());
		res.setOLDREQSEQNO(paraDoc.selectSingleNode("//OLDREQSEQNO").getText());
		res.setRETURN_STATUS(paraDoc.selectSingleNode("//RETURN_STATUS").getText());
		res.setTOTALNUM(paraDoc.selectSingleNode("//TOTALNUM").getText());
		
		List<?> reList = paraDoc.selectNodes("//RSLIST");
		List<RepayListResDto> resDtoLi=new ArrayList<>();
		for (Object object : reList) {
			Map<String,Object> resMap=new HashMap<>();
			Element e = (Element) object;
			Iterator<?> iterator = e.nodeIterator();
			while (iterator.hasNext()) {
				Element next = (Element) iterator.next();
				String name = next.getName();
				String text = next.getText();
				resMap.put(name, text);
			}
			
			//map 转 bean
			RepayListResDto resDto=(RepayListResDto)mapToBean(resMap, RepayListResDto.class);
			resDtoLi.add(resDto);
		}
		res.setRSLIST(resDtoLi);
		return res;
	}
	
	// #start utils
	
	private  Object mapToBean(Map<String, Object> map, Class<?> clazz) throws Exception {
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
	
	// #end utils
}
