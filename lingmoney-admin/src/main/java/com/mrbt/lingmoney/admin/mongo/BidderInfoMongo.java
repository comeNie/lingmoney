package com.mrbt.lingmoney.admin.mongo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author syb
 * @date 2017年7月5日 下午3:03:35
 * @version 1.0
 * @description 流标/放款银行返回交易记录
 **/
@Document(collection = "bidder_info")
public class BidderInfoMongo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 266141002413801874L;

	private int type; // 类型 1 流标结果、2 放款结果
	private String req_seq_no; // 投标交易流水号 C(28) 否 原投标流水号
	private String loan_no; // 借款编号 C(64) 是 借款编号
	private String ac_no; // 投资人账号 N(32) 是
	private String ac_name; // 投资人账号户名 C(128) 是
	private Double amount; // 投标金额 M 是
	private String status; // 状态 C(2) 是 L 待处理 R 正在处理 N 未明 F失败 S成功
	private String err_msg; // 错误原因 C(128) 是 STATUS=F返回。
	private String ext_filed3; // 备用字段3 C(300) 是 备用字段3
	private Date save_date; // 保存日期

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getReq_seq_no() {
		return req_seq_no;
	}

	public void setReq_seq_no(String req_seq_no) {
		this.req_seq_no = req_seq_no;
	}

	public String getLoan_no() {
		return loan_no;
	}

	public void setLoan_no(String loan_no) {
		this.loan_no = loan_no;
	}

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public String getAc_name() {
		return ac_name;
	}

	public void setAc_name(String ac_name) {
		this.ac_name = ac_name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public String getExt_filed3() {
		return ext_filed3;
	}

	public void setExt_filed3(String ext_filed3) {
		this.ext_filed3 = ext_filed3;
	}

	public Date getSave_date() {
		return save_date;
	}

	public void setSave_date(Date save_date) {
		this.save_date = save_date;
	}

}
