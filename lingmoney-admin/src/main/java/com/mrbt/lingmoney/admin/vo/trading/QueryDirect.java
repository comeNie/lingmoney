package com.mrbt.lingmoney.admin.vo.trading;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 
 * @author Administrator
 *
 */
@XStreamAlias("response")
public class QueryDirect extends XmlBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XStreamAsAttribute
	@XStreamAlias("platformNo")
	private String platformNo;

	private String code;

	private String description;

	@XStreamAlias("records")
	private List<Records> records;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Records> getRecords() {
		return records;
	}

	public void setRecords(List<Records> records) {
		this.records = records;
	}

}
