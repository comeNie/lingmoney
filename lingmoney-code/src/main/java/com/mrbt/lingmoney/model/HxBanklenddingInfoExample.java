package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HxBanklenddingInfoExample {
    /**
     * 排序字段,hx_banklendding_info
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,hx_banklendding_info
     */
    protected boolean distinct;

    /**
     * 查询条件实例,hx_banklendding_info
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,hx_banklendding_info
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,hx_banklendding_info
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,hx_banklendding_info
     */
    public HxBanklenddingInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,hx_banklendding_info
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,hx_banklendding_info
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,hx_banklendding_info
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,hx_banklendding_info
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,hx_banklendding_info
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,hx_banklendding_info
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,hx_banklendding_info
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,hx_banklendding_info
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,hx_banklendding_info
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,hx_banklendding_info
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,hx_banklendding_info
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,hx_banklendding_info
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,hx_banklendding_info
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,hx_banklendding_info
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：hx_banklendding_info
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andChannelFlowIsNull() {
            addCriterion("channel_flow is null");
            return (Criteria) this;
        }

        public Criteria andChannelFlowIsNotNull() {
            addCriterion("channel_flow is not null");
            return (Criteria) this;
        }

        public Criteria andChannelFlowEqualTo(String value) {
            addCriterion("channel_flow =", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowNotEqualTo(String value) {
            addCriterion("channel_flow <>", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowGreaterThan(String value) {
            addCriterion("channel_flow >", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowGreaterThanOrEqualTo(String value) {
            addCriterion("channel_flow >=", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowLessThan(String value) {
            addCriterion("channel_flow <", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowLessThanOrEqualTo(String value) {
            addCriterion("channel_flow <=", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowLike(String value) {
            addCriterion("channel_flow like", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowNotLike(String value) {
            addCriterion("channel_flow not like", value, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowIn(List<String> values) {
            addCriterion("channel_flow in", values, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowNotIn(List<String> values) {
            addCriterion("channel_flow not in", values, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowBetween(String value1, String value2) {
            addCriterion("channel_flow between", value1, value2, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andChannelFlowNotBetween(String value1, String value2) {
            addCriterion("channel_flow not between", value1, value2, "channelFlow");
            return (Criteria) this;
        }

        public Criteria andLoanNoIsNull() {
            addCriterion("loan_no is null");
            return (Criteria) this;
        }

        public Criteria andLoanNoIsNotNull() {
            addCriterion("loan_no is not null");
            return (Criteria) this;
        }

        public Criteria andLoanNoEqualTo(String value) {
            addCriterion("loan_no =", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoNotEqualTo(String value) {
            addCriterion("loan_no <>", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoGreaterThan(String value) {
            addCriterion("loan_no >", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoGreaterThanOrEqualTo(String value) {
            addCriterion("loan_no >=", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoLessThan(String value) {
            addCriterion("loan_no <", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoLessThanOrEqualTo(String value) {
            addCriterion("loan_no <=", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoLike(String value) {
            addCriterion("loan_no like", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoNotLike(String value) {
            addCriterion("loan_no not like", value, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoIn(List<String> values) {
            addCriterion("loan_no in", values, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoNotIn(List<String> values) {
            addCriterion("loan_no not in", values, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoBetween(String value1, String value2) {
            addCriterion("loan_no between", value1, value2, "loanNo");
            return (Criteria) this;
        }

        public Criteria andLoanNoNotBetween(String value1, String value2) {
            addCriterion("loan_no not between", value1, value2, "loanNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoIsNull() {
            addCriterion("res_jnl_no is null");
            return (Criteria) this;
        }

        public Criteria andResJnlNoIsNotNull() {
            addCriterion("res_jnl_no is not null");
            return (Criteria) this;
        }

        public Criteria andResJnlNoEqualTo(String value) {
            addCriterion("res_jnl_no =", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoNotEqualTo(String value) {
            addCriterion("res_jnl_no <>", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoGreaterThan(String value) {
            addCriterion("res_jnl_no >", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoGreaterThanOrEqualTo(String value) {
            addCriterion("res_jnl_no >=", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoLessThan(String value) {
            addCriterion("res_jnl_no <", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoLessThanOrEqualTo(String value) {
            addCriterion("res_jnl_no <=", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoLike(String value) {
            addCriterion("res_jnl_no like", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoNotLike(String value) {
            addCriterion("res_jnl_no not like", value, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoIn(List<String> values) {
            addCriterion("res_jnl_no in", values, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoNotIn(List<String> values) {
            addCriterion("res_jnl_no not in", values, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoBetween(String value1, String value2) {
            addCriterion("res_jnl_no between", value1, value2, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andResJnlNoNotBetween(String value1, String value2) {
            addCriterion("res_jnl_no not between", value1, value2, "resJnlNo");
            return (Criteria) this;
        }

        public Criteria andBwacNameIsNull() {
            addCriterion("bwac_name is null");
            return (Criteria) this;
        }

        public Criteria andBwacNameIsNotNull() {
            addCriterion("bwac_name is not null");
            return (Criteria) this;
        }

        public Criteria andBwacNameEqualTo(String value) {
            addCriterion("bwac_name =", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameNotEqualTo(String value) {
            addCriterion("bwac_name <>", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameGreaterThan(String value) {
            addCriterion("bwac_name >", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameGreaterThanOrEqualTo(String value) {
            addCriterion("bwac_name >=", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameLessThan(String value) {
            addCriterion("bwac_name <", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameLessThanOrEqualTo(String value) {
            addCriterion("bwac_name <=", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameLike(String value) {
            addCriterion("bwac_name like", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameNotLike(String value) {
            addCriterion("bwac_name not like", value, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameIn(List<String> values) {
            addCriterion("bwac_name in", values, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameNotIn(List<String> values) {
            addCriterion("bwac_name not in", values, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameBetween(String value1, String value2) {
            addCriterion("bwac_name between", value1, value2, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNameNotBetween(String value1, String value2) {
            addCriterion("bwac_name not between", value1, value2, "bwacName");
            return (Criteria) this;
        }

        public Criteria andBwacNoIsNull() {
            addCriterion("bwac_no is null");
            return (Criteria) this;
        }

        public Criteria andBwacNoIsNotNull() {
            addCriterion("bwac_no is not null");
            return (Criteria) this;
        }

        public Criteria andBwacNoEqualTo(String value) {
            addCriterion("bwac_no =", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoNotEqualTo(String value) {
            addCriterion("bwac_no <>", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoGreaterThan(String value) {
            addCriterion("bwac_no >", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoGreaterThanOrEqualTo(String value) {
            addCriterion("bwac_no >=", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoLessThan(String value) {
            addCriterion("bwac_no <", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoLessThanOrEqualTo(String value) {
            addCriterion("bwac_no <=", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoLike(String value) {
            addCriterion("bwac_no like", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoNotLike(String value) {
            addCriterion("bwac_no not like", value, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoIn(List<String> values) {
            addCriterion("bwac_no in", values, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoNotIn(List<String> values) {
            addCriterion("bwac_no not in", values, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoBetween(String value1, String value2) {
            addCriterion("bwac_no between", value1, value2, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andBwacNoNotBetween(String value1, String value2) {
            addCriterion("bwac_no not between", value1, value2, "bwacNo");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtIsNull() {
            addCriterion("ac_mng_amt is null");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtIsNotNull() {
            addCriterion("ac_mng_amt is not null");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtEqualTo(BigDecimal value) {
            addCriterion("ac_mng_amt =", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtNotEqualTo(BigDecimal value) {
            addCriterion("ac_mng_amt <>", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtGreaterThan(BigDecimal value) {
            addCriterion("ac_mng_amt >", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ac_mng_amt >=", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtLessThan(BigDecimal value) {
            addCriterion("ac_mng_amt <", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ac_mng_amt <=", value, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtIn(List<BigDecimal> values) {
            addCriterion("ac_mng_amt in", values, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtNotIn(List<BigDecimal> values) {
            addCriterion("ac_mng_amt not in", values, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ac_mng_amt between", value1, value2, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andAcMngAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ac_mng_amt not between", value1, value2, "acMngAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtIsNull() {
            addCriterion("guarant_amt is null");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtIsNotNull() {
            addCriterion("guarant_amt is not null");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtEqualTo(BigDecimal value) {
            addCriterion("guarant_amt =", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtNotEqualTo(BigDecimal value) {
            addCriterion("guarant_amt <>", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtGreaterThan(BigDecimal value) {
            addCriterion("guarant_amt >", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("guarant_amt >=", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtLessThan(BigDecimal value) {
            addCriterion("guarant_amt <", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("guarant_amt <=", value, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtIn(List<BigDecimal> values) {
            addCriterion("guarant_amt in", values, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtNotIn(List<BigDecimal> values) {
            addCriterion("guarant_amt not in", values, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarant_amt between", value1, value2, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andGuarantAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarant_amt not between", value1, value2, "guarantAmt");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNull() {
            addCriterion("error_msg is null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIsNotNull() {
            addCriterion("error_msg is not null");
            return (Criteria) this;
        }

        public Criteria andErrorMsgEqualTo(String value) {
            addCriterion("error_msg =", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotEqualTo(String value) {
            addCriterion("error_msg <>", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThan(String value) {
            addCriterion("error_msg >", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgGreaterThanOrEqualTo(String value) {
            addCriterion("error_msg >=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThan(String value) {
            addCriterion("error_msg <", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLessThanOrEqualTo(String value) {
            addCriterion("error_msg <=", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgLike(String value) {
            addCriterion("error_msg like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotLike(String value) {
            addCriterion("error_msg not like", value, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgIn(List<String> values) {
            addCriterion("error_msg in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotIn(List<String> values) {
            addCriterion("error_msg not in", values, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgBetween(String value1, String value2) {
            addCriterion("error_msg between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andErrorMsgNotBetween(String value1, String value2) {
            addCriterion("error_msg not between", value1, value2, "errorMsg");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNull() {
            addCriterion("apply_time is null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIsNotNull() {
            addCriterion("apply_time is not null");
            return (Criteria) this;
        }

        public Criteria andApplyTimeEqualTo(Date value) {
            addCriterion("apply_time =", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotEqualTo(Date value) {
            addCriterion("apply_time <>", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThan(Date value) {
            addCriterion("apply_time >", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("apply_time >=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThan(Date value) {
            addCriterion("apply_time <", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("apply_time <=", value, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeIn(List<Date> values) {
            addCriterion("apply_time in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotIn(List<Date> values) {
            addCriterion("apply_time not in", values, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeBetween(Date value1, Date value2) {
            addCriterion("apply_time between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andApplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("apply_time not between", value1, value2, "applyTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIsNull() {
            addCriterion("response_time is null");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIsNotNull() {
            addCriterion("response_time is not null");
            return (Criteria) this;
        }

        public Criteria andResponseTimeEqualTo(Date value) {
            addCriterion("response_time =", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotEqualTo(Date value) {
            addCriterion("response_time <>", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeGreaterThan(Date value) {
            addCriterion("response_time >", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("response_time >=", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeLessThan(Date value) {
            addCriterion("response_time <", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeLessThanOrEqualTo(Date value) {
            addCriterion("response_time <=", value, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeIn(List<Date> values) {
            addCriterion("response_time in", values, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotIn(List<Date> values) {
            addCriterion("response_time not in", values, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeBetween(Date value1, Date value2) {
            addCriterion("response_time between", value1, value2, "responseTime");
            return (Criteria) this;
        }

        public Criteria andResponseTimeNotBetween(Date value1, Date value2) {
            addCriterion("response_time not between", value1, value2, "responseTime");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：hx_banklendding_info
     *
     * @mbggenerated
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 类注释
     * Criterion
     * 数据库表：hx_banklendding_info
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}