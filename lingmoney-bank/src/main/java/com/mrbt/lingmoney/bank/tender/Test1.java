package com.mrbt.lingmoney.bank.tender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;

public class Test1 {

	HxTender hx = new HxTender();
	
	/*
	 * <parent>
			<RSLIST>
				<SUBSEQNO>haha</SUBSEQNO>
				<OLDREQSEQNO>haha</OLDREQSEQNO>
				<ACNO>haha</ACNO>
				<ACNAME>haha</ACNAME>
				<AMOUNT>haha</AMOUNT>
				<REMARK>haha</REMARK>
				<STATUS>haha</STATUS>
				<ERRORMSG>haha</ERRORMSG>
			</RSLIST>
			<RSLIST>
				<SUBSEQNO>haha</SUBSEQNO>
				<OLDREQSEQNO>haha</OLDREQSEQNO>
				<ACNO>haha</ACNO>
				<ACNAME>haha</ACNAME>
				<AMOUNT>haha</AMOUNT>
				<REMARK>haha</REMARK>
				<STATUS>haha</STATUS>
				<ERRORMSG>haha</ERRORMSG>
			</RSLIST>
		</parent>
	 */
	@Test
	public void testName2() throws Exception {
		
	}
	
	
	@Test
	public void testName1() throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("parent");
		Element RSLIST1 = root.addElement("RSLIST");
		RSLIST1.addElement("SUBSEQNO").addText("SUBSEQNO1");
		RSLIST1.addElement("OLDREQSEQNO").addText("OLDREQSEQNO1");
		RSLIST1.addElement("ACNO").addText("ACNO1");
		RSLIST1.addElement("ACNAME").addText("ACNAME1");
		RSLIST1.addElement("AMOUNT").addText("AMOUNT1");
		RSLIST1.addElement("REMARK").addText("REMARK1");
		RSLIST1.addElement("STATUS").addText("STATUS1");
		RSLIST1.addElement("ERRORMSG").addText("ERRORMSG1");
		
		Element RSLIST2 = root.addElement("RSLIST");
		RSLIST2.addElement("SUBSEQNO").addText("SUBSEQNO2");
		RSLIST2.addElement("OLDREQSEQNO").addText("OLDREQSEQNO2");
		RSLIST2.addElement("ACNO").addText("ACNO2");
		RSLIST2.addElement("ACNAME").addText("ACNAME2");
		RSLIST2.addElement("AMOUNT").addText("AMOUNT2");
		RSLIST2.addElement("REMARK").addText("REMARK2");
		RSLIST2.addElement("STATUS").addText("STATUS2");
		RSLIST2.addElement("ERRORMSG").addText("ERRORMSG2");
		System.out.println(document.asXML());
		
		List nodes = document.selectNodes("//RSLIST");
		JSONArray array = new JSONArray();
		Map<String, String> map = null;
		for (Object object : nodes) {
			Element e = (Element) object;
			Iterator iterator = e.nodeIterator();
			map = new HashMap<>();
			while (iterator.hasNext()) {
				Element next = (Element) iterator.next();
				String name = next.getName();
				String text = next.getText();
				map.put(name, text);
			}
			array.add(map);
		}
		System.out.println(array.toJSONString());
	}
	
	@Test
	public void testName() throws Exception {
		String SUBSEQNO = "";
		String OLDREQSEQNO = "13133";
		Document document = hx.tenderReturnSearch("APP", OLDREQSEQNO, SUBSEQNO);
		System.out.println(document.asXML());
		
	}
}
