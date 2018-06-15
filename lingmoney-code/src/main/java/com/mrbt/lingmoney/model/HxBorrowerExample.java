package com.mrbt.lingmoney.model;

import java.util.ArrayList;
import java.util.List;

public class HxBorrowerExample {
    /**
     * 排序字段,hx_borrower
     */
    protected String orderByClause;

    /**
     * 是否过滤重复数据,hx_borrower
     */
    protected boolean distinct;

    /**
     * 查询条件实例,hx_borrower
     */
    protected List<Criteria> oredCriteria;

    /**
     * 第一个返回记录行的偏移量,hx_borrower
     */
    protected int limitStart = -1;

    /**
     * 返回记录行的最大数目,hx_borrower
     */
    protected int limitEnd = -1;

    /**
     *  构造查询条件,hx_borrower
     */
    public HxBorrowerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *  设置排序字段,hx_borrower
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *  获取排序字段,hx_borrower
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *  设置过滤重复数据,hx_borrower
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *  是否过滤重复数据,hx_borrower
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *  获取当前的查询条件实例,hx_borrower
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *  增加或者的查询条件,用于构建或者查询,hx_borrower
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *  创建一个新的或者查询条件,hx_borrower
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *  创建一个查询条件,hx_borrower
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *  内部构建查询条件对象,hx_borrower
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *  清除查询条件,hx_borrower
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     *  设置第一个返回记录行的偏移量,hx_borrower
     * @param limitStart 第一个返回记录行的偏移量
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     *  获取第一个返回记录行的偏移量,hx_borrower
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     *  设置返回记录行的最大数目,hx_borrower
     * @param limitEnd 返回记录行的最大数目
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     *  获取返回记录行的最大数目,hx_borrower
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * 类注释
     * GeneratedCriteria
     * 数据库表：hx_borrower
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

        public Criteria andAccIdIsNull() {
            addCriterion("acc_id is null");
            return (Criteria) this;
        }

