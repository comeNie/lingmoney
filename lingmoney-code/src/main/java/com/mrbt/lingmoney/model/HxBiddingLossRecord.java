package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.util.Date;

public class HxBiddingLossRecord implements Serializable {
    /**
     * 32位uuid
     * 表字段 : hx_bidding_loss_record.id
     */
    private String id;

    /**
     * 借款编号
     * 表字段 : hx_bidding_loss_record.loan_no
     */
    private String loanNo;

    /**
     * 应用标识 PC APP WX
     * 表字段 : hx_bidding_loss_record.app_id
     */
    private String appId;

    /**
     * 渠道流水号
     * 表字段 : hx_bidding_loss_record.channel_flow
     */
    private String channelFlow;

    /**
     * 银行流水号
     * 表字段 : hx_bidding_loss_record.bank_flow
     */
    private String bankFlow;

    /**
     * 类型 0 申请流标  1 银行主动流标
     * 表字段 : hx_bidding_loss_record.type
     */
    private Integer type;

    /**
     * 状态 0 处理中 1成功 2失败
     * 表字段 : hx_bidding_loss_record.state
     */
    private Integer state;

    /**
     * 流标原因
     * 表字段 : hx_bidding_loss_record.cancel_reason
     */
    private String cancelReason;

    /**
     * 失败原因，仅流标失败才返回
     * 表字段 : hx_bidding_loss_record.error_msg
     */
    private String errorMsg;

    /**
     * 申请时间
     * 表字段 : hx_bidding_loss_record.apply_time
     */
    private Date applyTime;

    /**
     * 获取结果时间
     * 表字段 : hx_bidding_loss_record.response_time
     */
    private Date responseTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hx_bidding_loss_record
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取 32位uuid 字段:hx_bidding_loss_record.id
     *
     * @return hx_bidding_loss_record.id, 32位uuid
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 32位uuid 字段:hx_bidding_loss_record.id
     *
     * @param id the value for hx_bidding_loss_record.id, 32位uuid
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取 借款编号 字段:hx_bidding_loss_record.loan_no
     *
     * @return hx_bidding_loss_record.loan_no, 借款编号
     */
    public String getLoanNo() {
        return loanNo;
    }

    /**
     * 设置 借款编号 字段:hx_bidding_loss_record.loan_no
     *
     * @param loanNo the value for hx_bidding_loss_record.loan_no, 借款编号
     */
    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    /**
     * 获取 应用标识 PC APP WX 字段:hx_bidding_loss_record.app_id
     *
     * @return hx_bidding_loss_record.app_id, 应用标识 PC APP WX
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置 应用标识 PC APP WX 字段:hx_bidding_loss_record.app_id
     *
     * @param appId the value for hx_bidding_loss_record.app_id, 应用标识 PC APP WX
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 获取 渠道流水号 字段:hx_bidding_loss_record.channel_flow
     *
     * @return hx_bidding_loss_record.channel_flow, 渠道流水号
     */
    public String getChannelFlow() {
        return channelFlow;
    }

    /**
     * 设置 渠道流水号 字段:hx_bidding_loss_record.channel_flow
     *
     * @param channelFlow the value for hx_bidding_loss_record.channel_flow, 渠道流水号
     */
    public void setChannelFlow(String channelFlow) {
        this.channelFlow = channelFlow == null ? null : channelFlow.trim();
    }

    /**
     * 获取 银行流水号 字段:hx_bidding_loss_record.bank_flow
     *
     * @return hx_bidding_loss_record.bank_flow, 银行流水号
     */
    public String getBankFlow() {
        return bankFlow;
    }

    /**
     * 设置 银行流水号 字段:hx_bidding_loss_record.bank_flow
     *
     * @param bankFlow the value for hx_bidding_loss_record.bank_flow, 银行流水号
     */
    public void setBankFlow(String bankFlow) {
        this.bankFlow = bankFlow == null ? null : bankFlow.trim();
    }

    /**
     * 获取 类型 0 申请流标  1 银行主动流标 字段:hx_bidding_loss_record.type
     *
     * @return hx_bidding_loss_record.type, 类型 0 申请流标  1 银行主动流标
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置 类型 0 申请流标  1 银行主动流标 字段:hx_bidding_loss_record.type
     *
     * @param type the value for hx_bidding_loss_record.type, 类型 0 申请流标  1 银行主动流标
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 状态 0 处理中 1成功 2失败 字段:hx_bidding_loss_record.state
     *
     * @return hx_bidding_loss_record.state, 状态 0 处理中 1成功 2失败
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置 状态 0 处理中 1成功 2失败 字段:hx_bidding_loss_record.state
     *
     * @param state the value for hx_bidding_loss_record.state, 状态 0 处理中 1成功 2失败
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取 流标原因 字段:hx_bidding_loss_record.cancel_reason
     *
     * @return hx_bidding_loss_record.cancel_reason, 流标原因
     */
    public String getCancelReason() {
        return cancelReason;
    }

    /**
     * 设置 流标原因 字段:hx_bidding_loss_record.cancel_reason
     *
     * @param cancelReason the value for hx_bidding_loss_record.cancel_reason, 流标原因
     */
    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    /**
     * 获取 失败原因，仅流标失败才返回 字段:hx_bidding_loss_record.error_msg
     *
     * @return hx_bidding_loss_record.error_msg, 失败原因，仅流标失败才返回
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置 失败原因，仅流标失败才返回 字段:hx_bidding_loss_record.error_msg
     *
     * @param errorMsg the value for hx_bidding_loss_record.error_msg, 失败原因，仅流标失败才返回
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    /**
     * 获取 申请时间 字段:hx_bidding_loss_record.apply_time
     *
     * @return hx_bidding_loss_record.apply_time, 申请时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 设置 申请时间 字段:hx_bidding_loss_record.apply_time
     *
     * @param applyTime the value for hx_bidding_loss_record.apply_time, 申请时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 获取 获取结果时间 字段:hx_bidding_loss_record.response_time
     *
     * @return hx_bidding_loss_record.response_time, 获取结果时间
     */
    public Date getResponseTime() {
        return responseTime;
    }

    /**
     * 设置 获取结果时间 字段:hx_bidding_loss_record.response_time
     *
     * @param responseTime the value for hx_bidding_loss_record.response_time, 获取结果时间
     */
    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * ,hx_bidding_loss_record
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loanNo=").append(loanNo);
        sb.append(", appId=").append(appId);
        sb.append(", channelFlow=").append(channelFlow);
        sb.append(", bankFlow=").append(bankFlow);
        sb.append(", type=").append(type);
        sb.append(", state=").append(state);
        sb.append(", cancelReason=").append(cancelReason);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", applyTime=").append(applyTime);
        sb.append(", responseTime=").append(responseTime);
        sb.append("]");
        return sb.toString();
    }
}