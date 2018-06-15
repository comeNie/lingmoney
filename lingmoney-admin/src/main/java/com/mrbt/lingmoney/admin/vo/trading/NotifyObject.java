package com.mrbt.lingmoney.admin.vo.trading;

/**
 * 
 * @author Administrator
 *
 */
public class NotifyObject implements java.io.Serializable {
	private static final long serialVersionUID = 5130561559710957099L;
	private String type;
	private Integer tid;
	private Integer infoId;
	private String uid;
	private Object object;
	private String requestNo;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
