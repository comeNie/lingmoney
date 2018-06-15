package com.mrbt.lingmoney.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HxBorrowerInfoExample {
    /**
     * 排序字段,hx_borrower_info
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,hx_borrower_info
     */
    protected boolean distinct;

    /**
     * 查询条件实例,hx_borrower_info
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,hx_borrower_info
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,hx_borrower_info
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,hx_borrower_info
     */
    public HxBorrowerInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,hx_borrower_info
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,hx_borrower_info
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,hx_borrower_info
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,hx_borrower_info
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,hx_borrower_info
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,hx_borrower_info
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,hx_borrower_info
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,hx_borrower_info
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,hx_borrower_info
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,hx_borrower_info
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,hx_borrower_info
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,hx_borrower_info
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,hx_borrower_info
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,hx_borrower_info
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：hx_borrower_info
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

        public Criteria andBiddingIdIsNull() {
            addCriterion("bidding_id is null");
            return (Criteria) this;
        }

        public Criteria andBiddingIdIsNotNull() {
            addCriterion("bidding_id is not null");
            return (Criteria) this;
        }

        public Criteria andBiddingIdEqualTo(String value) {
            addCriterion("bidding_id =", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdNotEqualTo(String value) {
            addCriterion("bidding_id <>", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdGreaterThan(String value) {
            addCriterion("bidding_id >", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdGreaterThanOrEqualTo(String value) {
            addCriterion("bidding_id >=", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdLessThan(String value) {
            addCriterion("bidding_id <", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdLessThanOrEqualTo(String value) {
            addCriterion("bidding_id <=", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdLike(String value) {
            addCriterion("bidding_id like", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdNotLike(String value) {
            addCriterion("bidding_id not like", value, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdIn(List<String> values) {
            addCriterion("bidding_id in", values, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdNotIn(List<String> values) {
            addCriterion("bidding_id not in", values, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdBetween(String value1, String value2) {
            addCriterion("bidding_id between", value1, value2, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBiddingIdNotBetween(String value1, String value2) {
            addCriterion("bidding_id not between", value1, value2, "biddingId");
            return (Criteria) this;
        }

        public Criteria andBwIdIsNull() {
            addCriterion("bw_id is null");
            return (Criteria) this;
        }

        public Criteria andBwIdIsNotNull() {
            addCriterion("bw_id is not null");
            return (Criteria) this;
        }

        public Criteria andBwIdEqualTo(String value) {
            addCriterion("bw_id =", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdNotEqualTo(String value) {
            addCriterion("bw_id <>", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdGreaterThan(String value) {
            addCriterion("bw_id >", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdGreaterThanOrEqualTo(String value) {
            addCriterion("bw_id >=", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdLessThan(String value) {
            addCriterion("bw_id <", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdLessThanOrEqualTo(String value) {
            addCriterion("bw_id <=", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdLike(String value) {
            addCriterion("bw_id like", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdNotLike(String value) {
            addCriterion("bw_id not like", value, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdIn(List<String> values) {
            addCriterion("bw_id in", values, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdNotIn(List<String> values) {
            addCriterion("bw_id not in", values, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdBetween(String value1, String value2) {
            addCriterion("bw_id between", value1, value2, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwIdNotBetween(String value1, String value2) {
            addCriterion("bw_id not between", value1, value2, "bwId");
            return (Criteria) this;
        }

        public Criteria andBwAmtIsNull() {
            addCriterion("bw_amt is null");
            return (Criteria) this;
        }

        public Criteria andBwAmtIsNotNull() {
            addCriterion("bw_amt is not null");
            return (Criteria) this;
        }

        public Criteria andBwAmtEqualTo(BigDecimal value) {
            addCriterion("bw_amt =", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtNotEqualTo(BigDecimal value) {
            addCriterion("bw_amt <>", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtGreaterThan(BigDecimal value) {
            addCriterion("bw_amt >", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bw_amt >=", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtLessThan(BigDecimal value) {
            addCriterion("bw_amt <", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bw_amt <=", value, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtIn(List<BigDecimal> values) {
            addCriterion("bw_amt in", values, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtNotIn(List<BigDecimal> values) {
            addCriterion("bw_amt not in", values, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bw_amt between", value1, value2, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andBwAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bw_amt not between", value1, value2, "bwAmt");
            return (Criteria) this;
        }

        public Criteria andMortgageIdIsNull() {
            addCriterion("mortgage_id is null");
            return (Criteria) this;
        }

        public Criteria andMortgageIdIsNotNull() {
            addCriterion("mortgage_id is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageIdEqualTo(String value) {
            addCriterion("mortgage_id =", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdNotEqualTo(String value) {
            addCriterion("mortgage_id <>", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdGreaterThan(String value) {
            addCriterion("mortgage_id >", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_id >=", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdLessThan(String value) {
            addCriterion("mortgage_id <", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdLessThanOrEqualTo(String value) {
            addCriterion("mortgage_id <=", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdLike(String value) {
            addCriterion("mortgage_id like", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdNotLike(String value) {
            addCriterion("mortgage_id not like", value, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdIn(List<String> values) {
            addCriterion("mortgage_id in", values, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdNotIn(List<String> values) {
            addCriterion("mortgage_id not in", values, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdBetween(String value1, String value2) {
            addCriterion("mortgage_id between", value1, value2, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageIdNotBetween(String value1, String value2) {
            addCriterion("mortgage_id not between", value1, value2, "mortgageId");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoIsNull() {
            addCriterion("mortgage_info is null");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoIsNotNull() {
            addCriterion("mortgage_info is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoEqualTo(String value) {
            addCriterion("mortgage_info =", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoNotEqualTo(String value) {
            addCriterion("mortgage_info <>", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoGreaterThan(String value) {
            addCriterion("mortgage_info >", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_info >=", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoLessThan(String value) {
            addCriterion("mortgage_info <", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoLessThanOrEqualTo(String value) {
            addCriterion("mortgage_info <=", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoLike(String value) {
            addCriterion("mortgage_info like", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoNotLike(String value) {
            addCriterion("mortgage_info not like", value, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoIn(List<String> values) {
            addCriterion("mortgage_info in", values, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoNotIn(List<String> values) {
            addCriterion("mortgage_info not in", values, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoBetween(String value1, String value2) {
            addCriterion("mortgage_info between", value1, value2, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageInfoNotBetween(String value1, String value2) {
            addCriterion("mortgage_info not between", value1, value2, "mortgageInfo");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(String value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(String value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(String value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(String value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(String value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(String value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLike(String value) {
            addCriterion("check_date like", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotLike(String value) {
            addCriterion("check_date not like", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<String> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<String> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(String value1, String value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(String value1, String value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
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

        public Criteria andBwUseIsNull() {
            addCriterion("bw_use is null");
            return (Criteria) this;
        }

        public Criteria andBwUseIsNotNull() {
            addCriterion("bw_use is not null");
            return (Criteria) this;
        }

        public Criteria andBwUseEqualTo(String value) {
            addCriterion("bw_use =", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseNotEqualTo(String value) {
            addCriterion("bw_use <>", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseGreaterThan(String value) {
            addCriterion("bw_use >", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseGreaterThanOrEqualTo(String value) {
            addCriterion("bw_use >=", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseLessThan(String value) {
            addCriterion("bw_use <", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseLessThanOrEqualTo(String value) {
            addCriterion("bw_use <=", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseLike(String value) {
            addCriterion("bw_use like", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseNotLike(String value) {
            addCriterion("bw_use not like", value, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseIn(List<String> values) {
            addCriterion("bw_use in", values, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseNotIn(List<String> values) {
            addCriterion("bw_use not in", values, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseBetween(String value1, String value2) {
            addCriterion("bw_use between", value1, value2, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwUseNotBetween(String value1, String value2) {
            addCriterion("bw_use not between", value1, value2, "bwUse");
            return (Criteria) this;
        }

        public Criteria andBwInfoIsNull() {
            addCriterion("bw_info is null");
            return (Criteria) this;
        }

        public Criteria andBwInfoIsNotNull() {
            addCriterion("bw_info is not null");
            return (Criteria) this;
        }

        public Criteria andBwInfoEqualTo(String value) {
            addCriterion("bw_info =", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoNotEqualTo(String value) {
            addCriterion("bw_info <>", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoGreaterThan(String value) {
            addCriterion("bw_info >", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoGreaterThanOrEqualTo(String value) {
            addCriterion("bw_info >=", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoLessThan(String value) {
            addCriterion("bw_info <", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoLessThanOrEqualTo(String value) {
            addCriterion("bw_info <=", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoLike(String value) {
            addCriterion("bw_info like", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoNotLike(String value) {
            addCriterion("bw_info not like", value, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoIn(List<String> values) {
            addCriterion("bw_info in", values, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoNotIn(List<String> values) {
            addCriterion("bw_info not in", values, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoBetween(String value1, String value2) {
            addCriterion("bw_info between", value1, value2, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andBwInfoNotBetween(String value1, String value2) {
            addCriterion("bw_info not between", value1, value2, "bwInfo");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerIsNull() {
            addCriterion("mortgage_owner is null");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerIsNotNull() {
            addCriterion("mortgage_owner is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerEqualTo(String value) {
            addCriterion("mortgage_owner =", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerNotEqualTo(String value) {
            addCriterion("mortgage_owner <>", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerGreaterThan(String value) {
            addCriterion("mortgage_owner >", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_owner >=", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerLessThan(String value) {
            addCriterion("mortgage_owner <", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerLessThanOrEqualTo(String value) {
            addCriterion("mortgage_owner <=", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerLike(String value) {
            addCriterion("mortgage_owner like", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerNotLike(String value) {
            addCriterion("mortgage_owner not like", value, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerIn(List<String> values) {
            addCriterion("mortgage_owner in", values, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerNotIn(List<String> values) {
            addCriterion("mortgage_owner not in", values, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerBetween(String value1, String value2) {
            addCriterion("mortgage_owner between", value1, value2, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnerNotBetween(String value1, String value2) {
            addCriterion("mortgage_owner not between", value1, value2, "mortgageOwner");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonIsNull() {
            addCriterion("mortgage_common is null");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonIsNotNull() {
            addCriterion("mortgage_common is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonEqualTo(String value) {
            addCriterion("mortgage_common =", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonNotEqualTo(String value) {
            addCriterion("mortgage_common <>", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonGreaterThan(String value) {
            addCriterion("mortgage_common >", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_common >=", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonLessThan(String value) {
            addCriterion("mortgage_common <", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonLessThanOrEqualTo(String value) {
            addCriterion("mortgage_common <=", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonLike(String value) {
            addCriterion("mortgage_common like", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonNotLike(String value) {
            addCriterion("mortgage_common not like", value, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonIn(List<String> values) {
            addCriterion("mortgage_common in", values, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonNotIn(List<String> values) {
            addCriterion("mortgage_common not in", values, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonBetween(String value1, String value2) {
            addCriterion("mortgage_common between", value1, value2, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageCommonNotBetween(String value1, String value2) {
            addCriterion("mortgage_common not between", value1, value2, "mortgageCommon");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleIsNull() {
            addCriterion("mortgage_own_style is null");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleIsNotNull() {
            addCriterion("mortgage_own_style is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleEqualTo(String value) {
            addCriterion("mortgage_own_style =", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleNotEqualTo(String value) {
            addCriterion("mortgage_own_style <>", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleGreaterThan(String value) {
            addCriterion("mortgage_own_style >", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_own_style >=", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleLessThan(String value) {
            addCriterion("mortgage_own_style <", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleLessThanOrEqualTo(String value) {
            addCriterion("mortgage_own_style <=", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleLike(String value) {
            addCriterion("mortgage_own_style like", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleNotLike(String value) {
            addCriterion("mortgage_own_style not like", value, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleIn(List<String> values) {
            addCriterion("mortgage_own_style in", values, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleNotIn(List<String> values) {
            addCriterion("mortgage_own_style not in", values, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleBetween(String value1, String value2) {
            addCriterion("mortgage_own_style between", value1, value2, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageOwnStyleNotBetween(String value1, String value2) {
            addCriterion("mortgage_own_style not between", value1, value2, "mortgageOwnStyle");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionIsNull() {
            addCriterion("mortgage_region is null");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionIsNotNull() {
            addCriterion("mortgage_region is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionEqualTo(String value) {
            addCriterion("mortgage_region =", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionNotEqualTo(String value) {
            addCriterion("mortgage_region <>", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionGreaterThan(String value) {
            addCriterion("mortgage_region >", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_region >=", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionLessThan(String value) {
            addCriterion("mortgage_region <", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionLessThanOrEqualTo(String value) {
            addCriterion("mortgage_region <=", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionLike(String value) {
            addCriterion("mortgage_region like", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionNotLike(String value) {
            addCriterion("mortgage_region not like", value, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionIn(List<String> values) {
            addCriterion("mortgage_region in", values, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionNotIn(List<String> values) {
            addCriterion("mortgage_region not in", values, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionBetween(String value1, String value2) {
            addCriterion("mortgage_region between", value1, value2, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageRegionNotBetween(String value1, String value2) {
            addCriterion("mortgage_region not between", value1, value2, "mortgageRegion");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeIsNull() {
            addCriterion("mortgage_type is null");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeIsNotNull() {
            addCriterion("mortgage_type is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeEqualTo(String value) {
            addCriterion("mortgage_type =", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeNotEqualTo(String value) {
            addCriterion("mortgage_type <>", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeGreaterThan(String value) {
            addCriterion("mortgage_type >", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_type >=", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeLessThan(String value) {
            addCriterion("mortgage_type <", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeLessThanOrEqualTo(String value) {
            addCriterion("mortgage_type <=", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeLike(String value) {
            addCriterion("mortgage_type like", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeNotLike(String value) {
            addCriterion("mortgage_type not like", value, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeIn(List<String> values) {
            addCriterion("mortgage_type in", values, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeNotIn(List<String> values) {
            addCriterion("mortgage_type not in", values, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeBetween(String value1, String value2) {
            addCriterion("mortgage_type between", value1, value2, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageTypeNotBetween(String value1, String value2) {
            addCriterion("mortgage_type not between", value1, value2, "mortgageType");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceIsNull() {
            addCriterion("mortgage_buy_price is null");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceIsNotNull() {
            addCriterion("mortgage_buy_price is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceEqualTo(String value) {
            addCriterion("mortgage_buy_price =", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceNotEqualTo(String value) {
            addCriterion("mortgage_buy_price <>", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceGreaterThan(String value) {
            addCriterion("mortgage_buy_price >", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_buy_price >=", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceLessThan(String value) {
            addCriterion("mortgage_buy_price <", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceLessThanOrEqualTo(String value) {
            addCriterion("mortgage_buy_price <=", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceLike(String value) {
            addCriterion("mortgage_buy_price like", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceNotLike(String value) {
            addCriterion("mortgage_buy_price not like", value, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceIn(List<String> values) {
            addCriterion("mortgage_buy_price in", values, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceNotIn(List<String> values) {
            addCriterion("mortgage_buy_price not in", values, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceBetween(String value1, String value2) {
            addCriterion("mortgage_buy_price between", value1, value2, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageBuyPriceNotBetween(String value1, String value2) {
            addCriterion("mortgage_buy_price not between", value1, value2, "mortgageBuyPrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceIsNull() {
            addCriterion("mortgage_evaluate_price is null");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceIsNotNull() {
            addCriterion("mortgage_evaluate_price is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceEqualTo(String value) {
            addCriterion("mortgage_evaluate_price =", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceNotEqualTo(String value) {
            addCriterion("mortgage_evaluate_price <>", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceGreaterThan(String value) {
            addCriterion("mortgage_evaluate_price >", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_evaluate_price >=", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceLessThan(String value) {
            addCriterion("mortgage_evaluate_price <", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceLessThanOrEqualTo(String value) {
            addCriterion("mortgage_evaluate_price <=", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceLike(String value) {
            addCriterion("mortgage_evaluate_price like", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceNotLike(String value) {
            addCriterion("mortgage_evaluate_price not like", value, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceIn(List<String> values) {
            addCriterion("mortgage_evaluate_price in", values, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceNotIn(List<String> values) {
            addCriterion("mortgage_evaluate_price not in", values, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceBetween(String value1, String value2) {
            addCriterion("mortgage_evaluate_price between", value1, value2, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageEvaluatePriceNotBetween(String value1, String value2) {
            addCriterion("mortgage_evaluate_price not between", value1, value2, "mortgageEvaluatePrice");
            return (Criteria) this;
        }

        public Criteria andMortgageRateIsNull() {
            addCriterion("mortgage_rate is null");
            return (Criteria) this;
        }

        public Criteria andMortgageRateIsNotNull() {
            addCriterion("mortgage_rate is not null");
            return (Criteria) this;
        }

        public Criteria andMortgageRateEqualTo(String value) {
            addCriterion("mortgage_rate =", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateNotEqualTo(String value) {
            addCriterion("mortgage_rate <>", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateGreaterThan(String value) {
            addCriterion("mortgage_rate >", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateGreaterThanOrEqualTo(String value) {
            addCriterion("mortgage_rate >=", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateLessThan(String value) {
            addCriterion("mortgage_rate <", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateLessThanOrEqualTo(String value) {
            addCriterion("mortgage_rate <=", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateLike(String value) {
            addCriterion("mortgage_rate like", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateNotLike(String value) {
            addCriterion("mortgage_rate not like", value, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateIn(List<String> values) {
            addCriterion("mortgage_rate in", values, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateNotIn(List<String> values) {
            addCriterion("mortgage_rate not in", values, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateBetween(String value1, String value2) {
            addCriterion("mortgage_rate between", value1, value2, "mortgageRate");
            return (Criteria) this;
        }

        public Criteria andMortgageRateNotBetween(String value1, String value2) {
            addCriterion("mortgage_rate not between", value1, value2, "mortgageRate");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：hx_borrower_info
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
     * 数据库表：hx_borrower_info
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