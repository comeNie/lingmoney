package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class HxBiddingExample {
    /**
     * 排序字段,hx_bidding
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,hx_bidding
     */
    protected boolean distinct;

    /**
     * 查询条件实例,hx_bidding
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,hx_bidding
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,hx_bidding
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,hx_bidding
     */
    public HxBiddingExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,hx_bidding
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,hx_bidding
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,hx_bidding
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,hx_bidding
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,hx_bidding
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,hx_bidding
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,hx_bidding
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,hx_bidding
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,hx_bidding
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,hx_bidding
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,hx_bidding
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,hx_bidding
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,hx_bidding
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,hx_bidding
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：hx_bidding
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

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(Integer value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(Integer value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(Integer value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(Integer value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(Integer value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<Integer> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<Integer> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(Integer value1, Integer value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(Integer value1, Integer value2) {
            addCriterion("p_id not between", value1, value2, "pId");
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

        public Criteria andInvestIdIsNull() {
            addCriterion("invest_id is null");
            return (Criteria) this;
        }

        public Criteria andInvestIdIsNotNull() {
            addCriterion("invest_id is not null");
            return (Criteria) this;
        }

        public Criteria andInvestIdEqualTo(String value) {
            addCriterion("invest_id =", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotEqualTo(String value) {
            addCriterion("invest_id <>", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdGreaterThan(String value) {
            addCriterion("invest_id >", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdGreaterThanOrEqualTo(String value) {
            addCriterion("invest_id >=", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdLessThan(String value) {
            addCriterion("invest_id <", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdLessThanOrEqualTo(String value) {
            addCriterion("invest_id <=", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdLike(String value) {
            addCriterion("invest_id like", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotLike(String value) {
            addCriterion("invest_id not like", value, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdIn(List<String> values) {
            addCriterion("invest_id in", values, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotIn(List<String> values) {
            addCriterion("invest_id not in", values, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdBetween(String value1, String value2) {
            addCriterion("invest_id between", value1, value2, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestIdNotBetween(String value1, String value2) {
            addCriterion("invest_id not between", value1, value2, "investId");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoIsNull() {
            addCriterion("invest_obj_info is null");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoIsNotNull() {
            addCriterion("invest_obj_info is not null");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoEqualTo(String value) {
            addCriterion("invest_obj_info =", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoNotEqualTo(String value) {
            addCriterion("invest_obj_info <>", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoGreaterThan(String value) {
            addCriterion("invest_obj_info >", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoGreaterThanOrEqualTo(String value) {
            addCriterion("invest_obj_info >=", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoLessThan(String value) {
            addCriterion("invest_obj_info <", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoLessThanOrEqualTo(String value) {
            addCriterion("invest_obj_info <=", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoLike(String value) {
            addCriterion("invest_obj_info like", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoNotLike(String value) {
            addCriterion("invest_obj_info not like", value, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoIn(List<String> values) {
            addCriterion("invest_obj_info in", values, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoNotIn(List<String> values) {
            addCriterion("invest_obj_info not in", values, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoBetween(String value1, String value2) {
            addCriterion("invest_obj_info between", value1, value2, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andInvestObjInfoNotBetween(String value1, String value2) {
            addCriterion("invest_obj_info not between", value1, value2, "investObjInfo");
            return (Criteria) this;
        }

        public Criteria andRepayDateIsNull() {
            addCriterion("repay_date is null");
            return (Criteria) this;
        }

        public Criteria andRepayDateIsNotNull() {
            addCriterion("repay_date is not null");
            return (Criteria) this;
        }

        public Criteria andRepayDateEqualTo(String value) {
            addCriterion("repay_date =", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateNotEqualTo(String value) {
            addCriterion("repay_date <>", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateGreaterThan(String value) {
            addCriterion("repay_date >", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateGreaterThanOrEqualTo(String value) {
            addCriterion("repay_date >=", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateLessThan(String value) {
            addCriterion("repay_date <", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateLessThanOrEqualTo(String value) {
            addCriterion("repay_date <=", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateLike(String value) {
            addCriterion("repay_date like", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateNotLike(String value) {
            addCriterion("repay_date not like", value, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateIn(List<String> values) {
            addCriterion("repay_date in", values, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateNotIn(List<String> values) {
            addCriterion("repay_date not in", values, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateBetween(String value1, String value2) {
            addCriterion("repay_date between", value1, value2, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRepayDateNotBetween(String value1, String value2) {
            addCriterion("repay_date not between", value1, value2, "repayDate");
            return (Criteria) this;
        }

        public Criteria andRateStypeIsNull() {
            addCriterion("rate_stype is null");
            return (Criteria) this;
        }

        public Criteria andRateStypeIsNotNull() {
            addCriterion("rate_stype is not null");
            return (Criteria) this;
        }

        public Criteria andRateStypeEqualTo(String value) {
            addCriterion("rate_stype =", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeNotEqualTo(String value) {
            addCriterion("rate_stype <>", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeGreaterThan(String value) {
            addCriterion("rate_stype >", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeGreaterThanOrEqualTo(String value) {
            addCriterion("rate_stype >=", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeLessThan(String value) {
            addCriterion("rate_stype <", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeLessThanOrEqualTo(String value) {
            addCriterion("rate_stype <=", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeLike(String value) {
            addCriterion("rate_stype like", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeNotLike(String value) {
            addCriterion("rate_stype not like", value, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeIn(List<String> values) {
            addCriterion("rate_stype in", values, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeNotIn(List<String> values) {
            addCriterion("rate_stype not in", values, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeBetween(String value1, String value2) {
            addCriterion("rate_stype between", value1, value2, "rateStype");
            return (Criteria) this;
        }

        public Criteria andRateStypeNotBetween(String value1, String value2) {
            addCriterion("rate_stype not between", value1, value2, "rateStype");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateIsNull() {
            addCriterion("invest_obj_state is null");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateIsNotNull() {
            addCriterion("invest_obj_state is not null");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateEqualTo(String value) {
            addCriterion("invest_obj_state =", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateNotEqualTo(String value) {
            addCriterion("invest_obj_state <>", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateGreaterThan(String value) {
            addCriterion("invest_obj_state >", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateGreaterThanOrEqualTo(String value) {
            addCriterion("invest_obj_state >=", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateLessThan(String value) {
            addCriterion("invest_obj_state <", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateLessThanOrEqualTo(String value) {
            addCriterion("invest_obj_state <=", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateLike(String value) {
            addCriterion("invest_obj_state like", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateNotLike(String value) {
            addCriterion("invest_obj_state not like", value, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateIn(List<String> values) {
            addCriterion("invest_obj_state in", values, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateNotIn(List<String> values) {
            addCriterion("invest_obj_state not in", values, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateBetween(String value1, String value2) {
            addCriterion("invest_obj_state between", value1, value2, "investObjState");
            return (Criteria) this;
        }

        public Criteria andInvestObjStateNotBetween(String value1, String value2) {
            addCriterion("invest_obj_state not between", value1, value2, "investObjState");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumIsNull() {
            addCriterion("bw_total_num is null");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumIsNotNull() {
            addCriterion("bw_total_num is not null");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumEqualTo(Integer value) {
            addCriterion("bw_total_num =", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumNotEqualTo(Integer value) {
            addCriterion("bw_total_num <>", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumGreaterThan(Integer value) {
            addCriterion("bw_total_num >", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("bw_total_num >=", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumLessThan(Integer value) {
            addCriterion("bw_total_num <", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumLessThanOrEqualTo(Integer value) {
            addCriterion("bw_total_num <=", value, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumIn(List<Integer> values) {
            addCriterion("bw_total_num in", values, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumNotIn(List<Integer> values) {
            addCriterion("bw_total_num not in", values, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumBetween(Integer value1, Integer value2) {
            addCriterion("bw_total_num between", value1, value2, "bwTotalNum");
            return (Criteria) this;
        }

        public Criteria andBwTotalNumNotBetween(Integer value1, Integer value2) {
            addCriterion("bw_total_num not between", value1, value2, "bwTotalNum");
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

        public Criteria andZrFlagIsNull() {
            addCriterion("zr_flag is null");
            return (Criteria) this;
        }

        public Criteria andZrFlagIsNotNull() {
            addCriterion("zr_flag is not null");
            return (Criteria) this;
        }

        public Criteria andZrFlagEqualTo(String value) {
            addCriterion("zr_flag =", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagNotEqualTo(String value) {
            addCriterion("zr_flag <>", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagGreaterThan(String value) {
            addCriterion("zr_flag >", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagGreaterThanOrEqualTo(String value) {
            addCriterion("zr_flag >=", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagLessThan(String value) {
            addCriterion("zr_flag <", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagLessThanOrEqualTo(String value) {
            addCriterion("zr_flag <=", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagLike(String value) {
            addCriterion("zr_flag like", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagNotLike(String value) {
            addCriterion("zr_flag not like", value, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagIn(List<String> values) {
            addCriterion("zr_flag in", values, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagNotIn(List<String> values) {
            addCriterion("zr_flag not in", values, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagBetween(String value1, String value2) {
            addCriterion("zr_flag between", value1, value2, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andZrFlagNotBetween(String value1, String value2) {
            addCriterion("zr_flag not between", value1, value2, "zrFlag");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoIsNull() {
            addCriterion("ref_loan_no is null");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoIsNotNull() {
            addCriterion("ref_loan_no is not null");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoEqualTo(String value) {
            addCriterion("ref_loan_no =", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoNotEqualTo(String value) {
            addCriterion("ref_loan_no <>", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoGreaterThan(String value) {
            addCriterion("ref_loan_no >", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoGreaterThanOrEqualTo(String value) {
            addCriterion("ref_loan_no >=", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoLessThan(String value) {
            addCriterion("ref_loan_no <", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoLessThanOrEqualTo(String value) {
            addCriterion("ref_loan_no <=", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoLike(String value) {
            addCriterion("ref_loan_no like", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoNotLike(String value) {
            addCriterion("ref_loan_no not like", value, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoIn(List<String> values) {
            addCriterion("ref_loan_no in", values, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoNotIn(List<String> values) {
            addCriterion("ref_loan_no not in", values, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoBetween(String value1, String value2) {
            addCriterion("ref_loan_no between", value1, value2, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andRefLoanNoNotBetween(String value1, String value2) {
            addCriterion("ref_loan_no not between", value1, value2, "refLoanNo");
            return (Criteria) this;
        }

        public Criteria andOldReqseqIsNull() {
            addCriterion("old_reqseq is null");
            return (Criteria) this;
        }

        public Criteria andOldReqseqIsNotNull() {
            addCriterion("old_reqseq is not null");
            return (Criteria) this;
        }

        public Criteria andOldReqseqEqualTo(String value) {
            addCriterion("old_reqseq =", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqNotEqualTo(String value) {
            addCriterion("old_reqseq <>", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqGreaterThan(String value) {
            addCriterion("old_reqseq >", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqGreaterThanOrEqualTo(String value) {
            addCriterion("old_reqseq >=", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqLessThan(String value) {
            addCriterion("old_reqseq <", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqLessThanOrEqualTo(String value) {
            addCriterion("old_reqseq <=", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqLike(String value) {
            addCriterion("old_reqseq like", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqNotLike(String value) {
            addCriterion("old_reqseq not like", value, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqIn(List<String> values) {
            addCriterion("old_reqseq in", values, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqNotIn(List<String> values) {
            addCriterion("old_reqseq not in", values, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqBetween(String value1, String value2) {
            addCriterion("old_reqseq between", value1, value2, "oldReqseq");
            return (Criteria) this;
        }

        public Criteria andOldReqseqNotBetween(String value1, String value2) {
            addCriterion("old_reqseq not between", value1, value2, "oldReqseq");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：hx_bidding
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
     * 数据库表：hx_bidding
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