        public Criteria andAccIdIsNotNull() {
            addCriterion("acc_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccIdEqualTo(String value) {
            addCriterion("acc_id =", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotEqualTo(String value) {
            addCriterion("acc_id <>", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdGreaterThan(String value) {
            addCriterion("acc_id >", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdGreaterThanOrEqualTo(String value) {
            addCriterion("acc_id >=", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdLessThan(String value) {
            addCriterion("acc_id <", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdLessThanOrEqualTo(String value) {
            addCriterion("acc_id <=", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdLike(String value) {
            addCriterion("acc_id like", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotLike(String value) {
            addCriterion("acc_id not like", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdIn(List<String> values) {
            addCriterion("acc_id in", values, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotIn(List<String> values) {
            addCriterion("acc_id not in", values, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdBetween(String value1, String value2) {
            addCriterion("acc_id between", value1, value2, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotBetween(String value1, String value2) {
            addCriterion("acc_id not between", value1, value2, "accId");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeIsNull() {
            addCriterion("bw_idtype is null");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeIsNotNull() {
            addCriterion("bw_idtype is not null");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeEqualTo(String value) {
            addCriterion("bw_idtype =", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeNotEqualTo(String value) {
            addCriterion("bw_idtype <>", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeGreaterThan(String value) {
            addCriterion("bw_idtype >", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("bw_idtype >=", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeLessThan(String value) {
            addCriterion("bw_idtype <", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeLessThanOrEqualTo(String value) {
            addCriterion("bw_idtype <=", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeLike(String value) {
            addCriterion("bw_idtype like", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeNotLike(String value) {
            addCriterion("bw_idtype not like", value, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeIn(List<String> values) {
            addCriterion("bw_idtype in", values, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeNotIn(List<String> values) {
            addCriterion("bw_idtype not in", values, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeBetween(String value1, String value2) {
            addCriterion("bw_idtype between", value1, value2, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwIdtypeNotBetween(String value1, String value2) {
            addCriterion("bw_idtype not between", value1, value2, "bwIdtype");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidIsNull() {
            addCriterion("bw_acbankid is null");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidIsNotNull() {
            addCriterion("bw_acbankid is not null");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidEqualTo(String value) {
            addCriterion("bw_acbankid =", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidNotEqualTo(String value) {
            addCriterion("bw_acbankid <>", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidGreaterThan(String value) {
            addCriterion("bw_acbankid >", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidGreaterThanOrEqualTo(String value) {
            addCriterion("bw_acbankid >=", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidLessThan(String value) {
            addCriterion("bw_acbankid <", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidLessThanOrEqualTo(String value) {
            addCriterion("bw_acbankid <=", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidLike(String value) {
            addCriterion("bw_acbankid like", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidNotLike(String value) {
            addCriterion("bw_acbankid not like", value, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidIn(List<String> values) {
            addCriterion("bw_acbankid in", values, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidNotIn(List<String> values) {
            addCriterion("bw_acbankid not in", values, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidBetween(String value1, String value2) {
            addCriterion("bw_acbankid between", value1, value2, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbankidNotBetween(String value1, String value2) {
            addCriterion("bw_acbankid not between", value1, value2, "bwAcbankid");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameIsNull() {
            addCriterion("bw_acbankname is null");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameIsNotNull() {
            addCriterion("bw_acbankname is not null");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameEqualTo(String value) {
            addCriterion("bw_acbankname =", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameNotEqualTo(String value) {
            addCriterion("bw_acbankname <>", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameGreaterThan(String value) {
            addCriterion("bw_acbankname >", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameGreaterThanOrEqualTo(String value) {
            addCriterion("bw_acbankname >=", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameLessThan(String value) {
            addCriterion("bw_acbankname <", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameLessThanOrEqualTo(String value) {
            addCriterion("bw_acbankname <=", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameLike(String value) {
            addCriterion("bw_acbankname like", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameNotLike(String value) {
            addCriterion("bw_acbankname not like", value, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameIn(List<String> values) {
            addCriterion("bw_acbankname in", values, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameNotIn(List<String> values) {
            addCriterion("bw_acbankname not in", values, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameBetween(String value1, String value2) {
            addCriterion("bw_acbankname between", value1, value2, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andBwAcbanknameNotBetween(String value1, String value2) {
            addCriterion("bw_acbankname not between", value1, value2, "bwAcbankname");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andBwAgeIsNull() {
            addCriterion("bw_age is null");
            return (Criteria) this;
        }

        public Criteria andBwAgeIsNotNull() {
            addCriterion("bw_age is not null");
            return (Criteria) this;
        }

        public Criteria andBwAgeEqualTo(Integer value) {
            addCriterion("bw_age =", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeNotEqualTo(Integer value) {
            addCriterion("bw_age <>", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeGreaterThan(Integer value) {
            addCriterion("bw_age >", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("bw_age >=", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeLessThan(Integer value) {
            addCriterion("bw_age <", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeLessThanOrEqualTo(Integer value) {
            addCriterion("bw_age <=", value, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeIn(List<Integer> values) {
            addCriterion("bw_age in", values, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeNotIn(List<Integer> values) {
            addCriterion("bw_age not in", values, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeBetween(Integer value1, Integer value2) {
            addCriterion("bw_age between", value1, value2, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("bw_age not between", value1, value2, "bwAge");
            return (Criteria) this;
        }

        public Criteria andBwMarriageIsNull() {
            addCriterion("bw_marriage is null");
            return (Criteria) this;
        }

        public Criteria andBwMarriageIsNotNull() {
            addCriterion("bw_marriage is not null");
            return (Criteria) this;
        }

        public Criteria andBwMarriageEqualTo(String value) {
            addCriterion("bw_marriage =", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageNotEqualTo(String value) {
            addCriterion("bw_marriage <>", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageGreaterThan(String value) {
            addCriterion("bw_marriage >", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageGreaterThanOrEqualTo(String value) {
            addCriterion("bw_marriage >=", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageLessThan(String value) {
            addCriterion("bw_marriage <", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageLessThanOrEqualTo(String value) {
            addCriterion("bw_marriage <=", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageLike(String value) {
            addCriterion("bw_marriage like", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageNotLike(String value) {
            addCriterion("bw_marriage not like", value, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageIn(List<String> values) {
            addCriterion("bw_marriage in", values, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageNotIn(List<String> values) {
            addCriterion("bw_marriage not in", values, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageBetween(String value1, String value2) {
            addCriterion("bw_marriage between", value1, value2, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwMarriageNotBetween(String value1, String value2) {
            addCriterion("bw_marriage not between", value1, value2, "bwMarriage");
            return (Criteria) this;
        }

        public Criteria andBwSexIsNull() {
            addCriterion("bw_sex is null");
            return (Criteria) this;
        }

        public Criteria andBwSexIsNotNull() {
            addCriterion("bw_sex is not null");
            return (Criteria) this;
        }

        public Criteria andBwSexEqualTo(String value) {
            addCriterion("bw_sex =", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexNotEqualTo(String value) {
            addCriterion("bw_sex <>", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexGreaterThan(String value) {
            addCriterion("bw_sex >", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexGreaterThanOrEqualTo(String value) {
            addCriterion("bw_sex >=", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexLessThan(String value) {
            addCriterion("bw_sex <", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexLessThanOrEqualTo(String value) {
            addCriterion("bw_sex <=", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexLike(String value) {
            addCriterion("bw_sex like", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexNotLike(String value) {
            addCriterion("bw_sex not like", value, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexIn(List<String> values) {
            addCriterion("bw_sex in", values, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexNotIn(List<String> values) {
            addCriterion("bw_sex not in", values, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexBetween(String value1, String value2) {
            addCriterion("bw_sex between", value1, value2, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwSexNotBetween(String value1, String value2) {
            addCriterion("bw_sex not between", value1, value2, "bwSex");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordIsNull() {
            addCriterion("bw_credit_record is null");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordIsNotNull() {
            addCriterion("bw_credit_record is not null");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordEqualTo(String value) {
            addCriterion("bw_credit_record =", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordNotEqualTo(String value) {
            addCriterion("bw_credit_record <>", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordGreaterThan(String value) {
            addCriterion("bw_credit_record >", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordGreaterThanOrEqualTo(String value) {
            addCriterion("bw_credit_record >=", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordLessThan(String value) {
            addCriterion("bw_credit_record <", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordLessThanOrEqualTo(String value) {
            addCriterion("bw_credit_record <=", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordLike(String value) {
            addCriterion("bw_credit_record like", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordNotLike(String value) {
            addCriterion("bw_credit_record not like", value, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordIn(List<String> values) {
            addCriterion("bw_credit_record in", values, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordNotIn(List<String> values) {
            addCriterion("bw_credit_record not in", values, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordBetween(String value1, String value2) {
            addCriterion("bw_credit_record between", value1, value2, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwCreditRecordNotBetween(String value1, String value2) {
            addCriterion("bw_credit_record not between", value1, value2, "bwCreditRecord");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceIsNull() {
            addCriterion("bw_orgin_place is null");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceIsNotNull() {
            addCriterion("bw_orgin_place is not null");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceEqualTo(String value) {
            addCriterion("bw_orgin_place =", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceNotEqualTo(String value) {
            addCriterion("bw_orgin_place <>", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceGreaterThan(String value) {
            addCriterion("bw_orgin_place >", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("bw_orgin_place >=", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceLessThan(String value) {
            addCriterion("bw_orgin_place <", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceLessThanOrEqualTo(String value) {
            addCriterion("bw_orgin_place <=", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceLike(String value) {
            addCriterion("bw_orgin_place like", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceNotLike(String value) {
            addCriterion("bw_orgin_place not like", value, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceIn(List<String> values) {
            addCriterion("bw_orgin_place in", values, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceNotIn(List<String> values) {
            addCriterion("bw_orgin_place not in", values, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceBetween(String value1, String value2) {
            addCriterion("bw_orgin_place between", value1, value2, "bwOrginPlace");
            return (Criteria) this;
        }

        public Criteria andBwOrginPlaceNotBetween(String value1, String value2) {
            addCriterion("bw_orgin_place not between", value1, value2, "bwOrginPlace");
            return (Criteria) this;
        }
    }

    /**
     * 类注释
     * Criteria
     * 数据库表：hx_borrower
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
     * 数据库表：hx_borrower
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