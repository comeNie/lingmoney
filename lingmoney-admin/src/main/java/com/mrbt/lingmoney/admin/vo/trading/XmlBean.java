package com.mrbt.lingmoney.admin.vo.trading;

import java.io.StringWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author Administrator
 *
 */
public class XmlBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3928368015415677089L;
	protected static XStream xstream;

	static {
		xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.ignoreUnknownElements();
	}

	/**
	 * 
	 * @return xml
	 */
	public String toXml() {
		StringWriter writer = new StringWriter();
		writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		xstream.toXML(this, writer);
		return writer.toString().replaceAll("[\\r\\n]", "");
	}

	/**
	 * 
	 * @param xml xml
	 * @return	xml
	 */
	public Object fromXML(String xml) {
		xstream.alias("response", this.getClass());
		return xstream.fromXML(xml);
	}

}
