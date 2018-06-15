package com.mrbt.lingmoney.model;

import java.io.Serializable;
import java.util.Date;

public class LingbaoLotteryType implements Serializable {
    /**
     * 主键
     * 表字段 : lingbao_lottery_type.id
     */
    private Integer id;

    /**
     * 名称
     * 表字段 : lingbao_lottery_type.name
     */
    private String name;

    /**
     * 兑换所需积分(领宝个数)
     * 表字段 : lingbao_lottery_type.integral
     */
    private Integer integral;

    /**
     * 活动开始时间
     * 表字段 : lingbao_lottery_type.start_time
     */
    private Date startTime;

    /**
     * 活动结束时间
     * 表字段 : lingbao_lottery_type.end_time
     */
    private Date endTime;

    /**
     * 活动类型。0抽奖，1限时抢。
     * 表字段 : lingbao_lottery_type.type
     */
    private Integer type;

    /**
     * 状态。0不可用，1可用。
     * 表字段 : lingbao_lottery_type.status
     */
    private Integer status;

    /**
     * 优先级，0开始,由高到低排序
     * 表字段 : lingbao_lottery_type.priority
     */
    private Integer priority;

    /**
     * 图片路径，宣传图片
     * 表字段 : lingbao_lottery_type.picture
     */
    private String picture;

    /**
     * 文字说明
     * 表字段 : lingbao_lottery_type.statement
     */
    private String statement;

    /**
     * 活动规则
     * 表字段 : lingbao_lottery_type.rule
     */
    private String rule;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table lingbao_lottery_type
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取 主键 字段:lingbao_lottery_type.id
     *
     * @return lingbao_lottery_type.id, 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 主键 字段:lingbao_lottery_type.id
     *
     * @param id the value for lingbao_lottery_type.id, 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 名称 字段:lingbao_lottery_type.name
     *
     * @return lingbao_lottery_type.name, 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 名称 字段:lingbao_lottery_type.name
     *
     * @param name the value for lingbao_lottery_type.name, 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取 兑换所需积分(领宝个数) 字段:lingbao_lottery_type.integral
     *
     * @return lingbao_lottery_type.integral, 兑换所需积分(领宝个数)
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置 兑换所需积分(领宝个数) 字段:lingbao_lottery_type.integral
     *
     * @param integral the value for lingbao_lottery_type.integral, 兑换所需积分(领宝个数)
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取 活动开始时间 字段:lingbao_lottery_type.start_time
     *
     * @return lingbao_lottery_type.start_time, 活动开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置 活动开始时间 字段:lingbao_lottery_type.start_time
     *
     * @param startTime the value for lingbao_lottery_type.start_time, 活动开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 活动结束时间 字段:lingbao_lottery_type.end_time
     *
     * @return lingbao_lottery_type.end_time, 活动结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 活动结束时间 字段:lingbao_lottery_type.end_time
     *
     * @param endTime the value for lingbao_lottery_type.end_time, 活动结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 活动类型。0抽奖，1限时抢。 字段:lingbao_lottery_type.type
     *
     * @return lingbao_lottery_type.type, 活动类型。0抽奖，1限时抢。
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置 活动类型。0抽奖，1限时抢。 字段:lingbao_lottery_type.type
     *
     * @param type the value for lingbao_lottery_type.type, 活动类型。0抽奖，1限时抢。
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 状态。0不可用，1可用。 字段:lingbao_lottery_type.status
     *
     * @return lingbao_lottery_type.status, 状态。0不可用，1可用。
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置 状态。0不可用，1可用。 字段:lingbao_lottery_type.status
     *
     * @param status the value for lingbao_lottery_type.status, 状态。0不可用，1可用。
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 优先级，0开始,由高到低排序 字段:lingbao_lottery_type.priority
     *
     * @return lingbao_lottery_type.priority, 优先级，0开始,由高到低排序
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置 优先级，0开始,由高到低排序 字段:lingbao_lottery_type.priority
     *
     * @param priority the value for lingbao_lottery_type.priority, 优先级，0开始,由高到低排序
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取 图片路径，宣传图片 字段:lingbao_lottery_type.picture
     *
     * @return lingbao_lottery_type.picture, 图片路径，宣传图片
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置 图片路径，宣传图片 字段:lingbao_lottery_type.picture
     *
     * @param picture the value for lingbao_lottery_type.picture, 图片路径，宣传图片
     */
    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    /**
     * 获取 文字说明 字段:lingbao_lottery_type.statement
     *
     * @return lingbao_lottery_type.statement, 文字说明
     */
    public String getStatement() {
        return statement;
    }

    /**
     * 设置 文字说明 字段:lingbao_lottery_type.statement
     *
     * @param statement the value for lingbao_lottery_type.statement, 文字说明
     */
    public void setStatement(String statement) {
        this.statement = statement == null ? null : statement.trim();
    }

    /**
     * 获取 活动规则 字段:lingbao_lottery_type.rule
     *
     * @return lingbao_lottery_type.rule, 活动规则
     */
    public String getRule() {
        return rule;
    }

    /**
     * 设置 活动规则 字段:lingbao_lottery_type.rule
     *
     * @param rule the value for lingbao_lottery_type.rule, 活动规则
     */
    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }

    /**
     * ,lingbao_lottery_type
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", integral=").append(integral);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", priority=").append(priority);
        sb.append(", picture=").append(picture);
        sb.append(", statement=").append(statement);
        sb.append(", rule=").append(rule);
        sb.append("]");
        return sb.toString();
    }
}