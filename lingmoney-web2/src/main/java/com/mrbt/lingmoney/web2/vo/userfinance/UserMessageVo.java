package com.mrbt.lingmoney.web2.vo.userfinance;

import java.io.Serializable;

/**
 * @author wzy
 * @Date 2017年12月22日
 */
public class UserMessageVo implements Serializable {
	private static final long serialVersionUID = -3128263578148742040L;
	
	private Integer id;
	private String topic;
	private String sender;
	private String time;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
