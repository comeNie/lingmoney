package com.mrbt.lingmoney.web.vo.view;

import java.io.Serializable;

/**
 * @author syb
 * @date 2017年7月5日 上午10:23:52
 * @version 1.0
 * @description reuslt.jsp页面参数
 **/
public class ResultPageInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 处理的结果类型：1： 开户结果 2： 激活结果 3:充值结果 4：提现结果 5：投标结果
	 */
	private int type;
	/**
	 * 0 失败 1成功 2处理中
	 */
	private int status;

	/**
	 * 返回结果信息
	 */
	private String resultMsg;

	/**
	 * 失败原因
	 */
	private String failReason;

	/**
	 * 自动返回地址url
	 */
	private String autoReturnUrl;

	/**
	 * 自动返回地址名
	 */
	private String autoReturnName;

	/**
	 * 按钮1 url
	 */
	private String buttonOneUrl;
	/**
	 * 按钮1 名称
	 */
	private String buttonOneName;

	/**
	 * 按钮2 url
	 */
	private String buttonTwoUrl;
	/**
	 * 按钮2 名称
	 */
	private String buttonTwoName;

	/**
	 * 备用字段
	 */
	private Object backup;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getAutoReturnUrl() {
		return autoReturnUrl;
	}

	public void setAutoReturnUrl(String autoReturnUrl) {
		this.autoReturnUrl = autoReturnUrl;
	}

	public String getAutoReturnName() {
		return autoReturnName;
	}

	public void setAutoReturnName(String autoReturnName) {
		this.autoReturnName = autoReturnName;
	}

	public String getButtonOneUrl() {
		return buttonOneUrl;
	}

	public void setButtonOneUrl(String buttonOneUrl) {
		this.buttonOneUrl = buttonOneUrl;
	}

	public String getButtonOneName() {
		return buttonOneName;
	}

	public void setButtonOneName(String buttonOneName) {
		this.buttonOneName = buttonOneName;
	}

	public String getButtonTwoUrl() {
		return buttonTwoUrl;
	}

	public void setButtonTwoUrl(String buttonTwoUrl) {
		this.buttonTwoUrl = buttonTwoUrl;
	}

	public String getButtonTwoName() {
		return buttonTwoName;
	}

	public void setButtonTwoName(String buttonTwoName) {
		this.buttonTwoName = buttonTwoName;
	}

	public Object getBackup() {
		return backup;
	}

	public void setBackup(Object backup) {
		this.backup = backup;
	}

}
