package com.mrbt.lingmoney.web.vo.users;

import java.util.Date;

/**
 *@author syb
 *@date 2017年5月8日 下午6:13:52
 *@version 1.0
 *@description 用户问答页展示数据
 **/
public class UsersAskVo implements java.io.Serializable {
	private static final long serialVersionUID = -1174327562776727462L;
	/**
	 * 问题ID
	 */
	private int id;  	
	/**
	 * 标题
	 */
	private String title;    	
	/**
	 * 昵称
	 */
	private String nickName; 	
	/**
	 * 等级名称
	 */
	private String degreeName;  
	/**
	 * 提问内容
	 */
	private String content;  	
	/**
	 * 提问关键字
	 */
	private String keyWord; 	
	
	private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
